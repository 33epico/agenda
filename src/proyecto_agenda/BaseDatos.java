/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *Esta clase nos permite interactuar con la base de datos. Contiene todas las consultas que necesitamos 
 * en nuestra aplicación para haela funcionar.
 * @author Epico
 */
public class BaseDatos {

    private Component frame;

    public BaseDatos() {
    }
    /**
     * Este metodo nos ofrece la inserción de datos en la base de datos. El metodo no necesita que todos los
     * campos contengan información, pero es imprescindible insertar el DNI ya que es el unique id de la BDD asi como la
     * primary key.
     * 
     * @param DNI: unique id de la base de datos, este dato es imprescindible.
     * @param nombre
     * @param apellidos
     * @param direccion: Email, este dato se puede usar para enviar mails.
     * @param telefono
     * @throws ClassNotFoundException 
     */
    public void insercion(String DNI, String nombre, String apellidos, String direccion, String telefono) throws ClassNotFoundException {

        int intdni = Integer.parseInt(DNI);
        int inttelefono = Integer.parseInt(telefono);

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();// enviar comandos SQL a la base de datos, se usa la clase Statement de java
            stat.executeUpdate("INSERT INTO datos VALUES(" + intdni + ",'" + nombre + "','" + apellidos + "','" + direccion + "'," + inttelefono + ");");//Si el id es auto_increment no hay que poner el id
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de insercion");
        }
    }
    /**
     * Este metodo nos indica si existe o no el registro.
     * 
     * @param DNI
     * @return
     * @throws ClassNotFoundException 
     */

    public boolean existe(String DNI) throws ClassNotFoundException {

        boolean sino = false;

        try {
            String baseDNI = null;
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            //Un objeto Statement se usa para enviar sentencias SQL a la base de datos
            Statement stat = conn.createStatement();
            //ResultSet no es más que una clase java similar a una lista en la que está el resultado de la consulta
            ResultSet rs = stat.executeQuery("select * from datos where DNI = '" + DNI + "'");//executeQuery para su uso con SELECT
            while (rs.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {//recorremos todas las filas
                baseDNI = rs.getString("DNI");

            }

            if (baseDNI != null) {
                if (DNI.equals(baseDNI)) {
                    sino = true;
                }
            } else {
                sino = false;
            }
            stat.close();
            conn.close();//Cerramos la conexion
        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        return sino;
    }
    
    /**
     * Este metodo nos permite eliminar un registro a traves de su DNI.
     * 
     * @param DNI
     * @throws ClassNotFoundException 
     */

    public void eliminar(String DNI) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            stat.executeUpdate("DELETE FROM datos WHERE DNI='" + DNI + "'");
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de eliminacion");
        }

    }
    /**
     * Este método se utiliza para localizar datos en la base de datos cuando dicho datos solo nos va a
     * devolver una unica fila.
     * 
     * @param valor: Valor a buscar
     * @param campo: Campo dentro de la bdd donde buscamos la infrmación.
     * @param extraccion: con este parametro controlamos la infoamación que queremos 
     * obtener de ese registro. Asi si es extraccion=DNI el return de la función será el valor del DNI.
     * @return: nos devuelve el dato String pedido por el parametro extracción.
     * @throws ClassNotFoundException 
     */
    public String selecciononerow(String valor, String campo, String extraccion) throws ClassNotFoundException {

        String apellido = "";
        String nombre = "";
        String direccion = "";
        String telefono = "";
        String DNI = "";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from datos where " + campo + "='" + valor + "'");//executeQuery para su uso con SELECT

            while (rs.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {
                DNI = rs.getString("DNI");
                apellido = rs.getString("apellido");
                nombre = rs.getString("nombre");
                direccion = rs.getString("direccion");
                telefono = rs.getString("telefono");
            }
            if (extraccion.equals("apellido")) {
                return apellido;
            }
            if ("nombre".equals(extraccion)) {
                return nombre;
            }
            if ("direccion".equals(extraccion)) {
                return direccion;
            }
            if ("telefono".equals(extraccion)) {
                return telefono;
            }
            if ("DNI".equals(extraccion)) {
                return DNI;
            }
            stat.close();
            conn.close();//Cerramos la conexion

        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        return "nada";

    }
/**
 * A traves de este método podremos actualizar el dato que queramos en la base de datos. Doonde los 
 * parámetros campo es el campo a actualizar y bcampo es el dato que insertaremos. El parámetro igual será el
 * identificador para localizar el registro y bigual el valor de dicho registro.
 * @param campo1: campo a actualizar
 * @param bcampo1:dato que insertaremos
 * @param campo2: campo a actualizar
 * @param bcampo2:dato que insertaremos
 * @param campo3: campo a actualizar
 * @param bcampo3:dato que insertaremos
 * @param campo4: campo a actualizar
 * @param bcampo4:dato que insertaremos
 * @param igual:Campo a buscar
 * @param bigual: Dato para localizar
 * @throws ClassNotFoundException 
 */
    public void actualizar(String campo1, String bcampo1, String campo2, String bcampo2, String campo3, String bcampo3, String campo4, String bcampo4, String igual, String bigual) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            stat.executeUpdate("UPDATE datos SET " + campo1 + "='" + bcampo1 + "'," + campo2 + "='" + bcampo2 + "'," + campo3 + "='" + bcampo3 + "'," + campo4 + "='" + bcampo4 + "' WHERE " + igual + "='" + bigual + "' ");

            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de actualizacion");
        }

    }
