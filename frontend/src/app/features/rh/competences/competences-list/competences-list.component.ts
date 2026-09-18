import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CompetenceService } from '../../services/competence.service';
import { Competence } from '@core/models/competence.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-competences-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des compétences"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Compétences' }
      ]"
      [actions]="[
        { label: 'Nouvelle compétence', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <app-data-table
      [columns]="columns"
      [data]="competences()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class CompetencesListComponent implements OnInit {
  private readonly _competences = signal<Competence[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly competences = computed(() => this._competences());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  readonly columns: ColumnDef<Competence>[] = [
    { field: 'nomCompetence', header: 'Compétence', sortable: true },
    { field: 'typeCompetence', header: 'Type', sortable: true },
    { field: 'niveau', header: 'Niveau', sortable: true },
    {
      field: 'score',
      header: 'Score',
      sortable: true,
      cellRenderer: (row) => row.score ? `${row.score}/100` : '-'
    },
    {
      field: 'dateExpiration',
      header: 'Expiration',
      sortable: true,
      cellRenderer: (row) => row.dateExpiration ? new Date(row.dateExpiration).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'statutValidation',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutValidation}'"></app-status-badge>`
    }
  ];

  constructor(
    private competenceService: CompetenceService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadCompetences();
  }

  loadCompetences(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.competenceService.getAll(params).subscribe({
      next: (response: PageResponse<Competence>) => {
        this._competences.set(response.content);
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
    this.loadCompetences();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
