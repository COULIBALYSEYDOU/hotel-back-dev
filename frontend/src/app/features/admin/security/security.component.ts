import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-security',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="space-y-6">
      <section class="card !p-5">
        <div class="flex items-center justify-between flex-wrap gap-3">
          <div>
            <h1 class="text-headline-lg tracking-tight">Paramètres & Sécurité hôtelière</h1>
            <p class="text-body-sm text-on-surface-variant mt-0.5">
              Politiques d'authentification, MFA, chiffrement, journalisation.
            </p>
          </div>
          <span class="chip chip-primary">
            <span class="material-symbols-outlined text-sm">verified_user</span>
            Score sécurité : 84 / 100
          </span>
        </div>
      </section>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div class="card !p-5">
          <h2 class="text-headline-sm mb-4">Authentification</h2>
          <ul class="space-y-4">
            <li *ngFor="let s of authSettings"
                class="flex items-center justify-between gap-3 pb-3 border-b border-outline-variant last:border-none">
              <div>
                <div class="text-body-md font-medium">{{ s.label }}</div>
                <div class="text-caption text-on-surface-variant">{{ s.desc }}</div>
              </div>
              <label class="relative inline-flex items-center cursor-pointer">
                <input type="checkbox" [(ngModel)]="s.enabled" class="sr-only peer">
                <div class="w-11 h-6 bg-surface-container rounded-full peer peer-checked:bg-primary transition-all"></div>
                <div class="absolute left-0.5 top-0.5 w-5 h-5 bg-white rounded-full shadow transition-all"
                     [class.translate-x-5]="s.enabled"></div>
              </label>
            </li>
          </ul>
        </div>

        <div class="card !p-5">
          <h2 class="text-headline-sm mb-4">Politique de mot de passe</h2>
          <div class="space-y-4">
            <div>
              <label class="form-label">Longueur minimale</label>
              <input type="number" class="form-input" value="10">
            </div>
            <div>
              <label class="form-label">Rotation obligatoire (jours)</label>
              <input type="number" class="form-input" value="90">
            </div>
            <ul class="space-y-2 pt-2">
              <li *ngFor="let r of pwdRules" class="flex items-center gap-2 text-body-sm">
                <span class="material-symbols-outlined text-primary">check_circle</span>
                {{ r }}
              </li>
            </ul>
          </div>
        </div>

        <div class="card !p-5">
          <h2 class="text-headline-sm mb-4">Sessions actives</h2>
          <ul class="divide-y divide-outline-variant">
            <li *ngFor="let s of sessions" class="py-3 flex items-center gap-3">
              <span class="material-symbols-outlined text-primary">{{ s.icon }}</span>
              <div class="flex-1 min-w-0">
                <div class="text-body-md font-medium">{{ s.user }}</div>
                <div class="text-caption text-on-surface-variant">{{ s.device }} · {{ s.ip }} · {{ s.time }}</div>
              </div>
              <button class="btn-ghost !p-2">
                <span class="material-symbols-outlined text-base">logout</span>
              </button>
            </li>
          </ul>
        </div>

        <div class="card !p-5">
          <h2 class="text-headline-sm mb-4">Chiffrement & conformité</h2>
          <ul class="space-y-3">
            <li *ngFor="let c of conformite" class="flex items-center gap-3">
              <span class="material-symbols-outlined" [ngClass]="c.ok ? 'text-primary' : 'text-error'">
                {{ c.ok ? 'check_circle' : 'cancel' }}
              </span>
              <div class="flex-1">
                <div class="text-body-md font-medium">{{ c.label }}</div>
                <div class="text-caption text-on-surface-variant">{{ c.desc }}</div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  `,
})
export class SecurityComponent {
  authSettings = [
    { label: '2FA obligatoire',            desc: 'Exiger un second facteur pour tous les utilisateurs.',       enabled: true },
    { label: 'SSO Google Workspace',       desc: 'Authentification unique via Google.',                          enabled: false },
    { label: 'Verrouillage après 5 échecs', desc: 'Bloquer temporairement le compte après 5 tentatives.',        enabled: true },
    { label: 'Timeout session inactive',   desc: 'Déconnexion automatique après 30 minutes.',                    enabled: true },
    { label: 'Whitelist IP',               desc: 'Restreindre l\'accès à des plages d\'IP autorisées.',         enabled: false },
  ];

  pwdRules = [
    'Au moins une majuscule et une minuscule',
    'Au moins un chiffre',
    'Au moins un caractère spécial',
    'Interdiction des 3 derniers mots de passe utilisés',
  ];

  sessions = [
    { user: 'Awa Touré (Réception)',   device: 'Windows / Chrome',   ip: '196.13.121.42', time: 'Actif',           icon: 'computer' },
    { user: 'Bakayoko I. (Maint.)',    device: 'Android / App PMS',  ip: '196.13.121.88', time: 'Il y a 12 min',   icon: 'smartphone' },
    { user: 'Sylvie N\'Dri (Finance)', device: 'MacOS / Safari',      ip: '196.13.121.55', time: 'Actif',           icon: 'laptop_mac' },
  ];

  conformite = [
    { label: 'Chiffrement TLS 1.3',        desc: 'Toutes les communications sont chiffrées.',                    ok: true },
    { label: 'Chiffrement AES-256 au repos', desc: 'Base de données et sauvegardes chiffrées.',                  ok: true },
    { label: 'RGPD',                        desc: 'Registre des traitements et consentements en place.',         ok: true },
    { label: 'PCI-DSS',                     desc: 'Certification à renouveler (échéance dans 45 jours).',        ok: false },
    { label: 'Sauvegardes chiffrées',       desc: 'Rétention 30 jours + snapshot mensuel.',                       ok: true },
  ];
}