/**
 * Este metodo nos devuelve el número de registros de una consulta de esta manera sabremos si la aplicacion
 * necesita hacer una busqueda tipo one row o multy row.
 * 
 * @param entrada: valor a buscar
 * @param campo: campo en el que buscar
 * @return: tipo entero con la cantidad total de registro
 * @throws ClassNotFoundException 
 */
    public int contar(String entrada, String campo) throws ClassNotFoundException {

        int x = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from datos where " + campo + " like '%" + entrada + "%'");
            //Localizamos cuantos datos hay con el valor de busqueda
            while (rs.next()) {
                x++;
            }
            stat.close();
            conn.close();//Cerramos la conexion
        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }
        return x;

    }
/**
 * Metodo general para extraer un dato de la base de datos, en pruebas, aun no se utiliza.
 * @param valor
 * @param campo
 * @param extraccion
 * @throws ClassNotFoundException 
 */
    public void seleccion(String valor, String campo, String extraccion) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            //Un objeto Statement se usa para enviar sentencias SQL a la base de datos
            Statement stat = conn.createStatement();
            //ResultSet no es más que una clase java similar a una lista en la que está el resultado de la consulta
            ResultSet rs = stat.executeQuery("select * from datos where " + campo + " = '" + valor + "'");//executeQuery para su uso con SELECT
            stat.close();
            conn.close();
            while (rs.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {
                extraccion = rs.getString(campo);

            }
            //Cerramos la conexion

        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }
    }
/**
 * A traves de este método obtendremos un array bidimensional con todos los registro de la bdd buscados usando el campo y el valor.
 * 
 * @param campo: campo sobre el que hacemos la busqueda
 * @param valor: Valor que buscamos en la base de datos
 * @return: array bidimensional donde array[fila][campo de la bdd]
 * @throws ClassNotFoundException 
 */
    public Object[][] selectmulti(String campo, String valor) throws ClassNotFoundException {

        int objetos = 0;

        try {

            //String RDNI, String Rnombre, String Rapellido, String Rdireccion, String Rtelefono
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();

            ResultSet num = stat.executeQuery("select * from datos where " + campo + " like'%" + valor + "%'");//executeQuery para su uso con SELECT

            while (num.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {
                objetos++;
            }

            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        String[][] arrayselect = new String[objetos][5];

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from datos where " + campo + " like '%" + valor + "%'");//executeQuery para su uso con SELECT
            objetos = 0;

            while (rs.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {

                arrayselect[objetos][0] = rs.getString("DNI");
                arrayselect[objetos][1] = rs.getString("nombre");
                arrayselect[objetos][2] = rs.getString("apellido");
                arrayselect[objetos][3] = rs.getString("direccion");
                arrayselect[objetos][4] = rs.getString("telefono");
                objetos++;

            }

            stat.close();
            conn.close();//Cerramos la conexion

        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        return arrayselect;
    }
/**
 * A traves de este método obtendremos un array bidimensional con todos los registro de la bdd buscados usando el campo y el valor.
 * En este lo haremos a traves de los datos de nombre y apellidos de la base de datos, es una busqueda flexible ya que 
 * la realizamos con like.
 * 
 * @param nombre: valor a buscar en la base de datos
 * @param apellido: Valor que buscamos en la base de datos
 * @return: array bidimensional donde array[fila][campo de la bdd]
 * @throws ClassNotFoundException 
 */
    public Object[][] selectmulti2(String nombre, String apellido) throws ClassNotFoundException {

        int objetos = 0;
        String query;
        if (nombre.equals("")) {
            nombre = " ";
        }
        if (apellido.equals("")) {
            apellido = " ";
        }

        try {
            //String RDNI, String Rnombre, String Rapellido, String Rdireccion, String Rtelefono
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            ResultSet num = stat.executeQuery("select * from datos where nombre like '%" + nombre + "%' union select * from datos where apellido like '%" + apellido + "%'");
            while (num.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {
                objetos++;
            }

            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        String[][] arrayselect = new String[objetos][5];

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from datos where nombre like '%" + nombre + "%' union select * from datos where apellido like '%" + apellido + "%'");//executeQuery para su uso con SELECT
            objetos = 0;

            while (rs.next()) //executeUpdate para INSERT, UPDATE, DELETE
            {
                arrayselect[objetos][0] = rs.getString("DNI");
                arrayselect[objetos][1] = rs.getString("nombre");
                arrayselect[objetos][2] = rs.getString("apellido");
                arrayselect[objetos][3] = rs.getString("direccion");
                arrayselect[objetos][4] = rs.getString("telefono");
                objetos++;
            }
            stat.close();
            conn.close();//Cerramos la conexion

        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }

        return arrayselect;
    }
/**
 * Nos devualve el numero de registros con una busqueda multiple de nombre y apellidos. Es flexible
 * ya que se hace con like asi obtendremos un numero mayor de posibilidades al buscar que si fuera un valor estatico con =.
 * @param nombre
 * @param apellido
 * @return: valor entero que nos indica el número total de registros en esa busqueda.
 * @throws ClassNotFoundException 
 */
    public int contar2(String nombre, String apellido) throws ClassNotFoundException {
    
        int x = 0;
        int objetos = 0;
        String query;
        if (nombre.equals("")) {
            nombre = " ";
        }
        if (apellido.equals("")) {
            apellido = " ";
        }
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from datos where nombre like '%" + nombre + "%' union select * from datos where apellido like '%" + apellido + "%'");
            //Localizamos cuantos datos hay con el valor de busqueda
            while (rs.next()) {
                x++;
            }
            stat.close();
            conn.close();//Cerramos la conexion
        } catch (SQLException ex) {
            System.out.println("Error de seleccion");
        }
        return x;
    }

}
