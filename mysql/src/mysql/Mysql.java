/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;
import gestionBD.GestionBD;

/**
 *
 * @author Girona 03
 */
public class Mysql {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        GestionBD x = new GestionBD();
        x.conecta();
       x.createTable("pn2");
               
               
        
    
}
}
