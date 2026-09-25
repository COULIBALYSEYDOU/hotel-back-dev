import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormSlideoverComponent, FormField } from '@shared/components/form-slideover/form-slideover.component';

type ServiceEnCours = 'petit-dejeuner' | 'dejeuner' | 'diner';
type CanalCommande = 'cuisine' | 'room';
type FiltreCanal = 'toutes' | CanalCommande;

@Component({
  selector: 'app-restauration',
  standalone: true,
  imports: [CommonModule, FormSlideoverComponent],
  template: `
    <div class="space-y-6 max-w-[1600px] w-full mx-auto">

      <!-- Page Header Banner -->
      <section class="flex flex-col md:flex-row md:items-center justify-between gap-4 pb-2 border-b border-outline-variant/60">
        <div class="space-y-1">
          <div class="flex items-center gap-3">
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Restauration, Bar &amp; Room Service</h1>
            <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-primary-fixed/30 text-on-primary-fixed-variant border border-primary/20 text-label-sm font-label-sm">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              Système Indépendant Connecté PMS
            </span>
          </div>
          <p class="text-body-md font-body-md text-secondary">Supervision en direct de la cuisine centrale, des tables de prestige et des livraisons en étage.</p>
        </div>
        <!-- Secondary Actions & Shift Filter -->
        <div class="flex items-center gap-3">
          <div class="flex items-center bg-surface-container-lowest border border-outline-variant rounded-lg p-1">
            <button type="button" [class]="classeService('petit-dejeuner')" (click)="onChangerService('petit-dejeuner')">Petit-Déjeuner</button>
            <button type="button" [class]="classeService('dejeuner')" (click)="onChangerService('dejeuner')">Déjeuner</button>
            <button type="button" [class]="classeService('diner')" (click)="onChangerService('diner')">Service en cours : Dîner</button>
          </div>
          <button type="button" class="px-3.5 py-2 rounded-lg bg-surface-container-lowest border border-outline-variant hover:bg-surface-container-low text-on-surface text-label-sm font-label-sm flex items-center gap-2 transition-colors" (click)="onPlanDeSalle()">
            <span class="material-symbols-outlined text-base text-secondary">grid_view</span>
            <span>Plan de salle interactif</span>
          </button>
          <button type="button" class="px-3.5 py-2 rounded-lg bg-surface-container-lowest border border-outline-variant hover:bg-surface-container-low text-on-surface text-label-sm font-label-sm flex items-center gap-2 transition-colors" (click)="onImprimerBons()">
            <span class="material-symbols-outlined text-base text-secondary">print</span>
            <span>Imprimer bons cuisine</span>
          </button>
        </div>
      </section>

      <!-- ==================== KPIS F&B DU JOUR ==================== -->
      <section class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4">
        <!-- Card 1: CA Restauration -->
        <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm hover:border-outline transition-colors flex flex-col justify-between">
          <div class="flex items-center justify-between">
            <span class="text-label-md font-label-md text-secondary font-medium uppercase tracking-wider">CA Restauration du jour</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg">point_of_sale</span>
            </div>
          </div>
          <div class="mt-4">
            <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">3 840 000 <span class="text-headline-sm font-headline-sm font-normal text-secondary">FCFA</span></div>
            <div class="flex items-center gap-1.5 mt-2 text-primary font-label-sm text-label-sm">
              <span class="material-symbols-outlined text-sm font-bold">trending_up</span>
              <span class="font-bold">+14%</span>
              <span class="text-secondary font-normal">vs hier (Dîner)</span>
            </div>
          </div>
        </div>
        <!-- Card 2: Couverts & Commandes -->
        <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm hover:border-outline transition-colors flex flex-col justify-between">
          <div class="flex items-center justify-between">
            <span class="text-label-md font-label-md text-secondary font-medium uppercase tracking-wider">Couverts &amp; Commandes</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg">restaurant_menu</span>
            </div>
          </div>
          <div class="mt-4">
            <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">142 <span class="text-headline-sm font-headline-sm font-normal text-secondary">couverts</span></div>
            <div class="flex items-center gap-3 mt-2 text-secondary text-caption font-caption">
              <span class="flex items-center gap-1"><span class="w-2 h-2 rounded-full bg-primary"></span> 98 en salle</span>
              <span class="flex items-center gap-1"><span class="w-2 h-2 rounded-full bg-secondary-container"></span> 44 room-service</span>
            </div>
          </div>
        </div>
        <!-- Card 3: Ticket Moyen F&B -->
        <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm hover:border-outline transition-colors flex flex-col justify-between">
          <div class="flex items-center justify-between">
            <span class="text-label-md font-label-md text-secondary font-medium uppercase tracking-wider">Ticket Moyen F&amp;B</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg">receipt_long</span>
            </div>
          </div>
          <div class="mt-4">
            <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">27 000 <span class="text-headline-sm font-headline-sm font-normal text-secondary">FCFA</span></div>
            <div class="flex items-center gap-1.5 mt-2 text-secondary font-caption text-caption">
              <span class="font-medium text-primary">+2 400 FCFA</span>
              <span>/ couvert vs moyenne hebdomadaire</span>
            </div>
          </div>
        </div>
        <!-- Card 4: Imputation PMS -->
        <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm hover:border-outline transition-colors flex flex-col justify-between">
          <div class="flex items-center justify-between">
            <span class="text-label-md font-label-md text-secondary font-medium uppercase tracking-wider">Imputations &amp; Règlements</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-lg">hotel</span>
            </div>
          </div>
          <div class="mt-4">
            <div class="text-display-md font-display-md text-on-surface tracking-tight leading-none">78% <span class="text-headline-sm font-headline-sm font-normal text-secondary">sur chambre</span></div>
            <div class="flex items-center gap-1 mt-2 text-secondary text-caption font-caption">
              <span class="text-on-surface font-semibold">Note PMS activée</span>
              <span>• Wave, CB &amp; Espèces : 22%</span>
            </div>
          </div>
        </div>
      </section>

      <!-- ==================== MAIN SPLIT CONTENT ==================== -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

        <!-- ==================== GAUCHE (2/3): KDS & Commandes Actives ==================== -->
        <div class="lg:col-span-2 space-y-4">
          <div class="flex items-center justify-between px-1">
            <div class="flex items-center gap-3">
              <h2 class="text-headline-md font-headline-md text-on-surface">Commandes en Direct (KDS &amp; Service)</h2>
              <span class="px-2 py-0.5 rounded-full bg-primary/10 text-primary text-label-sm font-bold">4 Actives</span>
            </div>
            <!-- Quick Filter tabs -->
            <div class="flex items-center gap-2">
              <button type="button" [class]="classeFiltre('toutes')" (click)="onFiltrerCanal('toutes')">Toutes (4)</button>
              <button type="button" [class]="classeFiltre('cuisine')" (click)="onFiltrerCanal('cuisine')">Cuisine (2)</button>
              <button type="button" [class]="classeFiltre('room')" (click)="onFiltrerCanal('room')">Room Service (2)</button>
            </div>
          </div>
          <!-- Card Commandes Cluster -->
          <div class="space-y-3">

            <!-- Commande 1: #CMD-1082 (Chambre 102 - Suite Junior) -->
            <div class="bg-surface-container-lowest border-l-4 border-l-amber-500 border border-outline-variant rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow" *ngIf="estAffichee('room')">
              <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-outline-variant/60 pb-3">
                <div class="flex items-center gap-3">
                  <span class="text-headline-sm font-headline-sm font-bold text-on-surface">#CMD-1082</span>
                  <span class="px-2.5 py-0.5 rounded bg-primary-fixed/40 text-on-primary-fixed-variant text-label-sm font-semibold flex items-center gap-1">
                    <span class="material-symbols-outlined text-sm">bed</span>
                    Chambre 102 - Suite Junior
                  </span>
                  <span class="px-2 py-0.5 rounded bg-amber-50 text-amber-800 border border-amber-200 text-caption font-bold uppercase tracking-wider flex items-center gap-1">
                    <span class="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
                    M. Sékou Koné (VIP)
                  </span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="px-2.5 py-1 rounded-full bg-amber-100 text-amber-900 font-label-sm text-label-sm font-semibold flex items-center gap-1.5 animate-pulse">
                    <span class="material-symbols-outlined text-sm">hourglass_top</span>
                    En préparation cuisine (8 min)
                  </span>
                </div>
              </div>
              <div class="py-3 grid grid-cols-1 sm:grid-cols-3 gap-4 text-body-sm font-body-sm">
                <div class="sm:col-span-2 space-y-1">
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Poulet bicyclette braisé à l'ivoirienne
                    <span class="text-caption text-secondary">(Piment doux, oignons caramélisés)</span>
                  </p>
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Portion d'Allocos croustillants
                  </p>
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Bouteille Saint-Émilion Grand Cru 2018
                    <span class="text-caption text-primary font-semibold">★ Cave Privée</span>
                  </p>
                </div>
                <div class="flex flex-col sm:items-end justify-center">
                  <div class="text-headline-sm font-headline-sm font-bold text-on-surface">48 000 FCFA</div>
                  <div class="text-caption font-caption text-primary font-semibold flex items-center gap-1 mt-1">
                    <span class="material-symbols-outlined text-sm">check_circle</span>
                    Report sur facture chambre validé
                  </div>
                </div>
              </div>
              <div class="pt-3 border-t border-outline-variant/60 flex items-center justify-between text-caption text-secondary">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-sm">schedule</span>
                  <span>Commandé à 20:14 • Serveur : Koffi A.</span>
                </div>
                <div class="flex items-center gap-2">
                  <button type="button" class="px-2.5 py-1 text-label-sm font-label-sm rounded border border-outline-variant hover:bg-surface-container-low text-secondary" (click)="onNotifierSommelier('#CMD-1082')">Notifier sommelier</button>
                  <button type="button" class="px-3 py-1 text-label-sm font-label-sm rounded bg-primary text-on-primary hover:bg-primary-container font-semibold" (click)="onMarquerPret('#CMD-1082')">Marquer prêt pour livraison</button>
                </div>
              </div>
            </div>

            <!-- Commande 2: #CMD-1083 (Table 6 - Terrasse Lagune) -->
            <div class="bg-surface-container-lowest border-l-4 border-l-primary border border-outline-variant rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow" *ngIf="estAffichee('cuisine')">
              <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-outline-variant/60 pb-3">
                <div class="flex items-center gap-3">
                  <span class="text-headline-sm font-headline-sm font-bold text-on-surface">#CMD-1083</span>
                  <span class="px-2.5 py-0.5 rounded bg-surface-container-high text-on-surface text-label-sm font-semibold flex items-center gap-1">
                    <span class="material-symbols-outlined text-sm">deck</span>
                    Table 6 - Terrasse Lagune
                  </span>
                  <span class="px-2 py-0.5 rounded bg-surface-container-low text-secondary text-caption font-medium">
                    4 couverts
                  </span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="px-2.5 py-1 rounded-full bg-primary-fixed/40 text-on-primary-fixed-variant font-label-sm text-label-sm font-semibold flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-sm text-primary">done_all</span>
                    Prêt pour service
                  </span>
                </div>
              </div>
              <div class="py-3 grid grid-cols-1 sm:grid-cols-3 gap-4 text-body-sm font-body-sm">
                <div class="sm:col-span-2 space-y-1">
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">2x</span> Filet de mérou grillé sauce vierge &amp; attiéké
                  </p>
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">2x</span> Capitaine sauté aux épices de Grand-Bassam
                  </p>
                </div>
                <div class="flex flex-col sm:items-end justify-center">
                  <div class="text-headline-sm font-headline-sm font-bold text-on-surface">62 500 FCFA</div>
                  <div class="text-caption font-caption text-secondary mt-1">
                    Paiement à table (En attente fin service)
                  </div>
                </div>
              </div>
              <div class="pt-3 border-t border-outline-variant/60 flex items-center justify-between text-caption text-secondary">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-sm">schedule</span>
                  <span>Commandé à 19:55 • Chef de rang : Amadou B.</span>
                </div>
                <div class="flex items-center gap-2">
                  <button type="button" class="px-3 py-1 text-label-sm font-label-sm rounded bg-on-surface text-surface-container-lowest hover:bg-inverse-surface font-semibold flex items-center gap-1" (click)="onAssignerPorteur('#CMD-1083')">
                    <span class="material-symbols-outlined text-sm">room_service</span>
                    Assigner porteur
                  </button>
                </div>
              </div>
            </div>

            <!-- Commande 3: #CMD-1084 (Chambre 205 - Standard Balcon) -->
            <div class="bg-surface-container-lowest border-l-4 border-l-slate-400 border border-outline-variant rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow opacity-90" *ngIf="estAffichee('room')">
              <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-outline-variant/60 pb-3">
                <div class="flex items-center gap-3">
                  <span class="text-headline-sm font-headline-sm font-bold text-on-surface">#CMD-1084</span>
                  <span class="px-2.5 py-0.5 rounded bg-surface-container-high text-on-surface text-label-sm font-semibold flex items-center gap-1">
                    <span class="material-symbols-outlined text-sm">door_front</span>
                    Chambre 205 - Standard Balcon
                  </span>
                  <span class="px-2 py-0.5 rounded bg-surface-container-low text-secondary text-caption font-medium">
                    Mme. Valérie Dupuis
                  </span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="px-2.5 py-1 rounded-full bg-surface-container-high text-secondary font-label-sm text-label-sm font-semibold flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-sm text-primary">task_alt</span>
                    Livré en chambre
                  </span>
                </div>
              </div>
              <div class="py-3 grid grid-cols-1 sm:grid-cols-3 gap-4 text-body-sm font-body-sm">
                <div class="sm:col-span-2 space-y-1">
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Club Sandwich Étoile du Sud (Poulet fumé)
                  </p>
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Grand Cocktail Bissap frais aux feuilles de menthe
                  </p>
                </div>
                <div class="flex flex-col sm:items-end justify-center">
                  <div class="text-headline-sm font-headline-sm font-bold text-on-surface">18 500 FCFA</div>
                  <div class="text-caption font-caption text-primary font-semibold flex items-center gap-1 mt-1">
                    <span class="material-symbols-outlined text-sm">check</span>
                    Imputé Folio Chambre #205
                  </div>
                </div>
              </div>
              <div class="pt-3 border-t border-outline-variant/60 flex items-center justify-between text-caption text-secondary">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-sm">schedule</span>
                  <span>Livré à 20:20 par Paul K. (Durée totale : 19 min)</span>
                </div>
                <div class="flex items-center gap-2">
                  <button type="button" class="px-2.5 py-1 text-label-sm font-label-sm rounded border border-outline-variant hover:bg-surface-container-low text-secondary" (click)="onArchiverBon('#CMD-1084')">Archiver bon</button>
                </div>
              </div>
            </div>

            <!-- Commande 4: #CMD-1085 (Table 12 - Salle Panoramique) -->
            <div class="bg-surface-container-lowest border-l-4 border-l-blue-400 border border-outline-variant rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow" *ngIf="estAffichee('cuisine')">
              <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-outline-variant/60 pb-3">
                <div class="flex items-center gap-3">
                  <span class="text-headline-sm font-headline-sm font-bold text-on-surface">#CMD-1085</span>
                  <span class="px-2.5 py-0.5 rounded bg-surface-container-high text-on-surface text-label-sm font-semibold flex items-center gap-1">
                    <span class="material-symbols-outlined text-sm">table_restaurant</span>
                    Table 12 - Salle Panoramique
                  </span>
                  <span class="px-2 py-0.5 rounded bg-surface-container-low text-secondary text-caption font-medium">
                    2 couverts
                  </span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="px-2.5 py-1 rounded-full bg-blue-50 text-blue-800 border border-blue-200 font-label-sm text-label-sm font-semibold flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-sm">edit_note</span>
                    Prise de commande
                  </span>
                </div>
              </div>
              <div class="py-3 grid grid-cols-1 sm:grid-cols-3 gap-4 text-body-sm font-body-sm">
                <div class="sm:col-span-2 space-y-1">
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">2x</span> Cocktail de bienvenue Signature Passion &amp; Gin
                  </p>
                  <p class="font-medium text-on-surface flex items-center gap-2">
                    <span class="text-primary font-bold">1x</span> Planche tapas avocat crevettes sauvages de Sassandra
                  </p>
                </div>
                <div class="flex flex-col sm:items-end justify-center">
                  <div class="text-headline-sm font-headline-sm font-bold text-on-surface">24 000 FCFA</div>
                  <div class="text-caption font-caption text-secondary mt-1">
                    Saisie par tablette Barman #3
                  </div>
                </div>
              </div>
              <div class="pt-3 border-t border-outline-variant/60 flex items-center justify-between text-caption text-secondary">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-sm">schedule</span>
                  <span>Reçue à l'instant (20:31)</span>
                </div>
                <div class="flex items-center gap-2">
                  <button type="button" class="px-3 py-1 text-label-sm font-label-sm rounded bg-primary text-on-primary hover:bg-primary-container font-semibold" (click)="onTransmettreAuBar('#CMD-1085')">Transmettre au bar</button>
                </div>
              </div>
            </div>

          </div>
        </div>

        <!-- ==================== DROITE (1/3): Statut Espaces & Stocks ==================== -->
        <div class="space-y-6">

          <!-- Section Statut Espaces F&B -->
          <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm space-y-5">
            <div class="flex items-center justify-between border-b border-outline-variant/60 pb-3">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-xl">storefront</span>
                <h3 class="text-headline-sm font-headline-sm font-bold text-on-surface">Occupation des Espaces</h3>
              </div>
              <span class="text-caption font-caption text-secondary">Mise à jour directe</span>
            </div>
            <!-- Item 1: Restaurant Le Baobab Royal -->
            <div class="space-y-2">
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2 font-semibold text-on-surface">
                  <span class="material-symbols-outlined text-base text-primary">dinner_dining</span>
                  <span>Restaurant 'Le Baobab Royal'</span>
                </div>
                <span class="font-bold text-on-surface">18 / 24 <span class="text-secondary font-normal text-caption">tables</span></span>
              </div>
              <div class="w-full bg-surface-container-high h-2 rounded-full overflow-hidden">
                <div class="bg-primary h-full rounded-full" style="width: 75%;"></div>
              </div>
              <div class="flex justify-between text-caption font-caption text-secondary">
                <span>75% d'occupation</span>
                <span class="text-primary font-medium">6 tables prêtes</span>
              </div>
            </div>
            <!-- Item 2: Bar Piscine L'Étoile Bleue -->
            <div class="space-y-2">
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2 font-semibold text-on-surface">
                  <span class="material-symbols-outlined text-base text-primary">local_bar</span>
                  <span>Bar Piscine 'L'Étoile Bleue'</span>
                </div>
                <span class="font-bold text-on-surface">12 / 16 <span class="text-secondary font-normal text-caption">salons</span></span>
              </div>
              <div class="w-full bg-surface-container-high h-2 rounded-full overflow-hidden">
                <div class="bg-primary h-full rounded-full" style="width: 75%;"></div>
              </div>
              <div class="flex justify-between text-caption font-caption text-secondary">
                <span>Ambiance Lounge active</span>
                <span class="text-primary font-medium">4 salons libres</span>
              </div>
            </div>
            <!-- Item 3: Room Service 24/7 -->
            <div class="p-3 bg-surface-container-low rounded-xl border border-outline-variant space-y-2">
              <div class="flex items-center justify-between">
                <span class="text-label-md font-label-md font-bold text-on-surface flex items-center gap-1.5">
                  <span class="material-symbols-outlined text-base text-primary">room_service</span>
                  Room Service 24/7
                </span>
                <span class="px-2 py-0.5 rounded bg-primary-fixed/50 text-on-primary-fixed-variant text-[10px] font-bold">Actif</span>
              </div>
              <div class="grid grid-cols-2 gap-2 pt-1 text-body-sm font-body-sm">
                <div>
                  <span class="text-caption text-secondary block">Livreurs en service</span>
                  <span class="font-bold text-on-surface">4 en rotation</span>
                </div>
                <div>
                  <span class="text-caption text-secondary block">Temps moyen livr.</span>
                  <span class="font-bold text-primary">22 minutes</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Section Alertes Stocks Critiques -->
          <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-outline-variant/60 pb-3">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-error text-xl">warning</span>
                <h3 class="text-headline-sm font-headline-sm font-bold text-on-surface">Stocks Bar &amp; Cave Critiques</h3>
              </div>
              <span class="px-2 py-0.5 rounded bg-error-container text-on-error-container text-[11px] font-bold">2 Alertes</span>
            </div>
            <div class="space-y-3">
              <!-- Stock Item 1 -->
              <div class="flex items-center justify-between p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low transition-colors">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-error-container/40 text-error flex items-center justify-center flex-shrink-0">
                    <span class="material-symbols-outlined text-lg">liquor</span>
                  </div>
                  <div>
                    <div class="text-label-md font-label-md text-on-surface leading-tight">Moët &amp; Chandon Brut Impérial</div>
                    <div class="text-caption font-caption text-error font-semibold">Reste 3 bouteilles (Seuil min : 12)</div>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-1 rounded bg-surface-container-high text-secondary font-mono">Cave 01</span>
              </div>
              <!-- Stock Item 2 -->
              <div class="flex items-center justify-between p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low transition-colors">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-amber-100 text-amber-800 flex items-center justify-center flex-shrink-0">
                    <span class="material-symbols-outlined text-lg">water_bottle</span>
                  </div>
                  <div>
                    <div class="text-label-md font-label-md text-on-surface leading-tight">Eau Minérale Naturelle 1L</div>
                    <div class="text-caption font-caption text-amber-800 font-semibold">Reste 18 bouteilles (Seuil min : 48)</div>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-1 rounded bg-surface-container-high text-secondary font-mono">Bar Piscine</span>
              </div>
            </div>
            <!-- Restock CTA Button -->
            <button type="button" class="w-full mt-2 py-2.5 px-4 rounded-lg bg-surface-container-low border border-outline-variant hover:bg-surface-container-high text-primary font-label-md text-label-md font-bold flex items-center justify-center gap-2 transition-colors active:scale-[0.98]" (click)="onReapprovisionner()">
              <span class="material-symbols-outlined text-base">inventory</span>
              <span>Déclencher bon de réapprovisionnement direct</span>
            </button>
          </div>

          <!-- Quick Kitchen Note Component -->
          <div class="bg-surface-container-low border border-outline-variant rounded-xl p-4 flex items-start gap-3 text-body-sm font-body-sm">
            <span class="material-symbols-outlined text-primary text-xl mt-0.5">info</span>
            <div>
              <span class="font-bold text-on-surface block">Consigne Chef Exécutif :</span>
              <p class="text-secondary text-caption font-caption mt-0.5">La pêche locale de mérou est exceptionnelle ce soir. Suggérer l'accord avec le Saint-Véran blanc pour les tables 4 à 8.</p>
            </div>
          </div>

        </div>
      </div>

    </div>

    <app-form-slideover
      [(open)]="openNew"
      title="Nouvelle commande F&B"
      subtitle="Prise de commande rapide."
      submitLabel="Envoyer en cuisine"
      [fields]="createFields"
      [(model)]="newForm"
      (submitted)="onCreate($event)" />
  `,
})
export class RestaurationComponent {
  // ---- Flux de création conservé (slideover partagé) ----
  openNew = false;
  createFields: FormField[] = [
    { key: 'origine', label: 'Origine', type: 'select', options: ['CHAMBRE','RESTAURANT','BAR','POOL'], required: true },
    { key: 'ref', label: 'Chambre/Table', type: 'text', placeholder: '208 ou Table 12', required: true },
    { key: 'nbItems', label: 'Nombre articles', type: 'number', min: 1 },
    { key: 'total', label: 'Total (FCFA)', type: 'number', min: 0 },
    { key: 'notes', label: 'Notes cuisine', type: 'textarea', colSpan: 2 },
  ];
  newForm: Record<string, any> = { origine: 'RESTAURANT', ref: '', nbItems: 1, total: 0, notes: '' };

