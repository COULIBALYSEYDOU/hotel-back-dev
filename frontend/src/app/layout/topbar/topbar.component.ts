import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '@core/services/auth.service';
import { TenantService } from '@core/services/tenant.service';

/**
 * Topbar Étoile OS — portée à l'identique depuis la maquette Stitch
 * (« TOP SHELL: TopNavBar Component »).
 */
@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <header class="bg-surface-container-lowest text-primary shadow-sm border-b border-outline-variant z-30 flex-none">
      <div class="flex justify-between items-center w-full px-6 h-16 max-w-full">

        <!-- Brand & Property Anchor -->
        <div class="flex items-center gap-6">
          <div class="flex items-center gap-3">
            <div class="w-9 h-9 rounded-lg bg-primary flex items-center justify-center text-on-primary shadow-sm font-bold text-lg tracking-tight">
              <span>É</span>
            </div>
            <span class="text-headline-md font-headline-md font-bold text-primary tracking-tight">Étoile OS</span>
          </div>
          <div class="hidden lg:flex items-center gap-2 pl-6 border-l border-outline-variant text-label-lg font-label-lg">
            <span class="material-symbols-outlined text-primary text-xl">apartment</span>
            <span class="font-semibold text-on-surface">Hôtel Étoile du Sud - Abidjan Plateau</span>
            <span class="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-primary-fixed text-on-primary-fixed ml-2 font-medium">
              <span class="w-1.5 h-1.5 rounded-full bg-primary animate-pulse"></span>
              En Ligne (98.4% Occ.)
            </span>
          </div>
        </div>

        <!-- Center / Search Input -->
        <div class="flex-1 max-w-xl mx-8">
          <div class="relative w-full">
            <div class="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-secondary">
              <span class="material-symbols-outlined text-lg">search</span>
            </div>
            <input type="text"
                   placeholder="Rechercher un collaborateur (matricule, nom), un module ou une tâche..."
                   class="w-full h-10 pl-10 pr-12 text-body-md font-body-md bg-surface-container-low border border-outline-variant rounded-lg text-on-surface placeholder:text-secondary focus:bg-surface-container-lowest focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all duration-150 outline-none">
            <div class="absolute inset-y-0 right-0 pr-3 flex items-center gap-1 text-secondary text-caption font-caption">
              <kbd class="px-1.5 py-0.5 bg-surface-container rounded border border-outline-variant">⌘</kbd>
              <kbd class="px-1.5 py-0.5 bg-surface-container rounded border border-outline-variant">K</kbd>
            </div>
          </div>
        </div>

        <!-- Trailing Controls & Super-Admin Identity -->
        <div class="flex items-center gap-3">
          <button type="button" title="Synchronisation"
                  class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors relative">
            <span class="material-symbols-outlined text-xl">sync</span>
          </button>
          <button type="button" title="Notifications"
                  class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors relative">
            <span class="material-symbols-outlined text-xl">notifications</span>
            <span class="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-error"></span>
          </button>
          <button type="button" title="Aide &amp; Documentation"
                  class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors">
            <span class="material-symbols-outlined text-xl">help</span>
          </button>

          <!-- Divider -->
          <div class="h-6 w-px bg-outline-variant mx-1"></div>

          <!-- Admin Profile Pill -->
          <div class="flex items-center gap-3 pl-2 py-1 pr-3 rounded-xl hover:bg-surface-container-low transition-colors cursor-pointer border border-transparent hover:border-outline-variant">
            <div class="w-9 h-9 rounded-full object-cover ring-2 ring-primary/30 bg-primary-fixed text-on-primary-fixed flex items-center justify-center font-bold text-label-md">
              {{ initials() }}
            </div>
            <div class="hidden xl:flex flex-col text-left">
              <span class="text-label-md font-label-md text-on-surface leading-tight font-bold">
                {{ authService.currentUsername() || "M. Jean-Philippe N'Guessan" }}
              </span>
              <span class="text-caption font-caption text-primary font-semibold flex items-center gap-1">
                <span class="material-symbols-outlined text-xs">verified_user</span>
                Administrateur Général
              </span>
            </div>
            <span class="material-symbols-outlined text-secondary text-lg">expand_more</span>
          </div>

          <!-- Trailing Primary Action -->
          <a [routerLink]="['/reservations']" [queryParams]="{ new: 1 }"
             class="hidden 2xl:flex items-center gap-2 px-3.5 py-2 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-md font-label-md shadow-sm transition-all active:scale-95 no-underline">
            <span class="material-symbols-outlined text-lg">add</span>
            <span>Nouvelle Réservation</span>
          </a>
        </div>
      </div>
    </header>
  `,
})
export class TopbarComponent {
  @Output() toggleSidebar = new EventEmitter<void>();

  constructor(
    public authService: AuthService,
    public tenantService: TenantService,
    private router: Router,
  ) {}

  initials(): string {
    const u = this.authService.currentUsername();
    if (!u) return 'JN';
    return u.substring(0, 2).toUpperCase();
  }

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
