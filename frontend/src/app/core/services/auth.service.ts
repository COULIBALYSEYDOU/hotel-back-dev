import { Injectable, signal, computed } from '@angular/core';
import { Observable, of } from 'rxjs';

/**
 * Service d'authentification
 * Pour l'instant, gestion simple avec localStorage
 * À étendre avec JWT/OAuth selon les besoins
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly _isAuthenticated = signal<boolean>(false);
  private readonly _currentUser = signal<string | null>(null);

  readonly isAuthenticated = computed(() => this._isAuthenticated());
  readonly currentUsername = computed(() => this._currentUser());

  constructor() {
    // Vérifier si l'utilisateur est déjà authentifié
    const storedUser = localStorage.getItem('current_user');
    if (storedUser) {
      this._currentUser.set(storedUser);
      this._isAuthenticated.set(true);
    }
  }

  /**
   * Connecte un utilisateur
   */
  login(username: string, password: string): Observable<boolean> {
    // TODO: Implémenter l'appel API réel
    // Pour l'instant, simulation
    this._currentUser.set(username);
    this._isAuthenticated.set(true);
    localStorage.setItem('current_user', username);
    return of(true);
  }

  /**
   * Déconnecte l'utilisateur
   */
  logout(): void {
    this._currentUser.set(null);
    this._isAuthenticated.set(false);
    localStorage.removeItem('current_user');
  }

  /**
   * Retourne le nom d'utilisateur courant
   */
  getCurrentUsername(): string | null {
    return this._currentUser();
  }
}
