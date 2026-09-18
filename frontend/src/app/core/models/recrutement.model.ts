export interface Recrutement {
  uuid: string;
  id?: number;
  poste: string;
  departement?: string;
  candidatNom: string;
  candidatEmail?: string;
  candidatTelephone?: string;
  source?: string;
  statutCandidature?: StatutCandidature;
  dateCandidature?: string;
  dateEntretien?: string;
  evaluation?: string;
  note?: number;
  organisationId: number;
  dateCreation?: string;
}

export type StatutCandidature = 
  | 'NOUVEAU' 
  | 'EN_ATTENTE' 
  | 'ENTRETIEN_PLANIFIE' 
  | 'ENTRETIEN_EN_COURS' 
  | 'EN_EVALUATION' 
  | 'ACCEPTE' 
  | 'REJETE' 
  | 'RETIRE';

export interface CreateRecrutementRequest {
  poste: string;
  departement?: string;
  candidatNom: string;
  candidatEmail?: string;
  candidatTelephone?: string;
  source?: string;
  [key: string]: any;
}

export interface UpdateRecrutementRequest {
  statutCandidature?: StatutCandidature;
  dateEntretien?: string;
  evaluation?: string;
  note?: number;
  [key: string]: any;
}
