/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import ua.divas.mob.util.WLS_Utility;
import weblogic.security.URLCallbackHandler;
import weblogic.servlet.security.ServletAuthentication;

/**
 *
 * @author bakum
 */
@ManagedBean(name="login")
@SessionScoped
public class LoginController implements Serializable{
    String _username = null;
    String _password = null;

    public static String USERNAMETOKEN = "username";
    public static String PASSWORDTOKEN = "_____demoOnlyPasswordAttrString___________";

    public LoginController() {
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }
    
    public String doLogin(){
        String un = _username;
        byte[] pw = _password.getBytes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        try {
            CallbackHandler handler = new URLCallbackHandler(un, pw);
            Subject mySubject = weblogic.security.services.Authentication.login(handler);
            weblogic.servlet.security.ServletAuthentication.runAs(mySubject, request);
            ServletAuthentication.generateNewSessionID(request);
            
            Map sessionScope = ctx.getExternalContext().getSessionMap();
            sessionScope.put(this.USERNAMETOKEN, un);
            //sessionScope.put(this.PASSWORDTOKEN, new String(pw));
            
            if (WLS_Utility.isMember("administrator", un, true)) {
                return "home.xhtml?faces-redirect=true";
            } else if (WLS_Utility.isMember("z_manager", un, true)) {
                return "index.xhtml?faces-redirect=true";
            } else {
                return "dispatch.xhtml?faces-redirect=true";
            }
            //System.out.println("Administrator: "+admin);
            
            
            
        } catch(FailedLoginException fle) {
            RequestContext.getCurrentInstance().update("growl");
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password",
                                 "Определено неверное имя пользователя или пароль");
            ctx.addMessage("aut", msg);
            return "";
            
        } catch(LoginException le) {
            reportUnexpectedLoginError("LoginException", le);
            return "";
        }
    }
    
    private void reportUnexpectedLoginError(String errType, Exception e) {
        RequestContext.getCurrentInstance().update("growl");
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during login",
                             "Unexpected error during login (" + errType + "), please consult logs for detail");
        FacesContext.getCurrentInstance().addMessage("aut", msg);
        e.printStackTrace();
    }
    
    public String doLogout() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = ctx.getExternalContext();
        String logoutUrl = "login.xhtml?faces-redirect=true";
        ((HttpServletRequest) ectx.getRequest()).getSession().invalidate();
        try {
            ectx.redirect(logoutUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    
}
