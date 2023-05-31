package AdapterS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rhythmon.R;

import java.util.ArrayList;

import Clases_BBDD.Alumno;

public class AdapterAlumnos extends RecyclerView.Adapter<AdapterAlumnos.ViewHolderDatos> {

    private ArrayList<Alumno> listaAlumnos;

    //Clase AdapterAlumnos

    //Constructor AdapterAlumnos
    public AdapterAlumnos(ArrayList<Alumno> listaAlumnos){
        this.listaAlumnos = listaAlumnos;
    }

    // Crear contenedor de datos relacionandolo con el item_list
    public AdapterAlumnos.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_alumno,null,false);
        return new ViewHolderDatos(vista);
    }

    // Enlazar el contenedor con las distintas posiciones del ArrayList
    public void onBindViewHolder(AdapterAlumnos.ViewHolderDatos holder, int position){
        holder.asignarDatos(listaAlumnos.get(position));
    }
    //Devolver el tamaño del ArrayList
    public int getItemCount(){
        return listaAlumnos.size();
    }

    // Clase ViewHolderDatos
    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        //Declarar TextView
        private TextView tvNombreAlumno;
        private TextView tvApellidosAlumno;
        private TextView tvIdUserAlumno;
        private TextView tvPasswordAlumno;
        private TextView tvAniosAlumno;

        // Constructor ViewHolderDatos
        public ViewHolderDatos(View itemView){
            super(itemView);
            // Inicializar TextView
            tvNombreAlumno = itemView.findViewById(R.id.tvNombreAlumno);
            tvApellidosAlumno = itemView.findViewById(R.id.tvApellidosAlumno);
            tvIdUserAlumno = itemView.findViewById(R.id.tvIDUserAlumno);
            tvPasswordAlumno = itemView.findViewById(R.id.tvPasswordAlumno);
            tvAniosAlumno = itemView.findViewById(R.id.tvAniosAlumno);
        }

        // Asignar los datos a los componentes
        public void asignarDatos(Alumno alumno){
            tvNombreAlumno.setText(alumno.getNombre());
            tvApellidosAlumno.setText(alumno.getApellidos());
            tvIdUserAlumno.setText(alumno.getId_user());
            tvPasswordAlumno.setText(alumno.getContraseña());
            tvAniosAlumno.setText(Integer.toString(alumno.getAnios()));
        }
    }
}