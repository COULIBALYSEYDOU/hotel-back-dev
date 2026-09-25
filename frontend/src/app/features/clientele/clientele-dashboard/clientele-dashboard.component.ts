import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Carte client de la colonne de gauche. */
interface FicheClient {
  id: string;
  nom: string;
  initiales: string;
  /** Badge de segment (VIP Élite 2, Gold, Corporate…). */
  badge: string;
  badgeClass: string;
  societe: string;
  societeClass: string;
  nomClass: string;
  /** Vignette d'avatar : classe du cercle à initiales. */
  avatarClass: string;
  /** Les clients VIP ont une étoile en pastille. */
  etoile: boolean;
  /** Carte sélectionnée = bordure primaire épaisse. */
  carteClass: string;
  /** Pied de carte : soit le bloc « Dernier séjour / LTV », soit la ligne compacte. */
  detaille: boolean;
  dernierSejour?: string;
  ltv?: string;
  dernier?: string;
  sejours?: string;
  /** Icône de l'action rapide des cartes compactes. */
  actionIcon: string;
}

/** Une préférence de conciergerie. */
interface Preference {
  icon: string;
  intitule: string;
  valeur: string;
  precision: string;
}

/** Une ligne de l'historique des séjours. */
interface Sejour {
  ligneClass: string;
  periode: string;
  periodeClass: string;
  enCours: boolean;
  nuits: string;
  chambre: string;
  chambreClass: string;
  typeChambre: string;
  statut: string;
  statutClass: string;
  montant: string;
  montantClass: string;
}

/**
 * Fichier Clients & CRM Fidélité — Étoile OS.
 * Porté à l'identique depuis la maquette Stitch « clients ».
 */
