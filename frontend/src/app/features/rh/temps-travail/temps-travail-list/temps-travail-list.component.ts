import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TempsTravailService } from '../../services/temps-travail.service';
import { TempsTravail } from '@core/models/temps-travail.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-temps-travail-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Temps de travail"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Temps de travail' }
      ]"
      [actions]="[
        { label: 'Enregistrer temps', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <app-data-table
      [columns]="columns"
      [data]="tempsTravail()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class TempsTravailListComponent implements OnInit {
  private readonly _tempsTravail = signal<TempsTravail[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly tempsTravail = computed(() => this._tempsTravail());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  readonly columns: ColumnDef<TempsTravail>[] = [
    {
      field: 'dateJour',
      header: 'Date',
      sortable: true,
      cellRenderer: (row) => new Date(row.dateJour).toLocaleDateString('fr-FR')
    },
    {
      field: 'heuresNormales',
      header: 'Heures normales',
      sortable: true,
      cellRenderer: (row) => row.heuresNormales?.toString() || '-'
    },
    {
      field: 'heuresSupplementaires',
      header: 'Heures sup',
      sortable: true,
      cellRenderer: (row) => row.heuresSupplementaires?.toString() || '-'
    },
    { field: 'typeJour', header: 'Type jour', sortable: true },
    {
      field: 'statutValidation',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutValidation}'"></app-status-badge>`
    }
  ];

  constructor(
    private tempsTravailService: TempsTravailService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadTempsTravail();
  }

  loadTempsTravail(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.tempsTravailService.getAll(params).subscribe({
      next: (response: PageResponse<TempsTravail>) => {
        this._tempsTravail.set(response.content);
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
    this.loadTempsTravail();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
