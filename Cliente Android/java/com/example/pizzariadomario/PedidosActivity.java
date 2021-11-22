package com.example.pizzariadomario;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PedidosActivity extends AppCompatActivity {
    String id = "";
    JSONArray listaPedidosPizza;
    LinearLayout lista;
    Button btnRefrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_cozinheiro);
        lista = findViewById(R.id.caixaDaLista);

        refresh(this);
        btnRefrescar = findViewById(R.id.btnRefrescar);

        btnRefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lista.removeAllViews();

                lista.addView(btnRefrescar);

                RequestQueue queue = Volley.newRequestQueue(PedidosActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedido/"+id;


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PedidosActivity.this, error.toString()+"aaaaa", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

                refresh(view.getContext());

            }
        });
    }

    protected void refresh(Context context){

        RequestQueue queue = Volley.newRequestQueue(PedidosActivity.this);
        String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedido";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                id = new Integer(response.optInt("id")).toString();

                RequestQueue queue1 = Volley.newRequestQueue(PedidosActivity.this);
                String url1 ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedidopizza/"+id;


                JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response1) {
                        System.out.println(response1.toString());
                        listaPedidosPizza = response1;

                        for (int i = 0; i < listaPedidosPizza.length(); i++){
                            try {
                                lista.addView(devolverBox(listaPedidosPizza.getJSONObject(i), context));
                            } catch (JSONException e) {e.printStackTrace();}
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PedidosActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue1.add(request1);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PedidosActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }

    protected View devolverBox(JSONObject pedido, Context contexto){
        LinearLayout box = new LinearLayout(contexto);
        box.setOrientation(LinearLayout.HORIZONTAL);
        box.setMinimumWidth(850);
        box.setMinimumHeight(150);
        box.setBackgroundColor(Color.parseColor("#F7DCDB"));
        box.setPadding(5,5,5,5);
        box.setId(pedido.optInt("id"));
        LinearLayout box2 = new LinearLayout(contexto);
        box2.setOrientation(LinearLayout.VERTICAL);
        box2.setMinimumWidth(800);
        box2.setMinimumHeight(150);
        box2.setBackgroundColor(Color.parseColor("#F7DCDB"));
        box2.setPadding(7,7,7,7);



        TextView texto = new TextView(contexto);
        texto.setWidth(200);
        texto.setHeight(110);
        texto.setTextSize(30);
        texto.setPadding(3,3,3,3);
        texto.setText(pedido.optString("primeiroSabor"));

        TextView texto2 = new TextView(contexto);
        texto2.setWidth(200);
        texto2.setHeight(110);
        texto2.setTextSize(30);
        texto2.setPadding(3,3,3,3);
        texto2.setText(pedido.optString("segundoSabor"));

        box2.addView(texto);
        box2.addView(texto2);

        Button encerrado = new Button(contexto);
        encerrado.setWidth(200);
        encerrado.setHeight(150);
        encerrado.setBackground(getDrawable(R.drawable.btn_atendente));
        encerrado.setText("Pronto");
        encerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista.removeView(box);

                RequestQueue queue = Volley.newRequestQueue(PedidosActivity.this);
                String url ="https://9449-2804-14c-48b-40b8-c8b7-e017-8db2-3f8a.ngrok.io/api/pedidopizza/"+box.getId();


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);

            }
        });
        texto.setBackgroundColor(Color.parseColor("#fefdfe"));
        texto2.setBackgroundColor(Color.parseColor("#fefdfe"));

        box.addView(box2);
        box.addView(encerrado);

        return box;
    }
}