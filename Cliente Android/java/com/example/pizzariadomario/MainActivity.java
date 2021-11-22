package com.example.pizzariadomario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edtNome, edtSenha;
        edtNome = (EditText) findViewById(R.id.login);
        edtSenha = (EditText) findViewById(R.id.senha);
        Button enviar = (Button) findViewById(R.id.btnEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNome.getText().length()==0 ||edtSenha.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/usuario";

                JSONObject obj = new JSONObject();
                try {
                    obj.put("nome", edtNome.getText());
                    obj.put("senha", edtSenha.getText());
                }catch (JSONException e) {}


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            System.out.println(response.getString("role"));
                        } catch (JSONException e) {}

                        try {
                            novaJanela(response.getString("role"));
                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Senha ou nome inválidos", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);


            }
        });
    }
    private void novaJanela(String role){
        System.out.println("ENTROU");
        if(role.equals("gerente")){
            Intent intent = new Intent(this, InicioGerenteActivity.class);
            startActivity(intent);
        }
        if(role.equals("atendente")){
            Intent intent = new Intent(this, InicioAtendenteActivity.class);
            startActivity(intent);
        }
        if(role.equals("cozinheiro")){
            Intent intent = new Intent(this, PedidosActivity.class);
            startActivity(intent);
        }



    }
}