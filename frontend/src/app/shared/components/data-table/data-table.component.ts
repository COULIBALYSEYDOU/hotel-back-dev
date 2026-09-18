import { Component, Input, Output, EventEmitter, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ColumnDef } from '../../models/column-def.model';
import { PaginationParams, SortParams } from '@core/models/api-response.model';
import { ChevronLeft, ChevronRight, ChevronsLeft, ChevronsRight } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

/**
 * Composant table de données réutilisable
 * Avec pagination, tri et filtres
 */
@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  template: `
    <div class="bg-white rounded-lg shadow-md overflow-hidden">
      <!-- En-tête avec recherche -->
      <div *ngIf="searchable" class="p-4 border-b border-gray-200">
        <input
          type="text"
          [(ngModel)]="searchTerm"
          (ngModelChange)="onSearchChange()"
          placeholder="Rechercher..."
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
        />
      </div>

      <!-- Table -->
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th
                *ngFor="let col of columns"
                [class]="getHeaderClasses(col)"
                [style.width]="col.width"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                <div class="flex items-center space-x-2">
                  <span>{{ col.header }}</span>
                  <button
                    *ngIf="col.sortable"
                    (click)="onSort(col.field)"
                    class="text-gray-400 hover:text-gray-600"
                  >
                    ↕
                  </button>
                </div>
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <!-- Skeleton loader -->
            <tr *ngIf="loading">
              <td [attr.colspan]="columns.length" class="px-6 py-8">
                <div class="animate-pulse space-y-3">
                  <div *ngFor="let i of [1,2,3]" class="h-4 bg-gray-200 rounded"></div>
                </div>
              </td>
            </tr>
            
            <!-- Données -->
            <tr
              *ngFor="let row of filteredData(); trackBy: trackByFn"
              class="hover:bg-gray-50 transition-colors"
            >
              <td
                *ngFor="let col of columns"
                [class]="getCellClasses(col)"
                class="px-6 py-4 whitespace-nowrap text-sm"
              >
                <span [innerHTML]="getCellValue(row, col)"></span>
              </td>
            </tr>
            
            <!-- Message vide -->
            <tr *ngIf="!loading && filteredData().length === 0">
              <td [attr.colspan]="columns.length" class="px-6 py-8 text-center text-gray-500">
                Aucune donnée disponible
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
        <div class="text-sm text-gray-700">
          Affichage de {{ (currentPage() - 1) * pageSize() + 1 }} à
          {{ Math.min(currentPage() * pageSize(), totalElements) }} sur
          {{ totalElements }} résultats
        </div>
        
        <div class="flex items-center space-x-2">
          <button
            (click)="goToFirstPage()"
            [disabled]="currentPage() === 1"
            class="p-2 rounded-md border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <chevrons-left class="w-4 h-4" />
          </button>
          
          <button
            (click)="goToPreviousPage()"
            [disabled]="currentPage() === 1"
            class="p-2 rounded-md border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <chevron-left class="w-4 h-4" />
          </button>
          
          <span class="px-4 py-2 text-sm text-gray-700">
            Page {{ currentPage() }} / {{ totalPages() }}
          </span>
          
          <button
            (click)="goToNextPage()"
            [disabled]="currentPage() === totalPages()"
            class="p-2 rounded-md border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <chevron-right class="w-4 h-4" />
          </button>
          
          <button
            (click)="goToLastPage()"
            [disabled]="currentPage() === totalPages()"
            class="p-2 rounded-md border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <chevrons-right class="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class DataTableComponent<T = any> {
  @Input() columns: ColumnDef<T>[] = [];
  @Input() data: T[] = [];
  @Input() totalElements: number = 0;
  @Input() loading: boolean = false;
  @Input() searchable: boolean = true;
  @Input() pageSize: number = 20;

  @Output() pageChange = new EventEmitter<PaginationParams>();
  @Output() sortChange = new EventEmitter<SortParams>();
  @Output() searchChange = new EventEmitter<string>();

  // Signals pour l'état interne
  private readonly _currentPage = signal(1);
  private readonly _searchTerm = signal('');
  private readonly _sortField = signal<string | null>(null);
  private readonly _sortDirection = signal<'asc' | 'desc'>('asc');

  // Computed
  readonly currentPage = computed(() => this._currentPage());
  readonly searchTerm = computed(() => this._searchTerm());
  readonly totalPages = computed(() => Math.ceil(this.totalElements / this.pageSize));
  
  readonly filteredData = computed(() => {
    let result = [...this.data];
    
    // Filtrage par recherche
    const search = this._searchTerm().toLowerCase();
    if (search) {
      result = result.filter(row => {
        return this.columns.some(col => {
          const value = this.getCellValue(row, col);
          return value?.toLowerCase().includes(search);
        });
      });
    }
    
    return result;
  });

  readonly Math = Math;

  trackByFn(index: number, item: T): any {
    return (item as any).uuid || (item as any).id || index;
  }

  getCellValue(row: T, col: ColumnDef<T>): string {
    if (col.cellRenderer) {
      return col.cellRenderer(row);
    }
    
    const field = col.field as keyof T;
    const value = row[field];
    
    if (value === null || value === undefined) {
      return '-';
    }
    
    return String(value);
  }

  getHeaderClasses(col: ColumnDef<T>): string {
    return col.align === 'right' ? 'text-right' : col.align === 'center' ? 'text-center' : 'text-left';
  }

  getCellClasses(col: ColumnDef<T>): string {
    return col.align === 'right' ? 'text-right' : col.align === 'center' ? 'text-center' : 'text-left';
  }

  onSort(field: string | keyof T): void {
    const currentField = this._sortField();
    const currentDir = this._sortDirection();
    
    if (currentField === field) {
      // Inverser la direction
      this._sortDirection.set(currentDir === 'asc' ? 'desc' : 'asc');
    } else {
      this._sortField.set(String(field));
      this._sortDirection.set('asc');
    }
    
    this.sortChange.emit({
      field: String(field),
      direction: this._sortDirection()
    });
  }

  onSearchChange(): void {
    this.searchChange.emit(this._searchTerm());
  }

  goToFirstPage(): void {
    if (this._currentPage() > 1) {
      this._currentPage.set(1);
      this.emitPageChange();
    }
  }

  goToPreviousPage(): void {
    if (this._currentPage() > 1) {
      this._currentPage.set(this._currentPage() - 1);
      this.emitPageChange();
    }
  }

  goToNextPage(): void {
    if (this._currentPage() < this.totalPages()) {
      this._currentPage.set(this._currentPage() + 1);
      this.emitPageChange();
    }
  }

  goToLastPage(): void {
    const lastPage = this.totalPages();
    if (this._currentPage() < lastPage) {
      this._currentPage.set(lastPage);
      this.emitPageChange();
    }
  }

  private emitPageChange(): void {
    this.pageChange.emit({
      page: this._currentPage() - 1, // API utilise 0-based
      size: this.pageSize
    });
  }

  // Méthode publique pour réinitialiser la pagination
  resetPagination(): void {
    this._currentPage.set(1);
  }
}
