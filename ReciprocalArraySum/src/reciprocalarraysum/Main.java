/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reciprocalarraysum;

/**
 *
 * @author Lisa
 */
public class Main {

    public static void main(String[] args) {
        double[] zahlen = new double[100000];
        for (int i = 0; i < zahlen.length; i++) {
            zahlen[i] = Math.random() * 10;
        }

        double erg = ReciprocalArraySum.parManyTaskArraySum(zahlen, 4);
        System.out.println("Ergebniss: " + erg);
    }

}
