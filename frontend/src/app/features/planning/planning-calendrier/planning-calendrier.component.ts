import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

interface JourColonne {
  jour: string;
  numero: string;
  taux: string;
}

/**
 * Planning calendrier des chambres — vue rack Gantt.
 */
@Component({
  selector: 'app-planning-calendrier',
  standalone: true,
  imports: [CommonModule],
  styles: [
    `
      /* Custom subtle scrollbar for Gantt matrix */
      .gantt-scroll::-webkit-scrollbar {
        height: 6px;
        width: 6px;
      }
      .gantt-scroll::-webkit-scrollbar-track {
        background: #f2f4f6;
      }
      .gantt-scroll::-webkit-scrollbar-thumb {
        background: #bccac0;
        border-radius: 4px;
      }
    `,
  ],
  template: `
    <div class="flex flex-col gap-6 max-w-[1920px]">
      <!-- Top Row: Page Title + Bento KPI Strip -->
      <div class="flex flex-col xl:flex-row xl:items-center justify-between gap-4">
        <div>
          <div class="flex items-center gap-2 text-secondary text-body-sm font-body-sm mb-1">
            <span class="">Hôtel Étoile du Sud</span>
            <span class="">/</span>
            <span class="">Opérations</span>
            <span class="">/</span>
            <span class="text-on-surface font-semibold">Planning &amp; Racks</span>
          </div>
          <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Planning Général des Chambres</h1>
        </div>
        <!-- Bento Metric Badges (KPIs du Jour) -->
        <div class="grid grid-cols-2 sm:grid-cols-4 gap-3">
          <!-- Taux d'occupation -->
          <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-3 shadow-sm flex items-center gap-3.5 min-w-[150px]">
            <div class="w-10 h-10 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined">pie_chart</span>
            </div>
            <div>
              <div class="text-caption font-caption text-secondary uppercase tracking-wider">Occupation</div>
              <div class="flex items-baseline gap-1.5">
                <span class="text-headline-md font-headline-md text-on-surface">88%</span>
                <span class="text-caption font-caption text-primary font-semibold flex items-center">
                  <span class="material-symbols-outlined text-[14px]">arrow_upward</span>+4%
                </span>
              </div>
            </div>
          </div>
          <!-- 14 Arrivées -->
          <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-3 shadow-sm flex items-center gap-3.5 min-w-[140px]">
            <div class="w-10 h-10 rounded-lg bg-primary/10 flex items-center justify-center text-primary">
              <span class="material-symbols-outlined">login</span>
            </div>
            <div>
              <div class="text-caption font-caption text-secondary uppercase tracking-wider">Arrivées</div>
              <div class="flex items-baseline gap-1.5">
                <span class="text-headline-md font-headline-md text-on-surface">14</span>
                <span class="text-caption font-caption text-secondary">prévues</span>
              </div>
            </div>
          </div>
          <!-- 9 Départs -->
          <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-3 shadow-sm flex items-center gap-3.5 min-w-[140px]">
            <div class="w-10 h-10 rounded-lg bg-secondary-container/60 flex items-center justify-center text-on-secondary-container">
              <span class="material-symbols-outlined">logout</span>
            </div>
            <div>
              <div class="text-caption font-caption text-secondary uppercase tracking-wider">Départs</div>
              <div class="flex items-baseline gap-1.5">
                <span class="text-headline-md font-headline-md text-on-surface">9</span>
                <span class="text-caption font-caption text-secondary">du jour</span>
              </div>
            </div>
          </div>
          <!-- 2 Bloquées SAV -->
          <div class="bg-surface-container-lowest border border-outline-variant/70 rounded-xl p-3 shadow-sm flex items-center gap-3.5 min-w-[140px]">
            <div class="w-10 h-10 rounded-lg bg-error-container/50 flex items-center justify-center text-error">
              <span class="material-symbols-outlined">build_circle</span>
            </div>
            <div>
              <div class="text-caption font-caption text-secondary uppercase tracking-wider">Hors service</div>
              <div class="flex items-baseline gap-1.5">
                <span class="text-headline-md font-headline-md text-on-surface">2</span>
                <span class="text-caption font-caption text-error font-medium">Maintenance</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Calendar Controls & Filters Tool Strip -->
      <div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-5 mb-2 shadow-sm flex flex-wrap items-center justify-between gap-5">
        <!-- Date Navigator & Views -->
        <div class="flex items-center flex-wrap gap-4">
          <!-- Month and Arrow Switchers -->
          <div class="flex items-center bg-surface border border-outline-variant rounded-lg p-1">
            <button (click)="onSemainePrecedente()" class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-md transition-colors" title="Semaine précédente" type="button">
              <span class="material-symbols-outlined text-[18px]">chevron_left</span>
            </button>
            <span class="px-4 text-label-md font-label-md text-on-surface font-semibold">24 Oct - 31 Oct 2025</span>
            <button (click)="onSemaineSuivante()" class="p-2 text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-md transition-colors" title="Semaine suivante" type="button">
              <span class="material-symbols-outlined text-[18px]">chevron_right</span>
            </button>
          </div>
          <button (click)="onAujourdhui()" class="px-3.5 py-2 bg-surface hover:bg-surface-container-low border border-outline-variant text-on-surface rounded-lg font-label-md text-label-md transition-colors" type="button">
            Aujourd'hui
          </button>
          <!-- Period Toggle (Jour / Semaine / Mois) -->
          <div class="bg-surface-container-low p-1 rounded-lg flex items-center text-label-sm font-label-sm gap-1">
            <button (click)="periode.set('jour')" [class]="classePeriode('jour')" type="button">Jour</button>
            <button (click)="periode.set('semaine')" [class]="classePeriode('semaine')" type="button">Semaine</button>
            <button (click)="periode.set('mois')" [class]="classePeriode('mois')" type="button">Mois</button>
          </div>
          <div class="h-6 w-px bg-outline-variant/60 hidden md:block"></div>
          <!-- Sector & Wing Filter Dropdowns -->
          <div class="flex items-center gap-3">
            <select class="h-10 px-3.5 text-body-sm font-body-sm bg-surface border border-outline-variant rounded-lg text-on-surface focus:border-primary focus:ring-1 focus:ring-primary">
              <option>Toutes les ailes (Lagune &amp; Jardin)</option>
              <option>Bâtiment A Lagune</option>
              <option>Bâtiment B Jardin</option>
            </select>
            <select class="h-10 px-3.5 text-body-sm font-body-sm bg-surface border border-outline-variant rounded-lg text-on-surface focus:border-primary focus:ring-1 focus:ring-primary">
              <option>Tous les étages (1er, 2e, 3e)</option>
              <option>Étage 1 (Chambres 101-108)</option>
              <option>Étage 2 (Chambres 201-208)</option>
              <option>Étage 3 (Suites &amp; Penthouses)</option>
            </select>
            <!-- Statut Legend Popover / Filter -->
            <div class="flex items-center gap-2 pl-2">
              <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-caption font-caption bg-[#ecfdf5] text-[#059669] border border-[#059669]/30 font-medium">
                <span class="w-2 h-2 rounded-full bg-[#059669]"></span>Prête
              </span>
              <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-caption font-caption bg-[#eff6ff] text-[#2563eb] border border-[#2563eb]/30 font-medium">
                <span class="w-2 h-2 rounded-full bg-[#2563eb]"></span>Occupée
              </span>
              <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-caption font-caption bg-[#fffbeb] text-[#b45309] border border-[#b45309]/30 font-medium">
                <span class="w-2 h-2 rounded-full bg-[#b45309]"></span>Ménage
              </span>
              <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-caption font-caption bg-[#fef2f2] text-[#ba1a1a] border border-[#ba1a1a]/30 font-medium">
                <span class="w-2 h-2 rounded-full bg-[#ba1a1a]"></span>SAV
              </span>
            </div>
          </div>
        </div>
        <!-- Quick Operational Action Buttons -->
        <div class="flex items-center gap-2.5">
          <button (click)="onAssignationRapide()" class="flex items-center gap-2 px-3.5 py-2 text-body-sm font-label-md bg-surface hover:bg-surface-container-low border border-outline-variant text-on-surface rounded-lg transition-colors" type="button">
            <span class="material-symbols-outlined text-[18px] text-secondary">auto_fix_high</span>
            <span class="">Assignation rapide</span>
          </button>
          <button (click)="onBloquerChambre()" class="flex items-center gap-2 px-3.5 py-2 text-body-sm font-label-md bg-surface hover:bg-surface-container-low border border-outline-variant text-on-surface rounded-lg transition-colors" type="button">
            <span class="material-symbols-outlined text-[18px] text-error">lock</span>
            <span class="">Bloquer chambre</span>
          </button>
          <button (click)="onExporter()" class="flex items-center gap-2 px-3.5 py-2 text-body-sm font-label-md bg-surface hover:bg-surface-container-low border border-outline-variant text-on-surface rounded-lg transition-colors" title="Exporter au format Excel ou PDF" type="button">
            <span class="material-symbols-outlined text-[18px] text-secondary">file_download</span>
            <span class="">Export</span>
          </button>
        </div>
      </div>

      <!-- Main Workspace Split: Gantt Room Rack Grid + Interactive Detail Slide-over Panel -->
      <div class="flex gap-6 items-start">
        <!-- GANTT ROOM RACK MATRIX -->
        <div class="flex-1 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-sm overflow-hidden flex flex-col">
          <!-- Gantt Header Row -->
          <div class="flex border-b border-outline-variant bg-surface sticky top-0 z-20">
            <!-- Column: Rooms Directory Header -->
            <div class="w-64 flex-shrink-0 p-4 border-r border-outline-variant flex items-center justify-between bg-surface-container-low/50">
              <span class="text-label-md font-label-md text-secondary uppercase tracking-wider font-semibold">Chambres &amp; Typologie</span>
              <span class="text-caption font-caption text-secondary font-medium px-2 py-0.5 bg-surface rounded">8 Unités</span>
            </div>
            <!-- Columns: Days Strip 24 to 31 Octobre -->
            <div class="flex-1 grid grid-cols-8 divide-x divide-outline-variant text-center">
              <!-- Ven 24 Oct (Aujourd'hui) -->
              <div class="py-3.5 px-2 bg-primary/5 flex flex-col items-center justify-center relative gap-1">
                <span class="text-caption font-caption text-primary font-bold uppercase tracking-wide">Ven</span>
                <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center font-headline-sm text-label-md shadow-sm font-bold">
                  24
                </div>
                <span class="text-[11px] text-primary font-semibold">Aujourd'hui</span>
              </div>
              <!-- Jours suivants -->
              <div *ngFor="let j of jours" class="py-3.5 px-2 flex flex-col items-center justify-center gap-1 hover:bg-surface-container-low/30 transition-colors">
                <span class="text-caption font-caption text-secondary uppercase tracking-wide">{{ j.jour }}</span>
                <span class="font-headline-sm text-headline-sm text-on-surface font-semibold">{{ j.numero }}</span>
                <span class="text-[11px] text-secondary font-medium">{{ j.taux }}</span>
              </div>
            </div>
          </div>

          <!-- Gantt Rows Container -->
          <div class="divide-y divide-outline-variant overflow-x-auto gantt-scroll">
            <!-- ETAGE 1 SEPARATOR -->
            <div class="bg-surface-container-low px-5 py-2.5 flex items-center justify-between text-caption font-caption font-bold text-secondary uppercase tracking-widest">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[16px] text-primary">apartment</span> Bâtiment A • 1er Étage — Vue Lagune Ébrié</span>
              <span class="text-[11px] font-medium lowercase px-2 py-0.5 bg-surface-container-lowest rounded">4 chambres attribuées</span>
            </div>

            <!-- ROW 1: CHAMBRE 101 (Deluxe) -->
            <div class="flex min-h-[82px] hover:bg-surface-container-low/40 transition-colors relative items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-primary" title="Chambre Prête / Occupée"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-on-surface font-bold">101</span>
                      <span class="text-caption font-caption px-2 py-0.5 bg-surface-container-low text-secondary rounded font-medium">Lagune</span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Deluxe King</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-outline-variant text-[20px] cursor-pointer hover:text-on-surface">more_vert</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/[0.02]"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Reservation Bar: M. Dubois (24-26 Oct) -->
                <div (click)="onOuvrirReservation('M. Jean-Paul Dubois')" class="absolute top-3 bottom-3 left-3 w-[22.5%] bg-white border border-primary/40 rounded-xl px-3.5 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:border-primary hover:shadow transition-all">
                  <div class="flex items-center gap-2.5 overflow-hidden">
                    <span class="w-2.5 h-2.5 rounded-full bg-primary flex-shrink-0"></span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md text-on-surface truncate font-semibold">M. Jean-Paul Dubois</div>
                      <div class="text-caption font-caption text-secondary truncate">2 pers. • Booking.com</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2 py-1 rounded-md bg-surface-container-low text-primary font-bold ml-1.5 flex-shrink-0">Payé</span>
                </div>
                <!-- Reservation Bar 2: Mme Yao (27-30 Oct) -->
                <div (click)="onOuvrirReservation('Mme Christine Yao')" class="absolute top-3 bottom-3 left-[38.5%] w-[47%] bg-white border border-outline-variant rounded-xl px-3.5 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:border-primary hover:shadow transition-all">
                  <div class="flex items-center gap-2.5 overflow-hidden">
                    <span class="w-2.5 h-2.5 rounded-full bg-secondary flex-shrink-0"></span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md text-on-surface truncate font-semibold">Mme Christine Yao</div>
                      <div class="text-caption font-caption text-secondary truncate">Direct Web • Petit-déjeuner inclus</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2 py-1 rounded-md bg-surface-container text-secondary font-semibold ml-1.5 flex-shrink-0">Garanti</span>
                </div>
              </div>
            </div>

            <!-- ROW 2: CHAMBRE 102 (Standard King VIP) [SELECTED ROW FOCUS] -->
            <div class="flex min-h-[82px] bg-primary/[0.03] hover:bg-primary/[0.06] transition-colors relative border-l-4 border-primary items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-primary" title="Chambre Occupée VIP"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-on-surface font-bold">102</span>
                      <span class="inline-flex items-center gap-1 px-2 py-0.5 bg-amber-50 text-amber-700 text-[11px] font-bold rounded-md border border-amber-200">
                        VIP ★
                      </span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Std King VIP</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-primary text-[20px]">verified</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/10"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Main Highlighted Reservation: M. Sékou Koné (24 au 27 Oct) -->
                <div (click)="onOuvrirReservation('M. Sékou Koné')" class="absolute top-2.5 bottom-2.5 left-3 w-[35%] bg-primary text-on-primary rounded-xl px-4 py-2.5 shadow-md flex items-center justify-between cursor-pointer ring-2 ring-primary ring-offset-2 hover:opacity-95 transition-all">
                  <div class="flex items-center gap-3 overflow-hidden">
                    <div class="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center font-bold text-caption text-white flex-shrink-0">
                      SK
                    </div>
                    <div class="truncate">
                      <div class="text-label-md font-label-md font-bold text-white flex items-center gap-2 truncate">
                        <span class="">M. Sékou Koné</span>
                        <span class="px-2 py-0.5 bg-white/20 text-white rounded text-[10px] font-bold uppercase tracking-wider">VIP Élite</span>
                      </div>
                      <div class="text-caption font-caption text-white/80 truncate mt-0.5">3 Nuits • 195 000 FCFA • Check-in requis</div>
                    </div>
                  </div>
                  <span class="material-symbols-outlined text-white text-[22px] flex-shrink-0 ml-2">chevron_right</span>
                </div>
                <!-- Late Reservation for Room 102 (28 au 31 Oct) -->
                <div (click)="onOuvrirReservation('Mme Fatou Diop')" class="absolute top-3 bottom-3 left-[51.5%] w-[46%] bg-white border border-outline-variant rounded-xl px-3.5 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:border-primary hover:shadow transition-all">
                  <div class="flex items-center gap-2.5 overflow-hidden">
                    <span class="w-2.5 h-2.5 rounded-full bg-secondary flex-shrink-0"></span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md text-on-surface truncate font-semibold">Mme Fatou Diop</div>
                      <div class="text-caption font-caption text-secondary truncate">Expedia • Transfert inclus</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2 py-1 rounded-md bg-surface-container text-secondary font-semibold ml-1.5 flex-shrink-0">Confirmé</span>
                </div>
              </div>
            </div>

            <!-- ROW 3: CHAMBRE 103 (Junior Suite) -->
            <div class="flex min-h-[82px] hover:bg-surface-container-low/40 transition-colors relative items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-[#b45309]" title="Ménage en cours"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-on-surface font-bold">103</span>
                      <span class="text-caption font-caption px-2 py-0.5 bg-amber-50 text-amber-700 rounded-md border border-amber-200 font-semibold">Ménage</span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Junior Suite</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-outline-variant text-[20px]">cleaning_services</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/[0.02]"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Reservation: M. Patrick Moreau (25 au 29 Oct) -->
                <div (click)="onOuvrirReservation('M. Patrick Moreau')" class="absolute top-3 bottom-3 left-[14%] w-[47%] bg-white border border-primary/40 rounded-xl px-3.5 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:border-primary hover:shadow transition-all">
                  <div class="flex items-center gap-2.5 overflow-hidden">
                    <span class="w-2.5 h-2.5 rounded-full bg-primary flex-shrink-0"></span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md text-on-surface truncate font-semibold">M. Patrick Moreau</div>
                      <div class="text-caption font-caption text-secondary truncate">Air France Crew • Arrivée 18:30</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2 py-1 rounded-md bg-surface-container-low text-primary font-bold ml-1.5 flex-shrink-0">Attribuée</span>
                </div>
              </div>
            </div>

            <!-- ROW 4: CHAMBRE 104 (Deluxe - Bloquée Maintenance SAV) -->
            <div class="flex min-h-[82px] bg-error-container/10 hover:bg-error-container/20 transition-colors relative items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-error" title="Chambre Hors Service"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-error font-bold">104</span>
                      <span class="text-caption font-caption px-2 py-0.5 bg-error-container text-on-error-container rounded-md font-bold">SAV</span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Deluxe King</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-error text-[20px]">handyman</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/[0.02]"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Bloc Maintenance SAV Card (24 au 27 Oct) -->
                <div class="absolute top-3 bottom-3 left-3 w-[34%] bg-[#fff1f2] border border-error/40 border-dashed rounded-xl px-3.5 py-2.5 shadow-xs flex items-center justify-between">
                  <div class="flex items-center gap-2.5 text-error overflow-hidden">
                    <span class="material-symbols-outlined text-[20px] flex-shrink-0">build</span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md font-semibold text-error truncate">Intervention Climatisation</div>
                      <div class="text-caption font-caption text-on-surface-variant truncate">Ticket #SAV-882 • K. Koffi</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2.5 py-1 rounded-md bg-white text-error font-bold border border-error/20 flex-shrink-0 ml-1.5">Bloqué</span>
                </div>
              </div>
            </div>

            <!-- ETAGE 2 SEPARATOR -->
            <div class="bg-surface-container-low px-5 py-2.5 flex items-center justify-between text-caption font-caption font-bold text-secondary uppercase tracking-widest">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[16px] text-primary">diamond</span> Bâtiment A • 2e Étage — Suites de Prestige</span>
              <span class="text-[11px] font-medium lowercase px-2 py-0.5 bg-surface-container-lowest rounded">4 chambres disponibles</span>
            </div>

            <!-- ROW 5: CHAMBRE 105 (Suite Présidentielle) -->
            <div class="flex min-h-[82px] hover:bg-surface-container-low/40 transition-colors relative items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-primary" title="Suite Prête"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-on-surface font-bold">105</span>
                      <span class="text-caption font-caption px-2 py-0.5 bg-emerald-100 text-primary font-bold rounded-md">Penthouse</span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Suite Présidentielle</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-primary text-[20px]">star</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/[0.02]"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Reservation: Délégation Ministérielle (26 au 31 Oct) -->
                <div (click)="onOuvrirReservation('Délégation Primature CI')" class="absolute top-3 bottom-3 left-[26.5%] w-[71%] bg-white border-2 border-primary rounded-xl px-4 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:bg-surface-container-low/50 hover:shadow transition-all">
                  <div class="flex items-center gap-3 overflow-hidden">
                    <div class="w-8 h-8 rounded-full bg-primary-container text-on-primary flex items-center justify-center font-bold text-caption flex-shrink-0">
                      D
                    </div>
                    <div class="truncate">
                      <div class="text-label-md font-label-md font-bold text-on-surface flex items-center gap-2 truncate">
                        <span class="">Délégation Primature CI</span>
                        <span class="text-caption font-caption px-2 py-0.5 rounded bg-primary/10 text-primary font-semibold">Protocole VVIP</span>
                      </div>
                      <div class="text-caption font-caption text-secondary truncate mt-0.5">5 Nuits • Facturation Corporate Directe</div>
                    </div>
                  </div>
                  <div class="flex items-center gap-3 flex-shrink-0 ml-2">
                    <span class="text-caption font-caption font-bold text-on-surface bg-surface-container px-2 py-1 rounded">1 450 000 FCFA</span>
                    <span class="material-symbols-outlined text-primary text-[20px]">verified_user</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- ROW 6: CHAMBRE 106 (Deluxe Twin) -->
            <div class="flex min-h-[82px] hover:bg-surface-container-low/40 transition-colors relative items-center">
              <div class="w-64 self-stretch flex-shrink-0 p-4 border-r border-outline-variant bg-surface-container-lowest flex items-center justify-between">
                <div class="flex items-center gap-3">
                  <div class="w-1.5 h-12 rounded-full bg-primary"></div>
                  <div>
                    <div class="flex items-center gap-2">
                      <span class="font-headline-sm text-headline-sm text-on-surface font-bold">106</span>
                      <span class="text-caption font-caption px-2 py-0.5 bg-surface-container-low text-secondary rounded-md font-medium">Jardin</span>
                    </div>
                    <span class="text-body-sm font-body-sm text-secondary block truncate mt-0.5">Deluxe Twin</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-outline-variant text-[20px] cursor-pointer hover:text-on-surface">more_vert</span>
              </div>
              <div class="flex-1 self-stretch grid grid-cols-8 divide-x divide-outline-variant/60 relative items-center py-3">
                <div class="h-full bg-primary/[0.02]"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <div class="h-full"></div>
                <!-- Reservation: M. & Mme Alami (24 au 28 Oct) -->
                <div (click)="onOuvrirReservation('M. Mehdi Alami')" class="absolute top-3 bottom-3 left-3 w-[47%] bg-white border border-outline-variant rounded-xl px-3.5 py-2.5 shadow-sm flex items-center justify-between cursor-pointer hover:border-primary hover:shadow transition-all">
                  <div class="flex items-center gap-2.5 overflow-hidden">
                    <span class="w-2.5 h-2.5 rounded-full bg-primary flex-shrink-0"></span>
                    <div class="truncate">
                      <div class="text-label-md font-label-md text-on-surface truncate font-semibold">M. Mehdi Alami</div>
                      <div class="text-caption font-caption text-secondary truncate">2 Ad. + 1 Enf. • Petit-déjeuner inclus</div>
                    </div>
                  </div>
                  <span class="text-caption font-caption px-2 py-1 rounded-md bg-surface-container text-secondary font-semibold ml-1.5 flex-shrink-0">Recouvert</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Matrix Footer Info Bar -->
          <div class="p-4 bg-surface border-t border-outline-variant flex items-center justify-between text-caption font-caption text-secondary">
            <div class="flex items-center gap-6">
              <span class="flex items-center gap-2">
                <span class="material-symbols-outlined text-[18px] text-primary">drag_indicator</span>
                Glisser-déposer une réservation pour réassigner
              </span>
              <span class="flex items-center gap-2">
                <span class="material-symbols-outlined text-[18px] text-primary">touch_app</span>
                Double-clic pour ouvrir la fiche client
              </span>
            </div>
            <div class="flex items-center gap-3">
              <span class="">Dernière synchro : Il y a 1 minute</span>
              <button (click)="onRafraichir()" class="text-primary hover:underline font-bold px-2 py-1 rounded hover:bg-surface-container-low transition-colors" type="button">Rafraîchir</button>
            </div>
          </div>
        </div>

        <!-- ================= INTERACTIVE DETAIL SLIDE-OVER PANEL ================= -->
        <aside *ngIf="panneauVisible()" class="w-[410px] flex-shrink-0 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-md p-7 flex flex-col gap-6 sticky top-24">
          <!-- Panel Header -->
          <div class="flex items-start justify-between border-b border-outline-variant/60 pb-5">
            <div class="overflow-y-auto">
              <div class="flex items-center gap-2.5 mb-1.5">
                <span class="px-2.5 py-1 rounded-full text-caption font-caption font-bold bg-[#ecfdf5] text-primary border border-primary/20">
                  CHECK-IN EN ATTENTE
                </span>
                <span class="text-caption font-caption text-secondary font-medium">#RES-2025-9842</span>
              </div>
              <h2 class="text-headline-md font-headline-md text-on-surface font-bold">Détail Réservation</h2>
            </div>
            <button (click)="panneauVisible.set(false)" class="text-secondary hover:text-on-surface p-1.5 rounded-lg hover:bg-surface-container-low transition-colors" title="Fermer le panneau" type="button">
              <span class="material-symbols-outlined text-[20px]">close</span>
            </button>
          </div>
          <!-- Guest Profile Card -->
          <div class="flex items-center gap-4 bg-surface p-4 rounded-xl border border-outline-variant/60 shadow-xs">
            <div class="w-12 h-12 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold font-headline-md text-headline-sm shadow-sm flex-shrink-0 overflow-y-auto">
              SK
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-1.5">
                <h3 class="font-headline-sm text-headline-sm text-on-surface truncate font-bold">M. Sékou Koné</h3>
                <span class="material-symbols-outlined text-amber-500 text-[18px]" title="Client VIP Élite">verified</span>
              </div>
            </div>
          </div>
        </aside>
      </div>
    </div>
  `,
})
export class PlanningCalendrierComponent {
  /** Colonnes de jours suivant « Ven 24 » (rendu séparément car marqué Aujourd'hui). */
  jours: JourColonne[] = [
    { jour: 'Sam', numero: '25', taux: '94%' },
    { jour: 'Dim', numero: '26', taux: '90%' },
    { jour: 'Lun', numero: '27', taux: '75%' },
    { jour: 'Mar', numero: '28', taux: '82%' },
    { jour: 'Mer', numero: '29', taux: '88%' },
    { jour: 'Jeu', numero: '30', taux: '85%' },
    { jour: 'Ven', numero: '31', taux: '92%' },
  ];

