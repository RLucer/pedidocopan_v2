/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

/*
 * @author Girona 03
 */
public class GestionBD {

    String bd = "pedido_2019";
    String login = "rlucero";
    String password = "Rls232408";
    String url = "jdbc:mysql://201.238.207.58/" + bd;
    Connection conn = null;

    public GestionBD() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("conectado a la BD");
            }
        } catch (SQLException e) {
            System.out.println("error  :  " + e.getMessage());
        }
    }

    public ResultSet consulta(String sql) {
        ResultSet res = null;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            res = pstm.executeQuery();
        } catch (SQLException e) {
            System.err.println("error : " + e.getMessage());
        }
        return res;
    }

    public DefaultComboBoxModel obt_producto() {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione un Producto");
        ResultSet res = this.consulta("select * from producto order by producto asc");
        try {
            while (res.next()) {
                ListaModelo.addElement(res.getString("producto"));
               
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("error : " + ex.getMessage());
        }
        return  ListaModelo;
    }

    public JTextField obt_codigo_pro(String op,JTextField txtcodigo){
        ResultSet res = this.consulta("select * from producto where producto='"+op+"'");
        try{
            while(res.next()){
            txtcodigo.setText(res.getString("idproducto"));
            }
        
        
        }catch(SQLException ex){
            System.out.println("consulta mala : "+ex.getMessage());
        }
        
        return txtcodigo;
    }
    
    
}
