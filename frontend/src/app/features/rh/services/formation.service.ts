import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Formation, CreateFormationRequest, UpdateFormationRequest } from '@core/models/formation.model';

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/formations';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Formation>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());

    return this.http.get<PageResponse<Formation>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Formation> {
    return this.http.get<Formation>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<Formation[]> {
    return this.http.get<Formation[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  getByStatut(statut: string): Observable<Formation[]> {
    return this.http.get<Formation[]>(`${this.apiUrl}/statut/${statut}`);
  }

  create(dto: CreateFormationRequest): Observable<Formation> {
    return this.http.post<Formation>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateFormationRequest): Observable<Formation> {
    return this.http.put<Formation>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
