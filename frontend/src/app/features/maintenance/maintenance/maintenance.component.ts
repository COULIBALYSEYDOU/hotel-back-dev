import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Clé du filtre segmenté affiché en haut de page. */
type FiltrePanne = 'toutes' | 'encours' | 'pieces' | 'resolues';

interface Ticket {
  /** Rattachement au filtre segmenté. */
  categorie: Exclude<FiltrePanne, 'toutes'>;
  /** Poids de criticité, utilisé par le tri « par urgence ». */
  poids: number;

  carteClass: string;
  barreClass: string;

  chambre: string;
  equipement: string;
  emplacement: string;

  priorite: string;
  prioriteClass: string;

  description: string;
  descriptionClass: string;

  initiales: string;
  avatarClass: string;
  technicien: string;
  nomClass: string;

  heure: string;

  etat: string;
  etatClass: string;
  etatPointClass?: string;
  etatIcon?: string;
}

interface PieceDetachee {
  reference: string;
  designation: string;
  quantite: string;
  statut: string;
}

interface EvenementAudit {
  pastilleClass: string;
  icon: string;
  titre: string;
  meta: string;
  texte: string;
}

@Component({
  selector: 'app-maintenance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <div class="max-w-[1600px] mx-auto flex flex-col gap-6">
        <!-- 1. EN-TÊTE DE PAGE & ACTIONS -->
        <div
          class="flex flex-col md:flex-row md:items-center justify-between gap-4 bg-surface-container-lowest p-6 rounded-xl border border-outline-variant shadow-sm"
        >
          <div class="flex flex-col gap-1">
            <div class="flex items-center gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-low text-primary flex items-center justify-center">
                <span class="material-symbols-outlined text-[20px]">build</span>
              </div>
              <h1 class="text-headline-lg font-headline-lg text-on-surface">Maintenance &amp; SAV Technique</h1>
              <span
                class="px-2.5 py-0.5 rounded-full text-caption font-caption font-bold bg-[#ecfdf5] text-primary border border-primary/20"
                >Système opérationnel</span
              >
            </div>
            <p class="text-body-md font-body-md text-secondary pl-11">
              Supervision des pannes, équipements connectés IoT et interventions d'étage
            </p>
          </div>

          <!-- Quick Filters & Primary CTA -->
          <div class="flex flex-wrap items-center gap-3 pl-11 md:pl-0">
            <!-- Segmented Filter Pills -->
            <div
              class="inline-flex p-1 bg-surface-container-low rounded-lg border border-outline-variant text-label-md font-label-md"
            >
              <button [class]="classePastille('toutes')" (click)="filtre.set('toutes')">Toutes les pannes</button>
              <button [class]="classePastille('encours', true)" (click)="filtre.set('encours')">
                <span>En cours d'intervention</span>
                <span class="w-1.5 h-1.5 rounded-full bg-tertiary"></span>
              </button>
              <button [class]="classePastille('pieces')" (click)="filtre.set('pieces')">En attente pièces</button>
              <button [class]="classePastille('resolues')" (click)="filtre.set('resolues')">
                Résolues aujourd'hui
              </button>
            </div>

            <!-- + Déclarer un Incident SAV -->
            <button
              class="flex items-center gap-2 bg-primary text-on-primary px-4 py-2.5 rounded-lg font-label-lg text-label-lg shadow-sm hover:bg-primary-container transition-all duration-150 cursor-pointer active:scale-[0.98]"
              (click)="onDeclarerIncident()"
            >
              <span class="material-symbols-outlined text-[18px]">add_alert</span>
              + Déclarer un Incident SAV
            </button>
          </div>
        </div>

        <!-- 2. BARRES DE MÉTRIQUES DE SANTÉ DES ÉQUIPEMENTS (Bento KPI Grid) -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
          <!-- KPI 1 : Équipements Opérationnels -->
          <div
            class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between relative overflow-hidden group hover:border-primary/40 transition-colors"
          >
            <div class="flex items-start justify-between">
              <div class="flex flex-col">
                <span class="text-label-md font-label-md text-secondary">Équipements Opérationnels</span>
                <div class="flex items-baseline gap-2 mt-1">
                  <span class="text-display-md font-display-md text-on-surface">96%</span>
                  <span class="text-caption font-caption text-primary flex items-center font-bold">
                    <span class="material-symbols-outlined text-[14px]">arrow_upward</span> +1.2%
                  </span>
                </div>
              </div>
              <div class="w-10 h-10 rounded-lg bg-[#ecfdf5] text-primary flex items-center justify-center">
                <span class="material-symbols-outlined text-[22px]">devices_other</span>
              </div>
            </div>
            <div
              class="mt-4 pt-3 border-t border-surface-container flex items-center justify-between text-body-sm font-body-sm text-secondary"
            >
              <span class="truncate">Climatiseurs Carrier, RFID VingCard, IPTV</span>
              <span class="w-2 h-2 rounded-full bg-primary shrink-0 ml-2"></span>
            </div>
          </div>

          <!-- KPI 2 : Incidents Actifs -->
          <div
            class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between relative overflow-hidden group hover:border-error/40 transition-colors"
          >
            <div class="flex items-start justify-between">
              <div class="flex flex-col">
                <span class="text-label-md font-label-md text-secondary">Incidents Actifs</span>
                <div class="flex items-baseline gap-2 mt-1">
                  <span class="text-display-md font-display-md text-on-surface">2</span>
                  <span class="text-caption font-caption text-secondary">chambres sous alerte</span>
                </div>
              </div>
              <div class="w-10 h-10 rounded-lg bg-[#fff1f2] text-error flex items-center justify-center">
                <span class="material-symbols-outlined text-[22px]">warning</span>
              </div>
            </div>
            <div
              class="mt-4 pt-3 border-t border-surface-container flex items-center justify-between text-body-sm font-body-sm text-secondary"
            >
              <span class="truncate">Ch. 310 (révision), Ch. 205 (plomberie)</span>
              <span class="px-2 py-0.5 rounded text-caption font-caption font-bold bg-[#fff1f2] text-error shrink-0 ml-2"
                >Priorité</span
              >
            </div>
          </div>

          <!-- KPI 3 : Temps Moyen d'Intervention -->
          <div
            class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between relative overflow-hidden group hover:border-primary/40 transition-colors"
          >
            <div class="flex items-start justify-between">
              <div class="flex flex-col">
                <span class="text-label-md font-label-md text-secondary">Temps Moyen d'Intervention</span>
                <div class="flex items-baseline gap-2 mt-1">
                  <span class="text-display-md font-display-md text-on-surface">28</span>
                  <span class="text-headline-sm font-headline-sm text-secondary">min</span>
                </div>
              </div>
              <div class="w-10 h-10 rounded-lg bg-surface-container-low text-secondary flex items-center justify-center">
                <span class="material-symbols-outlined text-[22px]">timer</span>
              </div>
            </div>
            <div
              class="mt-4 pt-3 border-t border-surface-container flex items-center justify-between text-body-sm font-body-sm text-secondary"
            >
              <span>SLA Objectif &lt; 35 min</span>
              <span class="text-caption font-caption font-bold text-primary">-4 min vs sem. dern.</span>
            </div>
          </div>

          <!-- KPI 4 : Incidents Résolus ce jour -->
          <div
            class="bg-surface-container-lowest p-5 rounded-xl border border-outline-variant shadow-sm flex flex-col justify-between relative overflow-hidden group hover:border-primary/40 transition-colors"
          >
            <div class="flex items-start justify-between">
              <div class="flex flex-col">
                <span class="text-label-md font-label-md text-secondary">Incidents Résolus ce jour</span>
                <div class="flex items-baseline gap-2 mt-1">
                  <span class="text-display-md font-display-md text-on-surface">5</span>
                  <span class="text-caption font-caption text-primary font-bold">100% audités</span>
                </div>
              </div>
              <div class="w-10 h-10 rounded-lg bg-[#ecfdf5] text-primary flex items-center justify-center">
                <span class="material-symbols-outlined text-[22px]">task_alt</span>
              </div>
            </div>
            <div
              class="mt-4 pt-3 border-t border-surface-container flex items-center justify-between text-body-sm font-body-sm text-secondary"
            >
              <span class="truncate">Dont Ch. 102 thermostat validé</span>
              <span class="material-symbols-outlined text-[16px] text-primary shrink-0 ml-1">check_circle</span>
            </div>
          </div>
        </div>

        <!-- 3. VUE DOUBLE COLONNE TRÈS AÉRÉE -->
        <div class="grid grid-cols-12 gap-6 items-start pb-8">
          <!-- COLONNE GAUCHE : LISTE DES TICKETS SAV -->
          <div class="col-span-12 lg:col-span-5 flex flex-col gap-4">
            <div class="flex items-center justify-between px-1">
              <div class="flex items-center gap-2">
                <h2 class="text-headline-sm font-headline-sm text-on-surface">Tickets en Traitement</h2>
                <span
                  class="w-5 h-5 rounded-full bg-surface-container-high text-on-surface flex items-center justify-center text-caption font-caption font-bold"
                  >{{ ticketsVisibles().length }}</span
                >
              </div>
              <div class="flex items-center gap-2">
                <button
                  class="p-1.5 text-secondary hover:text-on-surface hover:bg-surface-container rounded-lg text-body-sm font-body-sm flex items-center gap-1"
                  (click)="onBasculerTri()"
                >
                  <span class="material-symbols-outlined text-[18px]">swap_vert</span>
                  Trier par urgence
                </button>
              </div>
            </div>

            <!-- Cartes ticket : la 1re (Ch. 310) est la carte active de la maquette -->
            <div
              *ngFor="let t of ticketsVisibles()"
              [class]="t.carteClass"
              (click)="onSelectionnerTicket(t)"
            >
              <div [class]="t.barreClass"></div>
              <div class="flex items-start justify-between gap-3 mb-3">
                <div class="flex items-center gap-2.5">
                  <div
                    class="px-2.5 py-1 bg-surface-container-low rounded border border-outline-variant font-headline-sm text-headline-sm font-bold text-on-surface"
                  >
                    {{ t.chambre }}
                  </div>
                  <div>
                    <h3 class="font-label-lg text-label-lg text-on-surface">{{ t.equipement }}</h3>
                    <p class="text-body-sm font-body-sm text-secondary">{{ t.emplacement }}</p>
                  </div>
                </div>
                <span [class]="t.prioriteClass">
                  {{ t.priorite }}
                </span>
              </div>

              <p [class]="t.descriptionClass">
                {{ t.description }}
              </p>

              <div
                class="flex items-center justify-between text-body-sm font-body-sm pt-2 border-t border-surface-container"
              >
                <div class="flex items-center gap-2">
                  <span [class]="t.avatarClass">
                    {{ t.initiales }}
                  </span>
                  <span [class]="t.nomClass">{{ t.technicien }}</span>
                </div>
                <div class="flex items-center gap-4 text-secondary">
                  <span class="flex items-center gap-1 text-caption font-caption">
                    <span class="material-symbols-outlined text-[14px]">schedule</span> {{ t.heure }}
                  </span>
                  <span [class]="t.etatClass">
                    <span *ngIf="t.etatPointClass" [class]="t.etatPointClass"></span>
                    <span *ngIf="t.etatIcon" class="material-symbols-outlined text-[14px]">{{ t.etatIcon }}</span>
                    {{ t.etat }}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <!-- COLONNE DROITE : VOLET DÉTAILLÉ DU TICKET -->
          <div class="col-span-12 lg:col-span-7 flex flex-col gap-5">
            <div
              class="bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm flex flex-col gap-6"
            >
              <!-- Détail Header : Chambre & Statut -->
              <div
                class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-5 border-b border-surface-container"
              >
                <div class="flex items-start gap-4">
                  <div
                    class="w-14 h-14 rounded-xl bg-surface-container-low border border-outline-variant flex flex-col items-center justify-center text-primary"
                  >
                    <span class="text-caption font-caption font-bold text-secondary uppercase">CH</span>
                    <span class="text-headline-lg font-headline-lg font-bold text-on-surface">310</span>
                  </div>
                  <div>
                    <div class="flex items-center gap-2.5">
                      <h2 class="text-headline-md font-headline-md text-on-surface">
                        Climatiseur Carrier Split DX-400
                      </h2>
                      <span
                        class="px-2.5 py-0.5 rounded text-label-sm font-label-sm bg-[#ecfdf5] text-primary border border-primary/20 font-semibold"
                      >
                        Intervention en cours
                      </span>
                    </div>
                    <p class="text-body-md font-body-md text-secondary mt-0.5">
                      Ticket #TK-2024-8842 • Ouvert par Passerelle IoT Carrier BACnet
                    </p>
                  </div>
                </div>

                <!-- Action Bar Buttons -->
                <div class="flex items-center gap-2.5">
                  <button
                    class="flex items-center gap-2 bg-surface-container-lowest border border-outline-variant text-on-surface px-3.5 py-2 rounded-lg font-label-md text-label-md hover:bg-surface-container-low transition-colors shadow-sm cursor-pointer active:scale-[0.98]"
                    (click)="onContacterTechnicien()"
                  >
                    <span class="material-symbols-outlined text-[18px] text-primary">call</span>
                    Contacter Technicien
                  </button>
                  <button
                    class="flex items-center gap-2 bg-primary text-on-primary px-4 py-2 rounded-lg font-label-md text-label-md shadow-sm hover:bg-primary-container transition-all cursor-pointer active:scale-[0.98]"
                    (click)="onCloturerTicket()"
                  >
                    <span class="material-symbols-outlined text-[18px]">verified</span>
                    Clôturer le Ticket &amp; Libérer la Chambre
                  </button>
                </div>
              </div>

              <!-- Bento Diagnostic Grid -->
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div class="bg-surface-container-low p-3.5 rounded-lg border border-outline-variant">
                  <span class="text-caption font-caption text-secondary">Localisation Physique</span>
                  <p class="text-label-md font-label-md text-on-surface mt-1">Suite 310 • Aile Sud (Vue Mer)</p>
                  <span class="text-body-sm font-body-sm text-secondary">Accès par badge Master Tech 2</span>
                </div>
                <div class="bg-surface-container-low p-3.5 rounded-lg border border-outline-variant">
                  <span class="text-caption font-caption text-secondary">Technicien assigné</span>
                  <div class="flex items-center gap-2 mt-1">
                    <span
                      class="w-5 h-5 rounded-full bg-secondary-container text-on-secondary-fixed flex items-center justify-center text-caption font-caption font-bold"
                      >MT</span
                    >
                    <p class="text-label-md font-label-md text-on-surface">Mamadou Traoré</p>
                  </div>
                  <span class="text-body-sm font-body-sm text-primary font-semibold">Sur site depuis 22 min</span>
                </div>
                <div class="bg-surface-container-low p-3.5 rounded-lg border border-outline-variant">
                  <span class="text-caption font-caption text-secondary">Statut PMS / Rack hôtelier</span>
                  <div class="flex items-center gap-1.5 mt-1">
                    <span class="w-2 h-2 rounded-full bg-error"></span>
                    <p class="text-label-md font-label-md text-on-surface">Chambre Hors-Service (OOO)</p>
                  </div>
                  <span class="text-body-sm font-body-sm text-secondary">Prochain check-in : 16:30</span>
                </div>
              </div>

              <!-- Pièces Remplacées & Diagnostic Technique -->
              <div class="flex flex-col gap-3">
                <h3 class="text-label-lg font-label-lg text-on-surface flex items-center gap-2">
                  <span class="material-symbols-outlined text-[20px] text-primary">inventory_2</span>
                  Pièces Détachées &amp; Consommables Utilisés
                </h3>
                <div class="bg-surface-container-lowest rounded-lg border border-outline-variant overflow-hidden">
                  <table class="w-full text-left text-body-sm font-body-sm">
                    <thead
                      class="bg-surface-container-low border-b border-outline-variant text-secondary text-caption font-caption font-bold uppercase tracking-wider"
                    >
                      <tr>
                        <th class="py-2.5 px-4">Référence Pièce</th>
                        <th class="py-2.5 px-4">Désignation</th>
                        <th class="py-2.5 px-4">Quantité</th>
                        <th class="py-2.5 px-4 text-right">Statut Pièce</th>
                      </tr>
                    </thead>
                    <tbody class="divide-y divide-surface-container">
                      <tr *ngFor="let p of pieces" class="hover:bg-surface-container-low/50 transition-colors">
                        <td class="py-3 px-4 font-mono text-on-surface font-semibold">{{ p.reference }}</td>
                        <td class="py-3 px-4 text-on-surface">{{ p.designation }}</td>
                        <td class="py-3 px-4 text-secondary">{{ p.quantite }}</td>
                        <td class="py-3 px-4 text-right">
                          <span
                            class="inline-flex items-center px-2 py-0.5 rounded text-caption font-caption font-bold bg-[#ecfdf5] text-primary"
                            >{{ p.statut }}</span
                          >
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>

              <!-- Historique d'Audit & Journal Chronologique -->
              <div class="flex flex-col gap-3">
                <div class="flex items-center justify-between">
                  <h3 class="text-label-lg font-label-lg text-on-surface flex items-center gap-2">
                    <span class="material-symbols-outlined text-[20px] text-primary">history</span>
                    Journal d'Activité Chronologique
                  </h3>
                  <span class="text-caption font-caption text-secondary">Audit Trail certifié ISO 9001</span>
                </div>

                <!-- Timeline Component -->
                <div
                  class="relative pl-6 space-y-6 before:absolute before:left-2.5 before:top-2 before:bottom-2 before:w-0.5 before:bg-outline-variant"
                >
                  <div *ngFor="let e of journal" class="relative flex items-start gap-4">
                    <div [class]="e.pastilleClass">
                      <span class="material-symbols-outlined text-[12px]">{{ e.icon }}</span>
                    </div>
                    <div class="flex-1 bg-surface-container-low p-3 rounded-lg border border-outline-variant">
                      <div class="flex items-center justify-between">
                        <span class="text-label-md font-label-md text-on-surface">{{ e.titre }}</span>
                        <span class="text-caption font-caption text-secondary">{{ e.meta }}</span>
                      </div>
                      <p class="text-body-sm font-body-sm text-secondary mt-1">
                        {{ e.texte }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Ajout d'une note interne pour la réception & housekeeping -->
              <div class="pt-2">
                <label class="text-label-md font-label-md text-secondary block mb-1.5"
                  >Note interne pour la réception &amp; l'équipe d'étage</label
                >
                <div class="flex gap-2">
                  <input
                    class="flex-1 h-10 px-3.5 bg-surface-container-low border border-outline-variant rounded-lg text-body-sm font-body-sm text-on-surface placeholder:text-secondary focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/20"
                    placeholder="Ajouter une instruction (ex: Prévoir ménage de rafraîchissement avant arrivée client VIP)..."
                    type="text"
                    [(ngModel)]="note"
                  />
                  <button
                    class="bg-secondary text-on-secondary px-4 py-2 rounded-lg font-label-md text-label-md hover:bg-on-surface transition-colors cursor-pointer"
                    (click)="onEnregistrerNote()"
                  >
                    Enregistrer la note
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
export class MaintenanceComponent {
  // ---------------------------------------------------------------- État UI
  filtre = signal<FiltrePanne>('toutes');
  /** false = ordre de la maquette, true = tri décroissant par criticité. */
  triUrgence = signal(false);
  note = '';

  // ------------------------------------------------- Variantes de pastilles
  private readonly pastilleActive =
    'px-3 py-1.5 rounded-md bg-surface-container-lowest text-on-surface shadow-sm font-semibold transition-all';
  private readonly pastilleInactive = 'px-3 py-1.5 rounded-md text-secondary hover:text-on-surface transition-all';
  private readonly pastilleActiveFlex =
    'px-3 py-1.5 rounded-md bg-surface-container-lowest text-on-surface shadow-sm font-semibold transition-all flex items-center gap-1.5';
  private readonly pastilleInactiveFlex =
    'px-3 py-1.5 rounded-md text-secondary hover:text-on-surface transition-all flex items-center gap-1.5';

  classePastille(cle: FiltrePanne, avecPoint = false): string {
    const actif = this.filtre() === cle;
    if (avecPoint) return actif ? this.pastilleActiveFlex : this.pastilleInactiveFlex;
    return actif ? this.pastilleActive : this.pastilleInactive;
  }

  // ---------------------------------------------------- Variantes de cartes
  private readonly carteActive =
    'bg-surface-container-lowest rounded-xl p-5 border-2 border-primary shadow-sm hover:shadow-md transition-all cursor-pointer relative overflow-hidden';
  private readonly carteNormale =
    'bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-sm hover:border-outline hover:shadow-md transition-all cursor-pointer relative overflow-hidden';

  private readonly barreVerte = 'absolute top-0 left-0 bottom-0 w-1.5 bg-primary';
  private readonly barreAmbre = 'absolute top-0 left-0 bottom-0 w-1.5 bg-[#f59e0b]';
  private readonly barreNeutre = 'absolute top-0 left-0 bottom-0 w-1.5 bg-outline-variant';

  private readonly badgeHaute =
    'px-2 py-0.5 rounded text-label-sm font-label-sm bg-[#fff1f2] text-error border border-error/20 font-bold tracking-wide uppercase';
  private readonly badgeMoyenne =
    'px-2 py-0.5 rounded text-label-sm font-label-sm bg-[#fffbeb] text-[#b45309] border border-[#b45309]/20 font-bold tracking-wide uppercase';
  private readonly badgeFaible =
    'px-2 py-0.5 rounded text-label-sm font-label-sm bg-surface-container text-secondary border border-outline-variant font-bold tracking-wide uppercase';
  private readonly badgeResolu =
    'px-2 py-0.5 rounded text-label-sm font-label-sm bg-[#ecfdf5] text-primary border border-primary/20 font-bold tracking-wide uppercase';

  private readonly descActive =
    'text-body-sm font-body-sm text-on-surface-variant bg-surface-container-low p-2.5 rounded-lg mb-3';
  private readonly descNormale =
    'text-body-sm font-body-sm text-secondary bg-surface-container-low p-2.5 rounded-lg mb-3';

  private readonly avatarAssigne =
    'w-6 h-6 rounded-full bg-secondary-container text-on-secondary-fixed flex items-center justify-center text-caption font-caption font-bold';
  private readonly avatarVide =
    'w-6 h-6 rounded-full bg-surface-container-high text-secondary flex items-center justify-center text-caption font-caption font-bold';

  // ------------------------------------------------------------- Les données
  tickets: Ticket[] = [
    {
      categorie: 'encours',
      poids: 3,
      carteClass: this.carteActive,
      barreClass: this.barreVerte,
      chambre: 'Ch. 310',
      equipement: 'Climatiseur Carrier Split DX',
      emplacement: 'Étage 3 - Suite Junior Vue Océan',
      priorite: 'Haute',
      prioriteClass: this.badgeHaute,
      description:
        'Signalement capteur IoT : Anomalie débit compresseur et bruit de ventilation supérieur à 48dB.',
      descriptionClass: this.descActive,
      initiales: 'MT',
      avatarClass: this.avatarAssigne,
      technicien: 'Mamadou T.',
      nomClass: 'text-on-surface font-medium',
      heure: '09:42 (il y a 38 min)',
      etat: 'En révision',
      etatClass: 'inline-flex items-center gap-1 text-caption font-caption font-bold text-tertiary',
      etatPointClass: 'w-1.5 h-1.5 rounded-full bg-tertiary animate-ping',
    },
    {
      categorie: 'pieces',
      poids: 2,
      carteClass: this.carteNormale,
      barreClass: this.barreAmbre,
      chambre: 'Ch. 205',
      equipement: 'Vanne thermostatique Sanitaire',
      emplacement: 'Étage 2 - Chambre Double Confort',
      priorite: 'Moyenne',
      prioriteClass: this.badgeMoyenne,
      description:
        'Signalement femme de chambre : Pression eau chaude fluctuante dans le mitigeur douche.',
      descriptionClass: this.descNormale,
      initiales: 'AK',
      avatarClass: this.avatarAssigne,
      technicien: 'Antoine K.',
      nomClass: 'text-on-surface font-medium',
      heure: '10:15',
      etat: 'En attente vanne',
      etatClass: 'text-caption font-caption font-semibold text-[#b45309]',
    },
    {
      categorie: 'pieces',
      poids: 1,
      carteClass: this.carteNormale,
      barreClass: this.barreNeutre,
      chambre: 'Ch. 412',
      equipement: 'Serrure Électronique VingCard',
      emplacement: 'Étage 4 - Suite Deluxe',
      priorite: 'Faible',
      prioriteClass: this.badgeFaible,
      description:
        'Alerte batterie IoT : Pile lithium de la serrure à 12%. Remplacement préventif programmé.',
      descriptionClass: this.descNormale,
      initiales: '--',
      avatarClass: this.avatarVide,
      technicien: 'Non assigné',
      nomClass: 'text-secondary font-medium',
      heure: '08:30',
      etat: "File d'attente",
      etatClass: 'text-caption font-caption font-semibold text-secondary',
    },
    {
      categorie: 'resolues',
      poids: 0,
      carteClass: this.carteNormale,
      barreClass: this.barreVerte,
      chambre: 'Ch. 108',
      equipement: 'IPTV 55" Hospitality Samsung',
      emplacement: 'Étage 1 - Chambre Classique',
      priorite: 'Résolu',
      prioriteClass: this.badgeResolu,
      description:
        'Déconnexion du boîtier passerelle Chromecast. Câble RJ45 rebranché et test de diffusion VOD validé.',
      descriptionClass: this.descNormale,
      initiales: 'MT',
      avatarClass: this.avatarAssigne,
      technicien: 'Mamadou T.',
      nomClass: 'text-on-surface font-medium',
      heure: '08:50',
      etat: 'Validé',
      etatClass: 'text-caption font-caption font-semibold text-primary flex items-center gap-1',
      etatIcon: 'check',
    },
  ];

  /** Le filtre segmenté et le tri par urgence agissent réellement sur la liste. */
  ticketsVisibles = computed(() => {
    const cle = this.filtre();
    const liste = cle === 'toutes' ? [...this.tickets] : this.tickets.filter((t) => t.categorie === cle);
    return this.triUrgence() ? liste.sort((a, b) => b.poids - a.poids) : liste;
  });

  pieces: PieceDetachee[] = [
    {
      reference: 'CR-FLT-882',
      designation: 'Filtre HEPA anti-poussière Carrier 400',
      quantite: '1 unité',
      statut: 'Installé',
    },
    {
      reference: 'VAL-SOL-DX',
      designation: 'Électrovanne de détente thermique R410A',
      quantite: '1 unité',
      statut: 'Remplacée',
    },
    {
      reference: 'SEAL-O-14',
      designation: 'Joint torique haute pression compresseur',
      quantite: '2 unités',
      statut: 'Contrôlé',
    },
  ];

  private readonly pastilleVerte =
    'absolute -left-6 top-1 w-5 h-5 rounded-full bg-primary text-on-primary flex items-center justify-center ring-4 ring-surface-container-lowest';
  private readonly pastilleNeutre =
    'absolute -left-6 top-1 w-5 h-5 rounded-full bg-surface-container-high text-secondary flex items-center justify-center ring-4 ring-surface-container-lowest';
  private readonly pastilleRouge =
    'absolute -left-6 top-1 w-5 h-5 rounded-full bg-error-container text-error flex items-center justify-center ring-4 ring-surface-container-lowest';

  journal: EvenementAudit[] = [
    {
      pastilleClass: this.pastilleVerte,
      icon: 'build',
      titre: 'Remplacement électrovanne effectué',
      meta: '10:18 • Mamadou T.',
      texte:
        'Cycle de purge frigorigène complété. Bruit résiduel mesuré à 32dB (conforme standard luxe hôtel < 35dB). Test thermodynamique en cours pendant 15 minutes.',
    },
    {
      pastilleClass: this.pastilleNeutre,
      icon: 'badge',
      titre: "Badgeage d'entrée chambre",
      meta: '09:56 • Serrure VingCard',
      texte: "Technicien Mamadou T. a accédé à la suite 310 via le pass d'astreinte technique.",
    },
    {
      pastilleClass: this.pastilleNeutre,
      icon: 'assignment_ind',
      titre: 'Ticket assigné & Chambre isolée',
      meta: '09:44 • Claire de Marval',
      texte:
        'Statut PMS de la chambre 310 basculé automatiquement en "Hors Service" (OOO) pour empêcher tout check-in anticipé.',
    },
    {
      pastilleClass: this.pastilleRouge,
      icon: 'sensors',
      titre: 'Alerte télémétrie IoT déclenchée',
      meta: '09:42 • Passerelle BACnet',
      texte:
        'Déviation température consigne : demandée 20.0°C / mesurée 25.4°C en continu depuis 40 minutes.',
    },
  ];

  // ---------------------------------------------------------------- Actions
  onDeclarerIncident(): void {
    console.log('Déclarer un incident SAV');
  }

  onBasculerTri(): void {
    this.triUrgence.update((v) => !v);
  }

  onSelectionnerTicket(t: Ticket): void {
    console.log('Ticket sélectionné', t.chambre);
  }

  onContacterTechnicien(): void {
    console.log('Contacter le technicien Mamadou Traoré');
  }

  onCloturerTicket(): void {
    console.log('Clôturer le ticket #TK-2024-8842 et libérer la chambre 310');
  }

  onEnregistrerNote(): void {
    if (!this.note.trim()) return;
    console.log('Note interne enregistrée :', this.note);
    this.note = '';
  }
}
