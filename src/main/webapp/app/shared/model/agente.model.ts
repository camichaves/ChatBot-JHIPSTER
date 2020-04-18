export interface IAgente {
  id?: number;
  authorWA?: string;
  nombre?: string;
  disponible?: boolean;
  horario?: string;
}

export class Agente implements IAgente {
  constructor(public id?: number, public authorWA?: string, public nombre?: string, public disponible?: boolean, public horario?: string) {
    this.disponible = this.disponible || false;
  }
}
