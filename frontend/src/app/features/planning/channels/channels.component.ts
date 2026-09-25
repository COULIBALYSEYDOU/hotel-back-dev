import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Un connecteur OTA « classique » (Booking, Expedia, Airbnb). */
interface ConnecteurOta {
  cle: string;
  logoClass: string;
  logo: string;
  nom: string;
  sousTitre: string;
  statut: string;
  partNuitees: string;
  commission: string;
  derniereSynchro: string;
  enPause: boolean;
}

/** Une ligne de la matrice « Quotas de Distribution & Parité Tarifaire ». */
interface CategorieChambre {
  /** Sert au filtre du sélecteur de catégories. */
  groupe: 'suite' | 'standard';

  ligneClass: string;
  iconeClass: string;
  icon: string;
  iconePleine: boolean;

  nom: string;
  vip: boolean;
  detail: string;

  stock: string;

  allocClass: string;
  alloc: string;

  /** Rangs 1, 2 et 4 : simple texte centré. */
  quotaTexte?: string;
  /** Rang 3 : badge « Fermé OTA ». */
  quotaBadge?: { icon: string; texte: string };
  quotaCelluleClass: string;

  tarif: string;
  tarifDetail: string;

  pariteClass: string;
  pariteIcon: string;
  parite: string;

  /** État pilotable : commande le badge de statut et le 2ᵉ bouton d'action. */
  stopSell: boolean;
}

