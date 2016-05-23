package com.example.newto.korsarze;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.newto.korsarze.bluetooth.BluetoothControl;
import com.example.newto.korsarze.bluetooth.BluetoothDeviceDataStructure;

import java.util.Set;

public class ConnectActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    ToggleButton btButton;
    ToggleButton scanButton;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<BluetoothDeviceDataStructure> btArrayAdapter;
    ListView scanDevicesListView;
    BroadcastReceiver bluetoothReceiver;
    TextView scanDevicesLabel;
    BluetoothControl bluetoothControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect);
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scanDevicesLabel = (TextView) findViewById(R.id.btDevicesLabel);
        bluetoothReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    btArrayAdapter.add(new BluetoothDeviceDataStructure(device.getName(), device.getAddress()));
                }
            }
        };
        scanDevicesListView = (ListView) findViewById(R.id.devicesListView);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothControl = BluetoothControl.getBluetoothControl();
        scanButton = (ToggleButton) findViewById(R.id.scanToggleButton);

        scanButton.setChecked(bluetoothAdapter.isDiscovering());
        scanButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btArrayAdapter.clear();
                btArrayAdapter.notifyDataSetChanged();

                if (isChecked) {
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(bluetoothReceiver, filter);
                    bluetoothAdapter.startDiscovery();
                    scanDevicesLabel.setText("Nearby devices:");



                } else {

                    bluetoothAdapter.cancelDiscovery();
                    setTextForLabelAndListView();
                }


            }
        });
        scanDevicesListView.setAdapter(btArrayAdapter);

        btButton = (ToggleButton) findViewById(R.id.btToggleButton);
        btButton.setChecked(bluetoothAdapter.isEnabled());
        btButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (bluetoothAdapter == null) {
                                                        Toast.makeText(getBaseContext(), "Bluetooth not supported. Aborting.", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (!isChecked) {
                                                            Toast.makeText(getBaseContext(), "Disabling bluetooth.", Toast.LENGTH_SHORT).show();
                                                            bluetoothControl.stop();
                                                            bluetoothAdapter.disable();
                                                            btButton.setChecked(false);
                                                            scanDevicesLabel.setText("");
                                                            bluetoothControl.stop();
                                                        } else {
                                                            Intent enableBtIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                                                            startActivityForResult(enableBtIntent, 1);//tu zamiast liczby fajnie by bylo uzyc stalej REQUEST_BT_ENABLE
                                                        }
                                                    }
                                                }
                                            }

        );

        registerForContextMenu(scanDevicesListView);


    }

    private void setTextForLabelAndListView() {
        btArrayAdapter.clear();
        if (!bluetoothAdapter.isEnabled()) {
            scanDevicesLabel.setText("");
        } else if (bluetoothAdapter.isDiscovering()) {
            scanDevicesLabel.setText("Nearby devices:");
        } else scanDevicesLabel.setText("Paired devices:");
        pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                btArrayAdapter.add(new BluetoothDeviceDataStructure(device.getName(), device.getAddress()));
            }
        }
        btArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.devicesListView) {
            menu.add("Connect");
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(getBaseContext(), "You are trying to connect with: " + btArrayAdapter.getItem(menuInfo.position).macAddress, Toast.LENGTH_SHORT).show();
        bluetoothAdapter.cancelDiscovery();
        String selectedDeviceMacAddress = btArrayAdapter.getItem(menuInfo.position).macAddress;

        bluetoothControl.connect(selectedDeviceMacAddress);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        btButton.setChecked(bluetoothAdapter.isEnabled());
        setTextForLabelAndListView();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {//z jakiegos powodu nie dzialaja mi stale z androida


            if (resultCode == Activity.RESULT_OK) {
                btButton.setChecked(true);
            } else {
                btButton.setChecked(false);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
        bluetoothAdapter.cancelDiscovery();//nie testowane, ale warto żeby było
    }

    public void Graj(View view) {
        Intent intent = new Intent(this, PrepareActivity.class);////////////zmiana tymczasowa
        startActivity(intent);}
    }
