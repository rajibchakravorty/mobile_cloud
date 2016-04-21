package com.ibm.simpleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the view as defined in res/layout/first_page.xml
        setContentView( R.layout.first_page );

        //retrieve the information passed on from the login page
        //and display that
    }
}
