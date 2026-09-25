import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-competences-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Compétences"
      subtitle="Référentiel des compétences par poste."
      icon="psychology"
    />
  `,
})
export class CompetencesListComponent {}
