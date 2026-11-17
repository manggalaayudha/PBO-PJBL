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

public class KatalogObat {
    private String namaUnit;
    private final List<Obat> daftarObat = new ArrayList<>();

    public KatalogObat(String namaUnit) {
        this.namaUnit = namaUnit;
    }

    // Agregasi: objek Obat dibuat di luar dan ditambahkan ke katalog
    public void tambahObat(Obat obat) {
        daftarObat.add(obat);
    }

    public Obat cariObat(String kodeObat) {
        for (Obat o : daftarObat) {
            if (o.getKodeObat().equalsIgnoreCase(kodeObat)) return o;
        }
        return null;
    }

    public List<Obat> getDaftarObat() {
        return Collections.unmodifiableList(daftarObat);
    }
}