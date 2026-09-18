import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EmployeService } from '../../services/employe.service';
import { Employe, Departement, StatutEmploye } from '@core/models/employe.model';
import { PageResponse, PaginationParams, SortParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ConfirmDialogComponent } from '@shared/components/confirm-dialog/confirm-dialog.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';
import { Plus, Edit, Trash2, Eye } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

/**
 * Composant liste des employés
 * Avec pagination, filtres et actions CRUD
 */
@Component({
  selector: 'app-employes-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    DataTableComponent,
    StatusBadgeComponent,
    PageHeaderComponent,
    ConfirmDialogComponent,
    LucideAngularModule
  ],
  template: `
    <app-page-header
      [title]="'Liste des employés'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Employés' }
      ]"
      [actions]="[
        { label: 'Nouvel employé', icon: 'plus', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <!-- Filtres -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">
            Département
          </label>
          <select
            [(ngModel)]="selectedDepartement"
            (ngModelChange)="onFilterChange()"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
          >
            <option value="">Tous les départements</option>
            <option *ngFor="let dept of departements" [value]="dept">
              {{ dept }}
            </option>
          </select>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">
            Statut
          </label>
          <select
            [(ngModel)]="selectedStatut"
            (ngModelChange)="onFilterChange()"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
          >
            <option value="">Tous les statuts</option>
            <option *ngFor="let statut of statuts" [value]="statut">
              {{ statut }}
            </option>
          </select>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">
            Pays
          </label>
          <select
            [(ngModel)]="selectedPays"
            (ngModelChange)="onFilterChange()"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
          >
            <option value="">Tous les pays</option>
            <option value="CI">Côte d'Ivoire</option>
            <option value="SN">Sénégal</option>
            <option value="GH">Ghana</option>
            <option value="FRA">France</option>
            <option value="CMR">Cameroun</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Table -->
    <app-data-table
      [columns]="columns"
      [data]="employes()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
      (sortChange)="onSortChange($event)"
    />

    <!-- Dialog de confirmation -->
    <app-confirm-dialog
      [(visible)]="showDeleteDialog"
      [message]="'Êtes-vous sûr de vouloir supprimer cet employé ?'"
      [confirmLabel]="'Supprimer'"
      (confirmed)="onConfirmDelete()"
    />
  `,
  styles: []
})
export class EmployesListComponent implements OnInit {
  // Signals
  private readonly _employes = signal<Employe[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);
  private readonly _pageSize = signal<number>(20);

  // Computed
  readonly employes = computed(() => this._employes());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  // Filtres
  selectedDepartement: string = '';
  selectedStatut: string = '';
  selectedPays: string = '';

  // Dialog
  showDeleteDialog = false;
  employeToDelete: string | null = null;

  // Constantes
  readonly departements: Departement[] = [
    'RECEPTION',
    'HOUSEKEEPING',
    'FINANCE',
    'RH',
    'FB',
    'MAINTENANCE',
    'SECURITE',
    'SPA',
    'RESTAURATION',
    'ADMINISTRATION'
  ];

  readonly statuts: StatutEmploye[] = [
    'ACTIF',
    'SUSPENDU',
    'INACTIF',
    'PERIODE_ESSAI',
    'LICENCIE'
  ];

  // Colonnes de la table
  readonly columns: ColumnDef<Employe>[] = [
    {
      field: 'matricule',
      header: 'Matricule',
      sortable: true,
      width: '120px'
    },
    {
      field: 'nom',
      header: 'Nom',
      sortable: true,
      cellRenderer: (row) => `${row.prenom} ${row.nom}`
    },
    {
      field: 'email',
      header: 'Email',
      sortable: true
    },
    {
      field: 'poste',
      header: 'Poste',
      sortable: true
    },
    {
      field: 'departement',
      header: 'Département',
      sortable: true
    },
    {
      field: 'statut',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statut}'"></app-status-badge>`
    },
    {
      field: 'dateEmbauche',
      header: 'Date embauche',
      sortable: true,
      cellRenderer: (row) => row.dateEmbauche ? new Date(row.dateEmbauche).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'actions',
      header: 'Actions',
      align: 'right',
      width: '150px',
      cellRenderer: (row) => this.getActionsHtml(row)
    }
  ];

  constructor(
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadEmployes();
  }

  loadEmployes(): void {
    this._loading.set(true);
    
    const params: PaginationParams = {
      page: this._currentPage(),
      size: this._pageSize()
    };

    this.employeService.getAll(params, this.selectedDepartement || undefined)
      .subscribe({
        next: (response: PageResponse<Employe>) => {
          // Filtrer par statut et pays côté client si nécessaire
          let filtered = response.content;
          
          if (this.selectedStatut) {
            filtered = filtered.filter(e => e.statut === this.selectedStatut);
          }
          
          if (this.selectedPays) {
            filtered = filtered.filter(e => e.paysCode === this.selectedPays);
          }

          this._employes.set(filtered);
          this._totalElements.set(response.totalElements);
          this._loading.set(false);
        },
        error: (error) => {
          this.toastr.error('Erreur lors du chargement des employés', 'Erreur');
          this._loading.set(false);
        }
      });
  }

  onPageChange(params: PaginationParams): void {
    this._currentPage.set(params.page);
    this.loadEmployes();
  }

  onSortChange(params: SortParams): void {
    // TODO: Implémenter le tri côté serveur si l'API le supporte
    console.log('Tri:', params);
  }

  onFilterChange(): void {
    this._currentPage.set(0);
    this.loadEmployes();
  }

  navigateToCreate(): void {
    // TODO: Implémenter la navigation
    console.log('Navigation vers création');
  }

  viewEmploye(uuid: string): void {
    // TODO: Implémenter la navigation
    console.log('Voir employé:', uuid);
  }

  editEmploye(uuid: string): void {
    // TODO: Implémenter la navigation
    console.log('Éditer employé:', uuid);
  }

  deleteEmploye(uuid: string): void {
    this.employeToDelete = uuid;
    this.showDeleteDialog = true;
  }

  onConfirmDelete(): void {
    if (this.employeToDelete) {
      this.employeService.delete(this.employeToDelete).subscribe({
        next: () => {
          this.toastr.success('Employé supprimé avec succès', 'Succès');
          this.loadEmployes();
        },
        error: (error) => {
          this.toastr.error('Erreur lors de la suppression', 'Erreur');
        }
      });
    }
    this.showDeleteDialog = false;
    this.employeToDelete = null;
  }

  activateEmploye(uuid: string): void {
    this.employeService.activate(uuid).subscribe({
      next: () => {
        this.toastr.success('Employé activé', 'Succès');
        this.loadEmployes();
      },
      error: (error) => {
        this.toastr.error('Erreur lors de l\'activation', 'Erreur');
      }
    });
  }

  deactivateEmploye(uuid: string): void {
    this.employeService.deactivate(uuid).subscribe({
      next: () => {
        this.toastr.success('Employé désactivé', 'Succès');
        this.loadEmployes();
      },
      error: (error) => {
        this.toastr.error('Erreur lors de la désactivation', 'Erreur');
      }
    });
  }

  private getActionsHtml(row: Employe): string {
    return `
      <div class="flex items-center justify-end space-x-2">
        <button class="text-primary-600 hover:text-primary-800" title="Voir">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
          </svg>
        </button>
        <button class="text-blue-600 hover:text-blue-800" title="Éditer">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
          </svg>
        </button>
        <button class="text-red-600 hover:text-red-800" title="Supprimer">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
          </svg>
        </button>
      </div>
    `;
  }
}
