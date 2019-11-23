package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich = null;

    ImageView ingredientsIv;
    TextView mAlsoKnow;
    TextView mOrigin;
    TextView mDesc;
    TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnow = findViewById(R.id.also_known_tv);
        mOrigin = findViewById(R.id.origin_tv);
        mDesc = findViewById(R.id.description_tv);
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

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {

            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String description = "";
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() != 0) {
            description = mAlsoKnow.getText().toString() + " ";
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                if (i == 0) {
                    description = description + sandwich.getAlsoKnownAs().get(i);
                } else {
                    description = description + ", " + sandwich.getAlsoKnownAs().get(i);
                }

                mAlsoKnow.setVisibility(View.VISIBLE);
                mAlsoKnow.setText(description);
            }
        } else {
            mAlsoKnow.setVisibility(View.INVISIBLE);
        }

        description = mDesc.getText().toString() + " ";

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            mDesc.setText(description + sandwich.getDescription());
        } else {
            mDesc.setVisibility(View.INVISIBLE);
        }

        description = mOrigin.getText().toString() + " ";

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrigin.setText(description + sandwich.getPlaceOfOrigin());
        } else {
            mOrigin.setVisibility(View.INVISIBLE);
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() != 0) {
            String ingred = mIngredients.getText().toString() + " ";
            for (int i =0; i < sandwich.getIngredients().size(); i++) {
                if (i == 0) {
                    ingred = ingred + sandwich.getIngredients().get(i);
                } else {
                    ingred = ingred + ", " + sandwich.getIngredients().get(i);
                }

                mIngredients.setVisibility(View.VISIBLE);
                mIngredients.setText(ingred);
            }
        } else {
            mIngredients.setVisibility(View.INVISIBLE);
        }
    }
}
