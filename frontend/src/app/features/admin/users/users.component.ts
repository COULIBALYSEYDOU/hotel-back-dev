import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Un collaborateur de l'annuaire. */
interface Collaborateur {
  nom: string;
  email: string;
  matricule: string;
  role: string;
  service: string;
  /** Premier module = module principal (mis en avant). */
  modules: string[];
  taches: number;
  binomes: number;
  statut: string;
  enShift: boolean;
  mfa: boolean;
}

/** Une mission transverse de la matrice multi-tâches. */
interface Mission {
  badge: string;
  badgeIcon?: string;
  badgeClass: string;
  echeance: string;
  titre: string;
  description?: string;
  progression?: number;
  equipe: string[];
  pied: string;
  piedIcon?: string;
  piedClass: string;
}

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <div class="space-y-6">

        <!-- 3. EN-TÊTE DE PAGE : ACTIONS PRINCIPALE & SECONDAIRE -->
        <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4 pb-1">
          <div>
            <div class="flex items-center gap-2 text-caption font-caption uppercase tracking-wider text-secondary mb-1">
              <span>Administration Système</span>
              <span>•</span>
              <span class="text-primary font-semibold">Gouvernance &amp; Sécurité</span>
            </div>
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">
              Administration des Collaborateurs &amp; Délégation des Tâches
            </h1>
            <p class="text-body-md font-body-md text-secondary mt-1">
              Gouvernance stricte : Enregistrement des comptes, habilitations modules SaaS et assignation multi-tâches transversales.
            </p>
          </div>
          <div class="flex items-center gap-3 flex-none">
            <button type="button"
                    class="flex items-center gap-2 px-4 py-2.5 bg-surface-container-lowest border border-outline-variant hover:border-outline text-on-surface rounded-lg text-label-md font-label-md shadow-sm hover:bg-surface-container-low transition-all">
              <span class="material-symbols-outlined text-lg text-primary">share</span>
              <span>Créer une tâche transversale</span>
            </button>
            <button type="button"
                    class="flex items-center gap-2 px-5 py-2.5 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-md font-label-md font-semibold shadow hover:-translate-y-0.5 transition-all duration-150">
              <span class="material-symbols-outlined text-lg">person_add</span>
              <span>Enregistrer un nouvel utilisateur</span>
            </button>
          </div>
        </div>

        <!-- 4. BANNIÈRE D'ALERTE INFORMATIVE DE CONFORMITÉ -->
        <div class="bg-surface-container-lowest border-l-4 border-primary rounded-xl p-4 shadow-sm flex items-start gap-4">
          <div class="w-10 h-10 rounded-lg bg-primary/10 text-primary flex items-center justify-center flex-none mt-0.5">
            <span class="material-symbols-outlined text-xl">shield</span>
          </div>
          <div class="flex-1 flex flex-col md:flex-row md:items-center md:justify-between gap-2">
            <div>
              <span class="text-label-md font-label-md text-on-surface font-bold">Privilèges Super-Admin actifs</span>
              <p class="text-body-sm font-body-sm text-secondary mt-0.5">
                Seul le profil Administrateur est habilité à activer les modules SaaS hôteliers, provisionner des accès
                et associer plusieurs agents sur un même pool de tâches opérationnelles.
              </p>
            </div>
            <div class="flex items-center gap-2 flex-none">
              <span class="inline-flex items-center gap-1 px-2.5 py-1 bg-surface-container text-secondary text-caption font-caption rounded font-semibold border border-outline-variant/60">
                <span class="material-symbols-outlined text-xs text-primary">lock</span>
                Chiffrement AES-256
              </span>
              <button type="button" class="text-primary text-label-sm font-label-sm font-semibold hover:underline">
                Journal d'audit
              </button>
            </div>
          </div>
        </div>

        <!-- 5. 4 CARTES DE STATISTIQUES KPIS -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">

          <!-- KPI 1 -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline/80 transition-colors">
            <div class="flex items-center justify-between text-secondary">
              <span class="text-label-sm font-label-sm uppercase tracking-wide">Utilisateurs Actifs</span>
              <div class="p-2 rounded-lg bg-primary-fixed/40 text-primary">
                <span class="material-symbols-outlined text-lg">group</span>
              </div>
            </div>
            <div class="my-3">
              <div class="text-display-md font-display-md text-on-surface">{{ totalUtilisateurs }}</div>
              <p class="text-body-sm font-body-sm text-secondary mt-1">
                Répartis sur <strong class="text-on-surface font-semibold">{{ nbServices }} services clés</strong>
              </p>
            </div>
            <div class="flex items-center gap-1.5 text-caption font-caption text-primary font-medium">
              <span class="material-symbols-outlined text-sm">check_circle</span>
              <span>100% profils vérifiés &amp; identifiés</span>
            </div>
          </div>

          <!-- KPI 2 -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline/80 transition-colors">
            <div class="flex items-center justify-between text-secondary">
              <span class="text-label-sm font-label-sm uppercase tracking-wide">Tâches en Cours / Multi-assignées</span>
              <div class="p-2 rounded-lg bg-secondary-container/60 text-secondary">
                <span class="material-symbols-outlined text-lg">assignment</span>
              </div>
            </div>
            <div class="my-3">
              <div class="text-display-md font-display-md text-on-surface">{{ tachesEnCours }}</div>
              <p class="text-body-sm font-body-sm text-secondary mt-1">
                <strong class="text-primary font-semibold">{{ tachesBinomes }} en binômes</strong> ou équipes mixtes
              </p>
            </div>
            <div class="flex items-center gap-1.5 text-caption font-caption text-secondary font-medium">
              <span class="material-symbols-outlined text-sm text-primary">group_add</span>
              <span>Délégation inter-services active</span>
            </div>
          </div>

          <!-- KPI 3 -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline/80 transition-colors">
            <div class="flex items-center justify-between text-secondary">
              <span class="text-label-sm font-label-sm uppercase tracking-wide">Modules SaaS Activés</span>
              <div class="p-2 rounded-lg bg-surface-container-high text-primary">
                <span class="material-symbols-outlined text-lg">widgets</span>
              </div>
            </div>
            <div class="my-3 flex items-baseline gap-2">
              <div class="text-display-md font-display-md text-on-surface">
                {{ modulesActifs }}
                <span class="text-headline-sm font-headline-sm text-secondary font-normal">/ {{ modulesTotal }}</span>
              </div>
            </div>
            <div class="w-full bg-surface-container rounded-full h-1.5 overflow-hidden">
              <div class="bg-primary h-1.5 rounded-full" [style.width.%]="pourcentModules()"></div>
            </div>
            <div class="flex justify-between items-center text-caption font-caption text-secondary mt-2">
              <span>Déployés à l'Hôtel</span>
              <span class="font-bold text-on-surface">{{ pourcentModulesArrondi() }}% du catalogue</span>
            </div>
          </div>

          <!-- KPI 4 -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between hover:border-outline/80 transition-colors">
            <div class="flex items-center justify-between text-secondary">
              <span class="text-label-sm font-label-sm uppercase tracking-wide">Audit &amp; Contrôle d'Accès</span>
              <div class="p-2 rounded-lg bg-primary-fixed/40 text-primary">
                <span class="material-symbols-outlined text-lg">history_toggle_off</span>
              </div>
            </div>
            <div class="my-3">
              <div class="text-display-md font-display-md text-primary">100%</div>
              <p class="text-body-sm font-body-sm text-secondary mt-1">Actions horodatées &amp; traçables</p>
            </div>
            <div class="flex items-center gap-1.5 text-caption font-caption text-secondary font-medium">
              <span class="material-symbols-outlined text-sm text-primary">fingerprint</span>
              <span>Rattachement ID matriculaire unique</span>
            </div>
          </div>
        </div>

        <!-- 6. SECTION PRINCIPALE : 2 GRANDS BLOCS (BENTO DOCK) -->
        <div class="grid grid-cols-1 xl:grid-cols-12 gap-6 items-start">

          <!-- BLOC A (8 colonnes) : ANNUAIRE DES UTILISATEURS & DROITS -->
          <div class="xl:col-span-8 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden flex flex-col">

            <div class="p-5 border-b border-outline-variant flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
              <div>
                <h2 class="text-headline-sm font-headline-sm text-on-surface">
                  Annuaire des Utilisateurs &amp; Attribution des Rôles &amp; Modules
                </h2>
                <p class="text-body-sm font-body-sm text-secondary">
                  Gérez les habilitations et autorisations fonctionnelles des {{ totalUtilisateurs }} membres d'équipe.
                </p>
              </div>
              <div class="flex items-center gap-2">
                <div class="relative">
                  <select [(ngModel)]="serviceFiltre"
                          class="h-9 pl-3 pr-8 text-label-sm font-label-sm bg-surface-container-low border border-outline-variant rounded-lg text-on-surface focus:ring-1 focus:ring-primary focus:border-primary">
                    <option value="">Tous les Services ({{ nbServices }})</option>
                    <option *ngFor="let s of servicesFiltrables" [value]="s">{{ s }}</option>
                  </select>
                </div>
                <button type="button"
                        class="h-9 px-3 bg-surface-container-low hover:bg-surface-container border border-outline-variant rounded-lg text-label-sm font-label-sm text-secondary flex items-center gap-1.5 transition-colors">
                  <span class="material-symbols-outlined text-base">filter_list</span>
                  <span>Filtres</span>
                </button>
              </div>
            </div>

            <div class="overflow-x-auto">
              <table class="w-full text-left border-collapse">
                <thead>
                  <tr class="bg-surface-container-low border-b border-outline-variant text-label-sm font-label-sm text-secondary">
                    <th class="py-3 px-4 font-semibold">Collaborateur</th>
                    <th class="py-3 px-4 font-semibold">Rôle &amp; Service</th>
                    <th class="py-3 px-4 font-semibold">Modules SaaS Autorisés</th>
                    <th class="py-3 px-4 font-semibold">Tâches Actives</th>
                    <th class="py-3 px-4 font-semibold">Statut Compte</th>
                    <th class="py-3 px-4 font-semibold text-right">Actions</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant/60 text-body-sm font-body-sm">
                  <tr *ngFor="let c of visibles()" class="hover:bg-surface-container-low/60 transition-colors">

                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div class="w-10 h-10 rounded-full border border-outline-variant bg-primary-fixed/50 text-on-primary-fixed flex items-center justify-center text-label-sm font-label-sm font-bold flex-none">
                          {{ initiales(c.nom) }}
                        </div>
                        <div>
                          <div class="text-label-md font-label-md font-bold text-on-surface">{{ c.nom }}</div>
                          <div class="text-caption font-caption text-secondary">{{ c.email }}</div>
                          <span class="inline-block mt-0.5 px-1.5 rounded text-[10px] font-mono bg-surface-container text-on-surface-variant font-medium">
                            {{ c.matricule }}
                          </span>
                        </div>
                      </div>
                    </td>

                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">{{ c.role }}</div>
                      <div class="text-caption font-caption text-secondary">{{ c.service }}</div>
                    </td>

                    <td class="py-3.5 px-4">
                      <div class="flex flex-wrap gap-1 max-w-[200px]">
                        <span *ngFor="let m of c.modules; let premier = first"
                              [class]="premier
                                ? 'px-2 py-0.5 rounded text-caption font-caption bg-primary-fixed/50 text-on-primary-fixed font-semibold border border-primary/20'
                                : 'px-2 py-0.5 rounded text-caption font-caption bg-surface-container-high text-on-surface-variant'">
                          {{ m }}
                        </span>
                      </div>
                    </td>

                    <td class="py-3.5 px-4">
                      <button type="button"
                              class="inline-flex items-center gap-1.5 px-2.5 py-1 bg-surface-container hover:bg-surface-container-high border border-outline-variant/70 rounded-full text-label-sm font-label-sm text-on-surface transition-colors">
                        <span [class]="c.enShift ? 'w-2 h-2 rounded-full bg-primary' : 'w-2 h-2 rounded-full bg-secondary'"></span>
                        <span>{{ c.taches }} {{ c.taches > 1 ? 'tâches' : 'tâche' }}</span>
                        <span class="text-caption font-caption text-secondary">
                          ({{ c.binomes > 1 ? c.binomes + ' binômes' : 'binôme' }})
                        </span>
                      </button>
                    </td>

                    <td class="py-3.5 px-4">
                      <div class="flex flex-col gap-1">
                        <span [class]="c.enShift
                                ? 'inline-flex items-center gap-1 text-label-sm font-label-sm font-semibold text-primary'
                                : 'inline-flex items-center gap-1 text-label-sm font-label-sm font-semibold text-secondary'">
                          <span [class]="c.enShift ? 'w-1.5 h-1.5 rounded-full bg-primary' : 'w-1.5 h-1.5 rounded-full bg-secondary'"></span>
                          {{ c.statut }}
                        </span>
                        <span *ngIf="c.mfa" class="text-caption font-caption text-secondary flex items-center gap-0.5">
                          <span class="material-symbols-outlined text-xs text-primary">verified</span> 2FA Activé
                        </span>
                      </div>
                    </td>

                    <td class="py-3.5 px-4 text-right">
                      <div class="flex items-center justify-end gap-1">
                        <button type="button" title="Éditer droits"
                                class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded transition-colors">
                          <span class="material-symbols-outlined text-lg">admin_panel_settings</span>
                        </button>
                        <button type="button" title="Assigner tâche"
                                class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded transition-colors">
                          <span class="material-symbols-outlined text-lg">add_task</span>
                        </button>
                        <button type="button" title="Options avancées"
                                class="p-1.5 text-secondary hover:text-error hover:bg-error-container/40 rounded transition-colors">
                          <span class="material-symbols-outlined text-lg">more_vert</span>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="p-4 border-t border-outline-variant bg-surface-container-lowest flex items-center justify-between text-body-sm font-body-sm text-secondary">
              <span>
                Affichage de <strong>{{ visibles().length }}</strong> sur <strong>{{ totalUtilisateurs }}</strong> collaborateurs enregistrés
              </span>
              <div class="flex items-center gap-1">
                <button type="button" disabled
                        class="px-2.5 py-1 border border-outline-variant rounded hover:bg-surface-container disabled:opacity-50 text-label-sm font-label-sm">
                  Précédent
                </button>
                <button type="button" class="px-2.5 py-1 bg-primary text-on-primary rounded text-label-sm font-label-sm font-semibold">1</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded hover:bg-surface-container text-label-sm font-label-sm">2</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded hover:bg-surface-container text-label-sm font-label-sm">3</button>
                <button type="button" class="px-2.5 py-1 border border-outline-variant rounded hover:bg-surface-container text-label-sm font-label-sm">Suivant</button>
              </div>
            </div>
          </div>

          <!-- BLOC B (4 colonnes) : MATRICE D'AFFECTATION MULTI-TÂCHES -->
          <div class="xl:col-span-4 space-y-6">

            <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm p-5 flex flex-col">
              <div class="flex items-center justify-between pb-3 border-b border-outline-variant">
                <div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface">Matrice Multi-Tâches</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Délégation transverse &amp; inter-services</p>
                </div>
                <button type="button" title="Ajouter une mission"
                        class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary hover:bg-primary/10 transition-colors">
                  <span class="material-symbols-outlined text-lg">add</span>
                </button>
              </div>

              <div class="mt-4 space-y-4">
                <div *ngFor="let t of missions"
                     class="p-3.5 rounded-lg bg-surface-container-low border border-outline-variant hover:border-primary/50 transition-colors">

                  <div class="flex items-start justify-between gap-2">
                    <span [class]="t.badgeClass">
                      <span *ngIf="t.badgeIcon" class="material-symbols-outlined text-xs">{{ t.badgeIcon }}</span>
                      {{ t.badge }}
                    </span>
                    <span class="text-caption font-caption text-secondary font-medium">{{ t.echeance }}</span>
                  </div>

                  <h4 class="text-label-lg font-label-lg text-on-surface mt-2 font-bold leading-snug">{{ t.titre }}</h4>

                  <div *ngIf="t.description" class="mt-2 text-caption font-caption text-secondary">{{ t.description }}</div>

                  <div *ngIf="t.progression !== undefined" class="mt-3">
                    <div class="flex justify-between text-caption font-caption text-secondary mb-1">
                      <span>Progression collective</span>
                      <span class="font-bold text-on-surface">{{ t.progression }}%</span>
                    </div>
                    <div class="w-full bg-surface-container rounded-full h-1.5 overflow-hidden">
                      <div class="bg-primary h-1.5 rounded-full" [style.width.%]="t.progression"></div>
                    </div>
                  </div>

                  <div class="mt-3.5 pt-3 border-t border-outline-variant/60 flex items-center justify-between">
                    <div class="flex items-center -space-x-2 overflow-hidden">
                      <span *ngFor="let p of t.equipe" [title]="p"
                            class="inline-flex h-7 w-7 rounded-full ring-2 ring-surface-container-lowest bg-primary-fixed/60 text-on-primary-fixed items-center justify-center text-[10px] font-bold">
                        {{ initiales(p) }}
                      </span>
                      <button type="button" title="Ajouter collaborateur"
                              class="h-7 w-7 rounded-full bg-surface-container border border-dashed border-outline flex items-center justify-center text-primary text-caption font-caption hover:bg-primary-fixed/40 transition-colors">
                        <span class="material-symbols-outlined text-sm">add</span>
                      </button>
                    </div>
                    <span [class]="t.piedClass">
                      <span *ngIf="t.piedIcon" class="material-symbols-outlined text-xs align-middle">{{ t.piedIcon }}</span>
                      {{ t.pied }}
                    </span>
                  </div>
                </div>
              </div>

              <button type="button"
                      class="mt-4 w-full py-2 bg-surface-container hover:bg-surface-container-high text-primary rounded-lg text-label-sm font-label-sm font-semibold transition-colors flex items-center justify-center gap-2">
                <span class="material-symbols-outlined text-base">hub</span>
                <span>Ouvrir l'organigramme multi-équipes</span>
              </button>
            </div>

            <!-- 7. ENCART GOUVERNANCE DES MODULES SAAS -->
            <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm">
              <div class="flex items-center gap-3 mb-3">
                <div class="p-2 rounded-lg bg-primary text-on-primary">
                  <span class="material-symbols-outlined text-lg">toggle_on</span>
                </div>
                <div>
                  <h4 class="text-headline-sm font-headline-sm text-on-surface">Gouvernance des Modules</h4>
                  <p class="text-caption font-caption text-secondary">Impact instantané sur les terminaux</p>
                </div>
              </div>
              <p class="text-body-sm font-body-sm text-secondary leading-relaxed">
                L'activation ou la restriction d'un module par l'administrateur reconfigure immédiatement la barre de
                navigation et les prérogatives des utilisateurs sans déconnexion forcée.
              </p>
              <div class="mt-4 pt-3 border-t border-outline-variant space-y-2.5">
                <div *ngFor="let g of gouvernance" class="flex items-center justify-between">
                  <span class="text-label-sm font-label-sm text-on-surface">{{ g.nom }}</span>
                  <button type="button" (click)="basculerGouvernance(g)"
                          [class]="g.actif
                            ? 'inline-flex items-center text-label-sm font-label-sm text-primary font-semibold'
                            : 'inline-flex items-center text-label-sm font-label-sm text-secondary'">
                    <span class="material-symbols-outlined text-xl">{{ g.actif ? 'toggle_on' : 'toggle_off' }}</span>
                    {{ g.actif ? 'Activé' : 'En veille' }}
                  </button>
                </div>
              </div>
              <div class="mt-4 bg-surface-container-low p-2.5 rounded-lg flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Dernière modification globale :</span>
                <strong class="text-on-surface">Il y a 14 min par M. N'Guessan</strong>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  `,
})
export class UsersComponent {

