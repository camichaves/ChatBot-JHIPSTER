import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILineaOrden, LineaOrden } from 'app/shared/model/linea-orden.model';
import { LineaOrdenService } from './linea-orden.service';
import { IOrden } from 'app/shared/model/orden.model';
import { OrdenService } from 'app/entities/orden/orden.service';

@Component({
  selector: 'jhi-linea-orden-update',
  templateUrl: './linea-orden-update.component.html'
})
export class LineaOrdenUpdateComponent implements OnInit {
  isSaving = false;

  ordens: IOrden[] = [];

  editForm = this.fb.group({
    id: [],
    codigo: [],
    detalle: [],
    cantidad: [],
    precio: [],
    orden: []
  });

  constructor(
    protected lineaOrdenService: LineaOrdenService,
    protected ordenService: OrdenService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineaOrden }) => {
      this.updateForm(lineaOrden);

      this.ordenService
        .query()
        .pipe(
          map((res: HttpResponse<IOrden[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOrden[]) => (this.ordens = resBody));
    });
  }

  updateForm(lineaOrden: ILineaOrden): void {
    this.editForm.patchValue({
      id: lineaOrden.id,
      codigo: lineaOrden.codigo,
      detalle: lineaOrden.detalle,
      cantidad: lineaOrden.cantidad,
      precio: lineaOrden.precio,
      orden: lineaOrden.orden
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lineaOrden = this.createFromForm();
    if (lineaOrden.id !== undefined) {
      this.subscribeToSaveResponse(this.lineaOrdenService.update(lineaOrden));
    } else {
      this.subscribeToSaveResponse(this.lineaOrdenService.create(lineaOrden));
    }
  }

  private createFromForm(): ILineaOrden {
    return {
      ...new LineaOrden(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      detalle: this.editForm.get(['detalle'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      precio: this.editForm.get(['precio'])!.value,
      orden: this.editForm.get(['orden'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineaOrden>>): void {
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

  trackById(index: number, item: IOrden): any {
    return item.id;
  }
}
