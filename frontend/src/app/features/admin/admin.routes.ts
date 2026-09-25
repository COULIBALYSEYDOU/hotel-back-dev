import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'users' },
  {
    path: 'users',
    loadComponent: () => import('./users/users.component').then(m => m.UsersComponent),
  },
  {
    path: 'roles',
    loadComponent: () => import('./roles/roles.component').then(m => m.RolesComponent),
  },
  {
    path: 'audit',
    loadComponent: () => import('./audit/audit.component').then(m => m.AuditComponent),
  },
  {
    path: 'security',
    loadComponent: () => import('./security/security.component').then(m => m.SecurityComponent),
  },
  {
    path: 'modules',
    loadComponent: () => import('./modules/modules.component').then(m => m.ModulesComponent),
  },
  {
    path: 'parametres',
    loadComponent: () => import('./parametres/parametres.component').then(m => m.ParametresComponent),
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./admin-dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent),
  },
];
