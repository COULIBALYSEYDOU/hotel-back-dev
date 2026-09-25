import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Abonnement & Modularité du Système — portage fidèle de la maquette Stitch
 * « abonnement_activation_des_modules_saas_toile_os ».
 */

type Onglet = 'TOUS' | 'ACTIFS' | 'RECOMMANDES' | 'ESSAI';

interface SaasModule {
  id: string;
  icon: string;
  titre: string;
  description: string;

  /** Module socle : toujours requis, interrupteur verrouillé en position active. */
  verrouille: boolean;
  /** Compris dans la formule Pro (pas de surcoût). */
  inclus: boolean;
  /** Supplément mensuel en FCFA (0 si inclus). */
  prix: number;
  /** Éligible à la période d'essai de 14 jours. */
  essai: boolean;
  /** Mis en avant dans l'onglet « Modules Recommandés ». */
  recommande: boolean;
  /** Décor d'angle (carte IA de la maquette). */
  decorAngle: boolean;

  actif: boolean;

  /** Pied de carte lorsque le module est actif. */
  piedIcon?: string;
  piedTexte?: string;
  piedLien?: boolean;
  piedDroite?: string;

  /** Bouton d'appel à l'action lorsque le module est inactif. */
  ctaIcon: string;
  ctaLabel: string;
}

const BASE_FORFAIT = 120000;

