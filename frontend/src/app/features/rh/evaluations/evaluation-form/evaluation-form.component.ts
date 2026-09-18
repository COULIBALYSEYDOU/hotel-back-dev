import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EvaluationService } from '../../services/evaluation.service';
import { EmployeService } from '../../services/employe.service';
import { CreateEvaluationRequest, UpdateEvaluationRequest, TypeEvaluation, Recommendation } from '@core/models/evaluation.model';
import { Employe } from '@core/models/employe.model';
import { ToastrService } from 'ngx-toastr';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';

@Component({
  selector: 'app-evaluation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageHeaderComponent],
  template: `
    <app-page-header
      [title]="isEditMode() ? 'Compléter l\'évaluation' : 'Démarrer une évaluation'"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Évaluations', route: '/rh/evaluations' },
        { label: isEditMode() ? 'Compléter' : 'Nouveau' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <form [formGroup]="evaluationForm" (ngSubmit)="onSubmit()">
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
            <label class="block text-sm font-medium text-gray-700 mb-2">Type *</label>
            <select formControlName="typeEvaluation" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option *ngFor="let type of types" [value]="type">{{ type }}</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Période début</label>
            <input type="date" formControlName="periodeDebut" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Période fin</label>
            <input type="date" formControlName="periodeFin" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div *ngIf="isEditMode()">
            <label class="block text-sm font-medium text-gray-700 mb-2">Score compétences (0-100)</label>
            <input type="number" formControlName="scoreCompetences" min="0" max="100" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div *ngIf="isEditMode()">
            <label class="block text-sm font-medium text-gray-700 mb-2">Score objectifs (0-100)</label>
            <input type="number" formControlName="scoreObjectifs" min="0" max="100" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div *ngIf="isEditMode()">
            <label class="block text-sm font-medium text-gray-700 mb-2">Score comportement (0-100)</label>
            <input type="number" formControlName="scoreComportement" min="0" max="100" class="w-full px-4 py-2 border border-gray-300 rounded-lg" />
          </div>

          <div *ngIf="isEditMode()">
            <label class="block text-sm font-medium text-gray-700 mb-2">Recommandation</label>
            <select formControlName="recommendation" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
              <option *ngFor="let rec of recommendations" [value]="rec">{{ rec }}</option>
            </select>
          </div>

          <div *ngIf="isEditMode()" class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Points forts</label>
            <textarea formControlName="pointsForts" rows="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg"></textarea>
          </div>

          <div *ngIf="isEditMode()" class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Points à améliorer</label>
            <textarea formControlName="pointsAmelioration" rows="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg"></textarea>
          </div>

          <div *ngIf="isEditMode()" class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Plan d'action</label>
            <textarea formControlName="planAction" rows="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg"></textarea>
          </div>

          <div *ngIf="isEditMode()" class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-2">Commentaires</label>
            <textarea formControlName="commentairesEvaluateur" rows="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg"></textarea>
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-4">
          <button type="button" (click)="onCancel()" class="px-4 py-2 border border-gray-300 rounded-lg">Annuler</button>
          <button type="submit" [disabled]="evaluationForm.invalid" class="px-4 py-2 bg-primary-600 text-white rounded-lg">
            {{ isEditMode() ? 'Compléter' : 'Démarrer' }}
          </button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class EvaluationFormComponent implements OnInit {
  private readonly _isEditMode = signal<boolean>(false);
  private readonly _employes = signal<Employe[]>([]);
  
  readonly isEditMode = this._isEditMode.asReadonly();
  readonly employes = this._employes.asReadonly();

  evaluationForm: FormGroup;

  readonly types: TypeEvaluation[] = ['ANNUEL', 'SEMESTRIEL', 'TRIMESTRIEL', 'PROBATION', 'PROMOTION'];
  readonly recommendations: Recommendation[] = ['PROMOTION', 'MAINTIEN', 'FORMATION', 'MISE_EN_GARDE'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private evaluationService: EvaluationService,
    private employeService: EmployeService,
    private toastr: ToastrService
  ) {
    this.evaluationForm = this.fb.group({
      employeId: ['', [Validators.required]],
      typeEvaluation: ['ANNUEL', [Validators.required]],
      periodeDebut: [''],
      periodeFin: [''],
      scoreCompetences: [null],
      scoreObjectifs: [null],
      scoreComportement: [null],
      pointsForts: [''],
      pointsAmelioration: [''],
      planAction: [''],
      recommendation: [''],
      commentairesEvaluateur: ['']
    });
  }

  ngOnInit(): void {
    this.loadEmployes();
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (uuid && uuid !== 'new') {
      this._isEditMode.set(true);
      this.loadEvaluation(uuid);
    }
  }

  private loadEmployes(): void {
    this.employeService.getAll({ page: 0, size: 1000 }).subscribe({
      next: (response) => this._employes.set(response.content)
    });
  }

  private loadEvaluation(uuid: string): void {
    this.evaluationService.getByUuid(uuid).subscribe({
      next: (eval) => {
        this.evaluationForm.patchValue({
          ...eval,
          periodeDebut: eval.periodeDebut?.split('T')[0],
          periodeFin: eval.periodeFin?.split('T')[0]
        });
      }
    });
  }

  onSubmit(): void {
    if (this.evaluationForm.valid) {
      const dto = this.evaluationForm.value;
      if (this.isEditMode()) {
        const uuid = this.route.snapshot.paramMap.get('uuid');
        if (uuid) {
          this.evaluationService.complete(uuid, dto).subscribe({
            next: () => {
              this.toastr.success('Évaluation complétée', 'Succès');
              this.router.navigate(['/rh/evaluations']);
            }
          });
        }
      } else {
        this.evaluationService.start(dto).subscribe({
          next: () => {
            this.toastr.success('Évaluation démarrée', 'Succès');
            this.router.navigate(['/rh/evaluations']);
          }
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/rh/evaluations']);
  }
}
