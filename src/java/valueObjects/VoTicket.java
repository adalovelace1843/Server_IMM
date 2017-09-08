/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valueObjects;

import java.io.Serializable;

/**
 *
 * @author e299227
 */
public class VoTicket implements Serializable {
    private String nombre;

    public VoTicket() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
