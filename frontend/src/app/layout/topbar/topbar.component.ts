import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Menu, Bell, User, LogOut } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';
import { AuthService } from '@core/services/auth.service';
import { TenantService } from '@core/services/tenant.service';

/**
 * Composant topbar avec notifications et profil
 */
@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-6">
      <!-- Bouton menu mobile -->
      <button
        (click)="onToggleSidebar()"
        class="lg:hidden p-2 rounded-lg hover:bg-gray-100"
      >
        <menu class="w-5 h-5" />
      </button>

      <!-- Espace vide sur desktop -->
      <div class="hidden lg:block"></div>

      <!-- Actions droite -->
      <div class="flex items-center space-x-4">
        <!-- Notifications -->
        <button class="relative p-2 rounded-lg hover:bg-gray-100">
          <bell class="w-5 h-5 text-gray-600" />
          <span class="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
        </button>

        <!-- Profil -->
        <div class="flex items-center space-x-3">
          <div class="text-right hidden md:block">
            <p class="text-sm font-medium text-gray-900">
              {{ authService.currentUsername() || 'Utilisateur' }}
            </p>
            <p class="text-xs text-gray-500">
              Org: {{ tenantService.currentOrganisationId() }}
            </p>
          </div>
          <button class="p-2 rounded-full bg-primary-100 text-primary-600">
            <user class="w-5 h-5" />
          </button>
          <button
            (click)="onLogout()"
            class="p-2 rounded-lg hover:bg-gray-100 text-gray-600"
            title="Déconnexion"
          >
            <log-out class="w-5 h-5" />
          </button>
        </div>
      </div>
    </header>
  `,
  styles: []
})
export class TopbarComponent {
  @Output() toggleSidebar = new EventEmitter<void>();

  constructor(
    public authService: AuthService,
    public tenantService: TenantService
  ) {}

  onToggleSidebar(): void {
    this.toggleSidebar.emit();
  }

  onLogout(): void {
    this.authService.logout();
    // TODO: Rediriger vers la page de connexion
  }
}
