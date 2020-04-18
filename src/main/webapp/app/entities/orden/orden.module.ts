import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdgarBotSharedModule } from 'app/shared/shared.module';
import { OrdenComponent } from './orden.component';
import { OrdenDetailComponent } from './orden-detail.component';
import { OrdenUpdateComponent } from './orden-update.component';
import { OrdenDeleteDialogComponent } from './orden-delete-dialog.component';
import { ordenRoute } from './orden.route';

@NgModule({
  imports: [EdgarBotSharedModule, RouterModule.forChild(ordenRoute)],
  declarations: [OrdenComponent, OrdenDetailComponent, OrdenUpdateComponent, OrdenDeleteDialogComponent],
  entryComponents: [OrdenDeleteDialogComponent]
})
export class EdgarBotOrdenModule {}
