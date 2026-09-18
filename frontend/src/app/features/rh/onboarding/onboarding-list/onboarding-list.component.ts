import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EmployeService } from '../../services/employe.service';
import { OnboardingService } from '../../services/onboarding.service';
import { Employe } from '@core/models/employe.model';
import { Onboarding, ChecklistItem } from '@core/models/onboarding.model';
import { DataTableComponent } from '@shared/components/data-table/data-table.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ColumnDef } from '@shared/models/column-def.model';
import { ToastrService } from 'ngx-toastr';
import { CheckCircle, Circle, XCircle } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-onboarding-list',
  standalone: true,
  imports: [CommonModule, RouterModule, DataTableComponent, StatusBadgeComponent, PageHeaderComponent, LucideAngularModule, DatePipe],
  template: `
    <app-page-header
      title="Onboarding"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Onboarding' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <div class="space-y-4">
        <div *ngFor="let onboarding of onboardings()" class="border border-gray-200 rounded-lg p-4">
          <div class="flex items-center justify-between mb-4">
            <div>
              <h3 class="font-semibold">{{ getEmployeName(onboarding.employeUuid) }}</h3>
              <p class="text-sm text-gray-500">Démarré le {{ onboarding.dateDebut | date:'dd/MM/yyyy' }}</p>
            </div>
            <app-status-badge [status]="onboarding.statut"></app-status-badge>
          </div>

          <div class="space-y-2">
            <div *ngFor="let item of onboarding.checklist || []" class="flex items-center space-x-2">
              <check-circle *ngIf="item.statut === 'COMPLETE'" class="w-5 h-5 text-green-500" />
              <circle *ngIf="item.statut === 'PENDING'" class="w-5 h-5 text-gray-400" />
              <x-circle *ngIf="item.statut === 'BLOCKED'" class="w-5 h-5 text-red-500" />
              <span [class.text-gray-500]="item.statut === 'PENDING'">{{ item.libelle }}</span>
            </div>
          </div>

          <div class="mt-4 flex justify-end space-x-2">
            <button
              (click)="viewDetail(onboarding.employeUuid)"
              class="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
            >
              Voir détails
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class OnboardingListComponent implements OnInit {
  private readonly _onboardings = signal<Onboarding[]>([]);
  private readonly _employes = signal<Map<string, Employe>>(new Map());

  readonly onboardings = computed(() => this._onboardings());

  constructor(
    private employeService: EmployeService,
    private onboardingService: OnboardingService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadOnboardings();
  }

  private loadOnboardings(): void {
    // TODO: Implémenter l'appel API réel
    // Pour l'instant, simulation
    this._onboardings.set([]);
  }

  getEmployeName(uuid: string): string {
    const emp = this._employes().get(uuid);
    return emp ? `${emp.prenom} ${emp.nom}` : '...';
  }

  viewDetail(uuid: string): void {
    // TODO: Navigation vers détail
  }
}
