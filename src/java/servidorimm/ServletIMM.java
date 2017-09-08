/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import valueObjects.VoTicket;
import valueObjects.VoTicketBasico;
import valueObjects.VoTicketCompleto;

/**
 *
 * @author e299227
 */
@WebService
public class ServletIMM {
    private final InterfaceB2B interfaz = new InterfaceB2BImpl();

    public ServletIMM() {
    }
    
    @WebMethod
    public String altaTicket (VoTicket vo){
        String res="ERROR EN SERVLETIMM";
        try {
            //res="VENTA OK";
            res= interfaz.ventaTicket(vo);
        } catch (SQLException ex) {
            return res="ERROR_WS: "+ex.getMessage();
          //  Logger.getLogger(ServletIMM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    /**
     * WS que permite dar de alta un ticket en su formato completo 
     * @param vo ingresa un VoTicketCompleto
     * @return 
     */
    @WebMethod
    public VoTicketBasico altaTicketCompleto(VoTicketCompleto vo){
        VoTicketBasico voTB = null;
        try {
            voTB = interfaz.ventaTicketCompleto(vo);
        } catch (SQLException ex) {
            Logger.getLogger(ServletIMM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voTB;
    }
}
