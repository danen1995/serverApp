/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.so;

import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.KupacEntity;

/**
 *
 * @author FON
 */
public class ObrisiKupca extends AbstractGenericOperation {

    KupacEntity kupac;

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
        if (ide instanceof KupacEntity) {
            kupac = (KupacEntity) ide;
        } else {
            throw new Exception("Error in parametar");
        }
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        db.obrisi(ide);
    }

}
