/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import exception.ExPersistenciaIMM;
import javax.jws.WebMethod;
import javax.jws.WebService;
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
        } catch (ExPersistenciaIMM ex) {
            voTB = new VoTicketBasico();
            voTB.setNro_ticket(-1);
            voTB.setImporte_total(0);
        }
        return voTB;
    }
    
    @WebMethod
    public int anularTicketIMM(int nroTicket, String agencia){
        int res=0;
        try {
            res=interfaz.anularTicketIMM(nroTicket, agencia);
        } catch (ExPersistenciaIMM ex) {
            res=-1;
        }
        return res;
    }
}
