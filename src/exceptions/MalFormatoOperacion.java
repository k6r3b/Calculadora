/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author carlos
 */
public class MalFormatoOperacion extends Exception {

    private final String operacion;
    
    public MalFormatoOperacion(String message, String operacion) {
        super(message);
        this.operacion = operacion;
    }

    public String getOperacion() {
        return operacion;
    }
    
}
