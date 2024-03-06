package vn.vimass.service.crawler.bhd.DongBoVsServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendDataFaceToDevice {
    public String strS(){

        String serverIP = "phone_ip_address";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverIP, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("Hello, Phone!");
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverIP);
            System.exit(1);
        }

        return "";
    }
}
