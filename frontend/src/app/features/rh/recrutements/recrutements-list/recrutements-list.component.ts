import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RecrutementService } from '../../services/recrutement.service';
import { Recrutement, StatutCandidature } from '@core/models/recrutement.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-recrutements-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des recrutements"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Recrutements' }
      ]"
      [actions]="[
        { label: 'Nouveau recrutement', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Statut</label>
          <select [(ngModel)]="selectedStatut" (ngModelChange)="onFilterChange()" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
            <option value="">Tous les statuts</option>
            <option *ngFor="let statut of statuts" [value]="statut">{{ statut }}</option>
          </select>
        </div>
      </div>
    </div>

    <app-data-table
      [columns]="columns"
      [data]="recrutements()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class RecrutementsListComponent implements OnInit {
  private readonly _recrutements = signal<Recrutement[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly recrutements = computed(() => this._recrutements());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  selectedStatut: string = '';

  readonly statuts: StatutCandidature[] = [
    'NOUVEAU', 'EN_ATTENTE', 'ENTRETIEN_PLANIFIE', 'ENTRETIEN_EN_COURS',
    'EN_EVALUATION', 'ACCEPTE', 'REJETE'
  ];

  readonly columns: ColumnDef<Recrutement>[] = [
    { field: 'candidatNom', header: 'Candidat', sortable: true },
    { field: 'poste', header: 'Poste', sortable: true },
    { field: 'departement', header: 'Département', sortable: true },
    {
      field: 'dateCandidature',
      header: 'Date candidature',
      sortable: true,
      cellRenderer: (row) => row.dateCandidature ? new Date(row.dateCandidature).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'statutCandidature',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutCandidature}'"></app-status-badge>`
    },
    {
      field: 'note',
      header: 'Note',
      sortable: true,
      cellRenderer: (row) => row.note ? `${row.note}/20` : '-'
    }
  ];

  constructor(
    private recrutementService: RecrutementService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadRecrutements();
  }

  loadRecrutements(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.recrutementService.getAll(params).subscribe({
      next: (response: PageResponse<Recrutement>) => {
        let filtered = response.content;
        if (this.selectedStatut) {
          filtered = filtered.filter(r => r.statutCandidature === this.selectedStatut);
        }
        this._recrutements.set(filtered);
        this._totalElements.set(response.totalElements);
        this._loading.set(false);
      },
      error: () => {
        this.toastr.error('Erreur lors du chargement', 'Erreur');
        this._loading.set(false);
      }
    });
  }

  onPageChange(params: PaginationParams): void {
    this._currentPage.set(params.page);
    this.loadRecrutements();
  }

  onFilterChange(): void {
    this._currentPage.set(0);
    this.loadRecrutements();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
