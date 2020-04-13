import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILineaOrden, LineaOrden } from 'app/shared/model/linea-orden.model';
import { LineaOrdenService } from './linea-orden.service';
import { LineaOrdenComponent } from './linea-orden.component';
import { LineaOrdenDetailComponent } from './linea-orden-detail.component';
import { LineaOrdenUpdateComponent } from './linea-orden-update.component';

@Injectable({ providedIn: 'root' })
export class LineaOrdenResolve implements Resolve<ILineaOrden> {
  constructor(private service: LineaOrdenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILineaOrden> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lineaOrden: HttpResponse<LineaOrden>) => {
          if (lineaOrden.body) {
            return of(lineaOrden.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LineaOrden());
  }
}

export const lineaOrdenRoute: Routes = [
  {
    path: '',
    component: LineaOrdenComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LineaOrdens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LineaOrdenDetailComponent,
    resolve: {
      lineaOrden: LineaOrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LineaOrdens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LineaOrdenUpdateComponent,
    resolve: {
      lineaOrden: LineaOrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LineaOrdens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LineaOrdenUpdateComponent,
    resolve: {
      lineaOrden: LineaOrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LineaOrdens'
    },
    canActivate: [UserRouteAccessService]
  }
];
