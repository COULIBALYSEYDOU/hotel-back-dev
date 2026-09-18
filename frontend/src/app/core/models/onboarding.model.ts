export interface Onboarding {
  uuid: string;
  employeUuid: string;
  statut: 'EN_COURS' | 'COMPLETE' | 'BLOQUE';
  etapeCourante?: string;
  checklist?: ChecklistItem[];
  dateDebut?: string;
  dateFin?: string;
  organisationId: number;
}

export interface ChecklistItem {
  etape: string;
  libelle: string;
  statut: 'PENDING' | 'COMPLETE' | 'BLOCKED';
  dateValidation?: string;
  validePar?: string;
  commentaires?: string;
}

export interface OnboardingDetail {
  employe: any;
  onboarding: Onboarding;
  checklist: ChecklistItem[];
  progression: number; // Pourcentage
}
