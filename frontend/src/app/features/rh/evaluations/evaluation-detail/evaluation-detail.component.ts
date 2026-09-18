import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { EvaluationService } from '../../services/evaluation.service';
import { Evaluation } from '@core/models/evaluation.model';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-evaluation-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, StatusBadgeComponent, PageHeaderComponent, DatePipe],
  template: `
    <app-page-header
      [title]="'Détails de l\'évaluation'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Évaluations', route: '/rh/evaluations' },
        { label: evaluation()?.typeEvaluation || '...' }
      ]"
      [actions]="getActions()"
    />

    <div *ngIf="loading()" class="text-center py-8">Chargement...</div>

    <div *ngIf="!loading() && evaluation()" class="space-y-6">
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Informations principales</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <dt class="text-sm font-medium text-gray-500">Type</dt>
            <dd class="text-sm text-gray-900">{{ evaluation()!.typeEvaluation }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Date</dt>
            <dd class="text-sm text-gray-900">{{ evaluation()!.dateEvaluation | date:'dd/MM/yyyy' }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Score global</dt>
            <dd class="text-sm text-gray-900 font-semibold">{{ evaluation()!.scoreGlobal }}/100</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Statut</dt>
            <dd class="text-sm"><app-status-badge [status]="evaluation()!.statutEvaluation || ''"></app-status-badge></dd>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Scores détaillés</h3>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <dt class="text-sm font-medium text-gray-500">Compétences</dt>
            <dd class="text-sm text-gray-900">{{ evaluation()!.scoreCompetences }}/100</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Objectifs</dt>
            <dd class="text-sm text-gray-900">{{ evaluation()!.scoreObjectifs }}/100</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Comportement</dt>
            <dd class="text-sm text-gray-900">{{ evaluation()!.scoreComportement }}/100</dd>
          </div>
        </div>
      </div>

      <div *ngIf="evaluation()!.pointsForts" class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Points forts</h3>
        <p class="text-sm text-gray-700">{{ evaluation()!.pointsForts }}</p>
      </div>

      <div *ngIf="evaluation()!.pointsAmelioration" class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Points à améliorer</h3>
        <p class="text-sm text-gray-700">{{ evaluation()!.pointsAmelioration }}</p>
      </div>

      <div *ngIf="evaluation()!.recommendation" class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Recommandation</h3>
        <p class="text-sm text-gray-900 font-semibold">{{ evaluation()!.recommendation }}</p>
      </div>
    </div>
  `,
  styles: []
})
export class EvaluationDetailComponent implements OnInit {
  private readonly _evaluation = signal<Evaluation | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly evaluation = computed(() => this._evaluation());
  readonly loading = computed(() => this._loading());

  constructor(
    private route: ActivatedRoute,
    private evaluationService: EvaluationService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadEvaluation(uuid);
    }
  }

  private loadEvaluation(uuid: string): void {
    this._loading.set(true);
    this.evaluationService.getByUuid(uuid).subscribe({
      next: (eval) => {
        this._evaluation.set(eval);
        this._loading.set(false);
      },
      error: () => this._loading.set(false)
    });
  }

  getActions(): any[] {
    const actions = [];
    if (this.evaluation()?.statutEvaluation === 'EN_COURS') {
      actions.push({ label: 'Compléter', action: () => this.navigateToEdit(), variant: 'primary' });
    }
    if (this.evaluation()?.statutEvaluation === 'COMPLETEE') {
      actions.push({ label: 'Valider', action: () => this.validateEvaluation(), variant: 'primary' });
    }
    return actions;
  }

  navigateToEdit(): void {
    // TODO: Navigation
  }

  validateEvaluation(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.evaluationService.validate(uuid).subscribe({
        next: () => {
          this.toastr.success('Évaluation validée', 'Succès');
          this.loadEvaluation(uuid);
        }
      });
    }
  }
}
