/**
 * Modèle Congé - Module RH
 */
export interface Conge {
  uuid: string;
  id?: number;
  employeId: number;
  employeNom?: string;
  employeMatricule?: string;
  
  // Type & Nature
  typeConge: TypeConge;
  sousTypeConge?: string;
  natureConge?: 'PAYE' | 'NON_PAYE' | 'PARTIELLEMENT_PAYE';
  
  // Période
  dateDebut: string; // ISO date
  dateFin: string;
  heureDebut?: string;
  heureFin?: string;
  congéPartiel?: boolean;
  nombreJours?: number;
  nombreJoursOuvres?: number;
  nombreJoursCalendaires?: number;
  nombreHeures?: number;
  
  // Statut & Workflow
  statutConge: StatutConge;
  etapeWorkflow?: string;
  niveauxValidation?: string;
  
  // Motif
  motif?: string;
  justification?: string;
  commentairesEmploye?: string;
  commentairesValidateur?: string;
  
  // Solde
  soldeAvant?: number;
  soldeApres?: number;
  soldeInitial?: number;
  totalPris?: number;
  soldeRestant?: number;
  
  // Approbation
  approuve?: boolean;
  approuvePar?: number;
  dateApprobation?: string;
  typeApprobation?: string;
  
  // Validation hiérarchique
  valideParManager?: boolean;
  valideParRh?: boolean;
  valideParDirection?: boolean;
  dateValidationManager?: string;
  dateValidationRh?: string;
  dateValidationDirection?: string;
  commentairesManager?: string;
  commentairesRh?: string;
  commentairesDirection?: string;
  
  // Rejet
  rejete?: boolean;
  rejetePar?: number;
  dateRejet?: string;
  motifRejet?: string;
  
  // Annulation
  annule?: boolean;
  annulePar?: number;
  dateAnnulation?: string;
  motifAnnulation?: string;
  
  // Conformité
  paysCode?: string;
  conformeReglementation?: boolean;
  obligationsLegales?: string;
  
  // Documents
  documentsJustificatifs?: string;
  certificatsMedicaux?: string;
  
  // Multi-tenant
  organisationId: number;
  hotelId?: number;
  
  // Audit
  dateCreation?: string;
  dateModification?: string;
  creePar?: string;
  modifiePar?: string;
}

export type TypeConge = 
  | 'ANNUEL' 
  | 'MALADIE' 
  | 'MATERNITE' 
  | 'PATERNITE' 
  | 'PARENTAL' 
  | 'SANS_SOLDE' 
  | 'RECUPERATION' 
  | 'RTT' 
  | 'EXCEPTIONNEL' 
  | 'SABBATIQUE' 
  | 'FORMATION' 
  | 'EVENEMENT_FAMILIAL' 
  | 'CONVENANCES_PERSONNELLES' 
  | 'AUTRE';

export type StatutConge = 
  | 'BROUILLON' 
  | 'EN_ATTENTE' 
  | 'EN_VALIDATION' 
  | 'APPROUVE' 
  | 'REJETE' 
  | 'ANNULE' 
  | 'EN_COURS' 
  | 'TERMINE' 
  | 'REPORTE';

export interface CreateCongeRequest {
  employeId: number;
  typeConge: TypeConge;
  dateDebut: string;
  dateFin: string;
  motif?: string;
  nombreJours?: number;
  [key: string]: any;
}

export interface UpdateCongeRequest {
  dateDebut?: string;
  dateFin?: string;
  motif?: string;
  [key: string]: any;
}

export interface ApproveCongeRequest {
  approbateurId: number;
}

export interface RejectCongeRequest {
  motif: string;
}
