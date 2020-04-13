import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChatBotTestModule } from '../../../test.module';
import { AgenteComponent } from 'app/entities/agente/agente.component';
import { AgenteService } from 'app/entities/agente/agente.service';
import { Agente } from 'app/shared/model/agente.model';

describe('Component Tests', () => {
  describe('Agente Management Component', () => {
    let comp: AgenteComponent;
    let fixture: ComponentFixture<AgenteComponent>;
    let service: AgenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [AgenteComponent],
        providers: []
      })
        .overrideTemplate(AgenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Agente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agentes && comp.agentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
