import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Recrutement, CreateRecrutementRequest, UpdateRecrutementRequest } from '@core/models/recrutement.model';

@Injectable({
  providedIn: 'root'
})
export class RecrutementService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/recrutements';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Recrutement>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Recrutement>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Recrutement> {
    return this.http.get<Recrutement>(`${this.apiUrl}/${uuid}`);
  }

  getByStatut(statut: string): Observable<Recrutement[]> {
    return this.http.get<Recrutement[]>(`${this.apiUrl}/statut/${statut}`);
  }

  create(dto: CreateRecrutementRequest): Observable<Recrutement> {
    return this.http.post<Recrutement>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateRecrutementRequest): Observable<Recrutement> {
    return this.http.put<Recrutement>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
