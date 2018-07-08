/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import rs.ac.bg.fon.silab.jdbc.example1.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.KupacEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.MestoEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.PorudzbinaEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.ProizvodEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.RadnikEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.StavkaPorudzbineEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.TipProizvodaEntity;
import rs.ac.bg.fon.silab.server.so.AbstractGenericOperation;
import rs.ac.bg.fon.silab.server.so.IzmeniKupca;
import rs.ac.bg.fon.silab.server.so.IzmeniPorudzbinu;
import rs.ac.bg.fon.silab.server.so.IzmeniProizvod;
import rs.ac.bg.fon.silab.server.so.ObrisiKupca;
import rs.ac.bg.fon.silab.server.so.ObrisiPorudzbinu;
import rs.ac.bg.fon.silab.server.so.ObrisiProizvod;
import rs.ac.bg.fon.silab.server.so.UcitajKupce;
import rs.ac.bg.fon.silab.server.so.UcitajListuMesta;
import rs.ac.bg.fon.silab.server.so.UcitajPorudzbine;
import rs.ac.bg.fon.silab.server.so.UcitajProizvode;
import rs.ac.bg.fon.silab.server.so.UcitajRadnike;
import rs.ac.bg.fon.silab.server.so.UcitajTipoveProizvoda;
import rs.ac.bg.fon.silab.server.so.ZapamtiKupca;
import rs.ac.bg.fon.silab.server.so.ZapamtiPorudzbinu;
import rs.ac.bg.fon.silab.server.so.ZapamtiProizvod;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author FON
 */
public class ThreadClient extends Thread {

    Socket socket;
    ThreadServer server;
    RadnikEntity ulogovaniRadnik;

    public ThreadClient(Socket socket, ThreadServer server) {
        this.socket = socket;
        this.server = server;
    }

    public Socket getSocket() {
        return socket;
    }

    public RadnikEntity getRadnik() {
        return ulogovaniRadnik;
    }

