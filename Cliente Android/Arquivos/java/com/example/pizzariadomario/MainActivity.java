package com.example.pizzariadomario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enviar = (Button) findViewById(R.id.btnEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                novaJanela();
            }
        });
    }
    private void novaJanela(){
        Intent intent = new Intent(this, CadastroDeClientesActivity.class);
        startActivity(intent);
    }
}