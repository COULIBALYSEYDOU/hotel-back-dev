import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-fiche-paie-form',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Nouvelle fiche de paie"
      subtitle="Formulaire de création."
      icon="edit_note"
    />
  `,
})
export class FichePaieFormComponent {}
