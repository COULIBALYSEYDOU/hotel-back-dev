import { TypeContrat } from './employe.model';

export interface ContratTravail {
  uuid: string;
  id?: number;
  employeId: number;
  typeContrat: TypeContrat;
  dateDebut?: string;
  dateFin?: string;
  statutContrat?: StatutContrat;
  poste?: string;
  departement?: string;
  salaireMensuel?: number;
  heuresSemaine?: number;
  finPeriodeEssai?: string;
  periodicitePaie?: string;
  motifFin?: string;
  renouvelable?: boolean;
  organisationId: number;
  dateCreation?: string;
}

export type StatutContrat = 
  | 'EN_REDACTION' 
  | 'EN_ATTENTE_SIGNATURE' 
  | 'ACTIF' 
  | 'PERIODE_ESSAI' 
  | 'SUSPENDU' 
  | 'EN_RENOUVELLEMENT' 
  | 'RENOUVELE' 
  | 'RESILIE' 
  | 'TERMINE' 
  | 'ANNULE';

export interface CreateContratTravailRequest {
  employeId: number;
  typeContrat: TypeContrat;
  dateDebut: string;
  dateFin?: string;
  poste?: string;
  salaireMensuel?: number;
  [key: string]: any;
}

export interface UpdateContratTravailRequest {
  dateFin?: string;
  statutContrat?: StatutContrat;
  salaireMensuel?: number;
  [key: string]: any;
}
