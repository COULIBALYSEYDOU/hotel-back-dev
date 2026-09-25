import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-conge-form',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Demande de congé"
      subtitle="Formulaire de demande."
      icon="edit_note"
    />
  `,
})
export class CongeFormComponent {}
