package mbds.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class CombatReseauSelect extends AppCompatActivity {

    ImageView imageMascotte;
    Mascotte mascotte;
    Button btnAccepteCombat;
    Button btnRetour;
    Button btnChercher;
    BluetoothAdapter mBluetoothAdapter;
    ArrayList<BluetoothDevice> listDevice;
    AcceptThread thread1;
    ConnectThread thread2;
    public Handler mHandler;
    Thread thread;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
                listDevice.add(device);
                Log.d("BLUETOOTH", "Ajout d'un device : " + listDevice);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_reseau_select);
        listDevice = new ArrayList<>();
        imageMascotte = (ImageView) findViewById(R.id.imagemascotte);
        btnAccepteCombat = (Button) findViewById(R.id.accepteCombat);
        btnRetour = (Button) findViewById(R.id.retour);
        btnChercher = (Button) findViewById(R.id.chercher);
        this.hideButton(true);

        //INSCRIPTION
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        imageMascotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatReseauSelect.this, ListMascotte.class);
                startActivityForResult(intent, 1);
            }
        });
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopThreads();
                finish();
            }
        });

        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // si l’adapter est null, le téléphone ne supporte pas le bluetooth
                    Toast.makeText(CombatReseauSelect.this, "Change de téléphone", Toast.LENGTH_LONG);
                }
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 2);
                } else {
                    runBt();
                }
            }
        });

        btnAccepteCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Discoverable
                Intent discoverableIntent =
                        new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
                startActivityForResult(discoverableIntent, 3);
            }
        });

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // On attend que des données de combat soient présentes
                        if((thread1 != null && thread1.getLogsCombat() != null) ||
                                (thread2 != null && thread2.getLogsCombat() != null)) {

                            ArrayList<LogCombat> logsCombat = new ArrayList<>();
                            if (thread1 != null && thread1.getLogsCombat() != null)
                                logsCombat = thread1.getLogsCombat();
                            if (thread2 != null && thread2.getLogsCombat() != null)
                                logsCombat = thread2.getLogsCombat();

                            Intent intent = new Intent(CombatReseauSelect.this, CombatMascottesActivity.class);

                            Log.d("BLUETOOTH", "On a récupéré logsCombat, taille : " + logsCombat.size());
                            intent.putExtra("logsCombat", (Serializable) logsCombat);

                            this.interrupt();
                            startActivity(intent);
                            break;
                        } else {
                            Log.d("MascotteTEST" , "je dors en attendant");
                            sleep(1000);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                mascotte = data.getExtras().getParcelable("Mascotte");
                if (mascotte != null) {
                    TextView txtMascotte1 = (TextView) findViewById(R.id.nommascotte);
                    txtMascotte1.setText(mascotte.getNom());

                    Drawable img = getResources().getDrawable( mascotte.getIdImage());
                    Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();

                    imageMascotte.setImageBitmap(imgBitmap);
                    this.hideButton(false);
                }
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                runBt();
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_CANCELED) {
                Log.d("BLUETOOTH", "discoverable done");
            } else {
                thread1 = new AcceptThread(mHandler);
                //Set la mascotte pour envoi
                thread1.setMascotte(mascotte);
                thread1.start();
            }
        }
    }

    private void hideButton(boolean bool) {
        if (bool) {
            btnAccepteCombat.setVisibility(View.GONE);
            btnChercher.setVisibility(View.GONE);
        } else {
            btnAccepteCombat.setVisibility(View.VISIBLE);
            btnChercher.setVisibility(View.VISIBLE);
        }
    }

    private void runBt() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        CharSequence[] cs = new CharSequence[pairedDevices.size()];

        if (pairedDevices.size() > 0) {
            for (int i = 0; i < pairedDevices.size(); i++) {
                BluetoothDevice device = (BluetoothDevice) pairedDevices.toArray()[i];
                listDevice.add(device);
                Log.d("BLUETOOTH", "Ajout d'un device : " + listDevice);
                String deviceName = device.getName();
                cs[i] = deviceName;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth appairé");
            builder.setItems(cs, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    BluetoothDevice device = listDevice.get(item);
                    thread2 = new ConnectThread(device, mascotte);
                    thread2.start();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
       stopThreads();
       finish();
    }

    public void stopThreads(){
        if(thread1 != null){
            thread1.cancel();
        }
        if(thread2 != null){
            thread2.cancel();
        }
        if(thread != null){
            thread.interrupt();
        }
    }

}
