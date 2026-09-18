import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { OffboardingService } from '../../services/offboarding.service';
import { EmployeService } from '../../services/employe.service';
import { Onboarding, ChecklistItem } from '@core/models/onboarding.model';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';
import { ToastrService } from 'ngx-toastr';
import { CheckCircle, Circle, XCircle } from 'lucide-angular';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-offboarding-list',
  standalone: true,
  imports: [CommonModule, RouterModule, PageHeaderComponent, StatusBadgeComponent, LucideAngularModule, DatePipe],
  template: `
    <app-page-header
      title="Offboarding"
      [breadcrumbs]="[
        { label: 'Accueil', route: '/dashboard' },
        { label: 'RH', route: '/rh' },
        { label: 'Offboarding' }
      ]"
    />

    <div class="bg-white rounded-lg shadow-md p-6">
      <p class="text-gray-500">Liste des offboarding en cours</p>
      <!-- TODO: Implémenter la liste complète -->
    </div>
  `,
  styles: []
})
export class OffboardingListComponent implements OnInit {
  ngOnInit(): void {
    // TODO: Charger les offboardings
  }
}
