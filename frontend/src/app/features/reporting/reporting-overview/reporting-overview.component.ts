import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface OngletIndicateur {
  cle: string;
  icon: string;
  libelle: string;
  option?: boolean;
}

@Component({
  selector: 'app-reporting-overview',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="max-w-[1440px] mx-auto space-y-6">

      <!-- HEADER TITLE & MODULAR BANNER -->
      <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Reporting &amp; Analytics Décisionnel</h1>
            <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-primary-fixed text-on-primary-fixed font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              Système Analytique Indépendant
            </span>
          </div>
          <p class="text-body-md font-body-md text-secondary mt-1">
            Indicateurs de performance RevPAR, ADR, mix des canaux et rentabilité par chambre.
          </p>
        </div>
        <!-- Synchronized Modules Info Pill -->
        <div class="flex items-center gap-3 bg-surface-container-lowest border border-outline-variant/80 rounded-xl px-4 py-2.5 shadow-sm">
          <span class="material-symbols-outlined text-primary text-[22px]">hub</span>
          <div class="text-body-sm font-body-sm text-secondary">
            <span class="font-semibold text-on-surface">Modules liés :</span>
            <span class="text-primary font-medium">Finance</span> &amp; <span class="text-primary font-medium">Chambres</span> synchronisés.
          </div>
          <a class="text-label-sm font-label-sm text-primary hover:underline font-semibold flex items-center" href="#" (click)="onAbonnement($event)">
            Abonnement &amp; Modularité <span class="material-symbols-outlined text-[14px] ml-0.5">chevron_right</span>
          </a>
        </div>
      </div>

      <!-- DEPARTMENT TABS / FILTERS -->
      <div class="flex items-center justify-between border-b border-surface-container pb-1">
        <div class="flex items-center space-x-2">
          <button *ngFor="let o of onglets"
                  type="button"
                  [class]="classeOnglet(o)"
                  (click)="onSelectionnerOnglet(o)">
            <span class="material-symbols-outlined text-[18px]">{{ o.icon }}</span>
            <span>{{ o.libelle }}</span>
            <span *ngIf="o.option" class="text-caption font-caption bg-surface-container-high text-secondary px-1.5 py-0.2 rounded text-[9px]">Option</span>
          </button>
        </div>
        <div class="text-caption font-caption text-secondary flex items-center gap-1.5">
          <span class="material-symbols-outlined text-[14px]">schedule</span>
          Dernier rafraîchissement des données : Aujourd'hui à 11:45
        </div>
      </div>

      <!-- 4 GRANDS MÉTRIQUES HÔTELIÈRES CLÉS -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <!-- Metric 1: RevPAR -->
        <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-5 shadow-sm hover:shadow-md transition-shadow">
          <div class="flex items-center justify-between text-secondary mb-2">
            <span class="text-label-md font-label-md uppercase tracking-wider text-secondary">RevPAR (Revenu par ch. dispo)</span>
            <div class="p-1.5 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-[18px]">trending_up</span>
            </div>
          </div>
          <div class="text-display-md font-display-md text-on-surface tracking-tight">
            65 000 <span class="text-headline-sm font-headline-sm text-secondary font-normal">FCFA</span>
          </div>
          <div class="flex items-center gap-2 mt-3 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold">
              <span class="material-symbols-outlined text-[16px] mr-0.5">arrow_upward</span>
              +14.2%
            </span>
            <span class="text-secondary">vs N-1 (Octobre 2023)</span>
          </div>
        </div>
        <!-- Metric 2: ADR -->
        <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-5 shadow-sm hover:shadow-md transition-shadow">
          <div class="flex items-center justify-between text-secondary mb-2">
            <span class="text-label-md font-label-md uppercase tracking-wider text-secondary">ADR (Taux Moyen Journalier)</span>
            <div class="p-1.5 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-[18px]">price_check</span>
            </div>
          </div>
          <div class="text-display-md font-display-md text-on-surface tracking-tight">
            74 000 <span class="text-headline-sm font-headline-sm text-secondary font-normal">FCFA</span>
          </div>
          <div class="flex items-center gap-2 mt-3 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold bg-primary-fixed/40 px-2 py-0.5 rounded">
              Objectif atteint à 105%
            </span>
            <span class="text-secondary">Budget 70 500 F</span>
          </div>
        </div>
        <!-- Metric 3: TO -->
        <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-5 shadow-sm hover:shadow-md transition-shadow">
          <div class="flex items-center justify-between text-secondary mb-2">
            <span class="text-label-md font-label-md uppercase tracking-wider text-secondary">Taux d'Occupation Global</span>
            <div class="p-1.5 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-[18px]">hotel</span>
            </div>
          </div>
          <div class="text-display-md font-display-md text-on-surface tracking-tight">
            87.8%
          </div>
          <div class="flex items-center gap-2 mt-3 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold">
              <span class="material-symbols-outlined text-[16px] mr-0.5">flash_on</span>
              Pic weekend 96%
            </span>
            <span class="text-secondary">Capacité : 54 suites</span>
          </div>
        </div>
        <!-- Metric 4: GOPPAR -->
        <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-5 shadow-sm hover:shadow-md transition-shadow">
          <div class="flex items-center justify-between text-secondary mb-2">
            <span class="text-label-md font-label-md uppercase tracking-wider text-secondary">GOPPAR (Bénéfice Brut / ch.)</span>
            <div class="p-1.5 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-[18px]">account_balance</span>
            </div>
          </div>
          <div class="text-display-md font-display-md text-on-surface tracking-tight">
            41 200 <span class="text-headline-sm font-headline-sm text-secondary font-normal">FCFA</span>
          </div>
          <div class="flex items-center gap-2 mt-3 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold">
              <span class="material-symbols-outlined text-[16px] mr-0.5">north_east</span>
              +8.5%
            </span>
            <span class="text-secondary">Marge nette opérationnelle 63%</span>
          </div>
        </div>
      </div>

      <!-- ANALYTICS CHARTS SECTION (Bento Grid: 2/3 and 1/3) -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Graphique 1: Évolution CA et Taux d'occupation jour par jour -->
        <div class="lg:col-span-2 bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-6 shadow-sm">
          <div class="flex flex-col sm:flex-row sm:items-center justify-between pb-4 border-b border-surface-container gap-3">
            <div>
              <h2 class="text-headline-sm font-headline-sm text-on-surface font-semibold">
                Évolution Journalière du Chiffre d'Affaires &amp; Taux d'Occupation
              </h2>
              <p class="text-caption font-caption text-secondary">
                Données consolidées du 1er au 31 Octobre 2024 — Recettes hébergement et taxes
              </p>
            </div>
            <!-- Chart Legend -->
            <div class="flex items-center gap-4 text-body-sm font-body-sm">
              <div class="flex items-center gap-1.5">
                <span class="w-3 h-3 rounded-full bg-primary"></span>
                <span class="text-on-surface">CA Journalier (FCFA)</span>
              </div>
              <div class="flex items-center gap-1.5">
                <span class="w-3 h-3 rounded-full bg-secondary-fixed-dim"></span>
                <span class="text-secondary">Taux d'occupation (%)</span>
              </div>
            </div>
          </div>
          <!-- SVG Bar & Line Chart -->
          <div class="relative mt-6 h-64 w-full">
            <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 700 200">
              <defs>
                <linearGradient id="primaryGrad" x1="0%" x2="0%" y1="0%" y2="100%">
                  <stop offset="0%" stop-color="#006948" stop-opacity="0.9"></stop>
                  <stop offset="100%" stop-color="#00855d" stop-opacity="0.2"></stop>
                </linearGradient>
              </defs>
              <!-- Grid Lines -->
              <line stroke="#eceef0" stroke-dasharray="4 4" stroke-width="1" x1="0" x2="700" y1="40" y2="40"></line>
              <line stroke="#eceef0" stroke-dasharray="4 4" stroke-width="1" x1="0" x2="700" y1="90" y2="90"></line>
              <line stroke="#eceef0" stroke-dasharray="4 4" stroke-width="1" x1="0" x2="700" y1="140" y2="140"></line>
              <line stroke="#cbd5e1" stroke-width="1" x1="0" x2="700" y1="180" y2="180"></line>
              <!-- Daily Bars (CA) Samples across 31 days -->
              <!-- Group 1: Week 1 -->
              <rect fill="url(#primaryGrad)" height="110" rx="2" width="12" x="25" y="70"></rect>
              <rect fill="url(#primaryGrad)" height="120" rx="2" width="12" x="45" y="60"></rect>
              <rect fill="url(#primaryGrad)" height="105" rx="2" width="12" x="65" y="75"></rect>
              <rect fill="url(#primaryGrad)" height="125" rx="2" width="12" x="85" y="55"></rect>
              <rect fill="#006948" height="145" rx="2" width="12" x="105" y="35"></rect>
              <rect fill="#006948" height="155" rx="2" width="12" x="125" y="25"></rect>
              <rect fill="#006948" height="150" rx="2" width="12" x="145" y="30"></rect>
              <!-- Group 2: Week 2 -->
              <rect fill="url(#primaryGrad)" height="115" rx="2" width="12" x="175" y="65"></rect>
              <rect fill="url(#primaryGrad)" height="110" rx="2" width="12" x="195" y="70"></rect>
              <rect fill="url(#primaryGrad)" height="120" rx="2" width="12" x="215" y="60"></rect>
              <rect fill="url(#primaryGrad)" height="130" rx="2" width="12" x="235" y="50"></rect>
              <rect fill="#006948" height="150" rx="2" width="12" x="255" y="30"></rect>
              <rect fill="#006948" height="160" rx="2" width="12" x="275" y="20"></rect>
              <rect fill="#006948" height="155" rx="2" width="12" x="295" y="25"></rect>
              <!-- Group 3: Week 3 -->
              <rect fill="url(#primaryGrad)" height="100" rx="2" width="12" x="325" y="80"></rect>
              <rect fill="url(#primaryGrad)" height="105" rx="2" width="12" x="345" y="75"></rect>
              <rect fill="url(#primaryGrad)" height="115" rx="2" width="12" x="365" y="65"></rect>
              <rect fill="url(#primaryGrad)" height="135" rx="2" width="12" x="385" y="45"></rect>
              <rect fill="#006948" height="145" rx="2" width="12" x="405" y="35"></rect>
              <rect fill="#006948" height="158" rx="2" width="12" x="425" y="22"></rect>
              <rect fill="#006948" height="152" rx="2" width="12" x="445" y="28"></rect>
              <!-- Group 4: Week 4 -->
              <rect fill="url(#primaryGrad)" height="120" rx="2" width="12" x="475" y="60"></rect>
              <rect fill="url(#primaryGrad)" height="125" rx="2" width="12" x="495" y="55"></rect>
              <rect fill="url(#primaryGrad)" height="115" rx="2" width="12" x="515" y="65"></rect>
              <rect fill="url(#primaryGrad)" height="140" rx="2" width="12" x="535" y="40"></rect>
              <rect fill="#006948" height="155" rx="2" width="12" x="555" y="25"></rect>
              <rect fill="#006948" height="162" rx="2" width="12" x="575" y="18"></rect>
              <rect fill="#006948" height="158" rx="2" width="12" x="595" y="22"></rect>
              <!-- Final Days -->
              <rect fill="url(#primaryGrad)" height="130" rx="2" width="12" x="625" y="50"></rect>
              <rect fill="url(#primaryGrad)" height="135" rx="2" width="12" x="645" y="45"></rect>
              <rect fill="#006948" height="155" rx="2" width="12" x="665" y="25"></rect>
              <!-- Overlay Polyline: Taux d'occupation -->
              <polyline fill="none" points="
                  31,90  51,80  71,95  91,70 111,50 131,38 151,45
                  181,85 201,88 221,78 241,65 261,42 281,30 301,35
                  331,100 351,95 371,85 391,60 411,48 431,34 451,40
                  481,75 501,70 521,80 541,55 561,38 581,28 601,32
                  631,65 651,60 671,35
                " stroke="#565e74" stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5"></polyline>
            </svg>
            <!-- Bottom X-axis labels -->
            <div class="flex justify-between text-[11px] text-secondary font-label-sm pt-2">
              <span>01 Oct</span>
              <span>05 Oct</span>
              <span>10 Oct</span>
              <span>15 Oct</span>
              <span>20 Oct</span>
              <span>25 Oct</span>
              <span>31 Oct</span>
            </div>
          </div>
          <!-- Insight summary below chart -->
          <div class="mt-4 pt-4 border-t border-surface-container flex items-center justify-between text-body-sm font-body-sm">
            <div class="flex items-center gap-2">
              <span class="inline-block w-2 h-2 rounded-full bg-primary"></span>
              <span class="text-on-surface">Record mensuel atteint le <strong>Samedi 26 Octobre</strong> (3 980 000 FCFA avec 98% d'occupation).</span>
            </div>
            <span class="text-primary font-semibold cursor-pointer hover:underline" (click)="onJournalVentes()">Voir le journal des ventes →</span>
          </div>
        </div>

        <!-- Graphique 2: Donut Mix des canaux de réservation -->
        <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-6 shadow-sm flex flex-col justify-between">
          <div>
            <div class="flex items-center justify-between pb-3 border-b border-surface-container">
              <h2 class="text-headline-sm font-headline-sm text-on-surface font-semibold">Mix des Canaux</h2>
              <span class="text-caption font-caption text-secondary">Oct. 2024</span>
            </div>
            <!-- Donut Visual (SVG) -->
            <div class="relative flex items-center justify-center my-6">
              <svg class="transform -rotate-90" height="180" viewBox="0 0 180 180" width="180">
                <!-- Background Ring -->
                <circle cx="90" cy="90" fill="none" r="68" stroke="#eceef0" stroke-width="22"></circle>
                <!-- Direct Front-Desk 42% -->
                <circle cx="90" cy="90" fill="none" r="68" stroke="#006948" stroke-dasharray="179.4 427.25" stroke-dashoffset="0" stroke-width="22"></circle>
                <!-- Site Web Mobile 28% -->
                <circle cx="90" cy="90" fill="none" r="68" stroke="#00855d" stroke-dasharray="119.6 427.25" stroke-dashoffset="-179.4" stroke-width="22"></circle>
                <!-- Booking / OTA 18% -->
                <circle cx="90" cy="90" fill="none" r="68" stroke="#bec6e0" stroke-dasharray="76.9 427.25" stroke-dashoffset="-299" stroke-width="22"></circle>
                <!-- Corporate Africom 12% -->
                <circle cx="90" cy="90" fill="none" r="68" stroke="#565e74" stroke-dasharray="51.3 427.25" stroke-dashoffset="-375.9" stroke-width="22"></circle>
              </svg>
              <!-- Central Metric -->
              <div class="absolute flex flex-col items-center justify-center text-center">
                <span class="text-caption font-caption uppercase tracking-wider text-secondary">Direct + Web</span>
                <span class="text-headline-lg font-headline-lg font-bold text-primary">70%</span>
                <span class="text-[10px] text-secondary">Économie commissions</span>
              </div>
            </div>
            <!-- Legend breakdown -->
            <div class="space-y-2.5">
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2">
                  <span class="w-2.5 h-2.5 rounded-full bg-primary"></span>
                  <span class="text-on-surface font-medium">Direct Front-Desk</span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-on-surface font-bold">42%</span>
                  <span class="text-caption text-secondary">(1 428 nuits)</span>
                </div>
              </div>
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2">
                  <span class="w-2.5 h-2.5 rounded-full bg-primary-container"></span>
                  <span class="text-on-surface font-medium">Site web mobile</span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-on-surface font-bold">28%</span>
                  <span class="text-caption text-secondary">(952 nuits)</span>
                </div>
              </div>
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2">
                  <span class="w-2.5 h-2.5 rounded-full bg-secondary-fixed-dim"></span>
                  <span class="text-on-surface font-medium">Booking / OTA</span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-on-surface font-bold">18%</span>
                  <span class="text-caption text-secondary">(612 nuits)</span>
                </div>
              </div>
              <div class="flex items-center justify-between text-body-sm font-body-sm">
                <div class="flex items-center gap-2">
                  <span class="w-2.5 h-2.5 rounded-full bg-secondary"></span>
                  <span class="text-on-surface font-medium">Corporate Africom</span>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-on-surface font-bold">12%</span>
                  <span class="text-caption text-secondary">(408 nuits)</span>
                </div>
              </div>
            </div>
          </div>
          <!-- Bottom micro-action -->
          <div class="pt-4 border-t border-surface-container mt-4">
            <button type="button"
                    class="w-full py-2 px-3 rounded-lg bg-surface-container-low hover:bg-surface-container text-primary font-label-md text-label-md font-semibold transition-colors flex items-center justify-center gap-1.5"
                    (click)="onAjusterContingents()">
              <span class="material-symbols-outlined text-[16px]">tune</span>
              <span>Ajuster les contingents OTA</span>
            </button>
          </div>
        </div>
      </div>

      <!-- TABLEAU DÉTAILLÉ : PERFORMANCE PAR CATÉGORIE DE CHAMBRE -->
      <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl shadow-sm overflow-hidden">
        <div class="p-5 border-b border-surface-container flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <h2 class="text-headline-sm font-headline-sm text-on-surface font-semibold">
              Performance Analytique par Catégorie de Chambre
            </h2>
            <p class="text-body-sm font-body-sm text-secondary">
              Suivi détaillé des ventes, du chiffre d'affaires cumulé et de la satisfaction SAV par type d'hébergement.
            </p>
          </div>
          <!-- Search or filter inside table -->
          <div class="flex items-center gap-3">
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-2.5 text-secondary text-[18px]">search</span>
              <input class="pl-9 pr-3 py-1.5 rounded-lg border border-outline-variant text-body-sm font-body-sm bg-surface-container-lowest focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary w-48"
                     placeholder="Filtrer une suite..."
                     type="text"
                     [(ngModel)]="filtre" />
            </div>
            <button type="button" class="p-2 rounded-lg border border-outline-variant hover:bg-surface-container-low text-secondary" (click)="onFiltrer()">
              <span class="material-symbols-outlined text-[18px]">filter_list</span>
            </button>
          </div>
        </div>
        <!-- Table Data -->
        <div class="overflow-x-auto">
          <table class="w-full text-left border-collapse">
            <thead>
              <tr class="bg-surface-container-low border-b border-surface-container text-label-md font-label-md text-secondary">
                <th class="py-3.5 px-6">Catégorie de Chambre</th>
                <th class="py-3.5 px-4 text-center">Nbre Chambres</th>
                <th class="py-3.5 px-4 text-right">Nuits Vendues</th>
                <th class="py-3.5 px-4 text-right">Taux d'Occ.</th>
                <th class="py-3.5 px-4 text-right">Prix Moyen (ADR)</th>
                <th class="py-3.5 px-6 text-right">Chiffre d'Affaires</th>
                <th class="py-3.5 px-4 text-center">Retours SAV</th>
                <th class="py-3.5 px-4 text-center">Statut Rentabilité</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-surface-container text-body-md font-body-md text-on-surface">
              <!-- Row 1: Suite Junior Océan -->
              <tr class="hover:bg-surface-container-low/50 transition-colors" *ngIf="estVisible('Suite Junior Océan')">
                <td class="py-4 px-6 font-semibold text-on-surface flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-primary-fixed/30 text-primary flex items-center justify-center font-bold">
                    <span class="material-symbols-outlined text-[18px]">water</span>
                  </div>
                  <div>
                    <div class="font-bold text-on-surface">Suite Junior Océan</div>
                    <div class="text-caption font-caption text-secondary">Vue panoramique lagune Ébrié &amp; Océan</div>
                  </div>
                </td>
                <td class="py-4 px-4 text-center font-medium">18 suites</td>
                <td class="py-4 px-4 text-right font-semibold">512 nuits</td>
                <td class="py-4 px-4 text-right">
                  <span class="text-primary font-bold">91.7%</span>
                </td>
                <td class="py-4 px-4 text-right text-secondary">95 000 FCFA</td>
                <td class="py-4 px-6 text-right font-bold text-on-surface">
                  48 640 000 <span class="text-caption font-normal text-secondary">FCFA</span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-surface-container text-primary font-medium">
                    0.8% <span class="material-symbols-outlined text-[14px] ml-1 text-primary">check_circle</span>
                  </span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2 py-0.5 rounded text-label-sm font-label-sm bg-primary-fixed text-on-primary-fixed font-semibold">
                    Excellente
                  </span>
                </td>
              </tr>
              <!-- Row 2: Chambre Supérieure Balcon -->
              <tr class="hover:bg-surface-container-low/50 transition-colors" *ngIf="estVisible('Chambre Supérieure Balcon')">
                <td class="py-4 px-6 font-semibold text-on-surface flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-surface-container text-secondary flex items-center justify-center font-bold">
                    <span class="material-symbols-outlined text-[18px]">balcony</span>
                  </div>
                  <div>
                    <div class="font-bold text-on-surface">Chambre Supérieure Balcon</div>
                    <div class="text-caption font-caption text-secondary">Étage 2 à 5 — Balcon privatif</div>
                  </div>
                </td>
                <td class="py-4 px-4 text-center font-medium">20 chambres</td>
                <td class="py-4 px-4 text-right font-semibold">544 nuits</td>
                <td class="py-4 px-4 text-right">
                  <span class="text-primary font-bold">87.7%</span>
                </td>
                <td class="py-4 px-4 text-right text-secondary">68 000 FCFA</td>
                <td class="py-4 px-6 text-right font-bold text-on-surface">
                  36 992 000 <span class="text-caption font-normal text-secondary">FCFA</span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-surface-container text-secondary font-medium">
                    1.4% <span class="material-symbols-outlined text-[14px] ml-1">build</span>
                  </span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2 py-0.5 rounded text-label-sm font-label-sm bg-surface-container-high text-on-surface font-semibold">
                    Normale
                  </span>
                </td>
              </tr>
              <!-- Row 3: Suite Présidentielle -->
              <tr class="hover:bg-surface-container-low/50 transition-colors bg-primary-fixed/5" *ngIf="estVisible('Suite Présidentielle')">
                <td class="py-4 px-6 font-semibold text-on-surface flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-primary text-on-primary flex items-center justify-center font-bold">
                    <span class="material-symbols-outlined text-[18px]">star</span>
                  </div>
                  <div>
                    <div class="font-bold text-on-surface flex items-center gap-2">
                      Suite Présidentielle
                      <span class="text-caption bg-primary text-on-primary px-1.5 rounded text-[9px] uppercase font-bold">VIP</span>
                    </div>
                    <div class="text-caption font-caption text-secondary">Dernier étage — Service majordome dédié</div>
                  </div>
                </td>
                <td class="py-4 px-4 text-center font-medium">4 suites</td>
                <td class="py-4 px-4 text-right font-semibold">108 nuits</td>
                <td class="py-4 px-4 text-right">
                  <span class="text-primary font-bold">87.1%</span>
                </td>
                <td class="py-4 px-4 text-right text-secondary">240 000 FCFA</td>
                <td class="py-4 px-6 text-right font-bold text-primary">
                  25 920 000 <span class="text-caption font-normal text-secondary">FCFA</span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-surface-container text-primary font-medium">
                    0.0% <span class="material-symbols-outlined text-[14px] ml-1 text-primary">verified</span>
                  </span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2 py-0.5 rounded text-label-sm font-label-sm bg-primary-fixed text-on-primary-fixed font-semibold">
                    Optimale
                  </span>
                </td>
              </tr>
              <!-- Row 4: Standard Jardin -->
              <tr class="hover:bg-surface-container-low/50 transition-colors" *ngIf="estVisible('Standard Jardin')">
                <td class="py-4 px-6 font-semibold text-on-surface flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-surface-container text-secondary flex items-center justify-center font-bold">
                    <span class="material-symbols-outlined text-[18px]">yard</span>
                  </div>
                  <div>
                    <div class="font-bold text-on-surface">Standard Jardin</div>
                    <div class="text-caption font-caption text-secondary">Rez-de-jardin — Accès patio fleuri</div>
                  </div>
                </td>
                <td class="py-4 px-4 text-center font-medium">12 chambres</td>
                <td class="py-4 px-4 text-right font-semibold">306 nuits</td>
                <td class="py-4 px-4 text-right">
                  <span class="text-secondary font-bold">82.3%</span>
                </td>
                <td class="py-4 px-4 text-right text-secondary">48 000 FCFA</td>
                <td class="py-4 px-6 text-right font-bold text-on-surface">
                  14 688 000 <span class="text-caption font-normal text-secondary">FCFA</span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-error-container text-on-error-container font-medium">
                    3.2% <span class="material-symbols-outlined text-[14px] ml-1 text-error">priority_high</span>
                  </span>
                </td>
                <td class="py-4 px-4 text-center">
                  <span class="inline-flex items-center px-2 py-0.5 rounded text-label-sm font-label-sm bg-surface-container text-secondary font-semibold">
                    Sous veille SAV
                  </span>
                </td>
              </tr>
            </tbody>
            <!-- Table Footer / Total -->
            <tfoot>
              <tr class="bg-surface-container-low font-bold text-on-surface border-t-2 border-outline-variant">
                <td class="py-4 px-6">Total Général Hôtelier (54 clés)</td>
                <td class="py-4 px-4 text-center">54 chambres</td>
                <td class="py-4 px-4 text-right font-extrabold text-primary">1 470 nuits</td>
                <td class="py-4 px-4 text-right font-extrabold text-primary">87.8%</td>
                <td class="py-4 px-4 text-right">74 000 FCFA (Moy.)</td>
                <td class="py-4 px-6 text-right text-headline-sm font-headline-sm text-primary font-bold">
                  126 240 000 <span class="text-caption font-normal text-secondary">FCFA</span>
                </td>
                <td class="py-4 px-4 text-center font-medium text-secondary">1.3%</td>
                <td class="py-4 px-4 text-center">
                  <span class="text-caption bg-primary-fixed/50 text-on-primary-fixed px-2 py-1 rounded font-bold">Performant</span>
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>

      <!-- ENCART D'INFORMATION MODULAIRE SAAS -->
      <div class="rounded-xl p-5 border border-outline-variant/80 bg-surface-container-low/60 flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div class="flex items-start gap-3.5">
          <div class="p-2 rounded-lg bg-surface-container-lowest text-primary border border-outline-variant/60 shadow-xs">
            <span class="material-symbols-outlined text-[24px]">sync_saved_locally</span>
          </div>
          <div>
            <h4 class="text-label-lg font-label-lg font-bold text-on-surface">Système de reporting synchronisé en temps réel</h4>
            <p class="text-body-sm font-body-sm text-secondary mt-0.5">
              Ce module est interconnecté aux bases des modules <strong class="text-on-surface">Finance</strong> et <strong class="text-on-surface">Chambres &amp; Housekeeping</strong>. Si le module <em>Restauration &amp; Room Service</em> est activé ultérieurement depuis le menu <em>Abonnement &amp; Modularité</em>, les métriques F&amp;B et Ticket Moyen par couvert s'ajouteront automatiquement à cet écran sans reconfiguration.
            </p>
          </div>
        </div>
        <div class="flex items-center gap-3 shrink-0">
          <button type="button"
                  class="px-4 py-2 rounded-lg bg-surface-container-lowest border border-outline-variant text-on-surface hover:bg-surface-container-low font-label-md text-label-md transition-colors"
                  (click)="onDocumentationApi()">
            Documentation API Métriques
          </button>
          <button type="button"
                  class="px-4 py-2 rounded-lg bg-primary hover:bg-primary-container text-on-primary font-label-md text-label-md transition-colors"
                  (click)="onGererModules()">
            Gérer mes modules
          </button>
        </div>
      </div>

    </div>
  `,
})
export class ReportingOverviewComponent {
  onglets: OngletIndicateur[] = [
    { cle: 'tous',        icon: 'tune',                     libelle: 'Tous les indicateurs' },
    { cle: 'hebergement', icon: 'king_bed',                 libelle: 'Hébergement & Chambres' },
    { cle: 'restauration', icon: 'restaurant',              libelle: 'Restauration & Room Service', option: true },
    { cle: 'finance',     icon: 'account_balance_wallet',   libelle: 'Finance & Encaissements' },
  ];

  ongletActif = signal<string>('tous');

  filtre = '';

  private readonly ongletActifClasses =
    'px-4 py-2 rounded-lg bg-surface-container-lowest text-primary font-label-md font-semibold shadow-xs border border-outline-variant/60 flex items-center gap-2';
  private readonly ongletInactifClasses =
    'px-4 py-2 rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container-lowest font-label-md transition-colors flex items-center gap-2';

  classeOnglet(onglet: OngletIndicateur): string {
    return this.ongletActif() === onglet.cle ? this.ongletActifClasses : this.ongletInactifClasses;
  }

  onSelectionnerOnglet(onglet: OngletIndicateur): void {
    this.ongletActif.set(onglet.cle);
    console.log('Onglet indicateurs sélectionné :', onglet.cle);
  }

  estVisible(categorie: string): boolean {
    const q = this.filtre.trim().toLowerCase();
    return q === '' || categorie.toLowerCase().includes(q);
  }

  onFiltrer(): void {
    console.log('Filtres avancés du tableau de performance');
  }

  onAbonnement(event: Event): void {
    event.preventDefault();
    console.log('Ouverture Abonnement & Modularité');
  }

  onJournalVentes(): void {
    console.log('Ouverture du journal des ventes');
  }

  onAjusterContingents(): void {
    console.log('Ajustement des contingents OTA');
  }

  onDocumentationApi(): void {
    console.log('Ouverture documentation API Métriques');
  }

  onGererModules(): void {
    console.log('Gestion des modules SaaS');
  }
}
