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
            System.out.println(parsearString("3"));
            System.out.println(parsearString("((3))"));
            System.out.println(parsearString("3*10"));
            System.out.println(parsearString("3+10"));
            System.out.println(parsearString("3-10"));
            System.out.println(parsearString("((3.1*10)3)/2"));
            System.out.println(parsearString("3.1/10"));
            System.out.println(parsearString("-3^3"));
            System.out.println(parsearString("10%3"));
        } catch (MalFormatoOperacion ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Patron para comprobar que sean numeros
    private static final String STR_REG_NUMERO = "(\\-?[0-9]+(\\.[0-9]+)?)";
    public static final Pattern PATRON_NUMERO = Pattern.compile(STR_REG_NUMERO);

    // Patron para buscar comprobar operaciones sin parentesis
    private static final String STR_REG_SIGNO = "([+/\\-*^%√])";
    public static final Pattern PATRON_SIGNO = Pattern.compile(STR_REG_SIGNO);

    // Patron de operacion basica de 2 operandos
    private static final String STR_REG_OP_SUMA = "(" + STR_REG_NUMERO + "\\+" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_SUMA = Pattern.compile(STR_REG_OP_SUMA);

    private static final String STR_REG_OP_RESTA = "(" + STR_REG_NUMERO + "\\-" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_RESTA = Pattern.compile(STR_REG_OP_RESTA);

    private static final String STR_REG_OP_MULT = "(" + STR_REG_NUMERO + "\\*" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_MULT = Pattern.compile(STR_REG_OP_MULT);

    private static final String STR_REG_OP_DIV = "(" + STR_REG_NUMERO + "\\/" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_DIV = Pattern.compile(STR_REG_OP_DIV);

    private static final String STR_REG_OP_POT = "(" + STR_REG_NUMERO + "\\^" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_POT = Pattern.compile(STR_REG_OP_POT);

    private static final String STR_REG_OP_MOD = "(" + STR_REG_NUMERO + "\\%" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_MOD = Pattern.compile(STR_REG_OP_MOD);

    private static final String STR_REG_OP_SQRT = "(\\√" + STR_REG_NUMERO + ")";
    public static final Pattern PATRON_OP_SQRT = Pattern.compile(STR_REG_OP_SQRT);

    private static final String STR_REG_OP_SIMPLE = "(" + STR_REG_NUMERO + STR_REG_SIGNO + STR_REG_NUMERO + "|" + STR_REG_OP_SQRT + ")";
    public static final Pattern PATRON_OP_SIMPLE = Pattern.compile(STR_REG_OP_SIMPLE);
    
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
        String strRet = operacion;
        Matcher matcherPar;
        Matcher matcherMP = Pattern.compile(STR_REG_NUMERO + "\\)\\(|[0-9]\\(|\\)[0-9]|[0-9]\\√").matcher(strRet);

        //Incluir multiplicacion al lado de parentesis
        while (matcherMP.find()) {
            var sb = new StringBuilder(strRet);
            int ind1 = matcherMP.start();
            sb.insert(ind1 + 1, "*");
            strRet = sb.toString();
            matcherMP.reset(strRet);
        }

        strRet = quitarNumParentesis(strRet);
        matcherPar = PATRON_PAR_SIMPLE.matcher(strRet);
        
        //Parsear operaciones
        while (matcherPar.find()) {
            StringBuilder sb;
            int ind1 = matcherPar.start(), ind2 = matcherPar.end();
            String nuevo = parsearOperacionCompuesta(strRet.substring(ind1 + 1, ind2 - 1));
            
            sb = new StringBuilder(strRet);
            sb.replace(ind1, ind2, nuevo);
            
            strRet = sb.toString();
            strRet = quitarNumParentesis(strRet);
            
            matcherPar.reset(strRet);
        }

        strRet = parsearOperacionCompuesta(strRet);

        try {
            ret = Double.parseDouble(strRet);
        } catch (NumberFormatException nfe) {
            throw new MalFormatoOperacion("La operacion introducida(" + strRet + ") presenta un formato, o caracteres erroneos");
        }

        return ret;
    }

    private static String quitarNumParentesis(String texto) {
        String ret = texto;
        Matcher matcher = Pattern.compile("\\(" + STR_REG_NUMERO + "\\)").matcher(ret);

        while (matcher.find()) {
            int ind1 = matcher.start(), ind2 = matcher.end();
            var sb = new StringBuilder(ret);
            sb.replace(ind1, ind2, ret.substring(ind1 + 1, ind2 - 1));

            ret = sb.toString();
            matcher.reset(ret);
        }

        return ret;
    }

    /**
     * Parsea operaciones de 3+ operandos y devuelve el resultado
     *
     * @param operacion
     * @return
     * @throws MalFormatoOperacion
     */
    private static String parsearOperacionCompuesta(String operacion) {
        String ret;

        String strRet = operacion;
        strRet = buscaPatron(PATRON_OP_SQRT, strRet);
        strRet = buscaPatron(PATRON_OP_POT, strRet);
        strRet = buscaPatron(PATRON_OP_MOD, strRet);
        strRet = buscaPatron(PATRON_OP_MULT, strRet);
        strRet = buscaPatron(PATRON_OP_DIV, strRet);
        strRet = buscaPatron(PATRON_OP_SUMA, strRet);
        strRet = buscaPatron(PATRON_OP_RESTA, strRet);

        ret = strRet;
        return ret;
    }

    /**
     * Recibe un patrón de una operacion de 2 digitos y una cadena de una
     * operacion de 2+ digitos, busca todas las coincidencias del patrón en la
     * cadena y las sustituye por su resultado.
     *
     * @param patron
     * @param operacion
     * @return
     */
    private static String buscaPatron(Pattern patron, String operacion) {
        var matcher = patron.matcher(operacion);

        while (matcher.find()) {
            var ind1 = matcher.start();
            var ind2 = matcher.end();
            var sb = new StringBuilder(operacion);
            String nuevoVal;

            nuevoVal = parsearCalculo(operacion.substring(ind1, ind2));
            sb.replace(ind1, ind2, nuevoVal);
            operacion = sb.toString();
            matcher.reset(operacion);
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
    private static String parsearCalculo(String operacion) {
        double ret, op1 = 0, op2 = 0;
        String strOp = operacion, tipo = "";
        Matcher matcherNum, matcherTipo;

        //Primer Operador
        matcherNum = PATRON_NUMERO.matcher(strOp);
        if (matcherNum.find()) {
            op1 = Double.parseDouble(strOp.substring(matcherNum.start(), matcherNum.end()));
            strOp = strOp.replaceFirst(STR_REG_NUMERO, "");
        }

        //Signo
        matcherTipo = PATRON_SIGNO.matcher(strOp);
        if (matcherTipo.find()) {
            tipo = strOp.substring(matcherTipo.start(), matcherTipo.end());
            strOp = strOp.replaceFirst(STR_REG_SIGNO, "");
        }

        //Segundo Operador
        matcherNum.reset(strOp);
        if (matcherNum.find()) {
            op2 = Double.parseDouble(strOp.substring(matcherNum.start(), matcherNum.end()));
        }

        switch (tipo) {
            default:
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
                ret = Math.pow(Math.abs(op1), op2);
                break;
            case "%":
                ret = op1 % op2;
                break;
            case "√":
                ret = Math.sqrt(op1);
                break;
        }

        return Double.toString(ret);
    }

}
