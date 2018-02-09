package mbds.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;


public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private static final UUID MY_UUID = new UUID(2,5);
    private static final String TAG = "ClientBluetooth";
    BluetoothAdapter mBluetoothAdapter;
    Mascotte mascotte;
    Mascotte mascotteEnnemie;

    ArrayList<LogCombat> logsCombat;

    public ConnectThread(BluetoothDevice device , Mascotte pMascotte) {

        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;
        mascotte = pMascotte;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        manageMyConnectedSocket(mmSocket);
    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
            String message_distant = in.readLine();
            Log.d("BLUETOOTH", message_distant);
            mascotteEnnemie = Mascotte.deserialize(message_distant);

            PrintWriter out = new PrintWriter(mmSocket.getOutputStream());
            out.println(mascotte.serialize());
            out.flush();

            // Recois les données du combat
            Log.d("BLUETOOTH", "On reçoit les données du combat");
            ObjectInputStream objectInput = new ObjectInputStream(mmSocket.getInputStream());
            //ByteArrayInputStream input2 = new ByteArrayInputStream();
            //byte[] bytesFromSocket = objectInput.
            //bytesFromSocket
            //ByteArrayInputStream bis = new ByteArrayInputStream();
            //ObjectInputStream ois = new ObjectOutputStream(bis);

            Object obj = null;
            try {
                obj = objectInput.readObject();
                Log.d("BLUETOOTH", "Objet lu");
                this.logsCombat = (ArrayList<LogCombat>)obj;
                Log.d("BLUETOOTH", "Objet sauvegardé et casté");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // pas de base
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

    public Mascotte getMascotteEnnemie(){
        return mascotteEnnemie;
    }

    // Dans le thread, on vérifiera si connecthread ou acceptthread a les logs remplis pour les charger
    public ArrayList<LogCombat> getLogsCombat() {
        return logsCombat;
    }

}
