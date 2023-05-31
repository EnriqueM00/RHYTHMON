package AdapterS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.rhythmon.R;
import java.util.ArrayList;

import Clases_BBDD.Centro;
import Clases_BBDD.Puntuacion;

// CLASE AdapterPuntuación
public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.ViewHolderDatos> {

    private ArrayList<Puntuacion> listaPuntuacion;

    //Constructor AdapterPuntuaciones
    public AdapterPuntuaciones(ArrayList<Puntuacion> listaPuntuacion){ this.listaPuntuacion = listaPuntuacion; }

    // Crear contenedor de datos relacionandolo con el item_list
    public AdapterPuntuaciones.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_puntuacion,null,false);
        return new ViewHolderDatos(vista);
    }

    // Enlazar el contenedor con las distintas posiciones del ArrayList
    public void onBindViewHolder(AdapterPuntuaciones.ViewHolderDatos holder, int position){
        holder.asignarDatos(listaPuntuacion.get(position));
    }
    //Devolver el tamaño del ArrayList
    public int getItemCount(){
        return listaPuntuacion.size();
    }

    // Clase ViewHolderDatos
    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        //Declarar TextView
        private TextView tvPuntuacion;

        // Constructor ViewHolderDatos
        public ViewHolderDatos(View itemView){
            super(itemView);
            // Inicializar TextView
            tvPuntuacion = itemView.findViewById(R.id.tvPuntuacionItemList);
        }

        // Asignar los datos a los componentes
        public void asignarDatos(Puntuacion puntuacion){
            tvPuntuacion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tvPuntuacion.setText(Double.toString(puntuacion.getPuntuacion()));
        }
    }
}
