/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyTickets") != null){
            ticketsFecha = (List<VoTicketCompleto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyTickets");
            fecha_desde=(Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyFechaDesde");
            fecha_hasta=(Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyFechaHasta");
        }else{
            ticketsFecha = new ArrayList<>();
            fecha_desde = new Date();
            fecha_hasta = new Date();
            try {
                listadoPorFechas();
            } catch (NamingException ex) {
                FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
            } catch (ClassNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
            }
        }
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
    
    public void listadoPorFechas() throws NamingException, SQLException, ClassNotFoundException{
            InterfaceB2B ib2b = new InterfaceB2BImpl();
            ticketsFecha = ib2b.obtenerListadoFecha(fecha_desde,fecha_hasta);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyTickets", ticketsFecha);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyFechaDesde", fecha_desde);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyFechaHasta", fecha_hasta);
    }
    
    public void btnListadoPorFechas(){
        try {
            listadoPorFechas();
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,"Reporte generado con éxito!","Info Ventas"));
        } catch (NamingException ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        } catch (ClassNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        }
    }
}
