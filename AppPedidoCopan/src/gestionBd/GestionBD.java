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
import javax.swing.JOptionPane;
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

    public DefaultComboBoxModel carga_producto_combo() {
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
        return ListaModelo;
    }

    public DefaultComboBoxModel carga_cliente_combo() {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione un Cliente");
        ResultSet res = this.consulta("select * from cliente order by cliente");
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

    public DefaultComboBoxModel carga_sucursales_combo(String rut) {
        DefaultComboBoxModel ListaModelo = new DefaultComboBoxModel();
        ListaModelo.addElement("Seleccione una Sucursal");
        ResultSet res = this.consulta("select * from direccion join cliente on direccion.idrut=cliente.idrut  where cliente.idrut='" + rut + "'");
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

    public JTextField obt_direccion(String op, JTextField txtdireccion, JTextField txtcomuna) {
        ResultSet res = this.consulta("select * from direccion join comuna on direccion.idcomuna=comuna.idcomuna where direccion='" + op + "'");
        try {
            while (res.next()) {
                txtdireccion.setText(res.getString("direccion"));
                txtcomuna.setText(res.getString("comuna"));
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
            System.out.println("consulta mala : " + ex.getMessage());
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
                JOptionPane.showMessageDialog(null, "Error Cliente NO Registrado");
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
