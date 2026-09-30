package com.example.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editSenha;
    private Button buttonLogin;
    private Button buttonCadastro;

    // Instância do banco utilizando a classe da sua colega
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastro = findViewById(R.id.buttonCadastro);

        // Inicialização com o Singleton da colega (Comente se for testar sem banco)
        /*
        db = AppDatabase.get(this);
        */

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        String email = editEmail.getText().toString().trim();
        String senhaDigitada = editSenha.getText().toString().trim();

        if (email.isEmpty() || senhaDigitada.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criptografa a senha para comparar com o hash salvo no banco
        String senhaCriptografada = SecurityUtils.hashSenha(senhaDigitada);

        /* DESCOMENTE PARA INTEGRAR COM O BANCO DA SUA COLEGA:
        User user = db.userDao().login(email, senhaCriptografada);
        if (user != null) {
            Toast.makeText(this, "Bem-vindo, " + user.user_name + "!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
        */

        // MOCK DE TESTE (Retirar quando ativar o banco)
        Toast.makeText(this, "Modo de Teste: Login validado!", Toast.LENGTH_SHORT).show();
    }
}