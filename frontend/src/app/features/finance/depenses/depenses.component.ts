import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-depenses',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Dépenses"
      subtitle="Achats, dépenses fournisseurs et validation."
      icon="shopping_cart"
    />
  `,
})
export class DepensesComponent {}
