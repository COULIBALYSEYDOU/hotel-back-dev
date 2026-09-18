import { Injectable, signal, computed } from '@angular/core';
import { TenantConfig } from '../models/tenant.model';

/**
 * Service de gestion du tenant (multi-tenant)
 * Gère l'organisation et l'hôtel courants
 */
@Injectable({
  providedIn: 'root'
})
export class TenantService {
  // Signals pour l'état du tenant
  private readonly _organisationId = signal<number | null>(null);
  private readonly _hotelId = signal<number | null>(null);
  private readonly _username = signal<string | null>(null);

  // Computed signals
  readonly currentOrganisationId = computed(() => this._organisationId());
  readonly currentHotelId = computed(() => this._hotelId());
  readonly currentUsername = computed(() => this._username());

  /**
   * Initialise le tenant depuis le localStorage ou les valeurs par défaut
   */
  constructor() {
    this.loadFromStorage();
  }

  /**
   * Définit l'organisation courante
   */
  setOrganisationId(organisationId: number): void {
    this._organisationId.set(organisationId);
    this.saveToStorage();
  }

  /**
   * Définit l'hôtel courant (optionnel)
   */
  setHotelId(hotelId: number | null): void {
    this._hotelId.set(hotelId);
    this.saveToStorage();
  }

  /**
   * Définit le nom d'utilisateur courant
   */
  setUsername(username: string | null): void {
    this._username.set(username);
    this.saveToStorage();
  }

  /**
   * Configure le tenant complet
   */
  setTenant(config: TenantConfig): void {
    this._organisationId.set(config.organisationId);
    this._hotelId.set(config.hotelId ?? null);
    this._username.set(config.username ?? null);
    this.saveToStorage();
  }

  /**
   * Vérifie si le tenant est configuré
   */
  isConfigured(): boolean {
    return this._organisationId() !== null;
  }

  /**
   * Réinitialise le tenant
   */
  reset(): void {
    this._organisationId.set(null);
    this._hotelId.set(null);
    this._username.set(null);
    localStorage.removeItem('tenant_config');
  }

  /**
   * Charge la configuration depuis le localStorage
   */
  private loadFromStorage(): void {
    const stored = localStorage.getItem('tenant_config');
    if (stored) {
      try {
        const config: TenantConfig = JSON.parse(stored);
        this._organisationId.set(config.organisationId);
        this._hotelId.set(config.hotelId ?? null);
        this._username.set(config.username ?? null);
      } catch (e) {
        console.error('Erreur lors du chargement du tenant:', e);
        // Valeurs par défaut pour le développement
        this._organisationId.set(1);
        this._username.set('admin');
      }
    } else {
      // Valeurs par défaut pour le développement
      this._organisationId.set(1);
      this._username.set('admin');
    }
  }

  /**
   * Sauvegarde la configuration dans le localStorage
   */
  private saveToStorage(): void {
    const config: TenantConfig = {
      organisationId: this._organisationId()!,
      hotelId: this._hotelId() ?? undefined,
      username: this._username() ?? undefined
    };
    localStorage.setItem('tenant_config', JSON.stringify(config));
  }
}
