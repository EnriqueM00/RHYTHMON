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

    private int codAlumno;
    private ScrollView scrollBinario;
    private TableLayout tableBinario;
    private Button btnJugar;
    private SeekBar seekBarDictados, seekBarTempo;
    private TextView tvSeekBarDictados, tvSeekBarTempo;
    private final int minValue = 90, maxValue = 140, intervalo = 10;
    private final int numIntervalos = (maxValue - minValue) / intervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconfig);

        Bundle b = getIntent().getExtras();
        codAlumno = b.getInt("codAlumno");

        scrollBinario = findViewById(R.id.scroll_binario);
        scrollBinario.setVisibility(View.VISIBLE);
        scrollBinario.setBackgroundColor(Color.parseColor("#7CBD7F"));

        tableBinario = findViewById(R.id.table_bin);

        tvSeekBarDictados = findViewById(R.id.tv_seekBar_dictado);
        tvSeekBarDictados.setText("1");
        seekBarDictados = findViewById(R.id.seekBar_Dictado);

        seekBarDictados.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekBarDictados.setText(Integer.toString(progress));
            }
        });

        tvSeekBarTempo = findViewById(R.id.tv_seekBar_tempo);
        tvSeekBarTempo.setText("90");
        seekBarTempo = findViewById(R.id.seekBar_Tempo);
        seekBarTempo.setMax(numIntervalos);

        seekBarTempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int valor = minValue + (progress * intervalo);
                tvSeekBarTempo.setText(Integer.toString(valor));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        CrearImageViewFormulasRitmicas();

        btnJugar = findViewById(R.id.btnJugarConfig);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempo = Integer.parseInt(tvSeekBarTempo.getText().toString());
                int dictados = Integer.parseInt(tvSeekBarDictados.getText().toString());
                Intent i = new Intent(Preconfig.this, MiniJuego.class);
                Bundle b = new Bundle();
                b.putInt("tempo",tempo);
                b.putInt("dictados", dictados);
                b.putInt("codAlumno", codAlumno);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }

    private void CrearImageViewFormulasRitmicas() {
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

                rowBinActual.addView(imageViewsFormulas[i]); //Aqui añadimos el objeto creado IV (ImageView)
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

    public void volverPreConfig(View view){ finish(); }
}