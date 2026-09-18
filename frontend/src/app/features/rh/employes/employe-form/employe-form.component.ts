import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeService } from '../../services/employe.service';
import { CreateEmployeRequest, Departement, TypeContrat, PaysCode } from '@core/models/employe.model';
import { ToastrService } from 'ngx-toastr';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

/**
 * Composant formulaire de création/édition d'employé
 */
@Component({
  selector: 'app-employe-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageHeaderComponent],
  template: `
    <app-page-header
      [title]="isEditMode() ? 'Modifier l\'employé' : 'Nouvel employé'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Employés', route: '/rh/employes' },
        { label: isEditMode() ? 'Modifier' : 'Nouveau' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <form [formGroup]="employeForm" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <!-- Informations de base -->
          <div class="md:col-span-2">
            <h3 class="text-lg font-semibold mb-4">Informations de base</h3>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Matricule *
            </label>
            <input
              type="text"
              formControlName="matricule"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Nom *
            </label>
            <input
              type="text"
              formControlName="nom"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Prénom *
            </label>
            <input
              type="text"
              formControlName="prenom"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Email *
            </label>
            <input
              type="email"
              formControlName="email"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Téléphone *
            </label>
            <input
              type="tel"
              formControlName="telephone"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Poste *
            </label>
            <input
              type="text"
              formControlName="poste"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Département *
            </label>
            <select
              formControlName="departement"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            >
              <option *ngFor="let dept of departements" [value]="dept">{{ dept }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Type de contrat *
            </label>
            <select
              formControlName="typeContrat"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            >
              <option *ngFor="let type of typesContrat" [value]="type">{{ type }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Date d'embauche *
            </label>
            <input
              type="date"
              formControlName="dateEmbauche"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Pays *
            </label>
            <select
              formControlName="paysCode"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            >
              <option value="CI">Côte d'Ivoire</option>
              <option value="SN">Sénégal</option>
              <option value="GH">Ghana</option>
              <option value="FRA">France</option>
              <option value="CMR">Cameroun</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Salaire de base
            </label>
            <input
              type="number"
              formControlName="salaireBase"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500"
            />
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
            [disabled]="employeForm.invalid"
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
export class EmployeFormComponent implements OnInit {
  private readonly _isEditMode = signal<boolean>(false);
  readonly isEditMode = this._isEditMode.asReadonly();

  employeForm: FormGroup;

  readonly departements: Departement[] = [
    'RECEPTION',
    'HOUSEKEEPING',
    'FINANCE',
    'RH',
    'FB',
    'MAINTENANCE',
    'SECURITE',
    'SPA',
    'RESTAURATION',
    'ADMINISTRATION'
  ];

  readonly typesContrat: TypeContrat[] = [
    'CDI',
    'CDD',
    'STAGE',
    'INTERIM',
    'APPRENTISSAGE',
    'TEMPS_PARTIEL'
  ];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {
    this.employeForm = this.fb.group({
      matricule: ['', [Validators.required]],
      nom: ['', [Validators.required]],
      prenom: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required]],
      poste: ['', [Validators.required]],
      departement: ['', [Validators.required]],
      typeContrat: ['CDI', [Validators.required]],
      dateEmbauche: ['', [Validators.required]],
      paysCode: ['CI', [Validators.required]],
      salaireBase: [null],
      devise: ['XOF']
    });
  }

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid && uuid !== 'new') {
      this._isEditMode.set(true);
      this.loadEmploye(uuid);
    }
  }

  private loadEmploye(uuid: string): void {
    this.employeService.getByUuid(uuid).subscribe({
      next: (employe) => {
        this.employeForm.patchValue({
          matricule: employe.matricule,
          nom: employe.nom,
          prenom: employe.prenom,
          email: employe.email,
          telephone: employe.telephone,
          poste: employe.poste,
          departement: employe.departement,
          typeContrat: employe.typeContrat,
          dateEmbauche: employe.dateEmbauche?.split('T')[0],
          paysCode: employe.paysCode,
          salaireBase: employe.salaireBase,
          devise: employe.devise
        });
      }
    });
  }

  onSubmit(): void {
    if (this.employeForm.valid) {
      const formValue = this.employeForm.value;
      const dto: CreateEmployeRequest = {
        ...formValue,
        dateEmbauche: new Date(formValue.dateEmbauche).toISOString()
      };

      if (this.isEditMode()) {
        const uuid = this.route.snapshot.paramMap.get('uuid');
        if (uuid) {
          this.employeService.update(uuid, dto).subscribe({
            next: () => {
              this.toastr.success('Employé modifié avec succès', 'Succès');
              this.router.navigate(['/rh/employes']);
            },
            error: () => {
              this.toastr.error('Erreur lors de la modification', 'Erreur');
            }
          });
        }
      } else {
        this.employeService.create(dto).subscribe({
          next: () => {
            this.toastr.success('Employé créé avec succès', 'Succès');
            this.router.navigate(['/rh/employes']);
          },
          error: () => {
            this.toastr.error('Erreur lors de la création', 'Erreur');
          }
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/rh/employes']);
  }
}
