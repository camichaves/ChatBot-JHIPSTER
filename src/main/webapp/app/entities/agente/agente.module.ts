import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatBotSharedModule } from 'app/shared/shared.module';
import { AgenteComponent } from './agente.component';
import { AgenteDetailComponent } from './agente-detail.component';
import { AgenteUpdateComponent } from './agente-update.component';
import { AgenteDeleteDialogComponent } from './agente-delete-dialog.component';
import { agenteRoute } from './agente.route';

@NgModule({
  imports: [ChatBotSharedModule, RouterModule.forChild(agenteRoute)],
  declarations: [AgenteComponent, AgenteDetailComponent, AgenteUpdateComponent, AgenteDeleteDialogComponent],
  entryComponents: [AgenteDeleteDialogComponent]
})
export class ChatBotAgenteModule {}
