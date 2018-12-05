package com.example.marcin.projektinterfejsukk;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    static protected ArrayList<Item> exampleItems;
    static protected ArrayList<Item> itemsInCart;


    private TableLayout tableLayout;
    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i = new Intent(getBaseContext(), itemPreview.class);
            i.putExtra("id", (int)v.getTag());
            startActivity(i);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar config
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Sklep sportowy");
        mDrawerLayout = findViewById(R.id.drawer_layout);


        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        Intent i;
                        switch (menuItem.getItemId())
                        {
                            case R.id.log_in:
                                menuItem.setVisible(false);

                                // Show My Account button
                                MenuItem temp = navigationView.getMenu().findItem(R.id.my_account);
                                temp.setVisible(true);
                                Toast.makeText(getBaseContext(), "Zalogowano", Toast.LENGTH_SHORT).show();

                                //mDrawerLayout.openDrawer(GravityCompat.START);
                                break;
                            case R.id.my_cart:
                                i = new Intent(getBaseContext(), MyCart.class);
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

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        // example data
        exampleItems = new ArrayList<Item>();
        itemsInCart = new ArrayList<Item>();

        exampleItems.add(new Item(
                "KOLARSTWO","ROWER GÓRSKI", 1399, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.rower_gorski,R.drawable.rower_gorski_2, R.drawable.rower_gorski_2))));
        exampleItems.add(new Item(
                "BIEGANIE","CZAPKA", 39, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.czapka,R.drawable.czapka_2, R.drawable.czapka_3))));
        exampleItems.add(new Item(
                "PIŁKA NOŻNA","PIŁKA NIKE", 89, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.pilka_nike,R.drawable.pilka_nike_2, R.drawable.pilka_nike_3))));

        exampleItems.add(new Item(
                "KOLARSTWO","ROWER GÓRSKI", 1399, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.rower_gorski,R.drawable.rower_gorski_2, R.drawable.rower_gorski_2))));
        exampleItems.add(new Item(
                "BIEGANIE","CZAPKA", 39, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.czapka,R.drawable.czapka_2, R.drawable.czapka_3))));
        exampleItems.add(new Item(
                "PIŁKA NOŻNA","PIŁKA NIKE", 89, getString(R.string.example_description),
                new ArrayList<Integer>(Arrays.asList(R.drawable.pilka_nike,R.drawable.pilka_nike_2, R.drawable.pilka_nike_3))));




        addRows();

    }

    private void addRows()
    {
        for (int i = 0; i < exampleItems.size(); i++)
        {
            TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table, null);
            tableRow1.setOnClickListener(onClickListener);
            tableRow1.setTag(i);

            TextView textViewName = tableRow1.findViewById(R.id.textViewName);
            ImageView imageView = tableRow1.findViewById(R.id.textImage);
            TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

            textViewName.setText(exampleItems.get(i).getName());
            imageView.setImageDrawable(getDrawable(exampleItems.get(i).getImages().get(0)));
            textViewPrice.setText(Double.toString(exampleItems.get(i).getPrice()) + " zł");
            tableLayout.addView(tableRow1, 0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint("Wyszukaj...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                tableLayout.removeAllViews();
                for (int i = 0; i < exampleItems.size(); i++)
                {

                    if (exampleItems.get(i).getName().matches(".*" + s.toUpperCase() + ".*"))
                    {
                        TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table, null);
                        TextView textViewName = tableRow1.findViewById(R.id.textViewName);
                        ImageView imageView = tableRow1.findViewById(R.id.textImage);
                        TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

                        textViewName.setText(exampleItems.get(i).getName());
                        imageView.setImageDrawable(getDrawable(exampleItems.get(i).getImages().get(0)));
                        textViewPrice.setText(Double.toString(exampleItems.get(i).getPrice()) + " zł");
                        tableLayout.addView(tableRow1, 0);
                    }

                }

                return false;
            }
        });

        // hide My Account button
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.my_account).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.soccer:
                item.setChecked(true);
                tableLayout.removeAllViews();
                for (int i = 0; i < exampleItems.size(); i++)
                {
                    if (exampleItems.get(i).getCategory().equals("PIŁKA NOŻNA"))
                    {
                        TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table, null);
                        TextView textViewName = tableRow1.findViewById(R.id.textViewName);
                        ImageView imageView = tableRow1.findViewById(R.id.textImage);
                        TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

                        textViewName.setText(exampleItems.get(i).getName());
                        imageView.setImageDrawable(getDrawable(exampleItems.get(i).getImages().get(0)));
                        textViewPrice.setText(Double.toString(exampleItems.get(i).getPrice()) + " zł");
                        tableLayout.addView(tableRow1, 0);
                    }

                }
                break;
            case R.id.running:
                item.setChecked(true);
                tableLayout.removeAllViews();
                for (int i = 0; i < exampleItems.size(); i++)
                {
                    if (exampleItems.get(i).getCategory().equals("BIEGANIE"))
                    {
                        TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table, null);
                        TextView textViewName = tableRow1.findViewById(R.id.textViewName);
                        ImageView imageView = tableRow1.findViewById(R.id.textImage);
                        TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

                        textViewName.setText(exampleItems.get(i).getName());
                        imageView.setImageDrawable(getDrawable(exampleItems.get(i).getImages().get(0)));
                        textViewPrice.setText(Double.toString(exampleItems.get(i).getPrice()) + " zł");
                        tableLayout.addView(tableRow1, 0);
                    }

                }
                break;
            case R.id.cycling:
                item.setChecked(true);
                tableLayout.removeAllViews();
                for (int i = 0; i < exampleItems.size(); i++)
                {
                    if (exampleItems.get(i).getClass().equals("KOLARSTWO"))
                    {
                        TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table, null);
                        TextView textViewName = tableRow1.findViewById(R.id.textViewName);
                        ImageView imageView = tableRow1.findViewById(R.id.textImage);
                        TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

                        textViewName.setText(exampleItems.get(i).getName());
                        imageView.setImageDrawable(getDrawable(exampleItems.get(i).getImages().get(0)));
                        textViewPrice.setText(Double.toString(exampleItems.get(i).getPrice()) + " zł");
                        tableLayout.addView(tableRow1, 0);
                    }

                }
                break;
            case R.id.allCategories:
                item.setChecked(true);
                tableLayout.removeAllViews();
                addRows();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
