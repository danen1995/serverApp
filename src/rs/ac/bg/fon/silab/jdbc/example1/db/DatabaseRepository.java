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
import rs.ac.bg.fon.silab.jdbc.example1.domen.RadnikEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.StavkaPorudzbineEntity;


/**
 *
 * @author FON
 */
public class DatabaseRepository {

//    public void saveBill(BillEntity billEntity) throws Exception {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        connection.setAutoCommit(false);
//        PreparedStatement ps = null;
//        try {
//            String queryBill = "INSERT INTO bill(date_created,time_created,user_created,amount,company_fk) values(?,?,?,?,?)";
//            ps = connection.prepareStatement(queryBill, Statement.RETURN_GENERATED_KEYS);
//            ps.setDate(1, Date.valueOf(billEntity.getCreatedDate()));
//            ps.setTime(2, Time.valueOf(billEntity.getCreatedTime()));
//            ps.setString(3, billEntity.getCreatedUser());
//            ps.setDouble(4, billEntity.getAmount());
//            ps.setLong(5, billEntity.getCompanyEntity().getId());
//            System.out.println(ps);
//
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                Long billId = rs.getLong(1);
//                System.out.println(ps);
//
//                String queryBillItem = "INSERT "
//                        + "INTO bill_items(bill_id, item_id, product_fk, quantity, amount, unit_measure) "
//                        + "VALUES(?, ?, ?, ?, ?, ?)";
//                PreparedStatement pstmt = connection.prepareStatement(queryBillItem);
//
//                for (BillItemlEntity billItem : billEntity.getItems()) {
//                    pstmt.setLong(1, billId);
//                    pstmt.setInt(2, billItem.getItemId());
//                    pstmt.setString(3, billItem.getProductEntity().getId());
//                    pstmt.setDouble(4, billItem.getQuantity());
//                    pstmt.setDouble(5, billItem.getAmount());
//                    pstmt.setString(6, billItem.getUnitMeasure().toString());
//
//                    pstmt.executeUpdate();
//                }
//                
//                pstmt.close();
//                
//                connection.commit();
//            }
//        } catch (Exception e) {
//            connection.rollback();
//            throw new Exception("Save bill error. " + e.getMessage());
//        } finally {
//            if (ps != null) {
//                ps.close();
//            }
//        }
//
//    }
//
//    public void saveCity(Long code, String name) throws Exception {
//
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("INSERT INTO city VALUES")
//                .append("(")
//                .append(code)
//                .append(",")
//                .append("'")
//                .append(name)
//                .append("'")
//                .append(")");
//
//        String query = sb.toString();
//        System.out.println(query);
//
//        Statement statement = connection.createStatement();
//        statement.executeUpdate(query);
//        statement.close();
//
//    }
//
//    public List<CompanyEntity> findAllCompanies() throws Exception {
//        List<CompanyEntity> companies = new ArrayList<>();
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT * FROM company ORDER BY name";
//        Statement s = connection.createStatement();
//
//        ResultSet rs = s.executeQuery(query);
//        while (rs.next()) {
//            Long companyId = rs.getLong("id");
//            String name = rs.getString("name");
//            String address = rs.getString("address");
//            String vat = rs.getString("vat");
//            String identNumber = rs.getString("ident_number");
//            Long code = rs.getLong("code_fk");
//            CityEntity city = findCityByCode(code);
//            CompanyEntity company = new CompanyEntity(companyId, name, address, vat, identNumber, city);
//            companies.add(company);
//        }
//        rs.close();
//        s.close();
//        return companies;
//    }
//
//    public CityEntity findCityByCode(Long code) throws Exception {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT * FROM city WHERE code=?";
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setLong(1, code);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            Long cityPtt = rs.getLong("code");
//            String name = rs.getString("name");
//            CityEntity city = new CityEntity(cityPtt, name);
//            rs.close();
//            ps.close();
//
//            return city;
//        }
//        throw new Exception("City with this code does not exist!");
//    }
//
//    public CompanyEntity findCompanyByID(Long id) throws Exception {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT * FROM company WHERE id=?";
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setLong(1, id);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            Long companyId = rs.getLong("id");
//            String name = rs.getString("name");
//            String address = rs.getString("address");
//            String vat = rs.getString("vat");
//            String identNumber = rs.getString("ident_number");
//            Long code = rs.getLong("code_fk");
//            CityEntity city = findCityByCode(code);
//            CompanyEntity company = new CompanyEntity(companyId, name, address, vat, identNumber, city);
//            rs.close();
//            ps.close();
//            return company;
//        }
//        throw new Exception("Kompanija sa ovim id-jem ne postoji!");
//    }
//
//    public CompanyEntity saveCompany(CompanyEntity company) throws Exception {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//
//        String query = "INSERT INTO company(name,address,vat,ident_number,code_fk) VALUES (?,?,?,?,?)";
//        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//
//        ps.setString(1, company.getName());
//        ps.setString(2, company.getAddress());
//        ps.setString(3, company.getVat());
//        ps.setString(4, company.getIdentNumber());
//        ps.setLong(5, company.getCity().getCode());
//
//        ps.executeUpdate();
//        ResultSet rs = ps.getGeneratedKeys();
//        if (rs.next()) {
//            Long companyID = rs.getLong(1);
//            System.out.println("Nova kompanija ima ID: " + companyID);
//            ps.close();
//
//            return findCompanyByID(companyID);
//        }
//        throw new Exception("Error in generating key!");
//
//    }
//
//    public CompanyEntity findCompanyByIDInnerJoin(Long id) throws Exception {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT company.id,company.name,company.address,company.vat,company.ident_number,city.code,city.name AS city FROM company INNER JOIN city ON company.code_fk=city.code WHERE company.id=?";
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setLong(1, id);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            Long companyId = rs.getLong("id");
//            String name = rs.getString("name");
//            String address = rs.getString("address");
//            String vat = rs.getString("vat");
//            String identNumber = rs.getString("ident_number");
//            Long code = rs.getLong("code");
//            String cityName = rs.getString("city");
//            CityEntity city = new CityEntity(code, cityName);
//            CompanyEntity company = new CompanyEntity(companyId, name, address, vat, identNumber, city);
//            rs.close();
//            ps.close();
//            return company;
//        }
//        throw new Exception("Company does not exist!");
//    }
//
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
//    
//    public List<ProductEntity> getAllProduct() throws Exception {
//        ArrayList<ProductEntity> products = new ArrayList<ProductEntity>();
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        String query = "SELECT * FROM product";
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery(query);
//        while (rs.next()) {
//            String productId = rs.getString("id");
//            String name = rs.getString("name");
//            Double price = rs.getDouble("price");
//            products.add(new ProductEntity(productId, name, price));
//        }
//        rs.close();
//        statement.close();
//        return products;
//    }
//    
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
    
