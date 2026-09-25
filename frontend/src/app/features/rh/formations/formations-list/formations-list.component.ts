import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-formations-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Formations"
      subtitle="Catalogue et sessions de formation."
      icon="school"
    />
  `,
})
export class FormationsListComponent {}
