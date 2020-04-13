import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatBotSharedModule } from 'app/shared/shared.module';
import { OrdenComponent } from './orden.component';
import { OrdenDetailComponent } from './orden-detail.component';
import { OrdenUpdateComponent } from './orden-update.component';
import { OrdenDeleteDialogComponent } from './orden-delete-dialog.component';
import { ordenRoute } from './orden.route';

@NgModule({
  imports: [ChatBotSharedModule, RouterModule.forChild(ordenRoute)],
  declarations: [OrdenComponent, OrdenDetailComponent, OrdenUpdateComponent, OrdenDeleteDialogComponent],
  entryComponents: [OrdenDeleteDialogComponent]
})
export class ChatBotOrdenModule {}
