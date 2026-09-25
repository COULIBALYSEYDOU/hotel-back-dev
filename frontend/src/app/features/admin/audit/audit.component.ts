import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Une ligne du journal d'audit immuable. */
interface LigneAudit {
  id: string;
  ligneClass: string;
  date: string;
  dateClass: string;
  heure: string;
  heureClass: string;
  auteur: string;
  fonction: string;
  fonctionClass: string;
  avatarClass: string;
  actionIcon?: string;
  action: string;
  actionClass: string;
  detail: string;
  moduleRef: string;
  moduleIcon: string;
  moduleClass: string;
  moduleEntite: string;
  ip: string;
  terminal: string;
  terminalIcon: string;
  etatAvant: string;
  etatAvantClass: string;
  etatApres: string;
  etatApresClass: string;
  criticite: string;
  criticiteClass: string;
  criticitePointClass: string;
  chevronClass: string;
}

/** Un compte collaborateur du répertoire. */
interface Collaborateur {
  nom: string;
  matricule: string;
  initiales: string;
  presenceClass: string;
  role: string;
  roleClass: string;
  departement: string;
  email: string;
  emailClass: string;
  mfa: string;
  mfaIcon: string;
  mfaClass: string;
  statut: string;
  statutClass: string;
  statutPointClass: string;
  delai?: string;
  poste?: string;
  posteClass: string;
  suspendu: boolean;
  ligneClass: string;
  titreRevoquer: string;
}

/**
 * Journal d'Audit Avancé & Gestion des Utilisateurs — Étoile OS.
 * Porté à l'identique depuis la maquette Stitch
 * « audit_des_actions_gestion_des_utilisateurs ».
 */
