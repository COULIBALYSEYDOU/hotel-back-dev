import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-formation-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail formation"
      subtitle="Session détaillée."
      icon="school"
    />
  `,
})
export class FormationDetailComponent {}
