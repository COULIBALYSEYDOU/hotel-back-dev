import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-temps-travail-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Temps de travail"
      subtitle="Pointages et heures travaillées."
      icon="schedule"
    />
  `,
})
export class TempsTravailListComponent {}
