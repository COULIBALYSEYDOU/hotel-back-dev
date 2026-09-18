import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';

export interface Breadcrumb {
  label: string;
  route?: string;
}

export interface HeaderAction {
  label: string;
  icon?: string;
  action: () => void;
  variant?: 'primary' | 'secondary' | 'danger';
}

/**
 * Composant en-tête de page réutilisable
 * Avec titre, breadcrumb et actions
 */
@Component({
  selector: 'app-page-header',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div class="mb-6">
      <!-- Breadcrumb -->
      <nav *ngIf="breadcrumbs && breadcrumbs.length > 0" class="mb-4">
        <ol class="flex items-center space-x-2 text-sm text-gray-500">
          <li *ngFor="let crumb of breadcrumbs; let last = last" class="flex items-center">
            <a
              *ngIf="crumb.route && !last"
              [routerLink]="crumb.route"
              class="hover:text-primary-600"
            >
              {{ crumb.label }}
            </a>
            <span *ngIf="!crumb.route || last" [class.text-gray-900]="last">
              {{ crumb.label }}
            </span>
            <span *ngIf="!last" class="mx-2">/</span>
          </li>
        </ol>
      </nav>

      <!-- Titre et actions -->
      <div class="flex items-center justify-between">
        <h1 class="text-3xl font-bold text-gray-900">{{ title }}</h1>
        
        <div *ngIf="actions && actions.length > 0" class="flex items-center space-x-2">
          <button
            *ngFor="let action of actions"
            (click)="action.action()"
            [class]="getActionClasses(action.variant)"
            class="px-4 py-2 rounded-lg font-medium transition-colors"
          >
            {{ action.label }}
          </button>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class PageHeaderComponent {
  @Input() title: string = '';
  @Input() breadcrumbs: Breadcrumb[] = [];
  @Input() actions: HeaderAction[] = [];

  getActionClasses(variant?: string): string {
    switch (variant) {
      case 'primary':
        return 'bg-primary-600 text-white hover:bg-primary-700';
      case 'danger':
        return 'bg-red-600 text-white hover:bg-red-700';
      case 'secondary':
      default:
        return 'bg-gray-200 text-gray-700 hover:bg-gray-300';
    }
  }
}
