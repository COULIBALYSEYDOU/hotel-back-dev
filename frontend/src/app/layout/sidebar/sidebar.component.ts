import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { 
  LayoutDashboard, 
  Users, 
  Building2, 
  Calendar, 
  DollarSign, 
  UserCircle,
  Settings,
  AlertCircle,
  Menu,
  X
} from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

interface MenuItem {
  label: string;
  route: string;
  icon: any;
  children?: MenuItem[];
}

/**
 * Composant sidebar avec navigation
 */
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <aside
      [class]="collapsed ? 'w-20' : 'w-64'"
      class="bg-gray-900 text-white transition-all duration-300 flex flex-col"
    >
      <!-- Logo -->
      <div class="h-16 flex items-center justify-between px-4 border-b border-gray-800">
        <h1 *ngIf="!collapsed" class="text-xl font-bold">🏨 Hotel</h1>
        <button
          (click)="onToggle()"
          class="p-2 rounded-lg hover:bg-gray-800 transition-colors"
        >
          <menu *ngIf="!collapsed" class="w-5 h-5" />
          <x *ngIf="collapsed" class="w-5 h-5" />
        </button>
      </div>

      <!-- Menu -->
      <nav class="flex-1 overflow-y-auto py-4">
        <ul class="space-y-1 px-2">
          <li *ngFor="let item of menuItems">
            <a
              [routerLink]="item.route"
              routerLinkActive="bg-primary-600"
              [routerLinkActiveOptions]="{ exact: false }"
              [class]="collapsed ? 'justify-center' : 'justify-start'"
              class="flex items-center px-4 py-3 rounded-lg hover:bg-gray-800 transition-colors"
            >
              <component [is]="item.icon" class="w-5 h-5" />
              <span *ngIf="!collapsed" class="ml-3">{{ item.label }}</span>
            </a>
          </li>
        </ul>
      </nav>
    </aside>
  `,
  styles: []
})
export class SidebarComponent {
  @Input() collapsed: boolean = false;
  @Output() toggle = new EventEmitter<void>();

  readonly menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/dashboard', icon: LayoutDashboard },
    { label: 'RH', route: '/rh', icon: Users },
    { label: 'Admin', route: '/admin', icon: Building2 },
    { label: 'Réservations', route: '/reservations', icon: Calendar },
    { label: 'Finance', route: '/finance', icon: DollarSign },
    { label: 'Clientèle', route: '/clientele', icon: UserCircle },
    { label: 'Alertes', route: '/alertes', icon: AlertCircle },
    { label: 'Paramètres', route: '/settings', icon: Settings }
  ];

  onToggle(): void {
    this.toggle.emit();
  }
}
