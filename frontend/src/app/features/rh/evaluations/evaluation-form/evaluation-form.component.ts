import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-evaluation-form',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Nouvelle évaluation"
      subtitle="Grille d'évaluation."
      icon="edit_note"
    />
  `,
})
export class EvaluationFormComponent {}
