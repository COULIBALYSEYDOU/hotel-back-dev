import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { KpiCardComponent } from '@shared/components/kpi-card/kpi-card.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

/**
 * Dashboard principal de l'application
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, KpiCardComponent, PageHeaderComponent],
  template: `
    <app-page-header
      title="Tableau de bord"
      [breadcrumbs]="[{ label: 'Accueil' }]"
    />

    <!-- KPIs -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <app-kpi-card
        title="Effectif total"
        [value]="642"
        subtitle="3 établissements"
        [trend]="{ value: 5.2, direction: 'up' }"
        clickRoute="/rh/employes"
      />
      
      <app-kpi-card
        title="Taux d'occupation"
        [value]="'78%'"
        subtitle="250 chambres"
        [trend]="{ value: 3.1, direction: 'up' }"
        clickRoute="/reservations"
      />
      
      <app-kpi-card
        title="Chiffre d'affaires"
        [value]="'12.5M XOF'"
        subtitle="Ce mois"
        [trend]="{ value: 8.5, direction: 'up' }"
        clickRoute="/finance"
      />
      
      <app-kpi-card
        title="Réservations"
        [value]="'1,234'"
        subtitle="Ce mois"
        [trend]="{ value: -2.3, direction: 'down' }"
        clickRoute="/reservations"
      />
    </div>

    <!-- Widgets -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Graphique occupation 7j -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Occupation 7 derniers jours</h3>
        <div class="h-64 flex items-center justify-center text-gray-400">
          Graphique à implémenter
        </div>
      </div>

      <!-- Revenus par source -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Revenus par source</h3>
        <div class="h-64 flex items-center justify-center text-gray-400">
          Graphique à implémenter
        </div>
      </div>

      <!-- SLA Support -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">SLA Support</h3>
        <div class="h-64 flex items-center justify-center text-gray-400">
          Graphique à implémenter
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class DashboardComponent implements OnInit {
  ngOnInit(): void {
    // TODO: Charger les données réelles depuis les services
  }
}
