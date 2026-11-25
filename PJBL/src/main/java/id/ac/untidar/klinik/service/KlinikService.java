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

    public KlinikService(KlinikRepository repo,
                         Notifikasi notif) {
        this.repo = repo;
        this.notif = notif;
    }

    // Pendaftaran pasien
    public void daftarPasien(Pasien pasien) {

        if (pasien == null) {
            throw new IllegalArgumentException(
                "Data pasien tidak boleh kosong"
            );
        }

        // Validasi nomor rekam medis duplikat
        if (repo.cariPasien(pasien.getNoRM()) != null) {
            throw new IllegalArgumentException(
                "Nomor rekam medis sudah terdaftar"
            );
        }

        repo.simpanPasien(pasien);
    }

    // Pembuatan antrean
    public Antrean buatAntrean(Pasien pasien,
                               Dokter dokter) {

        // Validasi pasien dan dokter
        if (pasien == null || dokter == null) {
            throw new IllegalArgumentException(
                "Pasien dan dokter harus tersedia"
            );
        }

        // Memeriksa apakah pasien sudah terdaftar
        Pasien terdaftar =
                repo.cariPasien(pasien.getNoRM());

        if (terdaftar == null) {
            throw new IllegalArgumentException(
                "Pasien belum terdaftar"
            );
        }

        // Mencegah antrean aktif ganda
        for (Antrean a : repo.semuaAntrean()) {

            if (a.getPasien().getNoRM()
                    .equalsIgnoreCase(terdaftar.getNoRM())

                    && !"SELESAI".equals(a.getStatus())

                    && !"BATAL".equals(a.getStatus())) {

                throw new IllegalStateException(
                    "Pasien masih memiliki antrean aktif"
                );
            }
        }

        // Menggunakan data pasien dari repository
        pasien = terdaftar;

        // Membuat antrean setelah validasi berhasil
        urutanHarian++;

        Antrean antrean = new Antrean(
            urutanHarian,
            pasien,
            dokter
        );

        repo.simpanAntrean(antrean);

        notif.kirim(
            pasien.getNama(),
            "Nomor antrean Anda: " + urutanHarian
        );

        return antrean;
    }

    // Pencatatan rekam medis
    public void catatRekamMedis(RekamMedis rm) {
        repo.simpanRekamMedis(rm);
    }

    // Proses pembayaran
    public boolean prosesPembayaran(
            Antrean antrean,
            int biayaObat,
            Pembayaran metodeBayar) {

        int total = antrean.hitungTotalTagihan(biayaObat);

        if (metodeBayar.bayar(total)) {

            antrean.ubahStatus("SELESAI");

            notif.kirim(
                antrean.getPasien().getNama(),
                "Pembayaran Rp" + total
                + " via " + metodeBayar.getMetode()
                + " berhasil."
            );

            return true;
        }

        return false;
    }
}