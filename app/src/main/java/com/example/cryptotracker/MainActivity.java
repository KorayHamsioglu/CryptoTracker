package com.example.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   private EditText editText;
   private RecyclerView RV;
   private Adapter adapter;
    private ArrayList<String> isimler;
    private  ArrayList<Double> fiyatlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.searchTxt);
        RV=findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setHasFixedSize(true);
        isimler=new ArrayList<String>();
        fiyatlar=new ArrayList<Double>();

       adapter=new Adapter(this,isimler,fiyatlar);
       RV.setAdapter(adapter);
        getData();

       editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
                 filter(editable.toString());
           }
       });


    }

    private void filter(String currencyName){
        ArrayList<String> filteredList=new ArrayList<>();
        for(String item: isimler) {
            if (item.toLowerCase().contains(currencyName.toLowerCase())) {
                filteredList.add(item);
            }
        }

            if ((filteredList.isEmpty())){
                Toast.makeText(this,"No coin found",Toast.LENGTH_SHORT).show();

            }
            else{
              adapter.filterList(filteredList);
        }


    }
    private void getData(){
        String url="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray data=response.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject dataObj=data.getJSONObject(i);
                        String name=dataObj.getString("name");
                        isimler.add(name);
                        JSONObject quote=dataObj.getJSONObject("quote");
                        JSONObject USD=quote.getJSONObject("USD");
                        double price=USD.getDouble("price");
                        fiyatlar.add(price);

                    }
                 adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Fail to Get",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","baacd20c-77f7-403d-869e-7f2d7461cf5a");
                return headers;

            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}