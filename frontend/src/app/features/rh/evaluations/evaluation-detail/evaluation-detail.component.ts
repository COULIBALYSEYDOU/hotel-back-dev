import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-evaluation-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail évaluation"
      subtitle="Fiche d'évaluation de performance."
      icon="grade"
    />
  `,
})
export class EvaluationDetailComponent {}
