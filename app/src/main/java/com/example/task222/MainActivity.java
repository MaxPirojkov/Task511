package com.example.task222;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final String FILENAME = "file.txt";

    final String LOG_TAG = "myLogs";


    private Random random = new Random();
    private ItemsDataAdapter adapter;
    private List<Integer> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);

        setSupportActionBar(myToolbar);

        fillImages();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemData item = generateRandomItemData();
                writeFile(adapter.getItems());
            }
        });

        adapter = new ItemsDataAdapter(this, null);
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showItemData(position);
                return true;

            }
        });

        adapter.addItems(readFile());

    }

    void writeFile(List<ItemData> items) {

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<ItemData> readFile() {
        try {
            FileInputStream fin = getApplicationContext().openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            List<ItemData> items = (List<ItemData>) ois.readObject();
            Log.v(LOG_TAG, "Records read successfully");
            Log.d(LOG_TAG, String.valueOf(items.size()));
            ois.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }




    private void fillImages() {
        images.add(android.R.drawable.ic_menu_report_image);
        images.add(android.R.drawable.ic_menu_add);
        images.add(android.R.drawable.ic_menu_agenda);
        images.add(android.R.drawable.ic_menu_camera);
        images.add(android.R.drawable.ic_menu_call);
    }


    private ItemData generateRandomItemData() {
        adapter.addItem(new ItemData(
                images.get(random.nextInt(images.size())),
                "Hello" + adapter.getCount(),
                "It\'s me"
        ));
        return null;
    }


    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle() + "\n",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open_notes) {
            Intent intentNotes = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intentNotes);
            Toast.makeText(MainActivity.this, R.string.open_book, Toast.LENGTH_LONG).show();
            return true;

        }

        if (id == R.id.action_open_goalsTime) {
            Intent intentGoals = new Intent(MainActivity.this, GoalActivity.class);
            startActivity(intentGoals);
            Toast.makeText(MainActivity.this, R.string.open_goals, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
