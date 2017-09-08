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

/**
 *
 * @author e299227
 */
public class InterfaceB2BImpl implements InterfaceB2B{

    @Override
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
    
}
