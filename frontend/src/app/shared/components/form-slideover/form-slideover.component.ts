import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export type FieldType = 'text' | 'number' | 'date' | 'datetime-local' | 'select' | 'textarea' | 'email' | 'tel';

export interface FormField {
  key: string;
  label: string;
  type: FieldType;
  placeholder?: string;
  required?: boolean;
  options?: string[]; // pour type 'select'
  colSpan?: 1 | 2; // 1 = demi-largeur, 2 = pleine largeur
  min?: number;
  max?: number;
}

/**
 * Slideover latéral réutilisable pour formulaires de création rapide.
 * Utilisation :
 *   <app-form-slideover
 *     [(open)]="showForm"
 *     title="Nouvelle réservation"
 *     subtitle="Créez un dossier en 30 secondes."
 *     [fields]="fields"
 *     [(model)]="form"
 *     submitLabel="Créer"
 *     (submitted)="onCreate($event)" />
 */
@Component({
  selector: 'app-form-slideover',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div *ngIf="open" class="fixed inset-0 z-50 flex" (click)="close()">
      <div class="flex-1 bg-black/30"></div>
      <aside class="w-full max-w-xl bg-surface-container-lowest h-full shadow-2xl overflow-y-auto"
             (click)="$event.stopPropagation()">
        <header class="flex items-center justify-between p-6 border-b border-outline-variant">
          <div>
            <h2 class="text-headline-md">{{ title }}</h2>
            <p *ngIf="subtitle" class="text-caption text-on-surface-variant">{{ subtitle }}</p>
          </div>
          <button class="btn-ghost !p-2" type="button" (click)="close()">
            <span class="material-symbols-outlined">close</span>
          </button>
        </header>

        <form class="p-6" (ngSubmit)="submit()">
          <div class="grid grid-cols-2 gap-4">
            <div *ngFor="let f of fields"
                 [class.col-span-2]="f.colSpan === 2 || f.type === 'textarea'">
              <label class="form-label">
                {{ f.label }}
                <span *ngIf="f.required" class="text-error">*</span>
              </label>

              <input *ngIf="['text','email','tel','date','datetime-local'].includes(f.type)"
                     [type]="f.type"
                     class="form-input"
                     [placeholder]="f.placeholder || ''"
                     [required]="!!f.required"
                     [(ngModel)]="model[f.key]"
                     [name]="f.key">

              <input *ngIf="f.type === 'number'"
                     type="number"
                     class="form-input"
                     [placeholder]="f.placeholder || ''"
                     [required]="!!f.required"
                     [min]="f.min ?? null"
                     [max]="f.max ?? null"
                     [(ngModel)]="model[f.key]"
                     [name]="f.key">

              <select *ngIf="f.type === 'select'"
                      class="form-input"
                      [required]="!!f.required"
                      [(ngModel)]="model[f.key]"
                      [name]="f.key">
                <option value="" disabled>—</option>
                <option *ngFor="let opt of f.options" [value]="opt">{{ opt }}</option>
              </select>

              <textarea *ngIf="f.type === 'textarea'"
                        rows="3"
                        class="form-input"
                        [placeholder]="f.placeholder || ''"
                        [required]="!!f.required"
                        [(ngModel)]="model[f.key]"
                        [name]="f.key"></textarea>
            </div>
          </div>

          <div class="pt-4 mt-6 flex items-center justify-end gap-2 border-t border-outline-variant -mx-6 -mb-6 px-6 py-4 bg-surface-container-low">
            <button type="button" class="btn-secondary" (click)="close()">Annuler</button>
            <button type="submit" class="btn-primary">
              <span class="material-symbols-outlined text-base">save</span>
              {{ submitLabel }}
            </button>
          </div>
        </form>
      </aside>
    </div>
  `,
})
export class FormSlideoverComponent {
  @Input() open = false;
  @Output() openChange = new EventEmitter<boolean>();

  @Input() title = '';
  @Input() subtitle = '';
  @Input() submitLabel = 'Enregistrer';
  @Input() fields: FormField[] = [];
  @Input() model: Record<string, any> = {};
  @Output() modelChange = new EventEmitter<Record<string, any>>();

  @Output() submitted = new EventEmitter<Record<string, any>>();

  close() {
    this.open = false;
    this.openChange.emit(false);
  }

  submit() {
    this.submitted.emit({ ...this.model });
    this.close();
  }
}