@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [CommonModule],
  template: `
    <!-- Compense le padding du layout pour retrouver la gouttière de la maquette -->
    <div class="-m-6 lg:-m-8 pb-28">
      <div class="max-w-7xl mx-auto px-8 py-8 space-y-8">

        <!-- 1. FIL D'ARIANE & EN-TÊTE -->
        <section class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
          <div>
            <nav aria-label="Fil d'Ariane" class="flex items-center gap-2 text-caption font-caption text-secondary mb-1.5">
              <span>Administration</span>
              <span class="material-symbols-outlined text-xs">chevron_right</span>
              <span>Hôtel Étoile du Sud</span>
              <span class="material-symbols-outlined text-xs">chevron_right</span>
              <span class="text-primary font-semibold">Forfait &amp; Modules SaaS</span>
            </nav>

            <h1 class="text-display-md font-display-md text-on-surface tracking-tight">
              Abonnement &amp; Modularité du Système
            </h1>
            <p class="text-body-md font-body-md text-secondary mt-1 max-w-3xl">
              Activez uniquement les modules nécessaires à votre établissement. L'interface et la barre de
              navigation s'adaptent instantanément pour garder un outil simple et épuré.
            </p>
          </div>

          <div class="flex items-center gap-3 self-start lg:self-center shrink-0">
            <button type="button"
                    class="flex items-center gap-2 px-4 py-2.5 bg-surface-container-lowest hover:bg-surface-container-low text-on-surface border border-outline-variant rounded-lg text-label-md font-label-md shadow-sm transition-all duration-150">
              <span class="material-symbols-outlined text-lg text-secondary">receipt_long</span>
              <span>Historique de Facturation</span>
            </button>
            <button type="button"
                    class="flex items-center gap-2 px-4 py-2.5 bg-primary hover:bg-primary-container text-on-primary rounded-lg text-label-md font-label-md shadow-sm transition-all duration-150 active:scale-[0.98]">
              <span class="material-symbols-outlined text-lg">upgrade</span>
              <span>+ Changer de Forfait</span>
            </button>
          </div>
        </section>

        <!-- 2. SYNTHÈSE DE L'ABONNEMENT (bento) -->
        <section class="bg-surface-container-lowest rounded-xl border border-outline-variant p-6 shadow-sm">
          <div class="grid grid-cols-1 lg:grid-cols-12 gap-6">

            <!-- Colonne A : formule & coût -->
            <div class="lg:col-span-4 border-b lg:border-b-0 lg:border-r border-outline-variant/60 pb-6 lg:pb-0 lg:pr-6 flex flex-col justify-between">
              <div>
                <div class="flex items-center justify-between gap-2 mb-2">
                  <span class="text-caption font-caption uppercase tracking-wider text-secondary font-semibold">Formule Souscrite</span>
                  <span class="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded-full text-label-sm font-label-sm bg-emerald-50 text-primary border border-primary/25">
                    <span class="w-1.5 h-1.5 rounded-full bg-primary animate-pulse"></span>
                    Actif • Renouvellement le 15 Nov 2026
                  </span>
                </div>

                <h2 class="text-headline-lg font-headline-lg text-on-surface">Plan Professionnel (Pro)</h2>

                <div class="mt-4 pt-3 border-t border-outline-variant/40">
                  <div class="text-caption font-caption text-secondary">Montant mensuel consolidé :</div>
                  <div class="flex items-baseline gap-2 mt-1">
                    <span class="text-display-md font-display-md text-primary tracking-tight">
                      {{ fmt(montantTotal()) }} FCFA
                    </span>
                    <span class="text-label-md font-label-md text-secondary">/ mois</span>
                  </div>
                  <p class="text-caption font-caption text-secondary mt-1">
                    Base {{ fmt(base) }} FCFA
                    + {{ fmt(supplements()) }} FCFA modules à la carte
                    ({{ nbPayantsActifs() }} module{{ nbPayantsActifs() > 1 ? 's' : '' }} payant{{ nbPayantsActifs() > 1 ? 's' : '' }} activé{{ nbPayantsActifs() > 1 ? 's' : '' }}).
                  </p>
                </div>
              </div>

              <div class="mt-5 p-3 rounded-lg bg-surface-container-low border border-outline-variant/40 flex items-center gap-3">
                <div class="w-8 h-8 rounded bg-surface-container-lowest border border-outline-variant flex items-center justify-center text-primary shrink-0">
                  <span class="material-symbols-outlined text-lg">account_balance_wallet</span>
                </div>
                <div class="flex flex-col text-xs overflow-hidden">
                  <span class="font-semibold text-on-surface truncate">Prélèvement automatique Wave Business</span>
                  <span class="text-secondary truncate">Visa terminant par 8820 • Facture reçue par email</span>
                </div>
              </div>
            </div>

            <!-- Colonne B : quotas -->
            <div class="lg:col-span-8 flex flex-col justify-between">
              <div>
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-headline-sm font-headline-sm text-on-surface">Utilisation des quotas alloués</h3>
                  <span class="text-label-sm font-label-sm text-secondary">Actualisation en temps réel</span>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div *ngFor="let q of quotas"
                       class="p-4 rounded-lg bg-surface-container-low border border-outline-variant/50">
                    <div class="flex items-center justify-between text-body-sm font-body-sm text-secondary mb-1">
                      <span class="flex items-center gap-1.5 font-medium text-on-surface">
                        <span class="material-symbols-outlined text-base text-primary">{{ q.icon }}</span>
                        {{ q.libelle }}
                      </span>
                      <span class="font-bold text-on-surface">{{ q.pct }}%</span>
                    </div>
                    <div class="text-headline-sm font-headline-sm text-on-surface mt-2 mb-2 font-bold">
                      {{ q.valeur }}
                      <span class="text-secondary font-normal text-body-sm">/ {{ q.total }}</span>
                    </div>
                    <div class="w-full h-2 rounded-full bg-surface-container-highest overflow-hidden">
                      <div class="h-full bg-primary rounded-full" [style.width.%]="q.pct"></div>
                    </div>
                    <span class="text-caption font-caption text-secondary mt-1.5 block">{{ q.note }}</span>
                  </div>
                </div>
              </div>

              <div class="flex items-center justify-between pt-4 mt-2 border-t border-outline-variant/40 text-body-sm font-body-sm">
                <span class="text-secondary flex items-center gap-1.5">
                  <span class="material-symbols-outlined text-base text-primary">info</span>
                  Besoin de connecter plus de 100 chambres ? Basculez sur le plan Enterprise sans interruption.
                </span>
                <a href="#" class="text-primary font-semibold hover:underline flex items-center gap-1 shrink-0">
                  Contacter notre spécialiste Afrique de l'Ouest
                  <span class="material-symbols-outlined text-sm">arrow_forward</span>
                </a>
              </div>
            </div>
          </div>
        </section>

        <!-- 3. ARCHITECTURE MODULAIRE -->
        <section class="space-y-6">

          <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-outline-variant pb-4">
            <div class="flex items-center gap-2 overflow-x-auto pb-1 md:pb-0" role="tablist">
              <button *ngFor="let t of onglets" type="button" (click)="onglet.set(t.key)"
                      class="px-3.5 py-1.5 rounded-lg font-label-md text-label-md transition-colors whitespace-nowrap"
                      [ngClass]="onglet() === t.key
                        ? 'bg-surface-container-lowest text-primary border border-primary/40 shadow-sm'
                        : 'text-secondary hover:text-on-surface hover:bg-surface-container-low'">
                {{ t.label }}{{ t.count !== null ? ' (' + t.count() + ')' : '' }}
              </button>
            </div>

            <div class="flex items-center gap-2 text-label-sm font-label-sm text-secondary shrink-0">
              <span class="inline-block w-2.5 h-2.5 rounded-full bg-primary"></span>
              <span>{{ nbActifs() }} modules actifs au sein de votre établissement</span>
            </div>
          </div>

          <!-- Grille des modules -->
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div *ngFor="let m of visibles()"
                 class="bg-surface-container-lowest rounded-xl p-5 shadow-sm flex flex-col justify-between transition-all duration-200 hover:shadow-md"
                 [ngClass]="carteClass(m)">

              <!-- Décor d'angle -->
              <div *ngIf="m.decorAngle && !m.actif"
                   class="absolute top-0 right-0 w-20 h-20 bg-primary/5 rounded-bl-full pointer-events-none"></div>

              <div>
                <div class="flex items-start justify-between gap-3 mb-3">
                  <div class="w-10 h-10 rounded-lg flex items-center justify-center transition-colors"
                       [ngClass]="iconeClass(m)">
                    <span class="material-symbols-outlined text-2xl"
                          [style.fontVariationSettings]="m.actif && !m.inclus ? fillOn : null">{{ m.icon }}</span>
                  </div>

                  <div class="flex items-center gap-2">
                    <span class="px-2 py-0.5 rounded text-label-sm font-label-sm" [ngClass]="badgeClass(m)">
                      {{ badgeTexte(m) }}
                    </span>

                    <!-- Interrupteur verrouillé (module socle) -->
                    <div *ngIf="m.verrouille"
                         class="w-11 h-6 bg-primary/40 rounded-full flex items-center p-1 cursor-not-allowed"
                         title="Ce module fondamental est toujours requis">
                      <div class="w-4 h-4 bg-surface-container-lowest rounded-full shadow transform translate-x-5"></div>
                    </div>

                    <!-- Interrupteur interactif -->
                    <button *ngIf="!m.verrouille" type="button" (click)="basculer(m)"
                            [attr.aria-label]="(m.actif ? 'Désactiver' : 'Activer') + ' le module ' + m.titre"
                            class="w-11 h-6 rounded-full flex items-center p-1 transition-colors duration-200 cursor-pointer"
                            [ngClass]="m.actif ? 'bg-primary' : 'bg-surface-container-highest'">
                      <div class="w-4 h-4 bg-surface-container-lowest rounded-full shadow transform transition-transform duration-200"
                           [ngClass]="m.actif ? 'translate-x-5' : 'translate-x-0'"></div>
                    </button>
                  </div>
                </div>

                <h4 class="text-headline-sm font-headline-sm text-on-surface">{{ m.titre }}</h4>
                <p class="text-body-sm font-body-sm text-secondary mt-2">{{ m.description }}</p>
              </div>

              <!-- Pied de carte -->
              <div class="mt-6 pt-3 border-t border-outline-variant/40 flex items-center justify-between text-caption font-caption text-secondary">

                <ng-container *ngIf="m.actif; else piedInactif">
                  <a *ngIf="m.piedLien" href="#"
                     class="text-label-sm font-label-sm text-primary font-semibold hover:underline flex items-center gap-1">
                    <span>{{ m.piedTexte }}</span>
                    <span class="material-symbols-outlined text-sm">arrow_forward</span>
                  </a>
                  <span *ngIf="!m.piedLien" class="flex items-center gap-1"
                        [ngClass]="m.piedIcon ? 'font-semibold text-primary' : ''">
                    <span *ngIf="m.piedIcon" class="material-symbols-outlined text-sm">{{ m.piedIcon }}</span>
                    {{ m.piedTexte }}
                  </span>
                  <span>{{ m.piedDroite }}</span>
                </ng-container>

                <ng-template #piedInactif>
                  <button type="button" (click)="basculer(m)"
                          class="w-full py-2 px-3 bg-surface-container-low hover:bg-primary hover:text-on-primary text-primary rounded-lg text-label-sm font-label-sm transition-all duration-150 flex items-center justify-center gap-1.5 font-semibold">
                    <span class="material-symbols-outlined text-base">{{ m.ctaIcon }}</span>
                    <span>{{ m.ctaLabel }}</span>
                  </button>
                </ng-template>
              </div>
            </div>
          </div>
        </section>

        <!-- 4. CONFORMITÉ & SÉCURITÉ -->
        <section class="bg-surface-container-lowest border border-outline-variant rounded-xl p-6 shadow-sm">
          <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
            <div class="flex items-start gap-4">
              <div class="w-12 h-12 rounded-xl bg-primary/10 text-primary flex items-center justify-center shrink-0">
                <span class="material-symbols-outlined text-2xl">shield</span>
              </div>
              <div>
                <h3 class="text-headline-sm font-headline-sm text-on-surface">
                  Conformité RGPD &amp; Réglementation Hôtelière Côte d'Ivoire (Côte d'Ivoire Tourisme)
                </h3>
                <p class="text-body-sm font-body-sm text-secondary mt-1">
                  L'ensemble des données de vos clients, fiches de police obligatoires et transactions Wave CI /
                  cartes bancaires sont chiffrées selon la norme bancaire PCI-DSS et répliquées en local à Abidjan.
                </p>
              </div>
            </div>
            <div class="shrink-0 flex items-center gap-2">
              <span class="text-label-sm font-label-sm text-primary bg-emerald-50 px-3 py-1.5 rounded-lg border border-primary/20 flex items-center gap-1.5">
                <span class="material-symbols-outlined text-sm">verified_user</span>
                Certifié Sécurisé
              </span>
            </div>
          </div>
        </section>
      </div>
    </div>

    <!-- 5. BARRE D'ACTION PERSISTANTE -->
    <aside aria-label="Confirmation des modifications"
           class="fixed bottom-0 right-0 left-64 bg-surface-container-lowest/95 backdrop-blur-md border-t border-outline-variant px-8 py-3.5 shadow-lg z-30 flex flex-col sm:flex-row items-center justify-between gap-4">
      <div class="flex items-center gap-3">
        <div class="w-8 h-8 rounded-full bg-emerald-50 text-primary flex items-center justify-center shrink-0 border border-primary/20">
          <span class="material-symbols-outlined text-lg">auto_mode</span>
        </div>
        <p class="text-body-sm font-body-sm text-secondary">
          <strong class="text-on-surface font-semibold">Modifications prises en compte en direct :</strong>
          la navigation de vos collaborateurs est automatiquement mise à jour selon les modules cochés.
        </p>
      </div>
      <div class="flex items-center gap-3 w-full sm:w-auto justify-end">
        <button type="button" (click)="reinitialiser()"
                class="px-4 py-2 text-label-md font-label-md text-secondary hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors border border-outline-variant/60 whitespace-nowrap">
          Réinitialiser aux modules par défaut
        </button>
        <button type="button" (click)="enregistrer()"
                class="px-5 py-2 text-label-md font-label-md bg-primary hover:bg-primary-container text-on-primary rounded-lg shadow-sm transition-all duration-150 active:scale-[0.98] flex items-center gap-2 whitespace-nowrap">
          <span class="material-symbols-outlined text-base">save</span>
          <span>Enregistrer la configuration des modules</span>
        </button>
      </div>
    </aside>
  `,
})
export class ModulesComponent {

