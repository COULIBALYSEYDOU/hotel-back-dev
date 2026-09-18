import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Competence, CreateCompetenceRequest, UpdateCompetenceRequest } from '@core/models/competence.model';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/competences';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Competence>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Competence>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Competence> {
    return this.http.get<Competence>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<Competence[]> {
    return this.http.get<Competence[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  getExpirant(): Observable<Competence[]> {
    return this.http.get<Competence[]>(`${this.apiUrl}/expirant`);
  }

  create(dto: CreateCompetenceRequest): Observable<Competence> {
    return this.http.post<Competence>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateCompetenceRequest): Observable<Competence> {
    return this.http.put<Competence>(`${this.apiUrl}/${uuid}`, dto);
  }

  validate(uuid: string): Observable<Competence> {
    return this.http.post<Competence>(`${this.apiUrl}/${uuid}/valider`, {});
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
