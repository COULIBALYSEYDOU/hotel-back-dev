import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="visible" class="fixed inset-0 z-50 bg-black/40 flex items-center justify-center p-4">
      <div class="bg-surface-container-lowest rounded-xl shadow-xl max-w-md w-full p-6">
        <h3 *ngIf="title" class="text-headline-sm mb-2">{{ title }}</h3>
        <p class="text-body-md text-on-surface-variant mb-6">{{ message }}</p>
        <div class="flex justify-end gap-2">
          <button class="btn-secondary" (click)="onCancel()">{{ cancelLabel }}</button>
          <button class="btn-primary" (click)="onConfirm()">{{ confirmLabel }}</button>
        </div>
      </div>
    </div>
  `,
})
export class ConfirmDialogComponent {
  @Input() visible = false;
  @Input() title?: string;
  @Input() message = 'Confirmer ?';
  @Input() confirmLabel = 'Confirmer';
  @Input() cancelLabel = 'Annuler';
  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() confirmed = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  onConfirm() {
    this.confirmed.emit();
    this.visibleChange.emit(false);
  }
  onCancel() {
    this.cancelled.emit();
    this.visibleChange.emit(false);
  }
}
