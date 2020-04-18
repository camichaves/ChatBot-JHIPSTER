import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IContacto, Contacto } from 'app/shared/model/contacto.model';
import { ContactoService } from './contacto.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-contacto-update',
  templateUrl: './contacto-update.component.html'
})
export class ContactoUpdateComponent implements OnInit {
  isSaving = false;

  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    nombrePersona: [],
    authorWA: [],
    cliente: []
  });

  constructor(
    protected contactoService: ContactoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contacto }) => {
      this.updateForm(contacto);

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

  updateForm(contacto: IContacto): void {
    this.editForm.patchValue({
      id: contacto.id,
      nombrePersona: contacto.nombrePersona,
      authorWA: contacto.authorWA,
      cliente: contacto.cliente
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contacto = this.createFromForm();
    if (contacto.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoService.update(contacto));
    } else {
      this.subscribeToSaveResponse(this.contactoService.create(contacto));
    }
  }

  private createFromForm(): IContacto {
    return {
      ...new Contacto(),
      id: this.editForm.get(['id'])!.value,
      nombrePersona: this.editForm.get(['nombrePersona'])!.value,
      authorWA: this.editForm.get(['authorWA'])!.value,
      cliente: this.editForm.get(['cliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContacto>>): void {
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

  trackById(index: number, item: ICliente): any {
    return item.id;
  }
}
