import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChatBotTestModule } from '../../../test.module';
import { OrdenUpdateComponent } from 'app/entities/orden/orden-update.component';
import { OrdenService } from 'app/entities/orden/orden.service';
import { Orden } from 'app/shared/model/orden.model';

describe('Component Tests', () => {
  describe('Orden Management Update Component', () => {
    let comp: OrdenUpdateComponent;
    let fixture: ComponentFixture<OrdenUpdateComponent>;
    let service: OrdenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChatBotTestModule],
        declarations: [OrdenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrdenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Orden(123);
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
        const entity = new Orden();
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
