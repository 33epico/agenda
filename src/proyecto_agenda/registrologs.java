/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Esta es la clase encargada de registrar los errores, nuevos registros y mails enviados. Esta es la clase que
 * se encarga de generar la fecha y hora de registro. 
 * Todos estos datos se guaran en un archivo llamado logs.txt.
 * @author Epico
 */
public class registrologs {
    String tipo=null;
    LocalDateTime hora;
    
    public void guardarlogs (String tipo,String DNI,String nombre,String apellidos, String direccion, String telefono){
        
        hora = LocalDateTime.now();
        BufferedWriter fichero2 = null;
        try {
            fichero2 = new BufferedWriter(new FileWriter("logs.txt", true)); //true es para que no elimine el contenido que tenga el fichero
            fichero2.write(tipo+"-"+DNI+"-"+nombre+"-"+apellidos+"-"+direccion+"-"+telefono+"-"+hora);
            fichero2.newLine();
            fichero2.close();
        } catch (IOException ex) {
            Logger.getLogger(registrologs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fichero2.close();
            } catch (IOException ex) {
                Logger.getLogger(registrologs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
