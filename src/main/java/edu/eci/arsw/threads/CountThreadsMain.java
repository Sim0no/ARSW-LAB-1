/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        final int hilos = 3;
        int aux1 = 99;
        int aux2 = 0;
        CountThread arregloHilos[] = new CountThread[hilos];
        for (int i = 0; i < 3 ; i++) {
            CountThread hilo = new CountThread(aux2,aux1);
            aux1+=100;
            aux2+=100;
            arregloHilos[i] = hilo;
        }
        for (CountThread c:arregloHilos) {
            c.start();
//            c.run();
        }
        
    }
    
}
