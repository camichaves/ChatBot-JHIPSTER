import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILineaOrden } from 'app/shared/model/linea-orden.model';

type EntityResponseType = HttpResponse<ILineaOrden>;
type EntityArrayResponseType = HttpResponse<ILineaOrden[]>;

@Injectable({ providedIn: 'root' })
export class LineaOrdenService {
  public resourceUrl = SERVER_API_URL + 'api/linea-ordens';

  constructor(protected http: HttpClient) {}

  create(lineaOrden: ILineaOrden): Observable<EntityResponseType> {
    return this.http.post<ILineaOrden>(this.resourceUrl, lineaOrden, { observe: 'response' });
  }

  update(lineaOrden: ILineaOrden): Observable<EntityResponseType> {
    return this.http.put<ILineaOrden>(this.resourceUrl, lineaOrden, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILineaOrden>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILineaOrden[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
