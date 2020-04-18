import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdgarBotTestModule } from '../../../test.module';
import { ConversacionDetailComponent } from 'app/entities/conversacion/conversacion-detail.component';
import { Conversacion } from 'app/shared/model/conversacion.model';

describe('Component Tests', () => {
  describe('Conversacion Management Detail Component', () => {
    let comp: ConversacionDetailComponent;
    let fixture: ComponentFixture<ConversacionDetailComponent>;
    const route = ({ data: of({ conversacion: new Conversacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EdgarBotTestModule],
        declarations: [ConversacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConversacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConversacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conversacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conversacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
