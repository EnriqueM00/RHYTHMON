package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Preconfig extends AppCompatActivity {

    private ScrollView scrollBinario;
    private TableLayout tableBinario;
    private Button btnJugar;
    private SeekBar seekBar;
    private TextView tvSeekBarProgres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconfig);

        scrollBinario = findViewById(R.id.scroll_binario);
        scrollBinario.setVisibility(View.VISIBLE);
        scrollBinario.setBackgroundColor(Color.parseColor("#7CBD7F"));

        tableBinario = findViewById(R.id.table_bin);

        tvSeekBarProgres = findViewById(R.id.tv_seekBar);
        tvSeekBarProgres.setText("1");
        seekBar = findViewById(R.id.seekBar_Dictado);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* TODO Auto-generated method stub */ }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* TODO Auto-generated method stub */ }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekBarProgres.setText(Integer.toString(progress));
            }
        });

        CrearImageButtonsFormulasRitmicas();
    }

    private void CrearImageButtonsFormulasRitmicas() {
        ImageView imageViewsFormulas[] = new ImageView[FormulasRitmicas.values().length];
        int elementosFilaActualBin = 0;
        TableRow rowBinActual = new TableRow(this);
        rowBinActual.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //Mitad del width screen (cada scroll la mitad) dividido entre lo que ocupa cada botón formula con margenes
        int formulasPorFila =  (int) (float)(this.getResources().getDisplayMetrics().widthPixels) / ConvertirDP2PX(80);

        for (int i = 0; i < FormulasRitmicas.values().length; i++) {
            FormulasRitmicas f = FormulasRitmicas.values()[i]; //Formula

            //Dibujar imagenes en TableLayout
            imageViewsFormulas[i] = new ImageView(this);
            int idDrawable = getResources().getIdentifier(f.getNombreImgXML(), "drawable", this.getPackageName());
            imageViewsFormulas[i].setImageResource(idDrawable);
            imageViewsFormulas[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE); //FIT_CENTER
            imageViewsFormulas[i].setAdjustViewBounds(true);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ConvertirDP2PX(50), ConvertirDP2PX(50));
            int margin = ConvertirDP2PX(15);
            lp.setMargins(margin, margin, margin, margin);
            imageViewsFormulas[i].setLayoutParams(lp);

            imageViewsFormulas[i].setBackgroundColor(Color.parseColor("#eac316"));


            //Lo añadimos al scroll correspondiente

                rowBinActual.addView(imageViewsFormulas[i]); //Aqui añadimos el objeto creado (IV)
                elementosFilaActualBin++;
                if (elementosFilaActualBin == formulasPorFila) { //Añadimos la fila completa a la tabla y creamos otra
                    tableBinario.addView(rowBinActual);
                    rowBinActual = new TableRow(this);
                    rowBinActual.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    elementosFilaActualBin = 0;
                }
        }

        //Añadimos la última fila si algun elementosFilaActual es mayor que 0
        if (elementosFilaActualBin > 0)
            tableBinario.addView(rowBinActual);
    }

    public int ConvertirDP2PX(float dp) {
        Resources r = this.getResources();
        return (int) Math.floor(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void jugar(View view){
        Intent i = new Intent(this, MiniJuego.class);
        startActivity(i);
    }

    public void volverPreConfig(View view){ finish(); }
}