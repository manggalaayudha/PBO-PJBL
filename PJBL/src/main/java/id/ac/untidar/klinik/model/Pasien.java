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

    public Pasien(String noRM, String nama, LocalDate tglLahir, String alamat) {
        this.noRM = noRM;
        this.nama = nama;
        this.tglLahir = tglLahir;
        this.alamat = alamat;
    }

    public String getNoRM() { return noRM; }
    public String getNama() { return nama; }

    public String getInfo() {
        return noRM + " - " + nama + " (" + alamat + ")";
    }
}