import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agente',
        loadChildren: () => import('./agente/agente.module').then(m => m.EdgarBotAgenteModule)
      },
      {
        path: 'conversacion',
        loadChildren: () => import('./conversacion/conversacion.module').then(m => m.EdgarBotConversacionModule)
      },
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.EdgarBotChatModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.EdgarBotProductoModule)
      },
      {
        path: 'orden',
        loadChildren: () => import('./orden/orden.module').then(m => m.EdgarBotOrdenModule)
      },
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.EdgarBotItemModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.EdgarBotClienteModule)
      },
      {
        path: 'contacto',
        loadChildren: () => import('./contacto/contacto.module').then(m => m.EdgarBotContactoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EdgarBotEntityModule {}
