package miniwas;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketHandler extends Thread {

    private Connector wasServer;
    private Socket socket;

    public SocketHandler(Connector connector, Socket socket) {
        this.wasServer = connector;
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        InputStream is = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            List<String> temp = new ArrayList<String>();

            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                temp.add(line);
                if (line.equals("")) {
                    break;
                }
            }

            //Request req = new Request(temp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }
}