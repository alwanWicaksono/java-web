package testprogrammingnds.javaweb.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
public class Karyawan {
    private long id;
    private String name;
    private String kodeKaryawan;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    private String noHp;
    private int limitReimbursement;
    private Date createdAt;
    private Date updatedAt;

    public Karyawan() {

    }

    public Karyawan(long id, String name, String kodeKaryawan, Date entryDate, String noHp, int limitReimbursement, Date createdAt, Date updatedAt){
        this.id = id;
        this.kodeKaryawan = kodeKaryawan;
        this.name = name;
        this.entryDate = entryDate;
        this.noHp = noHp;
        this.limitReimbursement = limitReimbursement;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Karyawan(String name, Date entryDate, String noHp, int limitReimbursement){
        this.name = name;
        this.entryDate = entryDate;
        this.noHp = noHp;
        this.limitReimbursement = limitReimbursement;
    }


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public  void setName(String name) { this.name = name; }

    public String getKodeKaryawan() { return kodeKaryawan; }

    public  void setKodeKaryawan(String kodeKaryawan) { this.kodeKaryawan = kodeKaryawan; }

    public Date getEntryDate() { return entryDate; }

    public  void setEntryDate(Date entryDate) { this.entryDate = entryDate; }

    public String getNoHp() { return noHp; }

    public  void setNoHp(String noHp) { this.noHp = noHp; }

    public int getLimitReimbursement() { return limitReimbursement; }

    public  void setLimitReimbursement(int limitReimbursement) { this.limitReimbursement = limitReimbursement; }

    public Date getCreatedAt() { return createdAt; }

    public  void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public  void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Karyawan [id=" + id + ", name=" + name + ", kodeKaryawan=" + kodeKaryawan + ", entryDate=" + entryDate + ", noHp=" + noHp + ", limitReimbursement=" + limitReimbursement + "]";
    }
}
