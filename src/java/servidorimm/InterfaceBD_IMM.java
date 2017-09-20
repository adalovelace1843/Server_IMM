/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import exception.ExPersistenciaIMM;
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
    public int guardarTicketCompleto (VoTicketCompleto vo) throws ExPersistenciaIMM;

    public boolean obtenerValidacionBDIMM(VoLogin vo) throws ExPersistenciaIMM;

    public List<VoTicketCompleto> obtenerListadoMensualBD() throws ExPersistenciaIMM;

    public List<VoTicketCompleto> obtenerListadoFechaBD(Date fecha_desde, Date fecha_hasta) throws ExPersistenciaIMM;

    public int anularTicketIMMBD(int nroTicket) throws ExPersistenciaIMM;

   
}
