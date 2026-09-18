import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AbsenceService } from '../../services/absence.service';
import { Absence, TypeAbsence } from '@core/models/absence.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-absences-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des absences"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Absences' }
      ]"
      [actions]="[
        { label: 'Nouvelle absence', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Type</label>
          <select [(ngModel)]="selectedType" (ngModelChange)="onFilterChange()" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
            <option value="">Tous les types</option>
            <option *ngFor="let type of types" [value]="type">{{ type }}</option>
          </select>
        </div>
      </div>
    </div>

    <app-data-table
      [columns]="columns"
      [data]="absences()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class AbsencesListComponent implements OnInit {
  private readonly _absences = signal<Absence[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly absences = computed(() => this._absences());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  selectedType: string = '';

  readonly types: TypeAbsence[] = [
    'MALADIE',
    'ARRET_MEDICAL',
    'ACCIDENT_TRAVAIL',
    'ABSENCE_NON_JUSTIFIEE',
    'AUTRE'
  ];

  readonly columns: ColumnDef<Absence>[] = [
    {
      field: 'dateDebut',
      header: 'Date début',
      sortable: true,
      cellRenderer: (row) => new Date(row.dateDebut).toLocaleDateString('fr-FR')
    },
    {
      field: 'dateFin',
      header: 'Date fin',
      sortable: true,
      cellRenderer: (row) => new Date(row.dateFin).toLocaleDateString('fr-FR')
    },
    { field: 'typeAbsence', header: 'Type', sortable: true },
    {
      field: 'nombreJours',
      header: 'Jours',
      sortable: true,
      cellRenderer: (row) => row.nombreJours?.toString() || '-'
    },
    {
      field: 'statutAbsence',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutAbsence}'"></app-status-badge>`
    },
    { field: 'justifiee', header: 'Justifiée', sortable: true, cellRenderer: (row) => row.justifiee ? 'Oui' : 'Non' }
  ];

  constructor(
    private absenceService: AbsenceService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadAbsences();
  }

  loadAbsences(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.absenceService.getAll(params).subscribe({
      next: (response: PageResponse<Absence>) => {
        let filtered = response.content;
        if (this.selectedType) {
          filtered = filtered.filter(a => a.typeAbsence === this.selectedType);
        }
        this._absences.set(filtered);
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
    this.loadAbsences();
  }

  onFilterChange(): void {
    this._currentPage.set(0);
    this.loadAbsences();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
