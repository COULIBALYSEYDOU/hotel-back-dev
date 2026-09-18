import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Facture, CreateFactureRequest, UpdateFactureRequest } from '@core/models/facture.model';

@Injectable({
  providedIn: 'root'
})
export class FactureService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/finances/factures';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Facture>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Facture>>(this.apiUrl, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Facture> {
    return this.http.get<Facture>(`${this.apiUrl}/${uuid}`);
  }

  getByStatut(statut: string): Observable<Facture[]> {
    return this.http.get<Facture[]>(`${this.apiUrl}/statut/${statut}`);
  }

  getImpayes(): Observable<Facture[]> {
    return this.http.get<Facture[]>(`${this.apiUrl}/impayes`);
  }

  create(dto: CreateFactureRequest): Observable<Facture> {
    return this.http.post<Facture>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateFactureRequest): Observable<Facture> {
    return this.http.put<Facture>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
