package com.apps.malpv.duckhuntproject;

import android.content.Intent;
import android.os.Bundle;

import com.apps.malpv.duckhuntproject.common.Constantes;
import com.apps.malpv.duckhuntproject.ui.GameActivity;
import com.apps.malpv.duckhuntproject.ui.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                /*
                Snackbar.make(view, "Falta configurar acción", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Para cargar un fragmento
        //Se obtinene el gestos de fragmentos
        getSupportFragmentManager()
                .beginTransaction()// se indica una trasacion, que sobre un contenedor se cargara un fragmento
                .add(R.id.contenedor, new jugadorRankingFragment()) //añadimos sobre fragmentContainer cargamos una instancia de jugadorRankingFragment
                .commit();//commit para que se lleve acabo
    }

}
