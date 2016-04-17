/*
 * Copyright 2016 IBM Corp. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.simpleapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibm.helper.TaskReceiver;
import com.ibm.simpleapp.tasks.CheckLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements TaskReceiver {

    public static String _parsingError = "Server response is not understood";

    private ProgressDialog _pDialogue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //set the view as defined in res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        //Retrieve the login button from the main view
        //and assign the task of checking login credential
        //to it

        Button loginButton = ( Button ) findViewById( R.id.loginBtn );

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLogin();
            }

        });

    }

    public void checkLogin(){

        //retrieve the username and password values from user
        EditText userText = ( EditText ) findViewById( R.id.unameText );
        EditText passwordText = ( EditText ) findViewById( R.id.passwordText );

        //the username or password field cannot be empty
        if( userText.getText().toString().equals( "") ||
                passwordText.getText().toString().equals( "")    ){

            Toast.makeText( getApplicationContext() , "username and password cannot be empty", Toast.LENGTH_LONG )
                 .show();

        }

        //else assume everything is right from the client side
        //pass the data to the client side for validation
        //this would cause the application to wait
        //so start a progress dialogue and then pass the data

        _pDialogue = new ProgressDialog( this );

        _pDialogue.setCancelable(false);
        _pDialogue.setTitle("Logging in...");
        _pDialogue.setMessage("Sending credentials");
        _pDialogue.show();


        CheckLogin cl = new CheckLogin( "http://10.0.2.2:3000/checklogin" );
        cl.setReceiver( this );
        cl.execute( userText.getText().toString(), passwordText.getText().toString() );



    }

    @Override
    public void receiveResult( String response, String source) {

        //so the result has arrived.

        //first, close the dialogue
        _pDialogue.dismiss();
        _pDialogue = null;

        //then check if this is the checklogin task
        if( source.equals( CheckLogin._taskID) ){

            try{
                JSONObject responseObject = new JSONObject( response );

                String record = responseObject.getString( "result" );
                String success = responseObject.getString( "record" );
                Toast.makeText( getApplicationContext() , record+":"+success, Toast.LENGTH_LONG )
                        .show();

            }
            catch( JSONException je ){
                Toast.makeText( getApplicationContext() , _parsingError, Toast.LENGTH_LONG )
                        .show();

            }



        }

    }
}
