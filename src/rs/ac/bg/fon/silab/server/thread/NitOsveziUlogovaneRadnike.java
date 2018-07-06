/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.thread;

import rs.ac.bg.fon.silab.server.form.FServer;

/**
 *
 * @author Antic
 */
public class NitOsveziUlogovaneRadnike extends Thread{
    FServer forma;

    public NitOsveziUlogovaneRadnike(FServer forma) {
        this.forma = forma;
    }

    @Override
    public void run() {
        while(true){
//            forma.postaviModelUlogovanihRadnika();
            try {
                sleep(10000);
            } catch (Exception e) {
            }
        
        }

    }
    
}
