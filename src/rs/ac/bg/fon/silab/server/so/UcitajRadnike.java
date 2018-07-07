/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.so;

import java.util.List;
import rs.ac.bg.fon.silab.jdbc.example1.domen.RadnikEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;

/**
 *
 * @author FON
 */
public class UcitajRadnike extends AbstractGenericOperation {

    List<IDomainEntity> lista;

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
        if (ide instanceof RadnikEntity) {
            RadnikEntity radnik = (RadnikEntity) ide;
        } else {
            throw new Exception("Error in parametar");
        }
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        lista = db.vratiSve(ide);
    }

    public List<IDomainEntity> getLista() {
        return lista;
    }

}
