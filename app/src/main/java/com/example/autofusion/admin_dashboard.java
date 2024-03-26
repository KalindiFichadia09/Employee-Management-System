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

public class admin_dashboard extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer_layout;
    NavigationView nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deshboard);

        toolbar = findViewById(R.id.toolbar);


        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(admin_dashboard.this,drawer_layout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer);
        drawer_layout.addDrawerListener(toggle);

        //sync toggle
        toggle.syncState();

        loadFragment(new admin_dboard());
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id  = item.getItemId();

                if(id==R.id.dashboard) {
                    loadFragment(new admin_dboard());
                    Toast.makeText(admin_dashboard.this,"Dashboard", Toast.LENGTH_SHORT).show();
                } else if (id==R.id.department) {
                    loadFragment(new admin_manage_departments());
                    Toast.makeText(admin_dashboard.this,"Departments", Toast.LENGTH_SHORT).show();
                } else if (id==R.id.employee) {
                    loadFragment(new admin_manage_employee());
                    Toast.makeText(admin_dashboard.this,"Employees", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.task) {
                    loadFragment(new admin_manage_task());
                    Toast.makeText(admin_dashboard.this,"Task", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.leave) {
                    loadFragment(new admin_manage_leave());
                    Toast.makeText(admin_dashboard.this,"Leave", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.salary) {
                    loadFragment(new admin_manage_salary());
                    Toast.makeText(admin_dashboard.this,"Salary", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.holiday) {
                    loadFragment(new admin_manage_holiday());
                    Toast.makeText(admin_dashboard.this,"Holiday", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), login.class));
                    Toast.makeText(admin_dashboard.this,"Logout", Toast.LENGTH_SHORT).show();
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