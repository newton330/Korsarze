package com.example.newto.korsarze.bluetooth;

/**
 * Created by Kamil Szuba on 2016-04-10.
 */
public class BluetoothDeviceDataStructure {

    public String nameOfDevice;
    public String macAddress;

    public BluetoothDeviceDataStructure(String nameOfDevice, String macAddress) {

        this.nameOfDevice = nameOfDevice;
        this.macAddress = macAddress;
    };

    @Override
    public String toString() {
        return this.nameOfDevice + "\n" + this.macAddress;
    }
}

