/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author xisko
 */
public class Usuario {
    
    //Declaración de variables
    private String contrasena;
    private String nick;
    private String email;
    private String passPatern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private String nickPattern = "[a-zA-Z0-9-_]{3,32}";
    private String emailPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    /*
	 * -La contraseña deberá tener como mínimo 8 caracteres -La contraseá deberá
	 * tener como mínimo 1 mayúscula -La contraseña deberá tener como mínimo 1
	 * minúscula -La contraseña deberá tener como mínimo 1 número -La contraseña
	 * puede tener carácteres especiales
	 * -----------------------------------------------------------------------------
	 * -El usuario puede contener caracteres de la A-Z, a-z, 0-9 y _ -Minimo 3
	 * caracteres -Máximo 32 caracteres
     */
    /**
     * Comprueba la validez del nombre de usuario, de la contraseña y del email.
     * Tiene dos métodos, uno para registrar la contraseña con un hash, y otro
     * método para comparar contraseñas con hashes.
     *
     * @param contrasena
     * @param nick
     * @param email
     * @throws IllegalArgumentException
     */
    public Usuario(String contrasena, String nick, String email) throws IllegalArgumentException {
        setContrasena(contrasena);
        setNick(nick);
        setEmail(email);
    }

    // Devuelve la excepción "IllegalArgumentException" Si la contrasena no es
    // correcta
    private void setContrasena(String contrasena) {
        if (compruebaContrasena(contrasena)) {
            this.contrasena = contrasena;
        } else {
            throw new IllegalArgumentException("Contrasena incorrecta");
        }
    }

    //Devuelve la excepción "IllegalArgumentException" si el usuario no es correcto
    private void setNick(String nick) {
        if (compruebaNick(nick)) {
            this.nick = nick;
        } else {
            throw new IllegalArgumentException("Nick incorrecto");
        }
    }

    private void setEmail(String email) {
        if (compruebaEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email incorrecto");
        }
    }

    //Comprueba si está acorde con la expresión regular
    private boolean compruebaNick(String nick) {
        boolean compruebaNick = true;
        if (!nick.matches(nickPattern)) {
            compruebaNick = false;
        }
        return compruebaNick;
    }

    // Comprobará si hay algún espacio o si está acorde a la expresión regular
    private boolean compruebaContrasena(String contrasena) {
        int comprob = 0;
        boolean comprueba = true;// Boolean por defecto en verdadero
        for (int i = 0; i < contrasena.length(); i++) {
            if (Character.isWhitespace(contrasena.charAt(i))) {
                // Si encuentra algún espacio, se incrementará en uno comprob
                comprob += 1;
            }
        }
        if (comprob >= 1) {
            // Si es mayor o igual que 1. Significará que hay un espacio en la contraseña
            // Devolverá falso.
            comprueba = false;
        } else if (!contrasena.matches(passPatern)) {
            // Si no concuerda con la expresión regular, devuelve falso.
            comprueba = false;
        }
        // Devuelve un único return.
        return comprueba;
    }

    //Comprueba si está acorde con la expresión regular
    private boolean compruebaEmail(String email) {
        boolean compruebaEmail = true;
        if (!email.matches(emailPattern)) {
            compruebaEmail = false;
        }
        return compruebaEmail;
    }

    /**
     * Este método devolverá la contrasena con la que el usuario se quiere
     * registrar
     *
     * @param password
     * @param password2
     * @return password
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public String registraContrasena(String password, String password2) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException {
        contrasenasIguales(password, password2);
        password = passHash(password);
        return password;
    }

    //Comprueba si las dos contrasenas son iguales, si no, devuelve una excepción.
    private boolean contrasenasIguales(String password, String password2) {
        password = this.contrasena;
        if (!password.equals(password2)) {
            throw new IllegalArgumentException("Contrasenas no son iguales");
        } else {
            return true;
        }
    }

    // Método que te devuelve un hash de un String.
    private String passHash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(password));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }

    /**
     * Este método comprobará la contraseña registrada con la contrasena
     * introducida por el usuario
     *
     * @param hashPassword
     * @param intentoContrasena
     * @return boolean
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean compruebaContrasenas(String hashPassword, String intentoContrasena)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        intentoContrasena = passHash(intentoContrasena);
        if (!intentoContrasena.equals(hashPassword)) {
            return false;
        } else {
            return true;
        }

    }
    
}
