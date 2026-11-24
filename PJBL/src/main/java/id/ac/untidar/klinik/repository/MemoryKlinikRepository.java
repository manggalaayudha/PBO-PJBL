/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.repository;

/**
 *
 * @author manggala
 */
import id.ac.untidar.klinik.model.Antrean;
import id.ac.untidar.klinik.model.Pasien;
import id.ac.untidar.klinik.model.RekamMedis;
import id.ac.untidar.klinik.model.Resep;
import java.util.ArrayList;
import java.util.List;

public class MemoryKlinikRepository implements KlinikRepository {
    private final List<Pasien> daftarPasien = new ArrayList<>();
    private final List<Antrean> daftarAntrean = new ArrayList<>();
    private final List<RekamMedis> daftarRM = new ArrayList<>();
    private final List<Resep> daftarResep = new ArrayList<>();

    @Override
    public void simpanPasien(Pasien pasien) { daftarPasien.add(pasien); }

    @Override
    public Pasien cariPasien(String noRM) {
        for (Pasien p : daftarPasien) {
            if (p.getNoRM().equalsIgnoreCase(noRM)) return p;
        }
        return null;
    }

    @Override
    public void simpanAntrean(Antrean antrean) { daftarAntrean.add(antrean); }

    @Override
    public List<Antrean> semuaAntrean() { return new ArrayList<>(daftarAntrean); }

    @Override
    public void simpanRekamMedis(RekamMedis rm) { daftarRM.add(rm); }

    @Override
    public void simpanResep(Resep resep) { daftarResep.add(resep); }

    @Override
    public Resep cariResep(String idResep) {
        for (Resep r : daftarResep) {
            if (r.getIdResep().equalsIgnoreCase(idResep)) return r;
        }
        return null;
    }
}

