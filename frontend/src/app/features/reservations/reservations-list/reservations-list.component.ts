import { Component, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';

type OngletReservation =
  | 'toutes'
  | 'confirmees'
  | 'en-sejour'
  | 'arrivees-jour'
  | 'attente-garantie'
  | 'annulees';

@Component({
  selector: 'app-reservations-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="flex-1 flex flex-col bg-surface relative">
      <!-- Top Sub-Header: Title, Filter Chips, Date Range & Fast Actions -->
      <section class="p-6 pb-4 bg-surface-container-lowest border-b border-outline-variant shadow-sm">
        <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
          <div>
            <div class="flex items-center gap-3">
              <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Gestion des Réservations</h1>
              <span class="px-2.5 py-0.5 rounded-full bg-primary-fixed text-on-primary-fixed-variant text-label-sm font-label-sm">Saison Haute</span>
            </div>
            <p class="text-body-sm font-body-sm text-secondary mt-0.5">Supervisez les arrivées, confirmez les départs et orchestrez les séjours de prestige.</p>
          </div>
          <!-- Filter Action Bar -->
          <div class="flex items-center flex-wrap gap-3">
            <!-- Date Range Selector -->
            <div class="flex items-center gap-2 bg-surface-container-low border border-outline-variant px-3 py-2 rounded-lg text-on-surface text-label-md font-label-md">
              <span class="material-symbols-outlined text-secondary text-base">date_range</span>
              <span class="">{{ plageDates }}</span>
              <span class="material-symbols-outlined text-secondary text-sm ml-1">keyboard_arrow_down</span>
            </div>
            <!-- Direct Open Slideover Button -->
            <button class="bg-primary hover:bg-primary-container text-on-primary px-4 py-2 rounded-lg font-label-md text-label-md flex items-center gap-2 shadow-sm transition-all duration-150 transform hover:-translate-y-0.5"
                    (click)="openForm.set(true)">
              <span class="material-symbols-outlined text-lg">add_circle</span>
              <span class="">+ Nouvelle Réservation</span>
            </button>
          </div>
        </div>
        <!-- Segmented Status Tabs & In-table Search Bar -->
        <div class="mt-5 flex flex-col sm:flex-row items-stretch sm:items-center justify-between gap-4">
          <!-- Tabs -->
          <div class="flex items-center gap-1 overflow-x-auto custom-scrollbar pb-1">
            <button [class]="classeOnglet('toutes')" (click)="onSelectionnerOnglet('toutes')">
              Toutes (42)
            </button>
            <button [class]="classeOnglet('confirmees')" (click)="onSelectionnerOnglet('confirmees')">
              Confirmées (28)
            </button>
            <button [class]="classeOngletPastille('en-sejour')" (click)="onSelectionnerOnglet('en-sejour')">
              <span class="w-2 h-2 rounded-full bg-primary"></span>
              En séjour / Enregistrées (11)
            </button>
            <button [class]="classeOngletPastille('arrivees-jour')" (click)="onSelectionnerOnglet('arrivees-jour')">
              <span class="w-2 h-2 rounded-full bg-secondary"></span>
              Arrivées du jour (7)
            </button>
            <button [class]="classeOngletPastille('attente-garantie')" (click)="onSelectionnerOnglet('attente-garantie')">
              <span class="w-2 h-2 rounded-full bg-amber-500"></span>
              En attente de garantie (3)
            </button>
            <button [class]="classeOnglet('annulees')" (click)="onSelectionnerOnglet('annulees')">
              Annulées (2)
            </button>
          </div>
          <!-- Filter quick search -->
          <div class="relative w-72 flex-shrink-0">
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-secondary text-base">filter_list</span>
            <input class="w-full h-8 pl-8 pr-3 text-body-sm font-body-sm bg-surface-container-low border border-outline-variant rounded-lg placeholder-secondary focus:outline-none focus:border-primary"
                   placeholder="Filtrer nom, #résa ou tél..." type="text" [(ngModel)]="filtre" name="filtre">
          </div>
        </div>
      </section>
      <!-- Content Workspace Grid (Table & Operational Details) -->
      <div class="flex-1 overflow-y-auto custom-scrollbar p-6 space-y-6">
        <!-- KPI Snapshot row -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex items-center justify-between">
            <div>
              <span class="text-caption font-caption text-secondary uppercase font-semibold">Arrivées Prévues</span>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold mt-1">7 Clients</div>
              <span class="text-caption font-caption text-primary flex items-center gap-1 mt-0.5">
                <span class="material-symbols-outlined text-xs">check_circle</span> 4 déjà pré-enregistrés
              </span>
            </div>
            <div class="w-10 h-10 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined">flight_land</span>
            </div>
          </div>
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex items-center justify-between">
            <div>
              <span class="text-caption font-caption text-secondary uppercase font-semibold">Revenu Portefeuille (Jour)</span>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold mt-1">4 850 000 F</div>
              <span class="text-caption font-caption text-primary flex items-center gap-1 mt-0.5">
                <span class="material-symbols-outlined text-xs">trending_up</span> +14% vs hier
              </span>
            </div>
            <div class="w-10 h-10 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined">account_balance_wallet</span>
            </div>
          </div>
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex items-center justify-between">
            <div>
              <span class="text-caption font-caption text-secondary uppercase font-semibold">Garanties en attente</span>
              <div class="text-headline-lg font-headline-lg text-error font-bold mt-1">3 Dossiers</div>
              <span class="text-caption font-caption text-secondary flex items-center gap-1 mt-0.5">
                Relance WhatsApp requise
              </span>
            </div>
            <div class="w-10 h-10 rounded-lg bg-surface-container-low flex items-center justify-center text-secondary">
              <span class="material-symbols-outlined">pending_actions</span>
            </div>
          </div>
          <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm flex items-center justify-between">
            <div>
              <span class="text-caption font-caption text-secondary uppercase font-semibold">Gouvernante / Chambres</span>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold mt-1">19 Prêtes</div>
              <span class="text-caption font-caption text-primary flex items-center gap-1 mt-0.5">
                3 en cours d'inspection
              </span>
            </div>
            <div class="w-10 h-10 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined">cleaning_services</span>
            </div>
          </div>
        </div>
        <!-- Main Hotel Bookings Table Card -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
          <div class="px-6 py-4 border-b border-outline-variant flex items-center justify-between">
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">table_chart</span>
              <h2 class="text-headline-sm font-headline-sm text-on-surface">Dossiers de Réservations Récents</h2>
            </div>
            <div class="flex items-center gap-2 text-label-sm font-label-sm text-secondary">
              <span class="material-symbols-outlined text-base">autorenew</span>
              <span class="">Mise à jour en temps réel</span>
            </div>
          </div>
          <div class="overflow-x-auto">
            <table class="w-full text-left border-collapse">
              <thead>
                <tr class="bg-surface-container-low border-b border-outline-variant text-label-md font-label-md text-secondary">
                  <th class="py-3 px-4 font-semibold">Réf Dossier</th>
                  <th class="py-3 px-4 font-semibold">Client &amp; Contact</th>
                  <th class="py-3 px-4 font-semibold">Chambre Affectée</th>
                  <th class="py-3 px-4 font-semibold">Dates Séjour</th>
                  <th class="py-3 px-4 font-semibold">Total &amp; Règlement</th>
                  <th class="py-3 px-4 font-semibold">Statut Opérationnel</th>
                  <th class="py-3 px-4 font-semibold text-right">Raccourcis Actions</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-outline-variant text-body-sm font-body-sm">
                <!-- Row 1: M. Sékou Koné (VIP 2) -->
                <tr *ngIf="estAffichee('arrivees-jour')" class="hover:bg-surface-container-low/60 transition-colors group">
                  <td class="py-3.5 px-4 font-mono font-bold text-on-surface">
                    <div class="flex items-center gap-1.5">
                      <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                      #ES-9921
                    </div>
                    <span class="text-caption font-caption text-secondary">Direct (Web Engine)</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-full bg-primary-fixed text-on-primary-fixed flex items-center justify-center font-bold text-xs">
                        SK
                      </div>
                      <div>
                        <div class="flex items-center gap-1.5">
                          <span class="font-bold text-on-surface">M. Sékou Koné</span>
                          <span class="px-1.5 py-0.2 rounded bg-surface-container-highest text-caption font-caption text-primary font-bold border border-primary/20">VIP 2</span>
                        </div>
                        <div class="text-caption font-caption text-secondary flex items-center gap-2">
                          <span class="">+225 07 88 12 40</span>
                          <span class="">•</span>
                          <span class="">Abidjan, CI</span>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-semibold text-on-surface">Ch. 102 — King Émeraude</div>
                    <span class="text-caption font-caption text-secondary">Étage 1 • Vue Lagune</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="text-on-surface font-semibold">24 Oct — 27 Oct</div>
                    <span class="text-caption font-caption text-secondary">3 nuits • 2 adultes</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-bold text-on-surface">540 000 FCFA</div>
                    <span class="inline-flex items-center gap-1 text-caption font-caption text-primary font-semibold bg-surface-container px-2 py-0.5 rounded">
                      <span class="material-symbols-outlined text-xs">smartphone</span> Wave CI (Soldé)
                    </span>
                  </td>
                  <td class="py-3.5 px-4">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-emerald-50 text-primary border border-primary/30">
                      <span class="w-1.5 h-1.5 rounded-full bg-primary animate-ping"></span>
                      Arrivée imminente (14h00)
                    </span>
                  </td>
                  <td class="py-3.5 px-4 text-right">
                    <div class="flex items-center justify-end gap-1">
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Check-in express" (click)="onCheckInExpress('#ES-9921')">
                        <span class="material-symbols-outlined text-lg">key</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Confirmation WhatsApp" (click)="onMessage('#ES-9921')">
                        <span class="material-symbols-outlined text-lg">chat</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Imprimer fiche de police / contrat" (click)="onImprimer('#ES-9921')">
                        <span class="material-symbols-outlined text-lg">print</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-on-surface hover:bg-surface-container rounded-lg transition-colors" title="Détails du dossier" (click)="onDetails('#ES-9921')">
                        <span class="material-symbols-outlined text-lg">more_vert</span>
                      </button>
                    </div>
                  </td>
                </tr>
                <!-- Row 2: Mme Claire Belmont -->
                <tr *ngIf="estAffichee('en-sejour')" class="hover:bg-surface-container-low/60 transition-colors group">
                  <td class="py-3.5 px-4 font-mono font-bold text-on-surface">
                    <div class="flex items-center gap-1.5">
                      <span class="w-1.5 h-1.5 rounded-full bg-blue-500"></span>
                      #ES-9920
                    </div>
                    <span class="text-caption font-caption text-secondary">Corporate (TotalEnergies)</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-full bg-secondary-fixed text-on-secondary-fixed flex items-center justify-center font-bold text-xs">
                        CB
                      </div>
                      <div>
                        <div class="flex items-center gap-1.5">
                          <span class="font-bold text-on-surface">Mme Claire Belmont</span>
                        </div>
                        <div class="text-caption font-caption text-secondary flex items-center gap-2">
                          <span class="">+33 6 12 34 56 78</span>
                          <span class="">•</span>
                          <span class="">Paris, FR</span>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-semibold text-on-surface">Ch. 204 — Suite Junior</div>
                    <span class="text-caption font-caption text-secondary">Étage 2 • Balcon Privatif</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="text-on-surface font-semibold">24 Oct — 30 Oct</div>
                    <span class="text-caption font-caption text-secondary">6 nuits • 1 adulte</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-bold text-on-surface">1 080 000 FCFA</div>
                    <span class="inline-flex items-center gap-1 text-caption font-caption text-secondary font-semibold bg-surface-container px-2 py-0.5 rounded">
                      <span class="material-symbols-outlined text-xs">credit_card</span> Visa Corp (Prise en charge)
                    </span>
                  </td>
                  <td class="py-3.5 px-4">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-blue-50 text-blue-800 border border-blue-200">
                      <span class="w-1.5 h-1.5 rounded-full bg-blue-600"></span>
                      En séjour • Ch. 204
                    </span>
                  </td>
                  <td class="py-3.5 px-4 text-right">
                    <div class="flex items-center justify-end gap-1">
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Extrait de compte" (click)="onExtraitCompte('#ES-9920')">
                        <span class="material-symbols-outlined text-lg">receipt_long</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Envoyer message" (click)="onMessage('#ES-9920')">
                        <span class="material-symbols-outlined text-lg">chat</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Imprimer facture proforma" (click)="onImprimer('#ES-9920')">
                        <span class="material-symbols-outlined text-lg">print</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-on-surface hover:bg-surface-container rounded-lg transition-colors" (click)="onDetails('#ES-9920')">
                        <span class="material-symbols-outlined text-lg">more_vert</span>
                      </button>
                    </div>
                  </td>
                </tr>
                <!-- Row 3: Dr. Jean-Philippe Traoré -->
                <tr *ngIf="estAffichee('attente-garantie')" class="hover:bg-surface-container-low/60 transition-colors group">
                  <td class="py-3.5 px-4 font-mono font-bold text-on-surface">
                    <div class="flex items-center gap-1.5">
                      <span class="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
                      #ES-9918
                    </div>
                    <span class="text-caption font-caption text-secondary">Téléphonique (Concierge)</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-full bg-amber-100 text-amber-900 flex items-center justify-center font-bold text-xs">
                        JT
                      </div>
                      <div>
                        <div class="flex items-center gap-1.5">
                          <span class="font-bold text-on-surface">Dr. Jean-Philippe Traoré</span>
                        </div>
                        <div class="text-caption font-caption text-secondary flex items-center gap-2">
                          <span class="">+221 77 450 19 82</span>
                          <span class="">•</span>
                          <span class="">Dakar, SN</span>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-semibold text-on-surface">Ch. 301 — Suite Présidentielle</div>
                    <span class="text-caption font-caption text-secondary">Étage 3 • Vue Panoramique</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="text-on-surface font-semibold">25 Oct — 28 Oct</div>
                    <span class="text-caption font-caption text-secondary">3 nuits • 2 adultes</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-bold text-on-surface">1 350 000 FCFA</div>
                    <span class="inline-flex items-center gap-1 text-caption font-caption text-amber-800 font-semibold bg-amber-50 px-2 py-0.5 rounded border border-amber-200">
                      <span class="material-symbols-outlined text-xs">schedule</span> Acompte en attente (50%)
                    </span>
                  </td>
                  <td class="py-3.5 px-4">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-amber-50 text-amber-800 border border-amber-300">
                      <span class="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
                      Option bloquée (Expire 18h)
                    </span>
                  </td>
                  <td class="py-3.5 px-4 text-right">
                    <div class="flex items-center justify-end gap-1">
                      <button class="p-1.5 text-secondary hover:text-amber-700 hover:bg-amber-50 rounded-lg transition-colors" title="Envoyer lien Wave Money" (click)="onLienWave('#ES-9918')">
                        <span class="material-symbols-outlined text-lg">send</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Rappeler client" (click)="onRappeler('#ES-9918')">
                        <span class="material-symbols-outlined text-lg">phone</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-on-surface hover:bg-surface-container rounded-lg transition-colors" (click)="onDetails('#ES-9918')">
                        <span class="material-symbols-outlined text-lg">more_vert</span>
                      </button>
                    </div>
                  </td>
                </tr>
                <!-- Row 4: Ambassade de Suisse (Délégation) -->
                <tr *ngIf="estAffichee('confirmees')" class="hover:bg-surface-container-low/60 transition-colors group">
                  <td class="py-3.5 px-4 font-mono font-bold text-on-surface">
                    <div class="flex items-center gap-1.5">
                      <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                      #ES-9915
                    </div>
                    <span class="text-caption font-caption text-secondary">Protocole Diplomatique</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-full bg-primary/10 text-primary flex items-center justify-center font-bold text-xs">
                        AS
                      </div>
                      <div>
                        <div class="flex items-center gap-1.5">
                          <span class="font-bold text-on-surface">Ambassade de Suisse (Délégation)</span>
                          <span class="px-1.5 py-0.2 rounded bg-surface-container-highest text-caption font-caption text-primary font-bold border border-primary/20">VIP 1</span>
                        </div>
                        <div class="text-caption font-caption text-secondary">Attn : M. H. Keller</div>
                      </div>
                    </div>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-semibold text-on-surface">Ch. 201 &amp; 202 — Connectées</div>
                    <span class="text-caption font-caption text-secondary">Étage 2 • Aile Est</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="text-on-surface font-semibold">26 Oct — 31 Oct</div>
                    <span class="text-caption font-caption text-secondary">5 nuits • 4 personnes</span>
                  </td>
                  <td class="py-3.5 px-4">
                    <div class="font-bold text-on-surface">2 250 000 FCFA</div>
                    <span class="inline-flex items-center gap-1 text-caption font-caption text-primary font-semibold bg-surface-container px-2 py-0.5 rounded">
                      <span class="material-symbols-outlined text-xs">account_balance</span> Virement Reçu
                    </span>
                  </td>
                  <td class="py-3.5 px-4">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-emerald-50 text-primary border border-primary/30">
                      <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                      Prêt &amp; Nettoyé (VIP Care)
                    </span>
                  </td>
                  <td class="py-3.5 px-4 text-right">
                    <div class="flex items-center justify-end gap-1">
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Check-in VIP" (click)="onCheckInExpress('#ES-9915')">
                        <span class="material-symbols-outlined text-lg">key</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-primary hover:bg-surface-container rounded-lg transition-colors" title="Imprimer protocole" (click)="onImprimer('#ES-9915')">
                        <span class="material-symbols-outlined text-lg">print</span>
                      </button>
                      <button class="p-1.5 text-secondary hover:text-on-surface hover:bg-surface-container rounded-lg transition-colors" (click)="onDetails('#ES-9915')">
                        <span class="material-symbols-outlined text-lg">more_vert</span>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- Table Pagination & Counter -->
          <div class="p-4 bg-surface-container-low/40 border-t border-outline-variant flex items-center justify-between text-body-sm font-body-sm text-secondary">
            <span class="">Affichage de <strong>4</strong> sur <strong>42</strong> dossiers au total</span>
            <div class="flex items-center gap-2">
              <button class="px-3 py-1 bg-surface-container-lowest border border-outline-variant rounded-md text-secondary hover:text-on-surface disabled:opacity-50" disabled>Précédent</button>
              <button class="px-3 py-1 bg-primary text-on-primary rounded-md font-semibold" (click)="onPage(1)">1</button>
              <button class="px-3 py-1 bg-surface-container-lowest border border-outline-variant rounded-md text-secondary hover:text-on-surface" (click)="onPage(2)">2</button>
              <button class="px-3 py-1 bg-surface-container-lowest border border-outline-variant rounded-md text-secondary hover:text-on-surface" (click)="onPage(3)">3</button>
              <button class="px-3 py-1 bg-surface-container-lowest border border-outline-variant rounded-md text-secondary hover:text-on-surface" (click)="onPage(2)">Suivant</button>
            </div>
          </div>
        </div>
        <!-- Quick Room Availability Shelf (Micro Gantt / Rack Preview) -->
        <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-5 shadow-sm space-y-4">
          <div class="flex items-center justify-between">
            <div>
              <h3 class="text-headline-sm font-headline-sm text-on-surface">Aperçu Express du Rack (Aujourd'hui)</h3>
              <p class="text-caption font-caption text-secondary">État des suites pour attributions immédiates à l'accueil</p>
            </div>
            <div class="flex items-center gap-4 text-caption font-caption">
              <span class="flex items-center gap-1.5"><span class="w-3 h-3 rounded bg-emerald-100 border border-primary"></span> Libre / Prête</span>
              <span class="flex items-center gap-1.5"><span class="w-3 h-3 rounded bg-secondary-fixed border border-secondary"></span> Occupée</span>
              <span class="flex items-center gap-1.5"><span class="w-3 h-3 rounded bg-amber-100 border border-amber-500"></span> Entretien / Blocage</span>
            </div>
          </div>
          <!-- Room Cards Row -->
          <div class="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-6 lg:grid-cols-8 gap-3">
            <!-- Room Card 1 -->
            <div class="p-3 bg-emerald-50/60 border-l-4 border-l-primary border border-outline-variant rounded-lg flex flex-col justify-between">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">101</span>
                <span class="text-caption font-caption bg-emerald-100 text-primary px-1 rounded">RDC</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Deluxe King</span>
              <div class="text-caption font-caption font-semibold text-primary mt-1">160 000 F</div>
            </div>
            <!-- Room Card 2 -->
            <div class="p-3 bg-surface-container-low border-l-4 border-l-secondary border border-outline-variant rounded-lg flex flex-col justify-between opacity-85">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">102</span>
                <span class="material-symbols-outlined text-secondary text-sm">lock</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">King Émeraude</span>
              <div class="text-caption font-caption text-secondary mt-1">S. Koné (Arrivée)</div>
            </div>
            <!-- Room Card 3 -->
            <div class="p-3 bg-emerald-50/60 border-l-4 border-l-primary border border-outline-variant rounded-lg flex flex-col justify-between">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">103</span>
                <span class="text-caption font-caption bg-emerald-100 text-primary px-1 rounded">RDC</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Standard Queen</span>
              <div class="text-caption font-caption font-semibold text-primary mt-1">120 000 F</div>
            </div>
            <!-- Room Card 4 -->
            <div class="p-3 bg-amber-50/70 border-l-4 border-l-amber-500 border border-outline-variant rounded-lg flex flex-col justify-between">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">104</span>
                <span class="material-symbols-outlined text-amber-600 text-sm">brush</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Deluxe King</span>
              <div class="text-caption font-caption text-amber-700 font-semibold mt-1">Nettoyage 15h</div>
            </div>
            <!-- Room Card 5 -->
            <div class="p-3 bg-surface-container-low border-l-4 border-l-secondary border border-outline-variant rounded-lg flex flex-col justify-between opacity-85">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">201</span>
                <span class="material-symbols-outlined text-secondary text-sm">lock</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Diplomatique</span>
              <div class="text-caption font-caption text-secondary mt-1">Bloqué Suisse</div>
            </div>
            <!-- Room Card 6 -->
            <div class="p-3 bg-emerald-50/60 border-l-4 border-l-primary border border-outline-variant rounded-lg flex flex-col justify-between">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">202</span>
                <span class="text-caption font-caption bg-emerald-100 text-primary px-1 rounded">Et. 2</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Diplomatique</span>
              <div class="text-caption font-caption font-semibold text-primary mt-1">220 000 F</div>
            </div>
            <!-- Room Card 7 -->
            <div class="p-3 bg-emerald-50/60 border-l-4 border-l-primary border border-outline-variant rounded-lg flex flex-col justify-between">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">301</span>
                <span class="text-caption font-caption bg-emerald-100 text-primary px-1 rounded">VIP</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Présidentielle</span>
              <div class="text-caption font-caption font-semibold text-primary mt-1">450 000 F</div>
            </div>
            <!-- Room Card 8 -->
            <div class="p-3 bg-surface-container-low border-l-4 border-l-secondary border border-outline-variant rounded-lg flex flex-col justify-between opacity-85">
              <div class="flex items-center justify-between">
                <span class="text-headline-sm font-headline-sm font-bold text-on-surface">302</span>
                <span class="material-symbols-outlined text-secondary text-sm">lock</span>
              </div>
              <span class="text-body-sm font-body-sm text-secondary truncate">Suite Baie</span>
              <div class="text-caption font-caption text-secondary mt-1">Occupée (J-2)</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- ========================================================================= -->
    <!-- SLIDE-OVER DRAWER: Progressive Reservation Creation Form                  -->
    <!-- ========================================================================= -->
    <aside [class]="classeSlideover()" id="slideover-creator">
      <!-- Slideover Header -->
      <div class="p-6 border-b border-outline-variant flex items-center justify-between bg-surface-container-low">
        <div class="flex items-center gap-3">
          <div class="w-9 h-9 rounded-lg bg-primary text-on-primary flex items-center justify-center">
            <span class="material-symbols-outlined text-xl">add_business</span>
          </div>
          <div>
            <h2 class="text-headline-md font-headline-md text-on-surface font-bold">Création Progressive de Réservation</h2>
            <span class="text-caption font-caption text-secondary">Nouveau dossier • Tarif Public FCFA Garanti</span>
          </div>
        </div>
        <button class="w-8 h-8 rounded-lg hover:bg-surface-container flex items-center justify-center text-secondary hover:text-on-surface transition-colors"
                (click)="openForm.set(false)">
          <span class="material-symbols-outlined text-xl">close</span>
        </button>
      </div>
      <!-- Stepper Navigation Header -->
      <div class="grid grid-cols-4 border-b border-outline-variant bg-surface-container-lowest text-center">
        <div class="py-3 border-b-2 border-primary text-primary font-bold text-caption font-caption flex flex-col items-center gap-1">
          <span class="w-5 h-5 rounded-full bg-primary text-on-primary flex items-center justify-center text-xs">1</span>
          <span class="">Dates &amp; Hôtes</span>
        </div>
        <div class="py-3 border-b-2 border-transparent text-secondary text-caption font-caption flex flex-col items-center gap-1">
          <span class="w-5 h-5 rounded-full bg-surface-container text-secondary flex items-center justify-center text-xs">2</span>
          <span class="">Chambre</span>
        </div>
        <div class="py-3 border-b-2 border-transparent text-secondary text-caption font-caption flex flex-col items-center gap-1">
          <span class="w-5 h-5 rounded-full bg-surface-container text-secondary flex items-center justify-center text-xs">3</span>
          <span class="">Client &amp; Police</span>
        </div>
        <div class="py-3 border-b-2 border-transparent text-secondary text-caption font-caption flex flex-col items-center gap-1">
          <span class="w-5 h-5 rounded-full bg-surface-container text-secondary flex items-center justify-center text-xs">4</span>
          <span class="">Paiement</span>
        </div>
      </div>
      <!-- Slideover Form Body (Scrollable) -->
      <div class="flex-1 overflow-y-auto custom-scrollbar p-6 space-y-6">
        <!-- Étape 1 : Dates & Voyageurs -->
        <div class="space-y-4">
          <div class="flex items-center gap-2">
            <span class="text-label-lg font-label-lg text-primary font-bold">1. Dates du Séjour &amp; Capacité</span>
            <span class="text-caption font-caption text-secondary">(Obligatoire)</span>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-label-md font-label-md text-on-surface mb-1">Date d'arrivée</label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-secondary text-base">calendar_today</span>
                <input class="w-full h-10 pl-9 pr-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                       type="date" [(ngModel)]="form.arrivee" name="arrivee">
              </div>
            </div>
            <div>
              <label class="block text-label-md font-label-md text-on-surface mb-1">Date de départ</label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-secondary text-base">event</span>
                <input class="w-full h-10 pl-9 pr-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                       type="date" [(ngModel)]="form.depart" name="depart">
              </div>
            </div>
          </div>
          <div class="grid grid-cols-3 gap-3">
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Adultes</label>
              <select class="w-full h-9 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm px-2"
                      [(ngModel)]="form.adultes" name="adultes">
                <option>1 Adulte</option>
                <option>2 Adultes</option>
                <option>3 Adultes</option>
              </select>
            </div>
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Enfants (-12a)</label>
              <select class="w-full h-9 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm px-2"
                      [(ngModel)]="form.enfants" name="enfants">
                <option>0 Enfant</option>
                <option>1 Enfant</option>
                <option>2 Enfants</option>
              </select>
            </div>
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Durée calculée</label>
              <div class="h-9 px-3 bg-surface-container rounded-lg flex items-center font-bold text-label-md text-primary">
                3 Nuits
              </div>
            </div>
          </div>
        </div>
        <hr class="border-outline-variant">
        <!-- Étape 2 : Choix de la chambre & Tarif journalier en FCFA -->
        <div class="space-y-4">
          <div class="flex items-center justify-between">
            <span class="text-label-lg font-label-lg text-primary font-bold">2. Choix de la Chambre &amp; Tarif</span>
            <span class="text-caption font-caption text-primary">3 disponibles</span>
          </div>
          <!-- Room selection card radio list -->
          <div class="space-y-2.5">
            <!-- Room Option A -->
            <label [class]="classeChambre('101')">
              <div class="flex items-center gap-3">
                <input class="w-4 h-4 text-primary focus:ring-primary" name="room_selection" type="radio"
                       [checked]="form.chambre === '101'" (change)="form.chambre = '101'">
                <div>
                  <div class="font-bold text-body-md text-on-surface">Chambre 101 — Deluxe King</div>
                  <div class="text-caption font-caption text-secondary">RDC • Lit King 200x200 • Petit-déjeuner inclus</div>
                </div>
              </div>
              <div class="text-right">
                <div class="font-bold text-headline-sm text-primary">160 000 F</div>
                <span class="text-caption font-caption text-secondary">/ nuit</span>
              </div>
            </label>
            <!-- Room Option B -->
            <label [class]="classeChambre('202')">
              <div class="flex items-center gap-3">
                <input class="w-4 h-4 text-primary focus:ring-primary" name="room_selection" type="radio"
                       [checked]="form.chambre === '202'" (change)="form.chambre = '202'">
                <div>
                  <div class="font-bold text-body-md text-on-surface">Chambre 202 — Suite Diplomatique</div>
                  <div class="text-caption font-caption text-secondary">Étage 2 • Salon privé &amp; Machine Espresso</div>
                </div>
              </div>
              <div class="text-right">
                <div class="font-bold text-headline-sm text-on-surface">220 000 F</div>
                <span class="text-caption font-caption text-secondary">/ nuit</span>
              </div>
            </label>
          </div>
        </div>
        <hr class="border-outline-variant">
        <!-- Étape 3 : Informations Client & Fiche Police / CNI -->
        <div class="space-y-4">
          <div class="flex items-center justify-between">
            <span class="text-label-lg font-label-lg text-primary font-bold">3. Informations Voyageur Principal</span>
            <button class="text-caption font-caption text-primary underline hover:text-primary-container" (click)="onRechercherClient()">Rechercher client existant</button>
          </div>
          <div class="grid grid-cols-2 gap-3">
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Nom complet</label>
              <input class="w-full h-10 px-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                     placeholder="ex: Koffi N'Guessan" type="text" [(ngModel)]="form.client" name="client">
            </div>
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Téléphone (Mobile Money)</label>
              <input class="w-full h-10 px-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                     placeholder="+225 05 XX XX XX" type="text" [(ngModel)]="form.telephone" name="telephone">
            </div>
          </div>
          <div class="grid grid-cols-2 gap-3">
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Numéro CNI ou Passeport</label>
              <input class="w-full h-10 px-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                     placeholder="CI-09827391 / C01234..." type="text" [(ngModel)]="form.piece" name="piece">
            </div>
            <div>
              <label class="block text-caption font-caption text-secondary mb-1">Nationalité</label>
              <input class="w-full h-10 px-3 rounded-lg border border-outline-variant bg-surface-container-lowest text-body-sm font-body-sm focus:border-primary focus:ring-1 focus:ring-primary"
                     placeholder="Ivoirienne" type="text" [(ngModel)]="form.nationalite" name="nationalite">
            </div>
          </div>
        </div>
        <hr class="border-outline-variant">
        <!-- Étape 4 : Paiement & Acompte Wave/Mobile Money -->
        <div class="space-y-4 pb-4">
          <div class="flex items-center justify-between">
            <span class="text-label-lg font-label-lg text-primary font-bold">4. Modalités de Garantie &amp; Règlement</span>
            <span class="text-label-md font-label-md text-on-surface font-bold">Sous-total : 480 000 FCFA</span>
          </div>
          <div class="grid grid-cols-3 gap-2">
            <label [class]="classePaiement('wave')" (click)="form.paiement = 'wave'">
              <span [class]="classeIconePaiement('wave')">smartphone</span>
              <span [class]="classeLibellePaiement('wave')">Wave CI / Orange</span>
            </label>
            <label [class]="classePaiement('carte')" (click)="form.paiement = 'carte'">
              <span [class]="classeIconePaiement('carte')">credit_card</span>
              <span [class]="classeLibellePaiement('carte')">Carte Visa / MC</span>
            </label>
            <label [class]="classePaiement('especes')" (click)="form.paiement = 'especes'">
              <span [class]="classeIconePaiement('especes')">payments</span>
              <span [class]="classeLibellePaiement('especes')">Espèces Réception</span>
            </label>
          </div>
          <div class="p-3 bg-surface-container-low rounded-lg space-y-2">
            <div class="flex items-center justify-between text-caption font-caption">
              <span class="text-secondary">Montant de l'acompte obligatoire (30%) :</span>
              <span class="font-bold text-on-surface">144 000 FCFA</span>
            </div>
            <div class="flex items-center gap-2">
              <input class="w-4 h-4 text-primary rounded focus:ring-primary" id="send-wave-link" type="checkbox"
                     [(ngModel)]="form.lienWave" name="lienWave">
              <label class="text-caption font-caption text-on-surface" for="send-wave-link">Générer immédiatement un lien de paiement Wave par SMS/WhatsApp</label>
            </div>
          </div>
        </div>
      </div>
      <!-- Slideover Footer Action Buttons -->
      <div class="p-4 border-t border-outline-variant bg-surface-container-lowest flex items-center justify-between">
        <button class="px-4 py-2 text-secondary hover:text-on-surface text-label-md font-label-md font-medium transition-colors"
                (click)="openForm.set(false)">
          Annuler
        </button>
        <div class="flex items-center gap-2">
          <button class="px-4 py-2 border border-outline-variant text-on-surface rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-colors"
                  (click)="onBrouillon()">
            Enregistrer en Brouillon
          </button>
          <button class="px-5 py-2.5 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-md font-label-md font-bold shadow-md transition-all"
                  (click)="submitForm()">
            Confirmer la Réservation
          </button>
        </div>
      </div>
    </aside>
  `,
  host: {
    '(document:keydown.escape)': 'openForm.set(false)',
  },
})
export class ReservationsListComponent implements OnInit {
  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    // Ouvre automatiquement le slideover si ?new=1 dans l'URL
    this.route.queryParamMap.subscribe(params => {
      if (params.get('new') === '1') {
        this.openForm.set(true);
      }
    });
  }

  // ---------------------------------------------------------------- En-tête
  plageDates = '24 Oct 2024 — 31 Oct 2024';
  filtre = '';

  // ------------------------------------------------------ Onglets de statut
  ongletActif = signal<OngletReservation>('toutes');

  private readonly ongletActifClasses =
    'px-3.5 py-1.5 rounded-lg text-label-md font-label-md bg-surface-container text-primary font-bold';
  private readonly ongletInactifClasses =
    'px-3.5 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors';
  private readonly ongletActifPastilleClasses =
    'px-3.5 py-1.5 rounded-lg text-label-md font-label-md bg-surface-container text-primary font-bold flex items-center gap-1.5';
  private readonly ongletInactifPastilleClasses =
    'px-3.5 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors flex items-center gap-1.5';

  classeOnglet(cle: OngletReservation): string {
    return this.ongletActif() === cle ? this.ongletActifClasses : this.ongletInactifClasses;
  }

  classeOngletPastille(cle: OngletReservation): string {
    return this.ongletActif() === cle ? this.ongletActifPastilleClasses : this.ongletInactifPastilleClasses;
  }

  onSelectionnerOnglet(cle: OngletReservation): void {
    this.ongletActif.set(cle);
  }

  /** Une ligne est visible si l'onglet « Toutes » est actif, si son statut correspond,
   *  ou si le filtre texte correspond au dossier. */
  estAffichee(statut: OngletReservation): boolean {
    const onglet = this.ongletActif();
    return onglet === 'toutes' || onglet === statut;
  }

  // -------------------------------------------------- Slideover de création
  openForm = signal(false);

  private readonly slideoverBase =
    'fixed top-0 right-0 w-[580px] h-screen bg-surface-container-lowest border-l border-outline-variant shadow-2xl z-50 transform transition-transform duration-300 ease-in-out flex flex-col';

  classeSlideover(): string {
    return this.openForm() ? this.slideoverBase : this.slideoverBase + ' translate-x-full';
  }

  form = {
    code: '', client: '', chambre: '101',
    arrivee: '2024-10-25', depart: '2024-10-28',
    adultes: '2 Adultes', enfants: '0 Enfant',
    telephone: '', piece: '', nationalite: '',
    paiement: 'wave', lienWave: true,
    canal: 'Direct', montant: 0, commentaire: '',
  };

  private readonly chambreSelectionnee =
    'flex items-center justify-between p-3 rounded-lg border-2 border-primary bg-primary-fixed/10 cursor-pointer';
  private readonly chambreNonSelectionnee =
    'flex items-center justify-between p-3 rounded-lg border border-outline-variant hover:bg-surface-container-low cursor-pointer';

  classeChambre(numero: string): string {
    return this.form.chambre === numero ? this.chambreSelectionnee : this.chambreNonSelectionnee;
  }

  private readonly paiementActif =
    'p-2.5 rounded-lg border border-primary bg-primary/5 flex flex-col items-center justify-center cursor-pointer text-center';
  private readonly paiementInactif =
    'p-2.5 rounded-lg border border-outline-variant hover:bg-surface-container-low flex flex-col items-center justify-center cursor-pointer text-center';

  classePaiement(cle: string): string {
    return this.form.paiement === cle ? this.paiementActif : this.paiementInactif;
  }

  classeIconePaiement(cle: string): string {
    return this.form.paiement === cle
      ? 'material-symbols-outlined text-primary mb-1'
      : 'material-symbols-outlined text-secondary mb-1';
  }

  classeLibellePaiement(cle: string): string {
    return this.form.paiement === cle
      ? 'text-caption font-caption font-bold text-primary'
      : 'text-caption font-caption text-secondary font-medium';
  }

  // ----------------------------------------------------- Actions du tableau
  onCheckInExpress(ref: string) { console.log('[Réservations] check-in express :', ref); }
  onMessage(ref: string)        { console.log('[Réservations] message client :', ref); }
  onImprimer(ref: string)       { console.log('[Réservations] impression :', ref); }
  onDetails(ref: string)        { console.log('[Réservations] détails du dossier :', ref); }
  onExtraitCompte(ref: string)  { console.log('[Réservations] extrait de compte :', ref); }
  onLienWave(ref: string)       { console.log('[Réservations] lien Wave Money :', ref); }
  onRappeler(ref: string)       { console.log('[Réservations] rappel client :', ref); }
  onPage(page: number)          { console.log('[Réservations] page :', page); }
  onRechercherClient()          { console.log('[Réservations] recherche client existant'); }
  onBrouillon()                 { console.log('[Réservations] brouillon :', this.form); }

  submitForm() {
    // TODO : brancher au ReservationService (POST /api/v1/planning/reservations)
    console.log('Nouvelle réservation', this.form);
    this.openForm.set(false);
  }
}
