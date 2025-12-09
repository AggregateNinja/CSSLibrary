/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

/**
 *
 * @author Edd
 */
public class Connection {
    String ip = null;
    String port = null;
    String username = null;
    String password = null;
    
    String rmi_ip = null;
    String rmi_port = null;
    String rmi_username = null;
    String rmi_password = null;
    
    String css_schema = "css";
    String web_schema = "cssweb";
    String billing_schema = "cssbilling";
    String emr_schema = "emrorders";
    String emr_billing_schema = "emrbilling";
    
    public Connection(String ip, String port, String username, String password, String rmi_ip, String rmi_port, String rmi_username, String rmi_password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        
        this.rmi_ip = rmi_ip;
        this.rmi_port = rmi_port;
        this.rmi_username = rmi_username;
        this.rmi_password = rmi_password;
    }
    
    public Connection(String ip, String port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        
        this.rmi_ip = ip;
        this.rmi_port = port;
        this.rmi_username = username;
        this.rmi_password = password;
    }
}
