/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.service;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.ItemResep;
import id.ac.untidar.klinik.model.KatalogObat;
import id.ac.untidar.klinik.model.Obat;
import id.ac.untidar.klinik.model.RekamMedis;
import id.ac.untidar.klinik.model.Resep;
import id.ac.untidar.klinik.repository.KlinikRepository;

public class FarmasiService {
    private final KlinikRepository repo;
    private final KatalogObat katalog;
    private final Notifikasi notif;

    public FarmasiService(KlinikRepository repo, KatalogObat katalog, Notifikasi notif) {
        this.repo = repo;
        this.katalog = katalog;
        this.notif = notif;
    }

    public void tambahObatKatalog(Obat obat) {
        katalog.tambahObat(obat);
    }

    public void terbitkanResep(RekamMedis rm, Resep resep) {
        rm.setResep(resep);
        repo.simpanResep(resep);
    }

    public void selesaikanResep(String idResep) {
        Resep resep = repo.cariResep(idResep);
        if (resep != null) {
            for (ItemResep item : resep.getItems()) {
                item.getObat().kurangiStok(item.getJumlah());
            }
            resep.ubahStatusSiap("SELESAI");
            notif.kirim("Pasien", "Resep " + idResep + " telah siap diambil di apotek.");
        }
    }
}
