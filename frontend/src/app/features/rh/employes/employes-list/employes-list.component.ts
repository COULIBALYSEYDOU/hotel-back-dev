import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-employes-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Employés"
      subtitle="Fichier du personnel."
      icon="group"
    />
  `,
})
export class EmployesListComponent {}
