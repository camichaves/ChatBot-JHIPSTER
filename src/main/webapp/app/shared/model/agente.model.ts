export interface IAgente {
  id?: number;
  identificador?: string;
  nombre?: string;
  disponible?: boolean;
  horario?: string;
}

export class Agente implements IAgente {
  constructor(
    public id?: number,
    public identificador?: string,
    public nombre?: string,
    public disponible?: boolean,
    public horario?: string
  ) {
    this.disponible = this.disponible || false;
  }
}
