package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;


public class BlackListValidatorThread extends Thread{

    private String ip;
    private HostBlacklistsDataSourceFacade repositorio;
    int rangoInicial,rangoFinal;
    private LinkedList<Integer> blackListOcurrences;


    public BlackListValidatorThread(String ipaddress, HostBlacklistsDataSourceFacade skds, int a, int b, LinkedList<Integer> blackListOcurrences){
        ip = ipaddress;
        repositorio = skds;
        rangoInicial = a;
        rangoFinal = b;
        this.blackListOcurrences = blackListOcurrences;

    }

    @Override
    public void run() {

        for (int i = rangoInicial; i<rangoFinal; i++) {
            if (repositorio.isInBlackListServer(i, ip)) {

                blackListOcurrences.add(i);

            }
        }
        //Matar el hilo
        Thread.currentThread().interrupt();


    }

    public int ocurrencias(){
        return blackListOcurrences.size();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public HostBlacklistsDataSourceFacade getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(HostBlacklistsDataSourceFacade repositorio) {
        this.repositorio = repositorio;
    }

    public int getRangoInicial() {
        return rangoInicial;
    }

    public void setRangoInicial(int rangoInicial) {
        this.rangoInicial = rangoInicial;
    }

    public int getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(int rangoFinal) {
        this.rangoFinal = rangoFinal;
    }

    public LinkedList<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

    public void setBlackListOcurrences(LinkedList<Integer> blackListOcurrences) {
        this.blackListOcurrences = blackListOcurrences;
    }
}
