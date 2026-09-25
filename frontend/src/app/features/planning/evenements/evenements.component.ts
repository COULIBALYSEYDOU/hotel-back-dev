import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormSlideoverComponent, FormField } from '@shared/components/form-slideover/form-slideover.component';

/** Filtres rapides de la liste « Séminaires & Événements » (maquette Étoile OS). */
type FiltreEvenement = 'tous' | 'aujourdhui' | 'avenir' | 'vip';

@Component({
  selector: 'app-evenements',
  standalone: true,
  imports: [CommonModule, FormSlideoverComponent],
  template: `
    <div class="space-y-6">
      <!-- Title and Top Action Toolbar -->
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div class="flex items-center gap-2.5">
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Événements, Salons &amp; Banqueting</h1>
            <span class="px-2.5 py-0.5 text-caption font-caption rounded-full bg-surface-container text-primary font-bold border border-outline-variant">Abidjan Plateau</span>
          </div>
          <p class="text-body-md font-body-md text-secondary mt-1">Gestion des 4 espaces réceptifs, séminaires corporate, mariages et prestations banquet.</p>
        </div>
        <!-- Action Toolbar -->
        <div class="flex items-center flex-wrap gap-2.5">
          <!-- Date Selector -->
          <div class="flex items-center gap-2 px-3.5 py-2 bg-surface-container-lowest border border-outline-variant rounded-lg text-on-surface text-label-md font-label-md shadow-sm">
            <span class="material-symbols-outlined text-secondary text-[18px]">calendar_today</span>
            <span>Cette semaine (21 - 27 Octobre 2024)</span>
            <span class="material-symbols-outlined text-secondary text-[16px]">expand_more</span>
          </div>
          <!-- Planning Graphique des Salles -->
          <button (click)="onPlanningGraphique()" class="px-3.5 py-2 rounded-lg bg-surface-container-lowest hover:bg-surface-container-low border border-outline-variant text-on-surface text-label-md font-label-md flex items-center gap-2 transition-colors shadow-sm">
            <span class="material-symbols-outlined text-primary text-[18px]">view_timeline</span>
            <span>Planning Graphique</span>
          </button>
          <!-- Export Fiches de Fonction -->
          <button (click)="onFichesFonction()" class="px-3.5 py-2 rounded-lg bg-surface-container-lowest hover:bg-surface-container-low border border-outline-variant text-on-surface text-label-md font-label-md flex items-center gap-2 transition-colors shadow-sm">
            <span class="material-symbols-outlined text-secondary text-[18px]">picture_as_pdf</span>
            <span>Fiches de Fonction</span>
          </button>
          <!-- Nouveau Devis / Banquet CTA -->
          <button (click)="openNew = true" class="px-4 py-2 rounded-lg bg-primary hover:bg-primary-container text-on-primary text-label-md font-label-md flex items-center gap-2 shadow-sm transition-all active:scale-[0.98]">
            <span class="material-symbols-outlined text-[18px]">post_add</span>
            <span>+ Devis / Nouveau Banquet</span>
          </button>
        </div>
      </div>

      <!-- ===================================================================== -->
      <!-- 4 KPIS SPÉCIFIQUES ÉVÉNEMENTS                                          -->
      <!-- ===================================================================== -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
        <!-- KPI 1 -->
        <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all">
          <div class="flex items-center justify-between text-secondary">
            <span class="text-label-md font-label-md">CA Événements du mois</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">paid</span>
            </div>
          </div>
          <div class="mt-3 flex items-baseline gap-2">
            <span class="text-headline-lg font-headline-lg text-on-surface font-bold">14 850 000</span>
            <span class="text-label-sm font-label-sm text-secondary">FCFA</span>
          </div>
          <div class="mt-2.5 flex items-center gap-1.5 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold text-caption font-caption bg-surface-container px-1.5 py-0.5 rounded">
              <span class="material-symbols-outlined text-[14px]">trending_up</span> +18%
            </span>
            <span class="text-secondary text-caption font-caption">vs mois précédent (Sept)</span>
          </div>
        </div>
        <!-- KPI 2 -->
        <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all">
          <div class="flex items-center justify-between text-secondary">
            <span class="text-label-md font-label-md">Événements confirmés</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">event_available</span>
            </div>
          </div>
          <div class="mt-3 flex items-baseline gap-2">
            <span class="text-headline-lg font-headline-lg text-on-surface font-bold">12</span>
            <span class="text-label-sm font-label-sm text-secondary">séminaires &amp; banquets</span>
          </div>
          <div class="mt-2.5 flex items-center gap-1.5 text-body-sm font-body-sm">
            <span class="text-on-surface font-semibold text-caption font-caption bg-surface-container-low px-1.5 py-0.5 rounded">
              850 participants
            </span>
            <span class="text-secondary text-caption font-caption">cumulés cette semaine</span>
          </div>
        </div>
        <!-- KPI 3 -->
        <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all">
          <div class="flex items-center justify-between text-secondary">
            <span class="text-label-md font-label-md">Taux d'occupation salles</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">meeting_room</span>
            </div>
          </div>
          <div class="mt-3 flex items-baseline gap-2">
            <span class="text-headline-lg font-headline-lg text-on-surface font-bold">84.5%</span>
            <span class="text-label-sm font-label-sm text-primary font-semibold">Haute demande</span>
          </div>
          <div class="mt-2.5 flex items-center gap-1.5 text-body-sm font-body-sm">
            <span class="text-secondary text-caption font-caption truncate">Ébène, Salons Lagune &amp; Patio</span>
          </div>
        </div>
        <!-- KPI 4 -->
        <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all">
          <div class="flex items-center justify-between text-secondary">
            <span class="text-label-md font-label-md">Encaissements &amp; Acomptes</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container-low flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">account_balance_wallet</span>
            </div>
          </div>
          <div class="mt-3 flex items-baseline gap-2">
            <span class="text-headline-lg font-headline-lg text-on-surface font-bold">9 200 000</span>
            <span class="text-label-sm font-label-sm text-secondary">FCFA</span>
          </div>
          <div class="mt-2.5 flex items-center gap-1.5 text-body-sm font-body-sm">
            <span class="inline-flex items-center text-primary font-semibold text-caption font-caption bg-surface-container px-1.5 py-0.5 rounded">
              Wave &amp; Virements
            </span>
            <span class="text-secondary text-caption font-caption">100% rapproché en caisse</span>
          </div>
        </div>
      </div>

      <!-- ===================================================================== -->
      <!-- SECTION PRINCIPALE 2 COLONNES (GRILLE BENTO MODERNE)                  -->
      <!-- ===================================================================== -->
      <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        <!-- =================================================================== -->
        <!-- COLONNE GAUCHE (2/3 -> 8 COLONNES) : LISTE DES ÉVÉNEMENTS           -->
        <!-- =================================================================== -->
        <div class="lg:col-span-8 space-y-4">
          <!-- En-tête de filtre de la liste -->
          <div class="p-4 rounded-xl bg-surface-container-lowest border border-outline-variant flex flex-wrap items-center justify-between gap-3">
            <div class="flex items-center gap-2">
              <span class="text-headline-sm font-headline-sm text-on-surface">Séminaires &amp; Événements</span>
              <span class="text-caption font-caption px-2 py-0.5 bg-surface-container-low rounded-full font-semibold text-secondary">4 en cours / programmés</span>
            </div>
            <!-- Filtres rapides -->
            <div class="flex items-center gap-1.5">
              <button (click)="onFiltre('tous')" [class]="classeFiltre('tous')">Tous</button>
              <button (click)="onFiltre('aujourdhui')" [class]="classeFiltre('aujourdhui')">Aujourd'hui (2)</button>
              <button (click)="onFiltre('avenir')" [class]="classeFiltre('avenir')">À venir (2)</button>
              <button (click)="onFiltre('vip')" [class]="classeFiltre('vip')">Banqueting VIP</button>
            </div>
          </div>

          <!-- LISTE D'ÉVÉNEMENTS CARTE BENTO -->
          <div class="space-y-3.5">
            <!-- Événement 1 : Africom Holding -->
            <div *ngIf="estAffiche('EVT-2024-089')" class="p-5 rounded-xl bg-surface-container-lowest border-2 border-primary/40 shadow-sm hover:shadow transition-all relative overflow-hidden group">
              <div class="absolute left-0 top-0 bottom-0 w-1.5 bg-primary"></div>
              <div class="flex flex-col sm:flex-row sm:items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2.5">
                    <span class="px-2.5 py-0.5 rounded-full text-caption font-caption bg-surface-container text-primary font-bold border border-outline-variant flex items-center gap-1">
                      <span class="w-1.5 h-1.5 rounded-full bg-primary animate-pulse"></span>
                      En cours (Jour 2/3)
                    </span>
                    <span class="text-caption font-caption text-secondary">Dossier #EVT-2024-089</span>
                  </div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface group-hover:text-primary transition-colors">
                    Séminaire Annuel Africom Holding
                  </h3>
                  <div class="flex flex-wrap items-center gap-y-1 gap-x-3 text-body-sm font-body-sm text-secondary pt-1">
                    <span class="flex items-center gap-1 font-medium text-on-surface">
                      <span class="material-symbols-outlined text-[16px] text-primary">domain</span>
                      Grande Salle Ébène
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">groups</span>
                      Plénière 120 participants
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">event_seat</span>
                      Format Théâtre
                    </span>
                  </div>
                </div>
                <!-- Montant & Acompte -->
                <div class="sm:text-right flex flex-col sm:items-end justify-between">
                  <div>
                    <div class="text-headline-sm font-headline-sm text-on-surface font-bold">4 200 000 FCFA</div>
                    <div class="inline-flex items-center gap-1 px-2 py-0.5 rounded text-caption font-caption bg-surface-container text-primary font-semibold mt-1">
                      <span class="material-symbols-outlined text-[12px]" style="font-variation-settings: 'FILL' 1;">check_circle</span>
                      Acompte 100% réglé (Wave Business)
                    </div>
                  </div>
                </div>
              </div>
              <!-- Prestations F&B & Services inclus -->
              <div class="mt-4 pt-3.5 border-t border-outline-variant/60 flex flex-wrap items-center justify-between gap-3 text-body-sm font-body-sm">
                <div class="flex flex-wrap items-center gap-2">
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-primary">coffee</span>
                    Pause-café viennoiseries ivoiriennes
                  </span>
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-primary">restaurant</span>
                    Déjeuner buffet prestige terrasse
                  </span>
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-primary">mic</span>
                    Sonorisation &amp; Micros HF (x4)
                  </span>
                </div>
                <button (click)="onFicheFonction('EVT-2024-089')" class="px-3 py-1.5 text-label-sm font-label-sm font-semibold text-primary hover:bg-surface-container-low rounded-lg transition-colors flex items-center gap-1">
                  <span>Fiche fonction</span>
                  <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                </button>
              </div>
            </div>

            <!-- Événement 2 : FinTech UEMOA 2024 -->
            <div *ngIf="estAffiche('EVT-2024-092')" class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all relative overflow-hidden group">
              <div class="absolute left-0 top-0 bottom-0 w-1.5 bg-secondary"></div>
              <div class="flex flex-col sm:flex-row sm:items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2.5">
                    <span class="px-2.5 py-0.5 rounded-full text-caption font-caption bg-secondary-container text-on-secondary-container font-bold flex items-center gap-1">
                      <span class="material-symbols-outlined text-[12px]">schedule</span>
                      Confirmé (Demain 08h30)
                    </span>
                    <span class="text-caption font-caption text-secondary">Dossier #EVT-2024-092</span>
                  </div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface group-hover:text-primary transition-colors">
                    Conférence FinTech UEMOA 2024
                  </h3>
                  <div class="flex flex-wrap items-center gap-y-1 gap-x-3 text-body-sm font-body-sm text-secondary pt-1">
                    <span class="flex items-center gap-1 font-medium text-on-surface">
                      <span class="material-symbols-outlined text-[16px] text-primary">domain</span>
                      Grand Salon Lagune
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">groups</span>
                      80 participants
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">school</span>
                      Format Salle de classe
                    </span>
                  </div>
                </div>
                <!-- Montant & Acompte -->
                <div class="sm:text-right flex flex-col sm:items-end justify-between">
                  <div>
                    <div class="text-headline-sm font-headline-sm text-on-surface font-bold">3 100 000 FCFA</div>
                    <div class="inline-flex items-center gap-1 px-2 py-0.5 rounded text-caption font-caption bg-surface-container text-secondary font-semibold mt-1">
                      <span class="material-symbols-outlined text-[12px]">info</span>
                      Acompte 60% versé (Solde à l'arrivée)
                    </div>
                  </div>
                </div>
              </div>
              <div class="mt-4 pt-3.5 border-t border-outline-variant/60 flex flex-wrap items-center justify-between gap-3 text-body-sm font-body-sm">
                <div class="flex flex-wrap items-center gap-2">
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-secondary">videocam</span>
                    Écrans 4K &amp; Régie Hybride Streaming
                  </span>
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-secondary">translate</span>
                    Cabine d'interprétation simultanée
                  </span>
                </div>
                <button (click)="onConsulterDevis('EVT-2024-092')" class="px-3 py-1.5 text-label-sm font-label-sm font-semibold text-secondary hover:text-primary hover:bg-surface-container-low rounded-lg transition-colors flex items-center gap-1">
                  <span>Consulter le devis</span>
                  <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                </button>
              </div>
            </div>

            <!-- Événement 3 : BICI-CI Gala -->
            <div *ngIf="estAffiche('EVT-2024-098')" class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all relative overflow-hidden group">
              <div class="absolute left-0 top-0 bottom-0 w-1.5 bg-primary-container"></div>
              <div class="flex flex-col sm:flex-row sm:items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2.5">
                    <span class="px-2.5 py-0.5 rounded-full text-caption font-caption bg-surface-container text-primary font-bold flex items-center gap-1">
                      <span class="material-symbols-outlined text-[12px]">verified</span>
                      Fiche de fonction validée (Samedi soir)
                    </span>
                    <span class="text-caption font-caption text-secondary">Dossier #EVT-2024-098</span>
                  </div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface group-hover:text-primary transition-colors">
                    Dîner de Gala &amp; Célébration BICI-CI
                  </h3>
                  <div class="flex flex-wrap items-center gap-y-1 gap-x-3 text-body-sm font-body-sm text-secondary pt-1">
                    <span class="flex items-center gap-1 font-medium text-on-surface">
                      <span class="material-symbols-outlined text-[16px] text-primary">nature_people</span>
                      Espace Patio Baobab
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">groups</span>
                      150 convives
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">dinner_dining</span>
                      Cocktail dînatoire + 5 services
                    </span>
                  </div>
                </div>
                <!-- Montant & Acompte -->
                <div class="sm:text-right flex flex-col sm:items-end justify-between">
                  <div>
                    <div class="text-headline-sm font-headline-sm text-on-surface font-bold">5 800 000 FCFA</div>
                    <div class="inline-flex items-center gap-1 px-2 py-0.5 rounded text-caption font-caption bg-surface-container text-primary font-semibold mt-1">
                      <span class="material-symbols-outlined text-[12px]">task_alt</span>
                      Virement Corporate reçu (Banque SGBCI)
                    </div>
                  </div>
                </div>
              </div>
              <div class="mt-4 pt-3.5 border-t border-outline-variant/60 flex flex-wrap items-center justify-between gap-3 text-body-sm font-body-sm">
                <div class="flex flex-wrap items-center gap-2">
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-primary">wine_bar</span>
                    Bar à Champagne &amp; Canapés chauds
                  </span>
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-primary">music_note</span>
                    Orchestre acoustique &amp; Lumière d'ambiance
                  </span>
                </div>
                <button (click)="onDetailsTraiteur('EVT-2024-098')" class="px-3 py-1.5 text-label-sm font-label-sm font-semibold text-secondary hover:text-primary hover:bg-surface-container-low rounded-lg transition-colors flex items-center gap-1">
                  <span>Détails traiteur</span>
                  <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                </button>
              </div>
            </div>

            <!-- Événement 4 : Orange Côte d'Ivoire -->
            <div *ngIf="estAffiche('EVT-2024-101')" class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm hover:border-outline transition-all relative overflow-hidden group">
              <div class="absolute left-0 top-0 bottom-0 w-1.5 bg-secondary-fixed"></div>
              <div class="flex flex-col sm:flex-row sm:items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2.5">
                    <span class="px-2.5 py-0.5 rounded-full text-caption font-caption bg-surface-container-high text-on-surface font-bold flex items-center gap-1">
                      <span class="material-symbols-outlined text-[12px]">pending</span>
                      En préparation (Vendredi 10h)
                    </span>
                    <span class="text-caption font-caption text-secondary">Dossier #EVT-2024-101</span>
                  </div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface group-hover:text-primary transition-colors">
                    Conseil d'Administration Orange Côte d'Ivoire
                  </h3>
                  <div class="flex flex-wrap items-center gap-y-1 gap-x-3 text-body-sm font-body-sm text-secondary pt-1">
                    <span class="flex items-center gap-1 font-medium text-on-surface">
                      <span class="material-symbols-outlined text-[16px] text-primary">shield</span>
                      Salon VIP Baie des Étoiles
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">groups</span>
                      18 administrateurs
                    </span>
                    <span>•</span>
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[16px] text-secondary">table_restaurant</span>
                      Format Table impériale
                    </span>
                  </div>
                </div>
                <!-- Montant & Acompte -->
                <div class="sm:text-right flex flex-col sm:items-end justify-between">
                  <div>
                    <div class="text-headline-sm font-headline-sm text-on-surface font-bold">1 750 000 FCFA</div>
                    <div class="inline-flex items-center gap-1 px-2 py-0.5 rounded text-caption font-caption bg-surface-container-low text-secondary font-semibold mt-1">
                      <span class="material-symbols-outlined text-[12px]">receipt_long</span>
                      Bon de commande validé
                    </div>
                  </div>
                </div>
              </div>
              <div class="mt-4 pt-3.5 border-t border-outline-variant/60 flex flex-wrap items-center justify-between gap-3 text-body-sm font-body-sm">
                <div class="flex flex-wrap items-center gap-2">
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-secondary">security</span>
                    Ligne fibre dédiée chiffrée
                  </span>
                  <span class="px-2.5 py-1 rounded-md bg-surface-container-low text-secondary text-caption font-caption flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[14px] text-secondary">room_service</span>
                    Déjeuner d'affaires en salle privatisée
                  </span>
                </div>
                <button (click)="onAssignerProtocole('EVT-2024-101')" class="px-3 py-1.5 text-label-sm font-label-sm font-semibold text-secondary hover:text-primary hover:bg-surface-container-low rounded-lg transition-colors flex items-center gap-1">
                  <span>Assigner protocole</span>
                  <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- =================================================================== -->
        <!-- COLONNE DROITE (1/3 -> 4 COLONNES) : STATUT SALLES & FICHE DE FONCTION -->
        <!-- =================================================================== -->
        <div class="lg:col-span-4 space-y-6">
          <!-- CARTE OCCUPATION DES 4 SALLES EN TEMPS RÉEL -->
          <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm space-y-4">
            <div class="flex items-center justify-between">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-[20px]">meeting_room</span>
                <h2 class="text-headline-sm font-headline-sm text-on-surface">Occupation Salles (4)</h2>
              </div>
              <span class="text-caption font-caption px-2 py-0.5 rounded-full bg-surface-container text-primary font-bold">En direct</span>
            </div>
            <!-- Liste des 4 salles -->
            <div class="space-y-3">
              <!-- Salle 1: Grande Salle Ébène -->
              <div class="p-3.5 rounded-lg border border-outline-variant bg-surface-container-low/40 flex items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2">
                    <span class="w-2.5 h-2.5 rounded-full bg-error ring-2 ring-error/20"></span>
                    <h4 class="text-label-md font-label-md text-on-surface font-bold">Grande Salle Ébène</h4>
                  </div>
                  <p class="text-body-sm font-body-sm text-secondary">Capacité 150 plénière • Occupée jusqu'à 18h00</p>
                  <div class="flex items-center gap-2 text-caption font-caption text-secondary">
                    <span class="px-1.5 py-0.5 rounded bg-surface-container text-on-surface">Africom Holding</span>
                    <span class="flex items-center gap-0.5 text-primary">
                      <span class="material-symbols-outlined text-[12px]">ac_unit</span> Carrier 21°C
                    </span>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-0.5 rounded bg-error-container text-on-error-container font-semibold">Occupée</span>
              </div>
              <!-- Salle 2: Salon Lagune -->
              <div class="p-3.5 rounded-lg border border-outline-variant bg-surface-container-low/40 flex items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2">
                    <span class="w-2.5 h-2.5 rounded-full bg-primary ring-2 ring-primary/20"></span>
                    <h4 class="text-label-md font-label-md text-on-surface font-bold">Salon Lagune</h4>
                  </div>
                  <p class="text-body-sm font-body-sm text-secondary">Capacité 80 • Libre actuellement</p>
                  <div class="flex items-center gap-2 text-caption font-caption text-secondary">
                    <span class="flex items-center gap-1 text-secondary">
                      <span class="material-symbols-outlined text-[12px]">cleaning_services</span>
                      Dressage en cours pour demain 08h30
                    </span>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-0.5 rounded bg-surface-container text-primary font-semibold">Dressage</span>
              </div>
              <!-- Salle 3: Salon VIP Baie des Étoiles -->
              <div class="p-3.5 rounded-lg border border-outline-variant bg-surface-container-low/40 flex items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2">
                    <span class="w-2.5 h-2.5 rounded-full bg-secondary ring-2 ring-secondary/20"></span>
                    <h4 class="text-label-md font-label-md text-on-surface font-bold">Salon VIP Baie des Étoiles</h4>
                  </div>
                  <p class="text-body-sm font-body-sm text-secondary">Capacité 20 bordée • Réservé 14h - 17h</p>
                  <div class="flex items-center gap-2 text-caption font-caption text-secondary">
                    <span class="px-1.5 py-0.5 rounded bg-surface-container-high text-on-surface">Visite protocolaire</span>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-0.5 rounded bg-secondary-container text-on-secondary-container font-semibold">Réservé</span>
              </div>
              <!-- Salle 4: Espace Patio Baobab -->
              <div class="p-3.5 rounded-lg border border-outline-variant bg-surface-container-low/40 flex items-start justify-between gap-3">
                <div class="space-y-1">
                  <div class="flex items-center gap-2">
                    <span class="w-2.5 h-2.5 rounded-full bg-primary ring-2 ring-primary/20"></span>
                    <h4 class="text-label-md font-label-md text-on-surface font-bold">Espace Patio Baobab</h4>
                  </div>
                  <p class="text-body-sm font-body-sm text-secondary">Capacité 200 cocktail • Plein air aménagé</p>
                  <div class="flex items-center gap-2 text-caption font-caption text-secondary">
                    <span class="text-primary font-medium">Libre (Test sono prévu 16h00)</span>
                  </div>
                </div>
                <span class="text-caption font-caption px-2 py-0.5 rounded bg-surface-container text-primary font-semibold">Disponible</span>
              </div>
            </div>
          </div>

          <!-- FICHE DE FONCTION RAPIDE DE L'ÉVÉNEMENT EN COURS -->
          <div class="p-5 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm space-y-4">
            <div class="flex items-center justify-between pb-3 border-b border-outline-variant">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-[20px]">assignment</span>
                <h2 class="text-headline-sm font-headline-sm text-on-surface">Fiche de Fonction Rapide</h2>
              </div>
              <span class="text-caption font-caption text-secondary font-mono">EVT-089</span>
            </div>
            <!-- Contexte de l'événement sélectionné -->
            <div>
              <div class="text-label-sm font-label-sm text-secondary uppercase tracking-wider">Événement ciblé</div>
              <div class="text-label-lg font-label-lg text-on-surface font-bold mt-0.5">Africom Holding • Plénière Annuelle</div>
            </div>
            <!-- Grille de détails logistiques -->
            <div class="grid grid-cols-2 gap-3 text-body-sm font-body-sm">
              <div class="p-2.5 rounded-lg bg-surface-container-low">
                <span class="text-caption font-caption text-secondary block">Régisseur référent</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface flex items-center gap-1 mt-0.5">
                  <span class="material-symbols-outlined text-[14px] text-primary">support_agent</span>
                  Mamadou Traoré (Poste 204)
                </span>
              </div>
              <div class="p-2.5 rounded-lg bg-surface-container-low">
                <span class="text-caption font-caption text-secondary block">Contact client</span>
                <span class="text-label-sm font-label-sm font-semibold text-on-surface flex items-center gap-1 mt-0.5">
                  <span class="material-symbols-outlined text-[14px] text-primary">phone_iphone</span>
                  Mme Christine K. (+225)
                </span>
              </div>
            </div>
            <!-- Spécifications Restauration & Traiteur -->
            <div class="space-y-2">
              <span class="text-label-sm font-label-sm text-secondary font-semibold">Menu &amp; Exigences alimentaires</span>
              <div class="p-3 rounded-lg border border-outline-variant bg-surface-container-low/20 space-y-2 text-body-sm font-body-sm">
                <div class="flex items-start gap-2">
                  <span class="material-symbols-outlined text-[16px] text-primary mt-0.5">check</span>
                  <span>Pause 15h30 : Café filtre Grand Bassam, jus de bissap bio frais, gaufrettes locales.</span>
                </div>
                <div class="flex items-start gap-2">
                  <span class="material-symbols-outlined text-[16px] text-primary mt-0.5">no_meals</span>
                  <span class="text-secondary"><strong class="text-on-surface font-semibold">Allergies signalées :</strong> 4 végétariens stricts, 2 intolérances arachide (plateaux identifiés par le chef).</span>
                </div>
              </div>
            </div>
            <!-- Protocoles Techniques & Réseau -->
            <div class="space-y-2">
              <span class="text-label-sm font-label-sm text-secondary font-semibold">Banqueting &amp; Technique</span>
              <div class="flex flex-col gap-1.5 text-body-sm font-body-sm text-secondary">
                <div class="flex items-center justify-between p-2 rounded bg-surface-container-low">
                  <span class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-[16px] text-primary">wifi</span>
                    SSID VIP : <strong class="text-on-surface">Etoile-Africom-Conf</strong>
                  </span>
                  <span class="text-caption font-caption text-primary font-bold">100 Mbps symétrique</span>
                </div>
                <div class="flex items-center justify-between p-2 rounded bg-surface-container-low">
                  <span class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-[16px] text-primary">palette</span>
                    Éclairage scénique plénière
                  </span>
                  <span class="text-caption font-caption text-secondary">Teinte Ambre Chaud 3000K</span>
                </div>
              </div>
            </div>
            <!-- Action boutons de la fiche -->
            <div class="pt-2 flex items-center gap-2">
              <button (click)="onImprimerCuisine()" class="flex-1 py-2 px-3 rounded-lg bg-surface-container hover:bg-surface-container-high text-on-surface text-label-sm font-label-sm font-semibold flex items-center justify-center gap-1.5 transition-colors">
                <span class="material-symbols-outlined text-[16px]">print</span>
                Imprimer Cuisine
              </button>
              <button (click)="onModifierFiche()" class="flex-1 py-2 px-3 rounded-lg bg-primary hover:bg-primary-container text-on-primary text-label-sm font-label-sm font-semibold flex items-center justify-center gap-1.5 transition-colors">
                <span class="material-symbols-outlined text-[16px]">edit</span>
                Modifier Fiche
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <app-form-slideover
      [(open)]="openNew"
      title="Nouveau devis / banquet"
      subtitle="Créez un dossier événementiel."
      submitLabel="Créer"
      [fields]="createFields"
      [(model)]="newForm"
      (submitted)="onCreate($event)" />
  `,
})
export class EvenementsComponent {
  // ---------------------------------------------------------------------------
  // Filtres rapides de la liste
  // ---------------------------------------------------------------------------
  filtre = signal<FiltreEvenement>('tous');

