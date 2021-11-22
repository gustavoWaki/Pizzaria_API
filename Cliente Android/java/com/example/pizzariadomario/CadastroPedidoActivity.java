package com.example.pizzariadomario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CadastroPedidoActivity extends AppCompatActivity {

    Spinner sabor1, sabor2;
    Button adicionar, fim, pesquisar;
    ArrayList<String> saborList = new ArrayList<>();
    ArrayAdapter<String> saborAdapter;
    ArrayList<Integer> idPizzas = new ArrayList<>();
    TextView tvPreco,tvEndereco;
    EditText edtNome;

    double preco = 0;
    int idCliente, idPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_pedido);
        sabor1 = (Spinner) findViewById(R.id.sabor1);
        sabor2 = (Spinner) findViewById(R.id.sabor2);
        adicionar = (Button) findViewById(R.id.btnAdd);
        fim = (Button) findViewById(R.id.btnFim);
        tvPreco = (TextView) findViewById(R.id.tvPreco);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        edtNome = (EditText) findViewById(R.id.edtNome);
        pesquisar = (Button) findViewById(R.id.btnPesq);


        adicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(CadastroPedidoActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/sabor";
                int idSabor1 = 0, idSabor2;
                JSONObject pizza = new JSONObject();

                String metade1 = sabor1.getSelectedItem().toString();
                String metade2 = sabor2.getSelectedItem().toString();
                JSONObject sabor1 = new JSONObject();
                try {
                    sabor1.put("nome",metade1);
                } catch (JSONException e) {}

                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, url, sabor1, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pizza.put("primeiroSabor",response.getInt("id"));
                            preco += response.getDouble("preco");
                            System.out.println(pizza);
                        } catch (JSONException e) {}

                        JSONObject sabor2 = new JSONObject();
                        try {
                            sabor2.put("nome",metade2);
                        } catch (JSONException e) {}

                        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, url, sabor2, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    pizza.put("segundoSabor",response.getInt("id"));
                                    preco += response.getDouble("preco");
                                    System.out.println(pizza);
                                } catch (JSONException e) {
                                    System.out.println("deu ERRO NO 2");
                                }
                                String url2 ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pizza";

                                JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.POST, url2, pizza, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        System.out.println(response.toString());
                                        try {
                                            idPizzas.add(response.getInt("id"));
                                            tvPreco.setText("R$ "+Double.toString(preco));
                                        } catch (JSONException e) {}
                                    }
                                }, new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                queue.add(request3);
                            }
                        }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(request2);
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request1);


            }
        });

        fim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idPizzas.size() == 0){
                    Toast.makeText(CadastroPedidoActivity.this, "NENHUMA PIZZA NO PEDIDO", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(idCliente <= 0){
                    Toast.makeText(CadastroPedidoActivity.this, "NENHUM CLIENTE SELECIONADO", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(CadastroPedidoActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedido";

                JSONObject obj = new JSONObject();
                try {
                    obj.put("idCliente", idCliente);
                }catch (JSONException e) {}

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        idPedido = response.optInt("id");

                        for(int i=0; i<idPizzas.size();i++){
                            RequestQueue queue = Volley.newRequestQueue(CadastroPedidoActivity.this);
                            String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedidopizza";

                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("idPedido", idPedido);
                                obj.put("idPizza", idPizzas.get(i));
                            }catch (JSONException e) {}


                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println(response.toString());
                                }
                            }, new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            queue.add(request);
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

                Intent intent = new Intent(view.getContext(), InicioAtendenteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNome.getText().length()==0)
                    Toast.makeText(CadastroPedidoActivity.this, "PREENCHA O CAMPO ANTES", Toast.LENGTH_SHORT).show();
                else{

                    RequestQueue queue = Volley.newRequestQueue(CadastroPedidoActivity.this);
                    String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/cliente";

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("nome", edtNome.getText());
                    }catch (JSONException e) {}


                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            tvEndereco.setText(response.optString("endereco"));
                            idCliente = response.optInt("id");
                            System.out.println(idCliente);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CadastroPedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(request);

                }
            }
        });



        RequestQueue queue = Volley.newRequestQueue(CadastroPedidoActivity.this);
        String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/sabor";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());
                for(int i=0; i<response.length();i++){
                    JSONObject jsonObject = response.optJSONObject(i);
                    String countryName = jsonObject.optString("nome");
                    saborList.add(countryName);
                    saborAdapter = new ArrayAdapter<>(CadastroPedidoActivity.this,
                            android.R.layout.simple_spinner_item, saborList);
                    saborAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sabor1.setAdapter(saborAdapter);
                    saborAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sabor2.setAdapter(saborAdapter);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroPedidoActivity.this, "Senha ou nome inválidos", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }


}