@Component({
  selector: 'app-channels',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="space-y-8">
      <!-- ==================== PAGE HEADER ==================== -->
      <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div>
          <div class="flex items-center gap-3 mb-1">
            <h1 class="text-headline-lg font-headline-lg font-bold text-on-surface tracking-tight">
              Moteur de Réservation Web &amp; Canaux OTA
            </h1>
            <span
              class="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded-full text-caption font-caption font-semibold bg-emerald-50 text-primary border border-emerald-200"
            >
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              Channel Manager Actif
            </span>
          </div>
          <p class="text-body-md font-body-md text-secondary">
            Distribution multi-canaux 2-Way, parité tarifaire et moteur direct sans commission
          </p>
        </div>
        <div class="flex items-center gap-3 flex-shrink-0">
          <button
            class="flex items-center gap-2 px-4 py-2.5 rounded-lg border border-outline-variant bg-surface-container-lowest text-on-surface hover:bg-surface hover:border-outline text-label-md font-label-md font-semibold shadow-sm transition-all"
            (click)="onAjusterRestrictions()"
          >
            <span class="material-symbols-outlined text-secondary">tune</span>
            <span>Ajuster restrictions (Stop Sell)</span>
          </button>
          <button
            class="flex items-center gap-2 px-4 py-2.5 rounded-lg bg-primary text-on-primary hover:bg-primary-container text-label-md font-label-md font-semibold shadow-sm transition-all active:scale-[0.98]"
            (click)="onConfigurerCanal()"
          >
            <span class="material-symbols-outlined">add_link</span>
            <span>Configurer nouveau canal OTA</span>
          </button>
        </div>
      </div>

      <!-- ==================== KEY DISTRIBUTION KPIS ==================== -->
      <section class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
        <!-- Card 1: Direct Share -->
        <div
          class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all"
        >
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider"
              >Part Réservations Directes</span
            >
            <div class="w-8 h-8 rounded-lg bg-emerald-50 text-primary flex items-center justify-center">
              <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">pie_chart</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2 mb-1">
            <span class="text-display-md font-display-md text-on-surface">70.2%</span>
            <span class="inline-flex items-center text-label-sm font-label-sm text-primary font-bold">
              <span class="material-symbols-outlined text-[14px]">arrow_upward</span> +6.4%
            </span>
          </div>
          <p class="text-body-sm font-body-sm text-secondary">Moteur Web Hôtel + Front Desk, 0% commission versée</p>
        </div>

        <!-- Card 2: Commission Savings -->
        <div
          class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all"
        >
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider"
              >Économie de Commissions</span
            >
            <div class="w-8 h-8 rounded-lg bg-emerald-50 text-primary flex items-center justify-center">
              <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">savings</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2 mb-1">
            <span class="text-headline-lg font-headline-lg font-bold text-on-surface tracking-tight"
              >4 680 000 FCFA</span
            >
          </div>
          <p class="text-body-sm font-body-sm text-secondary">Économisés ce mois-ci par rapport au mix OTA 18%</p>
        </div>

        <!-- Card 3: Connected Channels Status -->
        <div
          class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all"
        >
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider"
              >Statut Synchronisation Canaux</span
            >
            <div class="w-8 h-8 rounded-lg bg-emerald-50 text-primary flex items-center justify-center">
              <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">hub</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2 mb-1">
            <span class="text-headline-lg font-headline-lg font-bold text-on-surface tracking-tight">4 plateformes</span>
          </div>
          <p class="text-body-sm font-body-sm text-primary font-semibold">
            Connectées en temps réel (Booking, Expedia, Airbnb, Direct)
          </p>
        </div>

        <!-- Card 4: 2-Way API Latency -->
        <div
          class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all"
        >
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">Latence API 2-Way</span>
            <div class="w-8 h-8 rounded-lg bg-emerald-50 text-primary flex items-center justify-center">
              <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">speed</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2 mb-1">
            <span class="text-display-md font-display-md text-on-surface">1.2 s</span>
            <span class="inline-flex items-center text-label-sm font-label-sm text-primary font-semibold"
              >Ultra-rapide</span
            >
          </div>
          <p class="text-body-sm font-body-sm text-secondary">Zéro surréservation (overbooking) détectée sur 30j</p>
        </div>
      </section>

      <!-- ============ SECTION 1: STATUT ET FLUX DES CANAUX CONNECTÉS ============ -->
      <section class="space-y-4">
        <div class="flex items-center justify-between">
          <div>
            <h2 class="text-headline-sm font-headline-sm font-bold text-on-surface">
              Canaux de Distribution &amp; Connecteurs 2-Way
            </h2>
            <p class="text-body-sm font-body-sm text-secondary">
              Surveillance du mapping tarifaire, flux de disponibilité et intégrité des stocks par passerelle
            </p>
          </div>
          <div class="flex items-center gap-2">
            <button
              class="px-3 py-1.5 rounded-lg border border-outline-variant bg-surface-container-lowest text-label-sm font-label-sm text-secondary hover:text-on-surface transition-colors"
              (click)="onJournalAudit()"
            >
              Journal d'audit API
            </button>
            <button
              class="px-3 py-1.5 rounded-lg border border-outline-variant bg-surface-container-lowest text-label-sm font-label-sm text-primary font-semibold hover:border-primary transition-colors"
              (click)="onTesterFlux()"
            >
              Tester les flux XML
            </button>
          </div>
        </div>

        <!-- Connector Cards Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
          <!-- Connector 1: Direct Web Engine -->
          <div
            class="bg-surface-container-lowest rounded-xl border-2 border-primary/40 p-5 shadow-sm flex flex-col justify-between relative overflow-hidden"
          >
            <div
              class="absolute top-0 right-0 bg-primary text-on-primary px-3 py-0.5 rounded-bl-lg text-caption font-caption font-bold uppercase tracking-wider"
            >
              Prioritaire
            </div>
            <div>
              <div class="flex items-center gap-3 mb-4">
                <div class="w-12 h-12 rounded-xl bg-emerald-100 flex items-center justify-center text-primary">
                  <span class="material-symbols-outlined text-[26px]" style="font-variation-settings: 'FILL' 1;"
                    >language</span
                  >
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg font-bold text-on-surface">Direct Web Engine</h3>
                  <p class="text-caption font-caption text-secondary">Moteur Étoile Booking Intégré</p>
                </div>
              </div>
              <div class="space-y-2.5 py-3 border-y border-outline-variant text-body-sm font-body-sm">
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Statut du canal :</span>
                  <span
                    class="inline-flex items-center gap-1 px-2 py-0.5 rounded bg-emerald-50 text-primary font-label-sm text-label-sm font-bold"
                  >
                    <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                    En ligne &amp; Prioritaire
                  </span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Part des nuitées :</span>
                  <span class="font-bold text-on-surface">28% des ventes</span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Commission OTA :</span>
                  <span class="text-primary font-bold">0 FCFA (0%)</span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Taux de conversion :</span>
                  <span class="font-semibold text-on-surface">4.8% des visiteurs</span>
                </div>
              </div>
            </div>
            <div class="mt-4 pt-1 flex items-center justify-between">
              <span class="text-caption font-caption text-secondary">Sync continue • Direct DB</span>
              <a
                class="text-label-sm font-label-sm text-primary font-bold hover:underline inline-flex items-center gap-1"
                href="#"
                (click)="onParametrerWidget($event)"
              >
                Paramétrer widget <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
              </a>
            </div>
          </div>

          <!-- Connectors 2 à 4 : Booking.com, Expedia Group, Airbnb Hospitality -->
          <div
            *ngFor="let c of connecteurs"
            class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm hover:border-outline transition-all flex flex-col justify-between"
          >
            <div>
              <div class="flex items-center gap-3 mb-4">
                <div [class]="c.logoClass">
                  {{ c.logo }}
                </div>
                <div>
                  <h3 class="text-label-lg font-label-lg font-bold text-on-surface">{{ c.nom }}</h3>
                  <p class="text-caption font-caption text-secondary">{{ c.sousTitre }}</p>
                </div>
              </div>
              <div class="space-y-2.5 py-3 border-y border-outline-variant text-body-sm font-body-sm">
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Statut du canal :</span>
                  <span
                    class="inline-flex items-center gap-1 px-2 py-0.5 rounded bg-emerald-50 text-primary font-label-sm text-label-sm font-semibold"
                  >
                    <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                    {{ c.statut }}
                  </span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Part des nuitées :</span>
                  <span class="font-bold text-on-surface">{{ c.partNuitees }}</span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Taux commission :</span>
                  <span class="text-secondary font-semibold">{{ c.commission }}</span>
                </div>
                <div class="flex items-center justify-between">
                  <span class="text-secondary">Dernière synchro :</span>
                  <span
                    class="text-caption font-caption text-on-surface font-semibold bg-surface-container px-1.5 py-0.5 rounded"
                    >{{ c.derniereSynchro }}</span
                  >
                </div>
              </div>
            </div>
            <div class="mt-4 pt-1 flex items-center justify-between">
              <button
                class="px-2.5 py-1 text-label-sm font-label-sm text-error hover:bg-error-container/30 border border-error/30 rounded transition-colors"
                (click)="onBasculerPause(c)"
              >
                {{ c.enPause ? 'Reprendre inventaire' : 'Pause inventaire' }}
              </button>
              <button
                class="px-2.5 py-1 text-label-sm font-label-sm text-secondary hover:text-on-surface hover:bg-surface-container rounded transition-colors flex items-center gap-1"
                (click)="onSynchroniser(c)"
              >
                <span class="material-symbols-outlined text-[14px]">refresh</span> Synchro
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- ============ SECTION 2: TABLEAU DE CONTRÔLE DES TARIFS & QUOTAS ============ -->
      <section class="space-y-4">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
          <div>
            <div class="flex items-center gap-2">
              <h2 class="text-headline-sm font-headline-sm font-bold text-on-surface">
                Quotas de Distribution &amp; Parité Tarifaire
              </h2>
              <span class="px-2 py-0.5 rounded bg-surface-container-high text-caption font-caption font-semibold text-secondary"
                >Période : 7 Prochains Jours</span
              >
            </div>
            <p class="text-body-sm font-body-sm text-secondary">
              Pilotage fin des stocks alloués entre le moteur direct sans commission et les canaux OTA soumis à
              commissions
            </p>
          </div>
          <div class="flex items-center gap-3">
            <!-- Filter by category or search -->
            <div class="relative">
              <select
                class="pl-3 pr-8 py-1.5 rounded-lg border border-outline-variant bg-surface-container-lowest text-label-sm font-label-sm text-on-surface focus:outline-none focus:border-primary"
                [(ngModel)]="filtreCategorie"
              >
                <option value="toutes">Toutes les catégories (4)</option>
                <option value="suite">Suites uniquement</option>
                <option value="standard">Chambres standards</option>
              </select>
            </div>
            <button
              class="px-3 py-1.5 rounded-lg bg-surface border border-outline-variant hover:border-outline text-label-sm font-label-sm text-on-surface font-semibold flex items-center gap-1.5"
              (click)="onExporterMatrice()"
            >
              <span class="material-symbols-outlined text-[16px]">file_download</span>
              <span>Exporter matrice (XLS)</span>
            </button>
          </div>
        </div>

        <!-- Master Data Table Container -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full text-left border-collapse">
              <thead>
                <tr
                  class="bg-surface-container-low border-b border-outline-variant text-label-md font-label-md text-secondary uppercase tracking-wider"
                >
                  <th class="py-3.5 px-5">Catégorie de chambre</th>
                  <th class="py-3.5 px-4 text-center">Stock Total</th>
                  <th class="py-3.5 px-4 text-center bg-emerald-50/60 text-primary font-bold">Alloc. Web Direct</th>
                  <th class="py-3.5 px-4 text-center">Quota OTA (Bkg/Exp)</th>
                  <th class="py-3.5 px-4">Tarif Public (FCFA)</th>
                  <th class="py-3.5 px-4">Règle de Parité</th>
                  <th class="py-3.5 px-4">Statut Canal</th>
                  <th class="py-3.5 px-5 text-right">Actions de Yield</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-outline-variant text-body-sm font-body-sm">
                <tr *ngFor="let r of categoriesVisibles()" [class]="r.ligneClass">
                  <td class="py-4 px-5">
                    <div class="flex items-center gap-3">
                      <div [class]="r.iconeClass">
                        <span *ngIf="!r.iconePleine" class="material-symbols-outlined">{{ r.icon }}</span>
                        <span
                          *ngIf="r.iconePleine"
                          class="material-symbols-outlined"
                          style="font-variation-settings: 'FILL' 1;"
                          >{{ r.icon }}</span
                        >
                      </div>
                      <div>
                        <div *ngIf="!r.vip" class="text-label-lg font-label-lg font-bold text-on-surface">
                          {{ r.nom }}
                        </div>
                        <div *ngIf="r.vip" class="flex items-center gap-2">
                          <span class="text-label-lg font-label-lg font-bold text-on-surface">{{ r.nom }}</span>
                          <span class="px-1.5 py-0.2 rounded text-[10px] bg-primary text-on-primary font-bold"
                            >VIP ONLY</span
                          >
                        </div>
                        <div class="text-caption font-caption text-secondary">{{ r.detail }}</div>
                      </div>
                    </div>
                  </td>
                  <td class="py-4 px-4 text-center font-bold text-on-surface">
                    {{ r.stock }}
                  </td>
                  <td class="py-4 px-4 text-center bg-emerald-50/40 font-bold text-primary">
                    <span [class]="r.allocClass">{{ r.alloc }}</span>
                  </td>
                  <td [class]="r.quotaCelluleClass">
                    <ng-container *ngIf="r.quotaTexte">{{ r.quotaTexte }}</ng-container>
                    <span
                      *ngIf="r.quotaBadge"
                      class="inline-flex items-center gap-1 text-error text-label-sm font-label-sm font-bold"
                    >
                      <span class="material-symbols-outlined text-[14px]">{{ r.quotaBadge.icon }}</span>
                      {{ r.quotaBadge.texte }}
                    </span>
                  </td>
                  <td class="py-4 px-4">
                    <div class="font-bold text-on-surface">{{ r.tarif }}</div>
                    <div class="text-caption font-caption text-secondary">{{ r.tarifDetail }}</div>
                  </td>
                  <td class="py-4 px-4">
                    <span [class]="r.pariteClass">
                      <span class="material-symbols-outlined text-[14px]">{{ r.pariteIcon }}</span>
                      {{ r.parite }}
                    </span>
                  </td>
                  <td class="py-4 px-4">
                    <span
                      *ngIf="!r.stopSell"
                      class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-emerald-100 text-primary text-label-sm font-label-sm font-bold"
                    >
                      <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                      Vente ouverte
                    </span>
                    <span
                      *ngIf="r.stopSell"
                      class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-error-container text-error text-label-sm font-label-sm font-bold"
                    >
                      <span class="w-1.5 h-1.5 rounded-full bg-error"></span>
                      Stop Sell OTA Actif
                    </span>
                  </td>
                  <td class="py-4 px-5 text-right">
                    <div class="inline-flex items-center gap-2">
                      <button
                        class="px-2.5 py-1 rounded border border-outline-variant bg-surface hover:border-primary hover:text-primary text-label-sm font-label-sm font-semibold transition-colors"
                        (click)="onAjusterTarif(r)"
                      >
                        Ajuster tarif
                      </button>
                      <button
                        *ngIf="!r.stopSell"
                        class="px-2.5 py-1 rounded border border-outline-variant bg-surface hover:border-error hover:text-error text-label-sm font-label-sm font-semibold transition-colors"
                        (click)="onBasculerStopSell(r)"
                      >
                        Appliquer Stop Sell
                      </button>
                      <button
                        *ngIf="r.stopSell"
                        class="px-2.5 py-1 rounded bg-surface border border-outline-variant hover:bg-surface-container text-secondary text-label-sm font-label-sm font-semibold transition-colors"
                        (click)="onBasculerStopSell(r)"
                      >
                        Débloquer OTA
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- Table Bottom Bar -->
          <div
            class="p-4 bg-surface-container-low border-t border-outline-variant flex flex-col sm:flex-row items-center justify-between gap-3 text-body-sm font-body-sm text-secondary"
          >
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-primary text-[18px]">verified</span>
              <span
                >Règle de Parité Globale : <strong>98.4% conforme</strong>. Aucun écart préjudiciable détecté par le
                crawler de parité.</span
              >
            </div>
            <div class="flex items-center gap-3">
              <span class="text-caption font-caption">Dernière mise à jour automatique : il y a 38 secondes</span>
              <button
                class="text-label-sm font-label-sm text-primary font-bold hover:underline"
                (click)="onHistoriqueQuotas()"
              >
                Historique des modifications de quota
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- ==================== FLOATING QUICK INFO BANNER (YIELD ADVICE) ==================== -->
      <section
        *ngIf="conseilVisible()"
        class="bg-gradient-to-r from-emerald-50 to-surface-container-low rounded-xl border border-primary/20 p-5 flex items-center justify-between gap-4"
      >
        <div class="flex items-center gap-4">
          <div
            class="w-12 h-12 rounded-xl bg-primary text-on-primary flex items-center justify-center flex-shrink-0 shadow-sm"
          >
            <span class="material-symbols-outlined text-[24px]">auto_graph</span>
          </div>
          <div>
            <h4 class="text-label-lg font-label-lg font-bold text-on-surface">
              Recommandation Smart Yield Étoile OS
            </h4>
            <p class="text-body-sm font-body-sm text-secondary">
              Pic de demande corporative détecté pour le Sommet Économique Régional (vendredi 24 à dimanche 26). Nous
              vous recommandons d'activer le <strong>Stop Sell sur Booking.com</strong> pour la Suite Junior Océan et
              d'orienter 100% de l'inventaire restant vers le Moteur Web Direct.
            </p>
          </div>
        </div>
        <div class="flex items-center gap-2 flex-shrink-0">
          <button
            class="px-3 py-2 rounded-lg bg-surface border border-outline-variant hover:bg-surface-container text-label-md font-label-md text-secondary"
            (click)="conseilVisible.set(false)"
          >
            Ignorer
          </button>
          <button
            class="px-4 py-2 rounded-lg bg-primary text-on-primary hover:bg-primary-container text-label-md font-label-md font-semibold shadow-sm transition-all"
            (click)="onAppliquerRecommandation()"
          >
            Appliquer la recommandation
          </button>
        </div>
      </section>
    </div>
  `,
})
export class ChannelsComponent {
  // ---------------------------------------------------------------- État UI
  /**
   * Adossé à un signal pour que [(ngModel)] du sélecteur pilote réellement
   * le computed `categoriesVisibles`.
   */
  private _filtre = signal<'toutes' | 'suite' | 'standard'>('toutes');
  get filtreCategorie(): 'toutes' | 'suite' | 'standard' {
    return this._filtre();
  }
  set filtreCategorie(v: 'toutes' | 'suite' | 'standard') {
    this._filtre.set(v);
  }

  conseilVisible = signal(true);

  // ----------------------------------------------- Connecteurs OTA (2 à 4)
  connecteurs: ConnecteurOta[] = [
    {
      cle: 'booking',
      logoClass: 'w-12 h-12 rounded-xl bg-blue-50 text-blue-800 flex items-center justify-center font-bold text-lg',
      logo: 'B.',
      nom: 'Booking.com',
      sousTitre: 'Direct XML 2-Way Partner',
      statut: 'Connecté (XML 2-Way)',
      partNuitees: '18% des ventes',
      commission: '15.0% contractuel',
      derniereSynchro: 'il y a 42s',
      enPause: false,
    },
    {
      cle: 'expedia',
      logoClass: 'w-12 h-12 rounded-xl bg-amber-50 text-amber-700 flex items-center justify-center font-bold text-lg',
      logo: 'Exp',
      nom: 'Expedia Group',
      sousTitre: 'Expedia Partner Central API',
      statut: 'Connecté (EPC API)',
      partNuitees: '8% des ventes',
      commission: '18.0% contractuel',
      derniereSynchro: 'il y a 1 min',
      enPause: false,
    },
    {
      cle: 'airbnb',
      logoClass: 'w-12 h-12 rounded-xl bg-rose-50 text-rose-600 flex items-center justify-center font-bold text-lg',
      logo: 'Air',
      nom: 'Airbnb Hospitality',
      sousTitre: 'iCal & Official API V2',
      statut: 'Connecté (iCal + API)',
      partNuitees: '6% (Suites Longues)',
      commission: '14.0% hôte direct',
      derniereSynchro: 'il y a 3 min',
      enPause: false,
    },
  ];

  // ------------------------------------------------- Variantes de la matrice
  private readonly ligneNormale = 'hover:bg-surface-container-low transition-colors duration-150';
  private readonly ligneVip = 'bg-amber-50/20 hover:bg-amber-50/40 transition-colors duration-150';

  private readonly iconeNeutre =
    'w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center text-primary flex-shrink-0';
  private readonly iconeVip =
    'w-10 h-10 rounded-lg bg-amber-100 text-amber-800 flex items-center justify-center flex-shrink-0';

  private readonly allocNormale = 'inline-block px-2.5 py-1 rounded bg-emerald-100 text-primary';
  private readonly allocExclusive = 'inline-block px-2.5 py-1 rounded bg-primary text-on-primary';

  private readonly quotaCelluleTexte = 'py-4 px-4 text-center text-secondary font-medium';
  private readonly quotaCelluleBadge = 'py-4 px-4 text-center font-medium';

  private readonly pariteRespectee =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-emerald-50 text-primary border border-emerald-200 text-label-sm font-label-sm font-semibold';
  private readonly pariteDerogation =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-amber-50 text-amber-800 border border-amber-300 text-label-sm font-label-sm font-semibold';

  categories: CategorieChambre[] = [
    {
      groupe: 'suite',
      ligneClass: this.ligneNormale,
      iconeClass: this.iconeNeutre,
      icon: 'king_bed',
      iconePleine: false,
      nom: 'Suite Junior Océan',
      vip: false,
      detail: 'Vue Lagune Ébrié • 48 m² • R+3 à R+5',
      stock: '18 ch.',
      allocClass: this.allocNormale,
      alloc: '12 ch. (67%)',
      quotaTexte: '6 ch. (33%)',
      quotaCelluleClass: this.quotaCelluleTexte,
      tarif: '185 000 FCFA',
      tarifDetail: 'TTC / Nuitée',
      pariteClass: this.pariteRespectee,
      pariteIcon: 'check_circle',
      parite: 'Strictement Respectée',
      stopSell: false,
    },
    {
      groupe: 'standard',
      ligneClass: this.ligneNormale,
      iconeClass: this.iconeNeutre,
      icon: 'balcony',
      iconePleine: false,
      nom: 'Chambre Supérieure Balcon',
      vip: false,
      detail: 'Balcon aménagé • 32 m² • R+1 à R+4',
      stock: '20 ch.',
      allocClass: this.allocNormale,
      alloc: '14 ch. (70%)',
      quotaTexte: '6 ch. (30%)',
      quotaCelluleClass: this.quotaCelluleTexte,
      tarif: '120 000 FCFA',
      tarifDetail: 'TTC / Nuitée',
      pariteClass: this.pariteRespectee,
      pariteIcon: 'check_circle',
      parite: 'Strictement Respectée',
      stopSell: false,
    },
    {
      groupe: 'suite',
      ligneClass: this.ligneVip,
      iconeClass: this.iconeVip,
      icon: 'workspace_premium',
      iconePleine: true,
      nom: 'Suite Présidentielle',
      vip: true,
      detail: "Salon d'honneur • Majordome dédié • 110 m²",
      stock: '4 ch.',
      allocClass: this.allocExclusive,
      alloc: '4 ch. (100% Exclusif)',
      quotaBadge: { icon: 'block', texte: '0 ch. (Fermé OTA)' },
      quotaCelluleClass: this.quotaCelluleBadge,
      tarif: '450 000 FCFA',
      tarifDetail: 'Tarif Direct Garanti',
      pariteClass: this.pariteDerogation,
      pariteIcon: 'shield',
      parite: 'Dérogation Direct Exclusif',
      stopSell: true,
    },
    {
      groupe: 'standard',
      ligneClass: this.ligneNormale,
      iconeClass: this.iconeNeutre,
      icon: 'yard',
      iconePleine: false,
      nom: 'Standard Jardin',
      vip: false,
      detail: 'Vue patio végétalisé • 26 m² • RDC',
      stock: '12 ch.',
      allocClass: this.allocNormale,
      alloc: '7 ch. (58%)',
      quotaTexte: '5 ch. (42%)',
      quotaCelluleClass: this.quotaCelluleTexte,
      tarif: '95 000 FCFA',
      tarifDetail: 'TTC / Nuitée',
      pariteClass: this.pariteRespectee,
      pariteIcon: 'check_circle',
      parite: 'Strictement Respectée',
      stopSell: false,
    },
  ];

  /** Le sélecteur de catégories filtre réellement la matrice. */
  categoriesVisibles = computed(() => {
    const f = this._filtre();
    return f === 'toutes' ? this.categories : this.categories.filter((c) => c.groupe === f);
  });

  // ---------------------------------------------------------------- Actions
  onAjusterRestrictions(): void {
    console.log('Ajuster les restrictions (Stop Sell)');
  }

  onConfigurerCanal(): void {
    console.log('Configurer un nouveau canal OTA');
  }

  onJournalAudit(): void {
    console.log("Ouvrir le journal d'audit API");
  }

  onTesterFlux(): void {
    console.log('Tester les flux XML');
  }

  onParametrerWidget(e: Event): void {
    e.preventDefault();
    console.log('Paramétrer le widget du moteur direct');
  }

  onBasculerPause(c: ConnecteurOta): void {
    c.enPause = !c.enPause;
    console.log(`${c.nom} : inventaire ${c.enPause ? 'en pause' : 'repris'}`);
  }

  onSynchroniser(c: ConnecteurOta): void {
    c.derniereSynchro = "à l'instant";
    console.log(`Synchronisation forcée de ${c.nom}`);
  }

  onExporterMatrice(): void {
    console.log('Exporter la matrice de quotas (XLS)');
  }

  onAjusterTarif(r: CategorieChambre): void {
    console.log('Ajuster le tarif de', r.nom);
  }

  onBasculerStopSell(r: CategorieChambre): void {
    r.stopSell = !r.stopSell;
    console.log(`${r.nom} : ${r.stopSell ? 'Stop Sell OTA activé' : 'canaux OTA débloqués'}`);
  }

  onHistoriqueQuotas(): void {
    console.log('Historique des modifications de quota');
  }

  onAppliquerRecommandation(): void {
    const suite = this.categories.find((c) => c.nom === 'Suite Junior Océan');
    if (suite) suite.stopSell = true;
    this.conseilVisible.set(false);
    console.log('Recommandation Smart Yield appliquée');
  }
}
