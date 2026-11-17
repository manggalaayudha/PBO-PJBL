/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
import java.time.LocalDateTime;

public class RekamMedis {
    private final String idRM;
    private final LocalDateTime tanggal;
    private final Pasien pasien;
    private final Dokter dokter;
    private String keluhan;
    private String diagnosis;
    private Resep resep;

    public RekamMedis(String idRM, Pasien pasien, Dokter dokter, String keluhan, String diagnosis) {
        this.idRM = idRM;
        this.tanggal = LocalDateTime.now();
        this.pasien = pasien;
        this.dokter = dokter;
        this.keluhan = keluhan;
        this.diagnosis = diagnosis;
    }

    public String getIdRM() { return idRM; }
    public Pasien getPasien() { return pasien; }
    public Resep getResep() { return resep; }

    public void setResep(Resep resep) {
        this.resep = resep;
    }

    public String getRingkasan() {
        return "[" + idRM + "] " + pasien.getNama() + " | Diagnosis: " + diagnosis;
    }
}
