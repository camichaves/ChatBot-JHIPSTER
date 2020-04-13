import { Moment } from 'moment';
import { ILineaOrden } from 'app/shared/model/linea-orden.model';

export interface IOrden {
  id?: number;
  numero?: number;
  cliente?: string;
  fecha?: Moment;
  total?: number;
  lineaOrdens?: ILineaOrden[];
}

export class Orden implements IOrden {
  constructor(
    public id?: number,
    public numero?: number,
    public cliente?: string,
    public fecha?: Moment,
    public total?: number,
    public lineaOrdens?: ILineaOrden[]
  ) {}
}
