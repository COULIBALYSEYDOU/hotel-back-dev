import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OffboardingService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/offboarding';

  constructor(private http: HttpClient) {}

  start(employeUuid: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${employeUuid}/demarrer`, {});
  }

  validateStep(employeUuid: string, etape: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${employeUuid}/etape/${etape}/valider`, {});
  }
}
