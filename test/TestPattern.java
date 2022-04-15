/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import clases.Calculadora;

/**
 *
 * @author carlos
 */
public class TestPattern {
    
    public TestPattern() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testParentesis() {
        var patron = Calculadora.PATRON_PAR_SIMPLE;
        
        var matcher = patron.matcher("(1.3+2)");
        System.out.println(matcher.matches());
    }
    @Test
    public void testNumero() {
        var patron = Calculadora.PATRON_NUMERO;

        var matcher = patron.matcher("*10.3");
        System.out.println(matcher.matches());
    }
}
    

