import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILineaOrden } from 'app/shared/model/linea-orden.model';

@Component({
  selector: 'jhi-linea-orden-detail',
  templateUrl: './linea-orden-detail.component.html'
})
export class LineaOrdenDetailComponent implements OnInit {
  lineaOrden: ILineaOrden | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineaOrden }) => {
      this.lineaOrden = lineaOrden;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
