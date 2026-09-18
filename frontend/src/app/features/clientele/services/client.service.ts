import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Client, CreateClientRequest, UpdateClientRequest } from '@core/models/client.model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private readonly apiUrl = 'http://localhost:8098/api/clientele/client/clients';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Client>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Client>>(this.apiUrl, { params: httpParams });
  }

  getById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/${id}`);
  }

  getByEmail(email: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/email/${email}`);
  }

  getBySegment(segment: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/segment/${segment}`);
  }

  getByStatut(statut: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/statut/${statut}`);
  }

  create(dto: CreateClientRequest): Observable<Client> {
    return this.http.post<Client>(this.apiUrl, dto);
  }

  update(id: number, dto: UpdateClientRequest): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
