import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-conformite-verification',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Conformité RH"
      subtitle="Contrôle de conformité légale."
      icon="gavel"
    />
  `,
})
export class ConformiteVerificationComponent {}