  // ---- KPIs (données de gouvernance, cf. maquette) ----
  readonly totalUtilisateurs = 38;
  readonly nbServices = 7;
  readonly tachesEnCours = 24;
  readonly tachesBinomes = 14;
  readonly modulesActifs = 11;
  readonly modulesTotal = 13;

  pourcentModules = computed(() => (this.modulesActifs / this.modulesTotal) * 100);
  pourcentModulesArrondi = computed(() => Math.round(this.pourcentModules()));

  // ---- Annuaire ----
  readonly servicesFiltrables = [
    'Hébergement & Réception',
    'Gouvernance & Étages',
    'Maintenance & Technique',
    'Finance & Audit',
  ];

  private _serviceFiltre = signal('');
  get serviceFiltre(): string { return this._serviceFiltre(); }
  set serviceFiltre(v: string) { this._serviceFiltre.set(v); }

  collaborateurs = signal<Collaborateur[]>([
    {
      nom: 'Aminata Diallo',
      email: 'a.diallo@etoile-sud.ci',
      matricule: 'MAT-0492',
      role: 'Chef de Réception',
      service: 'Hébergement & Front-Desk',
      modules: ['Réservations', 'Planning', 'Clients & CRM'],
      taches: 3, binomes: 2,
      statut: 'En Shift', enShift: true, mfa: true,
    },
    {
      nom: 'Mariam Touré',
      email: 'm.toure@etoile-sud.ci',
      matricule: 'MAT-0318',
      role: 'Gouvernante Générale',
      service: 'Housekeeping & Propreté',
      modules: ['Housekeeping', 'Planning', 'Maintenance'],
      taches: 4, binomes: 3,
      statut: 'En Shift', enShift: true, mfa: true,
    },
    {
      nom: 'Ibrahim Fofana',
      email: 'i.fofana@etoile-sud.ci',
      matricule: 'MAT-0524',
      role: 'Superviseur Maintenance',
      service: 'Technique, SAV & HVAC',
      modules: ['Maintenance', 'Planning'],
      taches: 2, binomes: 2,
      statut: 'Actif', enShift: true, mfa: true,
    },
    {
      nom: 'Youssouf Bamba',
      email: 'y.bamba@etoile-sud.ci',
      matricule: 'MAT-0175',
      role: 'Night Auditor / Finance',
      service: 'Contrôle & Clôtures',
      modules: ['Finance', 'Réservations', 'Reporting'],
      taches: 1, binomes: 1,
      statut: 'Hors Shift (Nuit)', enShift: false, mfa: true,
    },
  ]);

