/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import exception.ExPersistenciaIMM;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import servidorimm.InterfaceB2B;
import servidorimm.InterfaceB2BImpl;
import valueObjects.VoTicketCompleto;

/**
 *
 * @author e299227
 */
@ManagedBean
@RequestScoped
public class sbVentasPorFecha {
    private List<VoTicketCompleto> ticketsFecha;
    private Date fecha_desde;
    private Date fecha_hasta;

    public sbVentasPorFecha() {
        ticketsFecha = new ArrayList<>();
        fecha_desde = new Date();
        fecha_hasta = new Date();
    }

    public List<VoTicketCompleto> getTicketsFecha() {
        return ticketsFecha;
    }

    public void setTicketsFecha(List<VoTicketCompleto> ticketsFecha) {
        this.ticketsFecha = ticketsFecha;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }
    
    public void listadoPorFechas() throws ExPersistenciaIMM {
        InterfaceB2B ib2b = new InterfaceB2BImpl();
        ticketsFecha = ib2b.obtenerListadoFecha(fecha_desde,fecha_hasta);
    }
    
}
