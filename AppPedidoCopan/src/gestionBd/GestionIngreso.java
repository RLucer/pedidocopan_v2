
package gestionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GestionIngreso {
    String bd = "pedido_2019";
    String login = "rlucero";
    String password = "Rls232408";
    String url = "jdbc:mysql://201.238.207.58/" + bd;
    Connection conn = null;
    
    public GestionIngreso() throws ClassNotFoundException {
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
    
        
     //------------------ METODO DE INGRESO ----------------------//
    public void ingreso(String sql) {
        //  ResultSet res = null;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CON EXITO");
            pstm.close();
        } catch (SQLException e) {
            System.err.println("error : " + e.getMessage());
        }
    }

    //---------INGRESO CATEGORIA LLAMANDO AL METODO DE INGRESO-------//    >>>>> EJEMPLO DE INGRESAR DATOS A LA BD
    public void ingreso_categoria(String op) {
        String sql = "insert into categoria (categoria)values('" + op + "');";
        ingreso(sql);
    }
    //------------------------------------------------------------------//
}
