package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GuessNumberActivity extends AppCompatActivity {

    private TextView tvInfo;
    private EditText etInput;
    private Button bControl;
    private Button bExit;

    private int secretNumber;
    private boolean gameFinished = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        // Находим элементы интерфейса
        tvInfo = findViewById(R.id.tvInfo);
        etInput = findViewById(R.id.etInput);
        bControl = findViewById(R.id.bControl);
        bExit = findViewById(R.id.bExit);

        // Загадываем число от 0 до 100
        startNewGame();

        // Обработчик нажатия на кнопку проверки
        bControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickControl();
            }
        });

        // Обработчик нажатия на кнопку выхода
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startNewGame() {
        secretNumber = random.nextInt(101); // Число от 0 до 100
        gameFinished = false;
        tvInfo.setText(getString(R.string.try_to_guess));
        etInput.setText("");
        bControl.setText(getString(R.string.input_value));
    }

    public void onClickControl() {
        // Проверка на пустой ввод
        String inputText = etInput.getText().toString().trim();
        if (inputText.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_input_error), Toast.LENGTH_SHORT).show();
            tvInfo.setText(getString(R.string.empty_input_message));
            return;
        }

        int userNumber;
        try {
            userNumber = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_number_error), Toast.LENGTH_SHORT).show();
            tvInfo.setText(getString(R.string.invalid_number_message));
            return;
        }

        // Проверка диапазона (0-100)
        if (userNumber < 0 || userNumber > 100) {
            Toast.makeText(this, getString(R.string.range_error), Toast.LENGTH_SHORT).show();
            tvInfo.setText(getString(R.string.range_message));
            return;
        }

        // Если игра закончена, начинаем новую
        if (gameFinished) {
            startNewGame();
            return;
        }

        // Проверка числа
        if (userNumber == secretNumber) {
            // Победа!
            tvInfo.setText(getString(R.string.victory) + " " + secretNumber + "!");
            bControl.setText(getString(R.string.play_again));
            gameFinished = true;
            Toast.makeText(this, getString(R.string.victory_toast), Toast.LENGTH_LONG).show();
        } else if (userNumber > secretNumber) {
            // Загаданное число меньше
            tvInfo.setText(getString(R.string.ahead));
        } else {
            // Загаданное число больше
            tvInfo.setText(getString(R.string.behind));
        }

        // Очищаем поле ввода
        etInput.setText("");
    }
}
