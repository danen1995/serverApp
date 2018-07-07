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
//                    case IOperation.SACUVAJ:
//                        IDomainEntity ide1 = (IDomainEntity) request.getData();
//                        try {
//                            if (ide1 instanceof KupacEntity) {
//                                AbstractGenericOperation sacuvajKupca = new SacuvajKupca();
//                                sacuvajKupca.templateExecute(ide1);
//                            }
//                            if (ide1 instanceof ProizvodEntity) {
//                                AbstractGenericOperation sacuvajProizvod = new SacuvajProizvod();
//                                sacuvajProizvod.templateExecute(ide1);
//                            }
//                            if (ide1 instanceof PorudzbinaEntity) {
//                                AbstractGenericOperation sacuvajPorudzbinu = new SacuvajPorudzbinu();
//                                sacuvajPorudzbinu.templateExecute(ide1);
//                            }
//                            if (ide1 instanceof StavkaPorudzbineEntity) {
//                                AbstractGenericOperation sacuvajStavkuPorudzbine = new SacuvajStavkuPorudzbine();
//                                sacuvajStavkuPorudzbine.templateExecute(ide1);
//                            }
//                            responseObject.setCode(IStatus.OK);
//                            responseObject.setData(ide1);
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                            responseObject.setCode(IStatus.ERROR);
//                            responseObject.setMessage(e.getMessage());
//                        }
//
//                        break;
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
//                    case IOperation.VRATI_SVE:
//                        IDomainEntity ide = (IDomainEntity) request.getData();
//                        try {
//                            List<IDomainEntity> lista = new DatabaseRepository().vratiSve(ide);
//                            responseObject.setCode(IStatus.OK);
//                            responseObject.setData(lista);
//                        } catch (Exception e) {
//                            responseObject.setCode(IStatus.ERROR);
//                            responseObject.setMessage(e.getMessage());
//                        }
//
//                        break;
                    case IOperation.VRATI_SVE_PROIZVODE:
                        try {
                            UcitajProizvode ucitajProizvode= new UcitajProizvode();
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
                            UcitajPorudzbine ucitajPorudzbine  = new UcitajPorudzbine();
                            ucitajPorudzbine.templateExecute((IDomainEntity) request.getData());
                            List<IDomainEntity> listaZaVracanje2 = ucitajPorudzbine.getLista();
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
//                    case IOperation.SACUVAJ_KUPCA:
//                        KupacEntity kupac = (KupacEntity) request.getData();
//                        try {
//                            //company = new DatabaseRepository().saveCompany(company);
//                            AbstractGenericOperation sacuvajKupaca = new SacuvajKupca();
//                            sacuvajKupaca.templateExecute(kupac);
//                            //company mora da sadrzi ID!
//                            responseObject.setCode(IStatus.OK);
//                            responseObject.setData(kupac);
//                        } catch (Exception e) {
//                            responseObject.setCode(IStatus.ERROR);
//                            responseObject.setMessage(e.getMessage());
//                        }

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
//                    case IOperation.IZMENI:
//                        IDomainEntity ide2 = (IDomainEntity) request.getData();
//                        try {
//                            if (ide2 instanceof KupacEntity) {
//                                ide2 = (KupacEntity) ide2;
//                                AbstractGenericOperation izmeniKupca = new IzmeniKupca();
//                                izmeniKupca.templateExecute(ide2);
//                            }
//                            if (ide2 instanceof ProizvodEntity) {
//                                ide2 = (ProizvodEntity) ide2;
//                                AbstractGenericOperation izmeniPacijenta = new IzmeniProizvod();
//                                izmeniPacijenta.templateExecute(ide2);
//                            }
////                            if (ide2 instanceof Pregled) {
////                                ide2 = (Pregled) ide2;
////                                AbstractGenericOperation izmeniPregled = new IzmeniPregled();
////                                izmeniPregled.templateExecute(ide2);
////                            }
//                            //company mora da sadrzi ID!
//                            responseObject.setCode(IStatus.OK);
//                            responseObject.setData(ide2);
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage() + e.getStackTrace());
//                            responseObject.setCode(IStatus.ERROR);
//                            responseObject.setMessage(e.getMessage());
//                        }
//
//                        break;
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
                    case IOperation.ZAPAMTI_STAVKU:
                        StavkaPorudzbineEntity stavka = (StavkaPorudzbineEntity) request.getData();
                        System.out.println(stavka);
                        System.out.println("*******");
                        try {
                            new DatabaseRepository().save(stavka);
                            responseObject.setCode(IStatus.OK);
                            responseObject.setData((IDomainEntity) request.getData());
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + e.getStackTrace());
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }
                        break;
//                    case IOperation.ZAPAMTI_LISTU_PREGLEDA:
//                        List<Pregled> pregledi = (List<Pregled>) request.getData();
//                        try {
//                            boolean uspesno = new DatabaseRepository().zapamtiPreglede(pregledi);
//                            responseObject.setCode(IStatus.OK);
//                            responseObject.setData(uspesno);
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage() + e.getStackTrace());
//                            responseObject.setCode(IStatus.ERROR);
//                            responseObject.setMessage(e.getMessage());
//                        }
//                        break;
                    case IOperation.IZMENI_PROIZVOD:
                        try {
                            AbstractGenericOperation izmeniProizvod= new IzmeniProizvod();
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
