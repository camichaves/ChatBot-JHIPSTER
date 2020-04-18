import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IItem, Item } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { IOrden } from 'app/shared/model/orden.model';
import { OrdenService } from 'app/entities/orden/orden.service';

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-update.component.html'
})
export class ItemUpdateComponent implements OnInit {
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
    protected itemService: ItemService,
    protected ordenService: OrdenService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);

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

  updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      codigo: item.codigo,
      detalle: item.detalle,
      cantidad: item.cantidad,
      precio: item.precio,
      orden: item.orden
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  private createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      detalle: this.editForm.get(['detalle'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      precio: this.editForm.get(['precio'])!.value,
      orden: this.editForm.get(['orden'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
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
