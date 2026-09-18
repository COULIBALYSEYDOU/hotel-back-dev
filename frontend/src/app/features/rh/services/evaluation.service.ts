import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, PaginationParams } from '@core/models/api-response.model';
import { Evaluation, CreateEvaluationRequest, UpdateEvaluationRequest, CompleteEvaluationRequest } from '@core/models/evaluation.model';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {
  private readonly apiUrl = 'http://localhost:8098/api/v1/rh/evaluations';

  constructor(private http: HttpClient) {}

  getAll(params: PaginationParams): Observable<PageResponse<Evaluation>> {
    const httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString());

    return this.http.get<PageResponse<Evaluation>>(`${this.apiUrl}/paginated`, { params: httpParams });
  }

  getByUuid(uuid: string): Observable<Evaluation> {
    return this.http.get<Evaluation>(`${this.apiUrl}/${uuid}`);
  }

  getByEmploye(employeId: number): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}/employe/${employeId}`);
  }

  start(dto: CreateEvaluationRequest): Observable<Evaluation> {
    return this.http.post<Evaluation>(this.apiUrl, dto);
  }

  complete(uuid: string, dto: CompleteEvaluationRequest): Observable<Evaluation> {
    return this.http.put<Evaluation>(`${this.apiUrl}/${uuid}/completer`, dto);
  }

  validate(uuid: string): Observable<Evaluation> {
    return this.http.post<Evaluation>(`${this.apiUrl}/${uuid}/valider`, {});
  }
}
