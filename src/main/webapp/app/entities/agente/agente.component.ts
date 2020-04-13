import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgente } from 'app/shared/model/agente.model';
import { AgenteService } from './agente.service';
import { AgenteDeleteDialogComponent } from './agente-delete-dialog.component';

@Component({
  selector: 'jhi-agente',
  templateUrl: './agente.component.html'
})
export class AgenteComponent implements OnInit, OnDestroy {
  agentes?: IAgente[];
  eventSubscriber?: Subscription;

  constructor(protected agenteService: AgenteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.agenteService.query().subscribe((res: HttpResponse<IAgente[]>) => {
      this.agentes = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAgentes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAgente): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAgentes(): void {
    this.eventSubscriber = this.eventManager.subscribe('agenteListModification', () => this.loadAll());
  }

  delete(agente: IAgente): void {
    const modalRef = this.modalService.open(AgenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agente = agente;
  }
}
