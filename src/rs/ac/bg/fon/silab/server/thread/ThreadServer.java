/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.jdbc.example1.components.table.model.RadnikTableModel;
import rs.ac.bg.fon.silab.server.form.FServer;

/**
 *
 * @author FON
 */
public class ThreadServer extends Thread{

    FServer gui;
    ServerSocket serverSocket;
    List<Thread> ulogovaniRadnici;

    public ThreadServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        ulogovaniRadnici = new ArrayList<>();
    }

    public ThreadServer(int port, FServer aThis) throws IOException {
        serverSocket = new ServerSocket(port);
        ulogovaniRadnici = new ArrayList<>();
        gui = aThis;
    }

    public List<Thread> getUlogovaniRadnici() {
        return ulogovaniRadnici;
    }
    
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                NitZaustaviServer zaustvi = new NitZaustaviServer(this, serverSocket);
                zaustvi.start();
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ThreadClient client = new ThreadClient(socket,this);
                client.start();
                //New client arrived
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void prekini() {
        this.interrupt();
    }

    public void dodajRadnika(ThreadClient client) {
        ulogovaniRadnici.add(client);
        RadnikTableModel model = (RadnikTableModel) gui.getTabela().getModel();
        model.dodaj(client.getRadnik());
    }

    void odjavi(ThreadClient client) {
        ulogovaniRadnici.remove(client);
        gui.odjavi(client.getRadnik());
    }
    
//    public List<ThreadClient> vratiKonektovane() {
//        List<ThreadClient> odjavljeni = new ArrayList<>();
//        for (ThreadClient client : clients) {
//            if(client.getSocket().isClosed()){
//                odjavljeni.add(client);
//            }
//        }
//        
//        for (ThreadClient odj : odjavljeni) {
//            clients.remove(odj);
//        }
//        return clients;
//    }
    
    
    
}
