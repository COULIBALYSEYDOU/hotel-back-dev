import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-budgets',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Budgets"
      subtitle="Prévisionnel vs réalisé et scénarios."
      icon="account_balance"
    />
  `,
})
export class BudgetsComponent {}
