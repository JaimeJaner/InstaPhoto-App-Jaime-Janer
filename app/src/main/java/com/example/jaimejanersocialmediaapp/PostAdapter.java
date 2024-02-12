package com.example.jaimejanersocialmediaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*A la hora de crear esta nueva clase, que es un adaptador para el RecyclerReview,
* hay que añadir los indicadores de que es una implementación. Tal como se ve abajo
* en la creación de la clase. Además, el adaptador debe estar conformado por los View Holders*/
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    Context context;
    ArrayList<Event> events = new ArrayList<>();

    public PostAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.post_holder, parent, false);
        return new PostHolder(mView);
    }

    /**/
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, int position) {

        holder.edtTitulo.setText(events.get(position).getTitulo());
        holder.edtDescripcion.setText(events.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        private EditText edtTitulo, edtDescripcion;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            edtTitulo = itemView.findViewById(R.id.txtTitulo_rec);
            edtDescripcion = itemView.findViewById(R.id.txtDescripcion_rec);
        }
    }
}
