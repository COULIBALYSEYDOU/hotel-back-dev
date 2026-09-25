import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface AuditEntry {
  time: string;
  user: string;
  action: string;
  ressource: string;
  ip: string;
  severity: 'INFO' | 'WARN' | 'ERROR';
  icon: string;
}

@Component({
  selector: 'app-audit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="space-y-6">
      <section class="card !p-5">
        <div class="flex items-center justify-between flex-wrap gap-3">
          <div>
            <h1 class="text-headline-lg tracking-tight">Journal d'audit</h1>
            <p class="text-body-sm text-on-surface-variant mt-0.5">
              Traces des actions utilisateurs et événements système.
            </p>
          </div>
          <div class="flex items-center gap-2">
            <button class="btn-secondary">
              <span class="material-symbols-outlined text-base">download</span>
              Export CSV
            </button>
            <button class="btn-primary">
              <span class="material-symbols-outlined text-base">rule</span>
              Alertes
            </button>
          </div>
        </div>
      </section>

      <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div class="card !p-4"><span class="text-caption text-on-surface-variant uppercase">Événements 24h</span>
          <div class="text-headline-lg font-bold mt-1">1 842</div></div>
        <div class="card !p-4"><span class="text-caption text-on-surface-variant uppercase">Utilisateurs uniques</span>
          <div class="text-headline-lg font-bold mt-1">28</div></div>
        <div class="card !p-4"><span class="text-caption text-on-surface-variant uppercase">Alertes actives</span>
          <div class="text-headline-lg font-bold mt-1 text-error">3</div></div>
        <div class="card !p-4"><span class="text-caption text-on-surface-variant uppercase">Actions critiques</span>
          <div class="text-headline-lg font-bold mt-1">12</div></div>
      </div>

      <div class="card !p-0 overflow-hidden">
        <div class="px-6 py-4 border-b border-outline-variant flex items-center justify-between gap-3 flex-wrap">
          <h2 class="text-headline-sm">Événements récents</h2>
          <div class="flex items-center gap-2">
            <select [(ngModel)]="sev" class="form-input !py-1 !w-auto text-body-sm">
              <option value="ALL">Toutes sévérités</option>
              <option value="INFO">Info</option>
              <option value="WARN">Attention</option>
              <option value="ERROR">Erreur</option>
            </select>
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-secondary text-base">search</span>
              <input [(ngModel)]="q" class="w-56 h-9 pl-9 pr-3 text-body-sm bg-surface-container-low border border-outline-variant rounded-lg focus:outline-none focus:border-primary" placeholder="Utilisateur, action...">
            </div>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="w-full text-body-sm">
            <thead class="text-label-sm text-on-surface-variant bg-surface-container-low">
              <tr>
                <th class="text-left px-4 py-3">Horodatage</th>
                <th class="text-left px-4 py-3">Utilisateur</th>
                <th class="text-left px-4 py-3">Action</th>
                <th class="text-left px-4 py-3">Ressource</th>
                <th class="text-left px-4 py-3">IP</th>
                <th class="text-left px-4 py-3">Sévérité</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-outline-variant">
              <tr *ngFor="let e of filtered()" class="hover:bg-surface-container-low">
                <td class="px-4 py-3 text-on-surface-variant font-mono">{{ e.time }}</td>
                <td class="px-4 py-3">{{ e.user }}</td>
                <td class="px-4 py-3">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-base text-primary">{{ e.icon }}</span>
                    {{ e.action }}
                  </div>
                </td>
                <td class="px-4 py-3 text-on-surface-variant font-mono">{{ e.ressource }}</td>
                <td class="px-4 py-3 text-on-surface-variant font-mono">{{ e.ip }}</td>
                <td class="px-4 py-3">
                  <span class="chip" [ngClass]="sevChip(e.severity)">{{ e.severity }}</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `,
})
export class AuditComponent {
  q = '';
  sev: 'ALL' | AuditEntry['severity'] = 'ALL';

  entries = signal<AuditEntry[]>([
    { time: '19:42:15', user: 'Awa Touré',   action: 'Création réservation',  ressource: 'RES-2026-1042',   ip: '196.13.121.42', severity: 'INFO',  icon: 'add' },
    { time: '19:38:02', user: 'Sylvie N\'Dri', action: 'Ouverture facture',    ressource: 'F-2026-001234',   ip: '196.13.121.55', severity: 'INFO',  icon: 'receipt_long' },
    { time: '19:15:41', user: 'system',       action: 'Sync canal',            ressource: 'Booking.com',      ip: '10.0.0.1',      severity: 'INFO',  icon: 'sync' },
    { time: '18:47:22', user: 'Bakayoko I.',  action: 'Ticket ouvert',         ressource: 'TCK-1042 · 305',  ip: '196.13.121.88', severity: 'WARN',  icon: 'build' },
    { time: '18:12:05', user: 'system',       action: 'Erreur sync Airbnb',    ressource: 'Airbnb API',       ip: '10.0.0.1',      severity: 'ERROR', icon: 'error' },
    { time: '17:58:11', user: 'Coulibaly S.', action: 'Modification rôle',     ressource: 'ROLE_FINANCE',     ip: '196.13.121.10', severity: 'WARN',  icon: 'admin_panel_settings' },
    { time: '17:32:00', user: 'Awa Touré',   action: 'Encaissement Mobile Money', ressource: 'PAY-2026-000285', ip: '196.13.121.42', severity: 'INFO',  icon: 'phone_iphone' },
    { time: '16:47:33', user: 'Ex-stagiaire Yao', action: 'Échec connexion (5)', ressource: 'usr_034',         ip: '105.71.4.12',   severity: 'ERROR', icon: 'gpp_bad' },
    { time: '16:22:17', user: 'system',       action: 'Sauvegarde quotidienne', ressource: 'DB main',          ip: '10.0.0.1',      severity: 'INFO',  icon: 'backup' },
    { time: '15:15:04', user: 'Aïcha Konaté', action: 'Chambre marquée propre', ressource: 'Chambre 208',      ip: '196.13.121.77', severity: 'INFO',  icon: 'cleaning_services' },
  ]);

  filtered = computed(() => {
    const q = this.q.toLowerCase().trim();
    return this.entries().filter(e => {
      if (this.sev !== 'ALL' && e.severity !== this.sev) return false;
      if (!q) return true;
      return e.user.toLowerCase().includes(q)
          || e.action.toLowerCase().includes(q)
          || e.ressource.toLowerCase().includes(q);
    });
  });

  sevChip(s: AuditEntry['severity']) {
    return { INFO: 'chip-primary', WARN: 'chip-secondary', ERROR: 'chip-error' }[s];
  }
}
