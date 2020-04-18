export interface IProducto {
  id?: number;
  codigo?: string;
  detalle?: string;
  detAdicional?: string;
  precio?: number;
  imagen?: string;
  stock?: number;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public codigo?: string,
    public detalle?: string,
    public detAdicional?: string,
    public precio?: number,
    public imagen?: string,
    public stock?: number
  ) {}
}
