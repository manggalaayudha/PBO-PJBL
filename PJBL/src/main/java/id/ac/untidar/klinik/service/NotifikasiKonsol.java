/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.service;

/**
 *
 * @author manggala
 */
public class NotifikasiKonsol implements Notifikasi {
    private final String prefixLabel;

    public NotifikasiKonsol(String prefixLabel) {
        this.prefixLabel = prefixLabel;
    }

    @Override
    public void kirim(String tujuan, String pesan) {
        System.out.println("[" + prefixLabel + "] Kepada " + tujuan + ": " + pesan);
    }
}
