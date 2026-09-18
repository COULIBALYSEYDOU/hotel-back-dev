import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Absence, CreateAbsenceRequest, UpdateAbsenceRequest } from '@core/models/absence.model';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/absences';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Absence>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Absence>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Absence> {
    return this.http.get<Absence>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<Absence[]> {
    return this.http.get<Absence[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  create(dto: CreateAbsenceRequest): Observable<Absence> {
    return this.http.post<Absence>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateAbsenceRequest): Observable<Absence> {
    return this.http.put<Absence>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
