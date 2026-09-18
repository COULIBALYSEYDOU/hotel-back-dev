import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Reservation, CreateReservationRequest, UpdateReservationRequest } from '@core/models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/reservations';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Reservation>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    return this.http.get<PageResponse<Reservation>>(this.apiUrl, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Reservation> {
    return this.http.get<Reservation>(`${this.apiUrl}/${uuid}`);
  }

  getByStatut(statut: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/statut/${statut}`);
  }

  create(dto: CreateReservationRequest): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, dto);
  }

  update(uuid: string, dto: UpdateReservationRequest): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${uuid}`, dto);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${uuid}`);
  }
}