  onCreate(data: Record<string, any>) {
    console.log('[RestaurationComponent] nouvel enregistrement :', data);
    // TODO : brancher au backend Spring Boot
    this.newForm = { origine: 'RESTAURANT', ref: '', nbItems: 1, total: 0, notes: '' };
  }

  // ---- Bascule de service (Petit-Déjeuner / Déjeuner / Dîner) ----
  service = signal<ServiceEnCours>('diner');

  private readonly serviceActif =
    'px-3 py-1.5 rounded bg-primary text-on-primary text-caption font-caption font-bold shadow-xs';
  private readonly serviceInactif =
    'px-3 py-1.5 rounded text-caption font-caption font-bold text-secondary hover:text-on-surface';

  classeService(cle: ServiceEnCours): string {
    return this.service() === cle ? this.serviceActif : this.serviceInactif;
  }

  onChangerService(cle: ServiceEnCours): void {
    this.service.set(cle);
    console.log('Service sélectionné :', cle);
  }

  // ---- Filtre des commandes en direct (Toutes / Cuisine / Room Service) ----
  filtreCanal = signal<FiltreCanal>('toutes');

  private readonly filtreActif =
    'px-3 py-1 text-label-sm font-label-sm rounded-lg bg-surface-container-high text-primary font-bold';
  private readonly filtreInactif =
    'px-3 py-1 text-label-sm font-label-sm rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors';

