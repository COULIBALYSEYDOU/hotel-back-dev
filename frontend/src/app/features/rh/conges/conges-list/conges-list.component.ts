import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CongeService } from '../../services/conge.service';
import { Conge, StatutConge, TypeConge } from '@core/models/conge.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ConfirmDialogComponent } from '@shared/components/confirm-dialog/confirm-dialog.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';
import { Plus, Check, X } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-conges-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    DataTableComponent,
    StatusBadgeComponent,
    PageHeaderComponent,
    ConfirmDialogComponent,
    LucideAngularModule,
    DatePipe
  ],
  template: `
    <app-page-header
      title="Liste des congés"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Congés' }
      ]"
      [actions]="[
        { label: 'Nouvelle demande', icon: 'plus', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <!-- Filtres -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Statut</label>
          <select
            [(ngModel)]="selectedStatut"
            (ngModelChange)="onFilterChange()"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg"
          >
            <option value="">Tous les statuts</option>
            <option *ngFor="let statut of statuts" [value]="statut">{{ statut }}</option>
          </select>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Type</label>
          <select
            [(ngModel)]="selectedType"
            (ngModelChange)="onFilterChange()"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg"
          >
            <option value="">Tous les types</option>
            <option *ngFor="let type of types" [value]="type">{{ type }}</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Table -->
    <app-data-table
      [columns]="columns"
      [data]="conges()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />

    <!-- Dialog approbation -->
    <app-confirm-dialog
      [(visible)]="showApproveDialog"
      [message]="'Approuver cette demande de congé ?'"
      [confirmLabel]="'Approuver'"
      (confirmed)="onConfirmApprove()"
    />

    <!-- Dialog rejet -->
    <app-confirm-dialog
      [(visible)]="showRejectDialog"
      [message]="'Rejeter cette demande de congé ?'"
      [confirmLabel]="'Rejeter'"
      (confirmed)="onConfirmReject()"
    />
  `,
  styles: []
})
export class CongesListComponent implements OnInit {
  private readonly _conges = signal<Conge[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly conges = computed(() => this._conges());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  selectedStatut: string = '';
  selectedType: string = '';

  showApproveDialog = false;
  showRejectDialog = false;
  congeToApprove: string | null = null;
  congeToReject: string | null = null;

  readonly statuts: StatutConge[] = [
    'EN_ATTENTE',
    'EN_VALIDATION',
    'APPROUVE',
    'REJETE',
    'EN_COURS',
    'TERMINE'
  ];

  readonly types: TypeConge[] = [
    'ANNUEL',
    'MALADIE',
    'MATERNITE',
    'PATERNITE',
    'SANS_SOLDE',
    'RECUPERATION',
    'RTT'
  ];

  readonly columns: ColumnDef<Conge>[] = [
    { field: 'employeMatricule', header: 'Matricule', sortable: true },
    {
      field: 'employeNom',
      header: 'Employé',
      sortable: true,
      cellRenderer: (row) => row.employeNom || '-'
    },
    { field: 'typeConge', header: 'Type', sortable: true },
    {
      field: 'dateDebut',
      header: 'Date début',
      sortable: true,
      cellRenderer: (row) => row.dateDebut ? new Date(row.dateDebut).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'dateFin',
      header: 'Date fin',
      sortable: true,
      cellRenderer: (row) => row.dateFin ? new Date(row.dateFin).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'nombreJours',
      header: 'Jours',
      sortable: true,
      cellRenderer: (row) => row.nombreJours?.toString() || '-'
    },
    {
      field: 'statutConge',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutConge}'"></app-status-badge>`
    },
    {
      field: 'actions',
      header: 'Actions',
      align: 'right',
      cellRenderer: (row) => this.getActionsHtml(row)
    }
  ];

  constructor(
    private congeService: CongeService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadConges();
  }

  loadConges(): void {
    this._loading.set(true);
    const params: PaginationParams = {
      page: this._currentPage(),
      size: 20
    };

    this.congeService.getAll(params).subscribe({
      next: (response: PageResponse<Conge>) => {
        let filtered = response.content;
        
        if (this.selectedStatut) {
          filtered = filtered.filter(c => c.statutConge === this.selectedStatut);
        }
        
        if (this.selectedType) {
          filtered = filtered.filter(c => c.typeConge === this.selectedType);
        }

        this._conges.set(filtered);
        this._totalElements.set(response.totalElements);
        this._loading.set(false);
      },
      error: () => {
        this.toastr.error('Erreur lors du chargement des congés', 'Erreur');
        this._loading.set(false);
      }
    });
  }

  onPageChange(params: PaginationParams): void {
    this._currentPage.set(params.page);
    this.loadConges();
  }

  onFilterChange(): void {
    this._currentPage.set(0);
    this.loadConges();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }

  viewConge(uuid: string): void {
    // TODO: Navigation
  }

  approveConge(uuid: string): void {
    this.congeToApprove = uuid;
    this.showApproveDialog = true;
  }

  rejectConge(uuid: string): void {
    this.congeToReject = uuid;
    this.showRejectDialog = true;
  }

  onConfirmApprove(): void {
    if (this.congeToApprove) {
      // TODO: Récupérer l'ID de l'approbateur depuis le service auth
      this.congeService.approve(this.congeToApprove, 1).subscribe({
        next: () => {
          this.toastr.success('Congé approuvé avec succès', 'Succès');
          this.loadConges();
        },
        error: () => {
          this.toastr.error('Erreur lors de l\'approbation', 'Erreur');
        }
      });
    }
    this.showApproveDialog = false;
    this.congeToApprove = null;
  }

  onConfirmReject(): void {
    if (this.congeToReject) {
      this.congeService.reject(this.congeToReject, 'Rejeté par le manager').subscribe({
        next: () => {
          this.toastr.success('Congé rejeté', 'Succès');
          this.loadConges();
        },
        error: () => {
          this.toastr.error('Erreur lors du rejet', 'Erreur');
        }
      });
    }
    this.showRejectDialog = false;
    this.congeToReject = null;
  }

  private getActionsHtml(row: Conge): string {
    const canApprove = row.statutConge === 'EN_ATTENTE' || row.statutConge === 'EN_VALIDATION';
    return `
      <div class="flex items-center justify-end space-x-2">
        <button class="text-primary-600 hover:text-primary-800" title="Voir">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
          </svg>
        </button>
        ${canApprove ? `
          <button class="text-green-600 hover:text-green-800" title="Approuver">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
            </svg>
          </button>
          <button class="text-red-600 hover:text-red-800" title="Rejeter">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        ` : ''}
      </div>
    `;
  }
}
