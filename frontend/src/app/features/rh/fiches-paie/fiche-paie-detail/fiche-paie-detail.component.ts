import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FichePaieService } from '../../services/fiche-paie.service';
import { FichePaie } from '@core/models/fiche-paie.model';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-fiche-paie-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      [title]="'Détails de la fiche de paie'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Fiches de paie', route: '/rh/fiches-paie' },
        { label: getPeriodLabel() }
      ]"
      [actions]="[{ label: 'Éditer', action: () => navigateToEdit(), variant: 'primary' }]"
    />

    <div *ngIf="loading()" class="text-center py-8">Chargement...</div>

    <div *ngIf="!loading() && fichePaie()" class="bg-white rounded-lg shadow-md p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div>
          <dt class="text-sm font-medium text-gray-500">Période</dt>
          <dd class="text-sm text-gray-900">{{ getPeriodLabel() }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Salaire brut</dt>
          <dd class="text-sm text-gray-900">{{ fichePaie()!.salaireBrut?.toLocaleString('fr-FR') }} XOF</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Cotisations patronales</dt>
          <dd class="text-sm text-gray-900">{{ fichePaie()!.cotisationPatronale?.toLocaleString('fr-FR') }} XOF</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Cotisations salariales</dt>
          <dd class="text-sm text-gray-900">{{ fichePaie()!.cotisationSalariale?.toLocaleString('fr-FR') }} XOF</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Impôt</dt>
          <dd class="text-sm text-gray-900">{{ fichePaie()!.impot?.toLocaleString('fr-FR') }} XOF</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Net à payer</dt>
          <dd class="text-sm text-gray-900 font-semibold">{{ fichePaie()!.netAPayer?.toLocaleString('fr-FR') }} XOF</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Statut</dt>
          <dd class="text-sm"><app-status-badge [status]="fichePaie()!.statutPaie || ''"></app-status-badge></dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-gray-500">Date paiement</dt>
          <dd class="text-sm text-gray-900">{{ fichePaie()!.datePaiement | date:'dd/MM/yyyy' }}</dd>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class FichePaieDetailComponent implements OnInit {
  private readonly _fichePaie = signal<FichePaie | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly fichePaie = computed(() => this._fichePaie());
  readonly loading = computed(() => this._loading());

  readonly mois = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];

  constructor(
    private route: ActivatedRoute,
    private fichePaieService: FichePaieService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadFichePaie(uuid);
    }
  }

  private loadFichePaie(uuid: string): void {
    this._loading.set(true);
    this.fichePaieService.getByUuid(uuid).subscribe({
      next: (fiche) => {
        this._fichePaie.set(fiche);
        this._loading.set(false);
      },
      error: () => this._loading.set(false)
    });
  }

  getPeriodLabel(): string {
    const fiche = this.fichePaie();
    if (fiche) {
      return `${this.mois[fiche.mois - 1]} ${fiche.annee}`;
    }
    return '...';
  }

  navigateToEdit(): void {
    // TODO: Navigation
  }
}
