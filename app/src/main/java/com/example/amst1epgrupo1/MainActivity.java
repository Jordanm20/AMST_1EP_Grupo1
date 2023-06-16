package com.example.amst1epgrupo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;

    private List<Hero> heroList;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        heroList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchEditText.getText().toString();
                if (!searchTerm.isEmpty()) {
                    buscarHeroe(searchTerm);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un término de búsqueda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buscarHeroe(String searchTerm) {
        String apiUrl = "https://www.superheroapi.com/api.php/217054987823935/search/" + searchTerm;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            heroList.clear();
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject heroObject = resultsArray.getJSONObject(i);
                                String name = heroObject.getString("name");
                                Hero hero = new Hero(name, "", "", 0, 0, 0, 0, 0, 0);
                                heroList.add(hero);
                            }
                            openResultsActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al analizar la respuesta del API", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error al realizar la solicitud al API", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(request);
    }

    private void openResultsActivity() {
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("heroList", new Gson().toJson(heroList));
        startActivity(intent);
    }
}
