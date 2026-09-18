/**
 * Modèle Employé - Module RH
 */
export interface Employe {
  uuid: string;
  id?: number;
  matricule: string;
  nom: string;
  prenom: string;
  nomUsuel?: string;
  email: string;
  telephone: string;
  whatsapp?: string;
  telegram?: string;
  linkedin?: string;
  
  // Poste & Hiérarchie
  poste: string;
  posteEn?: string;
  departement: Departement;
  service?: string;
  direction?: string;
  niveauHierarchique?: string;
  categorieProfessionnelle?: string;
  managerId?: number;
  responsableRhId?: number;
  
  // Emploi & Contrat
  dateEmbauche: string; // ISO date
  dateFinContrat?: string;
  statut: StatutEmploye;
  typeContrat: TypeContrat;
  regimeTravail?: string;
  tauxTravail?: number;
  typeEmploi?: string;
  teletravail?: boolean;
  
  // Rémunération
  salaireBase?: number;
  salaireBrut?: number;
  salaireNet?: number;
  salaireMensuel?: number;
  salaireAnnuel?: number;
  devise: string;
  periodicitePaie?: string;
  tauxHoraire?: number;
  heuresHebdomadaires?: number;
  heuresMensuelles?: number;
  
  // Localisation
  adresse?: string;
  ville?: string;
  codePostal?: string;
  region?: string;
  paysResidence?: string;
  paysNaissance?: string;
  nationalite?: string;
  paysCode: PaysCode;
  fuseauHoraire?: string;
  
  // Identité
  dateNaissance?: string;
  lieuNaissance?: string;
  sexe?: 'M' | 'F' | 'AUTRE';
  civilite?: 'M' | 'MME' | 'MLLE' | 'DR' | 'PROF';
  etatCivil?: 'CELIBATAIRE' | 'MARIE' | 'DIVORCE' | 'VEUF' | 'CONCUBINAGE' | 'PACS';
  
  // Identifiants légaux
  cnps?: string;
  nif?: string;
  passeport?: string;
  carteIdentite?: string;
  permisConduire?: string;
  carteSejour?: string;
  visa?: string;
  permisTravail?: string;
  
  // Banque
  banque?: string;
  rib?: string;
  iban?: string;
  bic?: string;
  adresseBanque?: string;
  deviseCompte?: string;
  
  // Contacts urgence
  contactUrgence1Nom?: string;
  contactUrgence1Telephone?: string;
  contactUrgence1Lien?: string;
  contactUrgence2Nom?: string;
  contactUrgence2Telephone?: string;
  contactUrgence2Lien?: string;
  
  // Famille
  conjoint?: string;
  nombreEnfants?: number;
  personneACharge?: number;
  
  // Formation
  niveauEtude?: string;
  dernierDiplome?: string;
  ecoleUniversite?: string;
  anneeDiplome?: number;
  specialite?: string;
  
  // Compétences
  languesParlees?: string; // JSON
  competencesTechniques?: string;
  certifications?: string;
  
  // Performance
  scorePerformanceGlobal?: number;
  scorePerformanceAnnee?: number;
  dateDerniereEvaluation?: string;
  statutEvaluation?: string;
  
  // Congés
  soldeCongesAcquis?: number;
  soldeCongesPris?: number;
  soldeCongesRestant?: number;
  soldeRtt?: number;
  absencesNonJustifiees?: number;
  retards?: number;
  
  // Santé
  groupeSanguin?: string;
  allergies?: string;
  conditionsMedicales?: string;
  restrictionsTravail?: string;
  
  // Sécurité
  niveauAcces?: string;
  permissions?: string; // JSON
  roles?: string; // JSON
  accesSystemeAutorise?: boolean;
  dateDebutAcces?: string;
  dateFinAcces?: string;
  badgesAcces?: string;
  
  // RGPD
  rgpdApplicable?: boolean;
  donneesSensibles?: boolean;
  baseLegale?: string;
  dureeRetention?: number;
  consentement?: boolean;
  
  // IA & Analytics
  scoreEngagement?: number;
  risqueDepart?: number;
  predictionsIa?: string; // JSON
  recommandationsIa?: string; // JSON
  insightsAnalytics?: string; // JSON
  
  // Multi-tenant
  organisationId: number;
  hotelId?: number;
  
  // Audit
  dateCreation?: string;
  dateModification?: string;
  creePar?: string;
  modifiePar?: string;
  version?: number;
  
  // Soft delete
  actif: boolean;
  supprime?: boolean;
}

export type StatutEmploye = 
  | 'CANDIDAT' 
  | 'PERIODE_ESSAI' 
  | 'ACTIF' 
  | 'EN_CONGE_PROLONGE' 
  | 'DISPONIBILITE' 
  | 'SUSPENDU' 
  | 'PREAVIS' 
  | 'DEMISSIONNAIRE' 
  | 'LICENCIE' 
  | 'RETRAITE' 
  | 'DECEDE' 
  | 'ARCHIVE' 
  | 'INACTIF';

export type TypeContrat = 
  | 'CDI' 
  | 'CDD' 
  | 'INTERIM' 
  | 'APPRENTISSAGE' 
  | 'PROFESSIONNALISATION' 
  | 'STAGE' 
  | 'MISSION' 
  | 'CONSULTANT' 
  | 'VACATION' 
  | 'REMPLACEMENT' 
  | 'SAISONNIER' 
  | 'TEMPS_PARTIEL' 
  | 'PORTAGE_SALARIAL' 
  | 'AUTRE';

export type Departement = 
  | 'RECEPTION' 
  | 'HOUSEKEEPING' 
  | 'FINANCE' 
  | 'RH' 
  | 'FB' 
  | 'MAINTENANCE' 
  | 'SECURITE' 
  | 'SPA' 
  | 'RESTAURATION' 
  | 'ADMINISTRATION';

export type PaysCode = 
  | 'CI' 
  | 'SN' 
  | 'GH' 
  | 'FRA' 
  | 'CMR' 
  | 'ML' 
  | 'BF' 
  | 'TG' 
  | 'BJ';

/**
 * DTO pour création d'employé
 */
export interface CreateEmployeRequest {
  matricule: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  poste: string;
  departement: Departement;
  typeContrat: TypeContrat;
  dateEmbauche: string;
  paysCode: PaysCode;
  salaireBase?: number;
  devise?: string;
  hotelId?: number;
  [key: string]: any; // Pour les autres champs optionnels
}

/**
 * DTO pour mise à jour d'employé
 */
export interface UpdateEmployeRequest {
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  poste?: string;
  departement?: Departement;
  typeContrat?: TypeContrat;
  salaireBase?: number;
  [key: string]: any;
}