    public void setRadnik(RadnikEntity ulogovaniRadnik) {
        this.ulogovaniRadnik = ulogovaniRadnik;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //prihvati zahtev od klijenta
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                RequestObject request = (RequestObject) input.readObject();
                //obradi zahtev
                ResponseObject responseObject = new ResponseObject();
                switch (request.getOperation()) {
                    case IOperation.odjava:
                        try {
//                            this.socket.close();System.out.println("d");
                            server.odjavi(this);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(ulogovaniRadnik);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_PROIZVODE:
                        try {
                            UcitajProizvode ucitajProizvode = new UcitajProizvode();
                            ucitajProizvode.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje = ucitajProizvode.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_PORUDZBINE:
                        try {
                            UcitajPorudzbine ucitajPorudzbine = new UcitajPorudzbine();
                            ucitajPorudzbine.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajPorudzbine.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_PORUDZBINE_ZA_KUPCA:
                        String jmbgK = (String) request.getData();
                        try {
                            List<PorudzbinaEntity> listaZaVracanje2 = new DatabaseRepository().vratiSvePorudzbineZaKupca(jmbgK);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_TIPOVE_PROIZVODA:
                        try {
                            UcitajTipoveProizvoda ucitajTipoveProizvoda = new UcitajTipoveProizvoda();
                            ucitajTipoveProizvoda.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajTipoveProizvoda.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_TIPOVE_PROIZVODA_PO_ID:
                        Long x = (Long) request.getData();
                        try {
//                            TipProizvodaEntity tt = new TipProizvodaEntity();
//                            tt.setIdTipaProizvoda(x);
//                            UcitajTipProizvoda ucitajTip = new UcitajTipProizvoda();
//                            ucitajTip.templateExecute(tt);
//                            TipProizvodaEntity tip = (TipProizvodaEntity) ucitajTip.vratiObjekat();
                            TipProizvodaEntity tip = (TipProizvodaEntity) new DatabaseRepository().nadjiPoIDu(new TipProizvodaEntity(), x);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(tip);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVA_MESTA:
                        try {
                            UcitajListuMesta ucitajMesta = new UcitajListuMesta();
                            ucitajMesta.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajMesta.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_KUPCE:
                        try {
                            UcitajKupce ucitajKupce = new UcitajKupce();
                            ucitajKupce.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajKupce.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_SVE_RADNIKE:
                        try {
                            UcitajRadnike ucitajRadnike = new UcitajRadnike();
                            ucitajRadnike.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajRadnike.getLista();
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(listaZaVracanje2);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.logovanje:
                        RadnikEntity r = (RadnikEntity) request.getData();
                        try {
                            r = new DatabaseRepository().logovanje(r);
                            System.out.println(r.getImeRadnika());
                            ulogovaniRadnik = r;
                            server.dodajRadnika(this);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(r);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_KUPCA_JMBG:
                        String id = (String) request.getData();
                        try {
                            KupacEntity kupac = (KupacEntity) new DatabaseRepository().nadjiPoIDu(new KupacEntity(), id);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(kupac);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_PROIZVOD_ID:
                        Long idPr = (Long) request.getData();
                        try {
                            ProizvodEntity proEn = (ProizvodEntity) new DatabaseRepository().nadjiPoIDu(new ProizvodEntity(), idPr);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(proEn);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_MESTO_PTT:
                        Long pttMesto = (Long) request.getData();
                        try {
                            MestoEntity mestoE = (MestoEntity) new DatabaseRepository().nadjiPoIDu(new MestoEntity(), pttMesto);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(mestoE);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_PORUDZBINU_ID:
                        Long idPor = (Long) request.getData();
                        try {
                            PorudzbinaEntity porEn = (PorudzbinaEntity) new DatabaseRepository().nadjiPoIDu(new PorudzbinaEntity(), idPor);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(porEn);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.VRATI_STAVKE_ZA_PORUDZBINU:
                        Long idP = (Long) request.getData();
                        try {
                            System.out.println(idP);
                            List<StavkaPorudzbineEntity> stavke = new DatabaseRepository().vratiStavkeZaPorudzbinu(idP);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(stavke);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.OBRISI_PROIZVOD:
                        try {
                            AbstractGenericOperation obrisiProizvod = new ObrisiProizvod();
                            obrisiProizvod.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(true);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.OBRISI_PORUDZBINU:
                        try {
                            AbstractGenericOperation obrisiPorudzbinu = new ObrisiPorudzbinu();
                            obrisiPorudzbinu.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(true);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.OBRISI_KUPCA:
                        try {
                            AbstractGenericOperation obrisiKupca = new ObrisiKupca();
                            obrisiKupca.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData(true);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.ZAPAMTI_PROIZVOD:
                        try {
                            AbstractGenericOperation zapamtiProizvod = new ZapamtiProizvod();
                            zapamtiProizvod.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.ZAPAMTI_KUPCA:
                        try {
                            AbstractGenericOperation zapamtiKupca = new ZapamtiKupca();
                            zapamtiKupca.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.ZAPAMTI_PORUDZBINU:
                        try {
                            AbstractGenericOperation zapamtiPorudzbinu = new ZapamtiPorudzbinu();
                            zapamtiPorudzbinu.templateExecute((IDomainEntity) request.getData());
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.IZMENI_PROIZVOD:
                        try {
                            AbstractGenericOperation izmeniProizvod = new IzmeniProizvod();
                            izmeniProizvod.templateExecute((IDomainEntity) request.getData());

                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.IZMENI_KUPCA:
                        try {
                            AbstractGenericOperation izmeniKupca = new IzmeniKupca();
                            izmeniKupca.templateExecute((IDomainEntity) request.getData());

                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.IZMENI_PORUDZBINU:
                        try {
                            AbstractGenericOperation izmeniPorudzbinu = new IzmeniPorudzbinu();
                            izmeniPorudzbinu.templateExecute((IDomainEntity) request.getData());

                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
                    case IOperation.IZMENI_STAVKE:
                        List<StavkaPorudzbineEntity> stavke = (List<StavkaPorudzbineEntity>) request.getData();
                        try {
                            new DatabaseRepository().obrisiStavkeZaPorudzbinu(stavke.get(0).getPorudzbina());
                            for (StavkaPorudzbineEntity stavkaPorudzbineEntity : stavke) {
                                new DatabaseRepository().ubaciStavku(stavkaPorudzbineEntity);
                            }
                            responseObject.setCode(IStatus.OK);
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;

                }

                //fill response object
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(responseObject);
                output.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
