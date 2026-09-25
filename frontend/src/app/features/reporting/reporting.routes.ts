import { Routes } from '@angular/router';
export const routes: Routes = [
  { path: '',           loadComponent: () => import('./reporting-overview/reporting-overview.component').then(m => m.ReportingOverviewComponent) },
  { path: 'documents',  loadComponent: () => import('./documents/documents.component').then(m => m.DocumentsComponent) },
  { path: 'audit',      loadComponent: () => import('./audit/audit.component').then(m => m.AuditComponent) },
  { path: 'compliance', loadComponent: () => import('./compliance/compliance.component').then(m => m.ComplianceComponent) },
];
