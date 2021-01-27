/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    public static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int n){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();

        AtomicInteger checked =  new AtomicInteger(0) ;

        AtomicInteger ocurrencesCount = new AtomicInteger(0) ;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();


        int aux = (skds.getRegisteredServersCount() / n) % 2 == 0 ? 0 : 1;

        BlackListValidatorThread hilos[] = new BlackListValidatorThread[n+aux];
        int inicial = 0;
        int end = skds.getRegisteredServersCount() / n;
        int rango = skds.getRegisteredServersCount() / n;


        for (int i = 0; i < n; i++) {
            BlackListValidatorThread hilo = new BlackListValidatorThread(ipaddress, inicial, end, blackListOcurrences, ocurrencesCount, checked);
            inicial = end +1;
            end +=  rango;
            hilos[i] = hilo;
        }


        if(aux == 1){

            BlackListValidatorThread hilo = new BlackListValidatorThread(ipaddress, end, skds.getRegisteredServersCount(), blackListOcurrences, ocurrencesCount, checked);
            hilos[n] = hilo;
        }



        for (BlackListValidatorThread t : hilos) {
            t.start();
        }
        for (BlackListValidatorThread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                t.interrupt();
            }
        }

        
        if (ocurrencesCount.get()>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checked, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
