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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyCart extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private TableLayout tableLayout;
    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i = new Intent(getBaseContext(), itemPreview.class);
            i.putExtra("id", (int) v.getTag());
            startActivity(i);

        }

    };

    private ArrayList<CheckableItem> itemsInCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);

        itemsInCart = new ArrayList<CheckableItem>();


        // Item to CheckableItem
        for (Item item : Main.itemsInCart)
            itemsInCart.add(new CheckableItem(false, item));

        /////// toolbar config
        Toolbar toolbar = findViewById(R.id.toolbar_my_cart);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Koszyk");
        mDrawerLayout = findViewById(R.id.drawer_layout_my_cart);


        final NavigationView navigationView = findViewById(R.id.nav_view_my_cart);
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

        tableLayout = (TableLayout) findViewById(R.id.tableLayout_my_cart);
        addRows(itemsInCart);

    }


    private void addRows(ArrayList<CheckableItem> itemsInCart)
    {
        for (int i = 0; i < itemsInCart.size(); i++)
        {

            TableRowWithContextMenuInfo tableRow1 = (TableRowWithContextMenuInfo) getLayoutInflater().inflate(R.layout.table_row_in_table_with_checkbox, null);
            tableRow1.setOnClickListener(onClickListener);
            tableRow1.setTag(i);

            CheckBox checkBox = tableRow1.findViewById(R.id.checkbox);
            checkBox.setTag(itemsInCart.get(i).hashCode());

            TextView textViewName = tableRow1.findViewById(R.id.textViewName);
            ImageView imageView = tableRow1.findViewById(R.id.textImage);
            TextView textViewPrice = tableRow1.findViewById(R.id.textViewPrice);

            textViewName.setText(itemsInCart.get(i).getName());
            imageView.setImageDrawable(getDrawable(itemsInCart.get(i).getImages().get(0)));
            textViewPrice.setText(Double.toString(itemsInCart.get(i).getPrice()) + " zł");
            tableLayout.addView(tableRow1, 0);
        }

    }

    public void onClickCheckbox(View view)
    {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked())
        {
            //checked.set((int) view.getTag(), true);
            for (CheckableItem item : itemsInCart)
                if (item.equals(checkBox.getTag()))
                    item.setChecked(true);

            checkBox.setChecked(true);
        } else
        {
            for (CheckableItem item : itemsInCart)
                if (item.equals(checkBox.getTag()))
                    item.setChecked(false);

            checkBox.setChecked(false);
        }

        TextView sum = findViewById(R.id.textVie_my_cart_sum);
        double sumDouble = 0;
        for (int i = 0; i < itemsInCart.size(); i++)
        {
            if (itemsInCart.get(i).getChecked())
                sumDouble += itemsInCart.get(i).getPrice();
        }
        sum.setText(Double.toString(sumDouble) + " zł");


    }

    public void onClickRemoveFromCart(View view)
    {
        Log.d("pies", Integer.toString(itemsInCart.size()));

        for (int i = 0; i < itemsInCart.size(); i++)
        {
            Log.d("pies", itemsInCart.get(i).getName() + " " + itemsInCart.get(i).getChecked().toString());
            if (itemsInCart.get(i).getChecked())
                itemsInCart.remove(i--);


        }
        Main.itemsInCart.clear();
        for (int i = 0; i < itemsInCart.size(); i++)
            Main.itemsInCart.add(itemsInCart.get(i).toItem());

        tableLayout.removeAllViewsInLayout();
        addRows(itemsInCart);
        TextView sum = findViewById(R.id.textVie_my_cart_sum);
        sum.setText("0 zł");


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

    public class CheckableItem extends Item
    {
        private Boolean checked;

        public CheckableItem(Boolean checked, String category, String name, double price, String description, ArrayList<Integer> images)
        {
            super(category, name, price, description, images);
            this.checked = checked;
        }

        public CheckableItem(Boolean checked, Item item)
        {
            super(item.getCategory(), item.getName(), item.getPrice(), item.getDescription(), item.getImages());
            this.checked = checked;
        }

        public Boolean getChecked()
        {
            return checked;
        }

        public void setChecked(Boolean checked)
        {
            this.checked = checked;
        }

        @Override
        public int hashCode()
        {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj)
        {
            if ((int) obj == hashCode())
                return true;
            return super.equals(obj);
        }

        public Item toItem()
        {
            return new Item(getCategory(), getName(), getPrice(), getDescription(), getImages());
        }
    }
}

