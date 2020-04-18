import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';
import { ConversacionDeleteDialogComponent } from './conversacion-delete-dialog.component';

@Component({
  selector: 'jhi-conversacion',
  templateUrl: './conversacion.component.html'
})
export class ConversacionComponent implements OnInit, OnDestroy {
  conversacions?: IConversacion[];
  eventSubscriber?: Subscription;

  constructor(
    protected conversacionService: ConversacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conversacionService.query().subscribe((res: HttpResponse<IConversacion[]>) => {
      this.conversacions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConversacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConversacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConversacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('conversacionListModification', () => this.loadAll());
  }

  delete(conversacion: IConversacion): void {
    const modalRef = this.modalService.open(ConversacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conversacion = conversacion;
  }
}
