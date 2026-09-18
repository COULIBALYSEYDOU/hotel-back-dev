import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EvaluationService } from '../../services/evaluation.service';
import { Evaluation, TypeEvaluation, StatutEvaluation } from '@core/models/evaluation.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-evaluations-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des évaluations"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Évaluations' }
      ]"
      [actions]="[
        { label: 'Nouvelle évaluation', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <app-data-table
      [columns]="columns"
      [data]="evaluations()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class EvaluationsListComponent implements OnInit {
  private readonly _evaluations = signal<Evaluation[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly evaluations = computed(() => this._evaluations());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  readonly columns: ColumnDef<Evaluation>[] = [
    { field: 'typeEvaluation', header: 'Type', sortable: true },
    {
      field: 'dateEvaluation',
      header: 'Date',
      sortable: true,
      cellRenderer: (row) => row.dateEvaluation ? new Date(row.dateEvaluation).toLocaleDateString('fr-FR') : '-'
    },
    {
      field: 'scoreGlobal',
      header: 'Score global',
      sortable: true,
      cellRenderer: (row) => row.scoreGlobal ? `${row.scoreGlobal}/100` : '-'
    },
    {
      field: 'statutEvaluation',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutEvaluation}'"></app-status-badge>`
    },
    {
      field: 'recommendation',
      header: 'Recommandation',
      sortable: true
    }
  ];

  constructor(
    private evaluationService: EvaluationService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadEvaluations();
  }

  loadEvaluations(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.evaluationService.getAll(params).subscribe({
      next: (response: PageResponse<Evaluation>) => {
        this._evaluations.set(response.content);
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
    this.loadEvaluations();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
