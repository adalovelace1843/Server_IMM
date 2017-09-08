/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import valueObjects.VoTicket;



/**
 *
 * @author e299227
 */
public class InterfaceBD_IMM_Impl implements InterfaceBD_IMM{
    private static Connection conn;
    private static InterfaceBD_IMM_Impl instancia;
    
    
    public static InterfaceBD_IMM_Impl getInstance() throws NamingException, SQLException{
        if(instancia == null){
            instancia = new InterfaceBD_IMM_Impl();
            instancia.conectarBD();
        }
        return instancia;
    }
    
    private void conectarBD() throws NamingException, SQLException{
        InitialContext initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:jboss/datasources/MySqlDS");
        conn = ds.getConnection();
       
    }
     
    @Override
    public void guardarTicket(VoTicket vo) throws SQLException {
        String sql2="insert into ventas (comentario) values (?)";
        PreparedStatement instruccion2;
        instruccion2 = conn.prepareStatement(sql2);
        
        instruccion2.setString(1, vo.getNombre());
        instruccion2.execute();

        instruccion2.close();
      
    }
    
}
