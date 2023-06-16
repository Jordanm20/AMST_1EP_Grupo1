package com.example.amst1epgrupo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TextView totalResultsTextView;
    private ListView heroesListView;
    private List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        totalResultsTextView = findViewById(R.id.totalResultsTextView);
        heroesListView = findViewById(R.id.heroesListView);

        String jsonHeroList = getIntent().getStringExtra("heroList");
        heroList = new Gson().fromJson(jsonHeroList, new TypeToken<List<Hero>>() {}.getType());

        totalResultsTextView.setText("Total de resultados: " + heroList.size());

        List<String> heroNames = new ArrayList<>();
        for (Hero hero : heroList) {
            heroNames.add(hero.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, heroNames);
        heroesListView.setAdapter(adapter);

        heroesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el héroe seleccionado
                Hero selectedHero = heroList.get(position);

                // Filtrar la lista de héroes para obtener solo los héroes con el mismo nombre
                List<Hero> filteredHeroList = new ArrayList<>();
                for (Hero hero : heroList) {
                    if (hero.getName().equals(selectedHero.getName())) {
                        filteredHeroList.add(hero);
                    }
                }

                // Crear un nuevo adaptador y lista de nombres de héroes a partir de la lista filtrada
                List<String> filteredHeroNames = new ArrayList<>();
                for (Hero hero : filteredHeroList) {
                    filteredHeroNames.add(hero.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ResultsActivity.this, android.R.layout.simple_list_item_1, filteredHeroNames);

                // Establecer el nuevo adaptador en el ListView
                heroesListView.setAdapter(adapter);
            }
        });
    }
}
