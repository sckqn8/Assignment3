package sckqn8.locateandpin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void optionsClass(View v) {
        Intent redirect = new Intent(Registration.this, ListOptions.class);
        startActivity(redirect);
    }
}
