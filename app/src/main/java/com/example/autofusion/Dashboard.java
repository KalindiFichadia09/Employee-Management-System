package com.example.autofusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer_layout;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp = getSharedPreferences("AutoFusionLogin",MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Dashboard.this,drawer_layout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer);
        drawer_layout.addDrawerListener(toggle);

        //sync toggle
        toggle.syncState();

        loadFragment(new Dboard());
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id  = item.getItemId();

                if(id==R.id.dashboard) {
                    loadFragment(new Dboard());
                    Toast.makeText(Dashboard.this,"Dashboard", Toast.LENGTH_SHORT).show();
                } else if (id==R.id.profile) {
                    loadFragment(new profile());
                    Toast.makeText(Dashboard.this,"Profile", Toast.LENGTH_SHORT).show();
                } else if (id==R.id.working_hour) {
                    loadFragment(new working_hour());
                    Toast.makeText(Dashboard.this,"Working Hour", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.task) {
                    loadFragment(new task());
                    Toast.makeText(Dashboard.this,"Task", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.leave) {
                    loadFragment(new leave());
                    Toast.makeText(Dashboard.this,"Leave", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.salaryslip) {
                    loadFragment(new salaryslip());
                    Toast.makeText(Dashboard.this,"Salary-slips", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.holiday) {
                    loadFragment(new holiday());
                    Toast.makeText(Dashboard.this,"Holiday", Toast.LENGTH_SHORT).show();
                } else {
                    spe=sp.edit();
                    spe.remove("logVar");
                    spe.apply();
                    startActivity(new Intent(getApplicationContext(), login.class));
                    Toast.makeText(Dashboard.this,"Logout", Toast.LENGTH_SHORT).show();
                    finish();
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }
    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container,fragment);
        ft.commit();
    }
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}