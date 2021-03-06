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
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * @author Girona 03
 */
public class GestionBD {
//prueba
    String bd = "gironacl_rlcompras";
    String login = "gironacl_root";
    String password = "Rls232408**8184";
    String url = "jdbc:mysql://201.148.105.97/" + bd;
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

    public String LOGUEAR(String nombre) {
        String salida = "";
        ResultSet res = this.consulta("select * from usuario  where usuario.nombre='" + nombre + "'");
        try {

            while (res.next()) {
                salida = res.getString("nombre");
                salida = salida + "," + res.getString("clave");
                salida = salida + "," + res.getString("idrol");
                salida = salida + "," + res.getString("idusuario");
                salida = salida + "," + res.getString("apellido");
            }
            res.close();

        } catch (SQLException ex) {
            System.err.println("error : " + ex.getMessage());
            System.out.println("no se hizo la consulta con el rol");
        }

        System.out.println("consulta realizada!");
        return salida;
    }

    public DefaultComboBoxModel carga_producto_combo() {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione un Producto");
        ResultSet res = this.consulta("Select producto.producto from producto");
        try {
            while (res.next()) {
                ListaModelo.addElement(res.getString("producto"));
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("error : " + ex.getMessage());
        }
        return ListaModelo;
    }

    public DefaultComboBoxModel carga_cliente_combo(String op) {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione un Cliente");
        ResultSet res = this.consulta("select cliente from cliente join direccion on cliente.idrut=direccion.idrut join usuario on direccion.idusuario=usuario.idusuario where usuario.idusuario='"+op +"'");
        try {
            while (res.next()) {
                ListaModelo.addElement(res.getString("cliente"));
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("error : " + ex.getMessage());
        }
        return ListaModelo;
    }

    public DefaultComboBoxModel carga_sucursales_combo(String rut, String ven) {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione una Sucursal");
        ResultSet res = this.consulta(" select direccion\n" +
" from direccion\n" +
" join cliente \n" +
" on cliente.idrut=direccion.idrut \n" +
" join usuario \n" +
" on direccion.idusuario=usuario.idusuario \n" +
" where cliente.idrut='"+rut+"' and  usuario.idusuario='"+ven+"'");
        try {
            while (res.next()) {
                ListaModelo.addElement(res.getString("direccion"));
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("error : " + ex.getMessage());
        }
        return ListaModelo;
    }

    public JTextField obt_codigo_pro(String op, JTextField txtcodigo) {
        ResultSet res = this.consulta("select * from producto where producto='" + op + "'");
        try {
            while (res.next()) {
                txtcodigo.setText(res.getString("idproducto"));
            }
        } catch (SQLException ex) {
            System.out.println("consulta mala : " + ex.getMessage());
        }

        return txtcodigo;
    }

    public JTextField obt_producto_por_cod(String cod, JTextField txtproducto, JTextField txtvalor, JTextField txtcodigo) {
        ResultSet res = this.consulta("select * from producto where idproducto='" + cod + "'");
        try {

            if (res.next()) {          //-- verificar si el resutlado de la consulta esta vacio avanza una fila
                res.beforeFirst();   // retrocede el puntero a la primera fila
                while (res.next()) {
                    txtproducto.setText(res.getString("producto"));
                    txtvalor.setText(res.getString("valorlista"));
                }
                res.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error Producto NO Registrado");
                txtproducto.setText("");
                txtcodigo.setText("");
            }
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
        return null;
    }

    public JTextField obt_direccion(String op, JTextField txtdireccion, JTextField txtcomuna,JTextField txtcodsuc ) {
        ResultSet res = this.consulta("select * from direccion join comuna on direccion.idcomuna=comuna.idcomuna where direccion='" + op + "'");
        try {
            while (res.next()) {
                txtdireccion.setText(res.getString("direccion"));
                txtcomuna.setText(res.getString("comuna"));
                txtcodsuc.setText(res.getString("iddireccion"));
            }
        } catch (SQLException ex) {
            System.out.println("consulta mala : " + ex.getMessage());
        }

        return txtcomuna;
    }

    public JTextField obt_datoscliente(String op, JTextField txtrut, JTextField txtrazonsocial, JTextField txtcategoria) {
        ResultSet res = this.consulta("select * from cliente join categoria on cliente.idcategoria=categoria.idcategoria where cliente='" + op + "'");
        try {
            while (res.next()) {
                txtrut.setText(res.getString("idrut"));
                txtrazonsocial.setText(res.getString("cliente"));
                txtcategoria.setText(res.getString("categoria"));
            }
        } catch (SQLException ex) {
            //System.out.println("consulta mala : " + ex.getMessage());
        }
        return txtrut;

    }

    public JTextField obt_datoscliente_por_rut(String op, JTextField txtrazonsocial, JTextField txtcategoria, JTextField txtrut) {
        ResultSet res = this.consulta("select * from cliente join categoria on cliente.idcategoria=categoria.idcategoria where idrut='" + op + "'");
        try {

            if (res.next()) {          //-- verificar si el resutlado de la consulta esta vacio avanza una fila
                res.beforeFirst();   // retrocede el puntero a la primera fila

                while (res.next()) {

                    txtrazonsocial.setText(res.getString("cliente"));
                    txtcategoria.setText(res.getString("categoria"));
                }
                res.close();
            } else {
              
            /*    JOptionPane.showMessageDialog(null, "ERROR:\n"
                       + "Cliente no Registrado", "Verifique Información",
                       JOptionPane.ERROR_MESSAGE);*/
                txtrut.setText("");
                txtrazonsocial.setText("");
                txtcategoria.setText("");
            }
        } catch (SQLException ex) {
            System.out.println("consulta mala : " + ex.getMessage());
        }
        return txtrazonsocial;

    }
}
