import { Moment } from 'moment';
import { IAgente } from 'app/shared/model/agente.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IConversacion {
  id?: number;
  sessionDF?: string;
  authorWA?: string;
  inicio?: Moment;
  ultActCli?: Moment;
  fin?: Moment;
  status?: boolean;
  agente?: IAgente;
  cliente?: ICliente;
}

export class Conversacion implements IConversacion {
  constructor(
    public id?: number,
    public sessionDF?: string,
    public authorWA?: string,
    public inicio?: Moment,
    public ultActCli?: Moment,
    public fin?: Moment,
    public status?: boolean,
    public agente?: IAgente,
    public cliente?: ICliente
  ) {
    this.status = this.status || false;
  }
}