  readonly base = BASE_FORFAIT;
  /** Variante « remplie » des Material Symbols pour les modules payants actifs. */
  readonly fillOn = "'FILL' 1";

  /** Formatage monétaire français sans dépendre de registerLocaleData. */
  fmt(n: number): string {
    return n.toLocaleString('fr-FR').replace(/ | /g, ' ');
  }

  quotas = [
    { icon: 'bed',   libelle: 'Chambres gérées',   valeur: '50',     total: '100',   pct: 50, note: '50 suites disponibles sur votre palier' },
    { icon: 'badge', libelle: 'Utilisateurs actifs', valeur: '12',   total: '25',    pct: 48, note: '13 accès réception/gouvernantes libres' },
    { icon: 'cloud', libelle: 'Stockage & Docs',   valeur: '4.2 Go', total: '20 Go', pct: 21, note: "Copies de pièces d'identité & factures" },
  ];

  private readonly defauts: SaasModule[] = [
    {
      id: 'HOUSEKEEPING', icon: 'bed',
      titre: 'Gestion des Chambres & Housekeeping',
      description: 'Inventaire des 50 chambres, statuts de ménage en direct, assignation des gouvernantes et télémétrie IoT.',
      verrouille: true, inclus: true, prix: 0, essai: false, recommande: false, decorAngle: false, actif: true,
      piedIcon: 'lock', piedTexte: 'Module Socle Fondamental', piedDroite: 'Rack & Étages',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'RESERVATIONS', icon: 'calendar_month',
      titre: 'Réservations & Planning Rack',
      description: "Calendrier Gantt interactif, moteur de réservation et formulaires d'arrivée express.",
      verrouille: true, inclus: true, prix: 0, essai: false, recommande: false, decorAngle: false, actif: true,
      piedIcon: 'lock', piedTexte: 'Module Socle Fondamental', piedDroite: 'Front Desk',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'FINANCE', icon: 'payments',
      titre: 'Finance, Caisse & Paiements Mobiles',
      description: "Encaissements Wave CI, Orange Money, clôture de shift, réconciliation d'espèces et journal de caisse.",
      verrouille: false, inclus: true, prix: 0, essai: false, recommande: false, decorAngle: false, actif: true,
      piedIcon: 'check_circle', piedTexte: 'Actif • Passerelles CI configurées', piedDroite: 'Comptabilité',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'CRM', icon: 'group',
      titre: 'CRM Hôtelier & Programme Fidélité VIP',
      description: "Fiches clients 360°, protocoles conciergerie VIP, suivi du LTV et émission de tokens de fidélité.",
      verrouille: false, inclus: false, prix: 25000, essai: false, recommande: false, decorAngle: false, actif: true,
      piedLien: true, piedTexte: 'Configurer le protocole VIP', piedDroite: 'Visible en barre latérale',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'MAINTENANCE', icon: 'build',
      titre: 'Maintenance, SAV Technique & Capteurs IoT',
      description: 'Supervision des passerelles Carrier BACnet, alertes climatisation/serrures RFID et suivi des techniciens.',
      verrouille: false, inclus: false, prix: 20000, essai: false, recommande: false, decorAngle: false, actif: true,
      piedIcon: 'sensors', piedTexte: 'Passerelles BACnet en ligne', piedDroite: 'SAV & Chambres',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'RH', icon: 'badge',
      titre: 'Ressources Humaines (RH) & Planning Équipes',
      description: 'Gestion des 28 employés, contrats locaux UEMOA, plannings de rotation jour/nuit et congés.',
      verrouille: false, inclus: false, prix: 20000, essai: false, recommande: false, decorAngle: false, actif: true,
      piedTexte: '28 dossiers salariés actifs', piedDroite: 'Roster 24/7',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'IA_YIELD', icon: 'auto_graph',
      titre: 'Assistant IA & Yield Management Prédictif',
      description: "Optimisation dynamique des tarifs des suites selon la demande à Abidjan et détection proactive des anomalies de coûts.",
      verrouille: false, inclus: false, prix: 35000, essai: true, recommande: true, decorAngle: true, actif: false,
      piedIcon: 'bolt', piedTexte: 'Essai en cours • 14 jours', piedDroite: 'Revenue Management',
      ctaIcon: 'bolt', ctaLabel: "Activer l'essai gratuit",
    },
    {
      id: 'FNB', icon: 'restaurant',
      titre: 'Restauration, Bar & Room-Service Connecté',
      description: 'Prise de commande en chambre via QR Code, facturation directe sur la note de la chambre et gestion du stock cuisine.',
      verrouille: false, inclus: false, prix: 30000, essai: false, recommande: true, decorAngle: false, actif: false,
      piedIcon: 'check_circle', piedTexte: 'Actif • Carte & stocks synchronisés', piedDroite: 'F&B',
      ctaIcon: 'add', ctaLabel: 'Activer le module',
    },
    {
      id: 'OTA', icon: 'travel_explore',
      titre: 'Moteur de Réservation Web Public & Passerelle OTA',
      description: 'Synchronisation bidirectionnelle Booking.com, Airbnb, Expedia et widget de réservation sans commission sur votre site.',
      verrouille: false, inclus: false, prix: 40000, essai: false, recommande: true, decorAngle: false, actif: false,
      piedIcon: 'sync_alt', piedTexte: 'Actif • Canaux synchronisés', piedDroite: 'Distribution',
      ctaIcon: 'sync_alt', ctaLabel: 'Activer le module',
    },
  ];

