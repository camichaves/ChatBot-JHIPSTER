import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IChat, Chat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { IConversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from 'app/entities/conversacion/conversacion.service';

@Component({
  selector: 'jhi-chat-update',
  templateUrl: './chat-update.component.html'
})
export class ChatUpdateComponent implements OnInit {
  isSaving = false;

  conversacions: IConversacion[] = [];

  editForm = this.fb.group({
    id: [],
    authorWA: [],
    mensaje: [],
    fecha: [],
    archivo: [],
    conversacion: []
  });

  constructor(
    protected chatService: ChatService,
    protected conversacionService: ConversacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chat }) => {
      this.updateForm(chat);

      this.conversacionService
        .query()
        .pipe(
          map((res: HttpResponse<IConversacion[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConversacion[]) => (this.conversacions = resBody));
    });
  }

  updateForm(chat: IChat): void {
    this.editForm.patchValue({
      id: chat.id,
      authorWA: chat.authorWA,
      mensaje: chat.mensaje,
      fecha: chat.fecha != null ? chat.fecha.format(DATE_TIME_FORMAT) : null,
      archivo: chat.archivo,
      conversacion: chat.conversacion
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chat = this.createFromForm();
    if (chat.id !== undefined) {
      this.subscribeToSaveResponse(this.chatService.update(chat));
    } else {
      this.subscribeToSaveResponse(this.chatService.create(chat));
    }
  }

  private createFromForm(): IChat {
    return {
      ...new Chat(),
      id: this.editForm.get(['id'])!.value,
      authorWA: this.editForm.get(['authorWA'])!.value,
      mensaje: this.editForm.get(['mensaje'])!.value,
      fecha: this.editForm.get(['fecha'])!.value != null ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      archivo: this.editForm.get(['archivo'])!.value,
      conversacion: this.editForm.get(['conversacion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChat>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IConversacion): any {
    return item.id;
  }
}
