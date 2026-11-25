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
        if (idResep == null || idResep.isBlank())
            throw new IllegalArgumentException("ID resep tidak boleh kosong");
        this.idResep = idResep.trim();
        this.statusSiap = "MENUNGGU";
    }

    public String getIdResep() { return idResep; }
    public String getStatusSiap() { return statusSiap; }

    public void tambahItem(Obat obat, int jumlah) {
        if (!"MENUNGGU".equals(statusSiap))
            throw new IllegalStateException(
                "Resep yang sudah selesai tidak dapat diubah");
        if (obat == null)
            throw new IllegalArgumentException("Obat tidak boleh kosong");
        items.add(new ItemResep(obat, jumlah));
    }

    public int totalObat() {
        int total = 0;
        for (ItemResep item : items) total += item.subtotal();
        return total;
    }

    public void ubahStatusSiap(String statusBaru) {
        if (!"MENUNGGU".equals(statusBaru) && !"SELESAI".equals(statusBaru))
            throw new IllegalArgumentException("Status resep tidak valid");
        if ("SELESAI".equals(statusSiap) && !"SELESAI".equals(statusBaru))
            throw new IllegalStateException("Resep selesai tidak dapat dikembalikan");
        this.statusSiap = statusBaru;
    }

    public List<ItemResep> getItems() {
        return Collections.unmodifiableList(items);
    }
}