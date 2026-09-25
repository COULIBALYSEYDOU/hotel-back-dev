import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-offboarding-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Offboarding"
      subtitle="Processus de départ des collaborateurs."
      icon="logout"
    />
  `,
})
export class OffboardingListComponent {}