  modules = signal<SaasModule[]>(this.defauts.map(m => ({ ...m })));
  onglet = signal<Onglet>('TOUS');

  nbActifs       = computed(() => this.modules().filter(m => m.actif).length);
  nbPayantsActifs = computed(() => this.modules().filter(m => m.actif && !m.inclus).length);
  nbRecommandes  = computed(() => this.modules().filter(m => m.recommande).length);
  nbEssai        = computed(() => this.modules().filter(m => m.essai).length);
  nbTotal        = computed(() => this.modules().length);

  supplements  = computed(() => this.modules().filter(m => m.actif && !m.inclus).reduce((s, m) => s + m.prix, 0));
  montantTotal = computed(() => BASE_FORFAIT + this.supplements());

  onglets: { key: Onglet; label: string; count: (() => number) | null }[] = [
    { key: 'TOUS',        label: 'Tous les modules',      count: () => this.nbTotal() },
    { key: 'ACTIFS',      label: 'Modules Activés',       count: () => this.nbActifs() },
    { key: 'RECOMMANDES', label: 'Modules Recommandés',   count: null },
    { key: 'ESSAI',       label: "Disponibles à l'essai", count: () => this.nbEssai() },
  ];

  visibles = computed(() => {
    const o = this.onglet();
    return this.modules().filter(m => {
      if (o === 'ACTIFS')      return m.actif;
      if (o === 'RECOMMANDES') return m.recommande;
      if (o === 'ESSAI')       return m.essai;
      return true;
    });
  });

