import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-conges-list',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Congés"
      subtitle="Demandes de congés et validation."
      icon="beach_access"
    />
  `,
})
export class CongesListComponent {}
