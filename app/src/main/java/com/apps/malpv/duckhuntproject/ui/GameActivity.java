package com.apps.malpv.duckhuntproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.malpv.duckhuntproject.R;
import com.apps.malpv.duckhuntproject.RankingActivity;
import com.apps.malpv.duckhuntproject.common.Constantes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvContadorDuck, tvNickname, tvTime;
    private ImageView ivDuck;
    int counter = 0;
    int anchoPantalla;
    int altoPantalla;
    Random aleatorio;
    boolean gameOver = false;
    String id, nick;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Instaciar la conexiòn a Firestore
        db = FirebaseFirestore.getInstance();

        //Inicialización de componentes
        initViewComponents();
        //Gestionamos eventos
        eventos();
        //Gestiona tamaño de pantalla
        initPantalla();
        //Mover pato al inicio del juego
        moveDuck();
        //Gestion cuenta regresiva
        initCuentaRegresiva();
    }

    //Gestiona la cuenta regresiva
    private void initCuentaRegresiva() {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished / 1000;
                tvTime.setText(segundosRestantes + "s");
            }

            public void onFinish() {
                tvTime.setText("0s!");
                gameOver = true;
                mostrarDialogoGameOver();
                savedResultFirestore();
            }
        }.start();

    }

    private void savedResultFirestore() {
        db.collection("jugadores")
                .document(id)
                .update("ducks", counter);
    }

    //Construye un dialogo con oopciones al terminar la cuenta regresiva
    private void mostrarDialogoGameOver() {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Haz conseguido cazar " + counter + " patos")
                .setTitle("GAME OVER");

        builder.setPositiveButton("Volver a jugar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                counter = 0;
                tvContadorDuck.setText("0");
                gameOver = false;
                initCuentaRegresiva();
                moveDuck();
            }
        });
        builder.setNegativeButton("Ver Ranking", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();

                Intent intent = new Intent(GameActivity.this, RankingActivity.class);
                startActivity(intent);

                //Añadido despues
                finish();
            }
        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        // 4. Mostrar dialogo
        dialog.show();
    }

    private void initPantalla() {
        //1. Obtener tamaño de pantalla del dispositivo que la ejecute
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        anchoPantalla = size.x;
        altoPantalla = size.y;

        //2. Inicializamos el objeto para generar numeros aleatorios
        aleatorio = new Random();

    }

    //Inicializador de componentes
    private void initViewComponents() {
        //Referenciamos
        tvContadorDuck = findViewById(R.id.textViewContadorDuck);
        tvNickname = findViewById(R.id.textViewNick);
        tvTime = findViewById(R.id.textViewTime);
        ivDuck = findViewById(R.id.imageViewDuck);

        //Cambiar tipo de fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(), "pixel.ttf");
        tvContadorDuck.setTypeface(typeface);
        tvNickname.setTypeface(typeface);
        tvTime.setTypeface(typeface);

        //Obtener extras: nick y setear en  textview correspondiente
        Bundle extras = getIntent().getExtras();
        nick = extras.getString(Constantes.EXTRA_NICK);
        id = extras.getString(Constantes.EXTRA_ID);
        tvNickname.setText(nick);
    }

    //Gestionador de eventos
    private void eventos() {
        ivDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameOver) {
                    counter++;
                    //Conversion de variable int a String
                    tvContadorDuck.setText(String.valueOf(counter));

                    //Cambiamos la imagen del pato
                    ivDuck.setImageResource(R.drawable.duck_clicked);

                    //Hace que el Runnable r se agregue a la cola de mensajes y se ejecute una vez transcurrido el tiempo especificado.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ivDuck.setImageResource(R.drawable.duck);
                            moveDuck();
                        }
                    }, 500);
                }
            }
        });
    }

    //Gestionador del movimiento del pato
    private void moveDuck() {
        int min = 0;
        int maximoX = anchoPantalla - ivDuck.getWidth();
        int maximoY = altoPantalla - ivDuck.getHeight();

        //Generamos numeros aleatorios || para coordenada X e Y dentro de la pantalla
        int randomX = aleatorio.nextInt(((maximoX - min) + 1) + min);
        int randomY = aleatorio.nextInt(((maximoY - min) + 1) + min);

        //Utilizamos los numeros aleaatorios para mover el pato
        ivDuck.setX(randomX);
        ivDuck.setY(randomY);
    }
}
