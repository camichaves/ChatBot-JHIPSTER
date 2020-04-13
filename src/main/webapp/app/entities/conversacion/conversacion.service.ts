import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConversacion } from 'app/shared/model/conversacion.model';

type EntityResponseType = HttpResponse<IConversacion>;
type EntityArrayResponseType = HttpResponse<IConversacion[]>;

@Injectable({ providedIn: 'root' })
export class ConversacionService {
  public resourceUrl = SERVER_API_URL + 'api/conversacions';

  constructor(protected http: HttpClient) {}

  create(conversacion: IConversacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversacion);
    return this.http
      .post<IConversacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conversacion: IConversacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversacion);
    return this.http
      .put<IConversacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConversacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConversacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(conversacion: IConversacion): IConversacion {
    const copy: IConversacion = Object.assign({}, conversacion, {
      inicio: conversacion.inicio && conversacion.inicio.isValid() ? conversacion.inicio.toJSON() : undefined,
      ultActCli: conversacion.ultActCli && conversacion.ultActCli.isValid() ? conversacion.ultActCli.toJSON() : undefined,
      fin: conversacion.fin && conversacion.fin.isValid() ? conversacion.fin.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio ? moment(res.body.inicio) : undefined;
      res.body.ultActCli = res.body.ultActCli ? moment(res.body.ultActCli) : undefined;
      res.body.fin = res.body.fin ? moment(res.body.fin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conversacion: IConversacion) => {
        conversacion.inicio = conversacion.inicio ? moment(conversacion.inicio) : undefined;
        conversacion.ultActCli = conversacion.ultActCli ? moment(conversacion.ultActCli) : undefined;
        conversacion.fin = conversacion.fin ? moment(conversacion.fin) : undefined;
      });
    }
    return res;
  }
}
