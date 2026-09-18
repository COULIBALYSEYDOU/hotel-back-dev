export interface Absence {
  uuid: string;
  id?: number;
  employeId: number;
  typeAbsence: TypeAbsence;
  dateDebut: string;
  dateFin: string;
  nombreJours?: number;
  statutAbsence?: StatutAbsence;
  motif?: string;
  justification?: string;
  justifiee?: boolean;
  arretMedical?: boolean;
  medecinNom?: string;
  numeroArret?: string;
  dateArret?: string;
  dateReprise?: string;
  accidentTravail?: boolean;
  descriptionAccident?: string;
  dateAccident?: string;
  lieuAccident?: string;
  validePar?: number;
  dateValidation?: string;
  organisationId: number;
  dateCreation?: string;
}

export type TypeAbsence = 
  | 'MALADIE' 
  | 'ARRET_MEDICAL' 
  | 'ACCIDENT_TRAVAIL' 
  | 'ABSENCE_NON_JUSTIFIEE' 
  | 'AUTRE';

export type StatutAbsence = 
  | 'EN_ATTENTE' 
  | 'VALIDEE' 
  | 'REJETEE' 
  | 'EN_COURS' 
  | 'TERMINEE';

export interface CreateAbsenceRequest {
  employeId: number;
  typeAbsence: TypeAbsence;
  dateDebut: string;
  dateFin: string;
  motif?: string;
  justification?: string;
  [key: string]: any;
}

export interface UpdateAbsenceRequest {
  dateFin?: string;
  motif?: string;
  justification?: string;
  [key: string]: any;
}
