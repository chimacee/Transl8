package com.example.transl8;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {


    //Member Variables
    Spinner translateFrom, translateTo;
    Button chooseLanguageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_language_activity);




    //Adding items on both spinners
    translateFrom = findViewById(R.id.translate_from_spinner);

    translateTo = findViewById(R.id.translate_to_spinner);

    // Create an ArrayAdapter using the String array and a spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.language_array, R.layout.spinner_item);

    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);

    // Apply the adapter to the spinner
        translateFrom.setAdapter(adapter);
        translateTo.setAdapter(adapter);

        translateFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Translate", "" + parent.getItemAtPosition(position));
                Log.d("Translate", "Waiting for translate to...");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        translateTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Translate", "" + parent.getItemAtPosition(position));
                Log.d("Translate", "Let's do some networking next...");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Adding OnClickListener to the button on the first page
        chooseLanguageButton = findViewById(R.id.choose_language_button);

        chooseLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("getTranslateFrom",translateFrom.getSelectedItem().toString());
                intent.putExtra("getTranslateTo", translateTo.getSelectedItem().toString());
                startActivity(intent);
            }
        });


    }

}
