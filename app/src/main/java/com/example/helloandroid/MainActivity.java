package com.example.helloandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    TextView mainTextView;
    EditText mainEditText;
    Button mainButton;
    Button btnGoToSecond;
    Button ok_btn, cnc_btn, delete_btn;
    ListView mainListView;

    private static final int REQUEST_SECOND_ACTIVITY = 1;

    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> mNameList = new ArrayList<>();
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим элементы
        mainTextView = findViewById(R.id.main_textview);
        mainEditText = findViewById(R.id.main_edittext);
        mainButton = findViewById(R.id.main_button);
        btnGoToSecond = findViewById(R.id.btnGoToSecond);
        Button btnLr4 = findViewById(R.id.btnLr4);
        mainListView = findViewById(R.id.main_listview);
        ok_btn = findViewById(R.id.ok_btn);
        cnc_btn = findViewById(R.id.cnc_btn);
        delete_btn = findViewById(R.id.delete_btn);

        // Назначаем обработчики
        mainButton.setOnClickListener(this);
        btnGoToSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            String message = mainEditText.getText().toString();
            intent.putExtra(SecondActivity.EXTRA_MESSAGE, message);
            startActivityForResult(intent, REQUEST_SECOND_ACTIVITY);
        });
        btnLr4.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewsDemoActivity.class)));
        mainListView.setOnItemClickListener(this);

        // Создаем общий обработчик для кнопок OK и Cancel
        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // По id определяем кнопку, вызвавшую этот обработчик
                int id = v.getId();
                if (id == R.id.ok_btn) {
                    // Кнопка OK
                    mainTextView.setText(getString(R.string.ok_message));
                    Toast.makeText(getApplicationContext(), 
                            getString(R.string.ok_message), 
                            Toast.LENGTH_LONG).show();
                } else if (id == R.id.cnc_btn) {
                    // Кнопка Cancel
                    mainTextView.setText(getString(R.string.cancel_message));
                    Toast.makeText(getApplicationContext(), 
                            getString(R.string.cancel_message), 
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        // Назначаем обработчик для кнопок OK и Cancel
        ok_btn.setOnClickListener(oclBtn);
        cnc_btn.setOnClickListener(oclBtn);
        delete_btn.setOnClickListener(this);

        // Настраиваем ListView
        mArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mNameList
        );
        mainListView.setAdapter(mArrayAdapter);

        // Устанавливаем текст из кода при запуске
        mainTextView.setText("Set in Java!");
    }

    // Нажатие на кнопку
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete_btn) {
            // Удаление выделенного элемента
            if (selectedPosition >= 0 && selectedPosition < mNameList.size()) {
                mNameList.remove(selectedPosition);
                mArrayAdapter.notifyDataSetChanged();
                selectedPosition = -1;
                mainTextView.setText(getString(R.string.greetings));
                Toast.makeText(getApplicationContext(), 
                        "Элемент удален", 
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), 
                        "Выберите элемент для удаления", 
                        Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // Обработка кнопки добавления в список
        if (v.getId() == R.id.main_button) {
            String text = mainEditText.getText().toString().trim();

            if (!text.isEmpty()) {
                // Удаляем дубликаты если элемент уже существует
                while (mNameList.contains(text)) {
                    mNameList.remove(text);
                }
                
                // Добавляем новый элемент
                mNameList.add(text);
                
                // Сортируем список (лексикографически)
                Collections.sort(mNameList);
                
                // Обновляем адаптер
                mArrayAdapter.notifyDataSetChanged();

                mainTextView.setText(
                        text + " is learning Android development!"
                );

                mainEditText.setText("");
            }
        }
    }

    // Нажатие на элемент списка
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedPosition = position;
        Log.d("omg android", position + ": " + mNameList.get(position));
        mainTextView.setText(
                mNameList.get(position) + " is learning Android development!"
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SECOND_ACTIVITY && resultCode == RESULT_OK && data != null) {
            String reply = data.getStringExtra(SecondActivity.REPLY_MESSAGE);
            if (reply != null) {
                mainTextView.setText("Ответ: " + reply);
            }
        }
    }

    // Жизненный цикл активити (ЛР2)
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "MainActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "MainActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "MainActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "MainActivity: onDestroy");
    }
}
