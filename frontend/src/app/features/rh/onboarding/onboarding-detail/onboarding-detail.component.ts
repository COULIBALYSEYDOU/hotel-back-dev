import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-onboarding-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail onboarding"
      subtitle="Parcours d'intégration."
      icon="task_alt"
    />
  `,
})
export class OnboardingDetailComponent {}
