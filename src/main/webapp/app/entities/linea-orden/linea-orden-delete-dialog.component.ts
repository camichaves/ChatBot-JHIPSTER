import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILineaOrden } from 'app/shared/model/linea-orden.model';
import { LineaOrdenService } from './linea-orden.service';

@Component({
  templateUrl: './linea-orden-delete-dialog.component.html'
})
export class LineaOrdenDeleteDialogComponent {
  lineaOrden?: ILineaOrden;

  constructor(
    protected lineaOrdenService: LineaOrdenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lineaOrdenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lineaOrdenListModification');
      this.activeModal.close();
    });
  }
}
