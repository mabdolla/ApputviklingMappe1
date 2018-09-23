package com.example.mohammadabdolla.s309856mappe1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); // Språkpreferanser, shared preferences blir laget
        String language = sharedPreferences.getString("preferenceslanguage","no");        // listpreference i preferences xml blir brukt. shared preferences blir lagt i stringen language
        Context context =getApplicationContext();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onRestart() {                                                                    //recreate metode blir lagt i onRestart slik at preferanser blir oppdatert underveis i spillet.
        super.onRestart();
        recreate();
    }

    public void showgame(View v) {                                                                  // Andre akiviterer blir lagt til
        Intent i=new Intent(this,GameActivity.class);
        startActivity(i);
    }

    public void showpreferances(View v) {
        Intent j=new Intent(this,GamePreferencesActivity.class);
        startActivity(j);
    }

    public void showStatistics(View v) {
        Intent k=new Intent(this,Statistics.class);
        startActivity(k);
    }

}
