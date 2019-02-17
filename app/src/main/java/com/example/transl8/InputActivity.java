package com.example.transl8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InputActivity extends MainActivity {


    EditText inputPageET;
    Button inputPageBT;
    Button goAgain;
    ConstraintLayout resultLayout;
    TextView resultPage;


    //The URL to get the language data from
    private final String BASE_URL = "https://api.mymemory.translated.net/get?q=" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_text_page);

        inputPageBT = findViewById(R.id.input_page_button);
        resultLayout = findViewById(R.id.result);
        goAgain = findViewById(R.id.go_again_button);
        resultPage = findViewById(R.id.textView3);
        inputPageET = findViewById(R.id.input_text);



        Intent intent = getIntent();
            final String translateFromData= intent.getStringExtra("getTranslateFrom");
            final String translateToData= intent.getStringExtra("getTranslateTo");


        //Setting a listener on the "Translate" button
        inputPageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputPageET.getText().toString().trim();
                Log.d("Translate", "Input from the edit text is " + input);letsDoSomeNetworking(BASE_URL + input +"&langpair=" + translateFromData
                        + "|" + translateToData);
                resultLayout.setVisibility(View.VISIBLE);
            }
        });

        //Setting a listener on the "Go Again" button
        goAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homepage);
            }
        });
    }

    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Translate", "JSON: " + response.toString());
                try {
                    //First create JSON object from the json...
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    //Then get translatedText as string...
                    String translation = obj.getJSONObject("responseData").getString("translatedText");
                    Log.d("Translate", "The translation is " + translation);
                    resultPage.setText(translation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Translate", "Request fail! Status code: " + statusCode);
                Log.d("Translate", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(InputActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }
        });
    }

    }
