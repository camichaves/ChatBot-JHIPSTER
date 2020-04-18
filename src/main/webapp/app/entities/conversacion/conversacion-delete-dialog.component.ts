import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';

@Component({
  templateUrl: './conversacion-delete-dialog.component.html'
})
export class ConversacionDeleteDialogComponent {
  conversacion?: IConversacion;

  constructor(
    protected conversacionService: ConversacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conversacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conversacionListModification');
      this.activeModal.close();
    });
  }
}
