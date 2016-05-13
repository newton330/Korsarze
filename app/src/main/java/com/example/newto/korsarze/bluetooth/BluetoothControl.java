package com.example.newto.korsarze.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observer;
import java.util.UUID;


/**
 * Created by Kamil Szuba on 2016-04-09.
 */
public class BluetoothControl {

    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // Member fields
    private final BluetoothAdapter bluetoothAdapter;
    //private final Handler mHandler;
    private ObservableStack observableByteBuffer;
    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private BluetoothState appState;
    private static BluetoothControl singleton = null;

    private BluetoothControl() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        observableByteBuffer = new ObservableStack();
        appState = BluetoothState.IDLE;
        // mHandler = handler;
    }

    public static BluetoothControl getBluetoothControl() {
        if (singleton == null) {
            singleton = new BluetoothControl();

        }
        return singleton;
    }
    public void setObserver(Observer observerToSet){
        observableByteBuffer.addObserver(observerToSet);
    }
    public Integer getNextData(){
       return observableByteBuffer.getTopByte();
    }
    public synchronized BluetoothState getState() {
        return appState;
    }


    public synchronized void startListening() {

        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (acceptThread == null) {
            acceptThread = new AcceptThread();
            acceptThread.start();
            System.out.println("start listening");
        }
        appState = BluetoothState.LISTENING;
    }

    public synchronized void connect(String macAddress) {

        if (appState == BluetoothState.CONNECTING) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);
        connectThread = new ConnectThread(device);
        connectThread.start();
        appState = BluetoothState.CONNECTING;
    }

    private synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }


        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        appState = BluetoothState.CONNECTED;
    }


    public synchronized void stop() {

        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }

        appState = BluetoothState.IDLE;
    }

    public void write(byte[] out) {
        ConnectedThread tempConnectedThread;
        synchronized (this) {
            if (appState != BluetoothState.CONNECTED) return;
            tempConnectedThread = connectedThread;
        }
        // Perform the write unsynchronized
        tempConnectedThread.write(out);
    }

    private void connectionFailed() {

        BluetoothControl.this.startListening();
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket bluetoothServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(
                        "SerialData", BluetoothControl.MY_UUID);

            } catch (IOException e) {
                System.out.println("Error while listening for connection." + e);
            }

            bluetoothServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            while (appState != BluetoothState.CONNECTED) {
                try {
                    socket = bluetoothServerSocket.accept();

                    appState = BluetoothState.CONNECTING;
                } catch (IOException e) {
                    System.out.println("Accept failed" + e);
                    break;
                }
                System.out.println("appstate: " + appState);
                if (socket != null) {
                    synchronized (this) {
                        switch (appState) {
                            case LISTENING:
                                break;
                            case CONNECTING:
                                System.out.println("Connecting...");
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case IDLE:
                                break;
                            case CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    System.out.println("Error while closing acceptedthread socket." + e);
                                }
                                break;
                        }
                    }
                }
            }

        }

        public void cancel() {
            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                System.out.println("Error while closing acceptedthread bluetoothServerSocket." + e);

            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final BluetoothDevice bluetoothDevice;

        public ConnectThread(BluetoothDevice device) {
            bluetoothDevice = device;
            BluetoothSocket tmp = null;


            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(
                        MY_UUID);
            } catch (IOException e) {
                System.out.println("Failed to create socket to device." + e);
            }
            bluetoothSocket = tmp;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                bluetoothSocket.connect();
            } catch (IOException e) {
                try {
                    bluetoothSocket.close();
                } catch (IOException ex) {
                    System.out.println("Unable to close bluetoothSocket." + ex);
                }
                connectionFailed();
                return;
            }

            synchronized (this) {
                connectThread = null;
            }


            connected(bluetoothSocket, bluetoothDevice);
        }

        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                System.out.println("Unable to close bluetootSocket " + e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream bluetoothInStream;
        private final OutputStream bluetoothOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                System.out.println("Unable to create I/O streams" + e);
            }

            bluetoothInStream = tmpIn;
            bluetoothOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = bluetoothInStream.read(buffer);
                    observableByteBuffer.push(bytes);
                } catch (IOException e) {
                    System.out.println("Connection lost.");
                    startListening();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                bluetoothOutStream.write(buffer);


            } catch (IOException e) {
                System.out.println("Unable to write message " + e);
            }
        }

        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                System.out.println("Unable to close socket" + e);
            }
        }
    }
}
