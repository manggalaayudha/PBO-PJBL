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
import java.util.List;

public interface KlinikRepository {
    void simpanPasien(Pasien pasien);
    Pasien cariPasien(String noRM);
    
    void simpanAntrean(Antrean antrean);
    List<Antrean> semuaAntrean();
    
    void simpanRekamMedis(RekamMedis rm);
    
    void simpanResep(Resep resep);
    Resep cariResep(String idResep);
}

