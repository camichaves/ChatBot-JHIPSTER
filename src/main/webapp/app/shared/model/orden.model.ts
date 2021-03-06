import { Moment } from 'moment';

export interface IOrden {
  id?: number;
  numero?: number;
  cliente?: string;
  fecha?: Moment;
  total?: number;
}

export class Orden implements IOrden {
  constructor(public id?: number, public numero?: number, public cliente?: string, public fecha?: Moment, public total?: number) {}
}
