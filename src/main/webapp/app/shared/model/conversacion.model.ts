import { Moment } from 'moment';
import { IChat } from 'app/shared/model/chat.model';
import { IAgente } from 'app/shared/model/agente.model';

export interface IConversacion {
  id?: number;
  cliente?: string;
  sessionDF?: string;
  authorWA?: string;
  inicio?: Moment;
  ultActCli?: Moment;
  fin?: Moment;
  chats?: IChat[];
  agente?: IAgente;
}

export class Conversacion implements IConversacion {
  constructor(
    public id?: number,
    public cliente?: string,
    public sessionDF?: string,
    public authorWA?: string,
    public inicio?: Moment,
    public ultActCli?: Moment,
    public fin?: Moment,
    public chats?: IChat[],
    public agente?: IAgente
  ) {}
}
