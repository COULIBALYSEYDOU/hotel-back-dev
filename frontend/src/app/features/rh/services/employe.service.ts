import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Employe, CreateEmployeRequest, UpdateEmployeRequest } from '@core/models/employe.model';

/**
 * Service pour la gestion des employés
 * Consomme l'API /api/v1/rh/employes
 */
@Injectable({
  providedIn: 'root'
})
export class EmployeService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/employes';

  constructor(private http: HttpClient) {}

  /**
   * Récupère la liste paginée des employés
   */
  getAll(
    params: PaginationParams,
    departement?: string
  ): Observable<PageResponse<Employe>> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());

    if (departement) {
      httpParams = httpParams.set('departement', departement);
    }

    return this.http.get<PageResponse<Employe>>(`${this.apiUrl}/paginated`, {
      params: httpParams
    });
  }

  /**
   * Récupère un employé par UUID
   */
  getByUuid(uuid: string): Observable<Employe> {
    return this.http.get<Employe>(`${this.apiUrl}/${uuid}`);
  }

  /**
   * Récupère un employé par ID
   */
  getById(id: number): Observable<Employe> {
    return this.http.get<Employe>(`${this.apiUrl}/id/${id}`);
  }

  /**
   * Récupère les employés par département
   */
  getByDepartement(departement: string): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.apiUrl}/departement/${departement}`);
  }

  /**
   * Crée un nouvel employé
   */
  create(dto: CreateEmployeRequest): Observable<Employe> {
    return this.http.post<Employe>(this.apiUrl, dto);
  }

  /**
   * Met à jour un employé
   */
  update(uuid: string, dto: UpdateEmployeRequest): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/${uuid}`, dto);
  }

  /**
   * Active un employé
   */
  activate(uuid: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${uuid}/activate`, {});
  }

  /**
   * Désactive un employé
   */
  deactivate(uuid: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${uuid}/deactivate`, {});
  }

  /**
   * Supprime un employé (soft delete)
   */
  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
