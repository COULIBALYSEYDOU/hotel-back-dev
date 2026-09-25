import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-evaluations-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Évaluations"
      subtitle="Campagnes d'évaluation en cours."
      icon="star_rate"
    />
  `,
})
export class EvaluationsListComponent {}
