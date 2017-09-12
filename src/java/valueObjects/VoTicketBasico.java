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
public class VoTicketBasico implements Serializable{
    private int nro_ticket;
    private float importe_total;
    
    public VoTicketBasico() {
    }

    public int getNro_ticket() {
        return nro_ticket;
    }

    public void setNro_ticket(int nro_ticket) {
        this.nro_ticket = nro_ticket;
    }

    public float getImporte_total() {
        return importe_total;
    }

    public void setImporte_total(float importe_total) {
        this.importe_total = importe_total;
    }
    
}
