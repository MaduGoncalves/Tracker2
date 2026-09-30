package com.example.tracker;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tracker.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        if (navHostFragment != null) {

            // NavController
            navController = navHostFragment.getNavController();

            // Telas principais da aplicação
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

            // Bottom Navigation
            BottomNavigationView bottomNavigationView =
                    binding.bottomNav;

            NavigationUI.setupWithNavController(
                    bottomNavigationView,
                    navController
            );
        }

        // Remove espaçamento do BottomNavigationView
        BottomNavigationView bottomNav = binding.bottomNav;

        ViewCompat.setOnApplyWindowInsetsListener(
                bottomNav,
                (v, insets) -> {

                    v.setPadding(
                            0,
                            0,
                            0,
                            0
                    );

                    return insets;
                }
        );

        // ================================================================
        // OBSERVER DA SESSÃO DO USUÁRIO
        // ================================================================

        viewModel.getUsuarioAtivo().observe(this, user -> {

            if (user != null) {

                // ========================================================
                // USUÁRIO LOGADO
                // ========================================================

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

            } else {

                // ========================================================
                // USUÁRIO NÃO LOGADO
                // ========================================================

                binding.bottomNav.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            "Tracker - Autenticação"
                    );
                }

                // Volta para o LoginFragment
                // quando o usuário fizer logout
                if (navController != null
                        && navController.getCurrentDestination() != null) {

                    int destinoAtual =
                            navController.getCurrentDestination().getId();

                    if (destinoAtual != R.id.LoginFragment) {

                        navController.popBackStack(
                                R.id.LoginFragment,
                                false
                        );
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.top_menu_main,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(
            @NonNull MenuItem item
    ) {

        int id = item.getItemId();

        if (id == R.id.theme_light) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );

            return true;

        } else if (id == R.id.theme_dark) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );

            return true;

        } else if (id == R.id.theme_system) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        if (navController != null) {

            return NavigationUI.navigateUp(
                    navController,
                    appBarConfiguration
            );
        }

        return super.onSupportNavigateUp();
    }
}