  periode = signal<'jour' | 'semaine' | 'mois'>('semaine');
  panneauVisible = signal(true);

  private readonly periodeActive = 'px-3.5 py-1.5 rounded-md bg-surface-container-lowest text-primary font-semibold shadow-xs';
  private readonly periodeInactive = 'px-3.5 py-1.5 rounded-md text-secondary hover:text-on-surface transition-colors';

  classePeriode(cle: 'jour' | 'semaine' | 'mois'): string {
    return this.periode() === cle ? this.periodeActive : this.periodeInactive;
  }

  onSemainePrecedente(): void {
    console.log('[Planning] Semaine précédente');
  }

  onSemaineSuivante(): void {
    console.log('[Planning] Semaine suivante');
  }

  onAujourdhui(): void {
    console.log("[Planning] Retour à aujourd'hui");
  }

  onAssignationRapide(): void {
    console.log('[Planning] Assignation rapide');
  }

  onBloquerChambre(): void {
    console.log('[Planning] Blocage de chambre');
  }

  onExporter(): void {
    console.log('[Planning] Export du rack');
  }

  onOuvrirReservation(client: string): void {
    this.panneauVisible.set(true);
    console.log('[Planning] Réservation sélectionnée :', client);
  }

  onRafraichir(): void {
    console.log('[Planning] Rafraîchissement du rack');
  }
}
