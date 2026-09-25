import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-kpi-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="card cursor-pointer hover:shadow-md transition-shadow" [routerLink]="clickRoute">
      <div class="text-caption text-on-surface-variant uppercase tracking-wider">{{ title }}</div>
      <div class="text-headline-lg font-bold text-on-surface mt-1">{{ value }}</div>
      <div *ngIf="subtitle" class="text-caption text-on-surface-variant mt-1">{{ subtitle }}</div>
      <div *ngIf="trend" class="mt-2 chip"
           [ngClass]="trend.direction === 'up' ? 'chip-primary' : 'chip-error'">
        <span class="material-symbols-outlined text-sm">
          {{ trend.direction === 'up' ? 'trending_up' : 'trending_down' }}
        </span>
        {{ trend.value }}%
      </div>
    </div>
  `,
})
export class KpiCardComponent {
  @Input() title = '';
  @Input() value: string | number = '';
  @Input() subtitle?: string;
  @Input() trend?: { value: number; direction: 'up' | 'down' };
  @Input() clickRoute?: string;
}
