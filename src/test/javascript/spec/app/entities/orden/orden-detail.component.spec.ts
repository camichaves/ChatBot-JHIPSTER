import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdgarBotTestModule } from '../../../test.module';
import { OrdenDetailComponent } from 'app/entities/orden/orden-detail.component';
import { Orden } from 'app/shared/model/orden.model';

describe('Component Tests', () => {
  describe('Orden Management Detail Component', () => {
    let comp: OrdenDetailComponent;
    let fixture: ComponentFixture<OrdenDetailComponent>;
    const route = ({ data: of({ orden: new Orden(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EdgarBotTestModule],
        declarations: [OrdenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrdenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrdenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orden on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orden).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
