package com.example.tracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;

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

        // SharedViewModel
        viewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);

        // NavHost
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(
                                R.id.nav_host_fragment_content_main
                        );

        BottomNavigationView bottomNav = binding.bottomNav;

        //  ---------------------- gerenciando a navegacao
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            // navController = gerenciador de rotas do android (sabe qual tela está ativa e para onde ir)
            navController = navHostFragment.getNavController();

            // dizendo quais telas sao consideradas "principais" (para não mostrar o botão de voltar <- na barra superior quando o usuário estiver nelas)
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.FiltroFragment,
                    R.id.ListFragment,
                    R.id.GridFragment
            ).build();

            // ActionBar
            NavigationUI.setupActionBarWithNavController(
                    this,
                    navController,
                    appBarConfiguration
            );

            // pegando a barra de navegacao do xml
            BottomNavigationView bottomNavigationView = binding.bottomNav;

            // conectando os cliques dos botoes do menu com a troca de telas do navcontroller
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        // retirando o espaçamento do bottom menu main:
        BottomNavigationView bottomNav = binding.bottomNav;

        ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        // =========================================================================
        // OBSERVER REATIVO DE SESSÃO DO USUÁRIO
        // =========================================================================
        viewModel.getUsuarioAtivo().observe(this, user -> {
            if (user != null) {
                // SESSÃO ATIVA: Exibe o menu inferior
                binding.bottomNav.setVisibility(View.VISIBLE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            "Tracker - " + user.user_name
                    );
                }

                // Se estiver no LoginFragment,
                // vai para o FiltroFragment
                if (navController != null
                        && navController.getCurrentDestination() != null) {

                    int destinoAtual =
                            navController.getCurrentDestination().getId();

                    if (destinoAtual == R.id.LoginFragment) {

                        navController.navigate(
                                R.id.FiltroFragment
                        );
                    }
                }

                // Se estiver na tela de login, avança para o filtro principal
//                if (navController != null && navController.getCurrentDestination() != null) {
//                    if (navController.getCurrentDestination().getId() == R.id.LoginFragment) {
//                        navController.navigate(R.id.FiltroFragment);
//                    }
//                }
            } else {
                // SESSÃO INATIVA / LOGOUT: Oculta o menu inferior
                binding.bottomNav.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Tracker - Autenticação");
                }

//                // Redireciona para o login caso não esteja nele
//                if (navController != null && navController.getCurrentDestination() != null) {
//                    if (navController.getCurrentDestination().getId() != R.id.LoginFragment) {
//                        navController.navigate(R.id.LoginFragment);
//                    }
//                }
            }
        });

//        binding.fab.setOnClickListener(
//                view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show()
//        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.theme_light) {
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