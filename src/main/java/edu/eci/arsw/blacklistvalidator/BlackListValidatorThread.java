package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


public class BlackListValidatorThread extends Thread{

    private String ip;
    private HostBlacklistsDataSourceFacade repositorio;
    int rangoInicial,rangoFinal;
    private LinkedList<Integer> blackListOcurrences;
    private AtomicInteger ocurrencesCount, checked;

    public BlackListValidatorThread(String ipaddress, int a, int b, LinkedList<Integer> blackListOcurrences, AtomicInteger ocurrencesCount, AtomicInteger checked){

        ip = ipaddress;
        repositorio = HostBlacklistsDataSourceFacade.getInstance();
        rangoInicial = a;
        rangoFinal = b;
        this.blackListOcurrences = blackListOcurrences;
        this.ocurrencesCount = ocurrencesCount;
        this.checked = checked;
    }


    @Override
    public void run() {
        for (int i = rangoInicial; i<rangoFinal && this.ocurrencesCount.get()< HostBlackListsValidator.BLACK_LIST_ALARM_COUNT; i++) {
            this.checked.getAndIncrement();
            if (repositorio.isInBlackListServer(i, ip)) {

                blackListOcurrences.add(i);
                ocurrencesCount.getAndIncrement();
            }
        }
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
