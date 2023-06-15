package com.example.amstapiapplab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class enviar_datos extends AppCompatActivity {
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private Button b1;
    private RequestQueue mQueue;
    private String token = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_datos);

        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");

        e1 = (EditText) findViewById(R.id.temp_ing);
        e2 = (EditText) findViewById(R.id.humedad_ing);
        e3 = (EditText) findViewById(R.id.peso_ing);

        b1 = (Button) findViewById(R.id.btn_agregar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar_informacion() == 1){
                    envio_datos();
                }
            }
        });
    }

    public void envio_datos(){
        String temperatura = e1.getText().toString();
        String humedad = e2.getText().toString();
        String peso = e3.getText().toString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("temperatura", temperatura);
        params.put("humedad", humedad);
        params.put("peso", peso);
        JSONObject parametros = new JSONObject(params);
        String url = "https://amst-lab-api.herokuapp.com/api/sensores/";
        System.out.println(parametros);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT "+token);
                System.out.println(token);
                return params;
            }
        };
        mQueue.add(request);
    }

    public int validar_informacion(){
        if(e1.getText().toString().equals("") && e2.getText().toString().equals("") && e3.getText().toString().equals("")){
            return 0;
        }
        else{
            return 1;
        }
    }
}