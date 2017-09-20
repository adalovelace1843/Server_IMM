/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import exception.ExPersistenciaIMM;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class sbListadoTickets {
    private List<VoTicketCompleto> tickets;
        
     public sbListadoTickets() {
        this.tickets = new ArrayList<>();  
        listadoMensual();
    }

    public List<VoTicketCompleto> getTickets() {
        return tickets;
    }

    public void setTickets(List<VoTicketCompleto> tickets) {
        this.tickets = tickets;
    }
    
    public void listadoMensual(){

        try {
            InterfaceB2B ib2b = new InterfaceB2BImpl();
            tickets=ib2b.obtenerListadoMensual();
        } catch (ExPersistenciaIMM ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        }

    }
    
    
    
}
