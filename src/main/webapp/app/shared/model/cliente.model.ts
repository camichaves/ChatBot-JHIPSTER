export interface ICliente {
  id?: number;
  nombreEmpresa?: string;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public nombreEmpresa?: string) {}
}
