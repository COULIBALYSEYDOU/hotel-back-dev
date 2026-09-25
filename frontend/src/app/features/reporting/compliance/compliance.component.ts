import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-compliance',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Conformité GDPR"
      subtitle="Registre des traitements, consentements, retention."
      icon="shield"
    />
  `,
})
export class ComplianceComponent {}
