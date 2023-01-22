package testprogrammingnds.javaweb.repository;

import testprogrammingnds.javaweb.model.Karyawan;

import java.util.List;

public interface KaryawanRepository {
    int save(Karyawan book);

    int update(Karyawan book);

    Karyawan findById(Long id);

    int deleteById(Long id);

    List<Karyawan> findAll(String name, String entryDate1, String entryDate2, String noHp);

    List<Karyawan> findBySearch(String title, String entryDate1, String entryDate2);
}
