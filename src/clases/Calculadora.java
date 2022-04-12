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
            System.out.println(parsearOperacionCompuesta("-10^3+15*3"));
        } catch (MalFormatoOperacion ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Patron para comprobar que sean numeros
    private static final String STR_REG_NUMERO = "([0-9]+(\\.[0-9]+)?)";
    public static final Pattern PATRON_NUMERO = Pattern.compile(STR_REG_NUMERO);

    //Patron para buscar comprobar operaciones sin parentesis
    private static final String STR_REG_SIGNO = "[+\\-*^%]";
    public static final Pattern PATRON_SIGNO = Pattern.compile(STR_REG_SIGNO);

    // Patron de operacion basica de 2 operandos
    private static final String STR_REG_OP_SIMPLE = "\\-?" + STR_REG_NUMERO + STR_REG_SIGNO + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_SIMPLE = Pattern.compile(STR_REG_OP_SIMPLE);

    private static final String STR_REG_OP_SUMA = "\\-?" + STR_REG_NUMERO + "\\+" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_SUMA = Pattern.compile(STR_REG_OP_SUMA);

    private static final String STR_REG_OP_RESTA = "\\-?" + STR_REG_NUMERO + "\\-" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_RESTA = Pattern.compile(STR_REG_OP_RESTA);

    private static final String STR_REG_OP_MULT = "\\-?" + STR_REG_NUMERO + "\\*" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_MULT = Pattern.compile(STR_REG_OP_MULT);

    private static final String STR_REG_OP_DIV = "\\-?" + STR_REG_NUMERO + "\\/" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_DIV = Pattern.compile(STR_REG_OP_DIV);

    private static final String STR_REG_OP_POT = "\\-?" + STR_REG_NUMERO + "\\^" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_POT = Pattern.compile(STR_REG_OP_POT);

    private static final String STR_REG_OP_MOD = "\\-?" + STR_REG_NUMERO + "\\%" + STR_REG_NUMERO;
    public static final Pattern PATRON_OP_MOD = Pattern.compile(STR_REG_OP_MOD);

    // Patron de operacion basica de 3+ operandos
    private static final String STR_REG_OP_MULTIPLE = "(" + STR_REG_OP_SIMPLE + "(" + STR_REG_SIGNO + STR_REG_NUMERO + ")+)";
    public static final Pattern PATRON_OP_MULTIPLE = Pattern.compile(STR_REG_OP_MULTIPLE);

    // Patron dque confirma una operacion de 2+ operandos
    private static final String STR_REG_OP_CUALQUIER = "(" + STR_REG_OP_SIMPLE + "|" + STR_REG_OP_MULTIPLE + ")";
    public static final Pattern PATRON_OP_CUALQUIER = Pattern.compile(STR_REG_OP_CUALQUIER);

    // Patron para buscar multiples operaciones dentro de parentesis
    private static final String STR_REG_PAR_SIMPLE = "(\\(" + STR_REG_OP_CUALQUIER + "\\))";
    public static final Pattern PATRON_PAR_SIMPLE = Pattern.compile(STR_REG_PAR_SIMPLE);

    /**
     * Analiza la operacion introducida, dividiendola en operaciones mas
     * sencillas y resolviendolas con el metodo parsearCalculo()
     *
     * @param operacion
     * @return
     * @throws exceptions.MalFormatoOperacion
     */
    public static double parsearString(String operacion) throws MalFormatoOperacion {

        double ret = 0;

        var matcherPar = PATRON_PAR_SIMPLE.matcher(operacion);

//        while (false) {
//            
//        }
        return ret;
    }

    /**
     * Parsea operaciones de 3+ operandos y devuelve el resultado
     *
     * @param operacion
     * @return
     * @throws MalFormatoOperacion
     */
    private static double parsearOperacionCompuesta(String operacion) throws MalFormatoOperacion {

        if (!operacion.matches(STR_REG_OP_CUALQUIER)) {
            throw new MalFormatoOperacion("El String '" + operacion + "' no tiene el formato: " + STR_REG_OP_CUALQUIER);
        }
        double ret = 0;

        operacion = cicloPatrones(PATRON_OP_POT, operacion);
        operacion = cicloPatrones(PATRON_OP_MOD, operacion);
        operacion = cicloPatrones(PATRON_OP_MULT, operacion);
        operacion = cicloPatrones(PATRON_OP_DIV, operacion);
        operacion = cicloPatrones(PATRON_OP_SUMA, operacion);
        operacion = cicloPatrones(PATRON_OP_RESTA, operacion);

        ret = Double.parseDouble(operacion);
        return ret;
    }

    private static String cicloPatrones(Pattern patron, String operacion) {
        var matcherMul = patron.matcher(operacion);

        while (matcherMul.find()) {
            var ind1 = matcherMul.start();
            var ind2 = matcherMul.end();
            var sb = new StringBuilder(operacion);
            var nuevoVal = 0d;
            try {
                nuevoVal = parsearCalculo(operacion.substring(ind1, ind2));
            } catch (MalFormatoOperacion ex) {
                Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
            }
            sb.replace(ind1, ind2, Double.toString(nuevoVal));
            operacion = sb.toString();
        }

        return operacion;
    }

    /**
     * Comprueba el formato de una operacion basica de 2 operandos y devuelve el
     * resultado de dicha operacion
     *
     * @param operacion String que contiene la operacion a ejecutar
     * @return El resultado de la operacion
     * @throws MalFormatoOperacion
     */
    private static double parsearCalculo(String operacion) throws MalFormatoOperacion {
        double ret = 0, op1, op2;
        Matcher matcher;
        String[] operandos;

        // Si el String no coincide lanza un error
        if (!operacion.matches(STR_REG_OP_SIMPLE)) {
            throw new MalFormatoOperacion("El String '" + operacion + "' no tiene el formato: " + STR_REG_OP_SIMPLE);
        }
        boolean firstNeg = false;
        if (operacion.startsWith("-")) {
            firstNeg = true;
            operacion = operacion.substring(1, operacion.length());
        }

        operandos = operacion.split(STR_REG_SIGNO);

        op1 = Double.parseDouble(operandos[0]) * (firstNeg ? -1 : 1);
        op2 = Double.parseDouble(operandos[1]);

        matcher = PATRON_SIGNO.matcher(operacion);
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
