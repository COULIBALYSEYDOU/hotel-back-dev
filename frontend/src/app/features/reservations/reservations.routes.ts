import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./reservations-list/reservations-list.component').then(m => m.ReservationsListComponent)
  }
];
