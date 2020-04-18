import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdgarBotSharedModule } from 'app/shared/shared.module';
import { ContactoComponent } from './contacto.component';
import { ContactoDetailComponent } from './contacto-detail.component';
import { ContactoUpdateComponent } from './contacto-update.component';
import { ContactoDeleteDialogComponent } from './contacto-delete-dialog.component';
import { contactoRoute } from './contacto.route';

@NgModule({
  imports: [EdgarBotSharedModule, RouterModule.forChild(contactoRoute)],
  declarations: [ContactoComponent, ContactoDetailComponent, ContactoUpdateComponent, ContactoDeleteDialogComponent],
  entryComponents: [ContactoDeleteDialogComponent]
})
export class EdgarBotContactoModule {}
