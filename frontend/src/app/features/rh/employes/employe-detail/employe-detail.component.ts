import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-employe-detail',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Détail employé"
      subtitle="Fiche individuelle complète."
      icon="person"
    />
  `,
})
export class EmployeDetailComponent {}
