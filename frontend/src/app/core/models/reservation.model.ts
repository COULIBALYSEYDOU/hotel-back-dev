export interface Reservation {
  uuid: string;
  id?: number;
  code: string;
  chambre?: string;
  hotelId: number;
  statut: StatutReservation;
  canal: CanalReservation;
  dateArrivee: string;
  dateDepart: string;
  montantTTC: number;
  devise?: string;
  clientId?: number;
  organisationId: number;
  dateCreation?: string;
}

export type StatutReservation = 'CONFIRMÉ' | 'EN_ATTENTE' | 'ANNULÉ' | 'NO_SHOW' | 'CHECK_IN' | 'CHECK_OUT';
export type CanalReservation = 'OTA' | 'DIRECT' | 'AGENCE' | 'TELEPHONE' | 'EMAIL';
export type TypeReservation = 'INDIVIDUELLE' | 'GROUPE' | 'EVENEMENT';

export interface CreateReservationRequest {
  chambre: string;
  hotelId: number;
  canal: CanalReservation;
  dateArrivee: string;
  dateDepart: string;
  montantTTC: number;
  clientId?: number;
  [key: string]: any;
}

export interface UpdateReservationRequest {
  statut?: StatutReservation;
  dateArrivee?: string;
  dateDepart?: string;
  montantTTC?: number;
  [key: string]: any;
}
