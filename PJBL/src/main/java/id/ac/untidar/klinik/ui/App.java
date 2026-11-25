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
import id.ac.untidar.klinik.repository.*;
import id.ac.untidar.klinik.service.*;

public class App {
    public static void main(String[] args) {
        KlinikRepository repo = new MemoryKlinikRepository();
        Notifikasi notif = new NotifikasiKonsol("INFO KLINIK");
        KatalogObat katalog = new KatalogObat("Apotek Utama");
        KlinikService klinikService = new KlinikService(repo, notif);
        FarmasiService farmasiService = new FarmasiService(repo, katalog, notif);

        Dokter dokter = new Dokter("D-01", "dr. Andi", "andi", "123",
                "Umum", 50000);

        farmasiService.tambahObatKatalog(
                new Obat("OB-01", "Paracetamol 500mg", "3x1", 10000, 50));
        farmasiService.tambahObatKatalog(
                new Obat("OB-02", "Amoxicillin", "3x1", 15000, 30));
        farmasiService.tambahObatKatalog(
                new Obat("OB-03", "Vitamin C", "1x1", 5000, 40));

        KlinikView view = new KlinikView(klinikService, farmasiService,
                repo, katalog, dokter);
        view.tampilkanMenuUtama();
    }
}
