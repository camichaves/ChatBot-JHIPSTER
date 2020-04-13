import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChatBotTestModule } from '../../../test.module';
import { LineaOrdenDetailComponent } from 'app/entities/linea-orden/linea-orden-detail.component';
import { LineaOrden } from 'app/shared/model/linea-orden.model';

describe('Component Tests', () => {
  describe('LineaOrden Management Detail Component', () => {
    let comp: LineaOrdenDetailComponent;
    let fixture: ComponentFixture<LineaOrdenDetailComponent>;
    const route = ({ data: of({ lineaOrden: new LineaOrden(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [LineaOrdenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LineaOrdenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineaOrdenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lineaOrden on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lineaOrden).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
