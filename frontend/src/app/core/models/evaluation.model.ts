/**
 * Modèle Évaluation de Performance - Module RH
 */
export interface Evaluation {
  uuid: string;
  id?: number;
  employeId: number;
  typeEvaluation: TypeEvaluation;
  dateEvaluation?: string; // ISO date
  periodeDebut?: string;
  periodeFin?: string;
  evaluateurId?: number;
  statutEvaluation?: StatutEvaluation;
  
  // Scores
  scoreGlobal?: number; // 0-100
  scoreCompetences?: number;
  scoreObjectifs?: number;
  scoreComportement?: number;
  
  // Objectifs
  objectifsAtteints?: number;
  objectifsNonAtteints?: number;
  objectifsFuturs?: string; // JSON
  
  // Évaluation
  pointsForts?: string;
  pointsAmelioration?: string;
  planAction?: string;
  recommendation?: Recommendation;
  commentairesEvaluateur?: string;
  commentairesEmploye?: string;
  
  // Validation
  validePar?: number;
  dateValidation?: string;
  notesInternes?: string;
  dateProchaineEvaluation?: string;
  
  // Multi-tenant
  organisationId: number;
  hotelId?: number;
  
  // Audit
  dateCreation?: string;
  dateModification?: string;
  creePar?: string;
  modifiePar?: string;
}

export type TypeEvaluation = 
  | 'ANNUEL' 
  | 'SEMESTRIEL' 
  | 'TRIMESTRIEL' 
  | 'PROBATION' 
  | 'PROMOTION';

export type StatutEvaluation = 
  | 'BROUILLON' 
  | 'EN_COURS' 
  | 'COMPLETEE' 
  | 'VALIDEE' 
  | 'ANNULEE';

export type Recommendation = 
  | 'PROMOTION' 
  | 'MAINTIEN' 
  | 'FORMATION' 
  | 'MISE_EN_GARDE' 
  | 'LICENCIEMENT';

export interface CreateEvaluationRequest {
  employeId: number;
  typeEvaluation: TypeEvaluation;
  periodeDebut?: string;
  periodeFin?: string;
  evaluateurId?: number;
  [key: string]: any;
}

export interface UpdateEvaluationRequest {
  scoreCompetences?: number;
  scoreObjectifs?: number;
  scoreComportement?: number;
  pointsForts?: string;
  pointsAmelioration?: string;
  planAction?: string;
  recommendation?: Recommendation;
  commentairesEvaluateur?: string;
  [key: string]: any;
}

export interface CompleteEvaluationRequest extends UpdateEvaluationRequest {
  // Même structure que UpdateEvaluationRequest
}
