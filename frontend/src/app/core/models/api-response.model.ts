/**
 * Modèle de réponse paginée générique de l'API Spring Boot
 */
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}

/**
 * Réponse API standard avec success/message/data
 */
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp?: string;
}

/**
 * Paramètres de pagination
 */
export interface PaginationParams {
  page: number;
  size: number;
}

/**
 * Paramètres de tri
 */
export interface SortParams {
  field: string;
  direction: 'asc' | 'desc';
}
