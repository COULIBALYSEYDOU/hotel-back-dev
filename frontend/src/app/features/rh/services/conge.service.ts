import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Conge, CreateCongeRequest, UpdateCongeRequest, ApproveCongeRequest, RejectCongeRequest } from '@core/models/conge.model';

@Injectable({
  providedIn: 'root'
})
export class CongeService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/conges';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Conge>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());

    return this.http.get<PageResponse<Conge>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Conge> {
    return this.http.get<Conge>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<Conge[]> {
    return this.http.get<Conge[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  getByStatut(statut: string): Observable<Conge[]> {
    return this.http.get<Conge[]>(`${this.apiUrl}/statut/${statut}`);
  }

  create(dto: CreateCongeRequest): Observable<Conge> {
    return this.http.post<Conge>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateCongeRequest): Observable<Conge> {
    return this.http.put<Conge>(`${this.apiUrl}/${uuid}`, dto);
  }

  approve(uuid: string, approbateurId: number): Observable<Conge> {
    return this.http.post<Conge>(`${this.apiUrl}/${uuid}/approve`, { approbateurId });
  }

  reject(uuid: string, motif: string): Observable<Conge> {
    return this.http.post<Conge>(`${this.apiUrl}/${uuid}/reject`, { motif });
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
