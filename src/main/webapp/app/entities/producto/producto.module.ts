import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdgarBotSharedModule } from 'app/shared/shared.module';
import { ProductoComponent } from './producto.component';
import { ProductoDetailComponent } from './producto-detail.component';
import { ProductoUpdateComponent } from './producto-update.component';
import { ProductoDeleteDialogComponent } from './producto-delete-dialog.component';
import { productoRoute } from './producto.route';

@NgModule({
  imports: [EdgarBotSharedModule, RouterModule.forChild(productoRoute)],
  declarations: [ProductoComponent, ProductoDetailComponent, ProductoUpdateComponent, ProductoDeleteDialogComponent],
  entryComponents: [ProductoDeleteDialogComponent]
})
export class EdgarBotProductoModule {}
