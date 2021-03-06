import { Moment } from 'moment';
import { IConversacion } from 'app/shared/model/conversacion.model';

export interface IChat {
  id?: number;
  authorWA?: string;
  mensaje?: string;
  fecha?: Moment;
  archivo?: string;
  conversacion?: IConversacion;
}

export class Chat implements IChat {
  constructor(
    public id?: number,
    public authorWA?: string,
    public mensaje?: string,
    public fecha?: Moment,
    public archivo?: string,
    public conversacion?: IConversacion
  ) {}
}
