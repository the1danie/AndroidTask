package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String REPLY_MESSAGE = "REPLY_MESSAGE";

    private TextView tvReceived;
    private EditText editTextReply;
    private Button btnSendBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvReceived = findViewById(R.id.tvReceived);
        editTextReply = findViewById(R.id.editTextReply);
        btnSendBack = findViewById(R.id.btnSendBack);

        // Получаем данные из MainActivity
        String receivedMessage = getIntent().getStringExtra(EXTRA_MESSAGE);
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            tvReceived.setText(getString(R.string.received_label) + " " + receivedMessage);
        } else {
            tvReceived.setText(getString(R.string.received_label) + " (пусто)");
        }

        // Отправка данных обратно в MainActivity
        btnSendBack.setOnClickListener(v -> {
            String reply = editTextReply.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(REPLY_MESSAGE, reply);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
