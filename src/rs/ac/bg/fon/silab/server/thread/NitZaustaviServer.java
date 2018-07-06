/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.thread;

import java.net.ServerSocket;

/**
 *
 * @author Antic
 */
public class NitZaustaviServer extends Thread{
    ThreadServer server;
    ServerSocket soket;

    public NitZaustaviServer(ThreadServer server, ServerSocket soket) {
        this.server = server;
        this.soket = soket;
    }

    @Override
    public void run() {
        while(true){
            if(server.isInterrupted()){
                try {
                    soket.close();
                            
                } catch (Exception e) {
                }
            }
        }
        
    }
    
    
}
