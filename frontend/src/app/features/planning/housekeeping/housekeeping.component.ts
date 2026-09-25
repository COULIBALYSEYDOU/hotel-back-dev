import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

/** Pastille IoT affichée en pied de carte chambre (RFID, climatisation…). */
interface PuceIot {
  icon?: string;
  label: string;
  puceClass: string;
  labelClass: string;
}

/** Une carte du rack « Rack en direct (50 Chambres) ». */
interface CarteChambre {
  numero: string;
  carteClass: string;
  barreClass: string;
  numeroClass: string;
  /** Icône « check_circle » pleine, présente uniquement sur la 102. */
  coche: boolean;
  /** Pastille VIP verte, présente uniquement sur la 202. */
  vip: boolean;
  categorie: string;
  statut: string;
  statutClass: string;

  blocClass: string;
  ligne1Class: string;
  ligne1Gauche: string;
  ligne1GaucheClass: string;
  ligne1Droite: string;
  ligne1DroiteClass: string;
  ligne2Icon: string;
  ligne2IconClass: string;
  ligne2: string;

  piedGaucheClass: string;
  puces: PuceIot[];
  /** Le pied de carte se termine soit par un libellé d'état, soit par un bouton. */
  actionBouton: boolean;
  actionLabel: string;
  actionClass: string;
}

/** Une pastille de la matrice synthétique des 50 unités. */
interface CelluleRack {
  numero: string;
  celluleClass: string;
  titre: string;
}

/** Un événement du journal des interventions du jour. */
interface Intervention {
  pastilleClass: string;
  pointClass: string;
  titre: string;
  detailAvant: string;
  detailFort: string;
  detailApres: string;
}

/** Une gouvernante en faction (mini-carte de droite). */
interface Gouvernante {
  initiales: string;
  avatarClass: string;
  nom: string;
  fonction: string;
  etat: string;
  etatClass: string;
}

/** Un module domotique supervisé (serrure, clim, IPTV, WiFi). */
interface ModuleIot {
  icon: string;
  iconClass: string;
  titre: string;
  etat: string;
  etatClass: string;
}

/**
 * Parc des Chambres & Housekeeping — porté à l'identique depuis la maquette
 * Stitch « gestion_des_chambres_housekeeping » (bloc <main>, lignes 217-920).
 *
 * Le shell (sidebar + topbar) reste celui validé précédemment : seule la zone
 * de travail est reprise ici.
 */
