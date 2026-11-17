/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Resep {
    private final String idResep;
    private String statusSiap;
    private final List<ItemResep> items = new ArrayList<>();

    public Resep(String idResep) {
        this.idResep = idResep;
        this.statusSiap = "MENUNGGU";
    }

    public String getIdResep() { return idResep; }
    public String getStatusSiap() { return statusSiap; }

    // Komposisi: ItemResep dibuat di dalam Resep
    public void tambahItem(Obat obat, int jumlah) {
        items.add(new ItemResep(obat, jumlah));
    }

    public int totalObat() {
        int total = 0;
        for (ItemResep item : items) {
            total += item.subtotal();
        }
        return total;
    }

    public void ubahStatusSiap(String statusBaru) {
        this.statusSiap = statusBaru;
    }

    public List<ItemResep> getItems() {
        return Collections.unmodifiableList(items);
    }
}
