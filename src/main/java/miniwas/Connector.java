package miniwas;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector extends Thread {

    private int port;

    public Connector(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("서버 시작!");
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                socket = serverSocket.accept();
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();

                System.out.println(isa.getHostName() +" 에서 접속");
                SocketHandler handler = new SocketHandler(this, socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {socket.close();} catch (IOException e) {}
            try {serverSocket.close();} catch (IOException e) {}
        }
    }
}