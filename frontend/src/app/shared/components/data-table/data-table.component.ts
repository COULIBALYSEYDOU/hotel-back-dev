import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card !p-0 overflow-hidden">
      <div *ngIf="loading" class="p-8 text-center text-on-surface-variant">Chargement...</div>
      <div *ngIf="!loading && !data?.length" class="p-8 text-center text-on-surface-variant">
        Aucune donnée à afficher.
      </div>
      <div *ngIf="!loading && data?.length" class="p-4 text-caption text-on-surface-variant">
        {{ data?.length }} élément(s) — vue simplifiée
      </div>
    </div>
  `,
})
export class DataTableComponent {
  @Input() columns: any[] = [];
  @Input() data: any[] = [];
  @Input() totalElements = 0;
  @Input() loading = false;
  @Input() pageSize = 20;
  @Output() pageChange = new EventEmitter<any>();
  @Output() sortChange = new EventEmitter<any>();
}
