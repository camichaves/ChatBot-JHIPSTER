import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrden, Orden } from 'app/shared/model/orden.model';
import { OrdenService } from './orden.service';
import { OrdenComponent } from './orden.component';
import { OrdenDetailComponent } from './orden-detail.component';
import { OrdenUpdateComponent } from './orden-update.component';

@Injectable({ providedIn: 'root' })
export class OrdenResolve implements Resolve<IOrden> {
  constructor(private service: OrdenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrden> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orden: HttpResponse<Orden>) => {
          if (orden.body) {
            return of(orden.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Orden());
  }
}

export const ordenRoute: Routes = [
  {
    path: '',
    component: OrdenComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.orden.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrdenDetailComponent,
    resolve: {
      orden: OrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.orden.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrdenUpdateComponent,
    resolve: {
      orden: OrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.orden.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrdenUpdateComponent,
    resolve: {
      orden: OrdenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.orden.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
