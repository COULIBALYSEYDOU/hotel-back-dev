import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '@core/services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
}

interface SousItem {
  label: string;
  route: string;
  /** Pastille de droite : nombre, ratio… */
  badge?: string;
  badgeClass?: string;
}

/**
 * Sidebar Étoile OS — portée à l'identique depuis la maquette Stitch
 * (« LEFT SHELL: SideNavBar Component (Docked, 260px) »).
 */
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <aside aria-label="Navigation latérale principale"
           class="w-64 bg-surface-container-lowest border-r border-outline-variant flex flex-col justify-between p-3 flex-none z-20 custom-scrollbar overflow-y-auto">

      <div class="flex flex-col gap-1">

        <!-- Subheader context -->
        <div class="px-3 pt-2 pb-3 mb-1 border-b border-outline-variant/60 flex items-center justify-between">
          <span class="text-caption font-caption uppercase tracking-wider text-secondary font-semibold">Portail Établissement</span>
          <span class="text-caption font-caption bg-surface-container px-2 py-0.5 rounded text-on-surface-variant font-medium">SaaS v3.4</span>
        </div>

        <!-- Navigation (13 items) -->
        <a *ngFor="let item of nav"
           [routerLink]="item.route"
           routerLinkActive="!bg-surface-container-high !text-primary !font-semibold"
           class="flex items-center gap-3 px-3 py-2 rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors duration-150 active:scale-[0.98] no-underline">
          <span class="material-symbols-outlined text-lg">{{ item.icon }}</span>
          <span class="text-label-md font-label-md">{{ item.label }}</span>
        </a>

        <!-- 13. Paramètres & Sécurité (onglet à sous-menu) -->
        <div class="mt-1">
          <button type="button" (click)="ouvert = !ouvert"
                  class="w-full flex items-center justify-between px-3 py-2 rounded-lg transition-colors"
                  [class]="ouvert
                    ? 'w-full flex items-center justify-between px-3 py-2 rounded-lg bg-surface-container-high text-primary font-semibold'
                    : 'w-full flex items-center justify-between px-3 py-2 rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container-low'">
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-lg">settings</span>
              <span class="text-label-md font-label-md">Paramètres &amp; Sécurité</span>
            </div>
            <span class="material-symbols-outlined text-base">{{ ouvert ? 'expand_less' : 'expand_more' }}</span>
          </button>

          <div *ngIf="ouvert" class="mt-1 ml-4 pl-3 border-l-2 border-primary space-y-1">
            <a *ngFor="let s of sousMenu"
               [routerLink]="s.route"
               #rla="routerLinkActive" routerLinkActive
               [class]="rla.isActive
                 ? 'no-underline flex items-center justify-between py-1.5 px-2 text-label-sm font-label-sm font-semibold text-primary bg-primary/10 rounded'
                 : 'no-underline flex items-center justify-between py-1.5 px-2 text-label-sm font-label-sm text-secondary hover:text-on-surface rounded hover:bg-surface-container-low transition-colors'">
              <span>{{ s.label }}</span>
              <span *ngIf="rla.isActive && !s.badge" class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              <span *ngIf="s.badge" [class]="s.badgeClass">{{ s.badge }}</span>
            </a>
          </div>
        </div>
      </div>

      <!-- Sidebar Footer -->
      <div class="pt-4 border-t border-outline-variant flex flex-col gap-1">
        <a routerLink="/reporting/documents"
           class="flex items-center gap-3 px-3 py-2 rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors text-label-md font-label-md no-underline">
          <span class="material-symbols-outlined text-lg">contact_support</span>
          <span>Aide &amp; Assistance</span>
        </a>
        <button type="button" (click)="onLogout()"
                class="w-full flex items-center gap-3 px-3 py-2 rounded-lg text-error hover:bg-error-container/40 transition-colors text-label-md font-label-md font-medium">
          <span class="material-symbols-outlined text-lg">logout</span>
          <span>Déconnexion</span>
        </button>
      </div>
    </aside>
  `,
})
export class SidebarComponent {
  @Input() collapsed = false;
  @Output() toggle = new EventEmitter<void>();

  /** Les 12 premières entrées de la maquette (la 13e a un sous-menu). */
  nav: NavItem[] = [
    { label: 'Tableau de bord',            icon: 'dashboard',       route: '/dashboard' },
    { label: 'Planning',                   icon: 'calendar_today',  route: '/planning/calendrier' },
    { label: 'Réservations',               icon: 'book_online',     route: '/reservations' },
    { label: 'Chambres & Housekeeping',    icon: 'bed',             route: '/planning/housekeeping' },
    { label: 'Finance',                    icon: 'payments',        route: '/finance/encaissements' },
    { label: 'Clients & CRM',              icon: 'group',           route: '/clientele' },
    { label: 'Maintenance & SAV',          icon: 'build',           route: '/maintenance' },
    { label: 'RH & Personnel',             icon: 'badge',           route: '/rh' },
    { label: 'Reporting & Analytics',      icon: 'analytics',       route: '/reporting' },
    { label: 'Événements & Banquets',      icon: 'celebration',     route: '/planning/evenements' },
    { label: 'Restauration & Room Service', icon: 'restaurant',     route: '/restauration' },
    { label: 'Moteur Web & OTAs',          icon: 'cloud_sync',      route: '/planning/channels' },
  ];

  sousMenu: SousItem[] = [
    { label: 'Gestion Utilisateurs & Droits', route: '/admin/users' },
    { label: "Journal d'Audit & Utilisateurs", route: '/admin/audit' },
    {
      label: 'Affectation Multi-Tâches', route: '/admin/roles',
      badge: '24',
      badgeClass: 'text-caption font-caption bg-secondary-container text-on-secondary-container px-1.5 rounded font-bold',
    },
    {
      label: 'Activation des Modules SaaS', route: '/admin/modules',
      badge: '11/13',
      badgeClass: 'text-caption font-caption text-primary font-bold',
    },
  ];

  /** Le sous-menu est déployé par défaut, comme dans la maquette. */
  ouvert = true;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
