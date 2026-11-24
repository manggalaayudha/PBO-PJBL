/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.payment;

/**
 *
 * @author manggala
 */
public class BayarAsuransi implements Pembayaran {
    private final String noPolis;
    private final String provider;

    public BayarAsuransi(String noPolis, String provider) {
        this.noPolis = noPolis;
        this.provider = provider;
    }

    public boolean validasiPolis() {
        return noPolis != null && !noPolis.isBlank();
    }

    @Override
    public boolean bayar(int jumlah) {
        return validasiPolis();
    }

    @Override
    public String getMetode() {
        return "Asuransi (" + provider + ")";
    }
}
