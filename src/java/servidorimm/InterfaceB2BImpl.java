/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import valueObjects.VoTicket;
import valueObjects.VoTicketBasico;
import valueObjects.VoTicketCompleto;

/**
 *
 * @author e299227
 */
public class InterfaceB2BImpl implements InterfaceB2B{
    
    
    public String ventaTicket(VoTicket vo) throws SQLException {
        String respuesta = "ERROR EN VENTATICKET INTERFACE B2B";
        try {
            InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
            in.guardarTicket(vo);
            respuesta = "Venta realizada correcamente";
        } catch (NamingException ex) {
            Logger.getLogger(InterfaceB2BImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

   
    public VoTicketBasico ventaTicketCompleto(VoTicketCompleto vo) throws SQLException {
        int numero=0;
        VoTicketBasico voTB = new VoTicketBasico();
        try {
            vo.setImporte_total(this.calcular_importe(vo.getCant_min()));
            InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
            numero=in.guardarTicketCompleto(vo);
            voTB.setNro_ticket(numero);
            voTB.setImporte_total(vo.getImporte_total());
        } catch (NamingException ex) {
            Logger.getLogger(InterfaceB2BImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voTB;
    }
    
        /**
     * Calcula el importe total a pagar dada una cantidad x de minutos
     * @param minutos cantidad de minutos del ticket
     * @return float importe total
     */
    private float calcular_importe (int minutos){
        float total=minutos/60;
        int resto= minutos%60;
        if(resto > 0){
            total=total+1;
        }
        return total*120;
    }
    
}
