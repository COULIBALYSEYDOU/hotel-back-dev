import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TopbarComponent } from '../topbar/topbar.component';

/**
 * Layout principal de l'application
 * Contient la sidebar, la topbar et le contenu principal
 */
@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent, TopbarComponent],
  template: `
    <div class="flex h-screen bg-gray-100">
      <!-- Sidebar -->
      <app-sidebar [collapsed]="sidebarCollapsed()" (toggle)="toggleSidebar()" />
      
      <!-- Contenu principal -->
      <div class="flex-1 flex flex-col overflow-hidden">
        <!-- Topbar -->
        <app-topbar (toggleSidebar)="toggleSidebar()" />
        
        <!-- Contenu -->
        <main class="flex-1 overflow-y-auto p-6">
          <ng-content></ng-content>
        </main>
      </div>
    </div>
  `,
  styles: []
})
export class MainLayoutComponent {
  readonly sidebarCollapsed = signal<boolean>(false);

  toggleSidebar(): void {
    this.sidebarCollapsed.update(collapsed => !collapsed);
  }
}
