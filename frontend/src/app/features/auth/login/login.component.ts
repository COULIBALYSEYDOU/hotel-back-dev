import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@core/services/auth.service';

/**
 * Login Étoile OS — inspiré du design landing de la maquette (côté vert émeraude + form épuré).
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="min-h-screen grid lg:grid-cols-2 bg-surface">

      <!-- Côté gauche : marque + storytelling -->
      <aside class="hidden lg:flex flex-col justify-between p-12 text-on-primary bg-gradient-to-br from-primary via-primary-600 to-on-primary-fixed">
        <div>
          <div class="flex items-center gap-3">
            <div class="w-11 h-11 rounded-lg bg-white/15 flex items-center justify-center backdrop-blur-sm">
              <span class="material-symbols-outlined text-2xl">star</span>
            </div>
            <div>
              <div class="text-headline-md font-bold tracking-tight">Étoile OS</div>
              <div class="text-caption opacity-80">Plateforme SaaS hôtelière</div>
            </div>
          </div>
        </div>

        <div class="space-y-6">
          <h1 class="text-display-lg font-bold leading-tight">
            La gestion hôtelière,<br/>
            <span class="text-primary-fixed">réinventée pour l'Afrique.</span>
          </h1>
          <p class="text-body-lg opacity-90 max-w-md">
            Réservations, planning, finances, RH — tout votre PMS dans une seule
            interface pensée pour les équipes de terrain.
          </p>
          <ul class="space-y-3 text-body-md">
            <li class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary-fixed">check_circle</span>
              Multi-établissements & multi-devises
            </li>
            <li class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary-fixed">check_circle</span>
              Wave, Orange Money & cartes bancaires
            </li>
            <li class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary-fixed">check_circle</span>
              Conforme OHADA & RGPD
            </li>
          </ul>
        </div>

        <div class="text-caption opacity-70">
          © 2026 Étoile OS · Made in Abidjan
        </div>
      </aside>

      <!-- Côté droit : formulaire -->
      <div class="flex items-center justify-center p-6 lg:p-12">
        <div class="w-full max-w-md">

          <!-- Header mobile -->
          <div class="lg:hidden flex items-center gap-3 mb-8">
            <div class="w-10 h-10 rounded-lg bg-primary flex items-center justify-center text-on-primary">
              <span class="material-symbols-outlined">star</span>
            </div>
            <div>
              <div class="text-headline-md text-primary font-bold">Étoile OS</div>
              <div class="text-caption text-on-surface-variant">Plateforme SaaS hôtelière</div>
            </div>
          </div>

          <h2 class="text-headline-lg text-on-surface">Connexion</h2>
          <p class="text-body-md text-on-surface-variant mt-1 mb-8">
            Bienvenue. Connectez-vous pour accéder à votre tableau de bord.
          </p>

          <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="space-y-4">
            <div>
              <label class="form-label" for="username">Nom d'utilisateur</label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">person</span>
                <input id="username" type="text" formControlName="username" autocomplete="username"
                       placeholder="admin"
                       class="form-input pl-10">
              </div>
            </div>

            <div>
              <div class="flex items-center justify-between mb-1">
                <label class="form-label mb-0" for="password">Mot de passe</label>
                <a class="text-label-sm text-primary hover:underline cursor-pointer">Mot de passe oublié ?</a>
              </div>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">lock</span>
                <input id="password" [type]="showPass() ? 'text' : 'password'"
                       formControlName="password" autocomplete="current-password"
                       placeholder="••••••••"
                       class="form-input pl-10 pr-10">
                <button type="button" (click)="showPass.set(!showPass())"
                        class="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface">
                  <span class="material-symbols-outlined text-lg">
                    {{ showPass() ? 'visibility_off' : 'visibility' }}
                  </span>
                </button>
              </div>
            </div>

            <div *ngIf="error()" class="chip chip-error w-full justify-start">
              <span class="material-symbols-outlined text-base">error</span>
              {{ error() }}
            </div>

            <button type="submit" [disabled]="loginForm.invalid || loading()"
                    class="btn-primary w-full justify-center py-2.5">
              <span *ngIf="!loading()" class="material-symbols-outlined text-base">login</span>
              <span *ngIf="loading()" class="material-symbols-outlined animate-spin text-base">progress_activity</span>
              {{ loading() ? 'Connexion...' : 'Se connecter' }}
            </button>
          </form>

          <div class="mt-8 pt-6 border-t border-outline-variant text-center text-caption text-on-surface-variant">
            Nouveau ? <a class="text-primary hover:underline cursor-pointer">Créer un compte administrateur</a>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class LoginComponent {
  loginForm: FormGroup;
  showPass = signal(false);
  loading = signal(false);
  error = signal<string | null>(null);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.loginForm = this.fb.group({
      username: ['admin', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;
    this.loading.set(true);
    this.error.set(null);
    const { username, password } = this.loginForm.value;
    this.authService.login(username, password).subscribe({
      next: (ok) => {
        this.loading.set(false);
        if (ok) this.router.navigate(['/dashboard']);
        else this.error.set('Identifiants invalides.');
      },
      error: (e) => {
        this.loading.set(false);
        this.error.set(e?.error?.message || 'Erreur de connexion.');
      },
    });
  }
}
