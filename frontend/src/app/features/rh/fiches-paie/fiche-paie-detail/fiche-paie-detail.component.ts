import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-fiche-paie-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail fiche de paie"
      subtitle="Bulletin de salaire détaillé."
      icon="receipt_long"
    />
  `,
})
export class FichePaieDetailComponent {}
