import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContacto } from 'app/shared/model/contacto.model';
import { ContactoService } from './contacto.service';
import { ContactoDeleteDialogComponent } from './contacto-delete-dialog.component';

@Component({
  selector: 'jhi-contacto',
  templateUrl: './contacto.component.html'
})
export class ContactoComponent implements OnInit, OnDestroy {
  contactos?: IContacto[];
  eventSubscriber?: Subscription;

  constructor(protected contactoService: ContactoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.contactoService.query().subscribe((res: HttpResponse<IContacto[]>) => {
      this.contactos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContacto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactos(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactoListModification', () => this.loadAll());
  }

  delete(contacto: IContacto): void {
    const modalRef = this.modalService.open(ContactoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contacto = contacto;
  }
}
