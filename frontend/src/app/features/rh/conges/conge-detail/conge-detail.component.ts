import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-conge-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail congé"
      subtitle="Dossier détaillé."
      icon="beach_access"
    />
  `,
})
export class CongeDetailComponent {}
