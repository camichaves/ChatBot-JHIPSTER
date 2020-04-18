import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrden } from 'app/shared/model/orden.model';

type EntityResponseType = HttpResponse<IOrden>;
type EntityArrayResponseType = HttpResponse<IOrden[]>;

@Injectable({ providedIn: 'root' })
export class OrdenService {
  public resourceUrl = SERVER_API_URL + 'api/ordens';

  constructor(protected http: HttpClient) {}

  create(orden: IOrden): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orden);
    return this.http
      .post<IOrden>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orden: IOrden): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orden);
    return this.http
      .put<IOrden>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrden>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrden[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orden: IOrden): IOrden {
    const copy: IOrden = Object.assign({}, orden, {
      fecha: orden.fecha && orden.fecha.isValid() ? orden.fecha.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orden: IOrden) => {
        orden.fecha = orden.fecha ? moment(orden.fecha) : undefined;
      });
    }
    return res;
  }
}