    public void startTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
    }
    
    public void commitTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().commit();
    }
    
    public void rollbackTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().rollback();
    }

    private IDomainEntity findById(IDomainEntity ide) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IDomainEntity nadjiPoIDu(IDomainEntity ide, Long x) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
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
        String query = "SELECT * FROM " + ide.getTableName() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
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
        String query = "SELECT * FROM " + ide.getTableName() + " WHERE " + ide.vratiPrimarniKljuc() + "=?";
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
        String query = "SELECT * FROM " + ide.getTableName()+ide.vratiStringZaJOIN();;
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        while (rs.next()) {
            lista.add(ide.vratiObjekat(rs));
        }
        rs.close();
        s.close();
        return lista;
    }

    public RadnikEntity logovanje(RadnikEntity r) throws Exception{
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
            RadnikEntity radnik = new RadnikEntity(id, ime, prezime, new java.util.Date(datumZaposlenja.getTime()), new java.util.Date(datumROdjenja.getTime()),adresa,r.getKorisnickoIme(),r.getLozinka());

            rs.close();
            ps.close();

            return radnik;
        }
        throw new Exception("Radnik ne postoji! Pogresili ste.!");
    }

    public List<StavkaPorudzbineEntity> vratiStavkeZaPorudzbinu(Long idP) throws Exception{
        List<StavkaPorudzbineEntity> lista = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        System.out.println(new StavkaPorudzbineEntity().getTableName());
        String query = "SELECT * FROM " + new StavkaPorudzbineEntity().getTableName()+ " WHERE idPorudzbine_fk="+idP;
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
}