  classeFiltre(cle: FiltreCanal): string {
    return this.filtreCanal() === cle ? this.filtreActif : this.filtreInactif;
  }

  onFiltrerCanal(cle: FiltreCanal): void {
    this.filtreCanal.set(cle);
  }

  estAffichee(canal: CanalCommande): boolean {
    return this.filtreCanal() === 'toutes' || this.filtreCanal() === canal;
  }

  // ---- Actions de service (à brancher sur le backend) ----
  onPlanDeSalle(): void {
    console.log('Ouverture du plan de salle interactif');
  }

  onImprimerBons(): void {
    console.log('Impression des bons cuisine');
  }

  onNotifierSommelier(commande: string): void {
    console.log('Notification sommelier pour', commande);
  }

  onMarquerPret(commande: string): void {
    console.log('Commande marquée prête pour livraison :', commande);
  }

  onAssignerPorteur(commande: string): void {
    console.log('Assignation d\'un porteur pour', commande);
  }

  onArchiverBon(commande: string): void {
    console.log('Archivage du bon', commande);
  }

  onTransmettreAuBar(commande: string): void {
    console.log('Commande transmise au bar :', commande);
  }

  onReapprovisionner(): void {
    console.log('Déclenchement du bon de réapprovisionnement direct');
  }
}
