package com.example.finish1m.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finish1m.Data.SQLite.SQLiteRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.UseCases.WriteSQLiteUserUseCase;
import com.example.finish1m.Presentation.Dialogs.DialogConfirm;
import com.example.finish1m.Presentation.Dialogs.OnConfirmListener;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityHubBinding;
import com.google.android.material.navigation.NavigationView;


public class HubActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHubBinding binding;

    private SQLiteRepositoryImpl sqLiteRepository;

    private WriteSQLiteUserUseCase writeSQLiteUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sqLiteRepository = new SQLiteRepositoryImpl(this);

        setSupportActionBar(binding.appBarHubActivity.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_hub_activity);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_exit:
                        DialogConfirm dialog = new DialogConfirm(HubActivity.this, "Выход", "Выйти", "Вы вуйствительно хотите выйти из аккаунта?", new OnConfirmListener(){
                            @Override
                            public void onConfirm(DialogConfirm d) {
                                writeSQLiteUserUseCase = new WriteSQLiteUserUseCase(sqLiteRepository, null, new OnSetDataListener() {
                                    @Override
                                    public void onSetData() {
                                        Intent intent = new Intent(HubActivity.this, StartActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailed() {
                                        Intent intent = new Intent(HubActivity.this, StartActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCanceled() {
                                        Intent intent = new Intent(HubActivity.this, StartActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                writeSQLiteUserUseCase.execute();
                            }
                        });
                        dialog.create(R.id.fragmentContainerView);
                break;
            case R.id.profile_refactor:

                        Intent intent = new Intent(HubActivity.this, RefactorUserActivity.class);
                        intent.putExtra("userEmail", PresentationConfig.user.getEmail());
                        startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_hub_activity);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}