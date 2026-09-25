import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'calendrier',
    loadComponent: () => import('./planning-calendrier/planning-calendrier.component').then(m => m.PlanningCalendrierComponent),
  },
  {
    path: 'housekeeping',
    loadComponent: () => import('./housekeeping/housekeeping.component').then(m => m.HousekeepingComponent),
  },
  {
    path: 'evenements',
    loadComponent: () => import('./evenements/evenements.component').then(m => m.EvenementsComponent),
  },
  {
    path: 'tarifications',
    loadComponent: () => import('./tarifications/tarifications.component').then(m => m.TarificationsComponent),
  },
  {
    path: 'channels',
    loadComponent: () => import('./channels/channels.component').then(m => m.ChannelsComponent),
  },
  { path: '', pathMatch: 'full', redirectTo: 'calendrier' },
];
