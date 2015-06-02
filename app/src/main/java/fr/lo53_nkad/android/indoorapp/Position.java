package fr.lo53_nkad.android.indoorapp;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by root on 29/05/15.
 */
public class Position {

    /**
     * location on the map (map coordinate)
     */
    int mapX;
    int mapY;

    /**
     * @mac Mobile identifier
     */
    String macAdd;
    int rssi;
    /**
     * Constructor
     */
    public Position(Context context) {
        this.macAdd = getMacAddress(context);
    }

    /**
     * Getter and Setter  section
     */
    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public String getMacAdd() {
        return macAdd;
    }

    public void setMacAdd(String macAdd) {
        this.macAdd = macAdd;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     *  String getMacAddress this function return the actual wifi mac address of the device
     */
    public String getMacAddress(Context context){
        WifiManager manager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        String mac = manager.getConnectionInfo().getMacAddress();
        if(mac == null){
            mac = "Device don't have mac or wifi is disable";
            return null;
        }
        return mac;
    }
}
