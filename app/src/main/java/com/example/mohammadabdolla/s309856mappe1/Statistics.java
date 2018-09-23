package com.example.mohammadabdolla.s309856mappe1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Statistics extends Activity {

 static int wrongans = 0;
 static int rightans = 0;
 TextView statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
    }

    public void seeStatistic(){
        statistic = (TextView) findViewById(R.id.statistic);
        statistic.setText(wrongans + " " + rightans);
    }

}
