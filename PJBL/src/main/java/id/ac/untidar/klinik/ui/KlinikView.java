/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.ui;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.service.FarmasiService;
import id.ac.untidar.klinik.service.KlinikService;

public class KlinikView {
    private final KlinikService klinikService;
    private final FarmasiService farmasiService;

    public KlinikView(KlinikService klinikService, FarmasiService farmasiService) {
        this.klinikService = klinikService;
        this.farmasiService = farmasiService;
    }

    public void tampilkanMenuUtama() {
        System.out.println("=== SISTEM INFORMASI KLINIK & FARMASI (v2) ===");
        System.out.println("1. Pendaftaran & Antrean | 2. Pemeriksaan Dokter | 3. Farmasi | 4. Kasir");
    }
}
