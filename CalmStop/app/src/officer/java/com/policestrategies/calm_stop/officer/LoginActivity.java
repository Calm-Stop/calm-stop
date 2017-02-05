package com.policestrategies.calm_stop.officer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.policestrategies.calm_stop.R;

/**
 * Allows the officer to log in or create an account.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.button:
                Intent i = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(i);
                break;

        }
    }


}
