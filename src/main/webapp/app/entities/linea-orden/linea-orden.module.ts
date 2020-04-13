import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatBotSharedModule } from 'app/shared/shared.module';
import { LineaOrdenComponent } from './linea-orden.component';
import { LineaOrdenDetailComponent } from './linea-orden-detail.component';
import { LineaOrdenUpdateComponent } from './linea-orden-update.component';
import { LineaOrdenDeleteDialogComponent } from './linea-orden-delete-dialog.component';
import { lineaOrdenRoute } from './linea-orden.route';

@NgModule({
  imports: [ChatBotSharedModule, RouterModule.forChild(lineaOrdenRoute)],
  declarations: [LineaOrdenComponent, LineaOrdenDetailComponent, LineaOrdenUpdateComponent, LineaOrdenDeleteDialogComponent],
  entryComponents: [LineaOrdenDeleteDialogComponent]
})
export class ChatBotLineaOrdenModule {}
