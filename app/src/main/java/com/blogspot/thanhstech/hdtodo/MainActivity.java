package com.blogspot.thanhstech.hdtodo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        setupListViewOneClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {

        EditText editText = (EditText)findViewById(R.id.etNewItem);
        String addText = editText.getText().toString();
        if(addText.equals("") || addText == null) {

            Toast.makeText(MainActivity.this, "Please enter an item to add!", Toast.LENGTH_SHORT).show();
        }
        else {

            itemsAdapter.add(addText);
            editText.setText("");
            writeItems();
        }

    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    private void setupListViewOneClickListener() {

        lvItems.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
                        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                        String value = items.get(position);
                        intent.putExtra("editValue", value);
                        intent.putExtra("editPosition", position);
                        startActivityForResult(intent, 100);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String updatedValue = data.getExtras().getString("updatedValue");
        items.set(data.getExtras().getInt("updatedPos"), updatedValue);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void writeItems() {

        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {

            FileUtils.writeLines(toDoFile, items);
        }
        catch (IOException exception) {

            exception.printStackTrace();
        }
    }

    private void readItems() {

        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {

            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        }
        catch (IOException exception) {

            exception.printStackTrace();
        }
    }
}
