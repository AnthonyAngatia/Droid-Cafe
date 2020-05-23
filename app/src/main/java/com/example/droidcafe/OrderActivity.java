package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    public static final String ORDER_KEY_EXTRA= "My Key" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        String retrieved = intent.getStringExtra(ORDER_KEY_EXTRA);
        TextView textView = findViewById(R.id.text_display_order_heading);
        textView.setText(retrieved);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radio_same_day:
                if(checked)
                    displayToast(getString(R.string.same_day_messenger_service));
                break;
            case R.id.radio_next_day:
                if(checked)
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.radio_pick_up:
                if(checked)
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                //Nothing
                break;
        }


    }
    public void displayToast(String message){
        Toast.makeText(getApplicationContext() , message, Toast.LENGTH_SHORT).show();
    }

}
