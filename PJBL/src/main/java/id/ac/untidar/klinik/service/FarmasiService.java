/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.service;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.*;
import id.ac.untidar.klinik.repository.KlinikRepository;
import java.util.LinkedHashMap;
import java.util.Map;

public class FarmasiService {
    private final KlinikRepository repo;
    private final KatalogObat katalog;
    private final Notifikasi notif;

    public FarmasiService(KlinikRepository repo, KatalogObat katalog,
                          Notifikasi notif) {
        this.repo = repo;
        this.katalog = katalog;
        this.notif = notif;
    }

    public void tambahObatKatalog(Obat obat) {
        if (obat == null)
            throw new IllegalArgumentException("Data obat tidak boleh kosong");
        if (katalog.cariObat(obat.getKodeObat()) != null)
            throw new IllegalArgumentException("Kode obat sudah terdaftar");
        katalog.tambahObat(obat);
    }

    public void tambahStokObat(String kodeObat, int jumlah) {
        Obat obat = katalog.cariObat(kodeObat);
        if (obat == null)
            throw new IllegalArgumentException("Obat tidak ditemukan");
        obat.tambahStok(jumlah);
    }

    public void terbitkanResep(RekamMedis rm, Resep resep) {
        if (rm == null || resep == null)
            throw new IllegalArgumentException("Rekam medis dan resep harus tersedia");
        if (resep.getItems().isEmpty())
            throw new IllegalArgumentException("Resep tidak boleh kosong");
        if (repo.cariResep(resep.getIdResep()) != null)
            throw new IllegalArgumentException("ID resep sudah terdaftar");
        if (rm.getResep() != null)
            throw new IllegalStateException("Rekam medis sudah memiliki resep");
        if (!"MENUNGGU".equals(resep.getStatusSiap()))
            throw new IllegalStateException("Status resep tidak valid");

        for (ItemResep item : resep.getItems()) {
            Obat obat = item.getObat();
            if (katalog.cariObat(obat.getKodeObat()) != obat)
                throw new IllegalArgumentException("Obat tidak terdaftar dalam katalog");
        }

        rm.setResep(resep);
        repo.simpanResep(resep);
    }

    public void selesaikanResep(String idResep) {
        Resep resep = repo.cariResep(idResep);
        if (resep == null)
            throw new IllegalArgumentException("Resep tidak ditemukan");
        if (!"MENUNGGU".equals(resep.getStatusSiap()))
            throw new IllegalStateException("Resep sudah selesai diproses");
        if (resep.getItems().isEmpty())
            throw new IllegalStateException("Resep tidak memiliki item obat");

        // Gabungkan kebutuhan obat yang sama.
        Map<Obat, Integer> kebutuhan = new LinkedHashMap<>();
        for (ItemResep item : resep.getItems()) {
            Obat obat = item.getObat();
            if (katalog.cariObat(obat.getKodeObat()) != obat)
                throw new IllegalStateException("Obat tidak terdaftar dalam katalog");
            kebutuhan.merge(obat, item.getJumlah(), Math::addExact);
        }

        // Validasi seluruh stok sebelum mengurangi stok apa pun.
        for (Map.Entry<Obat, Integer> data : kebutuhan.entrySet()) {
            Obat obat = data.getKey();
            if (!obat.isTersedia(data.getValue()))
                throw new IllegalStateException(
                    "Stok obat " + obat.getNama() + " tidak mencukupi");
        }

        for (Map.Entry<Obat, Integer> data : kebutuhan.entrySet()) {
            data.getKey().kurangiStok(data.getValue());
        }

        resep.ubahStatusSiap("SELESAI");
        notif.kirim("Pasien", "Resep " + idResep
            + " telah siap diambil di apotek.");
    }
}
