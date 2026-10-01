package com.example.tracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tracker.databinding.ActivityMainBinding;
import com.example.tracker.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private SharedViewModel viewModel;
    private NavController navController;
    private ActivityMainBinding binding;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(
                binding.main,
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        BottomNavigationView bottomNav = binding.bottomNav;

        // Gerenciando a navegação
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.FiltroFragment,
                    R.id.ListFragment,
                    R.id.GridFragment,
                    R.id.LoginFragment
            ).build();

            NavigationUI.setupActionBarWithNavController(
                    this,
                    navController,
                    appBarConfiguration
            );

            BottomNavigationView bottomNavigationView = binding.bottomNav;
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Esconde ou mostra a barra superior inteira dependendo se está no Login
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (getSupportActionBar() != null) {
                    if (destination.getId() == R.id.LoginFragment) {
                        getSupportActionBar().hide();
                    } else {
                        getSupportActionBar().show();
                    }
                }
            });
        }

        // Retirando o espaçamento do bottom menu main:
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        // Gerenciando a autenticação via ViewModel
        viewModel.getUsuarioAtivo().observe(this, user -> {
            this.currentUser = user;

            // Força o menu a se redesenhar sempre que o estado do usuário mudar (atualiza a foto)
            invalidateOptionsMenu();

            if (user != null) {
                binding.bottomNav.setVisibility(View.VISIBLE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Tracker - " + user.user_name);
                }

                // Se estiver no LoginFragment, avança para o filtro principal
                if (navController != null && navController.getCurrentDestination() != null) {
                    if (navController.getCurrentDestination().getId() == R.id.LoginFragment) {
                        navController.navigate(R.id.FiltroFragment);
                    }
                }
            } else {
                binding.bottomNav.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Tracker - Autenticação");
                }

                // Redireciona para o login caso não esteja nele
                if (navController != null && navController.getCurrentDestination() != null) {
                    if (navController.getCurrentDestination().getId() != R.id.LoginFragment) {
                        navController.navigate(R.id.LoginFragment);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentUser != null) {
            MenuItem profileItem = menu.findItem(R.id.action_profile);
            if (profileItem != null) {
                // Substitua 'fotoPerfil' pelo nome do atributo que guarda os bytes no seu model User
                byte[] fotoBytes = currentUser.photo;

                if (fotoBytes != null && fotoBytes.length > 0) {
                    // Converte o array de bytes em Bitmap original
                    Bitmap bitmapOriginal = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);

                    if (bitmapOriginal != null) {
                        // Transforma o Bitmap em uma versão circular
                        Bitmap bitmapCircular = getCircularBitmap(bitmapOriginal);

                        // Aplica no ícone do menu
                        Drawable drawable = new BitmapDrawable(getResources(), bitmapCircular);
                        profileItem.setIcon(drawable);
                    }
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // Método auxiliar para arredondar o Bitmap em formato de círculo perfeito
    private Bitmap getCircularBitmap(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int size = Math.min(width, height);

        // Cria um bitmap quadrado com o menor lado para garantir um círculo perfeito
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(output);

        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        paint.setShader(new android.graphics.BitmapShader(source,
                android.graphics.Shader.TileMode.CLAMP,
                android.graphics.Shader.TileMode.CLAMP));

        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return output;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_profile) {
            if (currentUser != null) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                intent.putExtra("USUARIO_ID", currentUser.id);
                startActivity(intent);
            }
            return true;
        }
        else if (id == R.id.action_logout) {
            if (currentUser != null) {
                viewModel.logout(currentUser);
            }
            return true;
        }
        else if (id == R.id.theme_light) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        } else if (id == R.id.theme_dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        } else if (id == R.id.theme_system) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        boolean handled = false;
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            handled = NavigationUI.navigateUp(navController, appBarConfiguration);
        }
        return handled || super.onSupportNavigateUp();
    }
}