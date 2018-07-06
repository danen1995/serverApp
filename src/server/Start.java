/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javax.swing.JFrame;
import rs.ac.bg.fon.silab.server.form.FServer;

/**
 *
 * @author FON
 */
public class Start {
    public static void main(String[] args) {
//        Server server = new Server();
//        server.start();
        JFrame form = new FServer();
        form.setVisible(true);
    }
}
