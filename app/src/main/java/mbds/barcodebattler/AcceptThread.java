package mbds.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

public class AcceptThread extends Thread {

    private static final String NAME = "monDevice";
    private static final UUID MY_UUID = new UUID(2,5);
    private static final String TAG = "ServeurBluetooth";
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter;
    Handler mascotteHandler;
    Mascotte mascotte;
    Mascotte mascotteEnnemie;
    ArrayList<LogCombat> logsCombat;

    public AcceptThread(Handler h) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        mascotteHandler = h;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                manageMyConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket)
    {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(mascotte.serialize());
            out.flush();

//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String message_distant = in.readLine();
//            Log.d("BLUETOOTH", message_distant);
//            Mascotte m = Mascotte.deserialize(message_distant);
//            Message message = new Message();
//            Bundle b = new Bundle();
//            b.putParcelable("personne",message);
//            message.setData(b);
//            mascotteHandler.dispatchMessage(message);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_distant = in.readLine();
            Log.d("BLUETOOTH", message_distant);
            mascotteEnnemie = Mascotte.deserialize(message_distant);

            // On fait le combat, on envoie les logs. Puis on les charge.
            Log.d("BLUETOOTH", "On va envoyer les logs de combat");
            ArrayList<LogCombat> logsCombatsSave = new ArrayList<>();
            ServiceCombat.lancerCombat(mascotte, mascotteEnnemie, logsCombatsSave);
            Log.d("BLUETOOTH", "Logs de combat remplis, taille :" + logsCombatsSave.size());

            //ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(logsCombatsSave);
            oos.flush();
            Log.d("BLUETOOTH", "Logs de combat flush");
            oos.close();

            //byte[] byteToTransfer = oos.getBytes();

            //out.println(this.logsCombat);
            //out.flush();
            Log.d("BLUETOOTH", "On sauvegarde nos données de combat");
            this.logsCombat = logsCombatsSave;

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }

    public void setMascotte(Mascotte mascotte){
        this.mascotte = mascotte;
    }

    public Mascotte getMascotteEnnemie(){
        return mascotteEnnemie;
    }

    // Dans le thread, on vérifiera si connecthread ou acceptthread a les logs remplis pour les charger
    public ArrayList<LogCombat> getLogsCombat() {
        return logsCombat;
    }
}

