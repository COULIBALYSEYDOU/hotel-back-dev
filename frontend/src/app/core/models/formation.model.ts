/**
 * Modèle Formation - Module RH
 */
export interface Formation {
  uuid: string;
  id?: number;
  employeId: number;
  titre: string;
  organisme?: string;
  dateDebut?: string; // ISO date
  dateFin?: string;
  cout?: number;
  devise?: string;
  statutFormation?: StatutFormation;
  certificatUrl?: string;
  commentaire?: string;
  
  // Multi-tenant
  organisationId: number;
  hotelId?: number;
  
  // Audit
  dateCreation?: string;
  dateModification?: string;
  creePar?: string;
  modifiePar?: string;
}

export type StatutFormation = 
  | 'PLANIFIEE' 
  | 'EN_COURS' 
  | 'TERMINEE' 
  | 'ANNULEE' 
  | 'REPORTEE';

export interface CreateFormationRequest {
  employeId: number;
  titre: string;
  organisme?: string;
  dateDebut?: string;
  dateFin?: string;
  cout?: number;
  devise?: string;
  statutFormation?: StatutFormation;
  [key: string]: any;
}

export interface UpdateFormationRequest {
  titre?: string;
  organisme?: string;
  dateDebut?: string;
  dateFin?: string;
  cout?: number;
  statutFormation?: StatutFormation;
  [key: string]: any;
}
