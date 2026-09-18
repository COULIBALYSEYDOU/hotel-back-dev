export interface Competence {
  uuid: string;
  id?: number;
  employeId: number;
  nomCompetence: string;
  typeCompetence: 'TECHNIQUE' | 'LINGUISTIQUE' | 'COMPORTEMENTALE' | 'CERTIFICATION';
  niveau: 'DEBUTANT' | 'INTERMEDIAIRE' | 'AVANCE' | 'EXPERT' | 'MAITRE';
  score?: number;
  statutValidation?: 'EN_ATTENTE' | 'VALIDE' | 'REFUSE' | 'EXPIRE';
  dateAcquisition?: string;
  dateExpiration?: string;
  organismeCertification?: string;
  numeroCertification?: string;
  description?: string;
  langue?: string;
  niveauLinguistique?: 'A1' | 'A2' | 'B1' | 'B2' | 'C1' | 'C2';
  obligatoire?: boolean;
  renouvelable?: boolean;
  dureeValiditeMois?: number;
  organisationId: number;
  dateCreation?: string;
}

export interface CreateCompetenceRequest {
  employeId: number;
  nomCompetence: string;
  typeCompetence: string;
  niveau: string;
  score?: number;
  [key: string]: any;
}

export interface UpdateCompetenceRequest {
  nomCompetence?: string;
  niveau?: string;
  score?: number;
  [key: string]: any;
}
