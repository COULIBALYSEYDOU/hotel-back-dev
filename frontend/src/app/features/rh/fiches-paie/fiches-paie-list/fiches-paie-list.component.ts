import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-fiches-paie-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Fiches de paie"
      subtitle="Génération et historique des bulletins."
      icon="payments"
    />
  `,
})
export class FichesPaieListComponent {}
