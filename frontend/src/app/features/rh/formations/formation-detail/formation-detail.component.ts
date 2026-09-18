import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FormationService } from '../../services/formation.service';
import { Formation } from '@core/models/formation.model';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-formation-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      [title]="'Détails de la formation'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Formations', route: '/rh/formations' },
        { label: formation()?.titre || '...' }
      ]"
      [actions]="[{ label: 'Éditer', action: () => navigateToEdit(), variant: 'primary' }]"
    />

    <div *ngIf="loading()" class="text-center py-8">Chargement...</div>

    <div *ngIf="!loading() && formation()" class="bg-white rounded-lg shadow-md p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div>
          <dt class="text-sm font-medium text-gray-500">Titre</dt>
          <dd class="text-sm text-gray-900">{{ formation()!.titre }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Organisme</dt>
          <dd class="text-sm text-gray-900">{{ formation()!.organisme || '-' }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Date début</dt>
          <dd class="text-sm text-gray-900">{{ formation()!.dateDebut | date:'dd/MM/yyyy' }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Date fin</dt>
          <dd class="text-sm text-gray-900">{{ formation()!.dateFin | date:'dd/MM/yyyy' }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Coût</dt>
          <dd class="text-sm text-gray-900">{{ formation()!.cout ? formation()!.cout + ' ' + (formation()!.devise || 'XOF') : '-' }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Statut</dt>
          <dd class="text-sm"><app-status-badge [status]="formation()!.statutFormation || ''"></app-status-badge></dd>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class FormationDetailComponent implements OnInit {
  private readonly _formation = signal<Formation | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly formation = computed(() => this._formation());
  readonly loading = computed(() => this._loading());

  constructor(
    private route: ActivatedRoute,
    private formationService: FormationService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadFormation(uuid);
    }
  }

  private loadFormation(uuid: string): void {
    this._loading.set(true);
    this.formationService.getByUuid(uuid).subscribe({
      next: (formation) => {
        this._formation.set(formation);
        this._loading.set(false);
      },
      error: () => this._loading.set(false)
    });
  }

  navigateToEdit(): void {
    // TODO: Navigation
  }
}
