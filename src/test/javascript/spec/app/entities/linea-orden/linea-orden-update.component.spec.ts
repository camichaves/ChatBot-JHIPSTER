import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChatBotTestModule } from '../../../test.module';
import { LineaOrdenUpdateComponent } from 'app/entities/linea-orden/linea-orden-update.component';
import { LineaOrdenService } from 'app/entities/linea-orden/linea-orden.service';
import { LineaOrden } from 'app/shared/model/linea-orden.model';

describe('Component Tests', () => {
  describe('LineaOrden Management Update Component', () => {
    let comp: LineaOrdenUpdateComponent;
    let fixture: ComponentFixture<LineaOrdenUpdateComponent>;
    let service: LineaOrdenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [LineaOrdenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LineaOrdenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineaOrdenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineaOrdenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LineaOrden(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LineaOrden();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
