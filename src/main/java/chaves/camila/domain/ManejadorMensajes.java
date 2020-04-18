package chaves.camila.domain;
import chaves.camila.web.rest.ConversacionResource;
import chaves.camila.service.*;

import java.util.List;

public abstract class  ManejadorMensajes {

    public static Chat getAnswer(Chat chat, ConversacionResource conversaciones){
        System.out.println("Entre en el manejador de mensajes");
        //LOGICA:
        //veo si el mensaje es de la lista de agentes y reviso palabras claves, sino:
        //ver conversacion nueva o que ya existe
        //getall de cnversaciones donde estado = activo
        // reviso si hay alguna conversacion activa con ese remitente
        // si la hay:
        //me fijo si tiene agente, si lo tiene direcciono.
        // Si no tiene agente, tomo el sessionid para mandar a dialogflow
        // sino creo un session id y mando a tensorflow

        List<Conversacion> rta = conversaciones.getAllConversacions();
        // Chat resp = new Chat();
        return null;
    }

}
