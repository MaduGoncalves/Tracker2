package com.example.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;

import com.example.tracker.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        BottomNavigationView bottomNav = binding.bottomNav;

        //  ---------------------- gerenciando a navegacao
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            // navController = gerenciador de rotas do android (sabe qual tela está ativa e para onde ir)
            navController = navHostFragment.getNavController();

            // dizendo quais telas sao consideradas "principais" (para não mostrar o botão de voltar <- na barra superior quando o usuário estiver nelas)
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.FiltroFragment, R.id.ListFragment, R.id.GridFragment).build();

            NavigationUI.setupActionBarWithNavController( this, navController, appBarConfiguration);


            // conectando os cliques dos botoes do menu com a troca de telas do navcontroller
            NavigationUI.setupWithNavController(bottomNav, navController);
        }

        //  ---------------------- retirando o espaçamento do bottom menu main:
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        //  ---------------------- gerenciando a autenticacao
        viewModel.getUsuarioAtivo().observe(this, user -> {
            this.currentUser = user;

            if (user != null) {
                binding.bottomNav.setVisibility(View.VISIBLE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Tracker - " + user.user_name);
                }

            } else {
                binding.bottomNav.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflando o menu
        getMenuInflater().inflate(R.menu.top_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_profile) {
            if (currentUser != null) {
                // Futuramente, você abrirá a tela de cadastro/edição passando o ID do usuário logado
                 Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                 intent.putExtra("USER_ID", currentUser.id);
                 startActivity(intent);
            }
            return true;
        }
        else if (id == R.id.action_logout) {
            if (currentUser != null) {
                // Aciona o método do ViewModel que altera isLogged = 0 no banco Room
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