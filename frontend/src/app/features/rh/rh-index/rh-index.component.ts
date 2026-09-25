import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Collaborateur {
  nom: string;
  matricule: string;
  photo: string;
  photoAlt: string;
  poste: string;
  service: string;
  contrat: string;
  shiftIcon: string;
  shiftIconClasse: string;
  shiftLibelle: string;
  shiftLibelleClasse: string;
  statutClasse: string;
  statutPointClasse: string;
  statutLibelle: string;
  ligneClasse: string;
  avatarClasse: string;
}

interface JourMatrice {
  libelle: string;
  enteteClasse: string;
  celluleClasse: string;
  effectifClasse: string;
  effectif: string;
  matinClasse: string;
  soirClasse: string;
  nuitClasse: string;
}

interface JourPlanning {
  conteneurClasse: string;
  jourClasse: string;
  jour: string;
  detailClasse: string;
  detail: string;
  heuresClasse: string;
  heures: string;
}

interface OngletService {
  cle: string;
  libelle: string;
}

@Component({
  selector: 'app-rh-index',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="max-w-[1600px] mx-auto space-y-6">

      <!-- Header Banner & Module Meta -->
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 pb-2 border-b border-outline-variant">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-headline-lg font-headline-lg text-on-surface tracking-tight">Ressources Humaines &amp; Planning des Équipes</h1>
            <!-- Status Badge -->
            <span class="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded-full text-caption font-caption uppercase tracking-wider bg-primary/10 text-primary border border-primary/20 font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              Module Indépendant Activé (Plan Pro)
            </span>
          </div>
          <p class="text-body-md font-body-md text-secondary mt-1">
            Gestion des 28 collaborateurs, plannings de shift, congés et conformité UEMOA
          </p>
        </div>
        <!-- Quick Filters/Tools -->
        <div class="flex items-center gap-2">
          <div class="inline-flex rounded-lg border border-outline-variant bg-surface-container-lowest p-1">
            <button type="button" [class]="classeVue('planning')" (click)="onSelectionnerVue('planning')">Planning Semaine</button>
            <button type="button" [class]="classeVue('paie')" (click)="onSelectionnerVue('paie')">Rapports de Paie</button>
            <button type="button" [class]="classeVue('caisse')" (click)="onSelectionnerVue('caisse')">Conformité Caisse</button>
          </div>
          <button type="button"
                  class="p-2 border border-outline-variant bg-surface-container-lowest rounded-lg text-secondary hover:bg-surface-container-low transition-colors"
                  title="Exporter PDF / Excel"
                  (click)="onExporter()">
            <span class="material-symbols-outlined text-lg">file_download</span>
          </button>
        </div>
      </div>

      <!-- 4 Synthetics KPIs Bento Grid -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <!-- KPI 1 -->
        <div class="p-5 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">En poste aujourd'hui</span>
            <div class="p-2 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-lg">badge</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface font-bold">19 <span class="text-headline-md font-headline-md font-normal text-secondary">/ 28</span></span>
          </div>
          <div class="flex items-center gap-1.5 mt-2 text-caption font-caption text-secondary">
            <span class="w-2 h-2 rounded-full bg-primary"></span>
            <span>12 matin (07h-15h) • 7 soir (15h-23h)</span>
          </div>
        </div>
        <!-- KPI 2 -->
        <div class="p-5 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">Masse Salariale Mensuelle</span>
            <div class="p-2 rounded-lg bg-surface-container-low text-secondary">
              <span class="material-symbols-outlined text-lg">payments</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-headline-lg font-headline-lg text-on-surface font-bold tracking-tight">6 450 000</span>
            <span class="text-label-sm font-label-sm text-secondary font-semibold">FCFA</span>
          </div>
          <div class="flex items-center gap-1 mt-2 text-caption font-caption text-primary font-semibold">
            <span class="material-symbols-outlined text-sm">trending_down</span>
            <span>-1.8% vs mois précédent (budget maîtrisé)</span>
          </div>
        </div>
        <!-- KPI 3 -->
        <div class="p-5 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">Congés &amp; Absences</span>
            <div class="p-2 rounded-lg bg-surface-container-low text-secondary">
              <span class="material-symbols-outlined text-lg">event_busy</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface font-bold">3 <span class="text-body-md font-body-md font-normal text-secondary">en cours</span></span>
          </div>
          <div class="flex items-center gap-1.5 mt-2 text-caption font-caption text-secondary">
            <span class="px-1.5 py-0.5 rounded bg-surface-container text-on-surface-variant font-medium">2 Payés</span>
            <span class="px-1.5 py-0.5 rounded bg-surface-container text-on-surface-variant font-medium">1 Récupération</span>
          </div>
        </div>
        <!-- KPI 4 -->
        <div class="p-5 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm hover:border-outline transition-all duration-200">
          <div class="flex items-center justify-between mb-3">
            <span class="text-label-md font-label-md text-secondary uppercase tracking-wider">Ponctualité &amp; Assiduité</span>
            <div class="p-2 rounded-lg bg-surface-container-low text-primary">
              <span class="material-symbols-outlined text-lg">timer</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-display-md font-display-md text-on-surface font-bold">96.4%</span>
          </div>
          <div class="flex items-center gap-1 mt-2 text-caption font-caption text-primary font-semibold">
            <span class="material-symbols-outlined text-sm">arrow_upward</span>
            <span>+1.2% ce mois-ci (pointage biométrique)</span>
          </div>
        </div>
      </div>

      <!-- 2-Column Main Operation View -->
      <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">

        <!-- Left Column (8 cols): Staff List & Weekly Shifts Planning -->
        <div class="lg:col-span-8 space-y-4">
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm overflow-hidden">
            <!-- Table Action & Filters Toolbar -->
            <div class="p-4 border-b border-outline-variant flex flex-wrap items-center justify-between gap-3 bg-surface-bright">
              <div class="flex items-center gap-2">
                <span class="text-headline-sm font-headline-sm text-on-surface">Effectifs &amp; Planning</span>
                <span class="text-label-sm font-label-sm text-secondary bg-surface-container px-2 py-0.5 rounded-md">Semaine 42 (16 - 22 Octobre)</span>
              </div>
              <!-- Department Tabs -->
              <div class="flex items-center gap-1 text-label-sm font-label-sm">
                <button *ngFor="let o of onglets"
                        type="button"
                        [class]="classeOnglet(o)"
                        (click)="onSelectionnerOnglet(o)">{{ o.libelle }}</button>
              </div>
            </div>
            <!-- Table of Employees -->
            <div class="overflow-x-auto">
              <table class="w-full text-left border-collapse">
                <thead>
                  <tr class="border-b border-outline-variant bg-surface-container-low text-secondary text-caption font-caption uppercase tracking-wider">
                    <th class="py-3 px-4 font-semibold">Collaborateur</th>
                    <th class="py-3 px-4 font-semibold">Service &amp; Poste</th>
                    <th class="py-3 px-4 font-semibold">Contrat</th>
                    <th class="py-3 px-4 font-semibold">Shift Aujourd'hui</th>
                    <th class="py-3 px-4 font-semibold">Statut Pointage</th>
                    <th class="py-3 px-4 font-semibold text-right">Actions</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-outline-variant text-body-md font-body-md">
                  <tr *ngFor="let c of collaborateurs" [class]="c.ligneClasse" (click)="onOuvrirFiche(c)">
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-3">
                        <div [class]="c.avatarClasse">
                          <img class="w-full h-full object-cover" [src]="c.photo" [alt]="c.photoAlt" />
                        </div>
                        <div>
                          <p class="font-semibold text-on-surface leading-tight">{{ c.nom }}</p>
                          <p class="text-caption font-caption text-secondary">Matricule : {{ c.matricule }}</p>
                        </div>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <p class="font-medium text-on-surface">{{ c.poste }}</p>
                      <p class="text-caption font-caption text-secondary">{{ c.service }}</p>
                    </td>
                    <td class="py-3.5 px-4">
                      <span class="inline-flex px-2 py-0.5 rounded text-caption font-caption font-semibold bg-surface-container text-on-surface-variant">{{ c.contrat }}</span>
                    </td>
                    <td class="py-3.5 px-4">
                      <div class="flex items-center gap-1.5">
                        <span [class]="c.shiftIconClasse">{{ c.shiftIcon }}</span>
                        <span [class]="c.shiftLibelleClasse">{{ c.shiftLibelle }}</span>
                      </div>
                    </td>
                    <td class="py-3.5 px-4">
                      <span [class]="c.statutClasse">
                        <span [class]="c.statutPointClasse"></span>
                        {{ c.statutLibelle }}
                      </span>
                    </td>
                    <td class="py-3.5 px-4 text-right">
                      <button type="button"
                              class="p-1 text-secondary hover:text-primary rounded hover:bg-surface-container transition-colors"
                              (click)="onMenuCollaborateur(c, $event)">
                        <span class="material-symbols-outlined text-lg">more_vert</span>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- Footer Pagination & Count -->
            <div class="p-3 border-t border-outline-variant bg-surface-container-lowest flex items-center justify-between text-caption font-caption text-secondary">
              <span>Affichage de 5 sur 28 collaborateurs</span>
              <div class="flex items-center gap-1">
                <button type="button" class="px-2 py-1 rounded border border-outline-variant hover:bg-surface-container transition-colors disabled:opacity-40" disabled>Précédent</button>
                <button type="button" class="px-2 py-1 rounded border border-primary bg-primary text-on-primary font-bold" (click)="onPage(1)">1</button>
                <button type="button" class="px-2 py-1 rounded border border-outline-variant hover:bg-surface-container transition-colors" (click)="onPage(2)">2</button>
                <button type="button" class="px-2 py-1 rounded border border-outline-variant hover:bg-surface-container transition-colors" (click)="onPage(3)">3</button>
                <button type="button" class="px-2 py-1 rounded border border-outline-variant hover:bg-surface-container transition-colors" (click)="onPage(2)">Suivant</button>
              </div>
            </div>
          </div>

          <!-- Weekly Shift Rack Preview (Condensed view) -->
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant p-4 shadow-sm">
            <div class="flex items-center justify-between mb-3">
              <span class="text-headline-sm font-headline-sm text-on-surface">Matrice des Postes de la Semaine</span>
              <div class="flex items-center gap-3 text-caption font-caption text-secondary">
                <div class="flex items-center gap-1"><span class="w-2.5 h-2.5 rounded bg-emerald-500"></span> Matin</div>
                <div class="flex items-center gap-1"><span class="w-2.5 h-2.5 rounded bg-blue-500"></span> Soir</div>
                <div class="flex items-center gap-1"><span class="w-2.5 h-2.5 rounded bg-slate-700"></span> Nuit</div>
                <div class="flex items-center gap-1"><span class="w-2.5 h-2.5 rounded bg-amber-400"></span> Congé</div>
              </div>
            </div>
            <div class="grid grid-cols-7 gap-2 text-center text-caption font-caption">
              <!-- Days Header -->
              <div *ngFor="let j of joursMatrice" [class]="j.enteteClasse">{{ j.libelle }}</div>
              <!-- Occupancy bars summary -->
              <div *ngFor="let j of joursMatrice" [class]="j.celluleClasse">
                <div [class]="j.effectifClasse">{{ j.effectif }}</div>
                <div class="w-full bg-surface-container h-1.5 rounded-full overflow-hidden flex">
                  <div [class]="j.matinClasse"></div>
                  <div [class]="j.soirClasse"></div>
                  <div [class]="j.nuitClasse"></div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Column (4 cols): Detailed Express Staff Profile (Awa Touré) -->
        <div class="lg:col-span-4 space-y-4">
          <div class="bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm p-5 space-y-5">
            <!-- Employee Card Header -->
            <div class="flex items-start justify-between">
              <div class="flex items-center gap-3">
                <div class="w-14 h-14 rounded-xl overflow-hidden ring-2 ring-primary/20 shadow-sm">
                  <img class="w-full h-full object-cover" [src]="fiche.photo" [alt]="fiche.photoAlt" />
                </div>
                <div>
                  <h3 class="text-headline-sm font-headline-sm text-on-surface font-bold">Awa Touré</h3>
                  <p class="text-body-sm font-body-sm text-secondary">Hôtesse Réception Senior</p>
                  <span class="inline-flex items-center gap-1 text-caption font-caption text-primary font-semibold mt-1">
                    <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
                    Matricule : ETH-0428
                  </span>
                </div>
              </div>
              <button type="button"
                      class="p-1.5 rounded-lg text-secondary hover:bg-surface-container-low transition-colors"
                      title="Modifier la fiche"
                      (click)="onModifierFiche()">
                <span class="material-symbols-outlined text-lg">edit</span>
              </button>
            </div>
            <!-- Contract & Administrative Data -->
            <div class="p-3 bg-surface-container-low rounded-lg space-y-2 border border-outline-variant">
              <div class="flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Type de Contrat :</span>
                <span class="font-semibold text-on-surface">CDI (Plein temps 40h)</span>
              </div>
              <div class="flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Date d'embauche :</span>
                <span class="font-semibold text-on-surface">04 Mars 2021 (3 ans 7 mois)</span>
              </div>
              <div class="flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Conformité CNPS / UEMOA :</span>
                <span class="inline-flex items-center gap-1 text-primary font-semibold">
                  <span class="material-symbols-outlined text-sm">verified</span>
                  Régularisée &amp; Déclarée
                </span>
              </div>
              <div class="flex items-center justify-between text-caption font-caption">
                <span class="text-secondary">Indice Conventionnel :</span>
                <span class="font-semibold text-on-surface">Catégorie 6 - Échelon B</span>
              </div>
            </div>
            <!-- Weekly Schedule for Awa -->
            <div class="space-y-2">
              <div class="flex items-center justify-between">
                <span class="text-label-md font-label-md font-semibold text-on-surface">Semaine en cours</span>
                <span class="text-caption font-caption text-secondary">38h planifiées</span>
              </div>
              <div class="space-y-1.5 text-caption font-caption">
                <div *ngFor="let j of planningSemaine" [class]="j.conteneurClasse">
                  <span [class]="j.jourClasse">{{ j.jour }}</span>
                  <span [class]="j.detailClasse">{{ j.detail }}</span>
                  <span [class]="j.heuresClasse">{{ j.heures }}</span>
                </div>
              </div>
            </div>
            <!-- Leave Balance & Quota -->
            <div class="space-y-2">
              <span class="text-label-md font-label-md font-semibold text-on-surface">Solde de Congés &amp; Droits</span>
              <div class="grid grid-cols-2 gap-2">
                <div class="p-2.5 rounded-lg border border-outline-variant bg-surface-container-low text-center">
                  <p class="text-display-md font-display-md text-primary font-bold leading-none">14.5</p>
                  <p class="text-caption font-caption text-secondary mt-1">Jours Payés Restants</p>
                </div>
                <div class="p-2.5 rounded-lg border border-outline-variant bg-surface-container-low text-center">
                  <p class="text-display-md font-display-md text-on-surface font-bold leading-none">2.0</p>
                  <p class="text-caption font-caption text-secondary mt-1">Jours Récupération</p>
                </div>
              </div>
            </div>
            <!-- Action Button for Staff Member -->
            <div class="pt-2">
              <button type="button"
                      class="w-full py-2.5 px-4 rounded-lg bg-primary text-on-primary text-label-md font-label-md font-semibold hover:bg-primary-container transition-all flex items-center justify-center gap-2 shadow-sm active:scale-[0.98]"
                      (click)="onModifierPlanning()">
                <span class="material-symbols-outlined text-lg">calendar_month</span>
                <span>Modifier le planning / Poser un congé</span>
              </button>
            </div>
          </div>
          <!-- Quick Compliance Card -->
          <div class="p-4 bg-surface-container-lowest rounded-xl border border-outline-variant shadow-sm flex items-center gap-3">
            <div class="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-xl">policy</span>
            </div>
            <div class="flex-1">
              <p class="text-label-md font-label-md font-semibold text-on-surface">Conformité Convention Hôtellerie</p>
              <p class="text-caption font-caption text-secondary">Dernier audit des plannings : Validé 100% sans dépassement des 44h légales</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Bottom Modularity Advisory Banner -->
      <div class="p-4 rounded-xl bg-surface-container-lowest border border-outline-variant shadow-sm flex items-center justify-between gap-4">
        <div class="flex items-center gap-3">
          <div class="w-8 h-8 rounded-lg bg-surface-container-low text-primary flex items-center justify-center shrink-0">
            <span class="material-symbols-outlined text-lg">tune</span>
          </div>
          <p class="text-body-sm font-body-sm text-secondary">
            <strong class="text-on-surface font-medium">Architecture Modulaire Étoile OS :</strong> Ce module RH est autonome. Sa désactivation depuis l'espace Modules masquera ce menu pour les réceptionnistes sans impacter les données de paie ni l'historique de pointage.
          </p>
        </div>
        <a class="shrink-0 text-label-sm font-label-sm font-semibold text-primary hover:underline flex items-center gap-1" href="#" (click)="onGererModules($event)">
          Gérer les modules
          <span class="material-symbols-outlined text-sm">arrow_forward</span>
        </a>
      </div>

    </div>
  `,
})
export class RhIndexComponent {
  // ---- Bascule de vue (Planning Semaine / Rapports de Paie / Conformité Caisse) ----
  vue = signal<'planning' | 'paie' | 'caisse'>('planning');

  private readonly vueActive =
    'px-3 py-1.5 rounded-md text-caption font-caption font-semibold bg-surface-container text-on-surface shadow-xs';
  private readonly vueInactive =
    'px-3 py-1.5 rounded-md text-caption font-caption text-secondary hover:text-on-surface transition-colors';

  classeVue(cle: 'planning' | 'paie' | 'caisse'): string {
    return this.vue() === cle ? this.vueActive : this.vueInactive;
  }

  onSelectionnerVue(cle: 'planning' | 'paie' | 'caisse'): void {
    this.vue.set(cle);
    console.log('Vue RH sélectionnée :', cle);
  }

  // ---- Onglets de service ----
  onglets: OngletService[] = [
    { cle: 'tous',         libelle: 'Tous (28)' },
    { cle: 'reception',    libelle: 'Réception (7)' },
    { cle: 'housekeeping', libelle: 'Housekeeping (12)' },
    { cle: 'maintenance',  libelle: 'Maintenance (5)' },
    { cle: 'securite',     libelle: 'Sécurité (4)' },
  ];

  ongletActif = signal<string>('tous');

  private readonly ongletActifClasse = 'px-2.5 py-1 rounded bg-primary text-on-primary font-medium';
  private readonly ongletInactifClasse = 'px-2.5 py-1 rounded text-secondary hover:bg-surface-container-low transition-colors';

  classeOnglet(onglet: OngletService): string {
    return this.ongletActif() === onglet.cle ? this.ongletActifClasse : this.ongletInactifClasse;
  }

  onSelectionnerOnglet(onglet: OngletService): void {
    this.ongletActif.set(onglet.cle);
    console.log('Service sélectionné :', onglet.cle);
  }

  // ---- Effectifs ----
  private static readonly PILL_SERVICE =
    'inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full text-caption font-caption font-semibold bg-emerald-50 text-primary border border-primary/20';
  private static readonly PILL_NEUTRE =
    'inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full text-caption font-caption font-semibold bg-surface-container text-secondary border border-outline-variant';
  private static readonly PILL_CONGE =
    'inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full text-caption font-caption font-semibold bg-amber-50 text-amber-800 border border-amber-300';

  collaborateurs: Collaborateur[] = [
    {
      nom: 'Awa Touré',
      matricule: 'ETH-0428',
      photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAYGB4WlQnQ8rDe3CKK5ANj66dS3Lm2Tot6NvaK1BOTkko2XYRwKRwF_2LQh8Pc_FMEbyFUz5yhYjS1gHELT6WHQKaU2pHLtr3KzIxHt1E5UDowzVhPBqiAvHHKuhpRoAVZPyog2wXBUoYlAvIo3LwWfbsvyBwJA4CkiLviwo0yZaaXoOW9g8qWLymGXDorFMF6Qsq6Da9cRZUkOxFGCHtRxWgGmwlpU3WTJmFxOmR-3o-HKkeNkliv',
      photoAlt: "Portrait studio moderne d'une jeune femme ivoirienne souriante et chaleureuse, en uniforme impeccable d'accueil hôtelier haut de gamme couleur vert forêt, éclairage de studio feutré et soigné.",
      poste: 'Hôtesse Réception Senior',
      service: 'Service Accueil & Conciergerie',
      contrat: 'CDI',
      shiftIcon: 'light_mode',
      shiftIconClasse: 'material-symbols-outlined text-base text-primary',
      shiftLibelle: 'Matin (07h-15h)',
      shiftLibelleClasse: 'text-label-md font-label-md font-medium text-on-surface',
      statutClasse: RhIndexComponent.PILL_SERVICE,
      statutPointClasse: 'w-1.5 h-1.5 rounded-full bg-primary',
      statutLibelle: 'En Service (Pointé 06:52)',
      ligneClasse: 'bg-surface-container-low/60 hover:bg-surface-container-low transition-colors cursor-pointer border-l-4 border-primary',
      avatarClasse: 'w-9 h-9 rounded-full bg-surface-container overflow-hidden ring-2 ring-primary/30',
    },
    {
      nom: "Koffi N'Dri",
      matricule: 'ETH-0312',
      photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDxHcS7r6GxmnI5XY3QiQT83B5FHGR7NLC2yD6CRECmR_nmch68rIA1j2YELhmHesgRPqn0ySRNFu9hFuoL3rY3ITNOsCiU9wYkhBCilfRFVe7mTGptHwMd4FYAG1BKYlE5Fo5eJqqPRHXBPUnTz-ljwHn4t5MoYpSA8xQL6_-iZJS2r13nrF6yC8B4E2tONdAVmVB13ML_rYbDwhTLNvClQv6BFt86c2-5zeA9_iO4AoLeDTgcbnLN',
      photoAlt: "Portrait professionnel d'un homme ouest-africain d'une trentaine d'années, souriant avec bienveillance, vêtu d'une chemise blanche cravate sombre de superviseur hôtelier de luxe, lumière douce tamisée.",
      poste: "Gouvernant d'Étage",
      service: 'Housekeeping & Suites',
      contrat: 'CDI',
      shiftIcon: 'light_mode',
      shiftIconClasse: 'material-symbols-outlined text-base text-primary',
      shiftLibelle: 'Matin (07h-15h)',
      shiftLibelleClasse: 'text-label-md font-label-md font-medium text-on-surface',
      statutClasse: RhIndexComponent.PILL_SERVICE,
      statutPointClasse: 'w-1.5 h-1.5 rounded-full bg-primary',
      statutLibelle: 'En Service (Pointé 06:58)',
      ligneClasse: 'hover:bg-surface-container-low transition-colors cursor-pointer',
      avatarClasse: 'w-9 h-9 rounded-full bg-surface-container overflow-hidden',
    },
    {
      nom: 'Marie Konan',
      matricule: 'ETH-0544',
      photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCHX_mxDaBuFQytrLaVvT6_BaTFCISiK5-Pp6oF8GjTgmqc0dk7z-Nz69Qk76qIIUSmvz9y_M60z7UjIZR5utmWmlSqE2-BTSHGCOxkVB7uSBqxD6gtuNAo4wv6_Wuc8uVQn-qJQs8KOXfjiNAkNyzHMB0tnXzXYwhOljFxyjxFIHPCackM2usgibhkXVrVFm5nQNquGEZMJDR5QqTPMRBdrMgYq9HZmRRTrve7VLZ6qkZL1qyubWW2',
      photoAlt: "Portrait en plan rapproché d'une femme ivoirienne dynamique et professionnelle, cheveux coiffés élégamment, portant un tablier gris ardoise haut de gamme d'intendante hôtelière, ambiance contemporaine sobre.",
      poste: 'Femme de Chambre',
      service: 'Housekeeping Étages 3 & 4',
      contrat: 'CDD',
      shiftIcon: 'bedtime',
      shiftIconClasse: 'material-symbols-outlined text-base text-secondary',
      shiftLibelle: 'Soir (15h-23h)',
      shiftLibelleClasse: 'text-label-md font-label-md font-medium text-on-surface',
      statutClasse: RhIndexComponent.PILL_NEUTRE,
      statutPointClasse: 'w-1.5 h-1.5 rounded-full bg-secondary',
      statutLibelle: 'Prévu à 15:00',
      ligneClasse: 'hover:bg-surface-container-low transition-colors cursor-pointer',
      avatarClasse: 'w-9 h-9 rounded-full bg-surface-container overflow-hidden',
    },
    {
      nom: 'Bakary Diop',
      matricule: 'ETH-0209',
      photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA2oiY1vNGJi4JyOtabAlSaY5nLhDWpikahwDxHQZlux98WrebU0nRWtlG5cBXaLvjXqeGTfraGRAXIDAVAqFzovrOGF3DT4TXY1MoX_wmPj4rrxf7y_CG3aX11PQaVqM29g7Gi3l1WoErqKyehqomwfTIxritMtWFSKa6zQFFMx7ho9r9l2zx9lvdmr_V9c367srGQv2GHUy-FcedO9dScQJr5Cl7NjRwZQyL8zoyqJFF45VBCDM0o',
      photoAlt: "Portrait d'un technicien de maintenance d'hôtel de luxe, homme mûr ouest-africain au regard attentif, polo technique sombre brodé au logo discret, arrière-plan sobre d'atelier technique impeccablement rangé.",
      poste: 'Électricien & CVC',
      service: 'Maintenance Technique',
      contrat: 'CDI',
      shiftIcon: 'event_busy',
      shiftIconClasse: 'material-symbols-outlined text-base text-secondary',
      shiftLibelle: 'Repos Hebdomadaire',
      shiftLibelleClasse: 'text-label-md font-label-md font-medium text-secondary',
      statutClasse: RhIndexComponent.PILL_CONGE,
      statutPointClasse: 'w-1.5 h-1.5 rounded-full bg-amber-600',
      statutLibelle: 'En Congé Payé (J-3/5)',
      ligneClasse: 'hover:bg-surface-container-low transition-colors cursor-pointer',
      avatarClasse: 'w-9 h-9 rounded-full bg-surface-container overflow-hidden',
    },
    {
      nom: 'Jean-Luc Adou',
      matricule: 'ETH-0610',
      photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBU82yIUjZysVRke0pi7Ze52x6hhWCI4rC2n6KN0pFm5jyvOW540zqeoQHSHK6WGy39mLl3moK17dl_FIrt9r7nIzUYsG9CQS5hL-7BZ3-N9EVXrNvTGNukVm0WMafiqO7gqg2t6MEHwustJgOPoxpWRVFpluRV0lo9q2iVgCCI0PwcHI-yIkwUCKO2pKSMv5_1eGQp3dI3vKEMc9ODlrSE1ODPmRd9jQmYiy0-azoF2U8B_F56aH2y',
      photoAlt: "Portrait en buste d'un jeune agent de sécurité hôtelière grand et calme, costume costume noir élégant, oreillette discrète, dans un vestibule d'hôtel prestigieux aux tons chaleureux et tamisés.",
      poste: 'Agent de Sûreté & Accès',
      service: 'Sécurité & Ronde de nuit',
      contrat: 'CDI',
      shiftIcon: 'dark_mode',
      shiftIconClasse: 'material-symbols-outlined text-base text-secondary',
      shiftLibelle: 'Nuit (23h-07h)',
      shiftLibelleClasse: 'text-label-md font-label-md font-medium text-on-surface',
      statutClasse: RhIndexComponent.PILL_NEUTRE,
      statutPointClasse: 'w-1.5 h-1.5 rounded-full bg-secondary',
      statutLibelle: 'Fin de garde à 07:00',
      ligneClasse: 'hover:bg-surface-container-low transition-colors cursor-pointer',
      avatarClasse: 'w-9 h-9 rounded-full bg-surface-container overflow-hidden',
    },
  ];

  // ---- Matrice hebdomadaire des postes ----
  private static readonly ENTETE_JOUR = 'p-2 bg-surface-container rounded-lg font-semibold text-on-surface';
  private static readonly ENTETE_AUJ =
    'p-2 bg-surface-container rounded-lg font-semibold text-on-surface ring-2 ring-primary/40 bg-surface-container-low text-primary';
  private static readonly CELLULE = 'p-2 border border-outline-variant rounded-lg space-y-1';
  private static readonly CELLULE_AUJ = 'p-2 border border-primary/50 bg-primary/5 rounded-lg space-y-1';
  private static readonly EFFECTIF = 'text-label-sm font-label-sm font-semibold';
  private static readonly EFFECTIF_AUJ = 'text-label-sm font-label-sm font-semibold text-primary';

  joursMatrice: JourMatrice[] = [
    { libelle: 'Lun 16', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '19 en poste',
      matinClasse: 'bg-emerald-500 w-[60%]', soirClasse: 'bg-blue-500 w-[30%]', nuitClasse: 'bg-slate-700 w-[10%]' },
    { libelle: 'Mar 17', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '18 en poste',
      matinClasse: 'bg-emerald-500 w-[55%]', soirClasse: 'bg-blue-500 w-[35%]', nuitClasse: 'bg-slate-700 w-[10%]' },
    { libelle: 'Mer 18 (Auj.)', enteteClasse: RhIndexComponent.ENTETE_AUJ, celluleClasse: RhIndexComponent.CELLULE_AUJ,
      effectifClasse: RhIndexComponent.EFFECTIF_AUJ, effectif: '19 en poste',
      matinClasse: 'bg-emerald-500 w-[63%]', soirClasse: 'bg-blue-500 w-[27%]', nuitClasse: 'bg-slate-700 w-[10%]' },
    { libelle: 'Jeu 19', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '20 en poste',
      matinClasse: 'bg-emerald-500 w-[50%]', soirClasse: 'bg-blue-500 w-[40%]', nuitClasse: 'bg-slate-700 w-[10%]' },
    { libelle: 'Ven 20', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '21 en poste',
      matinClasse: 'bg-emerald-500 w-[50%]', soirClasse: 'bg-blue-500 w-[40%]', nuitClasse: 'bg-slate-700 w-[10%]' },
    { libelle: 'Sam 21', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '17 en poste',
      matinClasse: 'bg-emerald-500 w-[50%]', soirClasse: 'bg-blue-500 w-[35%]', nuitClasse: 'bg-slate-700 w-[15%]' },
    { libelle: 'Dim 22', enteteClasse: RhIndexComponent.ENTETE_JOUR, celluleClasse: RhIndexComponent.CELLULE,
      effectifClasse: RhIndexComponent.EFFECTIF, effectif: '15 en poste',
      matinClasse: 'bg-emerald-500 w-[45%]', soirClasse: 'bg-blue-500 w-[40%]', nuitClasse: 'bg-slate-700 w-[15%]' },
  ];

  // ---- Fiche express Awa Touré ----
  fiche = {
    photo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDZO3mmMLcbDWzOMZWJU5ubz7ZGy-weYq3eXb1A0mB5HEe17spLX-aWCmkYsQyu3oR3BK1j-k1cJOJtdHt3pwgg_pWIDR4tdtRdg4HqMEF-w_6VzU878rlY9GZbgBrKVhpTUoeEq20nuzBC68ctS9iu8pcG32mh7Ote1CEo7vskGuaFWHAIKmJd089C9fG5pwJbQ8EDfMzE3IIagkFe6Kmug6ffK1CrqDbomQGTLerwm7VDeYpkuUTa',
    photoAlt: "Gros plan sur une hôtesse d'accueil d'hôtel 5 étoiles à Abidjan, jeune femme ivoirienne rayonnante avec un badge doré Étoile OS sur son blazer chic sombre, éclairage studio flatteur et naturel.",
  };

  private static readonly LIGNE_CLAIRE = 'flex items-center justify-between p-2 rounded bg-surface-bright border border-outline-variant';
  private static readonly LIGNE_CLAIRE_GRISE = 'flex items-center justify-between p-2 rounded bg-surface-bright border border-outline-variant text-secondary';
  private static readonly LIGNE_AUJ = 'flex items-center justify-between p-2 rounded bg-primary/10 border border-primary/30';
  private static readonly LIGNE_REPOS = 'flex items-center justify-between p-2 rounded bg-surface-container border border-outline-variant text-secondary';

  planningSemaine: JourPlanning[] = [
    { conteneurClasse: RhIndexComponent.LIGNE_CLAIRE, jourClasse: 'font-semibold text-on-surface', jour: 'Lundi 16',
      detailClasse: 'text-secondary', detail: '07h00 - 15h00 (Pointé 06:55 - 15:05)',
      heuresClasse: 'text-primary font-semibold', heures: '8h' },
    { conteneurClasse: RhIndexComponent.LIGNE_CLAIRE, jourClasse: 'font-semibold text-on-surface', jour: 'Mardi 17',
      detailClasse: 'text-secondary', detail: '07h00 - 15h00 (Pointé 06:50 - 15:02)',
      heuresClasse: 'text-primary font-semibold', heures: '8h' },
    { conteneurClasse: RhIndexComponent.LIGNE_AUJ, jourClasse: 'font-bold text-primary', jour: 'Mercredi 18 (Auj.)',
      detailClasse: 'text-primary font-medium', detail: '07h00 - 15h00 (En cours)',
      heuresClasse: 'text-primary font-bold', heures: 'En poste' },
    { conteneurClasse: RhIndexComponent.LIGNE_CLAIRE_GRISE, jourClasse: 'font-medium text-on-surface', jour: 'Jeudi 19',
      detailClasse: '', detail: '15h00 - 23h00 (Shift Soir)', heuresClasse: '', heures: '8h' },
    { conteneurClasse: RhIndexComponent.LIGNE_CLAIRE_GRISE, jourClasse: 'font-medium text-on-surface', jour: 'Vendredi 20',
      detailClasse: '', detail: '15h00 - 23h00 (Shift Soir)', heuresClasse: '', heures: '8h' },
    { conteneurClasse: RhIndexComponent.LIGNE_REPOS, jourClasse: 'font-medium', jour: 'Samedi 21',
      detailClasse: '', detail: 'Repos hebdomadaire', heuresClasse: '', heures: 'OFF' },
    { conteneurClasse: RhIndexComponent.LIGNE_REPOS, jourClasse: 'font-medium', jour: 'Dimanche 22',
      detailClasse: '', detail: 'Repos hebdomadaire', heuresClasse: '', heures: 'OFF' },
  ];

  // ---- Actions ----
  onExporter(): void {
    console.log('Export PDF / Excel du planning RH');
  }

  onOuvrirFiche(c: Collaborateur): void {
    console.log('Ouverture de la fiche collaborateur :', c.matricule);
  }

  onMenuCollaborateur(c: Collaborateur, event: Event): void {
    event.stopPropagation();
    console.log('Menu contextuel du collaborateur :', c.matricule);
  }

  onPage(page: number): void {
    console.log('Pagination effectifs — page', page);
  }

  onModifierFiche(): void {
    console.log('Modification de la fiche Awa Touré');
  }

  onModifierPlanning(): void {
    console.log('Modification du planning / pose de congé');
  }

  onGererModules(event: Event): void {
    event.preventDefault();
    console.log('Ouverture de la gestion des modules');
  }
}
