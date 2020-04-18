import { IOrden } from 'app/shared/model/orden.model';

export interface IItem {
  id?: number;
  codigo?: string;
  detalle?: string;
  cantidad?: number;
  precio?: number;
  orden?: IOrden;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public codigo?: string,
    public detalle?: string,
    public cantidad?: number,
    public precio?: number,
    public orden?: IOrden
  ) {}
}
