import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ConformiteResult {
  conforme: boolean;
  score: number;
  verifications: any[];
  nonConformites: any[];
}

export interface ActionPlan {
  actions: any[];
  priorites: any[];
}

@Injectable({
  providedIn: 'root'
})
export class ConformiteService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/conformite';

  constructor(private http: HttpClient) {}

  verify(employeUuid: string): Observable<ConformiteResult> {
    return this.http.get<ConformiteResult>(`${this.apiUrl}/employe/${employeUuid}/verifier`);
  }

  getActionPlan(employeUuid: string): Observable<ActionPlan> {
    return this.http.get<ActionPlan>(`${this.apiUrl}/employe/${employeUuid}/plan-action`);
  }
}
