import { Component } from '@angular/core';
import { ComingSoonComponent } from '@shared/components/coming-soon/coming-soon.component';

@Component({
  selector: 'app-documents',
  standalone: true,
  imports: [ComingSoonComponent],
  template: `
    <app-coming-soon
      title="Documents"
      subtitle="Bibliothèque de documents générés (factures PDF, exports…)."
      icon="description"
    />
  `,
})
export class DocumentsComponent {}
