import { ICliente } from 'app/shared/model/cliente.model';

export interface IContacto {
  id?: number;
  nombrePersona?: string;
  authorWA?: string;
  cliente?: ICliente;
}

export class Contacto implements IContacto {
  constructor(public id?: number, public nombrePersona?: string, public authorWA?: string, public cliente?: ICliente) {}
}
