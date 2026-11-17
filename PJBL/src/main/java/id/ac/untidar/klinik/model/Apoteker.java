/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */
public class Apoteker extends Pengguna {
    private String noSIPA;

    public Apoteker(String id, String nama, String username, String password, String noSIPA) {
        super(id, nama, username, password);
        this.noSIPA = noSIPA;
    }

    public String getNoSIPA() { return noSIPA; }

    @Override
    public String getPeran() {
        return "Apoteker";
    }
}

