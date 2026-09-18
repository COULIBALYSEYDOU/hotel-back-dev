export interface Facture {
  uuid: string;
  id?: number;
  code: string;
  reservationId?: number;
  montantTTC: number;
  devise: string;
  statut: StatutFacture;
  modePaiement?: ModePaiement;
  hotelId: number;
  dateEmission: string;
  datePaiement?: string;
  organisationId: number;
  dateCreation?: string;
}

export type StatutFacture = 'PAYÉ' | 'EN_ATTENTE' | 'ANNULÉ' | 'IMPAYÉ' | 'PARTIELLEMENT_PAYÉ';
export type ModePaiement = 'CARTE' | 'MOBILE_MONEY' | 'VIREMENT' | 'ESPECES' | 'CHEQUE';

export interface CreateFactureRequest {
  reservationId?: number;
  montantTTC: number;
  devise?: string;
  modePaiement?: ModePaiement;
  hotelId: number;
  [key: string]: any;
}

export interface UpdateFactureRequest {
  statut?: StatutFacture;
  modePaiement?: ModePaiement;
  datePaiement?: string;
  [key: string]: any;
}
