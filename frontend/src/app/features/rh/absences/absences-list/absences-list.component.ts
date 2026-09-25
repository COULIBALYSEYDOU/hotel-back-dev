import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-absences-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Absences"
      subtitle="Suivi des absences, arrêts maladie et injustifiés."
      icon="event_busy"
    />
  `,
})
export class AbsencesListComponent {}
