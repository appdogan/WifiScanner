package com.example.onurdogan.wifiscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ScanResult> scanResults;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private ArrayList<WifiValues> wifiValueslist;
    private WifiValues wifiValues;
    private IntentFilter ıntentFilter;
    private boolean success;
    private boolean successUptade;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private String ToastScanning = "Taranıyor";
    private String ToastError = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //scanWifi();
    }

    private void init() {

        recyclerView = findViewById(R.id.recycler);
    }

    private void prepareWifi() {

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        checkWifi();
        scanWifi();
    }

    private void checkPermissions() {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        else {
            prepareWifi();
        }
    }

    private void scanWifi() {

        checkWifi();
        ıntentFilter = new IntentFilter();
        ıntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        ıntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(broadcastReceiver,ıntentFilter);
        success = wifiManager.startScan();
        checkWifiDatas(success);
        ShowToast(ToastScanning);
    }

    private void checkWifiDatas(boolean success) {

        if(success == true){
            takeDatas();
        }else{
            ShowToast(ToastError);
        }
    }

    private void ShowToast(String msg) {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void takeDatas() {
        Log.d("Link Speed", String.valueOf(wifiInfo.getLinkSpeed()));
        scanResults = wifiManager.getScanResults();
        wifiValueslist = new ArrayList<>();

        for(ScanResult result: scanResults){


            wifiValueslist.add(new WifiValues(result.SSID,result.BSSID,result.level));
        }

        showDatas(wifiValueslist);

    }

    private void showDatas(ArrayList<WifiValues> wifiValuesArrayAdapter) {

        adapter = new Adapter(wifiValuesArrayAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            prepareWifi();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent ıntent) {
            checkWifi();
            successUptade = ıntent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED,false);
            checkWifiDatas(successUptade);
        }
    };

    private void checkWifi() {
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }

}
