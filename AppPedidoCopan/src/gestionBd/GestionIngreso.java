package gestionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import principal.TomarPedido;

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
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.executeUpdate();
            // JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CON EXITO");
            pstm.close();
        } catch (SQLException e) {
            System.err.println("error : " + e.getMessage());
        }
    }

    //---------INGRESO CATEGORIA LLAMANDO AL METODO DE INGRESO-------//    >>>>> EJEMPLO DE INGRESAR DATOS A LA BD
    public void ingreso_categoria(String op) {
        String sql = "insert into categoria (categoria)values('" + op + "');";
        ingreso(sql);
        JOptionPane.showMessageDialog(null, "Ingreso Correcto de Datos");
    }

    //------------------------------------------------------------------//
    //insertar pedido//
    //------------------ METODO DE INGRESO con devolucion----------------------//
    int idGenerado;

    public void ingreso_pedido_con_devolucion_ID(String sql) {
        ResultSet res = null;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("no se pudo gurdar fuck");
            }

            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
                 TomarPedido.setNumeroPedido(idGenerado);
                 TomarPedido.setIddetallepedido(idGenerado);
            }
            // JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CON EXITO");
            pstm.close();
        } catch (SQLException e) {
            System.err.println("error : " + e.getMessage());
        }

        System.out.println("idgenerado  " + idGenerado);
        
        
       
    }

    public void ingreso_pedido_devo(String fechapedido, String fechasalida, int monto, int idestadopedido, int iddireccion) {
        String sql = "INSERT INTO `pedido_2019`.`pedido` (`fecha_ingreso`, `fecha_salida`, `monto`, `idestadopedido`, `iddireccion`) "
                + "VALUES ('" + fechapedido + "', '" + fechasalida + "', '" + monto + "', '" + idestadopedido + "', '" + iddireccion + "');";
        ingreso_pedido_con_devolucion_ID(sql);

        JOptionPane.showMessageDialog(null, "FOLIO DE PEDIDO : "+idGenerado );
    }  

    //insetar detalle pedido
    public void ingreso_detallepedido(int idproducto, int idpedido, int cantidad) {
        String sql = "INSERT INTO `pedido_2019`.`detallepedido` (`idproducto`, `idpedido`, `cantidad`) VALUES ('" + idproducto + "', '" + idpedido + "', '" + cantidad + "');";
        ingreso(sql);
      
    }

}
