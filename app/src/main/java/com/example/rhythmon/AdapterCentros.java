package com.example.rhythmon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class AdapterCentros extends RecyclerView.Adapter<AdapterCentros.ViewHolderDatos> {

    private ArrayList<Centro> listaCentros;

    //Clase AdapterCentros

        //Constructor AdapterCentros
        public AdapterCentros(ArrayList<Centro> listaCentros){
            this.listaCentros = listaCentros;
        }

        // Crear contenedor de datos relacionandolo con el item_list
        public AdapterCentros.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType){
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_centro,null,false);
            return new ViewHolderDatos(vista);
        }

        // Enlazar el contenedor con las distintas posiciones del ArrayList
        public void onBindViewHolder(AdapterCentros.ViewHolderDatos holder, int position){
            holder.asignarDatos(listaCentros.get(position));
        }
        //Devolver el tamaño del ArrayList
        public int getItemCount(){
            return listaCentros.size();
        }

        // Clase ViewHolderDatos
        public class ViewHolderDatos extends RecyclerView.ViewHolder{
            //Declarar TextView
            private TextView tvCodCentro;
            private TextView tvNombreCentro;
            private TextView tvIdUserCentro;
            private TextView tvPasswordCentro;
            private TextView tvCiudadCentro;
            private TextView tvNumAlumnos;

            // Constructor ViewHolderDatos
            public ViewHolderDatos(View itemView){
                super(itemView);
                // Inicializar TextView
                tvCodCentro = itemView.findViewById(R.id.tvCodCentro);
                tvNombreCentro = itemView.findViewById(R.id.tvNombreCentro);
                tvIdUserCentro = itemView.findViewById(R.id.tvIDUserCentro);
                tvPasswordCentro = itemView.findViewById(R.id.tvPasswordCentro);
                tvCiudadCentro = itemView.findViewById(R.id.tvCiudadCentro);
                tvNumAlumnos = itemView.findViewById(R.id.tvNumAlumnos);
            }

            // Asignar los datos a los componentes
            public void asignarDatos(Centro centro){
                tvCodCentro.setText(Integer.toString(centro.getCodCentro()));
                tvNombreCentro.setText(centro.getNombre());
                tvIdUserCentro.setText(centro.getId_user());
                tvPasswordCentro.setText(centro.getContraseña());
                tvCiudadCentro.setText(centro.getCiudad());
                tvNumAlumnos.setText(Integer.toString(centro.getNumAlumnos()));
            }
        }
}