  private readonly filtreActifClasses =
    'px-3 py-1 rounded-md bg-surface-container text-primary font-label-sm text-label-sm font-semibold';
  private readonly filtreInactifClasses =
    'px-3 py-1 rounded-md text-secondary hover:text-on-surface hover:bg-surface-container-low font-label-sm text-label-sm';

  classeFiltre(cle: FiltreEvenement): string {
    return this.filtre() === cle ? this.filtreActifClasses : this.filtreInactifClasses;
  }

  onFiltre(cle: FiltreEvenement) {
    this.filtre.set(cle);
  }

  /**
   * Appartenance de chaque dossier aux filtres, déduite de la maquette elle-même :
   *  - « Aujourd'hui (2) » : EVT-089 (En cours, Jour 2/3) et EVT-101
   *    (Salon VIP Baie des Étoiles, « Réservé 14h - 17h » dans le panneau Occupation Salles).
   *  - « À venir (2) »     : EVT-092 (Demain 08h30) et EVT-098 (Samedi soir).
   *  - « Banqueting VIP »  : EVT-098 (gala / traiteur) et EVT-101 (Salon VIP).
   */
  private readonly appartenances: Record<string, FiltreEvenement[]> = {
    'EVT-2024-089': ['aujourdhui'],
    'EVT-2024-092': ['avenir'],
    'EVT-2024-098': ['avenir', 'vip'],
    'EVT-2024-101': ['aujourdhui', 'vip'],
  };

