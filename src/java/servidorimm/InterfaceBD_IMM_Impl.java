/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimm;

import exception.ExPersistenciaIMM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import valueObjects.VoLogin;
import valueObjects.VoTicketCompleto;
import valueObjects.voUsuario;



/**
 *
 * @author e299227
 */
public class InterfaceBD_IMM_Impl implements InterfaceBD_IMM{
    private static Connection conn;
    private static InterfaceBD_IMM_Impl instancia;
    
    
    public static InterfaceBD_IMM_Impl getInstance() throws ExPersistenciaIMM {
        if(instancia == null){
            instancia = new InterfaceBD_IMM_Impl();
            instancia.conectarBD();
        }
        return instancia;
    }
    
    private void conectarBD() throws ExPersistenciaIMM {
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:jboss/datasources/MySqlDS/");
            conn = ds.getConnection();
        } catch (NamingException ex) {
            throw new ExPersistenciaIMM("Error al intentar conectarse a la BD (SI)");
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al conectarse con la BD (SI)");
        }
       
    }
     
 
    /**
     * Funcion que permite persistir un ticket en bd_imm
     * @param vo recibe un VoTicketCompleto
     * @return devuelve el numero de ticket o -1 si hubo algún error
     */
    @Override
    public int guardarTicketCompleto(VoTicketCompleto vo) throws ExPersistenciaIMM  {
        try {
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
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al guardar ticket en la BD (SI)");
        }
    }
    
    
    // CERRAR LA CONEXION?

    @Override
    public boolean obtenerValidacionBDIMM(VoLogin vo) throws ExPersistenciaIMM{
        
        try {
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
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al obtener validación en la BD (SI): "+ex);
        }
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoMensualBD() throws ExPersistenciaIMM{
        try {
            List<VoTicketCompleto> tickets= new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select cv.* , if (a.numero=cv.numero,'anulado', 'ok') as estado\n" +
                                            "from ventascompleto cv \n" +
                                            "left join anulaciones a on a.numero=cv.numero\n" +
                                            "where month(fecha_hora_venta) = month(now()) and year(fecha_hora_venta)= year(now())");
            
            while (rs.next()) {
                VoTicketCompleto temp = new VoTicketCompleto();
                temp.setNro_ticket(rs.getInt("numero"));
                temp.setAgencia_venta(rs.getString("agencia"));
                temp.setMatricula(rs.getString("matricula"));
                temp.setF_h_venta(rs.getString("fecha_hora_venta"));
                temp.setF_h_inicio(rs.getString("fecha_hora_inicio"));
                temp.setCant_min(rs.getInt("minutos"));
                temp.setImporte_total(rs.getFloat("importe_Total"));
                temp.setEstado(rs.getString("estado"));
                tickets.add(temp);
                temp=null;
            }
            rs.close();
            st.close();
            
            return tickets;
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al obtener listado en la BD (SI)");
        }
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoFechaBD(Date fecha_desde, Date fecha_hasta) throws ExPersistenciaIMM{
        List<VoTicketCompleto> tickets= new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql="select cv.* , if (a.numero=cv.numero,'anulado', 'ok') as estado\n" +
                        "from ventascompleto cv\n" +
                        "left join anulaciones a on a.numero=cv.numero "+
                        "where fecha_hora_venta between ? and date_add(?, interval 1 day)";
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
                temp.setEstado(rs.getString("estado"));
                tickets.add(temp);
                temp=null;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al obtener listado en la BD (SI)");
        }
        return tickets;
    }

    @Override
    public int anularTicketIMMBD(int nroTicket, String agencia) throws ExPersistenciaIMM {
        int numero = -1;
        try {
            String sql0="select * from ventascompleto where numero=? and agencia=?";
            PreparedStatement inst0 = conn.prepareStatement(sql0);
            inst0.setInt(1, nroTicket);
            inst0.setString(2, agencia);
            ResultSet rs0=inst0.executeQuery();
            if(rs0.next()){
                
                String sql="SELECT numero+1 as numero from secuencia where tipo = 'anulacion'";
                PreparedStatement inst;
                inst = conn.prepareStatement(sql);
                ResultSet rs = inst.executeQuery(sql);
                if(rs.next()){
                    try {
                        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                        conn.setAutoCommit(false);
                        numero = rs.getInt("numero");
                        sql="INSERT INTO anulaciones (transaccion,numero) values (?,?)";
                        PreparedStatement inst2 = conn.prepareStatement(sql);
                        inst2.setInt(1, numero);
                        inst2.setInt(2, nroTicket);
                        inst2.executeUpdate();
                        sql="UPDATE secuencia SET numero=numero+1 where tipo = 'anulacion'";
                        PreparedStatement inst3 = conn.prepareStatement(sql);
                        if(inst3.executeUpdate() != 1){
                            numero=-1;
                        }
                        inst3.close();
                        inst2.close();
                        conn.commit();
                    } catch (SQLException ex) {
                        conn.rollback();
                        throw new ExPersistenciaIMM("No se pudo anular al ticket, se cancela operación (SI)");
                    }
                }
                rs.close();
                inst.close(); 
            }
            rs0.close();
            inst0.close();
            
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("No se pudo anular al ticket, se cancela operación");
        }    
        return numero;          
    }

    @Override
    public void altaUsuarioBD(voUsuario vo) throws ExPersistenciaIMM {
        try {
            String sql="insert into usuarios (usuario, clave, nivel) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, vo.getUsuario());
            ps.setString(2, vo.getClave());
            ps.setInt(3, vo.getNivel());
            ps.executeUpdate();
            ps.close();     
        } catch (SQLException ex) {
            if(ex.getErrorCode() == 1062){
                throw new ExPersistenciaIMM("Ya existe usuario");
            }else{
                throw new ExPersistenciaIMM("No se pudo de dar de alta al usuario. Error: "+ex.getMessage());
            }
        }
    }

    @Override
    public void bajaUsuarioBD(String usuario) throws ExPersistenciaIMM {
         try {
            String sql="delete from usuarios where usuario=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            int i=ps.executeUpdate();
            if(i==0){
                throw new ExPersistenciaIMM("No existe un usuario creado con ese nombre.");
            }
            
            ps.close();     
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("No se pudo eliminar al usuario. Error: "+ex.getMessage());
        }
    }

    @Override
    public boolean esAdminBD(String usuario) throws ExPersistenciaIMM {
        try {
            boolean esAdmin=false;
            String sql="select * from usuarios where usuario=? and nivel=0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);

            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                esAdmin=true;
            }
            rs.close();
            ps.close();
            return esAdmin;
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al obtener si es un administrador en la BD (SI)");
        }
    }

    @Override
    public List<String> obtenerUsuariosIMM() throws ExPersistenciaIMM {
        try {
            List<String> lista = new LinkedList<String>();
            String sql="select usuario from usuarios";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("usuario"));
            }
            return lista;
        } catch (SQLException ex) {
            throw new ExPersistenciaIMM("Error al obtener si es un administrador en la BD (SI)");
        }
    }
}
