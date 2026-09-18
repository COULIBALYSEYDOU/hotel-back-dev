import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Composant badge de statut réutilisable
 * Affiche un badge coloré selon le statut
 */
@Component({
  selector: 'app-status-badge',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span
      [class]="getStatusClasses()"
      class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
    >
      {{ status }}
    </span>
  `,
  styles: []
})
export class StatusBadgeComponent {
  @Input() status: string = '';

  getStatusClasses(): string {
    const statusLower = this.status.toLowerCase();
    
    // Statuts actifs (vert)
    if (['actif', 'confirmé', 'payé', 'approuvé', 'terminé', 'valide'].includes(statusLower)) {
      return 'bg-green-100 text-green-800';
    }
    
    // Statuts en attente (orange)
    if (['en_attente', 'en_validation', 'en_cours', 'brouillon', 'pending'].includes(statusLower)) {
      return 'bg-yellow-100 text-yellow-800';
    }
    
    // Statuts suspendus/inactifs (gris)
    if (['suspendu', 'inactif', 'annulé', 'terminé', 'archivé'].includes(statusLower)) {
      return 'bg-gray-100 text-gray-800';
    }
    
    // Statuts négatifs (rouge)
    if (['rejeté', 'licencié', 'impayé', 'annulé', 'no_show'].includes(statusLower)) {
      return 'bg-red-100 text-red-800';
    }
    
    // Par défaut
    return 'bg-blue-100 text-blue-800';
  }
}
