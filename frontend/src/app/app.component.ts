import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

/**
 * Composant racine — sert uniquement de point d'entrée pour le router.
 * Le layout (sidebar + topbar) est appliqué par MainLayoutComponent
 * en tant que route parente (voir app.routes.ts).
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`,
})
export class AppComponent {
  title = 'Étoile OS';
}
