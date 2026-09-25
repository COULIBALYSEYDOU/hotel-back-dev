import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-tarifications',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Tarifications"
      subtitle="Grilles tarifaires et politiques de prix."
      icon="sell"
    />
  `,
})
export class TarificationsComponent {}
