import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CongeService } from '../../services/conge.service';
import { EmployeService } from '../../services/employe.service';
import { CreateCongeRequest, UpdateCongeRequest, TypeConge } from '@core/models/conge.model';
import { Employe } from '@core/models/employe.model';
import { ToastrService } from 'ngx-toastr';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-conge-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageHeaderComponent],
  template: `
    <app-page-header
      [title]="isEditMode() ? 'Modifier le congé' : 'Nouvelle demande de congé'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Congés', route: '/rh/conges' },
        { label: isEditMode() ? 'Modifier' : 'Nouveau' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <form [formGroup]="congeForm" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Employé *</label>
            <select
              formControlName="employeId"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            >
              <option value="">Sélectionner un employé</option>
              <option *ngFor="let emp of employes()" [value]="emp.id">
                {{ emp.prenom }} {{ emp.nom }} ({{ emp.matricule }})
              </option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Type de congé *</label>
            <select
              formControlName="typeConge"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            >
              <option *ngFor="let type of typesConge" [value]="type">{{ type }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Date début *</label>
            <input
              type="date"
              formControlName="dateDebut"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Date fin *</label>
            <input
              type="date"
              formControlName="dateFin"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Nombre de jours</label>
            <input
              type="number"
              formControlName="nombreJours"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Motif</label>
            <textarea
              formControlName="motif"
              rows="3"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            ></textarea>
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-4">
          <button
            type="button"
            (click)="onCancel()"
            class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
          >
            Annuler
          </button>
          <button
            type="submit"
            [disabled]="congeForm.invalid"
            class="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 disabled:opacity-50"
          >
            {{ isEditMode() ? 'Modifier' : 'Créer' }}
          </button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class CongeFormComponent implements OnInit {
  private readonly _isEditMode = signal<boolean>(false);
  private readonly _employes = signal<Employe[]>([]);
  
  readonly isEditMode = this._isEditMode.asReadonly();
  readonly employes = this._employes.asReadonly();

  congeForm: FormGroup;

  readonly typesConge: TypeConge[] = [
    'ANNUEL',
    'MALADIE',
    'MATERNITE',
    'PATERNITE',
    'SANS_SOLDE',
    'RECUPERATION',
    'RTT',
    'EXCEPTIONNEL'
  ];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private congeService: CongeService,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {
    this.congeForm = this.fb.group({
      employeId: ['', [Validators.required]],
      typeConge: ['ANNUEL', [Validators.required]],
      dateDebut: ['', [Validators.required]],
      dateFin: ['', [Validators.required]],
      nombreJours: [null],
      motif: ['']
    });
  }

  ngOnInit(): void {
    this.loadEmployes();
    
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid && uuid !== 'new') {
      this._isEditMode.set(true);
      this.loadConge(uuid);
    }
  }

  private loadEmployes(): void {
    this.employeService.getAll({ page: 0, size: 1000 }).subscribe({
      next: (response) => {
        this._employes.set(response.content);
      }
    });
  }

  private loadConge(uuid: string): void {
    this.congeService.getByUuid(uuid).subscribe({
      next: (conge) => {
        this.congeForm.patchValue({
          employeId: conge.employeId,
          typeConge: conge.typeConge,
          dateDebut: conge.dateDebut?.split('T')[0],
          dateFin: conge.dateFin?.split('T')[0],
          nombreJours: conge.nombreJours,
          motif: conge.motif
        });
      }
    });
  }

  onSubmit(): void {
    if (this.congeForm.valid) {
      const formValue = this.congeForm.value;
      const dto: CreateCongeRequest = {
        ...formValue,
        dateDebut: new Date(formValue.dateDebut).toISOString(),
        dateFin: new Date(formValue.dateFin).toISOString()
      };

      if (this.isEditMode()) {
        const uuid = this.route.snapshot.paramMap.get('uuid');
        if (uuid) {
          this.congeService.update(uuid, dto).subscribe({
            next: () => {
              this.toastr.success('Congé modifié avec succès', 'Succès');
              this.router.navigate(['/rh/conges']);
            },
            error: () => this.toastr.error('Erreur lors de la modification', 'Erreur')
          });
        }
      } else {
        this.congeService.create(dto).subscribe({
          next: () => {
            this.toastr.success('Demande de congé créée avec succès', 'Succès');
            this.router.navigate(['/rh/conges']);
          },
          error: () => this.toastr.error('Erreur lors de la création', 'Erreur')
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/rh/conges']);
  }
}
