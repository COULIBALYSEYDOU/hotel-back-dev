/**
 * Modèles pour la gestion multi-tenant
 */
export interface TenantConfig {
  organisationId: number;
  hotelId?: number;
  username?: string;
}

export interface Etablissement {
  uuid: string;
  code: string;
  nom: string;
  adresse: string;
  ville: string;
  pays: PaysCode;
  devise: 'XOF' | 'GHS' | 'EUR' | 'USD';
  fuseau: string;
  tva: number;
  etoiles: 3 | 4 | 5;
  nbChambres: number;
  nbEmployes: number;
  gerantPrincipalId: string;
  statut: 'ACTIF' | 'INACTIF' | 'EN_CONFIGURATION';
}

export type PaysCode = 
  | 'CI' 
  | 'SN' 
  | 'GH' 
  | 'FRA' 
  | 'CMR' 
  | 'ML' 
  | 'BF' 
  | 'TG' 
  | 'BJ';
