package com.retaurentmenu.com.restaurentmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    @Bind(R.id.signInButton)
    SignInButton signInButton;
    /*Session  Mangements*/
    SharedPreferences   pref;

    /* Editor */
    SharedPreferences.Editor editor;

    public String  myprefs="MyPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref=getSharedPreferences(myprefs,MODE_PRIVATE);
        editor=pref.edit();
        ButterKnife.bind(Login.this);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Login.this, "heloo hello", Toast.LENGTH_SHORT).show();
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                //Initializing google api client
                mGoogleApiClient = new GoogleApiClient.Builder(Login.this)
                        .enableAutoManage(Login.this /* FragmentActivity */, Login.this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                signIn();

            }

        });


    }

    private void signIn() {

        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            // handleSignInResult(result);
            Toast.makeText(Login.this, "Login done", Toast.LENGTH_SHORT).show();
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            //Displaying name and email
            editor.putString("username", acct.getDisplayName().toString());
            editor.putString("email", acct.getEmail().toString());
            editor.commit();
            Intent intent=new Intent(Login.this,MenuList.class);
            startActivity(intent);


            Toast.makeText(Login.this, "" + acct.getDisplayName() + " " + acct.getEmail(), Toast.LENGTH_SHORT).show();
            Toast.makeText(Login.this, "" + acct.getAccount(), Toast.LENGTH_SHORT).show();


        }

    }

    @Override

    public  void   onBackPressed()
    {
        Toast.makeText(Login.this, "back button  pressed", Toast.LENGTH_SHORT).show();
      String sname=pref.getString("username","");
        if (sname!=null && sname.isEmpty()&&sname.equals("null")) {
            moveTaskToBack(true);
        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
