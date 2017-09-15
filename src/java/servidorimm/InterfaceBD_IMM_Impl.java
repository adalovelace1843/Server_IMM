/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import valueObjects.VoLogin;
import valueObjects.VoTicket;
import valueObjects.VoTicketCompleto;



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
        DataSource ds = (DataSource)initContext.lookup("java:jboss/datasources/MySqlDS/");
        conn = ds.getConnection();
       
    }
     
    @Override
    public void guardarTicket(VoTicket vo) throws SQLException {
        String sql2="INSERT into ventas (comentario) values (?)";
        PreparedStatement instruccion2;
        instruccion2 = conn.prepareStatement(sql2);
        
        instruccion2.setString(1, vo.getNombre());
        instruccion2.execute();

        instruccion2.close();
      
    }

    /**
     * Funcion que permite persistir un ticket en bd_imm
     * @param vo recibe un VoTicketCompleto
     * @return devuelve el numero de ticket o -1 si hubo algún error
     */
    @Override
    public int guardarTicketCompleto(VoTicketCompleto vo) throws SQLException {
        String sql="SELECT numero+1 as numero from secuencia where tipo = 'ticket'";
        PreparedStatement inst;
        inst = conn.prepareStatement(sql);
        ResultSet rs = inst.executeQuery(sql);
        int numero = -1;
        if(rs.next()){
            numero = rs.getInt("numero");
            sql="INSERT INTO ventascompleto (numero,agencia,matricula,fecha_hora_venta,fecha_hora_inicio,minutos,importe_Total) values (?,?,?,?,?,?,?)";
            PreparedStatement inst2 = conn.prepareStatement(sql);
            inst2.setInt(1, numero);
            inst2.setString(2, vo.getAgencia_venta());
            inst2.setString(3, vo.getMatricula());
            inst2.setString(4, vo.getF_h_venta());
            inst2.setString(5, vo.getF_h_inicio());
            inst2.setInt(6, vo.getCant_min());
            inst2.setFloat(7, vo.getImporte_total());
            if(inst2.executeUpdate() == 1){
                sql="UPDATE secuencia SET numero=numero+1 where tipo = 'ticket'";
                PreparedStatement inst3 = conn.prepareStatement(sql);
                if(inst3.executeUpdate() != 1){
                    numero=-1;
                }
                inst3.close();
            }
            inst2.close();
        }
        rs.close();
        inst.close();    
        return numero;          
    }
    
    
    // CERRAR LA CONEXION?

    @Override
    public boolean obtenerValidacionBDIMM(VoLogin vo) throws SQLException{
        boolean resultado=false;
        String sql="select * from usuarios where usuario=? and clave=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, vo.getUsuario());
        ps.setString(2, vo.getClave());
        ResultSet rs =ps.executeQuery();
        if(rs.next()){
            resultado=true;
        }
        rs.close();
        ps.close();
        return resultado;
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoMensualBD() throws SQLException {
        List<VoTicketCompleto> tickets= new ArrayList<>();
       
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from ventascompleto where month(fecha_hora_venta) = month(now()) and year(fecha_hora_venta)= year(now())");

        while (rs.next()) {
            VoTicketCompleto temp = new VoTicketCompleto();
            temp.setNro_ticket(rs.getInt("numero"));
            temp.setAgencia_venta(rs.getString("agencia"));
            temp.setMatricula(rs.getString("matricula"));
            temp.setF_h_venta(rs.getString("fecha_hora_venta"));
            temp.setF_h_inicio(rs.getString("fecha_hora_inicio"));
            temp.setCant_min(rs.getInt("minutos"));
            temp.setImporte_total(rs.getFloat("importe_Total"));
            tickets.add(temp);
            temp=null;
        }
        rs.close();
        st.close();
        
        return tickets;
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoFechaBD(Date fecha_desde, Date fecha_hasta) throws SQLException {
        List<VoTicketCompleto> tickets= new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql="select * from ventascompleto where fecha_hora_venta between ? and date_add(?, interval 1 day)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sdf.format(fecha_desde));
        ps.setString(2, sdf.format(fecha_hasta));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            VoTicketCompleto temp = new VoTicketCompleto();
            temp.setNro_ticket(rs.getInt("numero"));
            temp.setAgencia_venta(rs.getString("agencia"));
            temp.setMatricula(rs.getString("matricula"));
            temp.setF_h_venta(rs.getString("fecha_hora_venta"));
            temp.setF_h_inicio(rs.getString("fecha_hora_inicio"));
            temp.setCant_min(rs.getInt("minutos"));
            temp.setImporte_total(rs.getFloat("importe_Total"));
            tickets.add(temp);
            temp=null;
        }
        rs.close();
        ps.close();
        
        return tickets;
    }
}
