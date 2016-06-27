package app.com.wight.android.tempconverter10;

import java.text.NumberFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class TempConverter1Activity extends AppCompatActivity
implements OnEditorActionListener {

    // define variables for the widgets
    private EditText fahrenheitEditText;
    private TextView celsiusTextView;
    private float celsius;
    private String celsiusString;

    // define SharedPreferences object
    private SharedPreferences savedValues;

    // define an instance variable for the F to C temp conversion
    private String fahrenheitString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter1);

        // get references to the widgets
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        celsiusTextView = (TextView) findViewById(R.id.celsiusTextView);

        // set the listeners
        fahrenheitEditText.setOnEditorActionListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }
    @Override
    public void onPause(){
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        fahrenheitString = savedValues.getString("fahrenheitString", "");

        //set the fahrenheit temp on its widget
        fahrenheitEditText.setText(fahrenheitString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay(){

        // get the fahrenheit temp
        fahrenheitString = fahrenheitEditText.getText().toString();
        float fahrenheit;
        if (fahrenheitString.equals("")) {
            fahrenheit = 0;
        }
        else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }

        // calculate Fahrenheit to Celsius conversion
        celsius = ((fahrenheit - 32) * 5 / 9);

        //display the results with formatting
        NumberFormat degrees = NumberFormat.getNumberInstance();
        celsiusString = degrees.format(celsius);
        celsiusTextView.setText(celsiusString);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE||actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

}
