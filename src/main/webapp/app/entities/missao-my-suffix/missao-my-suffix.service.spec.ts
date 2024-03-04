/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import MissaoMySuffixService from './missao-my-suffix.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { MissaoMySuffix } from '@/shared/model/missao-my-suffix.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('MissaoMySuffix Service', () => {
    let service: MissaoMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new MissaoMySuffixService();
      currentDate = new Date();
      elemDefault = new MissaoMySuffix(
        123,
        'AAAAAAA',
        'ALTA',
        0,
        false,
        0,
        currentDate,
        currentDate,
        false,
        false,
        'APROVADA',
        false,
        'APROVADA',
        'DISPONIBILIZADO',
        0,
        0,
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataInicio: dayjs(currentDate).format(DATE_FORMAT),
            dataTermino: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a MissaoMySuffix', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dataInicio: dayjs(currentDate).format(DATE_FORMAT),
            dataTermino: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataTermino: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a MissaoMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MissaoMySuffix', async () => {
        const returnedFromService = Object.assign(
          {
            nomeMissao: 'BBBBBB',
            prioridade: 'BBBBBB',
            quantidadeDiaria: 1,
            meiaDiaria: true,
            quantidadeEquipe: 1,
            dataInicio: dayjs(currentDate).format(DATE_FORMAT),
            dataTermino: dayjs(currentDate).format(DATE_FORMAT),
            deslocamento: true,
            passagemAerea: true,
            status: 'BBBBBB',
            osverificada: true,
            acao: 'BBBBBB',
            valorTotalMissao: 'BBBBBB',
            valorDiariasRealizadas: 1,
            saldoDisponivel: 1,
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataTermino: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MissaoMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MissaoMySuffix', async () => {
        const patchObject = Object.assign(
          {
            prioridade: 'BBBBBB',
            quantidadeDiaria: 1,
            dataTermino: dayjs(currentDate).format(DATE_FORMAT),
            valorDiariasRealizadas: 1,
          },
          new MissaoMySuffix(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataTermino: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MissaoMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MissaoMySuffix', async () => {
        const returnedFromService = Object.assign(
          {
            nomeMissao: 'BBBBBB',
            prioridade: 'BBBBBB',
            quantidadeDiaria: 1,
            meiaDiaria: true,
            quantidadeEquipe: 1,
            dataInicio: dayjs(currentDate).format(DATE_FORMAT),
            dataTermino: dayjs(currentDate).format(DATE_FORMAT),
            deslocamento: true,
            passagemAerea: true,
            status: 'BBBBBB',
            osverificada: true,
            acao: 'BBBBBB',
            valorTotalMissao: 'BBBBBB',
            valorDiariasRealizadas: 1,
            saldoDisponivel: 1,
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataTermino: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MissaoMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MissaoMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MissaoMySuffix', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
