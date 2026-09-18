import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ContratService } from '../../services/contrat.service';
import { ContratTravail, StatutContrat } from '@core/models/contrat.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-contrats-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des contrats"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Contrats' }
      ]"
      [actions]="[
        { label: 'Nouveau contrat', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <app-data-table
      [columns]="columns"
      [data]="contrats()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class ContratsListComponent implements OnInit {
  private readonly _contrats = signal<ContratTravail[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly contrats = computed(() => this._contrats());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  readonly columns: ColumnDef<ContratTravail>[] = [
    { field: 'typeContrat', header: 'Type', sortable: true },
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
    { field: 'poste', header: 'Poste', sortable: true },
    {
      field: 'salaireMensuel',
      header: 'Salaire',
      sortable: true,
      cellRenderer: (row) => row.salaireMensuel ? `${row.salaireMensuel.toLocaleString('fr-FR')} XOF` : '-'
    },
    {
      field: 'statutContrat',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutContrat}'"></app-status-badge>`
    }
  ];

  constructor(
    private contratService: ContratService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadContrats();
  }

  loadContrats(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.contratService.getAll(params).subscribe({
      next: (response: PageResponse<ContratTravail>) => {
        this._contrats.set(response.content);
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
    this.loadContrats();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
