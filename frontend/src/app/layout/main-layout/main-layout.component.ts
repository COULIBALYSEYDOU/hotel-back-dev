import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TopbarComponent } from '../topbar/topbar.component';

/**
 * Layout principal Étoile OS — structure exacte de la maquette Stitch :
 * header pleine largeur en haut, puis rangée [sidebar 256px | zone de travail scrollable].
 */
@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterOutlet, SidebarComponent, TopbarComponent],
  template: `
    <div class="h-screen font-body-md text-on-surface bg-background flex flex-col overflow-hidden antialiased
                selection:bg-primary-fixed selection:text-on-primary-fixed">

      <!-- TOP SHELL -->
      <app-topbar />

      <!-- BODY CONTENT WRAPPER -->
      <div class="flex flex-1 overflow-hidden relative">
        <app-sidebar />

        <main class="flex-1 flex flex-col overflow-y-auto custom-scrollbar bg-background p-6 lg:p-8 space-y-6">
          <router-outlet></router-outlet>
        </main>
      </div>
    </div>
  `,
})
export class MainLayoutComponent {}