@Component({
  selector: 'app-clientele-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <div>

        <!-- 1. En-tête de page & Command Bar -->
        <div class="flex flex-col xl:flex-row xl:items-center xl:justify-between gap-4 mb-6">
          <div>
            <div class="flex items-center gap-2 mb-1">
              <h1 class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">Fichier Clients &amp; CRM Fidélité</h1>
              <span class="px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-primary/10 text-primary border border-primary/20">
                Protocole VIP Actif
              </span>
            </div>
            <p class="text-body-md font-body-md text-secondary">
              Gestion des profils clients, historiques de séjours, préférences et protocole VIP
            </p>
          </div>
          <div class="flex items-center gap-3">
            <button type="button" (click)="onNouveauProfil()"
                    class="px-4 py-2.5 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-lg font-label-lg flex items-center gap-2 shadow-sm transition-all duration-150 active:scale-[0.98]">
              <span class="material-symbols-outlined text-[20px]">person_add</span>
              + Nouveau Profil Client
            </button>
          </div>
        </div>

        <!-- 2. Ligne de métriques CRM Spacieuse (Bento KPI Cards) -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">

          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between">
            <div class="flex items-center justify-between text-secondary mb-2">
              <span class="text-label-md font-label-md">Total Clients Enregistrés</span>
              <span class="p-2 rounded-lg bg-surface-container-low text-primary">
                <span class="material-symbols-outlined text-[20px]">badge</span>
              </span>
            </div>
            <div>
              <div class="text-display-md font-display-md text-on-surface">1 420</div>
              <div class="text-body-sm font-body-sm text-secondary mt-1 flex items-center gap-1.5">
                <span class="text-primary font-semibold flex items-center text-[12px]">
                  <span class="material-symbols-outlined text-[14px]">trending_up</span> +38
                </span>
                profils ajoutés ce mois
              </div>
            </div>
          </div>

          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between">
            <div class="flex items-center justify-between text-secondary mb-2">
              <span class="text-label-md font-label-md">Clients VIP en Séjour Actuel</span>
              <span class="p-2 rounded-lg bg-surface-container-low text-primary">
                <span class="material-symbols-outlined text-[20px]" style="font-variation-settings: 'FILL' 1;">hotel_class</span>
              </span>
            </div>
            <div>
              <div class="text-display-md font-display-md text-on-surface">4 <span class="text-headline-md font-headline-md font-normal text-secondary">résidents</span></div>
              <div class="text-body-sm font-body-sm text-secondary mt-1 flex items-center gap-1.5">
                <span class="w-2 h-2 rounded-full bg-primary inline-block"></span>
                Suites 402, 501, 504 et Villa Lagon
              </div>
            </div>
          </div>

          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between">
            <div class="flex items-center justify-between text-secondary mb-2">
              <span class="text-label-md font-label-md">Taux de Récurrence</span>
              <span class="p-2 rounded-lg bg-surface-container-low text-primary">
                <span class="material-symbols-outlined text-[20px]">repeat</span>
              </span>
            </div>
            <div>
              <div class="text-display-md font-display-md text-on-surface">64%</div>
              <div class="text-body-sm font-body-sm text-secondary mt-1 flex items-center gap-1.5">
                <span class="text-primary font-semibold flex items-center text-[12px]">
                  <span class="material-symbols-outlined text-[14px]">arrow_upward</span> +4.2%
                </span>
                vs trimestre précédent
              </div>
            </div>
          </div>

          <div class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between">
            <div class="flex items-center justify-between text-secondary mb-2">
              <span class="text-label-md font-label-md">Portefeuille Corporate (Mois)</span>
              <span class="p-2 rounded-lg bg-surface-container-low text-primary">
                <span class="material-symbols-outlined text-[20px]">account_balance</span>
              </span>
            </div>
            <div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold">18 400 000 <span class="text-label-lg font-label-lg font-normal text-secondary">FCFA</span></div>
              <div class="text-body-sm font-body-sm text-secondary mt-1 flex items-center gap-1.5">
                <span class="text-primary font-semibold flex items-center text-[12px]">
                  <span class="material-symbols-outlined text-[14px]">trending_up</span> +12%
                </span>
                Objectif mensuel dépassé à 108%
              </div>
            </div>
          </div>
        </div>

        <!-- Filter Bar & Search Sub-Header -->
        <div class="bg-surface-container-lowest p-4 rounded-xl border border-outline-variant shadow-sm mb-6 flex flex-col lg:flex-row items-stretch lg:items-center justify-between gap-4">
          <div class="relative flex-1 max-w-lg">
            <span class="material-symbols-outlined absolute left-3 top-2.5 text-secondary text-[20px]">person_search</span>
            <input type="text" [(ngModel)]="recherche"
                   placeholder="Recherche rapide (Nom, Entreprise, Téléphone)..."
                   class="w-full pl-10 pr-4 py-2 bg-surface-container-low border border-outline-variant rounded-lg text-body-md font-body-md text-on-surface focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/20">
          </div>

          <div class="flex items-center gap-1 overflow-x-auto pb-1 lg:pb-0">
            <button *ngFor="let s of segments" type="button" (click)="segment = s.cle"
                    [class]="segment === s.cle
                      ? 'px-3.5 py-1.5 rounded-lg text-label-md font-label-md bg-surface-container-low text-primary font-bold border border-primary/20 transition-colors'
                      : 'px-3.5 py-1.5 rounded-lg text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low transition-colors'">
              {{ s.libelle }}
            </button>
          </div>

          <div class="flex items-center gap-2">
            <button type="button" class="px-3 py-2 border border-outline-variant rounded-lg text-label-md font-label-md text-secondary hover:bg-surface-container-low flex items-center gap-1.5">
              <span class="material-symbols-outlined text-[18px]">filter_list</span>
              Filtres avancés
            </button>
            <button type="button" class="px-3 py-2 border border-outline-variant rounded-lg text-label-md font-label-md text-secondary hover:bg-surface-container-low flex items-center gap-1.5">
              <span class="material-symbols-outlined text-[18px]">download</span>
              Exporter
            </button>
          </div>
        </div>

        <!-- 3. Disposition aérée en 2 colonnes (Liste vs Profil Détaillé) -->
        <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">

          <!-- ============ COLONNE GAUCHE : Liste des clients ============ -->
          <div class="lg:col-span-5 space-y-3">
            <div class="flex items-center justify-between px-1">
              <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">Résultats ({{ clientsVisibles().length }} clients trouvés)</span>
              <span class="text-caption font-caption text-secondary">Trier par : Fréquence de séjour</span>
            </div>

            <!-- Carte détaillée (client sélectionné) -->
            <ng-container *ngFor="let c of clientsVisibles()">

              <div *ngIf="c.detaille" [class]="c.carteClass" (click)="selectionner(c)">
                <div class="absolute top-4 right-4 flex items-center gap-1">
                  <button type="button" title="Contacter par WhatsApp"
                          class="w-8 h-8 rounded-lg bg-surface-container-low hover:bg-primary/10 text-primary flex items-center justify-center transition-colors">
                    <span class="material-symbols-outlined text-[18px]">chat</span>
                  </button>
                  <button type="button" title="Appeler"
                          class="w-8 h-8 rounded-lg bg-surface-container-low hover:bg-primary/10 text-primary flex items-center justify-center transition-colors">
                    <span class="material-symbols-outlined text-[18px]">call</span>
                  </button>
                </div>
                <div class="flex items-start gap-3.5">
                  <div class="relative">
                    <div [class]="c.avatarClass">{{ c.initiales }}</div>
                    <span *ngIf="c.etoile" class="absolute -bottom-1 -right-1 w-4 h-4 bg-primary text-white rounded-full flex items-center justify-center text-[10px] ring-2 ring-white">
                      ★
                    </span>
                  </div>
                  <div class="pr-16">
                    <div class="flex items-center gap-2">
                      <h3 [class]="c.nomClass">{{ c.nom }}</h3>
                      <span [class]="c.badgeClass">
                        {{ c.badge }}
                      </span>
                    </div>
                    <div [class]="c.societeClass">{{ c.societe }}</div>
                    <div class="mt-3 pt-3 border-t border-outline-variant/60 flex items-center justify-between text-body-sm font-body-sm">
                      <div>
                        <span class="text-caption font-caption text-secondary block">Dernier séjour</span>
                        <span class="font-medium text-on-surface">{{ c.dernierSejour }}</span>
                      </div>
                      <div class="text-right">
                        <span class="text-caption font-caption text-secondary block">LTV Dépensé</span>
                        <span class="font-semibold text-primary">{{ c.ltv }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Cartes compactes -->
              <div *ngIf="!c.detaille" [class]="c.carteClass" (click)="selectionner(c)">
                <div class="flex items-start justify-between">
                  <div class="flex items-start gap-3.5">
                    <div [class]="c.avatarClass">
                      {{ c.initiales }}
                    </div>
                    <div>
                      <div class="flex items-center gap-2">
                        <h3 [class]="c.nomClass">{{ c.nom }}</h3>
                        <span [class]="c.badgeClass">
                          {{ c.badge }}
                        </span>
                      </div>
                      <div [class]="c.societeClass">{{ c.societe }}</div>
                      <div class="mt-2.5 flex items-center gap-4 text-caption font-caption text-secondary">
                        <span>Dernier : {{ c.dernier }}</span>
                        <span>•</span>
                        <span>{{ c.sejours }}</span>
                      </div>
                    </div>
                  </div>
                  <div class="flex items-center gap-1">
                    <button type="button" class="w-8 h-8 rounded-lg text-secondary hover:bg-surface-container-low transition-colors">
                      <span class="material-symbols-outlined text-[18px]">{{ c.actionIcon }}</span>
                    </button>
                  </div>
                </div>
              </div>
            </ng-container>

            <!-- Pagination Bar -->
            <div class="flex items-center justify-between p-3 rounded-lg bg-surface-container-lowest border border-outline-variant text-body-sm font-body-sm text-secondary">
              <span>Affichage de 1 à {{ clientsVisibles().length }} sur 1 420</span>
              <div class="flex items-center gap-1">
                <button type="button" disabled class="p-1 rounded hover:bg-surface-container-low disabled:opacity-30">
                  <span class="material-symbols-outlined text-[18px]">chevron_left</span>
                </button>
                <span class="px-2 py-0.5 bg-surface-container-low text-on-surface font-semibold rounded text-caption font-caption">1</span>
                <button type="button" class="p-1 rounded hover:bg-surface-container-low">
                  <span class="material-symbols-outlined text-[18px]">chevron_right</span>
                </button>
              </div>
            </div>
          </div>

          <!-- ============ COLONNE DROITE : Fiche Détaillée ============ -->
          <div class="lg:col-span-7 space-y-5">
            <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">

              <!-- Banner & En-tête Profil -->
              <div class="p-6 border-b border-outline-variant bg-gradient-to-r from-surface-container-lowest via-surface-container-low/50 to-surface-container-lowest">
                <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
                  <div class="flex items-center gap-4">
                    <div class="relative">
                      <div class="w-16 h-16 rounded-full object-cover ring-4 ring-primary/20 shadow-sm bg-secondary-container text-on-secondary-fixed flex items-center justify-center font-bold text-headline-md">SK</div>
                      <div class="absolute bottom-0 right-0 w-5 h-5 bg-primary text-white rounded-full flex items-center justify-center text-[11px] shadow">
                        ✓
                      </div>
                    </div>
                    <div>
                      <div class="flex items-center gap-3">
                        <h2 class="text-headline-md font-headline-md font-bold text-on-surface">M. Sékou Koné</h2>
                        <span class="px-2.5 py-1 rounded-full text-label-sm font-label-sm bg-primary text-white font-bold tracking-wide flex items-center gap-1 shadow-sm">
                          <span class="material-symbols-outlined text-[14px]" style="font-variation-settings: 'FILL' 1;">star</span>
                          VIP Élite 2 · 14 séjours
                        </span>
                      </div>
                      <div class="text-body-md font-body-md text-secondary mt-0.5">
                        Directeur Général — Africom Holding
                      </div>
                    </div>
                  </div>
                  <div class="flex items-center gap-2">
                    <button type="button" (click)="onModifierPreferences()"
                            class="px-3.5 py-2 rounded-lg border border-outline-variant bg-white hover:bg-surface-container-low text-on-surface text-label-md font-label-md flex items-center gap-2 transition-all">
                      <span class="material-symbols-outlined text-[18px]">edit</span>
                      Modifier Préférences
                    </button>
                  </div>
                </div>

                <!-- Coordonnées & Pièce d'identité -->
                <div class="grid grid-cols-1 sm:grid-cols-3 gap-4 mt-6 pt-5 border-t border-outline-variant">
                  <div class="flex items-center gap-3">
                    <div class="w-9 h-9 rounded-lg bg-surface-container-low flex items-center justify-center text-secondary">
                      <span class="material-symbols-outlined text-[20px]">phone_iphone</span>
                    </div>
                    <div>
                      <div class="text-caption font-caption text-secondary">Téléphone direct</div>
                      <div class="text-body-sm font-body-sm font-semibold text-on-surface">+225 07 48 92 11 00</div>
                    </div>
                  </div>
                  <div class="flex items-center gap-3">
                    <div class="w-9 h-9 rounded-lg bg-surface-container-low flex items-center justify-center text-secondary">
                      <span class="material-symbols-outlined text-[20px]">mail</span>
                    </div>
                    <div>
                      <div class="text-caption font-caption text-secondary">Email professionnel</div>
                      <div class="text-body-sm font-body-sm font-semibold text-on-surface">s.kone&#64;africom-holding.ci</div>
                    </div>
                  </div>
                  <div class="flex items-center gap-3">
                    <div class="w-9 h-9 rounded-lg bg-emerald-50 text-primary flex items-center justify-center">
                      <span class="material-symbols-outlined text-[20px]">verified_user</span>
                    </div>
                    <div>
                      <div class="text-caption font-caption text-secondary">Identité officielle</div>
                      <div class="text-body-sm font-body-sm font-semibold text-on-surface">CNI CI-2022-8941V (Vérifiée)</div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Section : Préférences Personnalisées Conciergerie -->
              <div class="p-6 border-b border-outline-variant">
                <div class="flex items-center justify-between mb-4">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-primary text-[22px]">room_service</span>
                    <h3 class="text-headline-sm font-headline-sm font-bold text-on-surface">
                      Protocole &amp; Préférences Conciergerie
                    </h3>
                  </div>
                  <span class="text-caption font-caption text-primary font-bold bg-primary/10 px-2 py-0.5 rounded">
                    Strictement appliquées
                  </span>
                </div>

                <div class="grid grid-cols-1 sm:grid-cols-2 gap-3.5">
                  <div *ngFor="let p of preferences"
                       class="p-3.5 rounded-lg bg-surface-container-low/70 border border-outline-variant/80 flex items-start gap-3">
                    <div class="p-2 rounded-md bg-white text-primary shadow-xs">
                      <span class="material-symbols-outlined text-[20px]">{{ p.icon }}</span>
                    </div>
                    <div>
                      <div class="text-caption font-caption text-secondary uppercase tracking-wider">{{ p.intitule }}</div>
                      <div class="text-body-md font-body-md font-bold text-on-surface mt-0.5">{{ p.valeur }}</div>
                      <div class="text-caption font-caption text-secondary mt-0.5">{{ p.precision }}</div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Section : Historique des Séjours & Facturation -->
              <div class="p-6 border-b border-outline-variant">
                <div class="flex items-center justify-between mb-3">
                  <h3 class="text-headline-sm font-headline-sm font-bold text-on-surface flex items-center gap-2">
                    <span class="material-symbols-outlined text-secondary text-[20px]">history</span>
                    Historique des 3 Derniers Séjours
                  </h3>
                  <a href="#" class="text-label-md font-label-md text-primary hover:underline flex items-center gap-1">
                    Tout l'historique
                    <span class="material-symbols-outlined text-[16px]">chevron_right</span>
                  </a>
                </div>

                <div class="overflow-x-auto border border-outline-variant rounded-lg">
                  <table class="w-full text-left text-body-sm font-body-sm">
                    <thead class="bg-surface-container-low text-secondary text-caption font-caption uppercase tracking-wider border-b border-outline-variant">
                      <tr>
                        <th class="py-2.5 px-4">Période du Séjour</th>
                        <th class="py-2.5 px-3">Chambre / Type</th>
                        <th class="py-2.5 px-3">Statut</th>
                        <th class="py-2.5 px-4 text-right">Montant Réglé</th>
                      </tr>
                    </thead>
                    <tbody class="divide-y divide-outline-variant">
                      <tr *ngFor="let s of sejours" [class]="s.ligneClass">
                        <td class="py-3 px-4 text-on-surface">
                          <div [class]="s.periodeClass">
                            <span *ngIf="s.enCours" class="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
                            {{ s.periode }}
                          </div>
                          <div class="text-caption font-caption text-secondary">{{ s.nuits }}</div>
                        </td>
                        <td class="py-3 px-3 text-on-surface">
                          <span [class]="s.chambreClass">{{ s.chambre }}</span>
                          <div class="text-caption font-caption text-secondary">{{ s.typeChambre }}</div>
                        </td>
                        <td class="py-3 px-3">
                          <span [class]="s.statutClass">
                            {{ s.statut }}
                          </span>
                        </td>
                        <td [class]="s.montantClass">
                          {{ s.montant }}
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>

              <!-- Section : Notes Internes Confidentielles -->
              <div class="p-6 bg-surface-container-lowest">
                <div class="flex items-center justify-between mb-3">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-amber-600 text-[20px]">lock</span>
                    <h4 class="text-headline-sm font-headline-sm font-bold text-on-surface">
                      Notes Internes Confidentielles Réception
                    </h4>
                  </div>
                  <span class="text-caption font-caption text-secondary">Dernière mise à jour par A. Diop (Hier, 18:40)</span>
                </div>
                <div class="p-3.5 rounded-lg bg-amber-50/60 border border-amber-200/80 text-body-sm font-body-sm text-on-surface leading-relaxed">
                  {{ noteInterne }}
                </div>
                <div class="mt-4 flex items-center justify-end gap-3">
                  <button type="button" (click)="onAjouterNote()"
                          class="px-3.5 py-2 text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors flex items-center gap-1.5">
                    <span class="material-symbols-outlined text-[18px]">add_comment</span>
                    Ajouter une note
                  </button>
                  <button type="button" (click)="onSauvegarder()"
                          class="px-4 py-2 bg-primary text-white rounded-lg text-label-md font-label-md hover:bg-primary-container transition-all flex items-center gap-2 shadow-xs">
                    <span class="material-symbols-outlined text-[18px]">save</span>
                    Sauvegarder la Fiche
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class ClienteleDashboardComponent {
  /** La maquette affiche le champ pré-rempli avec « Koné ». */
  private _recherche = signal('Koné');
  private _touche = signal(false);
  get recherche(): string { return this._recherche(); }
  set recherche(v: string) { this._touche.set(true); this._recherche.set(v); }

  segment = 'vip';

  readonly segments = [
    { cle: 'tous', libelle: 'Tous (1 420)' },
    { cle: 'vip', libelle: 'VIP Élite (48)' },
    { cle: 'corporate', libelle: 'Sociétés / Corporate (312)' },
    { cle: 'nouveaux', libelle: 'Nouveaux clients (64)' },
  ];

  readonly noteInterne =
    '« Client hautement prioritaire pour la direction générale de l\'hôtel. ' +
    'Préfère les départs tardifs sans formalités au desk (check-out express sur carte corporate Africom déjà validée). ' +
    'Organiser le transfert aéroport en berline Mercedes classe E pour le samedi 15 février à 15h00 précises avec le chauffeur habituel (Mamadou). »';

  clients: FicheClient[] = [
    {
      id: 'kone',
      nom: 'M. Sékou Koné',
      initiales: 'SK',
      badge: 'VIP Élite 2',
      badgeClass: 'px-2 py-0.5 rounded text-[10px] font-bold bg-primary/10 text-primary border border-primary/20',
      societe: 'Africom Holding · Direction Générale',
      societeClass: 'text-body-sm font-body-sm text-secondary font-medium mt-0.5',
      nomClass: 'text-headline-sm font-headline-sm font-bold text-on-surface',
      avatarClass: 'w-12 h-12 rounded-full object-cover ring-2 ring-primary/30 bg-secondary-container text-on-secondary-fixed flex items-center justify-center font-bold text-label-lg',
      etoile: true,
      carteClass: 'p-4 rounded-xl bg-surface-container-lowest border-2 border-primary shadow-sm hover:shadow-md transition-all cursor-pointer relative',
      detaille: true,
      dernierSejour: 'En cours (Ch. 504)',
      ltv: '14 850 000 FCFA',
      actionIcon: 'chat',
    },
    {
      id: 'belmont',
      nom: 'Mme Claire Belmont',
      initiales: 'CB',
      badge: 'Gold',
      badgeClass: 'px-2 py-0.5 rounded text-[10px] font-bold bg-amber-50 text-amber-700 border border-amber-300',
      societe: 'TotalEnergies Exploration',
      societeClass: 'text-body-sm font-body-sm text-secondary mt-0.5',
      nomClass: 'text-headline-sm font-headline-sm font-semibold text-on-surface',
      avatarClass: 'w-12 h-12 rounded-full bg-secondary-container text-on-secondary-fixed flex items-center justify-center font-bold text-label-lg',
      etoile: false,
      carteClass: 'p-4 rounded-xl bg-surface-container-lowest border border-outline-variant hover:border-slate-300 shadow-sm transition-all cursor-pointer',
      detaille: false,
      dernier: '14 janv. 2025',
      sejours: '7 séjours (3 420 000 FCFA)',
      actionIcon: 'chat',
    },
    {
      id: 'traore',
      nom: 'Dr. Jean-Philippe Traoré',
      initiales: 'JT',
      badge: 'Corporate',
      badgeClass: 'px-2 py-0.5 rounded text-[10px] font-bold bg-surface-container-low text-secondary border border-outline-variant',
      societe: 'BCEAO Siège Régional',
      societeClass: 'text-body-sm font-body-sm text-secondary mt-0.5',
      nomClass: 'text-headline-sm font-headline-sm font-semibold text-on-surface',
      avatarClass: 'w-12 h-12 rounded-full bg-surface-container-high text-on-surface-variant flex items-center justify-center font-bold text-label-lg',
      etoile: false,
      carteClass: 'p-4 rounded-xl bg-surface-container-lowest border border-outline-variant hover:border-slate-300 shadow-sm transition-all cursor-pointer',
      detaille: false,
      dernier: '02 fév. 2025',
      sejours: '4 séjours (2 180 000 FCFA)',
      actionIcon: 'call',
    },
  ];

  /**
   * Le champ de recherche filtre réellement la liste (nom + société).
   * Tant que l'utilisateur n'a pas touché au champ, la valeur pré-remplie de la
   * maquette (« Koné ») reste décorative : les 3 fiches restent affichées, comme
   * dans la maquette. Dès la première frappe, le filtre devient effectif.
   */
  clientsVisibles = computed(() => {
    if (!this._touche()) return this.clients;
    const q = this._recherche().trim().toLowerCase();
    if (!q) return this.clients;
    return this.clients.filter(c =>
      `${c.nom} ${c.societe}`.toLowerCase().includes(q),
    );
  });

  preferences: Preference[] = [
    {
      icon: 'ac_unit',
      intitule: 'Climatisation Suite',
      valeur: '21.5°C constant',
      precision: 'Activer 3h avant arrivée',
    },
    {
      icon: 'local_cafe',
      intitule: "Boisson d'Accueil VIP",
      valeur: 'Kinkeliba glacé au citron',
      precision: 'Sans sucre ajouté, carafe verre',
    },
    {
      icon: 'newspaper',
      intitule: 'Presse Quotidienne',
      valeur: 'Jeune Afrique & Le Figaro',
      precision: 'Déposé sous porte à 06h30',
    },
    {
      icon: 'balcony',
      intitule: 'Attribution Chambre',
      valeur: 'Étage élevé (5+) vue lagune',
      precision: "Éloigné des machineries d'ascenseurs",
    },
  ];

  sejours: Sejour[] = [
    {
      ligneClass: 'bg-primary/5 font-medium',
      periode: '10 Fév. — 15 Fév. 2025',
      periodeClass: 'font-bold text-primary flex items-center gap-1.5',
      enCours: true,
      nuits: '5 nuits (Séjour en cours)',
      chambre: 'Suite 504',
      chambreClass: 'font-bold',
      typeChambre: 'Suite Junior Exécutive',
      statut: 'Occupée',
      statutClass: 'px-2 py-0.5 rounded text-[11px] font-bold bg-primary text-white',
      montant: '1 650 000 FCFA',
      montantClass: 'py-3 px-4 text-right font-bold text-primary',
    },
    {
      ligneClass: 'hover:bg-surface-container-low/40 transition-colors',
      periode: '12 Janv. — 16 Janv. 2025',
      periodeClass: 'font-semibold text-on-surface',
      enCours: false,
      nuits: '4 nuits',
      chambre: 'Suite 501',
      chambreClass: '',
      typeChambre: 'Suite Présidentielle',
      statut: 'Facturé Africom',
      statutClass: 'px-2 py-0.5 rounded text-[11px] font-medium bg-surface-container-low text-secondary',
      montant: '2 400 000 FCFA',
      montantClass: 'py-3 px-4 text-right font-semibold text-on-surface',
    },
    {
      ligneClass: 'hover:bg-surface-container-low/40 transition-colors',
      periode: '22 Nov. — 25 Nov. 2024',
      periodeClass: 'font-semibold text-on-surface',
      enCours: false,
      nuits: '3 nuits',
      chambre: 'Suite 504',
      chambreClass: '',
      typeChambre: 'Suite Junior Exécutive',
      statut: 'Facturé Africom',
      statutClass: 'px-2 py-0.5 rounded text-[11px] font-medium bg-surface-container-low text-secondary',
      montant: '1 050 000 FCFA',
      montantClass: 'py-3 px-4 text-right font-semibold text-on-surface',
    },
  ];

  selectionner(c: FicheClient): void {
    console.log('Client sélectionné', c.id);
  }

  onNouveauProfil(): void {
    console.log('Nouveau profil client');
  }

  onModifierPreferences(): void {
    console.log('Modifier les préférences');
  }

  onAjouterNote(): void {
    console.log('Ajouter une note interne');
  }

  onSauvegarder(): void {
    console.log('Sauvegarde de la fiche client');
  }
}