@Component({
  selector: 'app-housekeeping',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="contents">
      <div class="flex gap-8">

        <!-- Central Stage: KPI Counters, Control Toolbar, 50-Room Rack Grid -->
        <div class="flex-1 flex flex-col gap-6">

          <!-- Module Header & KPI Counters -->
          <div class="bg-surface-container-lowest p-6 rounded-xl border border-outline-variant shadow-sm flex flex-col xl:flex-row xl:items-center justify-between gap-4">
            <div>
              <div class="flex items-center gap-2 text-body-sm font-body-sm text-secondary">
                <span class="">Hôtel Étoile du Sud</span>
                <span class="">/</span>
                <span class="text-primary font-medium">Gestion Opérationnelle</span>
              </div>
              <h1 class="text-headline-lg font-headline-lg text-on-surface font-bold mt-1 tracking-tight">Parc des Chambres &amp; Housekeeping</h1>
              <p class="text-body-sm font-body-sm text-secondary mt-0.5">Supervision en direct des 50 clés, statut domotique IoT, dispatching des équipes ménage et maintenance technique.</p>
            </div>
            <!-- Badges Compteurs Résumés -->
            <div class="flex flex-wrap items-center gap-2.5">
              <!-- 28 Prêtes & Propres -->
              <div class="flex items-center gap-2.5 bg-surface-container-lowest border border-[#059669]/30 px-3.5 py-2 rounded-lg shadow-sm">
                <span class="w-2.5 h-2.5 rounded-full bg-[#059669]"></span>
                <div class="flex flex-col">
                  <span class="text-label-sm font-label-sm text-[#059669] uppercase font-bold">28 Prêtes &amp; Propres</span>
                  <span class="text-caption font-caption text-secondary">Inspection validée</span>
                </div>
              </div>
              <!-- 16 Occupées -->
              <div class="flex items-center gap-2.5 bg-surface-container-lowest border border-secondary-container px-3.5 py-2 rounded-lg shadow-sm">
                <span class="w-2.5 h-2.5 rounded-full bg-secondary"></span>
                <div class="flex flex-col">
                  <span class="text-label-sm font-label-sm text-secondary uppercase font-bold">16 Occupées</span>
                  <span class="text-caption font-caption text-secondary">Clients sur place</span>
                </div>
              </div>
              <!-- 4 En nettoyage ménage -->
              <div class="flex items-center gap-2.5 bg-surface-container-lowest border border-amber-300 px-3.5 py-2 rounded-lg shadow-sm">
                <span class="w-2.5 h-2.5 rounded-full bg-amber-500 animate-pulse"></span>
                <div class="flex flex-col">
                  <span class="text-label-sm font-label-sm text-amber-700 uppercase font-bold">4 En nettoyage</span>
                  <span class="text-caption font-caption text-secondary">Tournée en cours</span>
                </div>
              </div>
              <!-- 2 En maintenance SAV -->
              <div class="flex items-center gap-2.5 bg-surface-container-lowest border border-error/30 px-3.5 py-2 rounded-lg shadow-sm">
                <span class="w-2.5 h-2.5 rounded-full bg-error"></span>
                <div class="flex flex-col">
                  <span class="text-label-sm font-label-sm text-error uppercase font-bold">2 En maintenance</span>
                  <span class="text-caption font-caption text-secondary">Ch. 102 réactivée</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Control Toolbar: Floors, Categories, Layout toggle, CTAs -->
          <div class="bg-surface-container-lowest p-3.5 rounded-xl border border-outline-variant shadow-sm flex flex-wrap items-center justify-between gap-3">
            <!-- Floor Pills -->
            <div class="flex items-center gap-1.5 overflow-x-auto">
              <button type="button" (click)="etage.set('tous')"
                      class="px-3 py-1.5 rounded-lg text-label-md font-label-md bg-primary text-on-primary transition-colors cursor-pointer">Tous les étages (50)</button>
              <button type="button" (click)="etage.set('rdc')"
                      class="px-3 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:bg-surface-container-low transition-colors cursor-pointer">RDC (12)</button>
              <button type="button" (click)="etage.set('1')"
                      class="px-3 py-1.5 rounded-lg text-label-md font-label-md bg-surface-container-low text-on-surface font-semibold hover:bg-surface-container transition-colors cursor-pointer flex items-center gap-1.5">
                <span class="">1er Étage Aile Lagune (16)</span>
                <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              </button>
              <button type="button" (click)="etage.set('2')"
                      class="px-3 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:bg-surface-container-low transition-colors cursor-pointer">2e Étage (14)</button>
              <button type="button" (click)="etage.set('suites')"
                      class="px-3 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:bg-surface-container-low transition-colors cursor-pointer">Suites Belvédère (8)</button>
            </div>
            <!-- Right Tool Filters & Action Buttons -->
            <div class="flex items-center gap-3">
              <!-- Room category filter -->
              <select class="h-9 px-3 text-label-md font-label-md rounded-lg border border-outline-variant bg-surface-container-lowest text-on-surface focus:border-primary focus:ring-1 focus:ring-primary outline-none cursor-pointer">
                <option>Toutes catégories (Standard, Deluxe, Suite)</option>
                <option>Standard King (24)</option>
                <option>Deluxe Vue Lagune (18)</option>
                <option>Suites Prestige (8)</option>
              </select>
              <!-- View Mode Toggle -->
              <div class="flex items-center bg-surface-container-low p-1 rounded-lg border border-outline-variant/60">
                <button type="button" (click)="vue.set('grille')"
                        class="p-1.5 bg-surface-container-lowest text-primary rounded shadow-xs cursor-pointer" title="Vue Grille">
                  <span class="material-symbols-outlined text-[18px]">grid_view</span>
                </button>
                <button type="button" (click)="vue.set('liste')"
                        class="p-1.5 text-secondary hover:text-on-surface rounded cursor-pointer" title="Vue Liste détaillée">
                  <span class="material-symbols-outlined text-[18px]">view_list</span>
                </button>
              </div>
              <div class="h-6 w-px bg-outline-variant"></div>
              <!-- Quick Action CTAs -->
              <button type="button" (click)="onAffecterTournee()"
                      class="h-9 px-3.5 rounded-lg border border-outline-variant text-on-surface hover:bg-surface-container-low text-label-md font-label-md flex items-center gap-2 transition-colors cursor-pointer">
                <span class="material-symbols-outlined text-[18px] text-secondary">checklist</span>
                <span class="">Affecter tournée gouvernante</span>
              </button>
              <button type="button" (click)="onNouveauTicketSav()"
                      class="h-9 px-3.5 rounded-lg bg-surface-container-high hover:bg-surface-container text-on-surface border border-outline-variant text-label-md font-label-md flex items-center gap-2 transition-colors cursor-pointer">
                <span class="material-symbols-outlined text-[18px] text-primary">handyman</span>
                <span class="">Nouveau Ticket SAV</span>
              </button>
            </div>
          </div>

          <!-- 50-Room Rack Grid Section -->
          <div class="bg-surface-container-lowest p-6 rounded-xl border border-outline-variant shadow-sm flex flex-col gap-5">
            <div class="flex items-center justify-between pb-3 border-b border-outline-variant/60">
              <div class="flex items-center gap-2.5">
                <span class="text-headline-sm font-headline-sm text-on-surface font-bold">Rack en direct (50 Chambres)</span>
                <span class="px-2.5 py-1 rounded-full text-caption font-caption bg-surface-container text-secondary font-semibold">Taux d'occupation : 68%</span>
              </div>
              <div class="flex items-center gap-5 text-caption font-caption text-secondary">
                <div class="flex items-center gap-1.5"><span class="w-2.5 h-2.5 rounded-sm bg-[#059669]"></span> Prête</div>
                <div class="flex items-center gap-1.5"><span class="w-2.5 h-2.5 rounded-sm bg-secondary"></span> Occupée</div>
                <div class="flex items-center gap-1.5"><span class="w-2.5 h-2.5 rounded-sm bg-amber-500"></span> Nettoyage</div>
                <div class="flex items-center gap-1.5"><span class="w-2.5 h-2.5 rounded-sm bg-error"></span> Maintenance SAV</div>
              </div>
            </div>

            <!-- Dynamic Rack Cards Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-5">
              <div *ngFor="let c of chambres" [class]="c.carteClass">
                <div [class]="c.barreClass"></div>
                <div class="flex flex-col gap-3">
                  <div class="flex items-center justify-between">
                    <div>
                      <div class="flex items-center gap-2" *ngIf="c.coche">
                        <span [class]="c.numeroClass">{{ c.numero }}</span>
                        <span class="material-symbols-outlined text-[18px] text-[#059669]" style="font-variation-settings: 'FILL' 1;">check_circle</span>
                      </div>
                      <div class="flex items-center gap-1.5" *ngIf="c.vip">
                        <span [class]="c.numeroClass">{{ c.numero }}</span>
                        <span class="w-2 h-2 rounded-full bg-primary" title="Client VIP"></span>
                      </div>
                      <span [class]="c.numeroClass" *ngIf="!c.coche && !c.vip">{{ c.numero }}</span>
                      <p class="text-caption font-caption text-secondary mt-0.5">{{ c.categorie }}</p>
                    </div>
                    <span [class]="c.statutClass">{{ c.statut }}</span>
                  </div>
                  <div [class]="c.blocClass">
                    <div [class]="c.ligne1Class">
                      <span [class]="c.ligne1GaucheClass">{{ c.ligne1Gauche }}</span>
                      <span [class]="c.ligne1DroiteClass">{{ c.ligne1Droite }}</span>
                    </div>
                    <div class="flex items-center gap-2 text-caption font-caption text-secondary">
                      <span [class]="c.ligne2IconClass">{{ c.ligne2Icon }}</span>
                      <span class="">{{ c.ligne2 }}</span>
                    </div>
                  </div>
                </div>
                <!-- IoT Footprint & Actions -->
                <div class="pt-3 border-t border-outline-variant/40 flex items-center justify-between text-caption font-caption text-secondary">
                  <div [class]="c.piedGaucheClass">
                    <div *ngFor="let p of c.puces" [class]="p.puceClass">
                      <span class="material-symbols-outlined text-[16px]" *ngIf="p.icon">{{ p.icon }}</span>
                      <span [class]="p.labelClass">{{ p.label }}</span>
                    </div>
                  </div>
                  <button type="button" *ngIf="c.actionBouton" [class]="c.actionClass" (click)="onActionChambre(c)">{{ c.actionLabel }}</button>
                  <span *ngIf="!c.actionBouton" [class]="c.actionClass">{{ c.actionLabel }}</span>
                </div>
              </div>
            </div>

            <!-- Quick 50-Room Mini Matrix Selector -->
            <div class="mt-4 pt-4 border-t border-outline-variant/60">
              <div class="flex items-center justify-between mb-2">
                <span class="text-label-md font-label-md text-secondary">Vue synthétique complète des 50 unités hôtelières</span>
                <span class="text-caption font-caption text-secondary">Sélection rapide par pastille</span>
              </div>
              <div class="grid grid-cols-10 sm:grid-cols-25 gap-1.5">
                <button type="button" *ngFor="let m of matrice" [class]="m.celluleClass" [title]="m.titre"
                        (click)="chambreActive.set(m.numero)">{{ m.numero }}</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Side Inspection & SAV Quick Inspector Drawer (Chambre 102 focus) -->
        <aside class="w-96 flex flex-col gap-6 flex-shrink-0">

          <!-- Chambre 102 Quick Detail Card -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm flex flex-col gap-5">
            <!-- Header Inspection Status -->
            <div class="flex items-start justify-between">
              <div>
                <div class="flex items-center gap-2.5">
                  <h2 class="text-display-md font-display-md text-on-surface font-bold">Ch. 102</h2>
                  <span class="px-3 py-1 rounded text-label-sm font-label-sm font-bold bg-[#ecfdf5] text-[#059669] border border-[#059669]/30">Prête</span>
                </div>
                <p class="text-body-sm font-body-sm text-secondary mt-1">Deluxe Vue Lagune • 1er Étage Aile Sud</p>
              </div>
              <button type="button" (click)="onOptionsChambre()"
                      class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors cursor-pointer" title="Options de chambre">
                <span class="material-symbols-outlined text-[20px]">more_vert</span>
              </button>
            </div>

            <!-- Room Visual Preview -->
            <div class="relative rounded-xl overflow-hidden border border-outline-variant h-40">
              <img *ngIf="photoOk()" class="w-full h-full object-cover" [alt]="photoAlt" [src]="photoUrl" (error)="photoOk.set(false)" />
              <div *ngIf="!photoOk()"
                   class="w-full h-full object-cover bg-gradient-to-br from-primary/20 via-surface-container to-secondary-container flex items-center justify-center">
                <span class="material-symbols-outlined text-[40px] text-primary/50">bed</span>
              </div>
              <div class="absolute bottom-2.5 left-2.5 bg-inverse-surface/85 backdrop-blur-xs text-inverse-on-surface text-caption font-caption px-2.5 py-1 rounded flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[13px] text-primary-fixed">verified</span>
                <span class="">Contrôle qualité certifié 5 étoiles</span>
              </div>
            </div>

            <!-- Assigned Guest Section -->
            <div class="p-4 bg-surface-container-low rounded-xl border border-outline-variant/60 flex flex-col gap-3">
              <div class="flex items-center justify-between">
                <span class="text-label-md font-label-md text-secondary font-medium">Client assigné</span>
                <span class="px-2.5 py-0.5 rounded text-caption font-caption font-bold bg-primary/10 text-primary">VIP Gold</span>
              </div>
              <div class="flex items-center gap-3.5">
                <div class="w-10 h-10 rounded-full bg-primary-container text-on-primary-container font-bold flex items-center justify-center font-headline-sm text-headline-sm shadow-xs">
                  SK
                </div>
                <div>
                  <p class="text-label-lg font-label-lg font-bold text-on-surface">M. Sékou Koné</p>
                  <p class="text-caption font-caption text-secondary">Arrivée estimée : <span class="font-bold text-primary">Aujourd'hui 16:30</span></p>
                </div>
              </div>
              <div class="pt-2.5 border-t border-outline-variant/50 flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Séjour : 3 nuits (15-18 Nov)</span>
                <span class="font-semibold text-on-surface">Navette aéroport confirmée</span>
              </div>
            </div>

            <!-- IoT & Building Automation Modules -->
            <div class="flex flex-col gap-3">
              <h3 class="text-label-md font-label-md text-on-surface font-semibold">Supervision Domotique &amp; Équipements</h3>
              <div class="grid grid-cols-2 gap-2.5 text-caption font-caption">
                <div *ngFor="let m of modules"
                     class="p-3 rounded-lg border border-outline-variant bg-surface-container-lowest flex items-center gap-2.5 shadow-xs">
                  <span [class]="m.iconClass">{{ m.icon }}</span>
                  <div>
                    <p class="font-bold text-on-surface">{{ m.titre }}</p>
                    <p [class]="m.etatClass">{{ m.etat }}</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- Interventions & Housekeeping History -->
            <div class="flex flex-col gap-3">
              <div class="flex items-center justify-between">
                <h3 class="text-label-md font-label-md text-on-surface font-semibold">Journal des interventions du jour</h3>
                <span class="text-caption font-caption text-secondary">3 événements</span>
              </div>
              <div class="space-y-3.5 relative before:absolute before:left-2 before:top-2 before:bottom-2 before:w-0.5 before:bg-outline-variant/60">
                <div *ngFor="let i of interventions" class="flex items-start gap-3 pl-5 relative">
                  <span [class]="i.pastilleClass">
                    <span [class]="i.pointClass"></span>
                  </span>
                  <div>
                    <p class="text-caption font-caption font-bold text-on-surface">{{ i.titre }}</p>
                    <p class="text-caption font-caption text-secondary">{{ i.detailAvant }}<span class="font-semibold text-on-surface">{{ i.detailFort }}</span>{{ i.detailApres }}</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- Panel Action CTAs -->
            <div class="flex flex-col gap-2.5 pt-2 border-t border-outline-variant/40">
              <button type="button" (click)="onFicheTechnique()"
                      class="w-full py-2.5 px-4 bg-primary hover:bg-primary-container text-on-primary rounded-lg font-label-md text-label-md transition-all shadow-sm flex items-center justify-center gap-2 cursor-pointer active:scale-[0.98]">
                <span class="material-symbols-outlined text-[18px]">build_circle</span>
                <span class="">Voir fiche technique &amp; SAV</span>
              </button>
              <div class="grid grid-cols-2 gap-2.5">
                <button type="button" (click)="onForcerRecouche()"
                        class="py-2.5 px-3 border border-outline-variant hover:bg-surface-container-low text-on-surface rounded-lg font-label-md text-label-md transition-colors flex items-center justify-center gap-1.5 cursor-pointer">
                  <span class="material-symbols-outlined text-[16px] text-secondary">cleaning_services</span>
                  <span class="">Forcer Recouche</span>
                </button>
                <button type="button" (click)="onGenererCle()"
                        class="py-2.5 px-3 border border-outline-variant hover:bg-surface-container-low text-on-surface rounded-lg font-label-md text-label-md transition-colors flex items-center justify-center gap-1.5 cursor-pointer">
                  <span class="material-symbols-outlined text-[16px] text-secondary">vpn_key</span>
                  <span class="">Générer Clé IoT</span>
                </button>
              </div>
            </div>
          </div>

          <!-- Quick Shift Mini-Card (Équipe en service) -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm flex flex-col gap-3.5">
            <div class="flex items-center justify-between">
              <span class="text-label-md font-label-md text-on-surface font-semibold">Gouvernantes en faction</span>
              <span class="text-caption font-caption text-primary font-bold">Étage 1 (Actif)</span>
            </div>
            <div class="space-y-2.5">
              <div *ngFor="let g of gouvernantes"
                   class="flex items-center justify-between text-body-sm font-body-sm p-2.5 rounded-lg bg-surface-container-low">
                <div class="flex items-center gap-2.5">
                  <div [class]="g.avatarClass">{{ g.initiales }}</div>
                  <div>
                    <p class="text-label-md font-label-md text-on-surface font-semibold">{{ g.nom }}</p>
                    <p class="text-caption font-caption text-secondary">{{ g.fonction }}</p>
                  </div>
                </div>
                <span [class]="g.etatClass">{{ g.etat }}</span>
              </div>
            </div>
          </div>
        </aside>
      </div>
    </div>
  `,
})
export class HousekeepingComponent {
  etage = signal<'tous' | 'rdc' | '1' | '2' | 'suites'>('tous');
  vue = signal<'grille' | 'liste'>('grille');
  chambreActive = signal('102');
  photoOk = signal(true);

  readonly photoUrl =
    'https://lh3.googleusercontent.com/aida-public/AB6AXuAfLAyrK59578qC2wzyTO-v2xwPptn18ffj1_Tv2YVof6BHnqt07fNKcoXqlQNHa5vQCR0sF7OO_Ehwru5GnbwKKUjMubuWu_IA_PLkXHoRDSObVaUaRhph1VWzzsiPLBJkkcKHrc1cW1Y4lkLU1d1whfmSejxISqKR9Im_IQl9kcUEeFAOdRUjhJrFLuRG8dHNkLJ1VV_Jz0zt0b6lMUySPQckhJyMDaf6-EvQ2dcc0-VwVWK4PBuG';
  readonly photoAlt =
    "Une élégante suite d'hôtel contemporaine avec vue sur une lagune tropicale apaisante. " +
    'Les draps blancs immaculés sont parfaitement tirés et repassés, ornés de coussins vert émeraude ' +
    "et d'un plaid ardoise. La lumière dorée de l'après-midi pénètre par de grandes baies vitrées " +
    "donnant sur une végétation luxuriante et un plan d'eau calme, inspirant calme, hospitalité " +
    'haut de gamme et rigueur hôtelière.';

  /* eslint-disable @typescript-eslint/member-ordering */

  // --- Classes partagées, reprises telles quelles de la maquette ----------
  private readonly carteOccupee =
    'group relative bg-surface-container-lowest rounded-xl border border-outline-variant hover:border-secondary hover:shadow-md transition-all p-5 pl-5 flex flex-col justify-between gap-4 overflow-hidden';
  private readonly cartePrete =
    'group relative bg-surface-container-lowest rounded-xl border border-outline-variant hover:border-[#059669] hover:shadow-md transition-all p-5 pl-5 flex flex-col justify-between gap-4 overflow-hidden';
  private readonly carteMenage =
    'group relative bg-surface-container-lowest rounded-xl border border-outline-variant hover:border-amber-400 hover:shadow-md transition-all p-5 pl-5 flex flex-col justify-between gap-4 overflow-hidden';
  private readonly carteSav =
    'group relative bg-surface-container-lowest rounded-xl border border-outline-variant hover:border-error hover:shadow-md transition-all p-5 pl-5 flex flex-col justify-between gap-4 overflow-hidden';
  private readonly carteSelectionnee =
    'group relative bg-surface-container-lowest rounded-xl border-2 border-primary shadow-md p-5 pl-5 flex flex-col justify-between gap-4 overflow-hidden ring-2 ring-primary/15';

  private readonly barreVerte = 'absolute left-0 top-0 bottom-0 w-1.5 bg-[#059669]';
  private readonly barreGrise = 'absolute left-0 top-0 bottom-0 w-1.5 bg-secondary';
  private readonly barreAmbre = 'absolute left-0 top-0 bottom-0 w-1.5 bg-amber-500';
  private readonly barreRouge = 'absolute left-0 top-0 bottom-0 w-1.5 bg-error';

  private readonly numeroNormal = 'text-display-md font-display-md font-bold text-on-surface tracking-tight';
  private readonly numeroPrimaire = 'text-display-md font-display-md font-bold text-primary tracking-tight';
  private readonly numeroErreur = 'text-display-md font-display-md font-bold text-error tracking-tight';

  private readonly statutOccupee = 'px-2.5 py-1 rounded text-caption font-caption font-semibold bg-surface-container-low text-secondary';
  private readonly statutPrete = 'px-2.5 py-1 rounded text-caption font-caption font-semibold bg-[#ecfdf5] text-[#059669]';
  private readonly statutReactivee = 'px-2.5 py-1 rounded text-caption font-caption font-bold bg-[#ecfdf5] text-[#059669] border border-[#059669]/20';
  private readonly statutAmbre = 'px-2.5 py-1 rounded text-caption font-caption font-bold bg-[#fffbeb] text-amber-700 border border-amber-300';
  private readonly statutSav = 'px-2.5 py-1 rounded text-caption font-caption font-bold bg-error-container text-error';

  private readonly blocNeutre = 'bg-surface-container-low/60 rounded-lg p-3 text-body-sm font-body-sm flex flex-col gap-2';
  private readonly blocSelection = 'bg-surface-container-low rounded-lg p-3 text-body-sm font-body-sm border border-outline-variant/60 flex flex-col gap-2';
  private readonly blocAmbre = 'bg-amber-50/50 rounded-lg p-3 text-body-sm font-body-sm flex flex-col gap-2';
  private readonly blocRouge = 'mt-0.5 bg-red-50/60 rounded-lg p-3 text-body-sm font-body-sm flex flex-col gap-2';

  private readonly ligneGras = 'flex items-center justify-between text-body-sm font-body-sm text-on-surface font-semibold';
  private readonly ligneMedium = 'flex items-center justify-between text-body-sm font-body-sm text-on-surface font-medium';
  private readonly ligneErreur = 'flex items-center justify-between text-caption font-caption text-error font-semibold';

  private readonly iconeNeutre = 'material-symbols-outlined text-[15px]';
  private readonly pointPrimaire = 'w-1.5 h-1.5 rounded-full bg-primary';

  private readonly piedGap25 = 'flex items-center gap-2.5';
  private readonly piedGap3 = 'flex items-center gap-3';

  private readonly puceVerte = 'flex items-center gap-1 text-[#059669] font-medium bg-[#ecfdf5] px-2 py-1 rounded border border-[#059669]/20';

  chambres: CarteChambre[] = [
    {
      numero: '101', carteClass: this.carteOccupee, barreClass: this.barreGrise,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Deluxe Vue Lagune • Étage 1',
      statut: 'Occupée', statutClass: this.statutOccupee,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'Mme C. Dupont', ligne1GaucheClass: 'truncate',
      ligne1Droite: "Jusqu'au 18/11", ligne1DroiteClass: 'text-secondary text-caption font-normal',
      ligne2Icon: 'person', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Gouvernante assignée : Mariam T.',
      piedGaucheClass: this.piedGap3,
      puces: [
        { icon: 'lock', label: 'RFID 94%', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '21.0°C', puceClass: 'flex items-center gap-1 text-primary font-medium bg-primary/10 px-2 py-1 rounded', labelClass: '' },
      ],
      actionBouton: false, actionLabel: 'Non dérangé',
      actionClass: 'text-caption font-caption text-secondary/80 font-medium px-2 py-1 bg-surface-container rounded',
    },
    {
      numero: '102', carteClass: this.carteSelectionnee, barreClass: this.barreVerte,
      numeroClass: this.numeroPrimaire, coche: true, vip: false,
      categorie: 'Deluxe Vue Lagune • Étage 1',
      statut: 'Prête (Réactivée)', statutClass: this.statutReactivee,
      blocClass: this.blocSelection, ligne1Class: this.ligneGras,
      ligne1Gauche: 'M. Sékou Koné (VIP)', ligne1GaucheClass: 'text-primary font-bold truncate',
      ligne1Droite: 'Arrivée 16:30', ligne1DroiteClass: 'text-primary bg-primary/10 px-2 py-0.5 rounded text-caption font-bold',
      ligne2Icon: 'verified_user', ligne2IconClass: 'material-symbols-outlined text-[15px] text-primary',
      ligne2: 'Inspectée : Adèle B. (14:15)',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'EnOcéan OK', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '19.5°C', puceClass: this.puceVerte, labelClass: '' },
        { label: 'IoT OK', puceClass: 'text-[11px] bg-secondary-fixed text-on-secondary-fixed font-bold px-1.5 py-0.5 rounded', labelClass: '' },
      ],
      actionBouton: false, actionLabel: 'Actif au panneau →',
      actionClass: 'text-caption font-caption font-bold text-primary flex items-center gap-1 hover:underline cursor-pointer',
    },
    {
      numero: '103', carteClass: this.carteMenage, barreClass: this.barreAmbre,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Standard King • Étage 1',
      statut: 'Ménage (45m)', statutClass: this.statutAmbre,
      blocClass: this.blocAmbre, ligne1Class: this.ligneMedium,
      ligne1Gauche: 'Départ 11:30 effectué', ligne1GaucheClass: '',
      ligne1Droite: 'Recouche', ligne1DroiteClass: 'text-amber-700 font-bold bg-amber-100/60 px-2 py-0.5 rounded text-caption',
      ligne2Icon: 'cleaning_services', ligne2IconClass: 'material-symbols-outlined text-[15px] text-amber-600',
      ligne2: 'En charge : Fatou D.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock_open', label: 'Déverrouillée', puceClass: 'flex items-center gap-2 text-secondary bg-surface-container-low px-2 py-1 rounded', labelClass: '' },
      ],
      actionBouton: true, actionLabel: 'Déclarer propre',
      actionClass: 'px-3 py-1.5 rounded-lg bg-primary/10 hover:bg-primary text-primary hover:text-on-primary font-semibold text-caption font-caption transition-all cursor-pointer',
    },
    {
      numero: '104', carteClass: this.carteSav, barreClass: this.barreRouge,
      numeroClass: this.numeroErreur, coche: false, vip: false,
      categorie: 'Standard King • Étage 1',
      statut: 'SAV #409', statutClass: this.statutSav,
      blocClass: this.blocRouge, ligne1Class: this.ligneErreur,
      ligne1Gauche: 'Robinetterie thermostatique', ligne1GaucheClass: 'text-body-sm font-semibold',
      ligne1Droite: 'Bloquée', ligne1DroiteClass: 'bg-error/10 px-2 py-0.5 rounded font-bold',
      ligne2Icon: 'build', ligne2IconClass: 'material-symbols-outlined text-[15px] text-error',
      ligne2: 'Technicien SAV : Paul K.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'warning', label: 'Alerte vanne', puceClass: 'flex items-center gap-1 text-error bg-error-container/40 px-2 py-1 rounded', labelClass: 'font-medium' },
      ],
      actionBouton: false, actionLabel: 'Fin prévue 17h00',
      actionClass: 'text-caption font-caption text-error font-semibold bg-error/10 px-2.5 py-1 rounded',
    },
    {
      numero: '105', carteClass: this.cartePrete, barreClass: this.barreVerte,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Deluxe Vue Lagune • Étage 1',
      statut: 'Prête', statutClass: this.statutPrete,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'M. Alain Bernard', ligne1GaucheClass: 'truncate',
      ligne1Droite: 'Arrivée 18:00', ligne1DroiteClass: 'text-secondary text-caption font-normal',
      ligne2Icon: 'person', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Gouv: Mariam T.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'Prête', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '20.0°C', puceClass: this.puceVerte, labelClass: '' },
      ],
      actionBouton: true, actionLabel: 'Check-in direct',
      actionClass: 'px-3 py-1.5 rounded-lg bg-primary text-on-primary hover:bg-primary-container font-semibold text-caption font-caption transition-all cursor-pointer shadow-xs',
    },
    {
      numero: '106', carteClass: this.carteOccupee, barreClass: this.barreGrise,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Standard King • Étage 1',
      statut: 'Occupée', statutClass: this.statutOccupee,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'Dr. M. Touré', ligne1GaucheClass: 'truncate',
      ligne1Droite: "Jusqu'au 19/11", ligne1DroiteClass: 'text-secondary text-caption font-normal',
      ligne2Icon: 'person', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Gouvernante : Adèle B.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'Verrouillée', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: 'Eco 22°C', puceClass: 'flex items-center gap-1 text-secondary font-medium bg-surface-container px-2 py-1 rounded', labelClass: '' },
      ],
      actionBouton: false, actionLabel: 'Présent',
      actionClass: 'text-caption font-caption text-secondary font-medium px-2 py-1 bg-surface-container rounded',
    },
    {
      numero: '107', carteClass: this.cartePrete, barreClass: this.barreVerte,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Deluxe Vue Lagune • Étage 1',
      statut: 'Prête', statutClass: this.statutPrete,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'Disponible attribution', ligne1GaucheClass: 'truncate',
      ligne1Droite: 'Libre', ligne1DroiteClass: 'text-[#059669] font-bold bg-[#ecfdf5] px-2 py-0.5 rounded text-caption',
      ligne2Icon: 'clean_hands', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Désinfectée 11:20',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'Prête', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '20.5°C', puceClass: 'flex items-center gap-1 text-primary font-medium bg-primary/10 px-2 py-1 rounded', labelClass: '' },
      ],
      actionBouton: true, actionLabel: 'Attribuer',
      actionClass: 'px-3.5 py-1.5 rounded-lg border border-primary text-primary hover:bg-primary hover:text-on-primary font-semibold text-caption font-caption transition-all cursor-pointer',
    },
    {
      numero: '108', carteClass: this.carteMenage, barreClass: this.barreAmbre,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Suite Prestige • Étage 1',
      statut: 'À inspecter', statutClass: this.statutAmbre,
      blocClass: this.blocAmbre, ligne1Class: this.ligneMedium,
      ligne1Gauche: 'Ménage terminé', ligne1GaucheClass: '',
      ligne1Droite: 'En attente', ligne1DroiteClass: 'text-secondary bg-surface-container px-2 py-0.5 rounded text-caption',
      ligne2Icon: 'person_check', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Gouvernante : Adèle B.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'Verrouillée', puceClass: this.puceVerte, labelClass: '' },
      ],
      actionBouton: true, actionLabel: 'Valider inspection',
      actionClass: 'px-3 py-1.5 rounded-lg bg-amber-500 hover:bg-amber-600 text-white font-semibold text-caption font-caption transition-all cursor-pointer shadow-xs',
    },
    {
      numero: '201', carteClass: this.cartePrete, barreClass: this.barreVerte,
      numeroClass: this.numeroNormal, coche: false, vip: false,
      categorie: 'Standard King • Étage 2',
      statut: 'Prête', statutClass: this.statutPrete,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'M. Jean Roche', ligne1GaucheClass: 'truncate',
      ligne1Droite: 'Arrivée 20:00', ligne1DroiteClass: 'text-secondary text-caption font-normal',
      ligne2Icon: 'person', ligne2IconClass: this.iconeNeutre,
      ligne2: 'Gouv: Fatou D.',
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'Prête', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '21°C', puceClass: this.puceVerte, labelClass: '' },
      ],
      actionBouton: false, actionLabel: 'Clé mobile prête',
      actionClass: 'text-caption font-caption text-[#059669] font-semibold bg-[#ecfdf5] px-2.5 py-1 rounded border border-[#059669]/30',
    },
    {
      numero: '202', carteClass: this.carteOccupee, barreClass: this.barreGrise,
      numeroClass: this.numeroNormal, coche: false, vip: true,
      categorie: 'Deluxe Vue Lagune • Étage 2',
      statut: 'Occupée', statutClass: this.statutOccupee,
      blocClass: this.blocNeutre, ligne1Class: this.ligneGras,
      ligne1Gauche: 'Son Excellence Amb. Diallo', ligne1GaucheClass: 'truncate font-bold text-on-surface',
      ligne1Droite: 'VIP 1', ligne1DroiteClass: 'text-primary font-bold bg-primary/10 px-2 py-0.5 rounded text-caption',
      ligne2Icon: 'room_service', ligne2IconClass: this.iconeNeutre,
      ligne2: "Service d'étage 12h30",
      piedGaucheClass: this.piedGap25,
      puces: [
        { icon: 'lock', label: 'RFID OK', puceClass: this.puceVerte, labelClass: '' },
        { icon: 'mode_fan', label: '20.0°C', puceClass: this.puceVerte, labelClass: '' },
      ],
      actionBouton: false, actionLabel: 'Check-out 21/11',
      actionClass: 'text-caption font-caption text-secondary font-medium px-2 py-1 bg-surface-container rounded',
    },
  ];

  // --- Matrice synthétique des 50 unités ---------------------------------
  private readonly celluleOccupee = 'h-6 rounded text-caption font-caption font-bold bg-secondary text-on-secondary hover:opacity-80 transition';
  private readonly cellulePrete = 'h-6 rounded text-caption font-caption font-bold bg-[#059669] text-white hover:opacity-80 transition';
  private readonly celluleMenage = 'h-6 rounded text-caption font-caption font-bold bg-amber-500 text-white hover:opacity-80 transition';
  private readonly celluleSav = 'h-6 rounded text-caption font-caption font-bold bg-error text-white hover:opacity-80 transition';
  private readonly celluleSelection = 'h-6 rounded text-caption font-caption font-bold bg-[#059669] text-white ring-2 ring-primary ring-offset-1';
  private readonly celluleOccupeeFixe = 'h-6 rounded text-caption font-caption font-bold bg-secondary text-on-secondary';
  private readonly cellulePreteFixe = 'h-6 rounded text-caption font-caption font-bold bg-[#059669] text-white';
  private readonly celluleMenageFixe = 'h-6 rounded text-caption font-caption font-bold bg-amber-500 text-white';
  private readonly celluleSavFixe = 'h-6 rounded text-caption font-caption font-bold bg-error text-white';

  matrice: CelluleRack[] = [
    // 101 à 110
    { numero: '101', celluleClass: this.celluleOccupee, titre: '101 - Occupée' },
    { numero: '102', celluleClass: this.celluleSelection, titre: '102 - Prête (Sélectionnée)' },
    { numero: '103', celluleClass: this.celluleMenage, titre: '103 - Ménage' },
    { numero: '104', celluleClass: this.celluleSav, titre: '104 - SAV #409' },
    { numero: '105', celluleClass: this.cellulePrete, titre: '105 - Prête' },
    { numero: '106', celluleClass: this.celluleOccupee, titre: '106 - Occupée' },
    { numero: '107', celluleClass: this.cellulePrete, titre: '107 - Prête' },
    { numero: '108', celluleClass: this.celluleMenage, titre: '108 - À inspecter' },
    { numero: '109', celluleClass: this.cellulePrete, titre: '109 - Prête' },
    { numero: '110', celluleClass: this.celluleOccupee, titre: '110 - Occupée' },
    // 111 à 120
    { numero: '111', celluleClass: this.celluleOccupeeFixe, titre: '111 - Occupée' },
    { numero: '112', celluleClass: this.cellulePreteFixe, titre: '112 - Prête' },
    { numero: '113', celluleClass: this.cellulePreteFixe, titre: '113 - Prête' },
    { numero: '114', celluleClass: this.celluleOccupeeFixe, titre: '114 - Occupée' },
    { numero: '115', celluleClass: this.celluleOccupeeFixe, titre: '115 - Occupée' },
    { numero: '116', celluleClass: this.cellulePreteFixe, titre: '116 - Prête' },
    { numero: '117', celluleClass: this.celluleMenageFixe, titre: '117 - Ménage' },
    { numero: '118', celluleClass: this.cellulePreteFixe, titre: '118 - Prête' },
    { numero: '119', celluleClass: this.celluleOccupeeFixe, titre: '119 - Occupée' },
    { numero: '120', celluleClass: this.cellulePreteFixe, titre: '120 - Prête' },
    // 201 à 205
    { numero: '201', celluleClass: this.cellulePreteFixe, titre: '201 - Prête' },
    { numero: '202', celluleClass: this.celluleOccupeeFixe, titre: '202 - Occupée VIP' },
    { numero: '203', celluleClass: this.celluleOccupeeFixe, titre: '203 - Occupée' },
    { numero: '204', celluleClass: this.cellulePreteFixe, titre: '204 - Prête' },
    { numero: '205', celluleClass: this.celluleSavFixe, titre: '205 - SAV Électrique' },
    // 26 à 50 (sans title dans la maquette)
    { numero: '206', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '207', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '208', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '209', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '210', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '211', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '212', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '213', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '214', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '301', celluleClass: this.celluleMenageFixe, titre: '' },
    { numero: '302', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '303', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '304', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '305', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '306', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '307', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '308', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '401', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '402', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '403', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '404', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '405', celluleClass: this.celluleOccupeeFixe, titre: '' },
    { numero: '406', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '407', celluleClass: this.cellulePreteFixe, titre: '' },
    { numero: '408', celluleClass: this.cellulePreteFixe, titre: '' },
  ];

  modules: ModuleIot[] = [
    {
      icon: 'door_sensor', iconClass: 'material-symbols-outlined text-[20px] text-[#059669]',
      titre: 'Serrure RFID', etat: 'Verrouillée (94%)', etatClass: 'text-[#059669]',
    },
    {
      icon: 'thermostat', iconClass: 'material-symbols-outlined text-[20px] text-primary',
      titre: 'Clim Carrier', etat: 'Régulée à 19.5°C', etatClass: 'text-primary',
    },
    {
      icon: 'tv', iconClass: 'material-symbols-outlined text-[20px] text-secondary',
      titre: 'IPTV RoomOS', etat: 'Veille / Message prêt', etatClass: 'text-secondary',
    },
    {
      icon: 'wifi', iconClass: 'material-symbols-outlined text-[20px] text-[#059669]',
      titre: 'Borne AP WiFi', etat: 'Signal Optimal', etatClass: 'text-[#059669]',
    },
  ];

  interventions: Intervention[] = [
    {
      pastilleClass: 'absolute left-0 top-1 w-4 h-4 rounded-full bg-[#ecfdf5] border-2 border-[#059669] flex items-center justify-center',
      pointClass: 'w-1.5 h-1.5 rounded-full bg-[#059669]',
      titre: 'Inspection de conformité validée',
      detailAvant: 'Par ',
      detailFort: "Adèle B. (Gouvernante d'étage)",
      detailApres: ' à 14:15. Check-list 32 points validée.',
    },
    {
      pastilleClass: 'absolute left-0 top-1 w-4 h-4 rounded-full bg-secondary-container border-2 border-secondary flex items-center justify-center',
      pointClass: 'w-1.5 h-1.5 rounded-full bg-secondary',
      titre: 'Ticket SAV #407 clôturé : Réactivation Clim',
      detailAvant: 'Technicien Eric M. Réparation sonde soufflage Carrier et test de cycle thermodynamique OK (13:40).',
      detailFort: '',
      detailApres: '',
    },
    {
      pastilleClass: 'absolute left-0 top-1 w-4 h-4 rounded-full bg-surface-container border-2 border-outline flex items-center justify-center',
      pointClass: 'w-1.5 h-1.5 rounded-full bg-outline',
      titre: 'Ménage approfondi & réassort VIP',
      detailAvant: 'Par Fatou D. Corbeille de fruits locaux et peignoirs brodés installés (11:50).',
      detailFort: '',
      detailApres: '',
    },
  ];

  gouvernantes: Gouvernante[] = [
    {
      initiales: 'AB',
      avatarClass: 'w-7 h-7 rounded-full bg-primary/20 text-primary font-bold flex items-center justify-center text-caption font-caption',
      nom: 'Adèle B.', fonction: 'Inspectrice Aile Lagune',
      etat: '8/8 validées', etatClass: 'text-caption font-caption font-bold text-[#059669]',
    },
    {
      initiales: 'FD',
      avatarClass: 'w-7 h-7 rounded-full bg-secondary-container text-on-secondary-container font-bold flex items-center justify-center text-caption font-caption',
      nom: 'Fatou D.', fonction: 'Chambrière Nord',
      etat: 'En cours (Ch. 103)', etatClass: 'text-caption font-caption font-bold text-amber-600',
    },
  ];

  onAffecterTournee(): void { console.log('Affecter une tournée gouvernante'); }
  onNouveauTicketSav(): void { console.log('Nouveau ticket SAV'); }
  onOptionsChambre(): void { console.log('Options de la chambre', this.chambreActive()); }
  onFicheTechnique(): void { console.log('Fiche technique & SAV', this.chambreActive()); }
  onForcerRecouche(): void { console.log('Forcer recouche', this.chambreActive()); }
  onGenererCle(): void { console.log('Générer une clé IoT', this.chambreActive()); }

  onActionChambre(c: CarteChambre): void {
    console.log(`${c.actionLabel} — chambre ${c.numero}`);
  }
}
