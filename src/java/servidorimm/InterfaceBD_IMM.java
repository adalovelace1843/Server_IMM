/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.SQLException;
import valueObjects.VoTicket;

/**
 *
 * @author e299227
 */
public interface InterfaceBD_IMM {
    public void guardarTicket(VoTicket vo) throws SQLException;
}
