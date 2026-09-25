import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-onboarding-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Onboarding"
      subtitle="Intégration des nouveaux employés."
      icon="task_alt"
    />
  `,
})
export class OnboardingListComponent {}
