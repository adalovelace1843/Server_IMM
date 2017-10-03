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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import valueObjects.VoLogin;
import valueObjects.VoTicket;
import valueObjects.VoTicketBasico;
import valueObjects.VoTicketCompleto;
import valueObjects.voUsuario;

/**
 *
 * @author e299227
 */
public class InterfaceB2BImpl implements InterfaceB2B{
    /*private static InterfaceB2BImpl instance;
    
    public static InterfaceB2BImpl getInstance(){
        if (instance==null)
            instance = new InterfaceB2BImpl();
        return instance;
    }*/
      
    public VoTicketBasico ventaTicketCompleto(VoTicketCompleto vo) throws ExPersistenciaIMM {
        int numero=0;
        VoTicketBasico voTB = new VoTicketBasico();
        vo.setImporte_total(this.calcular_importe(vo.getCant_min()));
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        numero=in.guardarTicketCompleto(vo);
        voTB.setNro_ticket(numero);
        voTB.setImporte_total(vo.getImporte_total());
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
    
    @Override
    public boolean obtenerValidacionIMM(VoLogin vo) throws ExPersistenciaIMM{
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        return in.obtenerValidacionBDIMM(vo);
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoMensual() throws ExPersistenciaIMM {
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        return in.obtenerListadoMensualBD();
    }

    @Override
    public List<VoTicketCompleto> obtenerListadoFecha(Date fecha_desde, Date fecha_hasta) throws ExPersistenciaIMM{
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        return in.obtenerListadoFechaBD(fecha_desde, fecha_hasta);
    }

    @Override
    public int anularTicketIMM(int nroTicket, String agencia) throws ExPersistenciaIMM {
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        return in.anularTicketIMMBD(nroTicket, agencia);
    }

    @Override
    public void altaUsuario(voUsuario vo) throws ExPersistenciaIMM {
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        in.altaUsuarioBD(vo);
    }

    @Override
    public void bajaUsuario(String usuario) throws ExPersistenciaIMM {
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        in.bajaUsuarioBD(usuario);
    }

    @Override
    public boolean esAdmin(String usuario) throws ExPersistenciaIMM {
        InterfaceBD_IMM in = InterfaceBD_IMM_Impl.getInstance();
        return in.esAdminBD(usuario);
    }
}
