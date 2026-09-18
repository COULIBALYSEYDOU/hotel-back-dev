import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KpiCardComponent } from '@shared/components/kpi-card/kpi-card.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

/**
 * Dashboard RH
 */
@Component({
  selector: 'app-rh-dashboard',
  standalone: true,
  imports: [CommonModule, KpiCardComponent, PageHeaderComponent],
  template: `
    <app-page-header
      title="Dashboard RH"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH' }
      ]"
    />

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <app-kpi-card
        title="Effectif total"
        [value]="642"
        subtitle="3 établissements"
        clickRoute="/rh/employes"
      />
      
      <app-kpi-card
        title="Employés actifs"
        [value]="598"
        subtitle="93%"
      />
      
      <app-kpi-card
        title="En période d'essai"
        [value]="12"
        subtitle="2%"
      />
      
      <app-kpi-card
        title="Masse salariale"
        [value]="'45.2M XOF'"
        subtitle="Mensuel"
      />
    </div>
  `,
  styles: []
})
export class RhDashboardComponent {}
