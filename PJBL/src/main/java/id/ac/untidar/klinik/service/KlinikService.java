/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.service;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.Antrean;
import id.ac.untidar.klinik.model.Dokter;
import id.ac.untidar.klinik.model.Pasien;
import id.ac.untidar.klinik.model.RekamMedis;
import id.ac.untidar.klinik.payment.Pembayaran;
import id.ac.untidar.klinik.repository.KlinikRepository;

public class KlinikService {
    private final KlinikRepository repo;
    private final Notifikasi notif;
    private int urutanHarian = 0;

    public KlinikService(KlinikRepository repo, Notifikasi notif) {
        this.repo = repo;
        this.notif = notif;
    }

    public void daftarPasien(Pasien pasien) {
        repo.simpanPasien(pasien);
    }

    public Antrean buatAntrean(Pasien pasien, Dokter dokter) {
        urutanHarian++;
        Antrean antrean = new Antrean(urutanHarian, pasien, dokter);
        repo.simpanAntrean(antrean);
        notif.kirim(pasien.getNama(), "Nomor antrean Anda: " + urutanHarian);
        return antrean;
    }

    public void catatRekamMedis(RekamMedis rm) {
        repo.simpanRekamMedis(rm);
    }

    public boolean prosesPembayaran(Antrean antrean, int biayaObat, Pembayaran metodeBayar) {
        int total = antrean.hitungTotalTagihan(biayaObat);
        if (metodeBayar.bayar(total)) {
            antrean.ubahStatus("SELESAI");
            notif.kirim(antrean.getPasien().getNama(), "Pembayaran Rp" + total + " via " + metodeBayar.getMetode() + " berhasil.");
            return true;
        }
        return false;
    }
}
