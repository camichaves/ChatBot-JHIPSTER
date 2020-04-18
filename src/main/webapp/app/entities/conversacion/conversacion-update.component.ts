import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConversacion, Conversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';
import { IAgente } from 'app/shared/model/agente.model';
import { AgenteService } from 'app/entities/agente/agente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

type SelectableEntity = IAgente | ICliente;

@Component({
  selector: 'jhi-conversacion-update',
  templateUrl: './conversacion-update.component.html'
})
export class ConversacionUpdateComponent implements OnInit {
  isSaving = false;

  agentes: IAgente[] = [];

  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    sessionDF: [],
    authorWA: [],
    inicio: [],
    ultActCli: [],
    fin: [],
    status: [],
    agente: [],
    cliente: []
  });

  constructor(
    protected conversacionService: ConversacionService,
    protected agenteService: AgenteService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conversacion }) => {
      this.updateForm(conversacion);

      this.agenteService
        .query()
        .pipe(
          map((res: HttpResponse<IAgente[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAgente[]) => (this.agentes = resBody));

      this.clienteService
        .query()
        .pipe(
          map((res: HttpResponse<ICliente[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICliente[]) => (this.clientes = resBody));
    });
  }

  updateForm(conversacion: IConversacion): void {
    this.editForm.patchValue({
      id: conversacion.id,
      sessionDF: conversacion.sessionDF,
      authorWA: conversacion.authorWA,
      inicio: conversacion.inicio != null ? conversacion.inicio.format(DATE_TIME_FORMAT) : null,
      ultActCli: conversacion.ultActCli != null ? conversacion.ultActCli.format(DATE_TIME_FORMAT) : null,
      fin: conversacion.fin != null ? conversacion.fin.format(DATE_TIME_FORMAT) : null,
      status: conversacion.status,
      agente: conversacion.agente,
      cliente: conversacion.cliente
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conversacion = this.createFromForm();
    if (conversacion.id !== undefined) {
      this.subscribeToSaveResponse(this.conversacionService.update(conversacion));
    } else {
      this.subscribeToSaveResponse(this.conversacionService.create(conversacion));
    }
  }

  private createFromForm(): IConversacion {
    return {
      ...new Conversacion(),
      id: this.editForm.get(['id'])!.value,
      sessionDF: this.editForm.get(['sessionDF'])!.value,
      authorWA: this.editForm.get(['authorWA'])!.value,
      inicio: this.editForm.get(['inicio'])!.value != null ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      ultActCli:
        this.editForm.get(['ultActCli'])!.value != null ? moment(this.editForm.get(['ultActCli'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value != null ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      agente: this.editForm.get(['agente'])!.value,
      cliente: this.editForm.get(['cliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversacion>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
