package com.example.tracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonCamera;
    private Button buttonSalvar;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private TextView textTitulo;

    private Uri photoUri;
    private File photoFile;
    private AppDatabase db;

    private boolean isModoEdicao = false;
    private int usuarioIdEdicao = -1;
    private byte[] fotoEmBytes = null;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean granted) {
                    if (granted) {
                        abrirCamera();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Permissão de câmera negada", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean success) {
                    if (success) {
                        carregarImagem();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        imageView = findViewById(R.id.imageView);
        buttonCamera = findViewById(R.id.buttonCamera);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        textTitulo = findViewById(R.id.textTitulo);

        /* DESCOMENTE PARA INICIALIZAR O BANCO DA SUA COLEGA:
        db = AppDatabase.get(this);
        */

        if (getIntent().hasExtra("USUARIO_ID")) {
            isModoEdicao = true;
            usuarioIdEdicao = getIntent().getIntExtra("USUARIO_ID", -1);
            textTitulo.setText("Editar Perfil");
            buttonSalvar.setText("Atualizar");
            carregarDadosParaEdicao();
        }

        buttonCamera.setOnClickListener(v -> checarPermissao());
        buttonSalvar.setOnClickListener(v -> salvarCadastro());
    }

    private void checarPermissao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            abrirCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private File criarArquivoImagem() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nome = "JPEG_" + timeStamp + "_";
        File pasta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(nome, ".jpg", pasta);
    }

    private void abrirCamera() {
        try {
            photoFile = criarArquivoImagem();
            photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
            takePictureLauncher.launch(photoUri);
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao criar imagem", Toast.LENGTH_SHORT).show();
        }
    }

    private void carregarImagem() {
        if (photoFile != null && photoFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
            fotoEmBytes = obterBytesDaFoto();
        }
    }

    private byte[] obterBytesDaFoto() {
        if (photoFile == null || !photoFile.exists()) {
            return fotoEmBytes;
        }
        try {
            return Files.readAllBytes(photoFile.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    private void carregarDadosParaEdicao() {
        /* DESCOMENTE PARA CARREGAR DADOS DO BANCO:
        User user = db.userDao().buscarPorId(usuarioIdEdicao);
        if (user != null) {
            editNome.setText(user.user_name);
            editEmail.setText(user.email);
            fotoEmBytes = user.photo;

            if (user.photo != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.photo, 0, user.photo.length);
                imageView.setImageBitmap(bitmap);
            }
        }
        */
    }

    private void salvarCadastro() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || (senha.isEmpty() && !isModoEdicao)) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] fotoFinal = obterBytesDaFoto();
        if (fotoFinal == null) {
            Toast.makeText(this, "Tire uma foto de perfil", Toast.LENGTH_SHORT).show();
            return;
        }

        /* DESCOMENTE PARA PERSISTIR NO BANCO DA SUA COLEGA:
        if (isModoEdicao) {
            User user = db.userDao().buscarPorId(usuarioIdEdicao);
            if (user != null) {
                user.user_name = nome;
                user.email = email;
                if (!senha.isEmpty()) {
                    user.password = SecurityUtils.hashSenha(senha);
                }
                user.photo = fotoFinal;
                db.userDao().atualizar(user);
                Toast.makeText(this, "Perfil atualizado!", Toast.LENGTH_SHORT).show();
            }
        } else {
            User user = new User();
            user.user_name = nome;
            user.email = email;
            user.password = SecurityUtils.hashSenha(senha);
            user.photo = fotoFinal;

            db.userDao().insert(user);
            Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
        }
        */

        // MOCK DE TESTE
        Toast.makeText(this, "Modo de Teste: Salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}