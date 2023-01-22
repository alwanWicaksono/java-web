package testprogrammingnds.javaweb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import testprogrammingnds.javaweb.model.Karyawan;

import java.util.List;

@Repository
public class JdbcKaryawanRepository implements KaryawanRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Karyawan karyawan) {
        return jdbcTemplate.update("INSERT INTO karyawan (name, entry_date, no_hp, limit_reimbursement) VALUES(?,?,?,?);\n" +
                        "update karyawan set kode_karyawan = concat('K-', (SELECT lpad(id::text, 3, '0'))) where name = ?;",
                new Object[] { karyawan.getName().toLowerCase(), karyawan.getEntryDate(), karyawan.getNoHp(), karyawan.getLimitReimbursement(), karyawan.getName()}
        );
    }

    @Override
    public int update(Karyawan karyawan) {
        return jdbcTemplate.update("UPDATE karyawan SET name=?, entry_date=?, no_hp=?, limit_reimbursement=? WHERE id=?",
                new Object[] { karyawan.getName(), karyawan.getEntryDate(), karyawan.getNoHp(), karyawan.getLimitReimbursement(), karyawan.getId() });
    }

    @Override
    public Karyawan findById(Long id) {
        try {
            Karyawan karyawan = jdbcTemplate.queryForObject("SELECT * FROM karyawan WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Karyawan.class), id);

            return karyawan;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM karyawan WHERE id=?", id);
    }

    @Override
    public List<Karyawan> findAll(String name, String entryDate1, String entryDate2) {
        String query = "SELECT * from karyawan";
        if(name != null || (entryDate1 != null && entryDate2 != null)){
            query += " WHERE (name ILIKE '%" + name + "%'";
            if(entryDate1.length() > 0 && entryDate2.length() > 0){
                query += " AND entry_date BETWEEN '" + entryDate1 + "' AND '"+ entryDate2 +"'";
            }
            query += ")";
        }
        query += " ORDER BY kode_karyawan ASC";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Karyawan.class));
    }
    @Override
    public List<Karyawan> findBySearch(String name, String entryDate1, String entryDate2) {
        String q = "SELECT * from karyawan WHERE (name ILIKE '%" + name + "%' OR entry_date BETWEEN '" + entryDate1 + "' AND '"+ entryDate2 +"')";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Karyawan.class));
    }
}