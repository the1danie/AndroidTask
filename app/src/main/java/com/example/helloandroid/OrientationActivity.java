package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * ЛР4: Альтернативные ресурсы — один layout (myscreen),
 * портрет: res/layout/myscreen.xml (вертикально),
 * ландшафт: res/layout-land/myscreen.xml (горизонтально).
 */
public class OrientationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);
    }
}
