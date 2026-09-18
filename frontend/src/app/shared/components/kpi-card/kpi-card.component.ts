import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, TrendingUp, TrendingDown, ArrowRight } from 'lucide-angular';

/**
 * Composant carte KPI réutilisable
 * Affiche une métrique avec titre, valeur, sous-titre et tendance
 */
@Component({
  selector: 'app-kpi-card',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div
      [class]="clickRoute ? 'cursor-pointer hover:shadow-lg transition-shadow' : ''"
      [routerLink]="clickRoute || null"
      class="bg-white rounded-lg shadow-md p-6 border border-gray-200"
    >
      <div class="flex items-center justify-between mb-2">
        <h3 class="text-sm font-medium text-gray-600">{{ title }}</h3>
        <div *ngIf="trend" [class]="getTrendColorClasses()">
          <trending-up *ngIf="trend.direction === 'up'" class="w-4 h-4" />
          <trending-down *ngIf="trend.direction === 'down'" class="w-4 h-4" />
        </div>
      </div>
      
      <div class="flex items-baseline">
        <p class="text-3xl font-bold text-gray-900">{{ formatValue() }}</p>
        <span *ngIf="trend" [class]="getTrendTextClasses()" class="ml-2 text-sm font-medium">
          {{ trend.value > 0 ? '+' : '' }}{{ trend.value }}%
        </span>
      </div>
      
      <p *ngIf="subtitle" class="mt-2 text-sm text-gray-500">{{ subtitle }}</p>
      
      <div *ngIf="clickRoute" class="mt-4 flex items-center text-sm text-primary-600">
        <span>Voir détails</span>
        <arrow-right class="ml-1 w-4 h-4" />
      </div>
    </div>
  `,
  styles: []
})
export class KpiCardComponent {
  @Input() title: string = '';
  @Input() value: string | number = 0;
  @Input() subtitle: string = '';
  @Input() trend?: { value: number; direction: 'up' | 'down' };
  @Input() clickRoute?: string;

  formatValue(): string {
    if (typeof this.value === 'number') {
      // Formater les grands nombres avec séparateurs
      return this.value.toLocaleString('fr-FR');
    }
    return this.value;
  }

  getTrendColorClasses(): string {
    if (this.trend?.direction === 'up') {
      return 'text-green-500';
    }
    return 'text-red-500';
  }

  getTrendTextClasses(): string {
    if (this.trend?.direction === 'up') {
      return 'text-green-600';
    }
    return 'text-red-600';
  }
}
