/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import exceptions.MalFormatoOperacion;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carlos
 */
public class Operacion {
    
    private final Resultado resultado;

    private static final Pattern PATRON_OP_SUMA = Calculadora.getPATRON_OP_SUMA();
    private static final Pattern PATRON_OP_RESTA = Calculadora.getPATRON_OP_RESTA();
    private static final Pattern PATRON_OP_MULT = Calculadora.getPATRON_OP_MULT();
    private static final Pattern PATRON_OP_DIV = Calculadora.getPATRON_OP_DIV();
    private static final Pattern PATRON_OP_POT = Calculadora.getPATRON_OP_POT();
    private static final Pattern PATRON_OP_SQRT = Calculadora.getPATRON_OP_SQRT();
    private static final Pattern PATRON_OP_MOD = Calculadora.getPATRON_OP_MOD();
    
    public Operacion(String operacion) throws MalFormatoOperacion {
        this.resultado = new Resultado(resolverOperacion(operacion));
    }

    public Resultado getResultado() {
        return resultado;
    }
    
    /**
     * Parsea operaciones de 3+ operandos y devuelve el resultado
     *
     * @param operacion
     * @return
     * @throws MalFormatoOperacion
     */
    private double resolverOperacion(String operacion) throws MalFormatoOperacion {
        String strRet = operacion;
        strRet = buscaPatron(PATRON_OP_SQRT, strRet);
        strRet = buscaPatron(PATRON_OP_POT, strRet);
        strRet = buscaPatron(PATRON_OP_MOD, strRet);
        strRet = buscaPatron(PATRON_OP_MULT, strRet);
        strRet = buscaPatron(PATRON_OP_DIV, strRet);
        strRet = buscaPatron(PATRON_OP_SUMA, strRet);
        strRet = buscaPatron(PATRON_OP_RESTA, strRet);

        return Double.parseDouble(strRet);
    }

    /**
     * Recibe un patrón de una operacion de 2 digitos y una cadena de una
     * operacion de 2+ digitos, busca todas las coincidencias del patrón en la
     * cadena y las sustituye por su resultado.
     *
     * @param patron El patrón de operación a buscar
     * @param operacion La operacion en la que buscar el patrón.
     * @return
     */
    private String buscaPatron(Pattern patron, String operacion) throws MalFormatoOperacion {
        var matcher = patron.matcher(operacion);

        while (matcher.find()) {
            var ind1 = matcher.start();
            var ind2 = matcher.end();
            var sb = new StringBuilder(operacion);
            sb.replace(ind1, ind2, resolverBasico(operacion.substring(ind1, ind2))).toString();
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
    private String resolverBasico(String operacion) throws MalFormatoOperacion {
        double ret, op1 = 0, op2 = 0;
        String strOp = operacion, tipo = "";
        Matcher matcherNum, matcherTipo;

        //Primer Operador
        matcherNum = Calculadora.getPATRON_NUMERO().matcher(strOp);
        if (matcherNum.find()) {
            op1 = Double.parseDouble(strOp.substring(matcherNum.start(), matcherNum.end()));
            strOp = strOp.replaceFirst(Calculadora.getSTR_REG_NUMERO(), "");
        }

        //Signo
        var patronSignoCompuesto = Calculadora.getPATRON_SIGNO_COMPUESTO();
        matcherTipo = patronSignoCompuesto.matcher(strOp);
        if (matcherTipo.find()) {
            tipo = strOp.substring(matcherTipo.start(), matcherTipo.end());
            strOp = strOp.replaceFirst(Calculadora.getSTR_REG_SIGNO_COMPUESTO(), "");
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
                if (op1<=0) {
                    throw new MalFormatoOperacion("Intento de raiz a un número negativo", strOp);
                }
                ret = Math.sqrt(op1);
                break;
        }

        return Double.toString(ret);
    }

    
}
