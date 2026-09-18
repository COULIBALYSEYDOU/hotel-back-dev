import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TenantService } from '../services/tenant.service';
import { AuthService } from '../services/auth.service';

/**
 * Intercepteur HTTP qui injecte automatiquement les headers multi-tenant
 * sur toutes les requêtes vers l'API
 */
export const apiHeadersInterceptor: HttpInterceptorFn = (req, next) => {
  const tenantService = inject(TenantService);
  const authService = inject(AuthService);

  // Vérifier si c'est une requête vers notre API
  if (req.url.startsWith('/api/v1') || req.url.startsWith('http://localhost:8098/api/v1')) {
    // Cloner la requête et ajouter les headers
    const clonedReq = req.clone({
      setHeaders: {
        'X-Organisation-Id': String(tenantService.currentOrganisationId() ?? '1'),
        ...(tenantService.currentHotelId() && {
          'X-Hotel-Id': String(tenantService.currentHotelId()!)
        }),
        ...(authService.currentUsername() && {
          'X-Username': authService.currentUsername()!
        })
      }
    });

    return next(clonedReq);
  }

  return next(req);
};
