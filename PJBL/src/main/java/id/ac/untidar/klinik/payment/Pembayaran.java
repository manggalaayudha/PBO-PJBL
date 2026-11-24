/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.payment;

/**
 *
 * @author manggala
 */
public interface Pembayaran {
    boolean bayar(int jumlah);
    String getMetode();
}
