import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-employe-form',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Nouvel employé"
      subtitle="Formulaire d'embauche."
      icon="person_add"
    />
  `,
})
export class EmployeFormComponent {}
