package com.example.marcin.projektinterfejsukk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class itemPreview extends AppCompatActivity implements View.OnClickListener
{
    private TextView textViewPreviewName, textViewPreviewDescription, textViewPreviewPrice;
    private ImageSwitcher imageSwitcher;
    private Item item;

    public void onClickBack(View view)
    {
        onSupportNavigateUp();
    }

    public void onClickAddToCart(View view)
    {
        Main.itemsInCart.add(item);
        Toast.makeText(getBaseContext(), "Dodano do koszyka.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_preview);

        // adds back button to toolbar

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new MyViewFactory(this));

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));

        int id = getIntent().getExtras().getInt("id");
        item = new Item(
                Main.exampleItems.get(id).getCategory(),
                Main.exampleItems.get(id).getName(),
                Main.exampleItems.get(id).getPrice(),
                Main.exampleItems.get(id).getDescription(),
                Main.exampleItems.get(id).getImages());
        
        for (int i = 0; i < item.getImages().size(); i++)
        {
            ImageView localView = new ImageView(this);
            localView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            localView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            int resId = item.getImages().get(i);
            localView.setImageResource(resId);
            localView.setOnClickListener(this);
            linearLayout.addView(localView);
        }

        for (int i = 0; i < item.getImages().size(); i++)
        {
            ImageView localView = new ImageView(this);
            localView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            localView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            int resId = item.getImages().get(i);
            localView.setImageResource(resId);
            localView.setOnClickListener(this);
            linearLayout.addView(localView);
        }
        imageSwitcher.setImageDrawable(getDrawable(item.getImages().get(0)));

        textViewPreviewName = findViewById(R.id.textViewPreviewName);
        textViewPreviewName.setText(item.getName());

        textViewPreviewPrice = findViewById(R.id.textViewPreviewPrice);
        textViewPreviewPrice.setText(Double.toString(item.getPrice()) + " zł");

        textViewPreviewDescription = findViewById(R.id.textViewPreviewDescription);
        textViewPreviewDescription.setText(item.getDescription());

    }

    public void onClick(View v)
    {
        ImageView localView = (ImageView) v;
        imageSwitcher.setImageDrawable(((ImageView) v).getDrawable());


    }
}

class MyViewFactory implements ViewSwitcher.ViewFactory
{
    private Context context;

    public MyViewFactory(Context c)
    {
        context = c;
    }

    public View makeView()
    {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        return imageView;
    }

}