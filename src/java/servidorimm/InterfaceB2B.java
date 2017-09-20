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
import javax.naming.NamingException;
import valueObjects.VoLogin;
import valueObjects.VoTicket;
import valueObjects.VoTicketBasico;
import valueObjects.VoTicketCompleto;

/**
 *
 * @author e299227
 */
public interface InterfaceB2B {
    public VoTicketBasico ventaTicketCompleto (VoTicketCompleto vo) throws ExPersistenciaIMM;
    public boolean obtenerValidacionIMM(VoLogin vo) throws ExPersistenciaIMM;
    public List<VoTicketCompleto> obtenerListadoMensual() throws ExPersistenciaIMM;
    public List<VoTicketCompleto> obtenerListadoFecha(Date fecha_desde, Date fecha_hasta) throws ExPersistenciaIMM;
    public int anularTicketIMM(int nroTicket) throws ExPersistenciaIMM;
}
