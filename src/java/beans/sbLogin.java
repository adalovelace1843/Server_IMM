/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import servidorimm.InterfaceB2B;
import servidorimm.InterfaceB2BImpl;
import valueObjects.VoLogin;

/**
 *
 * @author e299227
 */
@ManagedBean
@RequestScoped
public class sbLogin {
    private VoLogin voLog = new VoLogin();

    public sbLogin() {
    }

    public VoLogin getVoLog() {
        return voLog;
    }

    public void setVoLog(VoLogin voLog) {
        this.voLog = voLog;
    }
    
    public String validarLoginIMM (){
        String resultado=null;
        try {
            InterfaceB2B ib2b= new InterfaceB2BImpl();
            if(ib2b.obtenerValidacionIMM(voLog)){
                resultado="menu";
            }else{
                 FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,"Error! Usuario/contraseña incorrecto","Info login"));
            }
        } catch (NamingException ex) {
             FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        } catch (SQLException ex) {
           FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        } catch (ClassNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error login"));
        }
        return resultado;
    }
}
