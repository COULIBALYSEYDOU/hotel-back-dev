import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-contrats-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Contrats"
      subtitle="Contrats de travail actifs et historique."
      icon="description"
    />
  `,
})
export class ContratsListComponent {}
