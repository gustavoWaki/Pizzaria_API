package com.example.pizzariadomario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class InicioAtendenteActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial_atendente);

        Button btnPedido, btnCadastro;
        btnPedido = (Button) findViewById(R.id.pedido);
        btnCadastro = (Button) findViewById(R.id.cadastro);
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CadastroPedidoActivity.class);
                startActivity(intent);
            }
        });
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CadastroDeClientesActivity.class);
                startActivity(intent);
            }
        });
    }
}