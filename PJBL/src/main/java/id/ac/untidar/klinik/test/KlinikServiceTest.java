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
import id.ac.untidar.klinik.payment.*;
import id.ac.untidar.klinik.repository.*;
import id.ac.untidar.klinik.service.*;
import java.time.LocalDate;

public class KlinikServiceTest {
    private static int lulus, gagal;

    public static void main(String[] args) {
        uji("Pendaftaran pasien", () -> {
            Data d = new Data();
            d.service.daftarPasien(d.pasien);
            cek(d.repo.cariPasien("RM-001") == d.pasien);
        });
        uji("Nomor RM duplikat", () -> {
            Data d = new Data();
            d.service.daftarPasien(d.pasien);
            Pasien duplikat = new Pasien("RM-001", "Andi",
                    LocalDate.of(2001, 1, 1), "Magelang");
            tolak(IllegalArgumentException.class,
                    () -> d.service.daftarPasien(duplikat));
            cek(d.repo.cariPasien("RM-001") == d.pasien);
        });
        uji("Nama pasien kosong", () -> {
            tolak(IllegalArgumentException.class, () -> new Pasien(
                    "RM-002", "", LocalDate.of(2001, 1, 1), "Magelang"));
        });
        uji("Pembuatan antrean", () -> {
            Data d = new Data();
            Antrean a = d.antrean();
            cek(d.repo.semuaAntrean().contains(a)
                    && "MENUNGGU".equals(a.getStatus()));
        });
        uji("Antrean ganda", () -> {
            Data d = new Data();
            d.antrean();
            tolak(IllegalStateException.class,
                    () -> d.service.buatAntrean(d.pasien, d.dokter));
            cek(d.repo.semuaAntrean().size() == 1);
        });
        uji("Pembayaran tunai cukup", () -> {
            Data d = new Data();
            Antrean a = d.antrean();
            cek(a.hitungTotalTagihan(20000) == 70000);
            Pembayaran bayar = new BayarTunai(100000);
            cek(d.service.prosesPembayaran(a, 20000, bayar)
                    && "SELESAI".equals(a.getStatus()));
        });
        uji("Pembayaran tunai kurang", () -> {
            Data d = new Data();
            Antrean a = d.antrean();
            cek(!d.service.prosesPembayaran(a, 20000, new BayarTunai(50000))
                    && "MENUNGGU".equals(a.getStatus()));
        });
        System.out.println("\nPASS: " + lulus + " | FAIL: " + gagal);
        if (gagal > 0) throw new AssertionError("Ada pengujian gagal");
    }

    private static class Data {
        final KlinikRepository repo = new MemoryKlinikRepository();
        final KlinikService service = new KlinikService(repo,
                new NotifikasiKonsol("TEST KLINIK"));
        final Pasien pasien = new Pasien("RM-001", "Budi Santoso",
                LocalDate.of(2000, 5, 12), "Magelang");
        final Dokter dokter = new Dokter("D-01", "dr. Andi", "andi",
                "123", "Umum", 50000);

        Antrean antrean() {
            service.daftarPasien(pasien);
            return service.buatAntrean(pasien, dokter);
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

