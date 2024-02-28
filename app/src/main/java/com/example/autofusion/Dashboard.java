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
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer_layout;
    NavigationView nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Dashboard.this,drawer_layout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer);

        drawer_layout.addDrawerListener(toggle);
        //sync toggle
        toggle.syncState();

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
                    loadFragment(new profile());
                    Toast.makeText(Dashboard.this,"Working Hour", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.task) {
                    loadFragment(new task());
                    Toast.makeText(Dashboard.this,"Task", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.leave) {
                    loadFragment(new leave());
                    Toast.makeText(Dashboard.this,"Leave", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.payslips) {
//                    loadFragment(new holiday());
                    startActivity(new Intent(getApplicationContext(), pro.class));
                    Toast.makeText(Dashboard.this,"Pay-slips", Toast.LENGTH_SHORT).show();
                }else {
                    loadFragment(new holiday());
                    Toast.makeText(Dashboard.this,"Holidays", Toast.LENGTH_SHORT).show();
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
            private void loadFragment(Fragment fragment) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.fragment_container,fragment);
                ft.commit();
            }
        });
    }
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}