/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
public class ItemResep {
    private final Obat obat;
    private final int jumlah;

    public ItemResep(Obat obat, int jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah obat minimal 1");
        this.obat = obat;
        this.jumlah = jumlah;
    }

    public Obat getObat() { return obat; }
    public int getJumlah() { return jumlah; }

    public int subtotal() {
        return obat.getHarga() * jumlah;
    }
}