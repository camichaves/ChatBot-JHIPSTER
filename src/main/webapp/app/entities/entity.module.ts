import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agente',
        loadChildren: () => import('./agente/agente.module').then(m => m.ChatBotAgenteModule)
      },
      {
        path: 'conversacion',
        loadChildren: () => import('./conversacion/conversacion.module').then(m => m.ChatBotConversacionModule)
      },
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.ChatBotChatModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.ChatBotProductoModule)
      },
      {
        path: 'orden',
        loadChildren: () => import('./orden/orden.module').then(m => m.ChatBotOrdenModule)
      },
      {
        path: 'linea-orden',
        loadChildren: () => import('./linea-orden/linea-orden.module').then(m => m.ChatBotLineaOrdenModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ChatBotEntityModule {}
