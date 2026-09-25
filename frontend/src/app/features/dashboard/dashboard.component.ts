import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

type PeriodeTableau = 'aujourdhui' | '7-jours' | 'mois';
type FluxClient = 'arrivees' | 'vip' | 'departs';

/**
 * Dashboard SaaS Étoile OS — reprend la structure de la maquette
 * "tableau_de_bord_saas_hotel_toile_du_sud_desktop_light".
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="max-w-[1600px] mx-auto space-y-6">
      <!-- 1. GREETING & CONTEXTUAL HEADER -->
      <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4 bg-surface-container-lowest p-6 rounded-xl border border-outline-variant shadow-sm">
        <div>
          <div class="flex items-center gap-2 text-primary text-label-md font-label-md font-semibold mb-1">
            <span class="material-symbols-outlined text-base">today</span>
            <span class="">Jeudi 24 Octobre 2024 • Service après-midi</span>
          </div>
          <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">
            Bonjour Awa 👋
          </h1>
          <p class="text-body-md font-body-md text-secondary mt-0.5">
            Actuellement 44 chambres sur 50 allouées. 14 arrivées attendues cet après-midi dont 3 profils VIP.
          </p>
        </div>
        <!-- Quick Controls -->
        <div class="flex flex-wrap items-center gap-3">
          <!-- Time range selector -->
          <div class="inline-flex rounded-lg bg-surface-container-low p-1 border border-outline-variant">
            <button [class]="classePeriode('aujourdhui')" (click)="onPeriode('aujourdhui')">Aujourd'hui</button>
            <button [class]="classePeriode('7-jours')" (click)="onPeriode('7-jours')">7 jours</button>
            <button [class]="classePeriode('mois')" (click)="onPeriode('mois')">Mois</button>
          </div>
          <!-- Secondary CTA -->
          <button class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-surface-container-lowest border border-outline-variant text-on-surface hover:bg-surface-container-low transition-all duration-150 text-label-md font-label-md font-semibold"
                  type="button" (click)="onRapportShift()">
            <span class="material-symbols-outlined text-lg text-secondary">receipt_long</span>
            <span class="">Rapport de shift</span>
          </button>
          <!-- Primary Flow Action -->
          <button class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-primary hover:bg-primary-container text-on-primary text-label-md font-label-md font-semibold shadow-sm transition-all duration-150"
                  type="button" (click)="onArriveeExpress()">
            <span class="material-symbols-outlined text-lg">verified_user</span>
            <span class="">Arrivée Express / Check-in VIP</span>
          </button>
        </div>
      </div>
      <!-- 2. FOUR ESSENTIAL KPIs (Level 1 Elevation) -->
      <section aria-label="Indicateurs de performance clés" class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-5">
        <!-- KPI 1: Taux d'occupation -->
        <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between text-secondary mb-3">
            <span class="text-label-md font-label-md uppercase tracking-wider">Taux d'occupation</span>
            <div class="w-8 h-8 rounded-lg bg-[#ecfdf5] text-primary flex items-center justify-center">
              <span class="material-symbols-outlined text-lg">hotel</span>
            </div>
          </div>
          <div class="flex items-baseline gap-3">
            <span class="text-display-md font-display-md text-on-surface">88%</span>
            <span class="inline-flex items-center text-label-sm font-label-sm text-primary font-semibold">
              <span class="material-symbols-outlined text-sm">trending_up</span>
              +4.2%
            </span>
          </div>
          <div class="mt-3 flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span class="">44 / 50 chambres allouées</span>
            <span class="text-primary font-medium">6 disponibles</span>
          </div>
          <!-- Tiny progress bar -->
          <div class="w-full bg-surface-container-low h-1.5 rounded-full mt-2.5 overflow-hidden">
            <div class="bg-primary h-full rounded-full" style="width: 88%"></div>
          </div>
        </div>
        <!-- KPI 2: CA du Jour & RevPAR -->
        <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between text-secondary mb-3">
            <span class="text-label-md font-label-md uppercase tracking-wider">Revenu du jour (RevPAR)</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container text-on-surface flex items-center justify-center">
              <span class="material-symbols-outlined text-lg">account_balance_wallet</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface tracking-tight">3 250 000</span>
            <span class="text-body-sm font-body-sm text-secondary font-medium">FCFA</span>
          </div>
          <div class="mt-3 flex items-center justify-between text-body-sm font-body-sm">
            <span class="text-secondary">Objectif journalier à 95%</span>
            <span class="text-primary font-semibold">RevPAR: 65 000 F</span>
          </div>
          <div class="w-full bg-surface-container-low h-1.5 rounded-full mt-2.5 overflow-hidden">
            <div class="bg-primary h-full rounded-full" style="width: 95%"></div>
          </div>
        </div>
        <!-- KPI 3: Arrivées Prévues -->
        <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between text-secondary mb-3">
            <span class="text-label-md font-label-md uppercase tracking-wider">Arrivées prévues</span>
            <div class="w-8 h-8 rounded-lg bg-[#f0fdf4] text-primary flex items-center justify-center">
              <span class="material-symbols-outlined text-lg">luggage</span>
            </div>
          </div>
          <div class="flex items-baseline gap-3">
            <span class="text-display-md font-display-md text-on-surface">14</span>
            <span class="inline-flex items-center px-2 py-0.5 rounded-full text-caption font-caption bg-[#fef3c7] text-[#92400e] font-semibold border border-[#fde68a]">
              3 VIP Élite
            </span>
          </div>
          <div class="mt-3 flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span class="">5 déjà enregistrés</span>
            <span class="text-on-surface font-semibold">9 restants</span>
          </div>
          <div class="w-full bg-surface-container-low h-1.5 rounded-full mt-2.5 overflow-hidden">
            <div class="bg-primary h-full rounded-full" style="width: 35.7%"></div>
          </div>
        </div>
        <!-- KPI 4: Départs Prévus -->
        <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between text-secondary mb-3">
            <span class="text-label-md font-label-md uppercase tracking-wider">Départs prévus</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-high text-secondary flex items-center justify-center">
              <span class="material-symbols-outlined text-lg">flight_takeoff</span>
            </div>
          </div>
          <div class="flex items-baseline gap-3">
            <span class="text-display-md font-display-md text-on-surface">9</span>
            <span class="text-body-sm font-body-sm text-secondary font-medium">Départs ce jour</span>
          </div>
          <div class="mt-3 flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span class="">7 déjà soldés &amp; validés</span>
            <span class="text-error font-medium">2 en attente</span>
          </div>
          <div class="w-full bg-surface-container-low h-1.5 rounded-full mt-2.5 overflow-hidden">
            <div class="bg-primary h-full rounded-full" style="width: 77.7%"></div>
          </div>
        </div>
      </section>
      <!-- 3. MAIN CONTENT SPLIT: ROOM STATUS OVERVIEW + ACTIVE GUEST FLOW + RIGHT RAIL -->
      <div class="grid grid-cols-1 xl:grid-cols-12 gap-6">
        <!-- LEFT & CENTER REGION (8 Cols) -->
        <div class="xl:col-span-8 space-y-6">
          <!-- ROOM STATUS OVERVIEW (Interactive status cards) -->
          <div class="bg-surface-container-lowest p-6 rounded-xl border border-outline-variant shadow-sm">
            <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 pb-4 mb-4 border-b border-outline-variant">
              <div>
                <h2 class="text-headline-md font-headline-md text-on-surface">État du parc des 50 Chambres</h2>
                <p class="text-body-sm font-body-sm text-secondary">Mise à jour automatique des capteurs de serrures et pointage Housekeeping</p>
              </div>
              <a class="inline-flex items-center gap-1 text-label-md font-label-md text-primary font-semibold hover:underline" href="#" (click)="onOuvrirRack($event)">
                <span class="">Ouvrir Rack complet</span>
                <span class="material-symbols-outlined text-base">north_east</span>
              </a>
            </div>
            <!-- 4 State Gauge Cards -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-3.5">
              <!-- Prêtes / Propres -->
              <div class="p-3.5 rounded-lg border border-primary/20 bg-[#ecfdf5]/40 hover:bg-[#ecfdf5] transition-colors">
                <div class="flex items-center justify-between">
                  <span class="text-label-sm font-label-sm uppercase font-semibold text-primary">Prêtes / Propres</span>
                  <span class="w-2.5 h-2.5 rounded-full bg-primary"></span>
                </div>
                <div class="mt-2 flex items-baseline gap-2">
                  <span class="text-display-md font-display-md text-primary">28</span>
                  <span class="text-caption font-caption text-secondary">56% du parc</span>
                </div>
                <span class="text-caption font-caption text-on-surface-variant block mt-1">Inspection gouvernante OK</span>
              </div>
              <!-- Occupées -->
              <div class="p-3.5 rounded-lg border border-secondary/20 bg-[#f1f5f9]/60 hover:bg-[#f1f5f9] transition-colors">
                <div class="flex items-center justify-between">
                  <span class="text-label-sm font-label-sm uppercase font-semibold text-secondary">Occupées</span>
                  <span class="w-2.5 h-2.5 rounded-full bg-secondary"></span>
                </div>
                <div class="mt-2 flex items-baseline gap-2">
                  <span class="text-display-md font-display-md text-on-surface">16</span>
                  <span class="text-caption font-caption text-secondary">Clients en séjour</span>
                </div>
                <span class="text-caption font-caption text-secondary block mt-1">Clefs actives BLE</span>
              </div>
              <!-- Housekeeping -->
              <div class="p-3.5 rounded-lg border border-[#f59e0b]/30 bg-[#fffbeb] hover:bg-[#fef3c7]/60 transition-colors">
                <div class="flex items-center justify-between">
                  <span class="text-label-sm font-label-sm uppercase font-semibold text-[#b45309]">En nettoyage</span>
                  <span class="w-2.5 h-2.5 rounded-full bg-[#f59e0b] animate-pulse"></span>
                </div>
                <div class="mt-2 flex items-baseline gap-2">
                  <span class="text-display-md font-display-md text-[#92400e]">4</span>
                  <span class="text-caption font-caption text-[#b45309]">En cours</span>
                </div>
                <span class="text-caption font-caption text-[#92400e] block mt-1">Équipe Étage 2 et 3</span>
              </div>
              <!-- Maintenance / SAV -->
              <div class="p-3.5 rounded-lg border border-outline-variant bg-surface-container-low hover:bg-surface-container transition-colors">
                <div class="flex items-center justify-between">
                  <span class="text-label-sm font-label-sm uppercase font-semibold text-secondary">Maintenance / SAV</span>
                  <span class="material-symbols-outlined text-base text-primary">check_circle</span>
                </div>
                <div class="mt-2 flex items-baseline gap-2">
                  <span class="text-display-md font-display-md text-on-surface">2</span>
                  <span class="text-caption font-caption text-primary font-semibold">Ch. 102 débloquée</span>
                </div>
                <span class="text-caption font-caption text-secondary block mt-1">1 clim en révision (Ch. 310)</span>
              </div>
            </div>
            <!-- Mini Visual Room Rack Preview (Hospitality Specific Component) -->
            <div class="mt-5 pt-4 border-t border-outline-variant">
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary font-semibold">Aperçu rapide : Étage 1 &amp; VIP</span>
                <span class="text-caption font-caption text-secondary">Légende : Vert (Libre) • Bleu (Occupée) • Ambre (Ménage)</span>
              </div>
              <div class="grid grid-cols-3 sm:grid-cols-6 gap-2.5">
                <!-- Room Card 101 -->
                <div class="bg-surface-container-lowest border border-outline-variant rounded-lg p-2.5 border-l-4 border-l-secondary relative">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-on-surface font-bold">101</span>
                    <span class="text-caption font-caption text-secondary">Deluxe</span>
                  </div>
                  <span class="text-caption font-caption text-secondary mt-1 block truncate">M. Dubois (J-2)</span>
                </div>
                <!-- Room Card 102 - Highlighted VIP Ready -->
                <div class="bg-[#ecfdf5]/30 border border-primary rounded-lg p-2.5 border-l-4 border-l-primary relative ring-1 ring-primary/20">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-primary font-bold">102</span>
                    <span class="text-[10px] font-bold px-1 rounded bg-[#ecfdf5] text-primary">VIP</span>
                  </div>
                  <span class="text-caption font-caption text-primary font-medium mt-1 block truncate">Prête • M. Koné</span>
                </div>
                <!-- Room Card 103 -->
                <div class="bg-surface-container-lowest border border-outline-variant rounded-lg p-2.5 border-l-4 border-l-[#f59e0b] relative">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-on-surface font-bold">103</span>
                    <span class="text-caption font-caption text-secondary">Junior</span>
                  </div>
                  <span class="text-caption font-caption text-[#b45309] mt-1 block truncate">Linge en cours</span>
                </div>
                <!-- Room Card 104 -->
                <div class="bg-surface-container-lowest border border-outline-variant rounded-lg p-2.5 border-l-4 border-l-primary relative">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-on-surface font-bold">104</span>
                    <span class="text-caption font-caption text-secondary">Deluxe</span>
                  </div>
                  <span class="text-caption font-caption text-primary mt-1 block truncate">Prête (Attente)</span>
                </div>
                <!-- Room Card 105 -->
                <div class="bg-surface-container-lowest border border-outline-variant rounded-lg p-2.5 border-l-4 border-l-secondary relative">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-on-surface font-bold">105</span>
                    <span class="text-caption font-caption text-secondary">Suite</span>
                  </div>
                  <span class="text-caption font-caption text-secondary mt-1 block truncate">Koffi A. (J-1)</span>
                </div>
                <!-- Room Card 108 -->
                <div class="bg-surface-container-lowest border border-outline-variant rounded-lg p-2.5 border-l-4 border-l-primary relative">
                  <div class="flex justify-between items-center">
                    <span class="text-headline-sm font-headline-sm text-on-surface font-bold">108</span>
                    <span class="text-caption font-caption text-secondary">Executive</span>
                  </div>
                  <span class="text-caption font-caption text-primary mt-1 block truncate">Prête • Bamba</span>
                </div>
              </div>
            </div>
          </div>
          <!-- ACTIVE GUEST FLOW TABLE (Arrivées & Départs Prioritaires) -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
            <div class="p-5 border-b border-outline-variant flex flex-col sm:flex-row sm:items-center justify-between gap-3">
              <div>
                <h2 class="text-headline-md font-headline-md text-on-surface">Flux des clients prioritaires de la journée</h2>
                <p class="text-body-sm font-body-sm text-secondary">Arrivées imminentes, réceptions VIP et départs à clôturer</p>
              </div>
              <!-- Filter tabs -->
              <div class="inline-flex rounded-lg bg-surface-container-low p-1 border border-outline-variant text-label-sm font-label-sm">
                <button [class]="classeFlux('arrivees')" (click)="onFlux('arrivees')">Toutes les arrivées (14)</button>
                <button [class]="classeFlux('vip')" (click)="onFlux('vip')">VIP uniquement (3)</button>
                <button [class]="classeFlux('departs')" (click)="onFlux('departs')">Départs à traiter (2)</button>
              </div>
            </div>
            <!-- Table -->
            <div class="overflow-x-auto">
              <table aria-label="Liste des flux de réservations du jour" class="w-full text-left border-collapse">
                <thead>
                  <tr class="bg-surface-container-low border-b border-outline-variant text-label-md font-label-md text-secondary uppercase tracking-wider">
                    <th class="py-3 px-4 font-semibold" scope="col">Client &amp; Statut</th>
                    <th class="py-3 px-4 font-semibold" scope="col">Chambre &amp; Catégorie</th>
                    <th class="py-3 px-4 font-semibold" scope="col">Heure Prévue</th>
                    <th class="py-3 px-4 font-semibold" scope="col">Statut Chambre</th>
                    <th class="py-3 px-4 font-semibold" scope="col">Paiement / Garantie</th>
                    <th class="py-3 px-4 font-semibold text-right" scope="col">Actions rapides</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant text-body-md font-body-md">
                  <!-- ROW 1: M. Sékou Koné (VIP Élite 2) - Room 102 (Highlighted) -->
                  <tr *ngIf="estAffichee('vip')" class="bg-[#f0fdf4]/50 hover:bg-[#f0fdf4] transition-colors">
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div class="w-9 h-9 rounded-full bg-[#ecfdf5] border border-primary text-primary flex items-center justify-center font-bold text-sm">
                          SK
                        </div>
                        <div>
                          <div class="flex items-center gap-1.5">
                            <span class="font-semibold text-on-surface">M. Sékou Koné</span>
                            <span class="inline-flex items-center px-2 py-0.5 rounded-full text-[10px] font-bold bg-[#fef3c7] text-[#92400e] border border-[#fde68a]">
                              VIP Élite 2
                            </span>
                          </div>
                          <span class="text-caption font-caption text-secondary">Directeur Régional • Résa #ES-8834</span>
                        </div>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-1.5">
                        <span class="font-bold text-on-surface">Ch. 102</span>
                        <span class="text-caption font-caption text-secondary">(Suite Présidentielle)</span>
                      </div>
                      <span class="text-caption font-caption text-primary font-medium flex items-center gap-1 mt-0.5">
                        <span class="material-symbols-outlined text-xs">task_alt</span>
                        SAV Clim résolu à 15:10
                      </span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">16:30</div>
                      <span class="text-caption font-caption text-secondary">Vol Air Côte d'Ivoire</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-[#ecfdf5] text-primary border border-primary/30 font-semibold">
                        <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                        Prête pour Check-in
                      </span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">Acompte Wave 65 000 F</div>
                      <span class="text-caption font-caption text-primary">Solde carte garantie OK</span>
                    </td>
                    <td class="py-3.5 px-4 text-right">
                      <button class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-primary hover:bg-primary-container text-on-primary text-label-sm font-label-sm font-semibold shadow-xs transition-transform active:scale-95"
                              type="button" (click)="onCheckInExpress('#ES-8834')">
                        <span class="material-symbols-outlined text-base">key</span>
                        <span class="">Check-in Express</span>
                      </button>
                    </td>
                  </tr>
                  <!-- ROW 2: M. Christian Diallo -->
                  <tr *ngIf="estAffichee('arrivees')" class="hover:bg-surface-container-low transition-colors">
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div class="w-9 h-9 rounded-full bg-surface-container text-secondary flex items-center justify-center font-bold text-sm">
                          CD
                        </div>
                        <div>
                          <span class="font-semibold text-on-surface">M. Christian Diallo</span>
                          <span class="text-caption font-caption text-secondary block">Séjour Pro (3 nuits) • #ES-8839</span>
                        </div>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="font-semibold text-on-surface">Ch. 204</span>
                      <span class="text-caption font-caption text-secondary block">Chambre Deluxe Balcon</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">17:15</div>
                      <span class="text-caption font-caption text-secondary">Navette aéroport</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-[#ecfdf5] text-primary border border-primary/30 font-semibold">
                        <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                        Prête
                      </span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">Pré-autorisation CB</div>
                      <span class="text-caption font-caption text-secondary">Mastercard **** 4102</span>
                    </td>
                    <td class="py-3.5 px-4 text-right">
                      <button class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg bg-surface-container hover:bg-surface-container-high text-on-surface text-label-sm font-label-sm font-semibold transition-colors"
                              type="button" (click)="onAttribution('#ES-8839')">
                        <span class="material-symbols-outlined text-base text-secondary">badge</span>
                        <span class="">Attribution</span>
                      </button>
                    </td>
                  </tr>
                  <!-- ROW 3: Mme Fatou Bamba -->
                  <tr *ngIf="estAffichee('arrivees')" class="hover:bg-surface-container-low transition-colors">
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div class="w-9 h-9 rounded-full bg-surface-container text-secondary flex items-center justify-center font-bold text-sm">
                          FB
                        </div>
                        <div>
                          <div class="flex items-center gap-1">
                            <span class="font-semibold text-on-surface">Mme Fatou Bamba</span>
                            <span class="px-1.5 py-0.5 rounded text-[10px] bg-secondary-container text-on-secondary-container font-semibold">Club Fidélité</span>
                          </div>
                          <span class="text-caption font-caption text-secondary">Résa Booking.com • #ES-8841</span>
                        </div>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="font-semibold text-on-surface">Ch. 108</span>
                      <span class="text-caption font-caption text-secondary block">Executive King</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">18:00</div>
                      <span class="text-caption font-caption text-secondary">Arrivée tardive signalée</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-[#ecfdf5] text-primary border border-primary/30 font-semibold">
                        <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                        Prête
                      </span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">Soldé en ligne</div>
                      <span class="text-caption font-caption text-primary font-medium">Reçu émis</span>
                    </td>
                    <td class="py-3.5 px-4 text-right">
                      <button class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg bg-surface-container hover:bg-surface-container-high text-on-surface text-label-sm font-label-sm font-semibold transition-colors"
                              type="button" (click)="onCleMobile('#ES-8841')">
                        <span class="material-symbols-outlined text-base text-secondary">send</span>
                        <span class="">Clé mobile BLE</span>
                      </button>
                    </td>
                  </tr>
                  <!-- ROW 4: M. Jean-Luc Moreau (Départ avec solde en attente) -->
                  <tr *ngIf="estAffichee('departs')" class="hover:bg-surface-container-low transition-colors">
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div class="w-9 h-9 rounded-full bg-surface-container-high text-secondary flex items-center justify-center font-bold text-sm">
                          JM
                        </div>
                        <div>
                          <span class="font-semibold text-on-surface">M. Jean-Luc Moreau</span>
                          <span class="text-caption font-caption text-[#ba1a1a] font-medium block">Départ prévu (Late Check-out 16:00)</span>
                        </div>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="font-semibold text-on-surface">Ch. 305</span>
                      <span class="text-caption font-caption text-secondary block">Junior Suite Mer</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-error">En retard (16:00)</div>
                      <span class="text-caption font-caption text-secondary">Bagages prêts</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-[#fffbeb] text-[#b45309] border border-[#f59e0b]/30 font-semibold">
                        <span class="w-1.5 h-1.5 rounded-full bg-[#f59e0b]"></span>
                        En instance départ
                      </span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="font-semibold text-on-surface">Extras: 42 000 FCFA</div>
                      <span class="text-caption font-caption text-secondary">Minibar &amp; Room service</span>
                    </td>
                    <td class="py-3.5 px-4 text-right">
                      <button class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-on-surface text-label-sm font-label-sm font-semibold transition-colors"
                              type="button" (click)="onFacturerCloturer('Ch. 305')">
                        <span class="material-symbols-outlined text-base text-primary">point_of_sale</span>
                        <span class="">Facturer &amp; Clôturer</span>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- Table Footer Pagination/Status -->
            <div class="p-4 bg-surface-container-low border-t border-outline-variant flex items-center justify-between text-body-sm font-body-sm text-secondary">
              <span class="">Affichage de 4 flux prioritaires sur 23 opérations du jour</span>
              <a class="text-primary font-semibold hover:underline flex items-center gap-1" href="#" (click)="onCarnetArrivees($event)">
                <span class="">Consulter l'intégralité du carnet d'arrivées</span>
                <span class="material-symbols-outlined text-sm">arrow_forward</span>
              </a>
            </div>
          </div>
        </div>
        <!-- RIGHT RAIL (4 Cols: Operational Alerts, Quick Actions, VIP Preferences) -->
        <div class="xl:col-span-4 space-y-6">
          <!-- VIP CONCIERGE SPOTLIGHT: M. SÉKOU KONÉ -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-primary/30 shadow-sm relative overflow-hidden">
            <div class="absolute top-0 right-0 transform translate-x-3 -translate-y-3 w-24 h-24 bg-primary/5 rounded-full pointer-events-none"></div>
            <div class="flex items-center justify-between pb-3 mb-3 border-b border-outline-variant">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-xl">stars</span>
                <span class="text-label-md font-label-md font-bold text-on-surface uppercase tracking-wide">Fiche Conciergerie VIP</span>
              </div>
              <span class="text-caption font-caption bg-[#ecfdf5] text-primary px-2 py-0.5 rounded font-bold">Suite 102</span>
            </div>
            <div class="space-y-3">
              <div class="flex items-start gap-3">
                <div class="w-10 h-10 rounded-full bg-primary/10 text-primary flex items-center justify-center font-bold text-base shrink-0">
                  SK
                </div>
                <div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface font-semibold">M. Sékou Koné</h3>
                  <p class="text-caption font-caption text-secondary">Hôte régulier • 14 séjours en 2024</p>
                </div>
              </div>
              <!-- Preferences Pill List -->
              <div class="bg-surface-container-low p-3 rounded-lg space-y-1.5 text-body-sm font-body-sm">
                <div class="flex items-center gap-2 text-on-surface">
                  <span class="material-symbols-outlined text-base text-primary">thermostat</span>
                  <span class="">Température chambre pré-réglée à <strong>21.5°C</strong></span>
                </div>
                <div class="flex items-center gap-2 text-on-surface">
                  <span class="material-symbols-outlined text-base text-primary">local_cafe</span>
                  <span class="">Café Nespresso Décaféiné réapprovisionné</span>
                </div>
                <div class="flex items-center gap-2 text-on-surface">
                  <span class="material-symbols-outlined text-base text-primary">newspaper</span>
                  <span class="">Presse : Jeune Afrique &amp; Le Monde Eco livrés</span>
                </div>
              </div>
              <div class="flex gap-2 pt-1">
                <button class="flex-1 py-2 px-3 rounded-lg bg-primary hover:bg-primary-container text-on-primary text-label-sm font-label-sm font-semibold transition-all flex items-center justify-center gap-1"
                        type="button" (click)="onAlerterMajordome()">
                  <span class="material-symbols-outlined text-base">room_service</span>
                  <span class="">Alerter majordome</span>
                </button>
                <button class="py-2 px-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container text-secondary text-label-sm font-label-sm font-semibold transition-all"
                        type="button" (click)="onMessageVip()">
                  <span class="material-symbols-outlined text-base">chat</span>
                </button>
              </div>
            </div>
          </div>
          <!-- OPERATIONAL TIMELINE & INCIDENT ALERTS -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm">
            <div class="flex items-center justify-between pb-3 mb-4 border-b border-outline-variant">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-secondary text-lg">notifications_active</span>
                <h3 class="text-headline-sm font-headline-sm text-on-surface">Flux Opérationnel &amp; SAV</h3>
              </div>
              <span class="text-caption font-caption text-secondary">Temps réel</span>
            </div>
            <!-- Timeline items -->
            <div class="space-y-4">
              <!-- Item 1: Resolved SAV Ch 102 -->
              <div class="flex items-start gap-3">
                <div class="w-7 h-7 rounded-full bg-[#ecfdf5] text-primary flex items-center justify-center shrink-0 mt-0.5">
                  <span class="material-symbols-outlined text-sm">check</span>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-body-sm font-body-sm text-on-surface font-semibold truncate">
                    Ticket SAV #402 résolu (Chambre 102)
                  </p>
                  <p class="text-caption font-caption text-secondary">
                    Thermostat réinitialisé avec succès par Mamadou T. (Maintenance) à 15:10.
                  </p>
                  <span class="text-[10px] text-primary font-bold">Chambre libérée pour M. Koné</span>
                </div>
              </div>
              <!-- Item 2: Minibar Restock Alert -->
              <div class="flex items-start gap-3">
                <div class="w-7 h-7 rounded-full bg-[#fffbeb] text-[#b45309] flex items-center justify-center shrink-0 mt-0.5">
                  <span class="material-symbols-outlined text-sm">kitchen</span>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-body-sm font-body-sm text-on-surface font-semibold truncate">
                    Réassort Minibar Étages 2 &amp; 3
                  </p>
                  <p class="text-caption font-caption text-secondary">
                    Demande transmise à l'économat : 12 eaux minérales et champagne Brut 37.5cl.
                  </p>
                  <span class="text-[10px] text-secondary">Il y a 24 min</span>
                </div>
              </div>
              <!-- Item 3: Key BLE generation -->
              <div class="flex items-start gap-3">
                <div class="w-7 h-7 rounded-full bg-surface-container text-secondary flex items-center justify-center shrink-0 mt-0.5">
                  <span class="material-symbols-outlined text-sm">vpn_key</span>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-body-sm font-body-sm text-on-surface font-semibold truncate">
                    Clé digitale BLE générée (Ch. 201)
                  </p>
                  <p class="text-caption font-caption text-secondary">
                    Envoyée par SMS sur le smartphone du client Mme Diop.
                  </p>
                  <span class="text-[10px] text-secondary">Il y a 42 min</span>
                </div>
              </div>
            </div>
          </div>
          <!-- OPERATIONAL SHORTCUTS (Quick Tool Launchers) -->
          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm">
            <h3 class="text-label-md font-label-md font-bold text-on-surface uppercase tracking-wide mb-3">
              Raccourcis Réception
            </h3>
            <div class="grid grid-cols-2 gap-2.5">
              <!-- Action 1: QR Clé BLE -->
              <button class="p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-left transition-all group"
                      type="button" (click)="onRaccourci('scanner-cle')">
                <span class="material-symbols-outlined text-primary text-xl mb-1 group-hover:scale-110 transition-transform block">qr_code_scanner</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface block">Scanner Clé BLE</span>
                <span class="text-[10px] text-secondary">Encodeur comptoir</span>
              </button>
              <!-- Action 2: Facture / Proforma -->
              <button class="p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-left transition-all group"
                      type="button" (click)="onRaccourci('facture')">
                <span class="material-symbols-outlined text-primary text-xl mb-1 group-hover:scale-110 transition-transform block">receipt</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface block">Générer Facture</span>
                <span class="text-[10px] text-secondary">Fiscale &amp; Acompte</span>
              </button>
              <!-- Action 3: Incident SAV -->
              <button class="p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-left transition-all group"
                      type="button" (click)="onRaccourci('sav')">
                <span class="material-symbols-outlined text-[#b45309] text-xl mb-1 group-hover:scale-110 transition-transform block">report_problem</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface block">Déclarer SAV</span>
                <span class="text-[10px] text-secondary">Ticket technicien</span>
              </button>
              <!-- Action 4: Navette VIP -->
              <button class="p-3 rounded-lg border border-outline-variant bg-surface-container-lowest hover:bg-surface-container-low text-left transition-all group"
                      type="button" (click)="onRaccourci('navette')">
                <span class="material-symbols-outlined text-primary text-xl mb-1 group-hover:scale-110 transition-transform block">directions_car</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface block">Navette Félix H-B</span>
                <span class="text-[10px] text-secondary">Planning chauffeur</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class DashboardComponent {
  // ------------------------------------------------- Sélecteur de période
  periode = signal<PeriodeTableau>('aujourdhui');

  private readonly periodeActive =
    'px-3 py-1.5 text-label-md font-label-md rounded-md bg-surface-container-lowest text-on-surface font-semibold shadow-xs';
  private readonly periodeInactive =
    'px-3 py-1.5 text-label-md font-label-md rounded-md text-secondary hover:text-on-surface transition-colors';

  classePeriode(cle: PeriodeTableau): string {
    return this.periode() === cle ? this.periodeActive : this.periodeInactive;
  }

  onPeriode(cle: PeriodeTableau): void {
    this.periode.set(cle);
  }

  // ------------------------------------------- Onglets du flux clients
  flux = signal<FluxClient>('arrivees');

  private readonly fluxActif =
    'px-2.5 py-1 rounded bg-surface-container-lowest font-semibold text-on-surface shadow-xs';
  private readonly fluxInactif =
    'px-2.5 py-1 rounded text-secondary hover:text-on-surface';

  classeFlux(cle: FluxClient): string {
    return this.flux() === cle ? this.fluxActif : this.fluxInactif;
  }

  onFlux(cle: FluxClient): void {
    this.flux.set(cle);
  }

  /** L'onglet « Toutes les arrivées » affiche l'intégralité des flux du jour,
   *  comme dans la maquette ; les deux autres restreignent à leur catégorie. */
  estAffichee(categorie: FluxClient): boolean {
    return this.flux() === 'arrivees' || this.flux() === categorie;
  }

  // ------------------------------------------------------------- Actions
  onRapportShift()   { console.log('[Tableau de bord] rapport de shift'); }
  onArriveeExpress() { console.log('[Tableau de bord] arrivée express / check-in VIP'); }

  onOuvrirRack(event: Event)     { event.preventDefault(); console.log('[Tableau de bord] ouvrir le rack complet'); }
  onCarnetArrivees(event: Event) { event.preventDefault(); console.log('[Tableau de bord] carnet d\'arrivées complet'); }

  onCheckInExpress(ref: string)    { console.log('[Tableau de bord] check-in express :', ref); }
  onAttribution(ref: string)       { console.log('[Tableau de bord] attribution de chambre :', ref); }
  onCleMobile(ref: string)         { console.log('[Tableau de bord] clé mobile BLE :', ref); }
  onFacturerCloturer(ref: string)  { console.log('[Tableau de bord] facturer & clôturer :', ref); }

  onAlerterMajordome() { console.log('[Tableau de bord] alerte majordome — Suite 102'); }
  onMessageVip()       { console.log('[Tableau de bord] message client VIP — M. Sékou Koné'); }

  onRaccourci(cle: string) { console.log('[Tableau de bord] raccourci réception :', cle); }
}
