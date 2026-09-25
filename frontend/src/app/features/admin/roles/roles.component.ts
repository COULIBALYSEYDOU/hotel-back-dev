import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Role {
  code: string;
  nom: string;
  membres: number;
  permissions: number;
  description: string;
  icon: string;
  color: string;
}

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="space-y-6">
      <section class="card !p-5 flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 class="text-headline-lg tracking-tight">Rôles & Permissions</h1>
          <p class="text-body-sm text-on-surface-variant mt-0.5">
            Gestion RBAC des rôles et de leurs permissions.
          </p>
        </div>
        <button class="btn-primary">
          <span class="material-symbols-outlined text-base">add</span>
          Nouveau rôle
        </button>
      </section>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div *ngFor="let r of roles" class="card !p-5">
          <div class="flex items-start justify-between mb-3">
            <div class="flex items-center gap-3">
              <div class="w-11 h-11 rounded-lg flex items-center justify-center" [ngClass]="r.color">
                <span class="material-symbols-outlined">{{ r.icon }}</span>
              </div>
              <div>
                <div class="text-headline-sm">{{ r.nom }}</div>
                <div class="text-caption text-on-surface-variant font-mono">{{ r.code }}</div>
              </div>
            </div>
            <button class="btn-ghost !p-2">
              <span class="material-symbols-outlined">edit</span>
            </button>
          </div>
          <p class="text-body-sm text-on-surface-variant mb-4">{{ r.description }}</p>
          <div class="flex items-center gap-3 text-caption">
            <span class="chip chip-primary">{{ r.membres }} membres</span>
            <span class="chip chip-neutral">{{ r.permissions }} permissions</span>
          </div>
        </div>
      </div>

      <div class="card !p-5">
        <h2 class="text-headline-sm mb-3">Matrice des permissions (aperçu)</h2>
        <div class="overflow-x-auto">
          <table class="w-full text-body-sm">
            <thead class="text-label-sm text-on-surface-variant bg-surface-container-low">
              <tr>
                <th class="text-left px-4 py-3">Module</th>
                <th class="text-center px-3 py-3">Admin</th>
                <th class="text-center px-3 py-3">Réception</th>
                <th class="text-center px-3 py-3">Housekeeping</th>
                <th class="text-center px-3 py-3">Finance</th>
                <th class="text-center px-3 py-3">RH</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-outline-variant">
              <tr *ngFor="let m of matrice">
                <td class="px-4 py-3 font-medium">{{ m.module }}</td>
                <td *ngFor="let p of m.perms" class="px-3 py-3 text-center">
                  <span class="material-symbols-outlined"
                        [ngClass]="p === 'RW' ? 'text-primary' : (p === 'R' ? 'text-secondary' : 'text-outline-variant')">
                    {{ p === 'RW' ? 'check_circle' : (p === 'R' ? 'visibility' : 'do_not_disturb_on') }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `,
})
export class RolesComponent {
  roles: Role[] = [
    { code: 'ROLE_ADMIN',        nom: 'Administrateur',        membres: 3,  permissions: 84, description: 'Accès total à tous les modules et à la configuration système.', icon: 'admin_panel_settings', color: 'bg-error/10 text-error' },
    { code: 'ROLE_RECEPTION',    nom: 'Réception',              membres: 12, permissions: 32, description: 'Gestion des réservations, check-in/out, encaissements.',      icon: 'concierge',            color: 'bg-primary/10 text-primary' },
    { code: 'ROLE_HOUSEKEEPING', nom: 'Housekeeping',           membres: 24, permissions: 12, description: 'Suivi des chambres, tâches ménage, inventaire linge.',        icon: 'cleaning_services',    color: 'bg-tertiary/10 text-tertiary' },
    { code: 'ROLE_FINANCE',      nom: 'Finance & Comptabilité', membres: 5,  permissions: 48, description: 'Facturation, encaissements, budget, rapports comptables.',    icon: 'payments',             color: 'bg-secondary/10 text-secondary' },
    { code: 'ROLE_RH',           nom: 'Ressources Humaines',    membres: 4,  permissions: 42, description: 'Gestion du personnel, paie, congés, formations.',            icon: 'badge',                color: 'bg-tertiary/10 text-tertiary' },
    { code: 'ROLE_MAINTENANCE',  nom: 'Maintenance',            membres: 8,  permissions: 20, description: 'Tickets d\'intervention, gestion des pièces détachées.',    icon: 'build',                color: 'bg-secondary/10 text-secondary' },
  ];

  matrice = [
    { module: 'Réservations', perms: ['RW', 'RW', 'R', 'R', 'N'] },
    { module: 'Planning',     perms: ['RW', 'RW', 'RW', 'R', 'N'] },
    { module: 'Finances',     perms: ['RW', 'R', 'N', 'RW', 'N'] },
    { module: 'Housekeeping', perms: ['RW', 'R', 'RW', 'N', 'N'] },
    { module: 'RH',           perms: ['RW', 'N', 'N', 'R', 'RW'] },
    { module: 'Reporting',    perms: ['RW', 'R', 'R', 'RW', 'R'] },
    { module: 'Admin',        perms: ['RW', 'N', 'N', 'N', 'N'] },
  ];
}
