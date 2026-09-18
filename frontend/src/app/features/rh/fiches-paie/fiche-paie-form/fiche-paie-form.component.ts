import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FichePaieService } from '../../services/fiche-paie.service';
import { EmployeService } from '../../services/employe.service';
import { CreateFichePaieRequest, UpdateFichePaieRequest } from '@core/models/fiche-paie.model';
import { Employe } from '@core/models/employe.model';
import { ToastrService } from 'ngx-toastr';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-fiche-paie-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageHeaderComponent],
  template: `
    <app-page-header
      [title]="isEditMode() ? 'Modifier la fiche de paie' : 'Nouvelle fiche de paie'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Fiches de paie', route: '/rh/fiches-paie' },
        { label: isEditMode() ? 'Modifier' : 'Nouveau' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <form [formGroup]="fichePaieForm" (ngSubmit)="onSubmit()">
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
            <label class="block text-sm font-medium text-gray-700 mb-2">Mois *</label>
            <select formControlName="mois" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option *ngFor="let m of mois" [value]="m.value">{{ m.label }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Année *</label>
            <input type="number" formControlName="annee" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Salaire brut</label>
            <input type="number" formControlName="salaireBrut" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Cotisations patronales</label>
            <input type="number" formControlName="cotisationPatronale" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Cotisations salariales</label>
            <input type="number" formControlName="cotisationSalariale" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Impôt</label>
            <input type="number" formControlName="impot" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Net à payer</label>
            <input type="number" formControlName="netAPayer" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-4">
          <button type="button" (click)="onCancel()" class="px-4 py-2 border border-gray-300 rounded-lg">Annuler</button>
          <button type="submit" [disabled]="fichePaieForm.invalid" class="px-4 py-2 bg-primary-600 text-white rounded-lg">Enregistrer</button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class FichePaieFormComponent implements OnInit {
  private readonly _isEditMode = signal<boolean>(false);
  private readonly _employes = signal<Employe[]>([]);
  
  readonly isEditMode = this._isEditMode.asReadonly();
  readonly employes = this._employes.asReadonly();

  fichePaieForm: FormGroup;

  readonly mois = [
    { value: 1, label: 'Janvier' }, { value: 2, label: 'Février' },
    { value: 3, label: 'Mars' }, { value: 4, label: 'Avril' },
    { value: 5, label: 'Mai' }, { value: 6, label: 'Juin' },
    { value: 7, label: 'Juillet' }, { value: 8, label: 'Août' },
    { value: 9, label: 'Septembre' }, { value: 10, label: 'Octobre' },
    { value: 11, label: 'Novembre' }, { value: 12, label: 'Décembre' }
  ];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private fichePaieService: FichePaieService,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {
    this.fichePaieForm = this.fb.group({
      employeId: ['', [Validators.required]],
      mois: [new Date().getMonth() + 1, [Validators.required]],
      annee: [new Date().getFullYear(), [Validators.required]],
      salaireBrut: [null],
      cotisationPatronale: [null],
      cotisationSalariale: [null],
      impot: [null],
      netAPayer: [null]
    });
  }

  ngOnInit(): void {
    this.loadEmployes();
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid && uuid !== 'new') {
      this._isEditMode.set(true);
      this.loadFichePaie(uuid);
    }
  }

  private loadEmployes(): void {
    this.employeService.getAll({ page: 0, size: 1000 }).subscribe({
      next: (response) => this._employes.set(response.content)
    });
  }

  private loadFichePaie(uuid: string): void {
    this.fichePaieService.getByUuid(uuid).subscribe({
      next: (fiche) => this.fichePaieForm.patchValue(fiche)
    });
  }

  onSubmit(): void {
    if (this.fichePaieForm.valid) {
      const dto = this.fichePaieForm.value;
      if (this.isEditMode()) {
        const uuid = this.route.snapshot.paramMap.get('uuid');
        if (uuid) {
          this.fichePaieService.update(uuid, dto).subscribe({
            next: () => {
              this.toastr.success('Fiche de paie modifiée', 'Succès');
              this.router.navigate(['/rh/fiches-paie']);
            }
          });
        }
      } else {
        this.fichePaieService.create(dto).subscribe({
          next: () => {
            this.toastr.success('Fiche de paie créée', 'Succès');
            this.router.navigate(['/rh/fiches-paie']);
          }
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/rh/fiches-paie']);
  }
}
