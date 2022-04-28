/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clases;

import exceptions.MalFormatoOperacion;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carlos
 */
public class Calculadora {

    /*public static void main(String[] args) {
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
    }*/
    // Patron para comprobar que sean numeros
    private static final String STR_REG_NUMERO = "(\\-?[0-9]+(\\.[0-9]+)?)";
    private static final Pattern PATRON_NUMERO = Pattern.compile(STR_REG_NUMERO);

    // Patron para buscar comprobar operaciones sin parentesis
    private static final String STR_REG_SIGNO_SIMPLE = "([√])";
    private static final Pattern PATRON_SIGNO_SIMPLE = Pattern.compile(STR_REG_SIGNO_SIMPLE);

    private static final String STR_REG_SIGNO_COMPUESTO = "([+/\\-*^%√])";
    private static final Pattern PATRON_SIGNO_COMPUESTO = Pattern.compile(STR_REG_SIGNO_COMPUESTO);

    // Patron de operacion basica de 2 operandos
    private static final String STR_REG_OP_SUMA = "(" + STR_REG_NUMERO + "\\+" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_SUMA = Pattern.compile(STR_REG_OP_SUMA);

    private static final String STR_REG_OP_RESTA = "(" + STR_REG_NUMERO + "\\-" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_RESTA = Pattern.compile(STR_REG_OP_RESTA);

    private static final String STR_REG_OP_MULT = "(" + STR_REG_NUMERO + "\\*" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_MULT = Pattern.compile(STR_REG_OP_MULT);

    private static final String STR_REG_OP_DIV = "(" + STR_REG_NUMERO + "\\/" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_DIV = Pattern.compile(STR_REG_OP_DIV);

    private static final String STR_REG_OP_POT = "(" + STR_REG_NUMERO + "\\^" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_POT = Pattern.compile(STR_REG_OP_POT);

    private static final String STR_REG_OP_MOD = "(" + STR_REG_NUMERO + "\\%" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_MOD = Pattern.compile(STR_REG_OP_MOD);

    private static final String STR_REG_OP_SQRT = "(√" + STR_REG_NUMERO + ")";
    private static final Pattern PATRON_OP_SQRT = Pattern.compile(STR_REG_OP_SQRT);

    private static final String STR_REG_OP_SIMPLE_SING = "(" + STR_REG_SIGNO_SIMPLE + STR_REG_NUMERO + ")";
    private static final String STR_REG_OP_SIMPLE_DOBLE = "(" + STR_REG_NUMERO + STR_REG_SIGNO_COMPUESTO + STR_REG_NUMERO + ")";
    private static final String STR_REG_OP_SIMPLE = "(" + STR_REG_OP_SIMPLE_DOBLE + "|" + STR_REG_OP_SIMPLE_SING + ")";

    // Patron de operacion basica de 3+ operandos
    private static final String STR_REG_OP_MULTIPLE = "(" + STR_REG_OP_SIMPLE + "(" + STR_REG_SIGNO_COMPUESTO + STR_REG_SIGNO_SIMPLE + "*" + STR_REG_NUMERO + ")+)";

    // Patron dque confirma una operacion de 2+ operandos
    private static final String STR_REG_OP_CUALQUIER = "(" + STR_REG_OP_SIMPLE + "|" + STR_REG_OP_MULTIPLE + ")";

    // Patron para buscar multiples operaciones dentro de parentesis
    private static final String STR_REG_PAR_SIMPLE = "(\\(" + STR_REG_OP_CUALQUIER + "\\))";
    private static final Pattern PATRON_PAR_SIMPLE = Pattern.compile(STR_REG_PAR_SIMPLE);

    public static String getSTR_REG_NUMERO() {
        return STR_REG_NUMERO;
    }

    public static Pattern getPATRON_NUMERO() {
        return PATRON_NUMERO;
    }

    public static String getSTR_REG_SIGNO_SIMPLE() {
        return STR_REG_SIGNO_SIMPLE;
    }

    public static Pattern getPATRON_SIGNO_SIMPLE() {
        return PATRON_SIGNO_SIMPLE;
    }

