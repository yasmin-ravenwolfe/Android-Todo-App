package com.projects.yasmin.todoapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TodoApp extends Activity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etItems;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      onCreate use this file as view
        setContentView(R.layout.activity_todo_app);

        lvItems = (ListView) findViewById(R.id.listView);
        etItems = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.button);

        readItems();

//      (context, resource [resource ID for a layout file containing a TextView to use when
//      instantiating views-use this view to instantiate list items], objects to populate listView with)
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("Go to class");
//        items.add("Code an Android app");
//        items.add("sleep");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item =  etItems.getText().toString();
                items.add(item);
                itemsAdapter.notifyDataSetChanged();
                etItems.setText("");
                writeItems();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
