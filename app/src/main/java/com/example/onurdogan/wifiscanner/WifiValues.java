package com.example.onurdogan.wifiscanner;

public class WifiValues {

    String WifiName;
    String WifiMac;
    int WifiStrenght;

    private int level1 = -50;   // (dBm)
    private int level2 = -70;
    private int level3 = -80;


    public WifiValues(String wifiName, String wifiMac, int wifiStrenght) {
        WifiName = wifiName;
        WifiMac = wifiMac;
        WifiStrenght = wifiStrenght;
    }

    public String getWifiName() {
        return WifiName;
    }

    public void setWifiName(String wifiName) {
        WifiName = wifiName;
    }

    public String getWifiMac() {
        return WifiMac;
    }

    public void setWifiMac(String wifiMac) {
        WifiMac = wifiMac;
    }

    public int getWifiStrenght() {
        return CalculateStrength(WifiStrenght);
    }

    public void setWifiStrenght(int wifiStrenght) {
        WifiStrenght = wifiStrenght;
    }

    public int CalculateStrength(int Mac){

        int index;
        if(Mac>level1){                       //Excellent Connection
            index = 0;
        }else if(level2<Mac && Mac<level1){   //Good Connection
            index = 1;
        }else if(level3<Mac && Mac<level2){   //Fair Connection
            index = 2;
        }else{                                // Weak Connection
            index = 3;
        }
        return index;
    }
}
