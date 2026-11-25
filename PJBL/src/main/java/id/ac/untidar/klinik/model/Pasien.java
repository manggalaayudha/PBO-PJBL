/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
import java.time.LocalDate;

public class Pasien {

    private final String noRM;
    private String nama;
    private LocalDate tglLahir;
    private String alamat;

    public Pasien(String noRM, String nama,
                  LocalDate tglLahir, String alamat) {

        // Validasi nomor rekam medis
        if (noRM == null || noRM.isBlank()) {
            throw new IllegalArgumentException(
                "Nomor rekam medis tidak boleh kosong"
            );
        }

        // Validasi nama pasien
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException(
                "Nama pasien tidak boleh kosong"
            );
        }

        // Validasi tanggal lahir
        if (tglLahir == null ||
                tglLahir.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                "Tanggal lahir tidak valid"
            );
        }

        this.noRM = noRM.trim();
        this.nama = nama;
        this.tglLahir = tglLahir;
        this.alamat = alamat;
    }

    public String getNoRM() {
        return noRM;
    }

    public String getNama() {
        return nama;
    }

    public String getInfo() {
        return noRM + " - " + nama + " (" + alamat + ")";
    }
}