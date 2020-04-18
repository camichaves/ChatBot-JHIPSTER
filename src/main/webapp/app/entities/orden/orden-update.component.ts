import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrden, Orden } from 'app/shared/model/orden.model';
import { OrdenService } from './orden.service';

@Component({
  selector: 'jhi-orden-update',
  templateUrl: './orden-update.component.html'
})
export class OrdenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
    cliente: [],
    fecha: [],
    total: []
  });

  constructor(protected ordenService: OrdenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orden }) => {
      this.updateForm(orden);
    });
  }

  updateForm(orden: IOrden): void {
    this.editForm.patchValue({
      id: orden.id,
      numero: orden.numero,
      cliente: orden.cliente,
      fecha: orden.fecha != null ? orden.fecha.format(DATE_TIME_FORMAT) : null,
      total: orden.total
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orden = this.createFromForm();
    if (orden.id !== undefined) {
      this.subscribeToSaveResponse(this.ordenService.update(orden));
    } else {
      this.subscribeToSaveResponse(this.ordenService.create(orden));
    }
  }

  private createFromForm(): IOrden {
    return {
      ...new Orden(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      fecha: this.editForm.get(['fecha'])!.value != null ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      total: this.editForm.get(['total'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrden>>): void {
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
}
