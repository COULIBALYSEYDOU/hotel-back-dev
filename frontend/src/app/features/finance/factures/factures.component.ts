import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-factures',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Factures"
      subtitle="Gestion des factures OHADA — émission, envoi, relance."
      icon="receipt_long"
    />
  `,
})
export class FacturesComponent {}
