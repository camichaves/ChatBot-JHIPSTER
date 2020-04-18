import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ConversacionService } from 'app/entities/conversacion/conversacion.service';
import { IConversacion, Conversacion } from 'app/shared/model/conversacion.model';

describe('Service Tests', () => {
  describe('Conversacion Service', () => {
    let injector: TestBed;
    let service: ConversacionService;
    let httpMock: HttpTestingController;
    let elemDefault: IConversacion;
    let expectedResult: IConversacion | IConversacion[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConversacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Conversacion(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicio: currentDate.format(DATE_TIME_FORMAT),
            ultActCli: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Conversacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_TIME_FORMAT),
            ultActCli: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            ultActCli: currentDate,
            fin: currentDate
          },
          returnedFromService
        );
        service
          .create(new Conversacion())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Conversacion', () => {
        const returnedFromService = Object.assign(
          {
            sessionDF: 'BBBBBB',
            authorWA: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            ultActCli: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            status: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            ultActCli: currentDate,
            fin: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Conversacion', () => {
        const returnedFromService = Object.assign(
          {
            sessionDF: 'BBBBBB',
            authorWA: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            ultActCli: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            status: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            ultActCli: currentDate,
            fin: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Conversacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
