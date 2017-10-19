/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import exception.ExPersistenciaIMM;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import servidorimm.InterfaceB2B;
import servidorimm.InterfaceB2BImpl;
import valueObjects.voUsuario;

/**
 *
 * @author e299227
 */
@ManagedBean
@RequestScoped
public class sbAdministracionUsuarios {
    private voUsuario vou = new voUsuario();
    private List<String> lista = null;
    
    public sbAdministracionUsuarios() {
        try {
            InterfaceB2B ib2b= new InterfaceB2BImpl();
            this.lista = ib2b.obtenerUsuarios();
        } catch (ExPersistenciaIMM ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error usuarios"));
        }
    }

    public voUsuario getVou() {
        return vou;
    }

    public void setVou(voUsuario vou) {
        this.vou = vou;
    }
    
    public void setLista(List lista){
        this.lista=lista;
    }
    public List<String> getLista(){
        return lista;
    }
    
    public void altaUsuario(){
        try {
            InterfaceB2B ib2b= new InterfaceB2BImpl();
            ib2b.altaUsuario(vou);
            this.lista = ib2b.obtenerUsuarios();
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,"Alta de usuario realizada con éxito!","Info usuarios"));
        } catch (ExPersistenciaIMM ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error usuarios"));
        }
    }
    
    public void bajaUsuario(){
        try {
            InterfaceB2B ib2b= new InterfaceB2BImpl();
            ib2b.bajaUsuario(vou.getUsuario());
            this.lista = ib2b.obtenerUsuarios();
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,"Baja de usuario realizada con éxito!","Info usuarios"));
        } catch (ExPersistenciaIMM ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error usuarios"));
        }
    }
    
    public boolean esAdmin(){
        boolean esAdmin=false;
        try {
            String usuario;
            FacesContext context = FacesContext.getCurrentInstance();
            usuario= (String) context.getExternalContext().getSessionMap().get("usuario");
            InterfaceB2B ib2b= new InterfaceB2BImpl();
            
            if(ib2b.esAdmin(usuario)){
                esAdmin=true;
            }

        } catch (ExPersistenciaIMM ex) {
            FacesContext.getCurrentInstance().addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: "+ex.getMessage(),"Error usuarios"));
        }
        return esAdmin;
    }
}
