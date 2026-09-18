import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./rh-index/rh-index.component').then(m => m.RhIndexComponent)
  },
  {
    path: 'employes',
    loadComponent: () => import('./employes/employes-list/employes-list.component').then(m => m.EmployesListComponent)
  },
  {
    path: 'employes/new',
    loadComponent: () => import('./employes/employe-form/employe-form.component').then(m => m.EmployeFormComponent)
  },
  {
    path: 'employes/:uuid',
    loadComponent: () => import('./employes/employe-detail/employe-detail.component').then(m => m.EmployeDetailComponent)
  },
  {
    path: 'employes/:uuid/edit',
    loadComponent: () => import('./employes/employe-form/employe-form.component').then(m => m.EmployeFormComponent)
  },
  {
    path: 'conges',
    loadComponent: () => import('./conges/conges-list/conges-list.component').then(m => m.CongesListComponent)
  },
  {
    path: 'conges/new',
    loadComponent: () => import('./conges/conge-form/conge-form.component').then(m => m.CongeFormComponent)
  },
  {
    path: 'conges/:uuid',
    loadComponent: () => import('./conges/conge-detail/conge-detail.component').then(m => m.CongeDetailComponent)
  },
  {
    path: 'conges/:uuid/edit',
    loadComponent: () => import('./conges/conge-form/conge-form.component').then(m => m.CongeFormComponent)
  },
  {
    path: 'formations',
    loadComponent: () => import('./formations/formations-list/formations-list.component').then(m => m.FormationsListComponent)
  },
  {
    path: 'formations/new',
    loadComponent: () => import('./formations/formation-form/formation-form.component').then(m => m.FormationFormComponent)
  },
  {
    path: 'formations/:uuid',
    loadComponent: () => import('./formations/formation-detail/formation-detail.component').then(m => m.FormationDetailComponent)
  },
  {
    path: 'formations/:uuid/edit',
    loadComponent: () => import('./formations/formation-form/formation-form.component').then(m => m.FormationFormComponent)
  },
  {
    path: 'fiches-paie',
    loadComponent: () => import('./fiches-paie/fiches-paie-list/fiches-paie-list.component').then(m => m.FichesPaieListComponent)
  },
  {
    path: 'fiches-paie/new',
    loadComponent: () => import('./fiches-paie/fiche-paie-form/fiche-paie-form.component').then(m => m.FichePaieFormComponent)
  },
  {
    path: 'fiches-paie/:uuid',
    loadComponent: () => import('./fiches-paie/fiche-paie-detail/fiche-paie-detail.component').then(m => m.FichePaieDetailComponent)
  },
  {
    path: 'fiches-paie/:uuid/edit',
    loadComponent: () => import('./fiches-paie/fiche-paie-form/fiche-paie-form.component').then(m => m.FichePaieFormComponent)
  },
  {
    path: 'evaluations',
    loadComponent: () => import('./evaluations/evaluations-list/evaluations-list.component').then(m => m.EvaluationsListComponent)
  },
  {
    path: 'evaluations/new',
    loadComponent: () => import('./evaluations/evaluation-form/evaluation-form.component').then(m => m.EvaluationFormComponent)
  },
  {
    path: 'evaluations/:uuid',
    loadComponent: () => import('./evaluations/evaluation-detail/evaluation-detail.component').then(m => m.EvaluationDetailComponent)
  },
  {
    path: 'evaluations/:uuid/edit',
    loadComponent: () => import('./evaluations/evaluation-form/evaluation-form.component').then(m => m.EvaluationFormComponent)
  },
  {
    path: 'competences',
    loadComponent: () => import('./competences/competences-list/competences-list.component').then(m => m.CompetencesListComponent)
  },
  {
    path: 'recrutements',
    loadComponent: () => import('./recrutements/recrutements-list/recrutements-list.component').then(m => m.RecrutementsListComponent)
  },
  {
    path: 'contrats',
    loadComponent: () => import('./contrats/contrats-list/contrats-list.component').then(m => m.ContratsListComponent)
  },
  {
    path: 'temps-travail',
    loadComponent: () => import('./temps-travail/temps-travail-list/temps-travail-list.component').then(m => m.TempsTravailListComponent)
  },
  {
    path: 'absences',
    loadComponent: () => import('./absences/absences-list/absences-list.component').then(m => m.AbsencesListComponent)
  },
  {
    path: 'onboarding',
    loadComponent: () => import('./onboarding/onboarding-list/onboarding-list.component').then(m => m.OnboardingListComponent)
  },
  {
    path: 'onboarding/:uuid',
    loadComponent: () => import('./onboarding/onboarding-detail/onboarding-detail.component').then(m => m.OnboardingDetailComponent)
  },
  {
    path: 'offboarding',
    loadComponent: () => import('./offboarding/offboarding-list/offboarding-list.component').then(m => m.OffboardingListComponent)
  },
  {
    path: 'conformite/employe/:uuid',
    loadComponent: () => import('./conformite/conformite-verification/conformite-verification.component').then(m => m.ConformiteVerificationComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./rh-dashboard/rh-dashboard.component').then(m => m.RhDashboardComponent)
  }
];
