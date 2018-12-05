package com.example.marcin.projektinterfejsukk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TableLayout;

import java.util.ArrayList;

public class Settings extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /////// toolbar config
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Ustawienia");
        mDrawerLayout = findViewById(R.id.drawer_layout_settings);


        final NavigationView navigationView = findViewById(R.id.nav_view_settings);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        switch (menuItem.getItemId())
                        {
                            case R.id.log_in:
                                menuItem.setVisible(false);

                                // Show My Account button
                                MenuItem temp = navigationView.getMenu().findItem(R.id.my_account);
                                temp.setVisible(true);

                                //mDrawerLayout.openDrawer(GravityCompat.START);
                                break;
                            case R.id.my_cart:
                                Intent i = new Intent(getBaseContext(), MyCart.class);
                                startActivity(i);
                                break;
                            case R.id.contact:
                                i = new Intent(getBaseContext(), Contact.class);
                                startActivity(i);
                                break;
                            case R.id.settings:
                                i = new Intent(getBaseContext(), Settings.class);
                                startActivity(i);
                                break;
                        }
                        // set item as selected to persist highlight
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here


                        return true;
                    }
                });
        ////////////


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
