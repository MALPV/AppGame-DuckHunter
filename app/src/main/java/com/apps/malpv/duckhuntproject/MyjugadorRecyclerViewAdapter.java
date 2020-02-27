package com.apps.malpv.duckhuntproject;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.malpv.duckhuntproject.Models.Jugador;
import java.util.List;

public class MyjugadorRecyclerViewAdapter extends RecyclerView.Adapter<MyjugadorRecyclerViewAdapter.ViewHolder> {

    private final List<Jugador> mValues;

    public MyjugadorRecyclerViewAdapter(List<Jugador> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_jugador, parent, false);
        return new ViewHolder(view);
    }

    //lo segundo
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //variable para la posicion
        int pos = position + 1;

        holder.mItem = mValues.get(position);
        holder.tvPosicion.setText(pos + "º");
        holder.tvDucks.setText(String.valueOf(mValues.get(position).getDucks()));
        holder.tvNickName.setText(mValues.get(position).getNick());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //lo primero
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPosicion;
        public final TextView tvDucks;
        public final TextView tvNickName;
        public Jugador mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPosicion = view.findViewById(R.id.textViewPosicion);
            tvDucks = view.findViewById(R.id.textViewDucks);
            tvNickName = view.findViewById(R.id.textViewNickNameR);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvNickName.getText() + "'";
        }
    }
}
