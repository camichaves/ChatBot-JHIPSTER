import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILineaOrden } from 'app/shared/model/linea-orden.model';
import { LineaOrdenService } from './linea-orden.service';
import { LineaOrdenDeleteDialogComponent } from './linea-orden-delete-dialog.component';

@Component({
  selector: 'jhi-linea-orden',
  templateUrl: './linea-orden.component.html'
})
export class LineaOrdenComponent implements OnInit, OnDestroy {
  lineaOrdens?: ILineaOrden[];
  eventSubscriber?: Subscription;

  constructor(protected lineaOrdenService: LineaOrdenService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lineaOrdenService.query().subscribe((res: HttpResponse<ILineaOrden[]>) => {
      this.lineaOrdens = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLineaOrdens();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILineaOrden): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLineaOrdens(): void {
    this.eventSubscriber = this.eventManager.subscribe('lineaOrdenListModification', () => this.loadAll());
  }

  delete(lineaOrden: ILineaOrden): void {
    const modalRef = this.modalService.open(LineaOrdenDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lineaOrden = lineaOrden;
  }
}
