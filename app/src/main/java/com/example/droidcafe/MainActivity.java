package com.example.droidcafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String mOrderMessage;
    FusedLocationProviderClient fusedLocationProviderClient;
    private String mLongitude;
    private String mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra(OrderActivity.ORDER_KEY_EXTRA, mOrderMessage);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_order) {
//            return true;
//        }

        switch (item.getItemId()){
            case R.id.action_order:
                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                intent.putExtra(OrderActivity.ORDER_KEY_EXTRA, mOrderMessage);
                startActivity(intent);
                return true;
            case R.id.action_call:
                Uri uri = Uri.parse("tel:0711223344");
                Intent myIntent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(myIntent);
                return true;
            case R.id.action_location:
                checkPermission();
                Uri gmmIntentUri = Uri.parse("geo:"+ mLongitude +","+ mLatitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                //Check if there is an app to receive the intent
                if(mapIntent.resolveActivity(getPackageManager()) != null);{
                    startActivity(mapIntent);
                }
                return true;
            case R.id.action_status:
                Uri websiteUri = Uri.parse("https://developer.android.com/training/basics/firstapp/building-ui");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, websiteUri);
                startActivity(webIntent);
                return true;




        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
            //wHEN GRANTED
            getLocation();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if(location != null){
                    //initialize geocoder
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    //Initialize address list
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                        //Set latitude
                        mLongitude = String.valueOf(addresses.get(0).getLongitude());
                        //setLongitude
                        mLatitude =  String.valueOf(addresses.get(0).getLatitude());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    //    Displaying toast message
    public void displayToast(String message){
        Toast.makeText(getApplicationContext() , message, Toast.LENGTH_SHORT).show();

    }

    public void showIcecreamMessage(View view) {
        mOrderMessage = getString(R.string.icecream_order);
        displayToast(mOrderMessage);

    }

    public void showDonutMessage(View view) {
        mOrderMessage = getString(R.string.donut_order);
        displayToast(mOrderMessage);
    }

    public void showFroyoMessage(View view) {
        mOrderMessage = getString(R.string.froyo_order);
        displayToast(mOrderMessage);
    }
}
