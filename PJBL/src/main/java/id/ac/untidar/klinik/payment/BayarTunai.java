/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.payment;

/**
 *
 * @author manggala
 */
public class BayarTunai implements Pembayaran {
    private final int uangDiterima;

    public BayarTunai(int uangDiterima) {
        this.uangDiterima = uangDiterima;
    }

    @Override
    public boolean bayar(int jumlah) {
        return uangDiterima >= jumlah;
    }

    public int hitungKembalian(int jumlah) {
        return Math.max(0, uangDiterima - jumlah);
    }

    @Override
    public String getMetode() {
        return "Tunai";
    }
}