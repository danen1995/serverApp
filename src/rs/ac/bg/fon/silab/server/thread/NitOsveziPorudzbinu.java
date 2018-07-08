/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.thread;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.jdbc.example1.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.PorudzbinaEntity;

/**
 *
 * @author Antic
 */
public class NitOsveziPorudzbinu extends Thread{

    public NitOsveziPorudzbinu() {
    }

    @Override
    public void run() {
        while(true){
            for (PorudzbinaEntity por : vratiSvePorudzbine()) {
                long brDana = ChronoUnit.DAYS.between(new java.sql.Date(por.getDatumPorudzbine().getTime()).toLocalDate(),LocalDate.now());
                if(brDana>1 && por.getStatus().equals("U pripremi")){
                    por.setStatus("Otpremljeno");
                }
                if(brDana>3 && por.getStatus().equals("Otpremljeno")){
                    por.setStatus("Dostavljeno");
                }
                try {
                    new DatabaseRepository().azurirajStatusPorudzbine(por);
                } catch (Exception ex) {
                    Logger.getLogger(NitOsveziPorudzbinu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                sleep(10000);
            } catch (Exception e) {
            }
        }

    }
    
    List<PorudzbinaEntity> vratiSvePorudzbine(){
        List<PorudzbinaEntity> lista = new ArrayList<>();
        List<IDomainEntity> lp = new ArrayList<>();;
        try {
                lp = new DatabaseRepository().vratiSve(new PorudzbinaEntity());
        } catch (Exception ex) {
            Logger.getLogger(NitOsveziPorudzbinu.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (IDomainEntity ide : lp) {
            lista.add((PorudzbinaEntity) ide);
        }
        return lista;
    }
    
    
}
