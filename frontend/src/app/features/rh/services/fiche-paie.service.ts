import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { FichePaie, CreateFichePaieRequest, UpdateFichePaieRequest } from '@core/models/fiche-paie.model';

@Injectable({
  providedIn: 'root'
})
export class FichePaieService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/fiches-paie';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<FichePaie>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());

    return this.http.get<PageResponse<FichePaie>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<FichePaie> {
    return this.http.get<FichePaie>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<FichePaie[]> {
    return this.http.get<FichePaie[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  getByEmployeAndPeriod(employeId: number, mois: number, annee: number): Observable<FichePaie> {
    return this.http.get<FichePaie>(`${this.apiUrl}/employe/${employeId}/mois/${mois}/annee/${annee}`);
  }

  create(dto: CreateFichePaieRequest): Observable<FichePaie> {
    return this.http.post<FichePaie>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateFichePaieRequest): Observable<FichePaie> {
    return this.http.put<FichePaie>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
