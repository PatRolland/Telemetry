/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

/**
 *
 * @author club
 */
public class MathFct {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) java.lang.Math.pow(10, places);
        value = value * factor;
        long tmp = java.lang.Math.round(value);
        return (double) tmp / factor;
    }    
}
