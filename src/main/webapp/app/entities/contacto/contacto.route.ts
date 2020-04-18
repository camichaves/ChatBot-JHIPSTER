import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContacto, Contacto } from 'app/shared/model/contacto.model';
import { ContactoService } from './contacto.service';
import { ContactoComponent } from './contacto.component';
import { ContactoDetailComponent } from './contacto-detail.component';
import { ContactoUpdateComponent } from './contacto-update.component';

@Injectable({ providedIn: 'root' })
export class ContactoResolve implements Resolve<IContacto> {
  constructor(private service: ContactoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContacto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contacto: HttpResponse<Contacto>) => {
          if (contacto.body) {
            return of(contacto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contacto());
  }
}

export const contactoRoute: Routes = [
  {
    path: '',
    component: ContactoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.contacto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContactoDetailComponent,
    resolve: {
      contacto: ContactoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.contacto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContactoUpdateComponent,
    resolve: {
      contacto: ContactoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.contacto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContactoUpdateComponent,
    resolve: {
      contacto: ContactoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'edgarBotApp.contacto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
