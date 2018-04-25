package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import junit.framework.Test;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mMainName;
    private TextView mAlsoKnownAs;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mMainName = findViewById(R.id.main_name_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : list) {
            stringBuilder.append(item).append("\n");
        }
        return stringBuilder.toString();
    }

    private void populateUI(Sandwich sandwich) {
        mMainName.setText(sandwich.getMainName());
        String replaceAlso = listToString(sandwich.getAlsoKnownAs());
        mAlsoKnownAs.setText(replaceAlso);
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription());
        String replaceIngredients = listToString(sandwich.getIngredients());
        mIngredients.setText(replaceIngredients);

        if(replaceAlso.equals(""))
        {
            mAlsoKnownAs.setText(getResources().getText(R.string.detail_no_data));
        }
        if(sandwich.getDescription().equals(""))
        {
            mDescription.setText(getResources().getText(R.string.detail_no_data));
        }
        if(sandwich.getPlaceOfOrigin().equals(""))
        {
            mPlaceOfOrigin.setText(getResources().getText(R.string.detail_no_data));
        }
        if(replaceIngredients.equals(""))
        {
            mIngredients.setText(getResources().getText(R.string.detail_no_data));
        }
    }
}
