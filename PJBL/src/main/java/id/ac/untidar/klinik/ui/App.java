/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.ui;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.*;
import id.ac.untidar.klinik.payment.*;
import id.ac.untidar.klinik.repository.*;
import id.ac.untidar.klinik.service.*;
import id.ac.untidar.klinik.ui.KlinikView;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Composition Root: merakit seluruh objek & dependency injection
        KlinikRepository repo = new MemoryKlinikRepository();
        Notifikasi notif = new NotifikasiKonsol("INFO KLINIK");
        KatalogObat katalog = new KatalogObat("Apotek Utama");

        KlinikService klinikService = new KlinikService(repo, notif);
        FarmasiService farmasiService = new FarmasiService(repo, katalog, notif);
        KlinikView view = new KlinikView(klinikService, farmasiService);

        view.tampilkanMenuUtama();

        // Simulasi singkat alur Rancangan v2
        Pasien pasien = new Pasien("RM-001", "Budi Santoso", LocalDate.of(1998, 5, 12), "Magelang");
        Dokter dokter = new Dokter("D-01", "dr. Andi", "andi", "123", "Umum", 50000);
        Obat paracetamol = new Obat("OB-01", "Paracetamol 500mg", "3x1", 10000, 50);

        farmasiService.tambahObatKatalog(paracetamol);
        klinikService.daftarPasien(pasien);
        Antrean antrean = klinikService.buatAntrean(pasien, dokter);

        RekamMedis rm = new RekamMedis("RM-2026-01", pasien, dokter, "Demam", "Observasi Febris");
        klinikService.catatRekamMedis(rm);

        Resep resep = new Resep("RSP-01");
        resep.tambahItem(paracetamol, 2);
        farmasiService.terbitkanResep(rm, resep);
        farmasiService.selesaikanResep("RSP-01");

        Pembayaran tunai = new BayarTunai(100000);
        klinikService.prosesPembayaran(antrean, resep.totalObat(), tunai);
    }
}
