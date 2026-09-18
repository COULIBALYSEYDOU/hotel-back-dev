/**
 * Définition d'une colonne pour le composant data-table
 */
export interface ColumnDef<T = any> {
  field: keyof T | string;
  header: string;
  sortable?: boolean;
  filterable?: boolean;
  width?: string;
  align?: 'left' | 'center' | 'right';
  cellRenderer?: (row: T) => string;
  cellTemplate?: string;
}
