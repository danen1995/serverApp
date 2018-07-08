/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.jdbc.example1.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.MestoEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.PorudzbinaEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.RadnikEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.StavkaPorudzbineEntity;

/**
 *
 * @author FON
 */
public class DatabaseRepository {


    public List<MestoEntity> vratiSvaMesta() throws Exception {
        ArrayList<MestoEntity> mesta = new ArrayList<MestoEntity>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM mesto";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Long ptt = rs.getLong("ptt");
            String naziv = rs.getString("naziv");
            mesta.add(new MestoEntity(ptt, naziv));
        }
        rs.close();
        statement.close();
        return mesta;
    }
    
    public IDomainEntity save(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(ide.getTableName())
                .append("(")
                .append(ide.getColumnNamesForInsert())
                .append(")")
                .append(" VALUES ")
                .append("(")
                .append(ide.getColumnValuesForInsert())
                .append(")");

        String query = sb.toString();
        System.out.println("Query: " + query);
        Statement s = connection.createStatement();
        s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        if (ide.isIdAutoincrement()) {
            ResultSet rs = s.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                ide.setAutoincrementId(id);
                System.out.println(id);
            }
        }
        return ide;

    }

    public void startTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
    }

    public void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().commit();
    }

    public void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().rollback();
    }

    private IDomainEntity findById(IDomainEntity ide) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IDomainEntity nadjiPoIDu(IDomainEntity ide, Long x) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + ide.vratiStringZaJOIN() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, x);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ide = ide.vratiObjekat(rs);
            rs.close();
            ps.close();
            return ide;
        }
        throw new Exception("City with this code does not exist!");
    }

    public IDomainEntity nadjiPoIDu(IDomainEntity ide, Integer x) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + ide.vratiStringZaJOIN() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, x);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ide = ide.vratiObjekat(rs);
            rs.close();
            ps.close();
            return ide;
        }
        throw new Exception("City with this code does not exist!");
    }

    public IDomainEntity nadjiPoIDu(IDomainEntity ide, String x) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + ide.vratiStringZaJOIN() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
        System.out.println(query);
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, x);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ide = ide.vratiObjekat(rs);
            rs.close();
            ps.close();
            return ide;
        }
        throw new Exception("City with this code does not exist!");
    }

    public List<IDomainEntity> vratiSve(IDomainEntity ide) throws Exception {
        List<IDomainEntity> lista = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + ide.vratiStringZaJOIN();
        System.out.println(query);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        while (rs.next()) {
            lista.add(ide.vratiObjekat(rs));
        }
        rs.close();
        s.close();
        return lista;
    }

    public RadnikEntity logovanje(RadnikEntity r) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM radnik WHERE korisnickoIme=? and lozinka=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, r.getKorisnickoIme());
        ps.setString(2, r.getLozinka());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Long id = rs.getLong(1);
            String ime = rs.getString(2);
            String prezime = rs.getString(3);
            Date datumZaposlenja = rs.getDate(4);
            Date datumROdjenja = rs.getDate(5);
            String adresa = rs.getString(6);
            RadnikEntity radnik = new RadnikEntity(id, ime, prezime, new java.util.Date(datumZaposlenja.getTime()), new java.util.Date(datumROdjenja.getTime()), adresa, r.getKorisnickoIme(), r.getLozinka());

            rs.close();
            ps.close();

            return radnik;
        }
        throw new Exception("Radnik ne postoji! Pogresili ste.!");
    }

    public List<StavkaPorudzbineEntity> vratiStavkeZaPorudzbinu(Long idP) throws Exception {
        List<StavkaPorudzbineEntity> lista = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + new StavkaPorudzbineEntity().getTableName() + new StavkaPorudzbineEntity().vratiStringZaJOIN() + " WHERE stavka_porudzbine.idPorudzbine_fk=" + idP;
        System.out.println(query);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        System.out.println(query);
        while (rs.next()) {
            lista.add((StavkaPorudzbineEntity) new StavkaPorudzbineEntity().vratiObjekat(rs));
            System.out.println((StavkaPorudzbineEntity) new StavkaPorudzbineEntity().vratiObjekat(rs));
        }
        rs.close();
        s.close();
        return lista;
    }

    public void izmeni(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
                .append(ide.getTableName())
                .append(" SET ")
                .append(ide.postaviVrednost())
                .append(" WHERE ")
                .append(ide.vratiPrimarniKljuc())
                .append("=")
                .append(ide.vratiVrednostPrimarnogKljucaString());

        String query = sb.toString();
        System.out.println("Query: " + query);
        Statement s = connection.createStatement();
        s.executeUpdate(query);
    }

    public boolean obrisi(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
                .append(ide.getTableName())
                .append(" WHERE ")
                .append(ide.vratiPrimarniKljuc())
                .append("=")
                .append(ide.vratiVrednostPrimarnogKljucaString());

        String query = sb.toString();
        System.out.println("Query: " + query);
        Statement s = connection.createStatement();
        s.executeUpdate(query);
        s.close();
        return true;
    }

    public IDomainEntity zapamtiSlozen(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement s = null;
        Statement s1 = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(ide.getTableName())
                    .append("(")
                    .append(ide.getColumnNamesForInsert())
                    .append(")")
                    .append(" VALUES ")
                    .append("(")
                    .append(ide.getColumnValuesForInsert())
                    .append(")");

            String query = sb.toString();
            System.out.println(query);
            s = connection.createStatement();
            s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            if (ide.isIdAutoincrement()) {
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    ide.setAutoincrementId(id);
                    System.out.println(id);
                    for (IDomainEntity ide2 : ide.vratiSlabeObjekte()) {
                        System.out.println(ide2);
                        System.out.println(ide2.getTableName());
                        System.out.println(ide2.getColumnNamesForInsert());
                        System.out.println(ide2.getColumnValuesForInsert());
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append("INSERT INTO ")
                                .append(ide2.getTableName())
                                .append("(")
                                .append(ide2.getColumnNamesForInsert())
                                .append(")")
                                .append(" VALUES ")
                                .append("(")
                                .append(ide2.getColumnValuesForInsert())
                                .append(")");

                        String query1 = sb1.toString();
                        System.out.println(query1);
                        s1 = connection.createStatement();
                        s1.executeUpdate(query1);
                    }
                    s1.close();
                    connection.commit();
                }
            }

            return ide;
        } catch (Exception e) {
            connection.rollback();
            throw new Exception("Save bill error. " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }

    }

    public void azurirajStatusPorudzbine(PorudzbinaEntity por) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE porudzbina SET status=? WHERE idPorudzbine=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, por.getStatus());
        ps.setLong(2, por.getIdPorudzbine());
        ps.executeUpdate();
    }

    public boolean obrisiStavkeZaPorudzbinu(PorudzbinaEntity porudzbina) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(true);
        String query = "DELETE FROM stavka_porudzbine WHERE idPorudzbine_fk=?";
        System.out.println(query);
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, porudzbina.getIdPorudzbine());
        System.out.println(porudzbina.getIdPorudzbine());
        ps.executeUpdate();
        return true;
    }

    public boolean ubaciStavku(StavkaPorudzbineEntity stavka) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(true);

        String query = "INSERT INTO stavka_porudzbine(rbrStavke,idPorudzbine_fk,kolicina,vrednost,idProizvoda_fk) VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        System.out.println(query);
        ps.setLong(1, stavka.getRbrStavke());
        ps.setLong(2, stavka.getPorudzbina().getIdPorudzbine());
        ps.setInt(3, stavka.getKolicina());
        ps.setDouble(4, stavka.getVrednost());
        ps.setLong(5, stavka.getProizvod().getIdProizvoda());
        System.out.println(stavka.getRbrStavke() + " " + stavka.getPorudzbina().getIdPorudzbine() + " " + stavka.getKolicina() + " " + stavka.getVrednost() + " " + stavka.getProizvod().getIdProizvoda());
        ps.executeUpdate();
        return true;

    }

    public List<PorudzbinaEntity> vratiSvePorudzbineZaKupca(String jmbgK) throws Exception{
        List<PorudzbinaEntity> lista = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + new PorudzbinaEntity().getTableName() + new PorudzbinaEntity().vratiStringZaJOIN()+" WHERE porudzbina.jmbgKupca_fk='"+jmbgK+"'";
        System.out.println(query);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        while (rs.next()) {
            lista.add((PorudzbinaEntity) new PorudzbinaEntity().vratiObjekat(rs));
        }
        rs.close();
        s.close();
        return lista;
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT * FROM porudzbina WHERE jmbgKupca_fk=?";
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setString(1, jmbgK);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            Long id = rs.getLong(1);
//            Date datumPor = rs.getDate(2);
//            String status = rs.getString(3);
//            Double ukIznos = rs.getDouble(4);
//            Double ukIznos = rs.getDouble(4);
//            Double ukIznos = rs.getDouble(4);
//            
//            String adresa = rs.getString(6);
//            RadnikEntity radnik = new RadnikEntity(id, ime, prezime, new java.util.Date(datumZaposlenja.getTime()), new java.util.Date(datumROdjenja.getTime()), adresa, r.getKorisnickoIme(), r.getLozinka());
//
//            rs.close();
//            ps.close();
//
//            return radnik;
    }

}
