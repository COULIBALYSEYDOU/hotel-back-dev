import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ConformiteService, ConformiteResult, ActionPlan } from '../../services/conformite.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { ToastrService } from 'ngx-toastr';
import { CheckCircle, XCircle, AlertCircle } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-conformite-verification',
  standalone: true,
  imports: [CommonModule, RouterModule, PageHeaderComponent, StatusBadgeComponent, LucideAngularModule, DatePipe],
  template: `
    <app-page-header
      [title]="'Vérification de conformité'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Conformité' }
      ]"
      [actions]="[
        { label: 'Plan d\'action', action: () => loadActionPlan(), variant: 'primary' }
      ]"
    />

    <div *ngIf="loading()" class="text-center py-8">Chargement...</div>

    <div *ngIf="!loading() && result()" class="space-y-6">
      <!-- Résultat global -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold">Résultat de la vérification</h3>
          <div class="flex items-center space-x-2">
            <check-circle *ngIf="result()!.conforme" class="w-6 h-6 text-green-500" />
            <x-circle *ngIf="!result()!.conforme" class="w-6 h-6 text-red-500" />
            <span class="font-semibold" [class.text-green-600]="result()!.conforme" [class.text-red-600]="!result()!.conforme">
              {{ result()!.conforme ? 'Conforme' : 'Non conforme' }}
            </span>
          </div>
        </div>
        <div class="w-full bg-gray-200 rounded-full h-4">
          <div
            class="bg-primary-600 h-4 rounded-full transition-all"
            [style.width.%]="result()!.score"
          ></div>
        </div>
        <p class="text-sm text-gray-600 mt-2">Score : {{ result()!.score }}%</p>
      </div>

      <!-- Vérifications -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Vérifications</h3>
        <div class="space-y-2">
          <div
            *ngFor="let verif of result()!.verifications"
            class="flex items-center space-x-2 p-2 border border-gray-200 rounded"
          >
            <check-circle *ngIf="verif.conforme" class="w-5 h-5 text-green-500" />
            <x-circle *ngIf="!verif.conforme" class="w-5 h-5 text-red-500" />
            <span>{{ verif.libelle }}</span>
          </div>
        </div>
      </div>

      <!-- Non-conformités -->
      <div *ngIf="result()!.nonConformites && result()!.nonConformites.length > 0" class="bg-red-50 rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4 text-red-800">Non-conformités</h3>
        <div class="space-y-2">
          <div
            *ngFor="let nc of result()!.nonConformites"
            class="flex items-start space-x-2 p-2 bg-white rounded"
          >
            <alert-circle class="w-5 h-5 text-red-500 mt-0.5" />
            <div>
              <p class="font-medium">{{ nc.libelle }}</p>
              <p class="text-sm text-gray-600">{{ nc.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class ConformiteVerificationComponent implements OnInit {
  private readonly _result = signal<ConformiteResult | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly result = computed(() => this._result());
  readonly loading = computed(() => this._loading());

  constructor(
    private route: ActivatedRoute,
    private conformiteService: ConformiteService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadVerification(uuid);
    }
  }

  private loadVerification(uuid: string): void {
    this._loading.set(true);
    this.conformiteService.verify(uuid).subscribe({
      next: (result) => {
        this._result.set(result);
        this._loading.set(false);
      },
      error: () => {
        this.toastr.error('Erreur lors de la vérification', 'Erreur');
        this._loading.set(false);
      }
    });
  }

  loadActionPlan(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      // TODO: Navigation vers plan d'action
    }
  }
}
