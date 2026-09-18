export interface Gerant {
  uuid: string;
  code: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  role: Role;
  statut: 'ACTIF' | 'SUSPENDU' | 'INACTIF';
  hotelIds: string[];
  mfaActif: boolean;
  multiEtablissement: boolean;
  organisationId: number;
  dateCreation?: string;
}

export type Role = 'SUPER_ADMIN' | 'GERANT' | 'SUPERVISEUR' | 'COMPTABLE' | 'RH_MANAGER' | 'RECEPTIONNISTE';

export interface CreateGerantRequest {
  code: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  role: Role;
  hotelIds?: string[];
  [key: string]: any;
}

export interface UpdateGerantRequest {
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  role?: Role;
  statut?: string;
  hotelIds?: string[];
  [key: string]: any;
}
