package com.example.edmol.webview;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class tab2Automatico extends Fragment {
    RelativeLayout display2;
    String fondoActual;
    RadioButton rbLitros, rbCubicos, rbGalones;
    EditText agua;
    ToggleButton botonPrender2;

    Handler bluetoothIn;
    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2automatico, container, false);
        display2 = (RelativeLayout) v.findViewById(R.id.Fondo);
        agua = (EditText) v.findViewById(R.id.etAgua);
        rbLitros = (RadioButton) v.findViewById(R.id.rbLitros);
        rbGalones = (RadioButton) v.findViewById(R.id.rbGalones);
        rbCubicos = (RadioButton) v.findViewById(R.id.rbCubicos);
        botonPrender2 = (ToggleButton) v.findViewById(R.id.botonPrender2);

        rbLitros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.VISIBLE);
                galones.setVisibility(View.INVISIBLE);
                cubicos.setVisibility(View.INVISIBLE);*/
            }
        });

        rbGalones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.INVISIBLE);
                galones.setVisibility(View.VISIBLE);
                cubicos.setVisibility(View.INVISIBLE);*/
            }
        });

        rbCubicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.INVISIBLE);
                galones.setVisibility(View.INVISIBLE);
                cubicos.setVisibility(View.VISIBLE);*/
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        botonPrender2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mConnectedThread.write("x"); //manda x cuando se prende
                    mConnectedThread.write(agua.getText().toString()); //manda cantidad de agua
                } else {
                    mConnectedThread.write("0"); //manda o cuando se apaga
                }
            }
        });
        return v;
    }

    //conexion
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice("98:D3:32:30:77:71");

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
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
        super.onPause();
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
            Toast.makeText(getContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                //finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String fondoSeleccionado = data.getStringExtra("fondoSeleccionado").toString();
                fondoActual = fondoSeleccionado;
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
                }
            }

        }
    }
}