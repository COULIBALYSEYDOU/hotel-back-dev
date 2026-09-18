import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { ContratTravail, CreateContratTravailRequest, UpdateContratTravailRequest } from '@core/models/contrat.model';

@Injectable({
  providedIn: 'root'
})
export class ContratService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/contrats';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<ContratTravail>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<ContratTravail>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<ContratTravail> {
    return this.http.get<ContratTravail>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<ContratTravail[]> {
    return this.http.get<ContratTravail[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  create(dto: CreateContratTravailRequest): Observable<ContratTravail> {
    return this.http.post<ContratTravail>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateContratTravailRequest): Observable<ContratTravail> {
    return this.http.put<ContratTravail>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
