import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { OnboardingService } from '../../services/onboarding.service';
import { EmployeService } from '../../services/employe.service';
import { OnboardingDetail, ChecklistItem } from '@core/models/onboarding.model';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { ToastrService } from 'ngx-toastr';
import { CheckCircle, Circle, XCircle } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-onboarding-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, PageHeaderComponent, StatusBadgeComponent, LucideAngularModule, DatePipe],
  template: `
    <app-page-header
      [title]="'Onboarding - ' + (detail()?.employe?.prenom || '') + ' ' + (detail()?.employe?.nom || '')"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Onboarding', route: '/rh/onboarding' },
        { label: 'Détails' }
      ]"
    />

    <div *ngIf="loading()" class="text-center py-8">Chargement...</div>

    <div *ngIf="!loading() && detail()" class="space-y-6">
      <!-- Informations employé -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Informations employé</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <dt class="text-sm font-medium text-gray-500">Nom</dt>
            <dd class="text-sm text-gray-900">{{ detail()!.employe.prenom }} {{ detail()!.employe.nom }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Matricule</dt>
            <dd class="text-sm text-gray-900">{{ detail()!.employe.matricule }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Poste</dt>
            <dd class="text-sm text-gray-900">{{ detail()!.employe.poste }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Statut onboarding</dt>
            <dd class="text-sm">
              <app-status-badge [status]="detail()!.onboarding.statut"></app-status-badge>
            </dd>
          </div>
        </div>
      </div>

      <!-- Progression -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Progression</h3>
        <div class="w-full bg-gray-200 rounded-full h-4 mb-2">
          <div
            class="bg-primary-600 h-4 rounded-full transition-all"
            [style.width.%]="detail()!.progression"
          ></div>
        </div>
        <p class="text-sm text-gray-600">{{ detail()!.progression }}% complété</p>
      </div>

      <!-- Checklist -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Checklist</h3>
        <div class="space-y-3">
          <div
            *ngFor="let item of detail()!.checklist"
            class="flex items-center justify-between p-3 border border-gray-200 rounded-lg"
            [class.bg-green-50]="item.statut === 'COMPLETE'"
            [class.bg-red-50]="item.statut === 'BLOCKED'"
          >
            <div class="flex items-center space-x-3">
              <check-circle *ngIf="item.statut === 'COMPLETE'" class="w-5 h-5 text-green-500" />
              <circle *ngIf="item.statut === 'PENDING'" class="w-5 h-5 text-gray-400" />
              <x-circle *ngIf="item.statut === 'BLOCKED'" class="w-5 h-5 text-red-500" />
              <span [class.line-through]="item.statut === 'COMPLETE'">{{ item.libelle }}</span>
            </div>
            <div class="flex items-center space-x-4">
              <span *ngIf="item.dateValidation" class="text-xs text-gray-500">
                Validé le {{ item.dateValidation | date:'dd/MM/yyyy' }}
              </span>
              <button
                *ngIf="item.statut === 'PENDING'"
                (click)="validateStep(item.etape)"
                class="px-3 py-1 bg-primary-600 text-white text-sm rounded-lg hover:bg-primary-700"
              >
                Valider
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Actions -->
      <div *ngIf="canFinalize()" class="bg-white rounded-lg shadow-md p-6">
        <button
          (click)="finalizeOnboarding()"
          class="w-full px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
        >
          Finaliser l'onboarding
        </button>
      </div>
    </div>
  `,
  styles: []
})
export class OnboardingDetailComponent implements OnInit {
  private readonly _detail = signal<OnboardingDetail | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly detail = computed(() => this._detail());
  readonly loading = computed(() => this._loading());

  constructor(
    private route: ActivatedRoute,
    private onboardingService: OnboardingService,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadOnboarding(uuid);
    }
  }

  private loadOnboarding(uuid: string): void {
    this._loading.set(true);
    // TODO: Implémenter l'appel API réel
    // Pour l'instant, simulation
    this.employeService.getByUuid(uuid).subscribe({
      next: (employe) => {
        // TODO: Charger les données d'onboarding réelles
        this._loading.set(false);
      }
    });
  }

  validateStep(etape: string): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.onboardingService.validateStep(uuid, etape).subscribe({
        next: () => {
          this.toastr.success('Étape validée', 'Succès');
          this.loadOnboarding(uuid);
        }
      });
    }
  }

  canFinalize(): boolean {
    const checklist = this.detail()?.checklist || [];
    return checklist.every(item => item.statut === 'COMPLETE');
  }

  finalizeOnboarding(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.onboardingService.finalize(uuid).subscribe({
        next: () => {
          this.toastr.success('Onboarding finalisé', 'Succès');
          this.loadOnboarding(uuid);
        }
      });
    }
  }
}
