package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ЛР4: Использование ресурсов — верхняя часть из XML, нижняя из Java.
 */
public class ViewsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_demo);

        // Оформление нижней части из Java-кода (как в методичке)
        LinearLayout llBottom = findViewById(R.id.llBottom);
        TextView tvBottom = findViewById(R.id.tvBottom);
        Button btnBottom = findViewById(R.id.btnBottom);

        llBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.llBottomColor));
        tvBottom.setText(R.string.tvBottomText);
        btnBottom.setText(R.string.btnBottomText);

        // Переход на экран с альтернативными layout (портрет/ландшафт)
        Button btnOrientation = findViewById(R.id.btnOrientation);
        if (btnOrientation != null) {
            btnOrientation.setOnClickListener(v ->
                    startActivity(new Intent(this, OrientationActivity.class)));
        }
    }
}
