package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3]; // Массив кнопок для игрового поля
    private boolean player1Turn = true; // Переменная для отслеживания хода игрока
    private int roundCount = 0; // Счетчик раундов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация кнопок
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i, j));
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private class ButtonClickListener implements View.OnClickListener {
        private int row;
        private int col;

        ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (!((Button) v).getText().toString().equals("")) {
                return; // Если кнопка уже нажата, ничего не делаем
            }

            if (player1Turn) {
                ((Button) v).setText("X"); // Установка символа X для игрока 1
            } else {
                ((Button) v).setText("O"); // Установка символа O для игрока 2
            }
            roundCount++; // Увеличиваем счетчик раундов
            if (checkForWin()) {
                if (player1Turn) {
                    showToast("Игрок 1 выиграл!"); // Сообщение о победе игрока 1
                } else {
                    showToast("Игрок 2 выиграл!"); // Сообщение о победе игрока 2
                }
                resetGame(); // Сброс игры
            } else if (roundCount == 9) {
                showToast("Ничья!"); // Сообщение о ничьей
                resetGame(); // Сброс игры
            } else {
                player1Turn = !player1Turn; // Переключение хода игрока
            }
        }
    }

    private boolean checkForWin() {
        // Проверка горизонтальных, вертикальных и диагональных линий на победу
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString())
                    && buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString())
                    && !buttons[i][0].getText().toString().equals("")) {
                return true; // Горизонтальная победа
            }
            if (buttons[0][i].getText().toString().equals(buttons[1][i].getText().toString())
                    && buttons[0][i].getText().toString().equals(buttons[2][i].getText().toString())
                    && !buttons[0][i].getText().toString().equals("")) {
                return true; // Вертикальная победа
            }
        }
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString())
                && buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString())
                && !buttons[0][0].getText().toString().equals("")) {
            return true; // Диагональная победа (слева направо)
        }
        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString())
                && buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString())
                && !buttons[0][2].getText().toString().equals("")) {
            return true; // Диагональная победа (справа налево)
        }
        return false; // Нет победителя
    }

    private void resetGame() {
        roundCount = 0; // Сброс счетчика раундов
        player1Turn = true; // Сброс хода игрока
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(""); // Очистка текста кнопок
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Показ сообщения о результате
    }
}
