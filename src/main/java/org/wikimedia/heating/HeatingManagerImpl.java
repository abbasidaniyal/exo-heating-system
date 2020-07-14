package org.wikimedia.heating;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HeatingManagerImpl {

    public void manageHeating(String t, String threshold, boolean active) {
        double dT = Double.parseDouble(t);
        double dThreshold = Double.parseDouble(threshold);
        if (active) {
            heaterOn(dT < dThreshold);
        }
    }

    private void heaterOn(boolean turnOn) {
        try {
            Socket socket = new Socket("heater.home", 9999);
            OutputStream os = socket.getOutputStream();
            if (turnOn)
                os.write("on".getBytes());
            else
                os.write("off".getBytes());
            os.flush();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
