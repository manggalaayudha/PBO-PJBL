/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
public class Obat {
    private final String kodeObat;
    private String nama;
    private String dosis;
    private int harga;
    private int stok;

    public Obat(String kodeObat, String nama, String dosis, int harga, int stok) {
        if (harga <= 0) throw new IllegalArgumentException("Harga harus > 0");
        if (stok < 0) throw new IllegalArgumentException("Stok tidak boleh negatif");
        this.kodeObat = kodeObat;
        this.nama = nama;
        this.dosis = dosis;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeObat() { return kodeObat; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getStok() { return stok; }

    public boolean isTersedia(int jumlah) {
        return stok >= jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (!isTersedia(jumlah)) {
            throw new IllegalStateException("Stok obat " + nama + " tidak mencukupi");
        }
        this.stok -= jumlah;
    }
}

