import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FormationService } from '../../services/formation.service';
import { Formation, StatutFormation } from '@core/models/formation.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-formations-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des formations"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Formations' }
      ]"
      [actions]="[
        { label: 'Nouvelle formation', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <app-data-table
      [columns]="columns"
      [data]="formations()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class FormationsListComponent implements OnInit {
  private readonly _formations = signal<Formation[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly formations = computed(() => this._formations());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  readonly columns: ColumnDef<Formation>[] = [
    { field: 'titre', header: 'Titre', sortable: true },
    { field: 'organisme', header: 'Organisme', sortable: true },
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
      field: 'cout',
      header: 'Coût',
      sortable: true,
      cellRenderer: (row) => row.cout ? `${row.cout} ${row.devise || 'XOF'}` : '-'
    },
    {
      field: 'statutFormation',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutFormation}'"></app-status-badge>`
    }
  ];

  constructor(
    private formationService: FormationService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadFormations();
  }

  loadFormations(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.formationService.getAll(params).subscribe({
      next: (response: PageResponse<Formation>) => {
        this._formations.set(response.content);
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
    this.loadFormations();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
