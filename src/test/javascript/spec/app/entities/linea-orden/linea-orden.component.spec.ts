import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChatBotTestModule } from '../../../test.module';
import { LineaOrdenComponent } from 'app/entities/linea-orden/linea-orden.component';
import { LineaOrdenService } from 'app/entities/linea-orden/linea-orden.service';
import { LineaOrden } from 'app/shared/model/linea-orden.model';

describe('Component Tests', () => {
  describe('LineaOrden Management Component', () => {
    let comp: LineaOrdenComponent;
    let fixture: ComponentFixture<LineaOrdenComponent>;
    let service: LineaOrdenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [LineaOrdenComponent],
        providers: []
      })
        .overrideTemplate(LineaOrdenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineaOrdenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineaOrdenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LineaOrden(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lineaOrdens && comp.lineaOrdens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
