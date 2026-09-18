import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { TempsTravail, CreateTempsTravailRequest, UpdateTempsTravailRequest } from '@core/models/temps-travail.model';

@Injectable({
  providedIn: 'root'
})
export class TempsTravailService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/temps-travail';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<TempsTravail>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<TempsTravail>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<TempsTravail> {
    return this.http.get<TempsTravail>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<TempsTravail[]> {
    return this.http.get<TempsTravail[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  getByDate(date: string): Observable<TempsTravail[]> {
    return this.http.get<TempsTravail[]>(`${this.apiUrl}/date/${date}`);
  }

  create(dto: CreateTempsTravailRequest): Observable<TempsTravail> {
    return this.http.post<TempsTravail>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateTempsTravailRequest): Observable<TempsTravail> {
    return this.http.put<TempsTravail>(`${this.apiUrl}/${uuid}`, dto);
  }

  validate(uuid: string, validateurId: number): Observable<TempsTravail> {
    return this.http.post<TempsTravail>(`${this.apiUrl}/${uuid}/validate`, { validateurId });
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
