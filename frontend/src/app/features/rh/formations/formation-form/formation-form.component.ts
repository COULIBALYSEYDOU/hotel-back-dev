import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FormationService } from '../../services/formation.service';
import { EmployeService } from '../../services/employe.service';
import { CreateFormationRequest, UpdateFormationRequest, StatutFormation } from '@core/models/formation.model';
import { Employe } from '@core/models/employe.model';
import { ToastrService } from 'ngx-toastr';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-formation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageHeaderComponent],
  template: `
    <app-page-header
      [title]="isEditMode() ? 'Modifier la formation' : 'Nouvelle formation'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Formations', route: '/rh/formations' },
        { label: isEditMode() ? 'Modifier' : 'Nouveau' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <form [formGroup]="formationForm" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Employé *</label>
            <select formControlName="employeId" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option value="">Sélectionner</option>
              <option *ngFor="let emp of employes()" [value]="emp.id">
                {{ emp.prenom }} {{ emp.nom }}
              </option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Titre *</label>
            <input type="text" formControlName="titre" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Organisme</label>
            <input type="text" formControlName="organisme" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Statut</label>
            <select formControlName="statutFormation" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option *ngFor="let statut of statuts" [value]="statut">{{ statut }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Date début</label>
            <input type="date" formControlName="dateDebut" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Date fin</label>
            <input type="date" formControlName="dateFin" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Coût</label>
            <input type="number" formControlName="cout" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Devise</label>
            <select formControlName="devise" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option value="XOF">XOF</option>
              <option value="EUR">EUR</option>
              <option value="USD">USD</option>
            </select>
          </div>

          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Commentaire</label>
            <textarea formControlName="commentaire" rows="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg"></textarea>
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-4">
          <button type="button" (click)="onCancel()" class="px-4 py-2 border border-gray-300 rounded-lg">Annuler</button>
          <button type="submit" [disabled]="formationForm.invalid" class="px-4 py-2 bg-primary-600 text-white rounded-lg">Enregistrer</button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class FormationFormComponent implements OnInit {
  private readonly _isEditMode = signal<boolean>(false);
  private readonly _employes = signal<Employe[]>([]);
  
  readonly isEditMode = this._isEditMode.asReadonly();
  readonly employes = this._employes.asReadonly();

  formationForm: FormGroup;

  readonly statuts: StatutFormation[] = ['PLANIFIEE', 'EN_COURS', 'TERMINEE', 'ANNULEE'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private formationService: FormationService,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {
    this.formationForm = this.fb.group({
      employeId: ['', [Validators.required]],
      titre: ['', [Validators.required]],
      organisme: [''],
      dateDebut: [''],
      dateFin: [''],
      cout: [null],
      devise: ['XOF'],
      statutFormation: ['PLANIFIEE'],
      commentaire: ['']
    });
  }

  ngOnInit(): void {
    this.loadEmployes();
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid && uuid !== 'new') {
      this._isEditMode.set(true);
      this.loadFormation(uuid);
    }
  }

  private loadEmployes(): void {
    this.employeService.getAll({ page: 0, size: 1000 }).subscribe({
      next: (response) => this._employes.set(response.content)
    });
  }

  private loadFormation(uuid: string): void {
    this.formationService.getByUuid(uuid).subscribe({
      next: (formation) => {
        this.formationForm.patchValue({
          ...formation,
          dateDebut: formation.dateDebut?.split('T')[0],
          dateFin: formation.dateFin?.split('T')[0]
        });
      }
    });
  }

  onSubmit(): void {
    if (this.formationForm.valid) {
      const dto = this.formationForm.value;
      if (this.isEditMode()) {
        const uuid = this.route.snapshot.paramMap.get('uuid');
        if (uuid) {
          this.formationService.update(uuid, dto).subscribe({
            next: () => {
              this.toastr.success('Formation modifiée', 'Succès');
              this.router.navigate(['/rh/formations']);
            }
          });
        }
      } else {
        this.formationService.create(dto).subscribe({
          next: () => {
            this.toastr.success('Formation créée', 'Succès');
            this.router.navigate(['/rh/formations']);
          }
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/rh/formations']);
  }
}
