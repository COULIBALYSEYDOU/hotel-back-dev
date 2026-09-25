import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'encaissements' },
  {
    path: 'encaissements',
    loadComponent: () => import('./encaissements/encaissements.component').then(m => m.EncaissementsComponent),
  },
  {
    path: 'factures',
    loadComponent: () => import('./factures/factures.component').then(m => m.FacturesComponent),
  },
  {
    path: 'depenses',
    loadComponent: () => import('./depenses/depenses.component').then(m => m.DepensesComponent),
  },
  {
    path: 'budgets',
    loadComponent: () => import('./budgets/budgets.component').then(m => m.BudgetsComponent),
  },
  {
    path: 'night-audit',
    loadComponent: () => import('./night-audit/night-audit.component').then(m => m.NightAuditComponent),
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./finance-dashboard/finance-dashboard.component').then(m => m.FinanceDashboardComponent),
  },
];
