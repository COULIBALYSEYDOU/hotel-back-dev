import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

/** Une ligne du tableau « Transactions Récentes ». */
interface Transaction {
  heure: string;
  reference: string;
  client: string;
  chambre: string;
  type: string;
  /** Libellé de la méthode de paiement (« Wave CI », « Visa •• 4912 »…). */
  methode: string;
  methodeClass: string;
  methodePointClass: string;
  montant: string;
  statut: string;
  statutClass: string;
  /** Ligne surlignée (paiement en attente de confirmation). */
  ligneClass: string;
  recuDisponible: boolean;
  recuIcon: string;
  recuClass: string;
  recuTitre: string;
}

/** Un compte marchand mobile synchronisé (Wave Business, Orange Marchand…). */
interface DepotMarchand {
  pointClass: string;
  intitule: string;
  detail: string;
  montant: string;
}

/**
 * Finance & Encaissements — porté à l'identique depuis la maquette Stitch
 * « finance_encaissements » (bloc <main>, lignes 333-810).
 *
 * Le shell (sidebar + topbar) reste celui validé précédemment : seuls les
 * éléments de la zone de travail sont repris ici.
 */
@Component({
  selector: 'app-encaissements',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="contents">
      <div class="max-w-[1600px] mx-auto space-y-6">

        <!-- SECTION 1: HEADER & ACTIONS -->
        <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 pb-2">
          <div>
            <div class="flex items-center gap-2">
              <h2 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Finance &amp; Encaissements</h2>
              <span class="px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-surface-container text-primary font-semibold">Shift #248</span>
            </div>
            <p class="text-body-md font-body-md text-secondary mt-1">
              Suivi des flux de caisse, paiements mobiles et clôture de shift
            </p>
          </div>
          <div class="flex items-center flex-wrap gap-3">
            <!-- Period Selector -->
            <button type="button" (click)="onChoisirPeriode()"
                    class="flex items-center gap-2 bg-surface-container-lowest border border-outline-variant px-3 py-2 rounded-lg text-label-md font-label-md shadow-xs">
              <span class="material-symbols-outlined text-secondary text-lg">calendar_today</span>
              <span class="text-on-surface font-medium">Aujourd'hui, 24 Oct 2024</span>
              <span class="material-symbols-outlined text-secondary text-sm">expand_more</span>
            </button>
            <!-- Secondary Action: Clôturer la Caisse -->
            <button type="button" (click)="onCloturerCaisse()"
                    class="flex items-center gap-2 bg-surface-container-lowest border border-outline-variant hover:bg-surface-container-low text-on-surface px-4 py-2 rounded-lg transition-all duration-200 ease-in-out cursor-pointer active:scale-[0.98] shadow-xs">
              <span class="material-symbols-outlined text-secondary text-base">lock_clock</span>
              <span class="text-label-md font-label-md font-semibold">Clôturer la Caisse</span>
            </button>
            <!-- Primary Action: Nouvel Encaissement -->
            <button type="button" (click)="onNouvelEncaissement()"
                    class="flex items-center gap-2 bg-primary hover:bg-primary-container text-on-primary px-4 py-2 rounded-lg transition-all duration-200 ease-in-out cursor-pointer active:scale-[0.98] shadow-sm">
              <span class="material-symbols-outlined text-base">add_circle</span>
              <span class="text-label-md font-label-md font-semibold">Nouvel Encaissement</span>
            </button>
          </div>
        </div>

        <!-- SECTION 2: KPIS FINANCIERS AÉRÉS (Bento Card Grid) -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">

          <!-- KPI 1: Total Revenue -->
          <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs flex flex-col justify-between relative overflow-hidden">
            <div class="absolute top-0 right-0 w-24 h-24 bg-primary/5 rounded-bl-full pointer-events-none"></div>
            <div>
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Chiffre d'Affaires du Jour</span>
                <span class="p-1.5 rounded-lg bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-sm">analytics</span>
                </span>
              </div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">
                3 250 000 <span class="text-label-md font-label-md text-secondary font-normal">FCFA</span>
              </div>
            </div>
            <div class="mt-4 flex items-center gap-1.5 text-primary text-body-sm font-body-sm">
              <span class="material-symbols-outlined text-sm font-bold">trending_up</span>
              <span class="font-semibold">+12%</span>
              <span class="text-secondary text-caption font-caption">vs J-1 (2 900 000 F)</span>
            </div>
          </div>

          <!-- KPI 2: Wave CI -->
          <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs flex flex-col justify-between">
            <div>
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Encaissements Wave CI</span>
                <span class="px-2 py-0.5 rounded-md bg-cyan-50 text-cyan-700 text-caption font-caption font-bold border border-cyan-200">WAVE</span>
              </div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">
                1 450 000 <span class="text-label-md font-label-md text-secondary font-normal">FCFA</span>
              </div>
            </div>
            <div class="mt-4 flex items-center justify-between">
              <div class="flex items-center gap-2 w-full">
                <div class="w-full bg-surface-container rounded-full h-1.5 overflow-hidden">
                  <div class="bg-cyan-500 h-1.5 rounded-full" style="width: 45%"></div>
                </div>
                <span class="text-label-sm font-label-sm font-semibold text-secondary whitespace-nowrap">45% du mix</span>
              </div>
            </div>
          </div>

          <!-- KPI 3: Orange Money -->
          <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs flex flex-col justify-between">
            <div>
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Orange Money</span>
                <span class="px-2 py-0.5 rounded-md bg-orange-50 text-orange-700 text-caption font-caption font-bold border border-orange-200">OM CI</span>
              </div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">
                980 000 <span class="text-label-md font-label-md text-secondary font-normal">FCFA</span>
              </div>
            </div>
            <div class="mt-4 flex items-center justify-between">
              <div class="flex items-center gap-2 w-full">
                <div class="w-full bg-surface-container rounded-full h-1.5 overflow-hidden">
                  <div class="bg-orange-500 h-1.5 rounded-full" style="width: 30%"></div>
                </div>
                <span class="text-label-sm font-label-sm font-semibold text-secondary whitespace-nowrap">30%</span>
              </div>
            </div>
          </div>

          <!-- KPI 4: Carte Bancaire -->
          <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs flex flex-col justify-between">
            <div>
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Carte Bancaire</span>
                <div class="flex items-center gap-1 text-secondary">
                  <span class="material-symbols-outlined text-base">credit_card</span>
                </div>
              </div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">
                520 000 <span class="text-label-md font-label-md text-secondary font-normal">FCFA</span>
              </div>
            </div>
            <div class="mt-4 flex items-center justify-between">
              <div class="flex items-center gap-2 w-full">
                <div class="w-full bg-surface-container rounded-full h-1.5 overflow-hidden">
                  <div class="bg-indigo-600 h-1.5 rounded-full" style="width: 16%"></div>
                </div>
                <span class="text-label-sm font-label-sm font-semibold text-secondary whitespace-nowrap">16%</span>
              </div>
            </div>
          </div>

          <!-- KPI 5: Espèces Réception -->
          <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs flex flex-col justify-between border-l-4 border-l-primary">
            <div>
              <div class="flex items-center justify-between mb-3">
                <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Espèces en Caisse</span>
                <span class="p-1 rounded bg-surface-container text-primary">
                  <span class="material-symbols-outlined text-sm">point_of_sale</span>
                </span>
              </div>
              <div class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">
                300 000 <span class="text-label-md font-label-md text-secondary font-normal">FCFA</span>
              </div>
            </div>
            <div class="mt-4 flex items-center justify-between text-caption font-caption">
              <span class="px-2 py-0.5 rounded bg-emerald-50 text-primary border border-primary/20 font-semibold">
                Écart caisse: 0 F
              </span>
              <span class="text-secondary">Conforme</span>
            </div>
          </div>
        </div>

        <!-- SECTION 3: DEUX SECTIONS AVEC RESPIRATION GÉNÉREUSE -->
        <div class="grid grid-cols-1 xl:grid-cols-12 gap-6 items-start">

          <!-- ========== COLONNE GAUCHE (LARGE) : TABLEAU DES TRANSACTIONS ========== -->
          <div class="xl:col-span-8 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-xs overflow-hidden">

            <!-- Table Toolbar -->
            <div class="p-5 border-b border-outline-variant flex flex-col sm:flex-row items-start sm:items-center justify-between gap-3">
              <div>
                <h3 class="text-headline-md font-headline-md text-on-surface font-semibold">Transactions Récentes</h3>
                <p class="text-body-sm font-body-sm text-secondary">18 encaissements enregistrés sur la session actuelle</p>
              </div>
              <div class="flex items-center gap-2">
                <button type="button" (click)="filtreOuvert.set(!filtreOuvert())"
                        class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg border border-outline-variant text-secondary hover:text-on-surface text-label-md font-label-md transition-colors">
                  <span class="material-symbols-outlined text-sm">filter_list</span>
                  <span class="">Filtrer</span>
                </button>
                <button type="button" (click)="onExporterCsv()"
                        class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg border border-outline-variant text-secondary hover:text-on-surface text-label-md font-label-md transition-colors">
                  <span class="material-symbols-outlined text-sm">download</span>
                  <span class="">Exporter CSV</span>
                </button>
              </div>
            </div>

            <!-- Champ de filtre (dévoilé par le bouton « Filtrer ») -->
            <div *ngIf="filtreOuvert()" class="px-5 py-3 border-b border-outline-variant bg-surface">
              <div class="flex items-center gap-2 px-3 py-2 rounded-lg bg-surface-container-lowest border border-outline-variant">
                <span class="material-symbols-outlined text-secondary text-lg">search</span>
                <input type="text" [(ngModel)]="recherche"
                       placeholder="Filtrer par client, référence, chambre ou méthode…"
                       class="w-full bg-transparent outline-none text-body-sm font-body-sm text-on-surface placeholder:text-secondary" />
              </div>
            </div>

            <!-- Table Container -->
            <div class="overflow-x-auto">
              <table class="w-full text-left border-collapse">
                <thead>
                  <tr class="bg-surface border-b border-outline-variant">
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary">Heure</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary">Réf Transaction</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary">Client &amp; Chambre</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary">Type</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary">Méthode</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary text-right">Montant</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary text-center">Statut</th>
                    <th class="py-3.5 px-4 text-caption font-caption uppercase tracking-wider text-secondary text-center">Reçu</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant text-body-sm font-body-sm">
                  <tr *ngFor="let t of transactionsVisibles()" [class]="t.ligneClass">
                    <td class="py-3 px-4 text-secondary font-mono text-xs">{{ t.heure }}</td>
                    <td class="py-3 px-4 font-mono font-medium text-xs text-on-surface">{{ t.reference }}</td>
                    <td class="py-3 px-4">
                      <div class="font-semibold text-on-surface">{{ t.client }}</div>
                      <div class="text-caption font-caption text-secondary">{{ t.chambre }}</div>
                    </td>
                    <td class="py-3 px-4">
                      <span class="inline-flex items-center text-xs font-medium text-on-surface-variant">{{ t.type }}</span>
                    </td>
                    <td class="py-3 px-4">
                      <span [class]="t.methodeClass">
                        <span [class]="t.methodePointClass"></span> {{ t.methode }}
                      </span>
                    </td>
                    <td class="py-3 px-4 text-right font-bold text-on-surface font-mono">
                      {{ t.montant }}
                    </td>
                    <td class="py-3 px-4 text-center">
                      <span [class]="t.statutClass">
                        {{ t.statut }}
                      </span>
                    </td>
                    <td class="py-3 px-4 text-center">
                      <button type="button" [class]="t.recuClass" [title]="t.recuTitre"
                              [disabled]="!t.recuDisponible" (click)="onTelechargerRecu(t)">
                        <span class="material-symbols-outlined text-lg">{{ t.recuIcon }}</span>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- Pagination Footer -->
            <div class="p-4 bg-surface border-t border-outline-variant flex items-center justify-between text-body-sm font-body-sm">
              <span class="text-secondary text-caption font-caption">Affichage de 1-{{ transactionsVisibles().length }} sur 18 encaissements</span>
              <div class="flex items-center gap-1">
                <button type="button" disabled
                        class="px-2.5 py-1 rounded border border-outline-variant text-secondary hover:bg-surface-container-low text-xs disabled:opacity-50">Précédent</button>
                <button type="button" class="px-2.5 py-1 rounded bg-primary text-on-primary text-xs font-semibold">1</button>
                <button type="button" class="px-2.5 py-1 rounded border border-outline-variant text-secondary hover:bg-surface-container-low text-xs">2</button>
                <button type="button" class="px-2.5 py-1 rounded border border-outline-variant text-secondary hover:bg-surface-container-low text-xs">3</button>
                <button type="button" class="px-2.5 py-1 rounded border border-outline-variant text-secondary hover:bg-surface-container-low text-xs">Suivant</button>
              </div>
            </div>
          </div>

          <!-- ========== COLONNE DROITE (PANNEAU 380px) : POINT DE CAISSE ========== -->
          <div class="xl:col-span-4 w-full space-y-5">

            <!-- Carte Principale du Shift & Caisse -->
            <div class="bg-surface-container-lowest rounded-xl p-5 border border-outline-variant shadow-xs">
              <div class="flex items-center justify-between pb-4 border-b border-outline-variant">
                <div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface font-semibold">Point de Caisse &amp; Shift</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Rapprochement opérationnel</p>
                </div>
                <span class="material-symbols-outlined text-primary text-xl">shield_lock</span>
              </div>

              <!-- Identité de la réceptionniste en poste -->
              <div class="mt-4 p-3 bg-surface rounded-lg border border-outline-variant/60 flex items-center gap-3">
                <div class="w-10 h-10 rounded-full bg-primary-fixed text-on-primary-fixed font-bold flex items-center justify-center text-sm">
                  AT
                </div>
                <div>
                  <div class="text-label-md font-label-md text-on-surface font-semibold">Awa Touré</div>
                  <div class="text-caption font-caption text-secondary">Hôtesse Réception • Shift Matin (08h - 16h)</div>
                </div>
              </div>

              <!-- Décompte Caisse Physique -->
              <div class="mt-4 space-y-2.5">
                <div class="flex justify-between items-center text-body-sm font-body-sm">
                  <span class="text-secondary">Fond de caisse initial :</span>
                  <span class="font-mono font-medium text-on-surface">50 000 FCFA</span>
                </div>
                <div class="flex justify-between items-center text-body-sm font-body-sm">
                  <span class="text-secondary">Encaissements espèces reçus :</span>
                  <span class="font-mono font-semibold text-primary">+250 000 FCFA</span>
                </div>
                <div class="flex justify-between items-center text-body-sm font-body-sm">
                  <span class="text-secondary">Décaissements / Menue dépense :</span>
                  <span class="font-mono font-medium text-secondary">0 FCFA</span>
                </div>
                <div class="pt-2 border-t border-dashed border-outline-variant flex justify-between items-center">
                  <span class="text-label-md font-label-md text-on-surface font-bold">Total Théorique en Tiroir :</span>
                  <span class="text-headline-sm font-headline-sm font-bold text-on-surface font-mono">300 000 FCFA</span>
                </div>
              </div>

              <!-- Zone de contrôle d'écart -->
              <div class="mt-4 p-3 rounded-lg bg-emerald-50 border border-primary/20 flex items-center justify-between">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-primary text-lg">check_circle</span>
                  <span class="text-label-md font-label-md text-primary font-semibold">Caisse Balancée</span>
                </div>
                <span class="text-caption font-caption font-mono font-bold text-primary">ÉCART = 0 FCFA</span>
              </div>

              <!-- Historique des Dépôts Marchands Mobiles du Jour -->
              <div class="mt-5">
                <div class="flex items-center justify-between mb-2.5">
                  <span class="text-label-sm font-label-sm uppercase tracking-wider text-secondary">Dépôts Comptes Marchands</span>
                  <span class="text-caption font-caption text-secondary">Sync Auto 14:45</span>
                </div>
                <div class="space-y-2">
                  <div *ngFor="let d of depots"
                       class="flex items-center justify-between p-2.5 bg-surface rounded-lg text-body-sm font-body-sm">
                    <div class="flex items-center gap-2">
                      <span [class]="d.pointClass"></span>
                      <div>
                        <div class="text-label-md font-label-md text-on-surface">{{ d.intitule }}</div>
                        <div class="text-caption font-caption text-secondary">{{ d.detail }}</div>
                      </div>
                    </div>
                    <div class="text-right font-mono font-semibold text-on-surface text-xs">
                      {{ d.montant }}
                    </div>
                  </div>
                </div>
              </div>

              <!-- Actions Rapides de Réconciliation -->
              <div class="mt-6 space-y-2.5 pt-4 border-t border-outline-variant">
                <button type="button" (click)="onReconciliation()"
                        class="w-full flex items-center justify-center gap-2 py-2.5 px-4 rounded-lg bg-surface-container-low hover:bg-surface-container text-on-surface border border-outline-variant font-semibold text-label-md font-label-md transition-all duration-200 ease-in-out cursor-pointer active:scale-[0.98]">
                  <span class="material-symbols-outlined text-primary text-base">sync_saved_locally</span>
                  <span class="">Réconciliation Automatique</span>
                </button>
                <button type="button" (click)="onRapportXZ()"
                        class="w-full flex items-center justify-center gap-2 py-2.5 px-4 rounded-lg bg-surface-container-lowest hover:bg-surface-container-low text-secondary hover:text-on-surface border border-outline-variant font-semibold text-label-md font-label-md transition-all duration-200 ease-in-out cursor-pointer active:scale-[0.98]">
                  <span class="material-symbols-outlined text-base">print</span>
                  <span class="">Générer rapport X/Z</span>
                </button>
              </div>
            </div>

            <!-- Carte Astuce & Rappel Protocole Hôtel -->
            <div class="bg-surface-container-lowest rounded-xl p-4 border border-outline-variant shadow-xs flex items-start gap-3">
              <span class="material-symbols-outlined text-primary text-xl mt-0.5">info</span>
              <div>
                <p class="text-label-md font-label-md text-on-surface font-semibold">Note de Transmission</p>
                <p class="text-body-sm font-body-sm text-secondary mt-0.5 leading-relaxed">
                  Le transfert de caisse vers le coffre principal sera visé par le Superviseur à 16h00. Veillez à rattacher tous les bordereaux Wave et TPE.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class EncaissementsComponent {
  filtreOuvert = signal(false);

  private _recherche = signal('');
  get recherche(): string { return this._recherche(); }
  set recherche(v: string) { this._recherche.set(v); }

  /* eslint-disable @typescript-eslint/member-ordering */

  // --- Classes partagées, extraites telles quelles de la maquette ---------
  private readonly ligneNormale = 'hover:bg-surface-container-low transition-colors duration-150 group';
  private readonly ligneAttente = 'hover:bg-surface-container-low transition-colors duration-150 group bg-amber-50/20';

  private readonly methodeWave =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-cyan-50 text-cyan-800 text-xs font-semibold border border-cyan-200';
  private readonly methodeOm =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-orange-50 text-orange-800 text-xs font-semibold border border-orange-200';
  private readonly methodeCarte =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-blue-50 text-blue-800 text-xs font-semibold border border-blue-200';
  private readonly methodeEspeces =
    'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-emerald-50 text-emerald-800 text-xs font-semibold border border-emerald-200';

  private readonly pointWave = 'w-1.5 h-1.5 rounded-full bg-cyan-500';
  private readonly pointOm = 'w-1.5 h-1.5 rounded-full bg-orange-500';
  private readonly pointCarte = 'w-1.5 h-1.5 rounded-full bg-blue-600';
  private readonly pointEspeces = 'w-1.5 h-1.5 rounded-full bg-emerald-600';

  private readonly statutValide =
    'inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-emerald-50 text-primary border border-primary/20';
  private readonly statutAttente =
    'inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-amber-50 text-amber-800 border border-amber-300';

  private readonly recuActif = 'p-1 text-secondary hover:text-primary rounded hover:bg-surface transition-colors';
  private readonly recuInactif = 'p-1 text-secondary/40 cursor-not-allowed rounded';

  transactions: Transaction[] = [
    {
      heure: '14:42',
      reference: 'TRX-94801',
      client: 'M. Sékou Koné',
      chambre: 'Ch. 102 • Suite Lagune',
      type: 'Acompte hébergement',
      methode: 'Wave CI',
      methodeClass: this.methodeWave,
      methodePointClass: this.pointWave,
      montant: '280 000 F',
      statut: 'Validé',
      statutClass: this.statutValide,
      ligneClass: this.ligneNormale,
      recuDisponible: true,
      recuIcon: 'receipt_long',
      recuClass: this.recuActif,
      recuTitre: 'Télécharger le reçu PDF',
    },
    {
      heure: '14:15',
      reference: 'TRX-94796',
      client: 'Mme Aminata Diallo',
      chambre: 'Ch. 205 • Deluxe Balcon',
      type: 'Caution séjour',
      methode: 'Orange Money',
      methodeClass: this.methodeOm,
      methodePointClass: this.pointOm,
      montant: '100 000 F',
      statut: 'Validé',
      statutClass: this.statutValide,
      ligneClass: this.ligneNormale,
      recuDisponible: true,
      recuIcon: 'receipt_long',
      recuClass: this.recuActif,
      recuTitre: 'Télécharger le reçu PDF',
    },
    {
      heure: '13:50',
      reference: 'TRX-94788',
      client: 'M. Jean-Philippe Moreau',
      chambre: 'Ch. 301 • Suite Exécutive',
      type: 'Restauration room service',
      methode: 'Visa •• 4912',
      methodeClass: this.methodeCarte,
      methodePointClass: this.pointCarte,
      montant: '68 500 F',
      statut: 'Validé',
      statutClass: this.statutValide,
      ligneClass: this.ligneNormale,
      recuDisponible: true,
      recuIcon: 'receipt_long',
      recuClass: this.recuActif,
      recuTitre: 'Télécharger le reçu PDF',
    },
    {
      heure: '12:30',
      reference: 'TRX-94775',
      client: 'M. Ibrahim Bamba',
      chambre: 'Ch. 104 • Standard Jardin',
      type: 'Solde séjour express',
      methode: 'Espèces Réception',
      methodeClass: this.methodeEspeces,
      methodePointClass: this.pointEspeces,
      montant: '75 000 F',
      statut: 'Validé',
      statutClass: this.statutValide,
      ligneClass: this.ligneNormale,
      recuDisponible: true,
      recuIcon: 'receipt_long',
      recuClass: this.recuActif,
      recuTitre: 'Télécharger le reçu PDF',
    },
    {
      heure: '11:55',
      reference: 'TRX-94760',
      client: 'Mme Fatou Camara',
      chambre: 'Ch. 402 • Penthouse',
      type: 'Spa & Soins bien-être',
      methode: 'Wave CI',
      methodeClass: this.methodeWave,
      methodePointClass: this.pointWave,
      montant: '45 000 F',
      statut: 'En attente',
      statutClass: this.statutAttente,
      ligneClass: this.ligneAttente,
      recuDisponible: false,
      recuIcon: 'hourglass_empty',
      recuClass: this.recuInactif,
      recuTitre: 'Reçu non disponible',
    },
    {
      heure: '10:12',
      reference: 'TRX-94742',
      client: 'SARL Ivoire Tech',
      chambre: 'Séminaire • Salle Ebène',
      type: 'Location salle & banquet',
      methode: 'Mastercard •• 8820',
      methodeClass: this.methodeCarte,
      methodePointClass: this.pointCarte,
      montant: '451 500 F',
      statut: 'Validé',
      statutClass: this.statutValide,
      ligneClass: this.ligneNormale,
      recuDisponible: true,
      recuIcon: 'receipt_long',
      recuClass: this.recuActif,
      recuTitre: 'Télécharger le reçu PDF',
    },
  ];

  /** Filtre réel sur client, référence, chambre, type et méthode. */
  transactionsVisibles = computed(() => {
    const q = this._recherche().trim().toLowerCase();
    if (!q) return this.transactions;
    return this.transactions.filter(t =>
      [t.client, t.reference, t.chambre, t.type, t.methode, t.heure]
        .join(' ')
        .toLowerCase()
        .includes(q),
    );
  });

  depots: DepotMarchand[] = [
    {
      pointClass: 'w-2 h-2 rounded-full bg-cyan-500',
      intitule: 'Wave Business #E01',
      detail: '11 transactions reçues',
      montant: '1 450 000 F',
    },
    {
      pointClass: 'w-2 h-2 rounded-full bg-orange-500',
      intitule: 'Orange Marchand #774',
      detail: '5 transactions reçues',
      montant: '980 000 F',
    },
  ];

  onChoisirPeriode(): void { console.log('Sélecteur de période'); }
  onCloturerCaisse(): void { console.log('Clôture de la caisse — shift #248'); }
  onNouvelEncaissement(): void { console.log('Nouvel encaissement'); }
  onExporterCsv(): void { console.log('Export CSV des transactions'); }
  onReconciliation(): void { console.log('Réconciliation automatique'); }
  onRapportXZ(): void { console.log('Génération du rapport X/Z'); }

  onTelechargerRecu(t: Transaction): void {
    if (!t.recuDisponible) return;
    console.log('Téléchargement du reçu', t.reference);
  }
}
