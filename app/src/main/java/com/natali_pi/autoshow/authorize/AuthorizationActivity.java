package com.natali_pi.autoshow.authorize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.natali_pi.autoshow.R;


public class AuthorizationActivity extends AppCompatActivity {
AuthorizationPresenter presenter = new AuthorizationPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText password = (EditText) findViewById(R.id.password);
        Button enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login(login.getText().toString(), password.getText().toString());
            }
        });
    }
}
