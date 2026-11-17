/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
public class Dokter extends Pengguna {
    private String spesialisasi;
    private int biayaJasa;

    public Dokter(String id, String nama, String username, String password, String spesialisasi, int biayaJasa) {
        super(id, nama, username, password);
        this.spesialisasi = spesialisasi;
        this.biayaJasa = biayaJasa;
    }

    public String getSpesialisasi() { return spesialisasi; }
    public int getBiayaJasa() { return biayaJasa; }

    @Override
    public String getPeran() {
        return "Dokter";
    }
}
