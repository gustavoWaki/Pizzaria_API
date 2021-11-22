package com.example.pizzariadomario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroDeClientesActivity extends AppCompatActivity {

    EditText edtNome,edtEndereco,edtTelefone;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_de_clientes);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTelefone = (EditText) findViewById(R.id.edtTele);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNome.getText().length()==0||edtTelefone.getText().length()==0||edtEndereco.getText().length()==0){
                    Toast.makeText(CadastroDeClientesActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                RequestQueue queue = Volley.newRequestQueue(CadastroDeClientesActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/cliente/criar";

                JSONObject obj = new JSONObject();
                try {
                    obj.put("telefone", edtTelefone.getText());
                    obj.put("nome", edtNome.getText());
                    obj.put("endereco", edtEndereco.getText());
                }catch (JSONException e) {}


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        Toast.makeText(CadastroDeClientesActivity.this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroDeClientesActivity.this, "ERRO NA INCLUSÃO", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

                Intent intent = new Intent(view.getContext(), InicioAtendenteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}