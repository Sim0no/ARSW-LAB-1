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
public class CountThread extends Thread{

    int rangoInicial,rangoFinal;
    public CountThread(int a, int b){
        this.rangoInicial = a;
        this.rangoFinal = b;
    }
    @Override
    public void run(){
        for (int i = rangoInicial; i <= rangoFinal; i++) {
            System.out.println(i);
        }
    }
}
