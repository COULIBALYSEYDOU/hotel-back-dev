import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-formation-form',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Nouvelle formation"
      subtitle="Créer une session de formation."
      icon="edit_note"
    />
  `,
})
export class FormationFormComponent {}
