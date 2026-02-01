package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    private TextView sharedTextTextView;
    private TextView sharedTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        sharedTextTextView = findViewById(R.id.shared_text_view);
        sharedTitleTextView = findViewById(R.id.shared_title_view);

        // Обрабатываем интент
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                // Обработка текста
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                String sharedSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT);

                if (sharedText != null && !sharedText.isEmpty()) {
                    sharedTextTextView.setText(sharedText);
                    if (sharedSubject != null) {
                        sharedTitleTextView.setText(getString(R.string.shared_subject) + ": " + sharedSubject);
                    } else {
                        sharedTitleTextView.setText(getString(R.string.shared_text_received));
                    }

                    String preview = sharedText.length() > 50 
                            ? sharedText.substring(0, 50) + "..." 
                            : sharedText;
                    Toast.makeText(this, 
                            getString(R.string.text_received) + ": " + preview, 
                            Toast.LENGTH_LONG).show();
                } else {
                    sharedTextTextView.setText(getString(R.string.no_text_received));
                    sharedTitleTextView.setText(getString(R.string.shared_text_received));
                    Toast.makeText(this, getString(R.string.no_text_received), Toast.LENGTH_SHORT).show();
                }
            } else if (type.startsWith("image/")) {
                // Обработка изображения
                Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    sharedTextTextView.setText(getString(R.string.image_received) + ": " + imageUri.toString());
                    sharedTitleTextView.setText(getString(R.string.shared_image_received));
                    Toast.makeText(this, getString(R.string.image_received), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            sharedTextTextView.setText(getString(R.string.no_data_received));
            sharedTitleTextView.setText(getString(R.string.app_name));
        }
    }
}