@Component({
  selector: 'app-audit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <div class="space-y-5">

        <!-- Page Header & Title Bar -->
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-3">
          <div>
            <div class="flex items-center gap-2 text-secondary text-caption font-caption uppercase tracking-wider mb-1">
              <span>Administration Centrale</span>
              <span class="material-symbols-outlined text-[12px]">chevron_right</span>
              <span>Sécurité &amp; Conformité</span>
              <span class="material-symbols-outlined text-[12px]">chevron_right</span>
              <span class="text-primary font-bold">Journal d'Audit Immuable</span>
            </div>
            <h2 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Journal d'Audit Avancé &amp; Gestion des Utilisateurs</h2>
            <p class="text-body-sm font-body-sm text-secondary mt-0.5">Traçabilité cryptographique SHA-256 à la seconde, gestion granulaire des rôles et contrôle des sessions opérationnelles.</p>
          </div>

          <div class="flex flex-wrap items-center gap-2 shrink-0">
            <!-- Security Compliance Badge -->
            <div class="flex items-center gap-2 px-3 py-1 bg-[#ecfdf5] border border-primary/30 text-primary rounded-full">
              <span class="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
              <span class="text-label-sm font-label-sm font-semibold tracking-tight">Audit Conforme ISO 27001 &amp; UEMOA</span>
            </div>

            <button type="button" (click)="onNouvelUtilisateur()"
                    class="flex items-center gap-1.5 bg-surface-container-lowest border border-outline-variant hover:border-primary text-on-surface hover:text-primary py-1.5 px-3 rounded-lg text-label-md font-label-md transition-colors active:scale-95 shadow-xs">
              <span class="material-symbols-outlined text-[18px]">person_add</span>
              <span>Nouvel Utilisateur</span>
            </button>
            <button type="button" (click)="onExporter()"
                    class="flex items-center gap-1.5 bg-primary hover:bg-primary-container text-on-primary py-1.5 px-3 rounded-lg text-label-md font-label-md transition-colors active:scale-95 shadow-xs">
              <span class="material-symbols-outlined text-[18px]">download_for_offline</span>
              <span>Exporter Journal (PDF/CSV)</span>
            </button>

            <!-- Bascule Journal / Répertoire -->
            <div class="flex items-center gap-2 bg-surface-container-lowest border border-outline-variant rounded-lg p-1">
              <button type="button" (click)="vue.set('audit')"
                      [class]="vue() === 'audit'
                        ? 'px-3 py-1.5 rounded-md text-label-md font-label-md bg-primary text-on-primary transition-all shadow-xs flex items-center gap-1.5'
                        : 'px-3 py-1.5 rounded-md text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low transition-all flex items-center gap-1.5'">
                <span class="material-symbols-outlined text-[16px]">history_edu</span>
                <span>Journal d'Audit (1 482)</span>
              </button>
              <button type="button" (click)="vue.set('users')"
                      [class]="vue() === 'users'
                        ? 'px-3 py-1.5 rounded-md text-label-md font-label-md bg-primary text-on-primary transition-all shadow-xs flex items-center gap-1.5'
                        : 'px-3 py-1.5 rounded-md text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low transition-all flex items-center gap-1.5'">
                <span class="material-symbols-outlined text-[16px]">manage_accounts</span>
                <span>Répertoire Collaborateurs (16)</span>
              </button>
            </div>
          </div>
        </div>

        <!-- ================= 4 METRICS KPIS ================= -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">

          <!-- KPI 1 -->
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline transition-colors">
            <div class="flex items-center justify-between">
              <span class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Total Utilisateurs Actifs</span>
              <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
                <span class="material-symbols-outlined text-[20px]">badge</span>
              </div>
            </div>
            <div class="mt-3">
              <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">16 <span class="text-headline-sm font-headline-sm font-normal text-secondary">Comptes</span></div>
              <div class="flex items-center gap-1.5 text-caption font-caption text-secondary mt-2">
                <span class="text-primary font-semibold flex items-center gap-0.5">
                  <span class="material-symbols-outlined text-[14px]">check_circle</span> 100%
                </span>
                <span>avec rôle nominatif strict</span>
              </div>
            </div>
          </div>

          <!-- KPI 2 -->
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline transition-colors">
            <div class="flex items-center justify-between">
              <span class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Événements Tracés (24h)</span>
              <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
                <span class="material-symbols-outlined text-[20px]">fingerprint</span>
              </div>
            </div>
            <div class="mt-3">
              <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">1 482 <span class="text-headline-sm font-headline-sm font-normal text-secondary">Actions</span></div>
              <div class="flex items-center gap-1.5 text-caption font-caption text-primary mt-2">
                <span class="material-symbols-outlined text-[14px]">verified</span>
                <span>100% horodatées &amp; immuables</span>
              </div>
            </div>
          </div>

          <!-- KPI 3 -->
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline transition-colors">
            <div class="flex items-center justify-between">
              <span class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Actions Sensibles &amp; Alertes</span>
              <div class="w-8 h-8 rounded-lg bg-amber-50 flex items-center justify-center text-amber-700">
                <span class="material-symbols-outlined text-[20px]">warning</span>
              </div>
            </div>
            <div class="mt-3">
              <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none text-amber-800">3 <span class="text-headline-sm font-headline-sm font-normal text-amber-700">Détections</span></div>
              <div class="flex items-center gap-1.5 text-caption font-caption text-secondary mt-2">
                <span class="text-amber-700 font-semibold flex items-center gap-0.5">
                  <span class="material-symbols-outlined text-[14px]">arrow_upward</span> +1
                </span>
                <span>Annulation facture &amp; passe RFID</span>
              </div>
            </div>
          </div>

          <!-- KPI 4 -->
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline transition-colors">
            <div class="flex items-center justify-between">
              <span class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Sessions Live Actives</span>
              <div class="w-8 h-8 rounded-lg bg-emerald-50 flex items-center justify-center text-primary">
                <span class="material-symbols-outlined text-[20px]">lock</span>
              </div>
            </div>
            <div class="mt-3">
              <div class="text-display-md font-display-md text-primary tracking-tight leading-none">7 <span class="text-headline-sm font-headline-sm font-normal text-secondary">En Ligne</span></div>
              <div class="flex items-center gap-1.5 text-caption font-caption text-secondary mt-2">
                <span class="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
                <span>Toutes validées par OTP 2FA</span>
              </div>
            </div>
          </div>
        </div>

        <!-- ================= SECTION A: JOURNAL D'AUDIT COMPLET ================= -->
        <section class="space-y-4" *ngIf="vue() === 'audit'">

          <!-- Filter & Search Toolbar -->
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm">
            <div class="flex flex-wrap items-center justify-between gap-3">
              <div class="flex flex-wrap items-center gap-3 flex-1">
                <div class="relative min-w-[240px] flex-1 max-w-sm">
                  <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-secondary text-[18px]">search</span>
                  <input type="text" [(ngModel)]="recherche"
                         placeholder="Filtrer par collaborateur, ID action, entité..."
                         class="w-full bg-surface-container-lowest border border-outline-variant text-on-surface text-body-sm font-body-sm rounded-lg pl-9 pr-3 py-2 focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/10 transition-all placeholder:text-secondary">
                </div>

                <div class="flex items-center gap-1.5 bg-surface-container-low border border-outline-variant/80 rounded-lg px-3 py-1.5 text-label-sm font-label-sm text-on-surface">
                  <span class="material-symbols-outlined text-secondary text-[16px]">calendar_month</span>
                  <span>Période: <strong>Aujourd'hui (Dernières 24h)</strong></span>
                  <span class="material-symbols-outlined text-secondary text-[14px]">expand_more</span>
                </div>

                <div class="flex items-center gap-1.5 bg-surface-container-low border border-outline-variant/80 rounded-lg px-3 py-1.5 text-label-sm font-label-sm text-on-surface">
                  <span class="material-symbols-outlined text-secondary text-[16px]">view_quilt</span>
                  <span>Module: <strong>Tous les modules</strong></span>
                  <span class="material-symbols-outlined text-secondary text-[14px]">expand_more</span>
                </div>

                <div class="flex items-center gap-1.5 bg-surface-container-low border border-outline-variant/80 rounded-lg px-3 py-1.5 text-label-sm font-label-sm text-on-surface">
                  <span class="material-symbols-outlined text-secondary text-[16px]">filter_alt</span>
                  <span>Criticité: <strong>Toutes</strong></span>
                  <span class="material-symbols-outlined text-secondary text-[14px]">expand_more</span>
                </div>
              </div>

              <div class="flex items-center gap-3">
                <div class="flex items-center gap-1.5 text-caption font-caption text-secondary">
                  <span class="w-2 h-2 rounded-full bg-primary animate-ping"></span>
                  <span class="font-medium text-on-surface">Flux en temps réel actif</span>
                </div>
                <button type="button" (click)="reinitialiser()" title="Réinitialiser filtres"
                        class="p-2 border border-outline-variant rounded-lg hover:bg-surface-container-low text-secondary transition-colors">
                  <span class="material-symbols-outlined text-[18px]">refresh</span>
                </button>
              </div>
            </div>
          </div>

          <!-- Grand Tableau d'Audit Détaillé -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full text-left border-collapse">
                <thead>
                  <tr class="bg-surface-container-low/70 border-b border-outline-variant text-secondary text-label-sm font-label-sm">
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Horodatage précis</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Utilisateur &amp; Rôle</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Action &amp; Détails Opérationnels</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Module &amp; Entité</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">IP &amp; Terminal</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Ancien → Nouvel État</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold text-center">Criticité</th>
                    <th class="py-3 px-3 uppercase tracking-wider font-semibold text-right">Détails</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant/60 text-body-sm font-body-sm">
                  <tr *ngFor="let l of lignesVisibles()" [class]="l.ligneClass" (click)="ouvrirTiroir(l)">

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <div [class]="l.dateClass">{{ l.date }}</div>
                      <div [class]="l.heureClass">{{ l.heure }}</div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <div class="flex items-center gap-2.5">
                        <div [class]="l.avatarClass">{{ initiales(l.auteur) }}</div>
                        <div>
                          <div class="font-label-md font-semibold text-on-surface">{{ l.auteur }}</div>
                          <div [class]="l.fonctionClass">{{ l.fonction }}</div>
                        </div>
                      </div>
                    </td>

                    <td class="py-3.5 px-4">
                      <div [class]="l.actionClass">
                        <span *ngIf="l.actionIcon" class="material-symbols-outlined text-[16px]">{{ l.actionIcon }}</span>
                        <span>{{ l.action }}</span>
                      </div>
                      <div class="text-caption text-secondary truncate max-w-xs">{{ l.detail }}</div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <span [class]="l.moduleClass">
                        <span class="material-symbols-outlined text-[16px]">{{ l.moduleIcon }}</span>
                        <span>{{ l.moduleRef }}</span>
                      </span>
                      <div class="text-caption text-secondary">{{ l.moduleEntite }}</div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <div class="font-mono text-label-sm text-on-surface">{{ l.ip }}</div>
                      <div class="text-caption text-secondary flex items-center gap-1">
                        <span class="material-symbols-outlined text-[12px]">{{ l.terminalIcon }}</span>
                        <span>{{ l.terminal }}</span>
                      </div>
                    </td>

                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-1.5 text-caption font-mono">
                        <span [class]="l.etatAvantClass">{{ l.etatAvant }}</span>
                        <span class="text-secondary">→</span>
                        <span [class]="l.etatApresClass">{{ l.etatApres }}</span>
                      </div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap text-center">
                      <span [class]="l.criticiteClass">
                        <span [class]="l.criticitePointClass"></span>
                        <span>{{ l.criticite }}</span>
                      </span>
                    </td>

                    <td class="py-3.5 px-3 whitespace-nowrap text-right">
                      <button type="button" [class]="l.chevronClass">
                        <span class="material-symbols-outlined text-[18px]">chevron_right</span>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- Table Pagination Footer -->
            <div class="px-4 py-3 bg-surface-container-low border-t border-outline-variant flex flex-wrap items-center justify-between gap-3 text-caption font-caption text-secondary">
              <div>
                Affichage de <span class="font-semibold text-on-surface">1 à {{ lignesVisibles().length }}</span> sur <span class="font-semibold text-on-surface">1 482</span> événements certifiés SHA-256
              </div>
              <div class="flex items-center gap-1">
                <button type="button" disabled class="px-2.5 py-1 border border-outline-variant rounded bg-surface-container-lowest text-secondary hover:text-on-surface disabled:opacity-40">Précédent</button>
                <button type="button" class="px-2.5 py-1 rounded bg-primary text-on-primary font-semibold">1</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded bg-surface-container-lowest text-secondary hover:text-on-surface">2</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded bg-surface-container-lowest text-secondary hover:text-on-surface">3</button>
                <span class="px-1 text-secondary">...</span>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded bg-surface-container-lowest text-secondary hover:text-on-surface">124</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded bg-surface-container-lowest text-secondary hover:text-on-surface">Suivant</button>
              </div>
            </div>
          </div>
        </section>

        <!-- ================= SECTION B: RÉPERTOIRE & GESTION DES UTILISATEURS ================= -->
        <section class="space-y-4" *ngIf="vue() === 'users'">

          <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm">
            <div>
              <h3 class="text-headline-sm font-headline-sm text-on-surface">Annuaire des Collaborateurs &amp; Privilèges d'Accès</h3>
              <p class="text-body-sm font-body-sm text-secondary">Contrôle des 16 comptes opérationnels, politiques de mots de passe et double authentification matérielle.</p>
            </div>
            <div class="flex items-center gap-2">
              <button type="button" (click)="onForcer2FA()"
                      class="flex items-center gap-1.5 bg-surface-container-low border border-outline-variant hover:bg-surface-container-high text-on-surface py-2 px-3 rounded-lg text-label-md font-label-md transition-colors">
                <span class="material-symbols-outlined text-[18px]">key</span>
                <span>Forcer Renouvellement 2FA Général</span>
              </button>
              <button type="button" (click)="onNouvelUtilisateur()"
                      class="flex items-center gap-1.5 bg-primary hover:bg-primary-container text-on-primary py-2 px-3 rounded-lg text-label-md font-label-md transition-colors shadow-xs">
                <span class="material-symbols-outlined text-[18px]">add</span>
                <span>Créer un Collaborateur</span>
              </button>
            </div>
          </div>

          <!-- Users Table -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full text-left border-collapse">
                <thead>
                  <tr class="bg-surface-container-low/70 border-b border-outline-variant text-secondary text-label-sm font-label-sm">
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Collaborateur</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Rôle &amp; Département</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Email Professionnel</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Sécurité 2FA</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold">Dernière Connexion / Statut</th>
                    <th class="py-3 px-4 uppercase tracking-wider font-semibold text-right">Actions Administrateur</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant/60 text-body-sm font-body-sm">
                  <tr *ngFor="let c of collaborateurs" [class]="c.ligneClass">

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <div class="flex items-center gap-3">
                        <div class="relative">
                          <div class="w-9 h-9 rounded-full bg-surface-container-high flex items-center justify-center text-secondary border border-outline-variant font-bold">
                            {{ c.initiales }}
                          </div>
                          <span [class]="c.presenceClass"></span>
                        </div>
                        <div>
                          <div class="font-label-md font-bold text-on-surface">{{ c.nom }}</div>
                          <div class="text-caption text-secondary">Matricule: {{ c.matricule }}</div>
                        </div>
                      </div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <span [class]="c.roleClass">{{ c.role }}</span>
                      <div class="text-caption text-secondary mt-0.5">{{ c.departement }}</div>
                    </td>

                    <td [class]="c.emailClass">{{ c.email }}</td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <span [class]="c.mfaClass">
                        <span class="material-symbols-outlined text-[16px]">{{ c.mfaIcon }}</span>
                        <span>{{ c.mfa }}</span>
                      </span>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap">
                      <div class="flex items-center gap-1.5">
                        <span [class]="c.statutPointClass"></span>
                        <span [class]="c.statutClass">{{ c.statut }}</span>
                        <span *ngIf="c.delai" class="text-caption text-secondary">{{ c.delai }}</span>
                      </div>
                      <div [class]="c.posteClass">{{ c.poste }}</div>
                    </td>

                    <td class="py-3.5 px-4 whitespace-nowrap text-right">
                      <div class="inline-flex items-center gap-1">
                        <ng-container *ngIf="!c.suspendu">
                          <button type="button" title="Modifier privilèges"
                                  class="p-1.5 rounded hover:bg-surface-container-low text-secondary hover:text-on-surface transition-colors">
                            <span class="material-symbols-outlined text-[18px]">manage_accounts</span>
                          </button>
                          <button type="button" [title]="c.titreRevoquer"
                                  class="p-1.5 rounded hover:bg-red-50 text-secondary hover:text-error transition-colors">
                            <span class="material-symbols-outlined text-[18px]">lock_reset</span>
                          </button>
                        </ng-container>
                        <button type="button" *ngIf="c.suspendu" title="Réactiver le compte"
                                class="px-2 py-1 rounded bg-surface-container-low hover:bg-surface-container-high text-label-sm font-label-sm text-primary font-semibold transition-colors">
                          Réactiver
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </section>
      </div>

      <!-- ===================================================================== -->
      <!-- DRAWER LATÉRAL DE TRAÇABILITÉ IMMUABLE                                -->
      <!-- ===================================================================== -->
      <div [class]="tiroirOuvert()
             ? 'fixed inset-y-0 right-0 w-[420px] bg-surface-container-lowest border-l border-outline-variant shadow-2xl z-50 transform transition-transform duration-200 ease-in-out flex flex-col justify-between'
             : 'fixed inset-y-0 right-0 w-[420px] bg-surface-container-lowest border-l border-outline-variant shadow-2xl z-50 transform translate-x-full transition-transform duration-200 ease-in-out flex flex-col justify-between'">

        <!-- Drawer Header -->
        <div class="p-5 border-b border-outline-variant flex items-center justify-between bg-surface-container-low/50">
          <div class="flex items-center gap-2.5">
            <div class="w-8 h-8 rounded-lg bg-primary/10 flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">verified</span>
            </div>
            <div>
              <h3 class="text-headline-sm font-headline-sm text-on-surface">Preuve d'Audit Certifiée</h3>
              <p class="text-caption font-caption text-secondary">ID: #{{ tiroirId() }}</p>
            </div>
          </div>
          <button type="button" (click)="fermerTiroir()"
                  class="p-1.5 rounded-lg hover:bg-surface-container-high text-secondary hover:text-on-surface transition-colors">
            <span class="material-symbols-outlined text-[20px]">close</span>
          </button>
        </div>

        <!-- Drawer Content -->
        <div class="p-5 overflow-y-auto space-y-5 flex-1">

          <!-- Security Stamp Badge -->
          <div class="p-3 bg-[#ecfdf5] border border-primary/20 rounded-xl space-y-1">
            <div class="flex items-center justify-between text-caption font-caption text-primary font-semibold uppercase">
              <span>Horodatage Certifié UTC+00:00</span>
              <span class="material-symbols-outlined text-[16px]">lock</span>
            </div>
            <div class="font-mono text-label-md font-bold text-on-surface">2024-10-24T20:14:08.412891Z</div>
            <div class="text-caption text-secondary">Signature vérifiée par clé matérielle HSM Étoile-Security-Node-01</div>
          </div>

          <!-- Action Card -->
          <div class="space-y-3">
            <h4 class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Détail de l'Action Traçable</h4>
            <div class="bg-surface-container-low p-3.5 rounded-xl border border-outline-variant/70 space-y-2">
              <div class="flex items-center justify-between text-body-sm">
                <span class="text-secondary">Type d'événement:</span>
                <span class="font-semibold text-primary">Encaissement / Folio Settle</span>
              </div>
              <div class="flex items-center justify-between text-body-sm">
                <span class="text-secondary">Module source:</span>
                <span class="font-semibold text-on-surface">Finance / Passerelle Wave</span>
              </div>
              <div class="flex items-center justify-between text-body-sm">
                <span class="text-secondary">Montant enregistré:</span>
                <span class="font-mono font-bold text-on-surface">185 000 FCFA</span>
              </div>
              <div class="flex items-center justify-between text-body-sm">
                <span class="text-secondary">Entité cible:</span>
                <span class="font-mono text-on-surface">Réservation #RES-98421</span>
              </div>
            </div>
          </div>

          <!-- Operator Details -->
          <div class="space-y-3">
            <h4 class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Identité de l'Opérateur</h4>
            <div class="flex items-center gap-3 p-3 bg-surface-container-lowest border border-outline-variant rounded-xl">
              <div class="w-10 h-10 rounded-full bg-surface-container-high flex items-center justify-center text-secondary border border-outline-variant font-bold">AT</div>
              <div>
                <div class="font-label-md font-bold text-on-surface">Awa Touré</div>
                <div class="text-caption text-secondary">Réceptionniste Front-Desk (Session ID #SES-88219)</div>
                <div class="text-caption font-mono text-primary font-semibold mt-0.5">2FA OTP Confirmé à 14:02</div>
              </div>
            </div>
          </div>

          <!-- Technical Context & SHA-256 Checksum -->
          <div class="space-y-3">
            <h4 class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Contexte Machine &amp; Immuabilité</h4>
            <div class="space-y-2 text-body-sm">
              <div class="bg-surface-container-low p-2.5 rounded-lg border border-outline-variant/60 font-mono text-caption text-secondary">
                <div class="text-on-surface font-semibold mb-1">Terminal &amp; Navigateur:</div>
                <div>IP: 160.154.42.12 (Orange Côte d'Ivoire Fibre)</div>
                <div>Device: POS-TERMINAL-01 / ChromeOS Embedded</div>
                <div>MAC Hash: 9e:4b:21:fa:71:08</div>
              </div>
              <div class="bg-surface-container-low p-2.5 rounded-lg border border-outline-variant/60">
                <div class="text-caption font-semibold text-secondary uppercase mb-1">Empreinte Cryptographique (SHA-256)</div>
                <div class="font-mono text-[10px] text-on-surface break-all bg-surface-container-lowest p-2 rounded border border-outline-variant">
                  e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
                </div>
                <div class="flex items-center gap-1 text-[11px] text-primary mt-1">
                  <span class="material-symbols-outlined text-[14px]">done_all</span>
                  <span>Bloc consigné dans le grand livre immuable</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Drawer Footer Actions -->
        <div class="p-4 border-t border-outline-variant bg-surface-container-low/50 space-y-2">
          <button type="button"
                  class="w-full flex items-center justify-center gap-2 bg-primary hover:bg-primary-container text-on-primary py-2.5 px-4 rounded-lg text-label-md font-label-md transition-all shadow-xs">
            <span class="material-symbols-outlined text-[18px]">print</span>
            <span>Générer le Certificat d'Audit (PDF Signé)</span>
          </button>
          <button type="button"
                  class="w-full flex items-center justify-center gap-2 bg-surface-container-lowest border border-error/30 text-error hover:bg-error-container/20 py-2 px-4 rounded-lg text-label-md font-label-md transition-all">
            <span class="material-symbols-outlined text-[18px]">lock</span>
            <span>Verrouiller cette Session à Distance</span>
          </button>
        </div>
      </div>

      <!-- Drawer Backdrop -->
      <div *ngIf="tiroirOuvert()" (click)="fermerTiroir()"
           class="fixed inset-0 bg-[#0f172a]/30 backdrop-blur-[2px] z-40 transition-opacity"></div>
    </div>
  `,
})
export class AuditComponent {
  /** Onglet courant : journal d'audit ou répertoire collaborateurs. */
  vue = signal<'audit' | 'users'>('audit');

  recherche = '';

  tiroirOuvert = signal(false);
  tiroirId = signal('AUD-94821');

  ouvrirTiroir(l: LigneAudit): void {
    this.tiroirId.set(l.id);
    this.tiroirOuvert.set(true);
  }

  fermerTiroir(): void {
    this.tiroirOuvert.set(false);
  }

  reinitialiser(): void {
    this.recherche = '';
  }

  onNouvelUtilisateur(): void {
    console.log('Nouvel utilisateur');
  }

  onExporter(): void {
    console.log('Export du journal (PDF/CSV)');
  }

  onForcer2FA(): void {
    console.log('Renouvellement 2FA général');
  }

  initiales(nom: string): string {
    return nom
      .replace(/^M\.\s*/, '')
      .split(' ')
      .filter(Boolean)
      .slice(0, 2)
      .map(p => p[0])
      .join('')
      .toUpperCase();
  }

  /* eslint-disable @typescript-eslint/member-ordering */
  private readonly avatarNeutre =
    'w-7 h-7 rounded-full object-cover border border-outline-variant bg-surface-container-high flex items-center justify-center text-caption font-bold text-secondary shrink-0';
  private readonly avatarCritique =
    'w-7 h-7 rounded-full object-cover border border-error/40 bg-surface-container-high flex items-center justify-center text-caption font-bold text-secondary shrink-0';
  private readonly ligneNormale = 'hover:bg-surface-container-low/50 transition-colors group cursor-pointer';
  private readonly chevronNormal = 'p-1 rounded text-secondary group-hover:text-primary transition-colors';
  private readonly criticiteVerte =
    'px-2 py-0.5 rounded-full text-caption font-semibold bg-[#ecfdf5] text-primary border border-primary/30 inline-flex items-center gap-1';
  private readonly pointVert = 'w-1.5 h-1.5 rounded-full bg-primary';

  lignes: LigneAudit[] = [
    {
      id: 'AUD-94821',
      ligneClass: this.ligneNormale,
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-on-surface',
      heure: '20:14:08.412', heureClass: 'font-mono text-caption text-secondary',
      auteur: 'Awa Touré', fonction: 'Réceptionniste Front-Desk',
      fonctionClass: 'text-caption text-secondary', avatarClass: this.avatarNeutre,
      action: 'Encaissement Wave Mobile Money (185 000 FCFA)',
      actionClass: 'font-semibold text-on-surface',
      detail: 'Transaction #WV-982181 • Solde hébergement Chambre 102',
      moduleRef: '#RES-98421', moduleIcon: 'payments',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-primary',
      moduleEntite: 'M. Sékou Koné',
      ip: '160.154.42.12', terminal: 'TPE Réception 01', terminalIcon: 'point_of_sale',
      etatAvant: 'Non payé', etatAvantClass: 'px-1.5 py-0.5 rounded bg-amber-50 text-amber-800 border border-amber-200',
      etatApres: 'Soldé', etatApresClass: 'px-1.5 py-0.5 rounded bg-[#ecfdf5] text-primary border border-primary/30 font-semibold',
      criticite: 'Info / Validé', criticiteClass: this.criticiteVerte, criticitePointClass: this.pointVert,
      chevronClass: this.chevronNormal,
    },
    {
      id: 'AUD-94820',
      ligneClass: this.ligneNormale,
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-on-surface',
      heure: '20:14:55.109', heureClass: 'font-mono text-caption text-secondary',
      auteur: 'Amadou Bamba', fonction: "Maître d'Hôtel - Le Jardin d'Ivoire",
      fonctionClass: 'text-caption text-secondary', avatarClass: this.avatarNeutre,
      action: "Report d'addition Restauration (48 000 FCFA)",
      actionClass: 'font-semibold text-on-surface',
      detail: 'Table 14 → Imputation Folio Principal Chambre 102',
      moduleRef: '#POS-44019', moduleIcon: 'restaurant',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-secondary',
      moduleEntite: 'Chambre 102 (S. Koné)',
      ip: '192.168.10.45', terminal: 'Tablette POS Salle 02', terminalIcon: 'tablet_android',
      etatAvant: 'Addition Ouverte', etatAvantClass: 'px-1.5 py-0.5 rounded bg-surface-container-high text-secondary',
      etatApres: 'Imputé Chambre', etatApresClass: 'px-1.5 py-0.5 rounded bg-blue-50 text-blue-700 border border-blue-200',
      criticite: 'Normal', criticiteClass: this.criticiteVerte, criticitePointClass: this.pointVert,
      chevronClass: this.chevronNormal,
    },
    {
      id: 'AUD-94819',
      ligneClass: this.ligneNormale,
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-on-surface',
      heure: '20:05:30.880', heureClass: 'font-mono text-caption text-secondary',
      auteur: 'M. Youssouf Bamba', fonction: 'Auditeur de Nuit Principal',
      fonctionClass: 'text-caption text-secondary', avatarClass: this.avatarNeutre,
      action: 'Lancement Pré-Clôture Journalière & 2 No-shows',
      actionClass: 'font-semibold text-amber-800 flex items-center gap-1',
      detail: 'Facturation 1ère nuit pénalité (#RES-98390 & #RES-98394)',
      moduleRef: '#NIGHT-AUDIT-24', moduleIcon: 'nightlight',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-secondary',
      moduleEntite: 'Clôture Date J-0',
      ip: '160.154.42.10', terminal: 'Poste Fixe Audit Nuit', terminalIcon: 'desktop_windows',
      etatAvant: 'En attente arrivée', etatAvantClass: 'px-1.5 py-0.5 rounded bg-surface-container-high text-secondary',
      etatApres: 'Marqué No-Show', etatApresClass: 'px-1.5 py-0.5 rounded bg-amber-100 text-amber-900 border border-amber-300 font-semibold',
      criticite: 'Avertissement',
      criticiteClass: 'px-2 py-0.5 rounded-full text-caption font-semibold bg-amber-50 text-amber-800 border border-amber-300 inline-flex items-center gap-1',
      criticitePointClass: 'w-1.5 h-1.5 rounded-full bg-amber-600',
      chevronClass: this.chevronNormal,
    },
    {
      id: 'AUD-94818',
      ligneClass: this.ligneNormale,
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-on-surface',
      heure: '19:42:15.002', heureClass: 'font-mono text-caption text-secondary',
      auteur: "Koffi N'Dri", fonction: 'Gouvernant Général Adjoint',
      fonctionClass: 'text-caption text-secondary', avatarClass: this.avatarNeutre,
      action: 'Inspection & Validation Chambre 102',
      actionClass: 'font-semibold text-on-surface',
      detail: 'Contrôle 42 points conforme • Suite Deluxe Émeraude',
      moduleRef: 'Chambre 102 (Étage 1)', moduleIcon: 'bed',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-secondary',
      moduleEntite: 'Check-in imminent VIP',
      ip: '192.168.12.88', terminal: 'Mobile Housekeeping Étage 1', terminalIcon: 'smartphone',
      etatAvant: 'Sale / À Nettoyer', etatAvantClass: 'px-1.5 py-0.5 rounded bg-red-50 text-red-700 border border-red-200',
      etatApres: 'Propre & Contrôlée', etatApresClass: 'px-1.5 py-0.5 rounded bg-[#ecfdf5] text-primary border border-primary/30 font-semibold',
      criticite: 'Succès', criticiteClass: this.criticiteVerte, criticitePointClass: this.pointVert,
      chevronClass: this.chevronNormal,
    },
    {
      id: 'AUD-94817',
      ligneClass: this.ligneNormale,
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-on-surface',
      heure: '18:30:12.750', heureClass: 'font-mono text-caption text-secondary',
      auteur: 'Awa Touré', fonction: 'Réceptionniste Front-Desk',
      fonctionClass: 'text-caption text-secondary', avatarClass: this.avatarNeutre,
      action: 'Émission Nouveau Passe RFID Chambre 102',
      actionClass: 'font-semibold text-on-surface',
      detail: "Encodeur Dormakaba #ENC-01 • Validité jusqu'au 26/10 à 12:00",
      moduleRef: '#KEY-RFID-8921', moduleIcon: 'vpn_key',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-secondary',
      moduleEntite: 'Chambre 102',
      ip: '160.154.42.12', terminal: 'Encodeur RFID Front 01', terminalIcon: 'sensors',
      etatAvant: 'Clé 0 active', etatAvantClass: 'px-1.5 py-0.5 rounded bg-surface-container-high text-secondary',
      etatApres: '1 Clé Client Créée', etatApresClass: 'px-1.5 py-0.5 rounded bg-emerald-50 text-primary border border-primary/30',
      criticite: 'Traçabilité', criticiteClass: this.criticiteVerte, criticitePointClass: this.pointVert,
      chevronClass: this.chevronNormal,
    },
    {
      id: 'AUD-94816',
      ligneClass: 'hover:bg-error-container/20 transition-colors group cursor-pointer bg-red-50/20',
      date: '24/10/2024', dateClass: 'font-mono text-label-sm font-semibold text-error',
      heure: '17:15:00.014', heureClass: 'font-mono text-caption text-error/80',
      auteur: "Yannick N'Guessan", fonction: 'Directeur Général (Super Admin)',
      fonctionClass: 'text-caption text-error font-medium', avatarClass: this.avatarCritique,
      actionIcon: 'security',
      action: 'Modification Plafond Remise Manuelle & Annulation',
      actionClass: 'font-bold text-error flex items-center gap-1',
      detail: 'Élévation de privilège temporaire pour audit trimestriel',
      moduleRef: '#CONFIG-SEC-09', moduleIcon: 'shield',
      moduleClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-error',
      moduleEntite: 'Paramètres Financement',
      ip: '41.202.219.8', terminal: 'VPN SSL Direction (Abidjan)', terminalIcon: 'vpn_lock',
      etatAvant: 'Max 500 000 FCFA', etatAvantClass: 'px-1.5 py-0.5 rounded bg-surface-container-high text-secondary',
      etatApres: 'Max 2 500 000 FCFA', etatApresClass: 'px-1.5 py-0.5 rounded bg-red-100 text-red-900 font-bold border border-red-300',
      criticite: 'Critique Sensible',
      criticiteClass: 'px-2 py-0.5 rounded-full text-caption font-semibold bg-red-100 text-error border border-error/30 inline-flex items-center gap-1',
      criticitePointClass: 'w-1.5 h-1.5 rounded-full bg-error animate-pulse',
      chevronClass: 'p-1 rounded text-secondary group-hover:text-error transition-colors',
    },
  ];

  /** Filtrage plein-texte réel sur le champ « Filtrer par collaborateur, ID action, entité… ». */
  lignesVisibles(): LigneAudit[] {
    const q = this.recherche.trim().toLowerCase();
    if (!q) return this.lignes;
    return this.lignes.filter(l =>
      [l.auteur, l.fonction, l.action, l.detail, l.moduleRef, l.moduleEntite, l.ip, l.terminal, l.id]
        .join(' ')
        .toLowerCase()
        .includes(q),
    );
  }

  private readonly presenceEnLigne = 'absolute bottom-0 right-0 w-2.5 h-2.5 rounded-full bg-primary ring-2 ring-surface-container-lowest';
  private readonly mfaActif = 'inline-flex items-center gap-1 text-label-sm font-semibold text-primary';
  private readonly emailActif = 'py-3.5 px-4 whitespace-nowrap font-mono text-body-sm text-on-surface';

  collaborateurs: Collaborateur[] = [
    {
      nom: "Yannick N'Guessan", matricule: '#DIR-001', initiales: 'YN',
      presenceClass: this.presenceEnLigne,
      role: 'Super Administrateur',
      roleClass: 'px-2.5 py-0.5 rounded-full text-caption font-semibold bg-purple-50 text-purple-800 border border-purple-200',
      departement: 'Direction Générale & Audit',
      email: 'yannick.nguessan@etoiledusud.ci', emailClass: this.emailActif,
      mfa: '2FA Actif (YubiKey OTP)', mfaIcon: 'verified_user', mfaClass: this.mfaActif,
      statut: 'En ligne', statutClass: 'font-semibold text-primary',
      statutPointClass: 'w-2 h-2 rounded-full bg-primary',
      delai: '(Il y a 2 min)', poste: 'Poste Bureau 301',
      posteClass: 'text-caption text-secondary font-mono',
      suspendu: false, ligneClass: 'hover:bg-surface-container-low/50 transition-colors',
      titreRevoquer: 'Révoquer session active',
    },
    {
      nom: 'Awa Touré', matricule: '#REC-014', initiales: 'AT',
      presenceClass: this.presenceEnLigne,
      role: 'Réceptionniste Front-Desk',
      roleClass: 'px-2.5 py-0.5 rounded-full text-caption font-semibold bg-[#ecfdf5] text-primary border border-primary/30',
      departement: 'Accueil, Facturation & Clés RFID',
      email: 'awa.toure@etoiledusud.ci', emailClass: this.emailActif,
      mfa: '2FA Actif (Google Auth)', mfaIcon: 'verified_user', mfaClass: this.mfaActif,
      statut: 'En ligne', statutClass: 'font-semibold text-primary',
      statutPointClass: 'w-2 h-2 rounded-full bg-primary',
      delai: '(Il y a 6 min)', poste: 'Borne Réception 01',
      posteClass: 'text-caption text-secondary font-mono',
      suspendu: false, ligneClass: 'hover:bg-surface-container-low/50 transition-colors',
      titreRevoquer: 'Révoquer session active',
    },
    {
      nom: "Koffi N'Dri", matricule: '#GOV-003', initiales: 'KN',
      presenceClass: this.presenceEnLigne,
      role: 'Gouvernant Général',
      roleClass: 'px-2.5 py-0.5 rounded-full text-caption font-semibold bg-emerald-50 text-primary border border-emerald-200',
      departement: 'Housekeeping & Contrôle Propreté',
      email: 'koffi.ndri@etoiledusud.ci', emailClass: this.emailActif,
      mfa: '2FA Actif (SMS OTP)', mfaIcon: 'verified_user', mfaClass: this.mfaActif,
      statut: 'En ligne', statutClass: 'font-semibold text-primary',
      statutPointClass: 'w-2 h-2 rounded-full bg-primary',
      delai: '(Il y a 32 min)', poste: 'Tablette Housekeeping',
      posteClass: 'text-caption text-secondary font-mono',
      suspendu: false, ligneClass: 'hover:bg-surface-container-low/50 transition-colors',
      titreRevoquer: 'Révoquer session',
    },
    {
      nom: 'M. Youssouf Bamba', matricule: '#AUD-002', initiales: 'YB',
      presenceClass: this.presenceEnLigne,
      role: 'Auditeur de Nuit',
      roleClass: 'px-2.5 py-0.5 rounded-full text-caption font-semibold bg-amber-50 text-amber-800 border border-amber-300',
      departement: 'Clôtures Fin de Journée & Main Courante',
      email: 'youssouf.bamba@etoiledusud.ci', emailClass: this.emailActif,
      mfa: '2FA Actif (Hardware Token)', mfaIcon: 'verified_user', mfaClass: this.mfaActif,
      statut: 'En ligne', statutClass: 'font-semibold text-primary',
      statutPointClass: 'w-2 h-2 rounded-full bg-primary',
      delai: '(Il y a 9 min)', poste: 'Poste Audit Nuit',
      posteClass: 'text-caption text-secondary font-mono',
      suspendu: false, ligneClass: 'hover:bg-surface-container-low/50 transition-colors',
      titreRevoquer: 'Révoquer session',
    },
    {
      nom: 'Fatou Sylla', matricule: '#CAI-008', initiales: 'FS',
      presenceClass: 'absolute bottom-0 right-0 w-2.5 h-2.5 rounded-full bg-slate-400 ring-2 ring-surface-container-lowest',
      role: 'Caissière Restaurant',
      roleClass: 'px-2.5 py-0.5 rounded-full text-caption font-semibold bg-surface-container-high text-secondary',
      departement: 'En congé sabbatique',
      email: 'fatou.sylla@etoiledusud.ci',
      emailClass: 'py-3.5 px-4 whitespace-nowrap font-mono text-body-sm text-secondary',
      mfa: 'Désactivé temporairement', mfaIcon: 'lock_clock',
      mfaClass: 'inline-flex items-center gap-1 text-label-sm font-semibold text-secondary',
      statut: 'Suspendu', statutClass: 'font-medium text-secondary',
      statutPointClass: 'w-2 h-2 rounded-full bg-slate-400',
      poste: 'Depuis le 15/09/2024', posteClass: 'text-caption text-secondary',
      suspendu: true, ligneClass: 'hover:bg-surface-container-low/50 transition-colors opacity-75',
      titreRevoquer: 'Révoquer session',
    },
  ];
}
