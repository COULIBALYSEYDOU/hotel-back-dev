import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { KpiCardComponent } from '@shared/components/kpi-card/kpi-card.component';
import { Users, Calendar, GraduationCap, FileText, Award, UserCheck, UserX, ShieldCheck } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

/**
 * Page d'accueil du module RH
 */
@Component({
  selector: 'app-rh-index',
  standalone: true,
  imports: [CommonModule, RouterModule, PageHeaderComponent, KpiCardComponent, LucideAngularModule],
  template: `
    <app-page-header
      title="Ressources Humaines"
      [breadcrumbs]="[{ label: 'Accueil', route: '/dashboard' }, { label: 'RH' }]"
    />

    <!-- KPIs rapides -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
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
        title="Congés en attente"
        [value]="23"
        subtitle="À valider"
        clickRoute="/rh/conges"
      />
      
      <app-kpi-card
        title="Formations en cours"
        [value]="15"
        subtitle="Ce mois"
        clickRoute="/rh/formations"
      />
    </div>

    <!-- Accès rapide aux sections -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <a
        routerLink="/rh/employes"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-primary-100 rounded-lg">
            <users class="w-6 h-6 text-primary-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Employés</h3>
            <p class="text-sm text-gray-500">Gestion du personnel</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/conges"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-blue-100 rounded-lg">
            <calendar class="w-6 h-6 text-blue-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Congés</h3>
            <p class="text-sm text-gray-500">Demandes et approbations</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/formations"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-green-100 rounded-lg">
            <graduation-cap class="w-6 h-6 text-green-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Formations</h3>
            <p class="text-sm text-gray-500">Formations et compétences</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/fiches-paie"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-yellow-100 rounded-lg">
            <file-text class="w-6 h-6 text-yellow-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Fiches de paie</h3>
            <p class="text-sm text-gray-500">Bulletins de salaire</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/evaluations"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-purple-100 rounded-lg">
            <award class="w-6 h-6 text-purple-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Évaluations</h3>
            <p class="text-sm text-gray-500">Performance et évaluations</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/onboarding"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-indigo-100 rounded-lg">
            <user-check class="w-6 h-6 text-indigo-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Onboarding</h3>
            <p class="text-sm text-gray-500">Intégration nouveaux employés</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/offboarding"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-red-100 rounded-lg">
            <user-x class="w-6 h-6 text-red-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Offboarding</h3>
            <p class="text-sm text-gray-500">Sortie des employés</p>
          </div>
        </div>
      </a>

      <a
        routerLink="/rh/dashboard"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow border border-gray-200"
      >
        <div class="flex items-center space-x-4">
          <div class="p-3 bg-gray-100 rounded-lg">
            <shield-check class="w-6 h-6 text-gray-600" />
          </div>
          <div>
            <h3 class="text-lg font-semibold text-gray-900">Dashboard</h3>
            <p class="text-sm text-gray-500">Vue d'ensemble RH</p>
          </div>
        </div>
      </a>
    </div>
  `,
  styles: []
})
export class RhIndexComponent {}
