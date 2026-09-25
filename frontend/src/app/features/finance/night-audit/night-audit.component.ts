import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface LigneNoShow {
  client: string;
  reference: string;
  chambre: string;
  garantie: string;
  actionClass: string;
  actionLibelle: string;
}

interface RapportJournalier {
  iconeClass: string;
  icon: string;
  titre: string;
  description: string;
  actionIcon: string;
}

@Component({
  selector: 'app-night-audit',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="contents">
      <!-- En-tête de page & Actions principales -->
      <section class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 pb-2 border-b border-outline-variant/60">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="font-headline-lg text-headline-lg font-bold text-on-surface">Night Audit &amp; Clôture de Journée Hôtelière</h1>
            <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-emerald-50 text-primary border border-primary/20 font-label-sm text-label-sm font-semibold">
              <span class="w-2 h-2 rounded-full bg-primary animate-ping"></span>
              Prêt pour exécution (00h30)
            </span>
          </div>
          <p class="font-body-md text-body-md text-secondary mt-1">
            Arrêté d'exploitation journalier, imputation des nuitées, contrôle des caisses et bascule comptable officielle.
          </p>
        </div>
        <div class="flex items-center gap-3 flex-shrink-0">
          <button (click)="onPreRapport()" class="inline-flex items-center gap-2 px-4 py-2.5 rounded-lg bg-surface-container-lowest border border-outline-variant text-on-surface font-label-md text-label-md hover:bg-surface-container-low transition-all shadow-xs active:scale-[0.98]">
            <span class="material-symbols-outlined text-[18px] text-secondary">visibility</span>
            <span>Générer pré-rapport d'audit</span>
          </button>
          <button (click)="onLancerCloture()" class="inline-flex items-center gap-2 px-5 py-2.5 rounded-lg bg-primary text-on-primary font-label-md text-label-md font-semibold hover:bg-tertiary shadow-sm transition-all active:scale-[0.98]">
            <span class="material-symbols-outlined text-[20px]" style="font-variation-settings: 'FILL' 1;">play_arrow</span>
            <span>Lancer la Clôture Automatisée</span>
          </button>
        </div>
      </section>

      <!-- 4 KPIS D'AUDIT HÔTELIER -->
      <section class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <!-- KPI 1: Recettes du jour -->
        <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs flex flex-col justify-between hover:shadow-sm transition-shadow">
          <div class="flex items-center justify-between">
            <span class="font-label-sm text-label-sm text-secondary uppercase font-semibold tracking-wider">Recettes réconciliées</span>
            <div class="p-2 rounded-lg bg-primary-fixed/20 text-primary">
              <span class="material-symbols-outlined text-[20px]">account_balance_wallet</span>
            </div>
          </div>
          <div class="mt-3">
            <span class="font-display-md text-display-md font-bold text-on-surface tracking-tight">24 850 000</span>
            <span class="font-label-md text-label-md text-secondary font-medium ml-1">FCFA</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between">
            <span class="inline-flex items-center gap-1 font-caption text-caption text-primary font-semibold">
              <span class="material-symbols-outlined text-[14px]">check_circle</span>
              100% rapproché
            </span>
            <span class="font-caption text-caption text-secondary">Caisses, TPE &amp; Wave/Orange</span>
          </div>
        </div>

        <!-- KPI 2: Chambres occupées / RMC -->
        <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs flex flex-col justify-between hover:shadow-sm transition-shadow">
          <div class="flex items-center justify-between">
            <span class="font-label-sm text-label-sm text-secondary uppercase font-semibold tracking-wider">Occupation &amp; RevPAR</span>
            <div class="p-2 rounded-lg bg-secondary-container/40 text-on-secondary-container">
              <span class="material-symbols-outlined text-[20px]">bed</span>
            </div>
          </div>
          <div class="mt-3 flex items-baseline gap-2">
            <span class="font-display-md text-display-md font-bold text-on-surface tracking-tight">42 / 50</span>
            <span class="font-label-sm text-label-sm font-semibold text-primary px-2 py-0.5 rounded-full bg-emerald-50">84.0%</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between">
            <span class="font-caption text-caption text-secondary">RevPAR calculé</span>
            <span class="font-caption text-caption font-bold text-on-surface">78 500 FCFA</span>
          </div>
        </div>

        <!-- KPI 3: Départs & Arrivées résiduels -->
        <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs flex flex-col justify-between hover:shadow-sm transition-shadow">
          <div class="flex items-center justify-between">
            <span class="font-label-sm text-label-sm text-secondary uppercase font-semibold tracking-wider">Mouvements Résiduels</span>
            <div class="p-2 rounded-lg bg-amber-50 text-amber-700">
              <span class="material-symbols-outlined text-[20px]">sync_problem</span>
            </div>
          </div>
          <div class="mt-3">
            <div class="flex items-baseline gap-2">
              <span class="font-display-md text-display-md font-bold text-error">2</span>
              <span class="font-body-md text-body-md font-semibold text-error">No-Shows</span>
            </div>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between">
            <span class="inline-flex items-center gap-1 font-caption text-caption text-primary font-semibold">
              <span class="material-symbols-outlined text-[14px]">done_all</span>
              0 départ en attente
            </span>
            <span (click)="onTraiterMouvements()" class="font-caption text-caption text-amber-700 font-semibold underline cursor-pointer">À traiter</span>
          </div>
        </div>

        <!-- KPI 4: Taxes & TVA collectées -->
        <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs flex flex-col justify-between hover:shadow-sm transition-shadow">
          <div class="flex items-center justify-between">
            <span class="font-label-sm text-label-sm text-secondary uppercase font-semibold tracking-wider">Taxes de séjour &amp; TVA</span>
            <div class="p-2 rounded-lg bg-surface-container-high text-on-surface">
              <span class="material-symbols-outlined text-[20px]">receipt_long</span>
            </div>
          </div>
          <div class="mt-3">
            <span class="font-display-md text-display-md font-bold text-on-surface tracking-tight">1 450 000</span>
            <span class="font-label-md text-label-md text-secondary font-medium ml-1">FCFA</span>
          </div>
          <div class="mt-3 pt-3 border-t border-outline-variant/40 flex items-center justify-between">
            <span class="font-caption text-caption text-secondary">Cadre légal UEMOA</span>
            <span class="inline-flex items-center gap-1 font-caption text-caption text-primary font-bold">
              <span class="material-symbols-outlined text-[12px]">verified</span>
              DGI Conforme
            </span>
          </div>
        </div>
      </section>

      <!-- WORKFLOW EN 2 COLONNES -->
      <section class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        <!-- COLONNE GAUCHE : Checklist séquentielle d'audit -->
        <div class="lg:col-span-7 space-y-4">
          <div class="p-6 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs">
            <div class="flex items-center justify-between pb-4 border-b border-outline-variant">
              <div>
                <h2 class="font-headline-sm text-headline-sm font-bold text-on-surface">Checklist séquentielle de clôture nocturne</h2>
                <p class="font-body-sm text-body-sm text-secondary">Standards opérationnels certifiés USALI &amp; OHADA</p>
              </div>
              <div class="flex items-center gap-2">
                <span class="font-label-sm text-label-sm text-secondary">Progression :</span>
                <span class="font-label-md text-label-md font-bold text-primary">3 / 5 validées</span>
              </div>
            </div>

            <!-- Pipeline Visual Indicator -->
            <div class="w-full bg-surface-container-high h-2 rounded-full my-5 overflow-hidden">
              <div class="bg-primary h-full rounded-full transition-all duration-500" style="width: 60%;"></div>
            </div>

            <!-- Liste des Étapes Séquentielles -->
            <div class="space-y-4">
              <!-- Étape 1 : Caisses POS & Rapprochement (Validé) -->
              <div class="p-4 rounded-lg border border-primary/30 bg-emerald-50/40 transition-all">
                <div class="flex items-start justify-between">
                  <div class="flex items-start gap-3">
                    <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center flex-shrink-0 mt-0.5">
                      <span class="material-symbols-outlined text-[18px]">check</span>
                    </div>
                    <div>
                      <div class="flex items-center gap-2">
                        <h3 class="font-headline-sm text-headline-sm font-semibold text-on-surface">Étape 1 : Rapprochement des points de vente (POS) &amp; Réception</h3>
                        <span class="px-2 py-0.5 rounded text-label-sm font-label-sm font-bold bg-primary text-on-primary">Validé</span>
                      </div>
                      <p class="font-body-sm text-body-sm text-secondary mt-1">
                        Clôture des caisses Restaurant L'Ivoire, Lounge Bar Plateau, Room Service et terminal Réception principale.
                      </p>
                      <!-- Détails déroulés du rapprochement -->
                      <div class="grid grid-cols-3 gap-3 mt-3 pt-3 border-t border-outline-variant/30 text-body-sm">
                        <div class="p-2 bg-surface-container-lowest rounded border border-outline-variant/60">
                          <span class="font-caption text-caption text-secondary block">POS Restaurant</span>
                          <span class="font-label-sm text-label-sm font-bold text-on-surface">8 420 000 FCFA</span>
                          <span class="text-caption text-primary block mt-0.5">✓ Zéro écart</span>
                        </div>
                        <div class="p-2 bg-surface-container-lowest rounded border border-outline-variant/60">
                          <span class="font-caption text-caption text-secondary block">Terminal TPE / Carte</span>
                          <span class="font-label-sm text-label-sm font-bold text-on-surface">12 180 000 FCFA</span>
                          <span class="text-caption text-primary block mt-0.5">✓ Télécollecte OK</span>
                        </div>
                        <div class="p-2 bg-surface-container-lowest rounded border border-outline-variant/60">
                          <span class="font-caption text-caption text-secondary block">Mobile Money</span>
                          <span class="font-label-sm text-label-sm font-bold text-on-surface">4 250 000 FCFA</span>
                          <span class="text-caption text-primary block mt-0.5">✓ Wave &amp; Orange OK</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Étape 2 : No-Shows & Annulations Tardives (Action Requise) -->
              <div class="p-4 rounded-lg border-2 border-amber-400 bg-amber-50/30 transition-all shadow-xs">
                <div class="flex items-start justify-between">
                  <div class="flex items-start gap-3">
                    <div class="w-8 h-8 rounded-full bg-amber-500 text-on-primary flex items-center justify-center flex-shrink-0 mt-0.5 font-bold font-label-md">
                      2
                    </div>
                    <div class="flex-1">
                      <div class="flex items-center gap-2">
                        <h3 class="font-headline-sm text-headline-sm font-semibold text-on-surface">Étape 2 : Traitement des Non-Présentations (No-Shows)</h3>
                        <span class="px-2 py-0.5 rounded text-label-sm font-label-sm font-bold bg-amber-100 text-amber-800 border border-amber-300">2 Dossiers Détectés</span>
                      </div>
                      <p class="font-body-sm text-body-sm text-secondary mt-1">
                        Clients non enregistrés à 00h00 avec garantie carte bancaire. Décision d'imputation des frais de première nuitée requise.
                      </p>
                      <!-- Mini tableau No-Show intégré -->
                      <div class="mt-3 bg-surface-container-lowest rounded-lg border border-outline-variant overflow-hidden">
                        <table class="w-full text-left font-body-sm text-body-sm">
                          <thead class="bg-surface-container-low text-secondary font-label-sm text-label-sm border-b border-outline-variant">
                            <tr>
                              <th class="py-2 px-3">Client &amp; Réservation</th>
                              <th class="py-2 px-3">Chambre</th>
                              <th class="py-2 px-3">Garantie</th>
                              <th class="py-2 px-3 text-right">Action d'audit</th>
                            </tr>
                          </thead>
                          <tbody class="divide-y divide-outline-variant/50">
                            <tr *ngFor="let n of noShows">
                              <td class="py-2.5 px-3">
                                <span class="font-label-sm text-label-sm font-bold block text-on-surface">{{ n.client }}</span>
                                <span class="font-caption text-caption text-secondary">{{ n.reference }}</span>
                              </td>
                              <td class="py-2.5 px-3 font-medium">{{ n.chambre }}</td>
                              <td class="py-2.5 px-3 text-secondary">{{ n.garantie }}</td>
                              <td class="py-2.5 px-3 text-right">
                                <button (click)="onActionNoShow(n)" [class]="n.actionClass">
                                  {{ n.actionLibelle }}
                                </button>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Étape 3 : Imputation automatique des tarifs de chambres -->
              <div class="p-4 rounded-lg border border-outline-variant bg-surface-container-lowest hover:border-primary/50 transition-all">
                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 rounded-full bg-surface-container-high text-secondary flex items-center justify-center flex-shrink-0 mt-0.5 font-bold font-label-md">
                    3
                  </div>
                  <div class="flex-1">
                    <div class="flex items-center justify-between">
                      <h3 class="font-headline-sm text-headline-sm font-semibold text-on-surface">Étape 3 : Imputation automatique des tarifs chambres &amp; taxes</h3>
                      <span class="px-2 py-0.5 rounded text-label-sm font-label-sm text-secondary bg-surface-container">Prêt</span>
                    </div>
                    <p class="font-body-sm text-body-sm text-secondary mt-1">
                      Facturation programmée de 42 chambres résidentes : hébergement, forfaits petits-déjeuners buffet et taxes de séjour UEMOA (1 000 FCFA/nuit/personne).
                    </p>
                    <div class="mt-2 text-caption text-secondary flex items-center gap-4">
                      <span>• Montant total estimé : <strong>9 650 000 FCFA</strong></span>
                      <span>• 42 folios clients ciblés</span>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Étape 4 : Gel du Grand-Livre & Liasses Comptables -->
              <div class="p-4 rounded-lg border border-outline-variant bg-surface-container-lowest hover:border-primary/50 transition-all">
                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 rounded-full bg-surface-container-high text-secondary flex items-center justify-center flex-shrink-0 mt-0.5 font-bold font-label-md">
                    4
                  </div>
                  <div class="flex-1">
                    <div class="flex items-center justify-between">
                      <h3 class="font-headline-sm text-headline-sm font-semibold text-on-surface">Étape 4 : Gel du Grand-Livre &amp; Génération des liasses financières</h3>
                      <span class="px-2 py-0.5 rounded text-label-sm font-label-sm text-secondary bg-surface-container">En attente</span>
                    </div>
                    <p class="font-body-sm text-body-sm text-secondary mt-1">
                      Archivage immuable des journaux auxiliaires, balance âgée débiteurs divers, et verrouillage des écritures comptables du 24/10/2024.
                    </p>
                  </div>
                </div>
              </div>

              <!-- Étape 5 : Bascule automatique de la date système -->
              <div class="p-4 rounded-lg border border-outline-variant bg-surface-container-lowest opacity-80">
                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 rounded-full bg-surface-container-high text-secondary flex items-center justify-center flex-shrink-0 mt-0.5 font-bold font-label-md">
                    5
                  </div>
                  <div class="flex-1">
                    <div class="flex items-center justify-between">
                      <h3 class="font-headline-sm text-headline-sm font-semibold text-on-surface">Étape 5 : Bascule de la date d'exploitation du PMS (Roll-Over)</h3>
                      <span class="px-2 py-0.5 rounded text-label-sm font-label-sm text-secondary bg-surface-container">Étape finale</span>
                    </div>
                    <p class="font-body-sm text-body-sm text-secondary mt-1">
                      Avancement du calendrier système du <strong>24 Octobre 2024</strong> vers le <strong>25 Octobre 2024</strong>. Réouverture des caisses matinales.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Panneau d'Information d'Assurance d'Audit -->
          <div class="p-4 rounded-xl bg-surface-container-low border border-outline-variant flex items-center justify-between text-body-sm">
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary text-[24px]">verified_user</span>
              <div>
                <span class="font-label-sm text-label-sm font-bold text-on-surface block">Sauvegarde miroir d'intégrité</span>
                <span class="text-secondary font-caption text-caption">Une copie chiffrée de la base de données PostgreSQL sera générée avant la bascule.</span>
              </div>
            </div>
            <span class="font-caption text-caption font-bold text-primary bg-primary-fixed/30 px-2.5 py-1 rounded">Serveur Abidjan-Cloud-01</span>
          </div>
        </div>

        <!-- COLONNE DROITE : Anomalies de contrôle & Rapports générés -->
        <div class="lg:col-span-5 space-y-6">
          <!-- Bloc 1: Alertes & Anomalies Critiques -->
          <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs">
            <div class="flex items-center justify-between pb-3 border-b border-outline-variant">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-amber-700 text-[20px]">warning</span>
                <h2 class="font-headline-sm text-headline-sm font-bold text-on-surface">Anomalies &amp; Contrôles d'Audit</h2>
              </div>
              <span class="font-label-sm text-label-sm px-2 py-0.5 bg-amber-100 text-amber-800 rounded-full font-bold">2 alertes</span>
            </div>
            <div class="divide-y divide-outline-variant/60">
              <!-- Alerte 1: Facture Chambre 104 -->
              <div class="py-3.5 space-y-2">
                <div class="flex items-start justify-between">
                  <div class="flex items-center gap-2">
                    <span class="w-2 h-2 rounded-full bg-error"></span>
                    <span class="font-label-sm text-label-sm font-bold text-on-surface">Chambre 104 : Solde Folio Résiduel</span>
                  </div>
                  <span class="font-label-sm text-label-sm text-error font-bold">45 000 FCFA</span>
                </div>
                <p class="font-body-sm text-body-sm text-secondary">
                  Départ tardif effectué sans encaissement du supplément check-out (Guest M. Touré). Folio toujours ouvert en réception.
                </p>
                <div class="flex items-center gap-2 pt-1">
                  <button (click)="onTransfererCityLedger()" class="px-2.5 py-1 bg-surface-container-high hover:bg-surface-container text-on-surface rounded text-caption font-semibold transition-colors">
                    Transférer sur Grand-Livre Client (City Ledger)
                  </button>
                  <button (click)="onVoirFolio()" class="px-2.5 py-1 bg-surface-container-lowest border border-outline-variant text-secondary hover:text-on-surface rounded text-caption font-medium transition-colors">
                    Voir Folio
                  </button>
                </div>
              </div>

              <!-- Alerte 2: Écart de caisse Lounge Bar -->
              <div class="py-3.5 space-y-2">
                <div class="flex items-start justify-between">
                  <div class="flex items-center gap-2">
                    <span class="w-2 h-2 rounded-full bg-amber-500"></span>
                    <span class="font-label-sm text-label-sm font-bold text-on-surface">Écart mineur en caisse Bar Terrasse</span>
                  </div>
                  <span class="font-label-sm text-label-sm text-amber-700 font-bold">- 5 000 FCFA</span>
                </div>
                <p class="font-body-sm text-body-sm text-secondary">
                  Différence constatée entre le rapport X du tiroir caisse et le comptage physique des espèces du shift du soir.
                </p>
                <div class="flex items-center justify-between pt-1">
                  <span class="font-caption text-caption text-secondary">Régularisation : Barman Chef Kouamé</span>
                  <button (click)="onImputerEcart()" class="px-2.5 py-1 bg-emerald-50 text-primary border border-primary/20 rounded text-caption font-semibold hover:bg-emerald-100 transition-colors">
                    Imputer en compte d'écart
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Bloc 2: Rapports réglementaires générés automatiquement -->
          <div class="p-5 bg-surface-container-lowest border border-outline-variant rounded-xl shadow-xs">
            <div class="flex items-center justify-between pb-3 border-b border-outline-variant">
              <div>
                <h2 class="font-headline-sm text-headline-sm font-bold text-on-surface">Rapports de Fin de Journée</h2>
                <p class="font-caption text-caption text-secondary">Exportation automatique après bascule</p>
              </div>
              <span class="material-symbols-outlined text-secondary text-[20px]">print</span>
            </div>
            <div class="mt-4 space-y-2.5">
              <div *ngFor="let r of rapports" (click)="onOuvrirRapport(r)" class="p-3 rounded-lg bg-surface-container-low border border-outline-variant/60 flex items-center justify-between hover:bg-surface-container transition-colors cursor-pointer">
                <div class="flex items-center gap-3">
                  <div [class]="r.iconeClass">
                    <span class="material-symbols-outlined text-[18px]">{{ r.icon }}</span>
                  </div>
                  <div>
                    <span class="font-label-sm text-label-sm font-bold text-on-surface block">{{ r.titre }}</span>
                    <span class="font-caption text-caption text-secondary">{{ r.description }}</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-secondary text-[18px]">{{ r.actionIcon }}</span>
              </div>
            </div>
            <!-- Bouton d'export groupé -->
            <button (click)="onTelechargerPack()" class="w-full mt-4 py-2 px-3 border border-outline-variant rounded-lg text-secondary hover:text-on-surface hover:bg-surface-container text-label-sm font-label-sm font-semibold transition-colors flex items-center justify-center gap-2">
              <span class="material-symbols-outlined text-[16px]">folder_zip</span>
              <span>Télécharger le Pack PDF &amp; Excel de Clôture</span>
            </button>
          </div>

          <!-- Widget Contexte Opérationnel de Clôture -->
          <div class="p-4 rounded-xl bg-surface-container-lowest border border-outline-variant flex items-center justify-between">
            <div class="flex items-center gap-3">
              <div class="p-2.5 rounded-lg bg-surface-container text-primary">
                <span class="material-symbols-outlined text-[20px]">nest_clock_farsight_analog</span>
              </div>
              <div>
                <span class="font-label-sm text-label-sm font-bold text-on-surface block">Heure de clôture recommandée</span>
                <span class="font-caption text-caption text-secondary">Dernier Room Service clôturé à 23h45</span>
              </div>
            </div>
            <span class="font-headline-sm text-headline-sm font-bold text-primary">00:30</span>
          </div>
        </div>
      </section>
    </div>
  `,
})
export class NightAuditComponent {
  noShows: LigneNoShow[] = [
    {
      client: 'Mme Aminata Koné',
      reference: '#RES-98421 • Booking.com',
      chambre: 'Suite 302 (Junior)',
      garantie: 'Visa •••• 4120',
      actionClass:
        'px-2 py-1 bg-primary text-on-primary text-caption font-semibold rounded hover:bg-tertiary transition-colors',
      actionLibelle: 'Imputer 1ère Nuitée',
    },
    {
      client: 'Dr. Marc Philippe',
      reference: '#RES-98435 • Direct Web',
      chambre: 'Chambre 415 (Deluxe)',
      garantie: 'Mastercard •••• 8829',
      actionClass:
        'px-2 py-1 bg-surface-container-high text-on-surface text-caption font-semibold rounded border border-outline-variant hover:bg-surface-container transition-colors',
      actionLibelle: 'Marquer No-Show',
    },
  ];

  rapports: RapportJournalier[] = [
    {
      iconeClass: 'w-8 h-8 rounded bg-primary-fixed/40 text-primary flex items-center justify-center',
      icon: 'table_chart',
      titre: 'Main-Courante Générale (D-Report)',
      description: "Chiffre d'affaires consolidé par département",
      actionIcon: 'download',
    },
    {
      iconeClass: 'w-8 h-8 rounded bg-secondary-container/60 text-on-secondary-container flex items-center justify-center',
      icon: 'account_balance',
      titre: 'Balance Âgée Débiteurs (City Ledger)',
      description: 'Créances entreprises & voyagistes en compte',
      actionIcon: 'download',
    },
    {
      iconeClass: 'w-8 h-8 rounded bg-surface-container-highest text-on-surface flex items-center justify-center',
      icon: 'local_police',
      titre: 'Fiches de Police & Registre DGSN',
      description: 'Télétransmission sécurisée des hébergés étrangers',
      actionIcon: 'cloud_upload',
    },
    {
      iconeClass: 'w-8 h-8 rounded bg-surface-container-highest text-on-surface flex items-center justify-center',
      icon: 'cleaning_services',
      titre: 'Ordre de Service Matinal Housekeeping',
      description: "Attribution des étages pour l'équipe du matin (07h00)",
      actionIcon: 'download',
    },
  ];

  onPreRapport(): void {
    console.log('[NightAudit] Génération du pré-rapport d\'audit');
  }

  onLancerCloture(): void {
    console.log('[NightAudit] Lancement de la clôture automatisée');
  }

  onTraiterMouvements(): void {
    console.log('[NightAudit] Traitement des mouvements résiduels');
  }

  onActionNoShow(ligne: LigneNoShow): void {
    console.log('[NightAudit] No-Show', ligne.client, '→', ligne.actionLibelle);
  }

  onTransfererCityLedger(): void {
    console.log('[NightAudit] Transfert du folio sur le City Ledger');
  }

  onVoirFolio(): void {
    console.log('[NightAudit] Ouverture du folio Chambre 104');
  }

  onImputerEcart(): void {
    console.log('[NightAudit] Imputation en compte d\'écart');
  }

  onOuvrirRapport(rapport: RapportJournalier): void {
    console.log('[NightAudit] Rapport', rapport.titre);
  }

  onTelechargerPack(): void {
    console.log('[NightAudit] Téléchargement du pack de clôture');
  }
}
