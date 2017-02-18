package com.blogspot.thanhstech.hdtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    private EditText etItemEditView;

    private int pos;

    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        value = getIntent().getStringExtra("editValue");
        pos = getIntent().getIntExtra("editPosition", 0);
        etItemEditView = (EditText)findViewById(R.id.etItemEditView);
        etItemEditView.setText(value);

    }

    @Override
    public void onBackPressed() {

        doFinish(value);
    }


    public void onSubmit(View view) {

        doFinish(((EditText)findViewById(R.id.etItemEditView)).getText().toString());
    }

    /**
     * Sets return value and position and finishes the activity
     * @param updatedValue
     */
    private void doFinish(String updatedValue) {

        Intent data = new Intent();
        data.putExtra("updatedValue",updatedValue );
        data.putExtra("updatedPos",pos );
        setResult(1, data);
        finish();
    }

}
