package com.apps.malpv.duckhuntproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.malpv.duckhuntproject.Models.Jugador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class jugadorRankingFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<Jugador> jugadorList;
    MyjugadorRecyclerViewAdapter adapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public jugadorRankingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static jugadorRankingFragment newInstance(int columnCount) {
        jugadorRankingFragment fragment = new jugadorRankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jugador_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            db.collection("jugadores")//se obtiene la coleccion
                    .orderBy("ducks", Query.Direction.DESCENDING)// se ordena la lista
                    .limit(10)// limitando a 10 jugadores
                    .get() //obtenemos los jugadores
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {// se verifica que es correcto el llamado
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            jugadorList = new ArrayList<>();

                            //Se recorre el documento por cada objeto Task
                            for (DocumentSnapshot document : task.getResult()){

                                //obtenemos el objeto taks y lo transformamos al objeto en uno de tipo jugador
                                Jugador jugadorItem = document.toObject(Jugador.class);
                                //lo añadimos a la lista
                                jugadorList.add(jugadorItem);

                                //Verificamos si obtenemos los objetos
                                Log.i(jugadorItem.getNick(), "Nick");

                                adapter = new MyjugadorRecyclerViewAdapter(jugadorList);
                                recyclerView.setAdapter(adapter);
                            }

                        }
                    });

        }
        return view;
    }

}
