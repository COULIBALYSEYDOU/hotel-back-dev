import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FichePaieService } from '../../services/fiche-paie.service';
import { FichePaie, StatutPaie } from '@core/models/fiche-paie.model';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-fiches-paie-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      title="Liste des fiches de paie"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Fiches de paie' }
      ]"
      [actions]="[
        { label: 'Nouvelle fiche', action: () => navigateToCreate(), variant: 'primary' }
      ]"
    />

    <!-- Filtres -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Mois</label>
          <select [(ngModel)]="selectedMois" (ngModelChange)="onFilterChange()" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
            <option value="">Tous les mois</option>
            <option *ngFor="let m of mois" [value]="m.value">{{ m.label }}</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Année</label>
          <input type="number" [(ngModel)]="selectedAnnee" (ngModelChange)="onFilterChange()" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
        </div>
      </div>
    </div>

    <app-data-table
      [columns]="columns"
      [data]="fichesPaie()"
      [totalElements]="totalElements()"
      [loading]="loading()"
      [pageSize]="20"
      (pageChange)="onPageChange($event)"
    />
  `,
  styles: []
})
export class FichesPaieListComponent implements OnInit {
  private readonly _fichesPaie = signal<FichePaie[]>([]);
  private readonly _loading = signal<boolean>(false);
  private readonly _totalElements = signal<number>(0);
  private readonly _currentPage = signal<number>(0);

  readonly fichesPaie = computed(() => this._fichesPaie());
  readonly loading = computed(() => this._loading());
  readonly totalElements = computed(() => this._totalElements());

  selectedMois: string = '';
  selectedAnnee: number = new Date().getFullYear();

  readonly mois = [
    { value: '1', label: 'Janvier' },
    { value: '2', label: 'Février' },
    { value: '3', label: 'Mars' },
    { value: '4', label: 'Avril' },
    { value: '5', label: 'Mai' },
    { value: '6', label: 'Juin' },
    { value: '7', label: 'Juillet' },
    { value: '8', label: 'Août' },
    { value: '9', label: 'Septembre' },
    { value: '10', label: 'Octobre' },
    { value: '11', label: 'Novembre' },
    { value: '12', label: 'Décembre' }
  ];

  readonly columns: ColumnDef<FichePaie>[] = [
    {
      field: 'mois',
      header: 'Période',
      sortable: true,
      cellRenderer: (row) => `${this.mois[row.mois - 1]?.label || row.mois}/${row.annee}`
    },
    {
      field: 'salaireBrut',
      header: 'Salaire brut',
      sortable: true,
      cellRenderer: (row) => row.salaireBrut ? `${row.salaireBrut.toLocaleString('fr-FR')} XOF` : '-'
    },
    {
      field: 'netAPayer',
      header: 'Net à payer',
      sortable: true,
      cellRenderer: (row) => row.netAPayer ? `${row.netAPayer.toLocaleString('fr-FR')} XOF` : '-'
    },
    {
      field: 'statutPaie',
      header: 'Statut',
      sortable: true,
      cellRenderer: (row) => `<app-status-badge [status]="'${row.statutPaie}'"></app-status-badge>`
    },
    {
      field: 'datePaiement',
      header: 'Date paiement',
      sortable: true,
      cellRenderer: (row) => row.datePaiement ? new Date(row.datePaiement).toLocaleDateString('fr-FR') : '-'
    }
  ];

  constructor(
    private fichePaieService: FichePaieService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadFichesPaie();
  }

  loadFichesPaie(): void {
    this._loading.set(true);
    const params: PaginationParams = { page: this._currentPage(), size: 20 };

    this.fichePaieService.getAll(params).subscribe({
      next: (response: PageResponse<FichePaie>) => {
        this._fichesPaie.set(response.content);
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
    this.loadFichesPaie();
  }

  onFilterChange(): void {
    this._currentPage.set(0);
    this.loadFichesPaie();
  }

  navigateToCreate(): void {
    // TODO: Navigation
  }
}
