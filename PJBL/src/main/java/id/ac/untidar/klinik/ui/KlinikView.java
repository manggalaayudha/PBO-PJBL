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
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class KlinikView {
    private final KlinikService klinikService;
    private final FarmasiService farmasiService;
    private final KlinikRepository repo;
    private final KatalogObat katalog;
    private final Dokter dokter;
    private final Scanner input = new Scanner(System.in);
    private final Map<Integer, RekamMedis> pemeriksaan = new HashMap<>();

    public KlinikView(KlinikService klinikService, FarmasiService farmasiService,
                      KlinikRepository repo, KatalogObat katalog, Dokter dokter) {
        this.klinikService = klinikService;
        this.farmasiService = farmasiService;
        this.repo = repo;
        this.katalog = katalog;
        this.dokter = dokter;
    }

    public void tampilkanMenuUtama() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   SISTEM MANAJEMEN KLINIK & FARMASI V3");
            System.out.println("========================================");
            System.out.println("1. Pendaftaran pasien");
            System.out.println("2. Ambil antrean");
            System.out.println("3. Daftar antrean");
            System.out.println("4. Pemeriksaan dokter");
            System.out.println("5. Pemrosesan resep");
            System.out.println("6. Daftar stok obat");
            System.out.println("7. Tambah stok obat");
            System.out.println("8. Pembayaran");
            System.out.println("0. Keluar");
            if (!input.hasNextLine()) return;
            try {
                switch (bacaInt("Pilih menu: ")) {
                    case 1: daftarPasien(); break;
                    case 2: ambilAntrean(); break;
                    case 3: tampilkanAntrean(); break;
                    case 4: pemeriksaanDokter(); break;
                    case 5: prosesResep(); break;
                    case 6: tampilkanStok(); break;
                    case 7: tambahStok(); break;
                    case 8: pembayaran(); break;
                    case 0:
                        System.out.println("Terima kasih telah menggunakan sistem.");
                        return;
                    default: System.out.println("Pilihan menu tidak tersedia.");
                }
            } catch (IllegalArgumentException | IllegalStateException
                     | java.time.DateTimeException e) {
                System.out.println("Proses gagal: " + e.getMessage());
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Input telah berakhir.");
                return;
            }
        }
    }

    private String baca(String pesan) {
        System.out.print(pesan);
        return input.nextLine().trim();
    }

    private int bacaInt(String pesan) {
        return Integer.parseInt(baca(pesan));
    }

    private void daftarPasien() {
        System.out.println("\n=== PENDAFTARAN PASIEN ===");
        String noRM = baca("Nomor rekam medis: ");
        String nama = baca("Nama pasien: ");
        LocalDate tglLahir = LocalDate.parse(baca("Tanggal lahir (YYYY-MM-DD): "));
        String alamat = baca("Alamat: ");
        Pasien pasien = new Pasien(noRM, nama, tglLahir, alamat);
        klinikService.daftarPasien(pasien);
        System.out.println("Pendaftaran pasien berhasil.");
        System.out.println(pasien.getInfo());
    }

    private void ambilAntrean() {
        System.out.println("\n=== AMBIL ANTREAN ===");
        Pasien pasien = repo.cariPasien(baca("Nomor rekam medis: "));
        if (pasien == null)
            throw new IllegalArgumentException("Pasien belum terdaftar");
        Antrean antrean = klinikService.buatAntrean(pasien, dokter);
        System.out.println("Nomor antrean: " + antrean.getNoAntrean());
        System.out.println("Dokter: " + dokter.getNama());
        System.out.println("Status: " + antrean.getStatus());
    }

    private void tampilkanAntrean() {
        System.out.println("\n=== DAFTAR ANTREAN ===");
        if (repo.semuaAntrean().isEmpty()) {
            System.out.println("Belum ada antrean.");
            return;
        }
        for (Antrean a : repo.semuaAntrean()) {
            System.out.println("No. " + a.getNoAntrean() + " | "
                    + a.getPasien().getNama() + " | " + a.getStatus());
        }
    }

    private Antrean cariAntrean(int nomor) {
        for (Antrean a : repo.semuaAntrean()) {
            if (a.getNoAntrean() == nomor) return a;
        }
        throw new IllegalArgumentException("Nomor antrean tidak ditemukan");
    }

    private void pemeriksaanDokter() {
        System.out.println("\n=== PEMERIKSAAN DOKTER ===");
        tampilkanAntrean();
        Antrean a = cariAntrean(bacaInt("Nomor antrean: "));
        if (!"MENUNGGU".equals(a.getStatus())
                || pemeriksaan.containsKey(a.getNoAntrean()))
            throw new IllegalStateException("Antrean sudah diperiksa atau tidak aktif");
        String keluhan = baca("Keluhan pasien: ");
        String diagnosis = baca("Diagnosis: ");
        if (keluhan.isBlank() || diagnosis.isBlank())
            throw new IllegalArgumentException("Keluhan dan diagnosis tidak boleh kosong");
        RekamMedis rm = new RekamMedis("RM-PERIKSA-" + a.getNoAntrean(),
                a.getPasien(), a.getDokter(), keluhan, diagnosis);
        tampilkanStok();
        int jumlahJenis = bacaInt("Jumlah jenis obat (0 jika tanpa resep): ");
        if (jumlahJenis < 0)
            throw new IllegalArgumentException("Jumlah jenis obat tidak boleh negatif");
        if (jumlahJenis > 0) {
            Resep resep = new Resep("RSP-" + a.getNoAntrean());
            for (int i = 0; i < jumlahJenis; i++) {
                System.out.println("\nObat ke-" + (i + 1));
                Obat obat = katalog.cariObat(baca("Kode obat: "));
                if (obat == null)
                    throw new IllegalArgumentException("Obat tidak ditemukan");
                resep.tambahItem(obat, bacaInt("Jumlah obat: "));
            }
            farmasiService.terbitkanResep(rm, resep);
            System.out.println("Resep berhasil dibuat: " + resep.getIdResep());
        }
        klinikService.catatRekamMedis(rm);
        pemeriksaan.put(a.getNoAntrean(), rm);
        a.ubahStatus("DIPERIKSA");
        System.out.println(rm.getRingkasan());
        System.out.println("Pemeriksaan dokter selesai.");
    }

    private void prosesResep() {
        System.out.println("\n=== FARMASI ===");
        boolean adaResep = false;
        for (RekamMedis rm : pemeriksaan.values()) {
            Resep r = rm.getResep();
            if (r != null) {
                adaResep = true;
                System.out.println(r.getIdResep() + " | "
                        + rm.getPasien().getNama() + " | " + r.getStatusSiap());
            }
        }
        if (!adaResep) {
            System.out.println("Belum ada resep.");
            return;
        }
        farmasiService.selesaikanResep(baca("ID resep: "));
        System.out.println("Resep berhasil diproses.");
    }

    private void tampilkanStok() {
        System.out.println("\n=== DAFTAR STOK OBAT ===");
        for (Obat obat : katalog.getDaftarObat()) {
            System.out.println(obat.getKodeObat() + " | " + obat.getNama()
                    + " | Harga: Rp" + obat.getHarga() + " | Stok: " + obat.getStok());
        }
    }

    private void tambahStok() {
        tampilkanStok();
        String kode = baca("Kode obat: ");
        int jumlah = bacaInt("Jumlah penambahan stok: ");
        farmasiService.tambahStokObat(kode, jumlah);
        System.out.println("Stok obat berhasil ditambahkan.");
        tampilkanStok();
    }

    private void pembayaran() {
        System.out.println("\n=== PEMBAYARAN ===");
        Antrean a = cariAntrean(bacaInt("Nomor antrean: "));
        if ("SELESAI".equals(a.getStatus()))
            throw new IllegalStateException("Tagihan sudah dibayar");
        RekamMedis rm = pemeriksaan.get(a.getNoAntrean());
        if (rm == null)
            throw new IllegalStateException("Pasien belum menjalani pemeriksaan");
        Resep resep = rm.getResep();
        if (resep != null && !"SELESAI".equals(resep.getStatusSiap()))
            throw new IllegalStateException("Resep belum selesai diproses");
        int biayaObat = resep == null ? 0 : resep.totalObat();
        int total = a.hitungTotalTagihan(biayaObat);
        System.out.println("Biaya dokter : Rp" + a.getDokter().getBiayaJasa());
        System.out.println("Biaya obat   : Rp" + biayaObat);
        System.out.println("Total        : Rp" + total);
        System.out.println("1. Tunai");
        System.out.println("2. Asuransi");
        int pilihan = bacaInt("Metode pembayaran: ");
        Pembayaran metode;
        int uangDiterima = 0;
        if (pilihan == 1) {
            uangDiterima = bacaInt("Uang diterima: Rp");
            if (uangDiterima < 0)
                throw new IllegalArgumentException("Uang tidak boleh negatif");
            metode = new BayarTunai(uangDiterima);
        } else if (pilihan == 2) {
            String noPolis = baca("Nomor polis: ");
            String provider = baca("Provider asuransi: ");
            if (provider.isBlank())
                throw new IllegalArgumentException("Provider tidak boleh kosong");
            metode = new BayarAsuransi(noPolis, provider);
        } else {
            throw new IllegalArgumentException("Metode pembayaran tidak valid");
        }
        if (!klinikService.prosesPembayaran(a, biayaObat, metode)) {
            System.out.println("Pembayaran gagal. Periksa uang atau polis.");
            return;
        }
        System.out.println("\n================================");
        System.out.println("       STRUK PEMBAYARAN");
        System.out.println("================================");
        System.out.println("Pasien       : " + a.getPasien().getNama());
        System.out.println("No. RM       : " + a.getPasien().getNoRM());
        System.out.println("No. antrean  : " + a.getNoAntrean());
        System.out.println("Biaya dokter : Rp" + a.getDokter().getBiayaJasa());
        System.out.println("Biaya obat   : Rp" + biayaObat);
        System.out.println("Total        : Rp" + total);
        System.out.println("Metode       : " + metode.getMetode());
        if (metode instanceof BayarTunai) {
            BayarTunai tunai = (BayarTunai) metode;
            System.out.println("Uang diterima: Rp" + uangDiterima);
            System.out.println("Kembalian    : Rp" + tunai.hitungKembalian(total));
        }
        System.out.println("Status       : LUNAS");
        System.out.println("================================");
    }
}