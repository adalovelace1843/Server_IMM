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
public class VoTicketCompleto extends VoTicketBasico implements Serializable {
    private String agencia_venta;
    private String matricula;
    private String f_h_venta;
    private String f_h_inicio;
    private int cant_min;
    private String estado;

    public VoTicketCompleto() {
    }

    public String getAgencia_venta() {
        return agencia_venta;
    }

    public void setAgencia_venta(String agencia_venta) {
        this.agencia_venta = agencia_venta;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getF_h_venta() {
        return f_h_venta;
    }

    public void setF_h_venta(String f_h_venta) {
        this.f_h_venta = f_h_venta;
    }

    public String getF_h_inicio() {
        return f_h_inicio;
    }

    public void setF_h_inicio(String f_h_inicio) {
        this.f_h_inicio = f_h_inicio;
    }

    public int getCant_min() {
        return cant_min;
    }

    public void setCant_min(int cant_min) {
        this.cant_min = cant_min;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}
