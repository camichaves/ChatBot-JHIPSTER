import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChatBotTestModule } from '../../../test.module';
import { OrdenComponent } from 'app/entities/orden/orden.component';
import { OrdenService } from 'app/entities/orden/orden.service';
import { Orden } from 'app/shared/model/orden.model';

describe('Component Tests', () => {
  describe('Orden Management Component', () => {
    let comp: OrdenComponent;
    let fixture: ComponentFixture<OrdenComponent>;
    let service: OrdenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [OrdenComponent],
        providers: []
      })
        .overrideTemplate(OrdenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Orden(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ordens && comp.ordens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
