package com.example.uscart.s2lab2_minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText editRows;
    private Button submit;
    private EditText editCols;
    private Spinner diffSpinner;
    private String spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editCols = findViewById(R.id.EditColumns);

        editRows = findViewById(R.id.EditRows);
        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new SettingsActivity.submitBtnListener());

        diffSpinner = findViewById(R.id.diffSpinner);
        String[] arraySpinner = new String[]{"Easy", "Medium", "Hard", "Impossible"}; //array of options to give to the spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //spinner
        diffSpinner.setAdapter(adapter);
        diffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) { //if an item is selected
                spinner = adapter.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { //do nothing

            }
        });

        if(diffSpinner.getSelectedItem() != null) {spinner = diffSpinner.getSelectedItem().toString();} //set the value to the one chosen
    }


    private class submitBtnListener implements View.OnClickListener { //click to send values
        public void onClick(View v) {
            if (editRows.getText().toString().isEmpty() || editCols.getText().toString().isEmpty()) { //if there isn't anything in it
                Toast.makeText(getApplicationContext(), "Enter the amount of rows and columns.", Toast.LENGTH_SHORT).show();
            } //don't send the values, instead tell the player to enter them
            else {
                if(editRows.getText().toString().length() < 4 && editCols.getText().toString().length() < 4) { //if the value is not greater than 1000 (it crashes if you do Integer.parseInt on it because the value is larger than an integer so I have to check this way)
                    if (Integer.parseInt(editRows.getText().toString()) >= 10) { //make sure the value they entered is less than 10, or else the -1s won't show
                        Toast.makeText(getApplicationContext(), "Enter an amount less than ten!", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(editCols.getText().toString()) >= 10) { //^makes sure
                        Toast.makeText(getApplicationContext(), "Enter an amount less than ten!", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(editRows.getText().toString()) == 0) { //you can't have width or a length of 0
                        Toast.makeText(getApplicationContext(), "Enter an amount that isn't zero.", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(editCols.getText().toString()) == 0) { //^no zeros
                        Toast.makeText(getApplicationContext(), "Enter an amount that isn't zero.", Toast.LENGTH_SHORT).show();
                    } else { //if all checks have been passed:
                        Toast.makeText(getApplicationContext(), "Rows -  " + editRows.getText().toString() + " \n" + "Columns -  " + editCols.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingsActivity.this, RunActivity.class); //make run class intent
                        intent.putExtra("Rows", editRows.getText().toString());
                        intent.putExtra("Columns", editCols.getText().toString());
                        intent.putExtra("Spinner", spinner); //add the values to the extra so you can get them in the run class
                        startActivity(intent); //go to the run class
                    }
                } //if the amount's length is not < 4, tell the user to enter a value less than 10.
                else { Toast.makeText(getApplicationContext(), "Enter an amount less than ten!", Toast.LENGTH_SHORT).show(); }
            }
        }
    }
}
