import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface RoleMetier {
  cle: string;
  icon: string;
  libelle: string;
}

@Component({
  selector: 'app-parametres',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <!-- Header Section -->
      <section class="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-outline-variant/60 pb-6">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Paramètres, Contrôle d'Accès &amp; Sécurité Hôtelière</h1>
            <span class="flex items-center gap-1.5 px-2.5 py-0.5 rounded-full bg-[#ecfdf5] border border-primary text-primary text-label-sm font-label-sm">
              <span class="material-symbols-outlined text-xs" data-icon="verified_user" style="font-variation-settings: 'FILL' 1;">verified_user</span>
              <span>Audit Sécurité Conforme ISO &amp; UEMOA</span>
            </span>
          </div>
          <p class="text-body-md font-body-md text-secondary mt-1">Gestion fine des permissions par métier, journal des connexions et conformité des données pour les établissements multi-services.</p>
        </div>
        <!-- Action CTAs -->
        <div class="flex items-center gap-3 self-start md:self-auto">
          <button (click)="onExporterJournal()" class="flex items-center gap-2 px-4 py-2 border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-on-surface rounded-lg text-label-md font-label-md transition-all shadow-sm">
            <span class="material-symbols-outlined text-lg" data-icon="file_download">file_download</span>
            <span>Exporter journal d'audit</span>
          </button>
          <button (click)="onSauvegarder()" class="flex items-center gap-2 px-4 py-2 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-md font-label-md transition-all shadow-sm active:scale-[0.98]">
            <span class="material-symbols-outlined text-lg" data-icon="check_circle">check_circle</span>
            <span>Sauvegarder modifications</span>
          </button>
        </div>
      </section>

      <!-- 3 Governance & Security KPI Cards -->
      <section class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <!-- Card 1: Users & Roles -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm hover:shadow transition-shadow">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary">Utilisateurs &amp; Collaborateurs</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg" data-icon="manage_accounts">manage_accounts</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface">16</span>
            <span class="text-label-md font-label-md text-primary font-semibold">comptes actifs</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span>4 Rôles configurés</span>
            <div class="flex -space-x-1">
              <span class="inline-block px-1.5 py-0.5 rounded text-[10px] font-semibold bg-surface-container text-on-surface-variant">Direction</span>
              <span class="inline-block px-1.5 py-0.5 rounded text-[10px] font-semibold bg-surface-container text-on-surface-variant">Front-Desk</span>
              <span class="inline-block px-1.5 py-0.5 rounded text-[10px] font-semibold bg-surface-container text-on-surface-variant">Housekeeping</span>
              <span class="inline-block px-1.5 py-0.5 rounded text-[10px] font-semibold bg-surface-container text-on-surface-variant">Comptabilité</span>
            </div>
          </div>
        </div>

        <!-- Card 2: 2FA MFA Status -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm hover:shadow transition-shadow">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary">Authentification Double Facteur (2FA)</span>
            <div class="w-8 h-8 rounded-lg bg-[#ecfdf5] flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg" data-icon="security" style="font-variation-settings: 'FILL' 1;">security</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface">100%</span>
            <span class="text-label-md font-label-md text-primary font-semibold">du personnel protégé</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span class="flex items-center gap-1.5">
              <span class="w-2 h-2 rounded-full bg-primary"></span>
              <span>Passerelle OTP Orange &amp; MTN active</span>
            </span>
            <span class="text-caption font-caption text-primary font-semibold">Protocole Strict</span>
          </div>
        </div>

        <!-- Card 3: Daily Audit Logs -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm hover:shadow transition-shadow">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary">Événements d'audit journaliers</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-secondary">
              <span class="material-symbols-outlined text-lg" data-icon="history_toggle_off">history_toggle_off</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface">148</span>
            <span class="text-label-md font-label-md text-secondary">actions tracées (24h)</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between text-body-sm font-body-sm">
            <span class="text-primary flex items-center gap-1">
              <span class="material-symbols-outlined text-sm" data-icon="check">check</span>
              <span>0 tentative d'accès non autorisée</span>
            </span>
            <span class="text-caption font-caption text-secondary">Chiffrement AES-256</span>
          </div>
        </div>
      </section>

      <!-- Main Dual-Pane Content Layout -->
      <section class="grid grid-cols-1 lg:grid-cols-12 gap-6">
        <!-- Left Pane: Role & Module Permission Matrix -->
        <div class="lg:col-span-8 bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm flex flex-col">
          <!-- Role Navigation Tabs -->
          <div class="border-b border-outline-variant/70 pb-4 mb-6">
            <div class="flex items-center justify-between mb-3">
              <div>
                <h2 class="text-headline-sm font-headline-sm text-on-surface">Matrice des Permissions par Métier</h2>
                <p class="text-body-sm font-body-sm text-secondary">Définissez les prérogatives d'exécution, de lecture et d'autorisation financière par module.</p>
              </div>
              <span class="text-label-sm font-label-sm px-2.5 py-1 bg-surface-container rounded-md text-on-surface-variant font-medium">Profil: Équipe Opérationnelle</span>
            </div>
            <!-- Role Selector Pills -->
            <div class="flex items-center gap-2 overflow-x-auto pb-1">
              <button *ngFor="let r of roles" (click)="onSelectionnerRole(r)" [class]="classePastille(r)">
                <span class="material-symbols-outlined text-sm" [attr.data-icon]="r.icon">{{ r.icon }}</span>
                <span>{{ r.libelle }}</span>
              </button>
            </div>
          </div>

          <!-- Module Permissions Rows -->
          <div class="space-y-4">
            <!-- Row 1: Tableau de bord -->
            <div class="p-4 rounded-lg border border-outline-variant/60 bg-surface/50 hover:bg-surface transition-colors flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3">
                <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-xl" data-icon="dashboard">dashboard</span>
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg text-on-surface">Tableau de bord de Réception</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Vue d'ensemble opérationnelle de l'établissement en temps réel.</p>
                </div>
              </div>
              <div class="flex items-center gap-3">
                <div class="text-right">
                  <span class="text-label-sm font-label-sm text-on-surface block">Consultation restreinte aux arrivées</span>
                  <span class="text-caption font-caption text-secondary">Masquage du RevPAR &amp; marge brute</span>
                </div>
                <!-- Active Toggle Switch -->
                <label class="relative inline-flex items-center cursor-pointer">
                  <input [(ngModel)]="permissions.tableauBord" class="sr-only peer" type="checkbox" />
                  <div class="w-11 h-6 bg-surface-container-highest peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>

            <!-- Row 2: Planning & Rack -->
            <div class="p-4 rounded-lg border border-outline-variant/60 bg-surface/50 hover:bg-surface transition-colors flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3">
                <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-xl" data-icon="calendar_month">calendar_month</span>
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg text-on-surface">Planning &amp; Rack Hôtelier (Gantt)</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Attribution des suites, gestion des départs tardifs et surclassements.</p>
                </div>
              </div>
              <div class="flex items-center gap-3">
                <div class="text-right">
                  <span class="text-label-sm font-label-sm text-on-surface block">Consultation &amp; Modification autorisées</span>
                  <span class="text-caption font-caption text-primary font-semibold">Changement de chambre actif</span>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input [(ngModel)]="permissions.planning" class="sr-only peer" type="checkbox" />
                  <div class="w-11 h-6 bg-surface-container-highest peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>

            <!-- Row 3: Module Finance & Encaissements -->
            <div class="p-4 rounded-lg border border-outline-variant/60 bg-surface/50 hover:bg-surface transition-colors flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3">
                <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-xl" data-icon="payments">payments</span>
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg text-on-surface">Module Finance &amp; Encaissements</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Clôture journalière de shift et passerelles Mobile Money / TPE.</p>
                </div>
              </div>
              <div class="flex items-center gap-4">
                <div class="flex flex-col items-end gap-1">
                  <div class="flex items-center gap-1.5 text-label-sm font-label-sm text-primary">
                    <span class="material-symbols-outlined text-sm" data-icon="check_circle">check_circle</span>
                    <span>Clôture de shift autorisée</span>
                  </div>
                  <div class="flex items-center gap-1.5 text-label-sm font-label-sm text-secondary">
                    <span class="material-symbols-outlined text-sm text-error" data-icon="lock">lock</span>
                    <span class="text-error font-medium">Modification des tarifs : Verrouillée</span>
                  </div>
                </div>
                <!-- Dual Switch Visual -->
                <label class="relative inline-flex items-center cursor-pointer">
                  <input [(ngModel)]="permissions.finance" class="sr-only peer" type="checkbox" />
                  <div class="w-11 h-6 bg-surface-container-highest peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>

            <!-- Row 4: Module Clients & CRM -->
            <div class="p-4 rounded-lg border border-outline-variant/60 bg-surface/50 hover:bg-surface transition-colors flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3">
                <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-xl" data-icon="group">group</span>
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg text-on-surface">Module Clients &amp; CRM VIP</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Préférences séjours, historique des nuitées et coordonnées.</p>
                </div>
              </div>
              <div class="flex items-center gap-4">
                <div class="flex flex-col items-end gap-1">
                  <div class="flex items-center gap-1.5 text-label-sm font-label-sm text-primary">
                    <span class="material-symbols-outlined text-sm" data-icon="check_circle">check_circle</span>
                    <span>Création fiche client autorisée</span>
                  </div>
                  <div class="flex items-center gap-1.5 text-label-sm font-label-sm text-secondary">
                    <span class="material-symbols-outlined text-sm text-error" data-icon="lock">lock</span>
                    <span>Export de la base : Réservé Direction</span>
                  </div>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input [(ngModel)]="permissions.crm" class="sr-only peer" type="checkbox" />
                  <div class="w-11 h-6 bg-surface-container-highest peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>

            <!-- Row 5: Télémétrie IoT & Clés mobiles -->
            <div class="p-4 rounded-lg border border-outline-variant/60 bg-surface/50 hover:bg-surface transition-colors flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3">
                <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-xl" data-icon="key">key</span>
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg text-on-surface">Télémétrie IoT &amp; Serrures Connectées</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Émission des badges RFID et passes numériques de serrures intelligentes.</p>
                </div>
              </div>
              <div class="flex items-center gap-3">
                <div class="text-right">
                  <span class="text-label-sm font-label-sm text-primary font-semibold block">Émission de clé Bluetooth autorisée</span>
                  <span class="text-caption font-caption text-secondary">Validité limitée à la durée du séjour</span>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input [(ngModel)]="permissions.iot" class="sr-only peer" type="checkbox" />
                  <div class="w-11 h-6 bg-surface-container-highest peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>
          </div>

          <!-- Bottom Notice for Security Matrix -->
          <div class="mt-6 pt-4 border-t border-outline-variant/40 flex items-center justify-between text-caption font-caption text-secondary">
            <span class="flex items-center gap-1">
              <span class="material-symbols-outlined text-sm text-primary" data-icon="info">info</span>
              Toute modification prend effet immédiatement sur les terminaux de réception et l'application mobile.
            </span>
            <button (click)="onRetablirDefaut()" class="text-primary hover:underline font-semibold text-label-sm font-label-sm">Rétablir les permissions par défaut</button>
          </div>
        </div>

        <!-- Right Pane: Live Audit Log & Session Security -->
        <div class="lg:col-span-4 space-y-6">
          <!-- Audit Feed Card -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm">
            <div class="flex items-center justify-between pb-4 border-b border-outline-variant/60">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-xl" data-icon="policy">policy</span>
                <h2 class="text-headline-sm font-headline-sm text-on-surface">Journal d'Activité Récent</h2>
              </div>
              <span class="w-2.5 h-2.5 rounded-full bg-primary animate-ping" title="Flux direct"></span>
            </div>
            <div class="mt-5 space-y-5 relative before:absolute before:inset-0 before:left-3 before:w-0.5 before:bg-surface-container-highest">
              <!-- Event 1 -->
              <div class="relative flex items-start gap-4 pl-7">
                <div class="absolute left-1.5 top-1.5 -translate-x-1/2 w-3 h-3 rounded-full border-2 border-surface-container-lowest bg-primary"></div>
                <div class="flex-1">
                  <div class="flex items-center justify-between text-caption font-caption text-secondary mb-0.5">
                    <span class="font-semibold text-on-surface">Paiement Mobile Money</span>
                    <span>Il y a 12 min</span>
                  </div>
                  <p class="text-body-sm font-body-sm text-on-surface">
                    Réceptionniste <strong class="font-semibold text-primary">Awa Touré</strong> a encaissé <span class="font-semibold">185 000 FCFA</span> via Wave Business.
                  </p>
                  <span class="inline-block mt-1 px-2 py-0.5 rounded text-[10px] bg-[#ecfdf5] text-primary border border-primary/20 font-medium">
                    Rapprochement validé #WAV-904
                  </span>
                </div>
              </div>

              <!-- Event 2 -->
              <div class="relative flex items-start gap-4 pl-7">
                <div class="absolute left-1.5 top-1.5 -translate-x-1/2 w-3 h-3 rounded-full border-2 border-surface-container-lowest bg-primary"></div>
                <div class="flex-1">
                  <div class="flex items-center justify-between text-caption font-caption text-secondary mb-0.5">
                    <span class="font-semibold text-on-surface">Mise à jour statut chambre</span>
                    <span>Il y a 34 min</span>
                  </div>
                  <p class="text-body-sm font-body-sm text-on-surface">
                    Gouvernant <strong class="font-semibold text-on-surface">Koffi N'Dri</strong> a changé le statut de la chambre <strong>102</strong> à <em class="text-primary font-medium">Propre &amp; Contrôlée</em>.
                  </p>
                  <span class="inline-block mt-1 px-2 py-0.5 rounded text-[10px] bg-surface-container text-secondary font-medium">
                    Prête pour Check-in
                  </span>
                </div>
              </div>

              <!-- Event 3 -->
              <div class="relative flex items-start gap-4 pl-7">
                <div class="absolute left-1.5 top-1.5 -translate-x-1/2 w-3 h-3 rounded-full border-2 border-surface-container-lowest bg-primary"></div>
                <div class="flex-1">
                  <div class="flex items-center justify-between text-caption font-caption text-secondary mb-0.5">
                    <span class="font-semibold text-on-surface">Gouvernance Système</span>
                    <span>Il y a 1h 15min</span>
                  </div>
                  <p class="text-body-sm font-body-sm text-on-surface">
                    <strong class="font-semibold text-on-surface">Direction Générale</strong> a validé l'activation du module <span class="font-semibold text-primary">Restauration &amp; Room Service</span>.
                  </p>
                  <span class="inline-block mt-1 px-2 py-0.5 rounded text-[10px] bg-secondary-container/40 text-on-secondary-container font-medium">
                    Extension SaaS activée
                  </span>
                </div>
              </div>

              <!-- Event 4 -->
              <div class="relative flex items-start gap-4 pl-7">
                <div class="absolute left-1.5 top-1.5 -translate-x-1/2 w-3 h-3 rounded-full border-2 border-surface-container-lowest bg-secondary"></div>
                <div class="flex-1">
                  <div class="flex items-center justify-between text-caption font-caption text-secondary mb-0.5">
                    <span class="font-semibold text-on-surface">Session Sécurisée</span>
                    <span>Il y a 2h 40min</span>
                  </div>
                  <p class="text-body-sm font-body-sm text-on-surface">
                    Connexion réussie depuis <strong>Abidjan</strong> (IP <code class="text-[11px] bg-surface-container px-1 py-0.5 rounded">160.154.x.x</code>, session chiffrée TLS 1.3).
                  </p>
                  <span class="inline-block mt-1 px-2 py-0.5 rounded text-[10px] bg-surface-container text-secondary font-medium">
                    2FA Confirmé par SMS
                  </span>
                </div>
              </div>
            </div>
            <div class="mt-6 pt-4 border-t border-outline-variant/40">
              <button (click)="onHistoriqueComplet()" class="w-full py-2 bg-surface-container hover:bg-surface-container-high text-secondary hover:text-on-surface text-label-sm font-label-sm rounded-lg transition-colors flex items-center justify-center gap-1.5">
                <span>Consulter l'historique complet (30 jours)</span>
                <span class="material-symbols-outlined text-sm" data-icon="arrow_forward">arrow_forward</span>
              </button>
            </div>
          </div>

          <!-- Session & Regional Compliance Card -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm">
            <div class="flex items-center gap-2 mb-3">
              <span class="material-symbols-outlined text-primary text-xl" data-icon="cloud_done">cloud_done</span>
              <h3 class="text-headline-sm font-headline-sm text-on-surface">Conformité UEMOA &amp; RGPD</h3>
            </div>
            <p class="text-body-sm font-body-sm text-secondary mb-4">
              Les bases de données hôtelières sont répliquées en temps réel sur des centres certifiés Tier III avec masquage automatique des coordonnées bancaires.
            </p>
            <div class="space-y-2">
              <div class="flex items-center justify-between text-body-sm font-body-sm py-1.5 border-b border-outline-variant/40">
                <span class="text-secondary">Rétention des données d'enregistrement</span>
                <span class="font-semibold text-on-surface">365 jours</span>
              </div>
              <div class="flex items-center justify-between text-body-sm font-body-sm py-1.5 border-b border-outline-variant/40">
                <span class="text-secondary">Signature électronique des fiches de police</span>
                <span class="text-primary font-semibold">Activée (e-Gov)</span>
              </div>
              <div class="flex items-center justify-between text-body-sm font-body-sm py-1.5">
                <span class="text-secondary">Certificat SSL / Datacenter</span>
                <span class="text-primary font-semibold">Valide (SHA-256)</span>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  `,
})
export class ParametresComponent {
  roles: RoleMetier[] = [
    { cle: 'reception', icon: 'support_agent', libelle: 'Réception & Front-Desk' },
    { cle: 'housekeeping', icon: 'cleaning_services', libelle: 'Housekeeping / Étages' },
    { cle: 'finance', icon: 'account_balance_wallet', libelle: 'Responsable Finance / Caisse' },
    { cle: 'gouvernante', icon: 'military_tech', libelle: 'Gouvernante Générale' },
  ];

  roleActif = signal<string>('reception');

  private readonly pastilleActive =
    'px-4 py-2 rounded-lg bg-primary text-on-primary text-label-md font-label-md font-semibold shadow-sm flex items-center gap-2';
  private readonly pastilleInactive =
    'px-4 py-2 rounded-lg bg-surface-container hover:bg-surface-container-high text-secondary hover:text-on-surface text-label-md font-label-md transition-colors flex items-center gap-2';

  classePastille(role: RoleMetier): string {
    return this.roleActif() === role.cle ? this.pastilleActive : this.pastilleInactive;
  }

  permissions = {
    tableauBord: true,
    planning: true,
    finance: true,
    crm: true,
    iot: true,
  };

  onSelectionnerRole(role: RoleMetier): void {
    this.roleActif.set(role.cle);
    console.log('[Parametres] Rôle sélectionné :', role.libelle);
  }

  onExporterJournal(): void {
    console.log("[Parametres] Export du journal d'audit");
  }

  onSauvegarder(): void {
    console.log('[Parametres] Sauvegarde des permissions', this.permissions);
  }

  onRetablirDefaut(): void {
    this.permissions = { tableauBord: true, planning: true, finance: true, crm: true, iot: true };
    console.log('[Parametres] Permissions rétablies par défaut');
  }

  onHistoriqueComplet(): void {
    console.log("[Parametres] Ouverture de l'historique complet (30 jours)");
  }
}
