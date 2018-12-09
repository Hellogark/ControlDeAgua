package com.example.edmol.webview;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class control extends AppCompatActivity {

    CoordinatorLayout display2;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private TextView mCargaTexto;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    TextView aguaTotal;
    TextView flujoAgua;

    //variables para la conexion
    Handler bluetoothIn;
    EditText velocidad;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private SectionsPagerAdapter.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        display2 = (CoordinatorLayout) findViewById(R.id.Fondo);
        mProgressBar = (ProgressBar) findViewById(R.id.pbProgreso);
        mCargaTexto = (TextView) findViewById(R.id.tvCargaCompleta);
        aguaTotal = (TextView) findViewById(R.id.txtCantidad);
        flujoAgua = (TextView) findViewById(R.id.txtCantidad);

        String fondoActual;
        fondoActual = getIntent().getExtras().getString("fondoActual");
        switch (fondoActual) {
            case "colorFondo1":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                break;
            case "colorFondo2":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                break;
            case "colorFondo3":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                break;
            case "colorFondo4":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                break;
            default:
                Toast.makeText(this, "Algo malo pasó", Toast.LENGTH_SHORT).show();
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCargaTexto.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //codigo de conexion
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //imprime los datos recibidos
                        //txtString.setText("Datos recibidos = " + dataInPrint);
                        int dataLength = dataInPrint.length();
                        //imprime el tamaño del string
                        //txtStringLength.setText("Tamaño del String = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            //separa el string recibido
                            String valor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String valor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            flujoAgua.setText(valor0); //se insertan los valores que manda el arduino
                            aguaTotal.setText(valor1);

                            /*if(sensor0.equals("1.00"))
                                sensorView0.setText("Encendido");	//update the textviews with sensor values
                            else
                                sensorView0.setText("Apagado");	//update the textviews with sensor values
                                sensorView1.setText(sensor1);
                                sensorView2.setText(sensor2);
                                sensorView3.setText(sensor3);
                            //sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                            */
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        mSectionsPagerAdapter.checkBTState();

        //EJEMPLO DE APAGAR, ENCENDER Y MANDAR VALOR AL ARDUINO
        /*// Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("x");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Apagar el LED", Toast.LENGTH_SHORT).show();
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("o");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Encender el LED", Toast.LENGTH_SHORT).show();
            }
        });
        //mandar velocidad de 0 a 9
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConnectedThread.write(velocidad.getText().toString());    // Send "0-9" via Bluetooth
                Toast.makeText(getBaseContext(), "Manda velocidad", Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter implements com.example.edmol.webview.SectionsPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: tab1Manual tab1 = new tab1Manual();
                return tab1;

                case 1: tab2Automatico tab2 = new tab2Automatico();
                return tab2;

                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position){
            switch (position){
                case 0: return "Manual";
                case 1: return "Automático";
            }
            return null;
        }

        //codigo de conexion
        @Override
        public void onResume() {
            mSectionsPagerAdapter.onResume();

            //create device and set the MAC address
            //Log.i("ramiro", "adress : " + address);
            BluetoothDevice device = btAdapter.getRemoteDevice("98:D3:32:30:77:71");

            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try
            {
                btSocket.connect();
            } catch (IOException e) {
                try
                {
                    btSocket.close();
                } catch (IOException e2)
                {
                    //insert code to deal with this
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();

            //I send a character when resuming.beginning transmission to check device is connected
            //If it is not an exception will be thrown in the write method and finish() will be called
            mConnectedThread.write("x");
        }

        @Override
        public void onPause()
        {
            mSectionsPagerAdapter.onPause();
            try
            {
                //Don't leave Bluetooth sockets open when leaving activity
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }

        //Checks that the Android device Bluetooth is available and prompts to be turned on if off
        private void checkBTState() {

            if(btAdapter==null) {
                Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
            } else {
                if (btAdapter.isEnabled()) {
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 1);
                }
            }
        }

        //create new class for connect thread
        private class ConnectedThread extends Thread {
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;

            //creation of the connect thread
            public ConnectedThread(BluetoothSocket socket) {
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                try {
                    //Create I/O streams for connection
                    tmpIn = socket.getInputStream();
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) { }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }


            public void run() {
                byte[] buffer = new byte[256];
                int bytes;

                // Keep looping to listen for received messages
                while (true) {
                    try {
                        bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                        String readMessage = new String(buffer, 0, bytes);
                        // Send the obtained bytes to the UI Activity via handler
                        bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                    } catch (IOException e) {
                        break;
                    }
                }
            }
            //write method
            public void write(String input) {
                byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
                try {
                    mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
                } catch (IOException e) {
                    //if you cannot write, close the application
                    Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
    }
}
}