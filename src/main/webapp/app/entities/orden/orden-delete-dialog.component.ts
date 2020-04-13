import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrden } from 'app/shared/model/orden.model';
import { OrdenService } from './orden.service';

@Component({
  templateUrl: './orden-delete-dialog.component.html'
})
export class OrdenDeleteDialogComponent {
  orden?: IOrden;

  constructor(protected ordenService: OrdenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ordenListModification');
      this.activeModal.close();
    });
  }
}
