import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CongeService } from '../../services/conge.service';
import { Conge } from '@core/models/conge.model';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ConfirmDialogComponent } from '@shared/components/confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-conge-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, StatusBadgeComponent, PageHeaderComponent, ConfirmDialogComponent, DatePipe],
  template: `
    <app-page-header
      [title]="'Détails du congé'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Congés', route: '/rh/conges' },
        { label: conge()?.employeMatricule || '...' }
      ]"
      [actions]="getActions()"
    />

    <div *ngIf="loading()" class="text-center py-8">
      <p class="text-gray-500">Chargement...</p>
    </div>

    <div *ngIf="!loading() && conge()" class="space-y-6">
      <!-- Informations principales -->
      <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Informations principales</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <dt class="text-sm font-medium text-gray-500">Employé</dt>
            <dd class="text-sm text-gray-900">{{ conge()!.employeNom }} ({{ conge()!.employeMatricule }})</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Type</dt>
            <dd class="text-sm text-gray-900">{{ conge()!.typeConge }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Date début</dt>
            <dd class="text-sm text-gray-900">{{ conge()!.dateDebut | date:'dd/MM/yyyy' }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Date fin</dt>
            <dd class="text-sm text-gray-900">{{ conge()!.dateFin | date:'dd/MM/yyyy' }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Nombre de jours</dt>
            <dd class="text-sm text-gray-900">{{ conge()!.nombreJours || '-' }}</dd>
          </div>
          <div>
            <dt class="text-sm font-medium text-gray-500">Statut</dt>
            <dd class="text-sm">
              <app-status-badge [status]="conge()!.statutConge"></app-status-badge>
            </dd>
          </div>
        </div>
      </div>

      <!-- Motif -->
      <div *ngIf="conge()!.motif" class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Motif</h3>
        <p class="text-sm text-gray-700">{{ conge()!.motif }}</p>
      </div>

      <!-- Actions workflow -->
      <div *ngIf="canApprove() || canReject()" class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold mb-4">Actions</h3>
        <div class="flex space-x-4">
          <button
            *ngIf="canApprove()"
            (click)="approveConge()"
            class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
          >
            Approuver
          </button>
          <button
            *ngIf="canReject()"
            (click)="rejectConge()"
            class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
          >
            Rejeter
          </button>
        </div>
      </div>
    </div>

    <app-confirm-dialog
      [(visible)]="showApproveDialog"
      [message]="'Approuver cette demande de congé ?'"
      [confirmLabel]="'Approuver'"
      (confirmed)="onConfirmApprove()"
    />

    <app-confirm-dialog
      [(visible)]="showRejectDialog"
      [message]="'Rejeter cette demande de congé ?'"
      [confirmLabel]="'Rejeter'"
      (confirmed)="onConfirmReject()"
    />
  `,
  styles: []
})
export class CongeDetailComponent implements OnInit {
  private readonly _conge = signal<Conge | null>(null);
  private readonly _loading = signal<boolean>(true);

  readonly conge = computed(() => this._conge());
  readonly loading = computed(() => this._loading());

  showApproveDialog = false;
  showRejectDialog = false;

  constructor(
    private route: ActivatedRoute,
    private router: RouterModule,
    private congeService: CongeService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.loadConge(uuid);
    }
  }

  private loadConge(uuid: string): void {
    this._loading.set(true);
    this.congeService.getByUuid(uuid).subscribe({
      next: (conge) => {
        this._conge.set(conge);
        this._loading.set(false);
      },
      error: () => {
        this._loading.set(false);
      }
    });
  }

  canApprove(): boolean {
    const statut = this.conge()?.statutConge;
    return statut === 'EN_ATTENTE' || statut === 'EN_VALIDATION';
  }

  canReject(): boolean {
    const statut = this.conge()?.statutConge;
    return statut === 'EN_ATTENTE' || statut === 'EN_VALIDATION';
  }

  getActions(): any[] {
    return [
      { label: 'Éditer', action: () => this.navigateToEdit(), variant: 'primary' }
    ];
  }

  navigateToEdit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    // TODO: Navigation
  }

  approveConge(): void {
    this.showApproveDialog = true;
  }

  rejectConge(): void {
    this.showRejectDialog = true;
  }

  onConfirmApprove(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.congeService.approve(uuid, 1).subscribe({
        next: () => {
          this.toastr.success('Congé approuvé', 'Succès');
          this.loadConge(uuid);
        },
        error: () => this.toastr.error('Erreur', 'Erreur')
      });
    }
    this.showApproveDialog = false;
  }

  onConfirmReject(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid) {
      this.congeService.reject(uuid, 'Rejeté').subscribe({
        next: () => {
          this.toastr.success('Congé rejeté', 'Succès');
          this.loadConge(uuid);
        },
        error: () => this.toastr.error('Erreur', 'Erreur')
      });
    }
    this.showRejectDialog = false;
  }
}
