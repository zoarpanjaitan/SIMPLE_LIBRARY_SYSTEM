package mvc.Model;

import java.util.Date;

public class Peminjaman {

    /**
     * @return the idPeminjaman
     */
    public Integer getIdPeminjaman() {
        return idPeminjaman;
    }

    /**
     * @param idPeminjaman the idPeminjaman to set
     */
    public void setIdPeminjaman(Integer idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    /**
     * @return the idBuku
     */
    public Integer getIdBuku() {
        return idBuku;
    }

    /**
     * @param idBuku the idBuku to set
     */
    public void setIdBuku(Integer idBuku) {
        this.idBuku = idBuku;
    }

    /**
     * @return the namaPeminjam
     */
    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    /**
     * @param namaPeminjam the namaPeminjam to set
     */
    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }

    /**
     * @return the noHp
     */
    public String getNoHp() {
        return noHp;
    }

    /**
     * @param noHp the noHp to set
     */
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    /**
     * @return the tglPeminjaman
     */
    public Date getTglPeminjaman() {
        return tglPeminjaman;
    }

    /**
     * @param tglPeminjaman the tglPeminjaman to set
     */
    public void setTglPeminjaman(Date tglPeminjaman) {
        this.tglPeminjaman = tglPeminjaman;
    }

    /**
     * @return the tglPengembalian
     */
    public Date getTglPengembalian() {
        return tglPengembalian;
    }

    /**
     * @param tglPengembalian the tglPengembalian to set
     */
    public void setTglPengembalian(Date tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the judulBuku
     */
    public String getJudulBuku() {
        return judulBuku;
    }

    /**
     * @param judulBuku the judulBuku to set
     */
    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }
    private Integer idPeminjaman;
    private Integer idBuku;
    private String namaPeminjam;
    private String noHp;
    private Date tglPeminjaman;
    private Date tglPengembalian;
    private String status;

    // Variabel tambahan ini akan kita gunakan nanti
    // untuk mempermudah menampilkan judul buku di tabel peminjaman.
    private String judulBuku;
}