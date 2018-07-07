/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.so;

import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.ProizvodEntity;

/**
 *
 * @author FON
 */
public class ZapamtiProizvod extends AbstractGenericOperation {

    ProizvodEntity proizvod;
    
    @Override
    protected void validate(IDomainEntity ide) throws Exception {
        if (ide instanceof ProizvodEntity) {
            ProizvodEntity proizvod = (ProizvodEntity) ide;
        } else {
            throw new Exception("Error in parametar");
        }
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        db.save(ide);
    }

}
