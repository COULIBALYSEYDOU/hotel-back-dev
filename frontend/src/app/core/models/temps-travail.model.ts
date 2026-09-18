export interface TempsTravail {
  uuid: string;
  id?: number;
  employeId: number;
  dateJour: string;
  heuresNormales?: number;
  heuresSupplementaires?: number;
  typeJour?: 'OUVRE' | 'WEEKEND' | 'FERIE' | 'CONGE';
  statutValidation?: 'EN_ATTENTE' | 'VALIDE' | 'REJETE';
  valide?: boolean;
  validateurId?: number;
  commentaire?: string;
  organisationId: number;
  dateCreation?: string;
}

export interface CreateTempsTravailRequest {
  employeId: number;
  dateJour: string;
  heuresNormales?: number;
  heuresSupplementaires?: number;
  typeJour?: string;
  [key: string]: any;
}

export interface UpdateTempsTravailRequest {
  heuresNormales?: number;
  heuresSupplementaires?: number;
  commentaire?: string;
  [key: string]: any;
}
