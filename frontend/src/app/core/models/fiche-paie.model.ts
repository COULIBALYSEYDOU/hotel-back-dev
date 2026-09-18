/**
 * Modèle Fiche de Paie - Module RH
 */
export interface FichePaie {
  uuid: string;
  id?: number;
  employeId: number;
  mois: number; // 1-12
  annee: number;
  salaireBrut?: number;
  cotisationPatronale?: number;
  cotisationSalariale?: number;
  impot?: number;
  netAPayer?: number;
  statutPaie?: StatutPaie;
  datePaiement?: string; // ISO date
  modePaiement?: ModePaiement;
  referencePaiement?: string;
  
  // Multi-tenant
  organisationId: number;
  hotelId?: number;
  
  // Audit
  dateCreation?: string;
  dateModification?: string;
  creePar?: string;
  modifiePar?: string;
}

export type StatutPaie = 
  | 'BROUILLON' 
  | 'VALIDE' 
  | 'PAYEE' 
  | 'ANNULEE';

export type ModePaiement = 
  | 'VIREMENT' 
  | 'CHEQUE' 
  | 'ESPECES' 
  | 'CARTE' 
  | 'MOBILE_MONEY';

export interface CreateFichePaieRequest {
  employeId: number;
  mois: number;
  annee: number;
  salaireBrut?: number;
  cotisationPatronale?: number;
  cotisationSalariale?: number;
  impot?: number;
  netAPayer?: number;
  [key: string]: any;
}

export interface UpdateFichePaieRequest {
  salaireBrut?: number;
  cotisationPatronale?: number;
  cotisationSalariale?: number;
  impot?: number;
  netAPayer?: number;
  statutPaie?: StatutPaie;
  [key: string]: any;
}