  estAffiche(dossier: string): boolean {
    const f = this.filtre();
    return f === 'tous' || (this.appartenances[dossier] ?? []).includes(f);
  }

  // ---------------------------------------------------------------------------
  // Actions de la barre d'outils et des cartes
  // ---------------------------------------------------------------------------
  onPlanningGraphique() { console.log('[Evenements] planning graphique des salles'); }
  onFichesFonction()    { console.log('[Evenements] export PDF des fiches de fonction'); }

  onFicheFonction(dossier: string)     { console.log('[Evenements] fiche fonction', dossier); }
  onConsulterDevis(dossier: string)    { console.log('[Evenements] consulter le devis', dossier); }
  onDetailsTraiteur(dossier: string)   { console.log('[Evenements] détails traiteur', dossier); }
  onAssignerProtocole(dossier: string) { console.log('[Evenements] assigner protocole', dossier); }

  onImprimerCuisine() { console.log('[Evenements] impression de la fiche cuisine EVT-089'); }
  onModifierFiche()   { console.log('[Evenements] modification de la fiche de fonction EVT-089'); }

  // ---------------------------------------------------------------------------
  // Création d'un dossier événementiel (logique métier préexistante conservée)
  // ---------------------------------------------------------------------------
  openNew = false;
  createFields: FormField[] = [
    { key: 'nom', label: "Nom de l'événement", type: 'text', required: true, colSpan: 2 },
    { key: 'client', label: 'Client', type: 'text', required: true },
    { key: 'salle', label: 'Salle', type: 'select', options: ['Grande Salle Ébène','Grand Salon Lagune','Salon VIP Baie des Étoiles','Espace Patio Baobab'] },
    { key: 'date', label: 'Date', type: 'date', required: true },
    { key: 'heure', label: 'Heure', type: 'text', placeholder: '19h' },
    { key: 'invites', label: 'Nombre d\'invités', type: 'number', min: 1 },
    { key: 'ca', label: 'Devis (FCFA)', type: 'number', min: 0 },
    { key: 'commentaire', label: 'Description', type: 'textarea', colSpan: 2 },
  ];
  newForm: Record<string, any> = { nom: '', client: '', salle: '', date: '', heure: '', invites: 1, ca: 0, commentaire: '' };

  onCreate(data: Record<string, any>) {
    console.log('[EvenementsComponent] nouvel enregistrement :', data);
    // TODO : brancher au backend Spring Boot
    this.newForm = { nom: '', client: '', salle: '', date: '', heure: '', invites: 1, ca: 0, commentaire: '' };
  }
}
