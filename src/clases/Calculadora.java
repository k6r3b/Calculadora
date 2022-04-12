/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clases;

import exceptions.MalFormatoOperacion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carlos
 */
public class Calculadora {

    public static void main(String[] args) {
        try {
            System.out.println(parsearCalculo("10+1"));
        } catch (MalFormatoOperacion ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Patron para comprobar que sean numeros
    private static final String STR_REG_NUMERO = "[0-9]+(\\.[0-9]+)?";
    public static final Pattern PATRON_NUMERO = Pattern.compile(STR_REG_NUMERO);

    //Patron para buscar comprobar operaciones sin parentesis
    private static final String STR_REG_SIGNO = "[+\\-*^%]";
    public static final Pattern PATRON_SIGNOS = Pattern.compile(STR_REG_SIGNO);

    // Patron de operacion basica de 2 operandos
    private static final String STR_REG_OP_SIMPLE = STR_REG_NUMERO + STR_REG_SIGNO + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_SIMPLE = Pattern.compile(STR_REG_OP_SIMPLE);

    // Patron de operacion basica de 3+ operandos
    private static final String STR_REG_OP_MULTIPLE = STR_REG_OP_SIMPLE + "(" + STR_REG_SIGNO + STR_REG_NUMERO + ")+";
    public static final Pattern PATRON_OP_MULTIPLE = Pattern.compile(STR_REG_OP_MULTIPLE);
    
    // Patron dque confirma una operacion de 2+ operandos
    private static final String STR_REG_OP_CUALQUIER = STR_REG_OP_SIMPLE + "|" + STR_REG_OP_MULTIPLE;
    public static final Pattern PATRON_OP_CUALQUIER = Pattern.compile(STR_REG_OP_CUALQUIER);

    // Patron para buscar multiples operaciones dentro de parentesis
    private static final String STR_REG_PARENTESIS = "\\(" + STR_REG_OP_CUALQUIER + "\\)";
    public static final Pattern PATRON_PARENTESIS = Pattern.compile(STR_REG_PARENTESIS);

    /**
     * Analiza la operacion introducida, dividiendola en operaciones mas
     * sencillas y resolviendolas con el metodo parsearCalculo()
     *
     * @param calculo
     * @return
     */
    public static double parsearString(String calculo) {
//        throw new Exception("Metodo aun sin implementar");
        double ret = 0;
        var matcherPar = PATRON_PARENTESIS.matcher(calculo);

//        while (false) {
//            
//        }
        return ret;
    }

    /**
     * Comprueba el formato de una operacion basica de 2 operandos y devuelve el
     * resultado de dicha operacion
     *
     * @param operacion String que contiene la operacion a ejecutar
     * @return El resultado de la operacion
     * @throws MalFormatoOperacion
     */
    public static double parsearCalculo(String operacion) throws MalFormatoOperacion {
        double ret = 0;

        Matcher matcher = PATRON_SIGNOS.matcher(operacion);

        // Si el String no coincide lanza un error
        if (!operacion.matches(STR_REG_OP_SIMPLE)) {
            throw new MalFormatoOperacion("El String '" + operacion + "' no tiene el formato apropiado");
        }

        String[] operandos = operacion.split(STR_REG_SIGNO);
        double op1 = Double.parseDouble(operandos[0]);
        double op2 = Double.parseDouble(operandos[1]);

        matcher.find();
        String tipo = operacion.substring(matcher.start(), matcher.end());

        switch (tipo) {
            case "+":
                ret = op1 + op2;
                break;
            case "-":
                ret = op1 - op2;
                break;
            case "*":
                ret = op1 * op2;
                break;
            case "/":
                ret = op1 / op2;
                break;
            case "^":
                ret = Math.pow(op1, op2);
                break;
            case "%":
                ret = op1 % op2;
                break;
        }
        return ret;
    }

}
