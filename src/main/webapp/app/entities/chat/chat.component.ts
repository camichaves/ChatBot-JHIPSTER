import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { ChatDeleteDialogComponent } from './chat-delete-dialog.component';

@Component({
  selector: 'jhi-chat',
  templateUrl: './chat.component.html'
})
export class ChatComponent implements OnInit, OnDestroy {
  chats?: IChat[];
  eventSubscriber?: Subscription;

  constructor(protected chatService: ChatService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.chatService.query().subscribe((res: HttpResponse<IChat[]>) => {
      this.chats = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChats(): void {
    this.eventSubscriber = this.eventManager.subscribe('chatListModification', () => this.loadAll());
  }

  delete(chat: IChat): void {
    const modalRef = this.modalService.open(ChatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chat = chat;
  }
}
