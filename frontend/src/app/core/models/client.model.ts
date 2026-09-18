export interface Client {
  id: number;
  uuid?: string;
  nom: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  dateNaissance?: string;
  civilite?: 'M' | 'MME' | 'MLLE';
  segment?: SegmentClient;
  statut?: StatutClient;
  typeClient?: TypeClient;
  risqueChurn?: RisqueChurn;
  organisationId: number;
  dateCreation?: string;
}

export type SegmentClient = 'VIP' | 'ENTREPRISE' | 'LOISIR' | 'FIDELITE' | 'OCCASIONNEL';
export type StatutClient = 'ACTIF' | 'INACTIF' | 'BLOQUE' | 'ARCHIVE';
export type TypeClient = 'PARTICULIER' | 'ENTREPRISE' | 'GROUPE' | 'AGENCE';
export type RisqueChurn = 'FAIBLE' | 'MOYEN' | 'ELEVE' | 'CRITIQUE';

export interface CreateClientRequest {
  nom: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  segment?: SegmentClient;
  [key: string]: any;
}

export interface UpdateClientRequest {
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  segment?: SegmentClient;
  [key: string]: any;
}
