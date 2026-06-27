package com.norbel.clasificadorgatosperros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvResultado;
    private Interpreter interpreter;


    private final String MODELO = "modelo_gatos_perros.tflite";
    private final String[] clases = {
            "Avión", "Automóvil", "Pájaro", "Gato", "Ciervo",
            "Perro", "Rana", "Caballo", "Barco", "Camión"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        tvResultado = findViewById(R.id.tvResultado);
        Button btn = findViewById(R.id.btnSeleccionar);

        try {
            interpreter = new Interpreter(loadModelFile());
            tvResultado.setText("Modelo cargado. Selecciona una imagen.");
        } catch (Exception e) {
            tvResultado.setText("Error al cargar modelo: " + e.getMessage());
            e.printStackTrace();
        }

        btn.setOnClickListener(v -> abrirGaleria());
    }

    private void abrirGaleria() {
        launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri == null) return;

                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(
                                    getContentResolver().openInputStream(uri)
                            );

                            imageView.setImageBitmap(bitmap);
                            tvResultado.setText("Clasificando...");
                            clasificar(bitmap);

                        } catch (Exception e) {
                            tvResultado.setText("Error al clasificar: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
            );

    private void clasificar(Bitmap bitmap) {
        if (interpreter == null) {
            tvResultado.setText("Error: el modelo no se cargó.");
            return;
        }

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 160, 160, true);

        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 160 * 160 * 3);

        int[] pixels = new int[160 * 160];

        resized.getPixels(pixels, 0, 160, 0, 0, 160, 160);
        for (int pixel : pixels) {
            buffer.putFloat(((pixel >> 16) & 0xFF) / 255f);
            buffer.putFloat(((pixel >> 8) & 0xFF) / 255f);
            buffer.putFloat((pixel & 0xFF) / 255f);
        }

        float[][] output = new float[1][1];
        interpreter.run(buffer, output);

        int maxIndex = 0;
        float prob = output[0][0];

        String clase = prob > 0.5 ? "Perro" : "Gato";

        tvResultado.setText(
                "Clase: " + clase +
                        "\nConfianza: " + String.format("%.2f", prob * 100) + "%"
        );
    }

    private ByteBuffer loadModelFile() throws Exception {
        FileInputStream inputStream =
                new FileInputStream(getAssets().openFd(MODELO).getFileDescriptor());

        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = getAssets().openFd(MODELO).getStartOffset();
        long declaredLength = getAssets().openFd(MODELO).getDeclaredLength();

        return fileChannel.map(
                FileChannel.MapMode.READ_ONLY,
                startOffset,
                declaredLength
        );
    }
}