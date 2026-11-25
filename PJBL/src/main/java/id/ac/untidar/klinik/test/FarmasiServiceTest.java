/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.test;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.*;
import id.ac.untidar.klinik.repository.*;
import id.ac.untidar.klinik.service.*;
import java.time.LocalDate;

public class FarmasiServiceTest {
    private static int lulus, gagal;

    public static void main(String[] args) {
        uji("Harga obat nol", () -> tolak(IllegalArgumentException.class,
                () -> new Obat("OB-02", "Obat uji", "3x1", 0, 10)));
        uji("Stok awal negatif", () -> tolak(IllegalArgumentException.class,
                () -> new Obat("OB-02", "Obat uji", "3x1", 10000, -1)));
        uji("Jumlah item resep nol", () -> {
            Data d = new Data();
            Resep r = new Resep("RSP-01");
            tolak(IllegalArgumentException.class, () -> r.tambahItem(d.obat, 0));
            cek(r.getItems().isEmpty());
        });
        uji("Stok mencukupi", () -> {
            Data d = new Data();
            Resep r = d.resep(2);
            d.service.selesaikanResep(r.getIdResep());
            cek(d.obat.getStok() == 48 && r.totalObat() == 20000
                    && "SELESAI".equals(r.getStatusSiap())
                    && d.repo.cariResep(r.getIdResep()) == r && d.rm.getResep() == r);
        });
        uji("Stok tidak mencukupi", () -> {
            Data d = new Data();
            Resep r = d.resep(60);
            tolak(IllegalStateException.class,
                    () -> d.service.selesaikanResep(r.getIdResep()));
            cek(d.obat.getStok() == 50 && "MENUNGGU".equals(r.getStatusSiap()));
        });
        uji("Resep diproses ulang", () -> {
            Data d = new Data();
            Resep r = d.resep(2);
            d.service.selesaikanResep(r.getIdResep());
            tolak(IllegalStateException.class,
                    () -> d.service.selesaikanResep(r.getIdResep()));
            cek(d.obat.getStok() == 48);
        });
        uji("Stok tidak cukup pada item kedua", () -> {
            Data d = new Data();
            Obat kedua = new Obat("OB-02", "Obat kedua", "3x1", 5000, 1);
            d.service.tambahObatKatalog(kedua);
            Resep r = new Resep("RSP-01");
            r.tambahItem(d.obat, 2);
            r.tambahItem(kedua, 2);
            d.service.terbitkanResep(d.rm, r);
            tolak(IllegalStateException.class,
                    () -> d.service.selesaikanResep(r.getIdResep()));
            cek(d.obat.getStok() == 50 && kedua.getStok() == 1
                    && "MENUNGGU".equals(r.getStatusSiap()));
        });
        System.out.println("\nPASS: " + lulus + " | FAIL: " + gagal);
        if (gagal > 0) throw new AssertionError("Ada pengujian gagal");
    }

    private static class Data {
        final KlinikRepository repo = new MemoryKlinikRepository();
        final KatalogObat katalog = new KatalogObat("Apotek Utama");
        final FarmasiService service = new FarmasiService(repo, katalog,
                new NotifikasiKonsol("TEST FARMASI"));
        final Obat obat = new Obat("OB-01", "Paracetamol", "3x1", 10000, 50);
        final Pasien pasien = new Pasien("RM-001", "Budi Santoso",
                LocalDate.of(2000, 5, 12), "Magelang");
        final Dokter dokter = new Dokter("D-01", "dr. Andi", "andi",
                "123", "Umum", 50000);
        final RekamMedis rm = new RekamMedis("RM-UJI-01", pasien, dokter,
                "Demam", "Febris");

        Data() { service.tambahObatKatalog(obat); }

        Resep resep(int jumlah) {
            Resep r = new Resep("RSP-01");
            r.tambahItem(obat, jumlah);
            service.terbitkanResep(rm, r);
            return r;
        }
    }

    private static void cek(boolean kondisi) {
        if (!kondisi) throw new AssertionError("Hasil tidak sesuai harapan");
    }

    private static void tolak(Class<? extends RuntimeException> tipe, Runnable aksi) {
        try {
            aksi.run();
        } catch (RuntimeException e) {
            if (!tipe.isInstance(e)) throw e;
            System.out.println("Ditolak: " + e.getMessage());
            return;
        }
        throw new AssertionError("Input seharusnya ditolak");
    }

    private static void uji(String nama, Runnable aksi) {
        System.out.println("\n=== " + nama + " ===");
        try {
            aksi.run();
            lulus++;
            System.out.println("PASS");
        } catch (RuntimeException | AssertionError e) {
            gagal++;
            System.out.println("FAIL: " + e.getMessage());
        }
    }
}

