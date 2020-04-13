import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgente, Agente } from 'app/shared/model/agente.model';
import { AgenteService } from './agente.service';

@Component({
  selector: 'jhi-agente-update',
  templateUrl: './agente-update.component.html'
})
export class AgenteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identificador: [],
    nombre: [],
    disponible: [],
    horario: []
  });

  constructor(protected agenteService: AgenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agente }) => {
      this.updateForm(agente);
    });
  }

  updateForm(agente: IAgente): void {
    this.editForm.patchValue({
      id: agente.id,
      identificador: agente.identificador,
      nombre: agente.nombre,
      disponible: agente.disponible,
      horario: agente.horario
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agente = this.createFromForm();
    if (agente.id !== undefined) {
      this.subscribeToSaveResponse(this.agenteService.update(agente));
    } else {
      this.subscribeToSaveResponse(this.agenteService.create(agente));
    }
  }

  private createFromForm(): IAgente {
    return {
      ...new Agente(),
      id: this.editForm.get(['id'])!.value,
      identificador: this.editForm.get(['identificador'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      disponible: this.editForm.get(['disponible'])!.value,
      horario: this.editForm.get(['horario'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgente>>): void {
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
