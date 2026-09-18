import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { EmployeService } from '../../services/employe.service';
import { Employe } from '@core/models/employe.model';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

/**
 * Composant détail d'un employé
 */
@Component({
  selector: 'app-employe-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      [title]="'Détails employé'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Employés', route: '/rh/employes' },
        { label: employe()?.matricule || '...' }
      ]"
      [actions]="[
        { label: 'Éditer', action: () => navigateToEdit(), variant: 'primary' }
      ]"
    />

    <div *ngIf="loading()" class="text-center py-8">
      <p class="text-gray-500">Chargement...</p>
    </div>

    <div *ngIf="!loading() && employe()" class="bg-white rounded-lg shadow-md p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Informations personnelles -->
        <div>
          <h3 class="text-lg font-semibold mb-4">Informations personnelles</h3>
          <dl class="space-y-2">
            <div>
              <dt class="text-sm font-medium text-gray-500">Matricule</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.matricule }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Nom complet</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.prenom }} {{ employe()!.nom }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Email</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.email }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Téléphone</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.telephone }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Statut</dt>
              <dd class="text-sm">
                <app-status-badge [status]="employe()!.statut"></app-status-badge>
              </dd>
            </div>
          </dl>
        </div>

        <!-- Informations professionnelles -->
        <div>
          <h3 class="text-lg font-semibold mb-4">Informations professionnelles</h3>
          <dl class="space-y-2">
            <div>
              <dt class="text-sm font-medium text-gray-500">Poste</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.poste }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Département</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.departement }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Type de contrat</dt>
              <dd class="text-sm text-gray-900">{{ employe()!.typeContrat }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Date d'embauche</dt>
              <dd class="text-sm text-gray-900">
                {{ employe()!.dateEmbauche ? (employe()!.dateEmbauche | date:'dd/MM/yyyy') : '-' }}
              </dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Salaire de base</dt>
              <dd class="text-sm text-gray-900">
                {{ employe()!.salaireBase ? (employe()!.salaireBase | number:'1.0-0') + ' ' + (employe()!.devise || 'XOF') : '-' }}
              </dd>
            </div>
          </dl>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class EmployeDetailComponent implements OnInit {
  private readonly _employe = signal<Employe | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly employe = computed(() => this._employe());
  readonly loading = computed(() => this._loading());

  constructor(
    private route: ActivatedRoute,
    private employeService: EmployeService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadEmploye(uuid);
    }
  }

  private loadEmploye(uuid: string): void {
    this._loading.set(true);
    this.employeService.getByUuid(uuid).subscribe({
      next: (employe) => {
        this._employe.set(employe);
        this._loading.set(false);
      },
      error: () => {
        this._loading.set(false);
      }
    });
  }

  navigateToEdit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    // TODO: Navigation vers édition
  }
}
