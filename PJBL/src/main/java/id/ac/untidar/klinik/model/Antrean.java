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

public class Antrean {
    private final int noAntrean;
    private final LocalDate tanggal;
    private String status;
    private final Pasien pasien;
    private final Dokter dokter;

    public Antrean(int noAntrean, Pasien pasien, Dokter dokter) {
        this.noAntrean = noAntrean;
        this.tanggal = LocalDate.now();
        this.status = "MENUNGGU";
        this.pasien = pasien;
        this.dokter = dokter;
    }

    public int getNoAntrean() { return noAntrean; }
    public String getStatus() { return status; }
    public Pasien getPasien() { return pasien; }
    public Dokter getDokter() { return dokter; }

    public void ubahStatus(String statusBaru) {
        this.status = statusBaru;
    }

    public int hitungTotalTagihan(int totalBiayaObat) {
        return dokter.getBiayaJasa() + totalBiayaObat;
    }
}