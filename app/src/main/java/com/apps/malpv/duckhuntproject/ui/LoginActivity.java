package com.apps.malpv.duckhuntproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.malpv.duckhuntproject.Models.Jugador;
import com.apps.malpv.duckhuntproject.R;
import com.apps.malpv.duckhuntproject.common.Constantes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText etNick;
    private Button btnIniciar;
    String nick;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instaciar la conexiòn a Firestore
        db = FirebaseFirestore.getInstance();

        etNick = findViewById(R.id.editTextNick);
        btnIniciar = findViewById(R.id.buttonIniciar);

        //Cambiar tipo de fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(), "pixel.ttf");
        etNick.setTypeface(typeface);
        btnIniciar.setTypeface(typeface);

        //Evento click
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick = etNick.getText().toString();

                if (nick.isEmpty()){
                    etNick.setError("Debe ingresar un nick");
                }else {
                    addNickAndStart();
                }
            }
        });
    }

    private void addNickAndStart() {
        //Para hacer la consulta debemos llamar al objeto db
        //Antes verificamos si el nick ya esta en la db, si no existe se prosigue a añadir
        db.collection("jugadores").whereEqualTo("nick", nick)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() > 0){
                            etNick.setError("El nick no esta disponible");
                        } else {
                            addNickFirestore();
                        }
                    }
                });
    }

    private void addNickFirestore() {
        Jugador nuevoJugador = new Jugador(nick, 0);

        db.collection("jugadores")
                .add(nuevoJugador)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        etNick.setText("");
                        Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                        intent.putExtra(Constantes.EXTRA_NICK, nick);
                        intent.putExtra(Constantes.EXTRA_ID, documentReference.getId());
                        startActivity(intent);
                    }
                });
    }
}
