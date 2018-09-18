package miniwas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketHandler extends Thread {

    private WasServer wasServer;
    private Socket socket;

    private String methodName;
    private String pathName;
    private Map<String, String> headerInfo;
    private Map<String, String> paramMap;

    public SocketHandler(WasServer wasServer, Socket socket) {
        this.wasServer = wasServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            List<String> temp = new ArrayList<String>();

            String line = null;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                temp.add(line);
                if (line.equals("")) {
                    break;
                }
            }

            String[][] splits = new String[temp.size()][];
            headerInfo = new HashMap<String, String>();

            for (int a = 0; a < temp.size() - 1; a++) {
                if (a == 0) {
                    splits[a] = temp.get(a).split(" ");
                    methodName = splits[a][0];
                    pathName = splits[a][1];

                    if (pathName.contains("?")) {
                        String[] reqInfo = pathName.split("\\?");
                        String[] paramInfo = reqInfo[1].split("&");
                        String[] param = new String[2];

                        paramMap = new HashMap<String, String>();
                        for (String p : paramInfo) {
                            param = p.split("=");
                            paramMap.put(param[0], param[1]);
                        }
                        pathName = reqInfo[0];
                    }
                    continue;
                }
                splits[a] = temp.get(a).split(": ");
                headerInfo.put(splits[a][0], splits[a][1]);
            }

            /*
            System.out.println(methodName);
            System.out.println(pathName);

            for (Map.Entry<String, String> entry : headerInfo.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }*/

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