import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Placeholder générique "à venir" — utilisé pour les pages non encore converties
 * depuis les maquettes Stitch. Respecte le design system Étoile OS.
 */
@Component({
  selector: 'app-coming-soon',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="max-w-4xl mx-auto">
      <div class="card text-center py-16">
        <div class="mx-auto w-20 h-20 rounded-full bg-primary/10 flex items-center justify-center mb-6">
          <span class="material-symbols-outlined text-primary" style="font-size: 40px;">
            {{ icon }}
          </span>
        </div>
        <h1 class="text-headline-lg text-on-surface mb-2">{{ title }}</h1>
        <p class="text-body-md text-on-surface-variant max-w-xl mx-auto mb-6">
          {{ subtitle }}
        </p>
        <div class="inline-flex items-center gap-2 chip chip-neutral">
          <span class="material-symbols-outlined text-base">construction</span>
          <span>Interface en cours de construction</span>
        </div>
      </div>
    </section>
  `,
})
export class ComingSoonComponent {
  @Input() title = 'Bientôt disponible';
  @Input() subtitle = 'Cette section sera intégrée depuis la maquette Étoile OS.';
  @Input() icon = 'auto_awesome';
}
