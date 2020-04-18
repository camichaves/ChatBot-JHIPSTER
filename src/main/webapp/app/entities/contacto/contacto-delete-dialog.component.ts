import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContacto } from 'app/shared/model/contacto.model';
import { ContactoService } from './contacto.service';

@Component({
  templateUrl: './contacto-delete-dialog.component.html'
})
export class ContactoDeleteDialogComponent {
  contacto?: IContacto;

  constructor(protected contactoService: ContactoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactoListModification');
      this.activeModal.close();
    });
  }
}