  basculer(m: SaasModule) {
    if (m.verrouille) return;
    this.modules.update(list => list.map(x => x.id === m.id ? { ...x, actif: !x.actif } : x));
  }

  reinitialiser() {
    this.modules.set(this.defauts.map(m => ({ ...m })));
    this.onglet.set('TOUS');
  }

  enregistrer() {
    const actifs = this.modules().filter(m => m.actif).map(m => m.id);
    console.log('[ModulesComponent] configuration enregistrée :', {
      modulesActifs: actifs,
      montantMensuel: this.montantTotal(),
    });
    // TODO : brancher sur PUT /api/v1/admin/modules
  }

  /** Bordure et relief de la carte selon l'état du module. */
  carteClass(m: SaasModule): string {
    if (m.inclus) return 'border border-outline-variant hover:border-outline';
    if (m.actif)  return 'border-2 border-primary/30';
    return 'relative overflow-hidden group border border-outline-variant/70 hover:border-primary/50';
  }

  /** Pastille d'icône : pleine si module payant actif, discrète si inactif. */
  iconeClass(m: SaasModule): string {
    if (m.inclus) return 'bg-primary/10 text-primary';
    if (m.actif)  return 'bg-primary text-on-primary shadow-sm';
    return 'bg-surface-container-low text-secondary group-hover:text-primary';
  }

  badgeTexte(m: SaasModule): string {
    if (m.inclus) return 'Inclus dans Pro';
    const prix = `+ ${m.prix.toLocaleString('fr-FR')} FCFA/mois`;
    return m.essai && !m.actif ? `${prix} • Essai 14 jours` : prix;
  }

  badgeClass(m: SaasModule): string {
    if (m.inclus) return 'bg-surface-container-high text-on-surface-variant font-medium';
    if (m.actif)  return 'bg-emerald-50 text-primary border border-primary/30 font-semibold';
    if (m.essai)  return 'bg-amber-50 text-amber-700 border border-amber-200 font-semibold';
    return 'bg-surface-container-low text-secondary border border-outline-variant/50 font-semibold';
  }
}
