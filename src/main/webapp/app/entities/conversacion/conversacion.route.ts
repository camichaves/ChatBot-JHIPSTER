import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConversacion, Conversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';
import { ConversacionComponent } from './conversacion.component';
import { ConversacionDetailComponent } from './conversacion-detail.component';
import { ConversacionUpdateComponent } from './conversacion-update.component';

@Injectable({ providedIn: 'root' })
export class ConversacionResolve implements Resolve<IConversacion> {
  constructor(private service: ConversacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConversacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conversacion: HttpResponse<Conversacion>) => {
          if (conversacion.body) {
            return of(conversacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Conversacion());
  }
}

export const conversacionRoute: Routes = [
  {
    path: '',
    component: ConversacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConversacionDetailComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConversacionUpdateComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConversacionUpdateComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
