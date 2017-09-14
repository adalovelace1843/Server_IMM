/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import valueObjects.VoLogin;
import valueObjects.VoTicket;
import valueObjects.VoTicketCompleto;

/**
 *
 * @author e299227
 */
public interface InterfaceBD_IMM  {
    public void guardarTicket(VoTicket vo) throws SQLException;
    public int guardarTicketCompleto (VoTicketCompleto vo) throws SQLException;

    public boolean obtenerValidacionBDIMM(VoLogin vo) throws SQLException;

    public List<VoTicketCompleto> obtenerListadoMensualBD() throws SQLException;

    public List<VoTicketCompleto> obtenerListadoFechaBD(Date fecha_desde, Date fecha_hasta) throws SQLException;

   
}
