/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.untidar.klinik.model;

/**
 *
 * @author manggala
 */

public class Resepsionis extends Pengguna {
    private String shift;

    public Resepsionis(String id, String nama, String username, String password, String shift) {
        super(id, nama, username, password);
        this.shift = shift;
    }

    public String getShift() { return shift; }

    @Override
    public String getPeran() {
        return "Resepsionis";
    }
}

