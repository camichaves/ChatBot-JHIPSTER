import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdgarBotSharedModule } from 'app/shared/shared.module';
import { ConversacionComponent } from './conversacion.component';
import { ConversacionDetailComponent } from './conversacion-detail.component';
import { ConversacionUpdateComponent } from './conversacion-update.component';
import { ConversacionDeleteDialogComponent } from './conversacion-delete-dialog.component';
import { conversacionRoute } from './conversacion.route';

@NgModule({
  imports: [EdgarBotSharedModule, RouterModule.forChild(conversacionRoute)],
  declarations: [ConversacionComponent, ConversacionDetailComponent, ConversacionUpdateComponent, ConversacionDeleteDialogComponent],
  entryComponents: [ConversacionDeleteDialogComponent]
})
export class EdgarBotConversacionModule {}