  /** Rattache chaque collaborateur à l'un des services filtrables de la maquette. */
  private readonly rattachement: Record<string, string> = {
    'MAT-0492': 'Hébergement & Réception',
    'MAT-0318': 'Gouvernance & Étages',
    'MAT-0524': 'Maintenance & Technique',
    'MAT-0175': 'Finance & Audit',
  };

  visibles = computed(() => {
    const filtre = this._serviceFiltre();
    if (!filtre) return this.collaborateurs();
    return this.collaborateurs().filter(c => this.rattachement[c.matricule] === filtre);
  });

  // ---- Matrice multi-tâches ----
  readonly missions: Mission[] = [
    {
      badge: 'Priorité Haute VIP',
      badgeIcon: 'star',
      badgeClass: 'inline-flex items-center gap-1 text-[11px] font-semibold uppercase tracking-wider px-2 py-0.5 rounded text-primary bg-primary-fixed/40',
      echeance: "Aujourd'hui, 16h30",
      titre: 'Préparation Arrivée Délégation Diplomatique (Suites 301 à 305)',
      progression: 75,
      equipe: ['Sékou Koné', 'Mariam Touré', 'Paul Kouassi'],
      pied: '3 services unis',
      piedClass: 'text-caption font-caption text-secondary font-medium',
    },
    {
      badge: 'Réglementaire UEMOA',
      badgeClass: 'inline-flex items-center gap-1 text-[11px] font-semibold uppercase tracking-wider px-2 py-0.5 rounded text-secondary bg-surface-container',
      echeance: 'J-1 Clôture',
      titre: 'Contrôle Clôture Fiscale & Liasses UEMOA',
      description: 'Audits croisés des factures nuit & réconciliations cartes FCFA.',
      equipe: ['Youssouf Bamba', 'Aïcha Traoré'],
      pied: 'Prêt',
      piedIcon: 'check',
      piedClass: 'text-caption font-caption text-primary font-semibold inline-flex items-center gap-1',
    },
    {
      badge: 'Maintenance Critique',
      badgeClass: 'inline-flex items-center gap-1 text-[11px] font-semibold uppercase tracking-wider px-2 py-0.5 rounded text-error bg-error-container/40',
      echeance: "Aujourd'hui",
      titre: 'Inspection Système Climatisation VRV - Aile Sud',
      description: 'Test compresseurs & étanchéité fluide frigorigène R410A.',
      equipe: ['Ibrahim Fofana', 'Koffi Serge'],
      pied: 'Binôme dédié',
      piedClass: 'text-caption font-caption text-secondary',
    },
  ];

  // ---- Gouvernance des modules ----
  gouvernance = [
    { nom: 'Moteur Web & OTAs Sync', actif: true },
    { nom: 'Banquet & Traiteur Extérieur', actif: false },
  ];

  basculerGouvernance(g: { nom: string; actif: boolean }) {
    g.actif = !g.actif;
  }

  initiales(nom: string): string {
    return nom.split(' ').filter(Boolean).slice(0, 2).map(p => p[0]).join('').toUpperCase();
  }
}
