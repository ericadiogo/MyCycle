package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class PositivityActivity extends AppCompatActivity {
    private static final String TAG = "PositivityActivity";
    private LinearLayout positiveBack;
    private Button positiveButton;
    private TextView positiveText;
    private CardView cardPositiveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positivity);

        positiveBack = findViewById(R.id.positiveBack);
        positiveButton = findViewById(R.id.positiveButton);
        positiveText = findViewById(R.id.positiveText);
        cardPositiveText = findViewById(R.id.cardPositiveText);

        positiveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PositivityActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWellnessInfo();
            }
        });
    }

    private void fetchWellnessInfo() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://positivity-tips.p.rapidapi.com/api/positivity/wellness")
                .get()
                .addHeader("x-rapidapi-key", "42ebb54883msh0ddb41c73e00aa7p1da3b5jsn4258689ea6f5")
                .addHeader("x-rapidapi-host", "positivity-tips.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Network call failed", e);
                runOnUiThread(() -> {
                    positiveText.setText("Failed to fetch data.");
                    cardPositiveText.setVisibility(View.VISIBLE);
                    positiveText.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    Log.d(TAG, "Response received: " + responseBody);

                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                    final String tip = jsonObject.get("tip").getAsString();

                    runOnUiThread(() -> {
                        positiveText.setText(tip);
                        cardPositiveText.setVisibility(View.VISIBLE);
                        positiveText.setVisibility(View.VISIBLE);
                    });
                } else {
                    Log.e(TAG, "Server returned error: " + response.code() + " - " + response.message());
                    final String responseBody = response.body() != null ? response.body().string() : "No response body";
                    Log.e(TAG, "Error response body: " + responseBody);
                    runOnUiThread(() -> {
                        positiveText.setText("Failed to fetch data. Error: " + responseBody);
                        cardPositiveText.setVisibility(View.VISIBLE);
                        positiveText.setVisibility(View.VISIBLE);
                    });
                }
            }
        });
    }
}
