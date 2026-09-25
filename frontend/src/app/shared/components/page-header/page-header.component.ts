import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

export interface Breadcrumb { label: string; route?: string; }
export interface HeaderAction {
  label: string;
  action?: () => void;
  variant?: 'primary' | 'secondary';
  icon?: string;
}

@Component({
  selector: 'app-page-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <header class="mb-6">
      <nav *ngIf="breadcrumbs?.length" class="text-caption text-on-surface-variant mb-2 flex items-center gap-1">
        <ng-container *ngFor="let b of breadcrumbs; let last = last">
          <a *ngIf="b.route && !last" [routerLink]="b.route" class="hover:text-primary">{{ b.label }}</a>
          <span *ngIf="!b.route || last" [class.font-semibold]="last" [class.text-on-surface]="last">{{ b.label }}</span>
          <span *ngIf="!last" class="mx-1">/</span>
        </ng-container>
      </nav>
      <div class="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 class="text-headline-lg">{{ title }}</h1>
          <p *ngIf="subtitle" class="text-body-sm text-on-surface-variant mt-1">{{ subtitle }}</p>
        </div>
        <div *ngIf="actions?.length" class="flex items-center gap-2">
          <button *ngFor="let a of actions"
                  [class]="a.variant === 'primary' ? 'btn-primary' : 'btn-secondary'"
                  (click)="a.action && a.action()">
            <span *ngIf="a.icon" class="material-symbols-outlined text-base">{{ a.icon }}</span>
            {{ a.label }}
          </button>
        </div>
      </div>
    </header>
  `,
})
export class PageHeaderComponent {
  @Input() title = '';
  @Input() subtitle?: string;
  @Input() breadcrumbs?: Breadcrumb[];
  @Input() actions?: HeaderAction[];
}
