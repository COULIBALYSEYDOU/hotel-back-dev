import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-recrutements-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Recrutements"
      subtitle="Candidatures, entretiens et offres d'emploi."
      icon="work"
    />
  `,
})
export class RecrutementsListComponent {}