    public static String getSTR_REG_SIGNO_COMPUESTO() {
        return STR_REG_SIGNO_COMPUESTO;
    }

    public static Pattern getPATRON_SIGNO_COMPUESTO() {
        return PATRON_SIGNO_COMPUESTO;
    }

    public static String getSTR_REG_OP_SUMA() {
        return STR_REG_OP_SUMA;
    }

    public static Pattern getPATRON_OP_SUMA() {
        return PATRON_OP_SUMA;
    }

    public static String getSTR_REG_OP_RESTA() {
        return STR_REG_OP_RESTA;
    }

    public static Pattern getPATRON_OP_RESTA() {
        return PATRON_OP_RESTA;
    }

    public static String getSTR_REG_OP_MULT() {
        return STR_REG_OP_MULT;
    }

    public static Pattern getPATRON_OP_MULT() {
        return PATRON_OP_MULT;
    }

    public static String getSTR_REG_OP_DIV() {
        return STR_REG_OP_DIV;
    }

    public static Pattern getPATRON_OP_DIV() {
        return PATRON_OP_DIV;
    }

    public static String getSTR_REG_OP_POT() {
        return STR_REG_OP_POT;
    }

    public static Pattern getPATRON_OP_POT() {
        return PATRON_OP_POT;
    }

    public static String getSTR_REG_OP_MOD() {
        return STR_REG_OP_MOD;
    }

    public static Pattern getPATRON_OP_MOD() {
        return PATRON_OP_MOD;
    }

    public static String getSTR_REG_OP_SQRT() {
        return STR_REG_OP_SQRT;
    }

    public static Pattern getPATRON_OP_SQRT() {
        return PATRON_OP_SQRT;
    }

    public static String getSTR_REG_OP_SIMPLE_SING() {
        return STR_REG_OP_SIMPLE_SING;
    }

    public static String getSTR_REG_OP_SIMPLE_DOBLE() {
        return STR_REG_OP_SIMPLE_DOBLE;
    }

    public static String getSTR_REG_OP_SIMPLE() {
        return STR_REG_OP_SIMPLE;
    }

    public static String getSTR_REG_OP_MULTIPLE() {
        return STR_REG_OP_MULTIPLE;
    }

    public static String getSTR_REG_OP_CUALQUIER() {
        return STR_REG_OP_CUALQUIER;
    }

    public static String getSTR_REG_PAR_SIMPLE() {
        return STR_REG_PAR_SIMPLE;
    }

    public static Pattern getPATRON_PAR_SIMPLE() {
        return PATRON_PAR_SIMPLE;
    }
    
    /**
     * Resuelve la operacion introducida como texto y la devuelve como número
     * entero.
     *
     * @param operacion La operacion a resolver
     * @return Resultado de la operación
     * @throws exceptions.MalFormatoOperacion Si la operacion tiene errores.
     */
    public static double resolverCalculo(String operacion) throws MalFormatoOperacion {
        double ret = 0;
        String strRet = operacion;
        Matcher matcherPar;
        Matcher matcherMP = Pattern.compile(
                STR_REG_NUMERO
                + "\\)\\("
                + "|[0-9]\\("
                + "|\\)[0-9]"
                + "|[0-9]√"
                + "|\\)√"
        ).matcher(strRet);

        try {
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
                int ind1 = matcherPar.start(), ind2 = matcherPar.end();
                StringBuilder sb = new StringBuilder(strRet);
                String nuevo = new Operacion(strRet.substring(ind1 + 1, ind2 - 1)).getResultado().toString();

                strRet = quitarNumParentesis(sb.replace(ind1, ind2, nuevo).toString());

                matcherPar.reset(strRet);
            }

            strRet = new Operacion(strRet).getResultado().toString();

            ret = Double.parseDouble(strRet);
        } catch (NumberFormatException nfe) {
            throw new MalFormatoOperacion("Formato erróneo", strRet);
        } catch (MalFormatoOperacion mfo) {
            throw mfo;
        }
        
        return ret;
    }
    
    /**
     * Quita los parentesis que rodean a un único número.
     * @param texto
     * @return Operacion sin paréntesis
     */
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
    
}
