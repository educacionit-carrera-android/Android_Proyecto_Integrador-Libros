package com.educacionit.libros;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibrosAdapter extends
        RecyclerView.Adapter<LibrosAdapter.LibrosViewHolder> {

    private List<Libro> libros = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public LibrosAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLibro =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_libro, parent, false);
        return new LibrosViewHolder(itemLibro);
    }

    @Override
    public void onBindViewHolder(@NonNull LibrosViewHolder holder, final int position) {
        holder.txtNombre.setText(libros.get(position).getNombre());
        holder.txtAutor.setText(libros.get(position).getAutor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(libros.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    class LibrosViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtAutor;

        LibrosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtAutor = itemView.findViewById(R.id.txtAutor);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Libro libro);
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        notifyDataSetChanged();
    }

    public List<Libro> getLibros() {
        return this.libros;
    }
}
