package com.example.ganeshshetty.yakshanidhi;
/**
 * Author : Ganesh Shetty
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Flash_Activity extends Activity {
    private int SPLASH_TIME_OUT = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                final Dialog dialog = new Dialog(Flash_Activity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.languagedialog);
                // Set dialog title
                dialog.setTitle("User Language");
                // set values for custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.title);
                final RadioButton radiokannada=(RadioButton)dialog.findViewById(R.id.kannada);
                final RadioButton radioenglish=(RadioButton)dialog.findViewById(R.id.english);
                dialog.show();
                Button done = (Button) dialog.findViewById(R.id.done_button);
                // if done button is clicked, close the custom dialog
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(radiokannada.isChecked()==true){
                            setLanguage("kn");
                        }else if(radioenglish.isChecked()==true)
                        {
                            setLanguage("en");
                        }else {
                            Toast.makeText(Flash_Activity.this, "Select any one of the language", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }, SPLASH_TIME_OUT);


    }
    public void setLanguage(String language_code)
    {
        Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent mainIntent = new Intent(Flash_Activity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
