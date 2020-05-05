package com.example.task222;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final String FILENAME = "file.txt";

    final String LOG_TAG = "myLogs";

    final String DIR_SD = "MyFiles";

    List<String> strings = new ArrayList<>();


    private Random random = new Random();
    private ItemsDataAdapter adapter;
    private List<Drawable> images = new ArrayList<>();
    List<ItemData> items;
    int position;

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
                generateRandomItemData();


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


    }


    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }



    void writeFile() {
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write(getItem(position));
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void testclick(View v) {
//        try {
//            FileInputStream fstream = new FileInputStream(FILENAME);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//            String strLine;
//            List<String> strings = new ArrayList<>();
//            while ((strLine = br.readLine()) != null) {
//                strings.add(strLine);
//            }
//            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.item_list_view, strings);
//            list1.setAdapter(adapter1);
//            fstream.close();
//            br.close();
//            }
//        } catch (IOException e) {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Ошибка", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }


    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_report_image));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_add));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_agenda));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_camera));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_call));
    }


    private void generateRandomItemData() {
        adapter.addItem(new ItemData(
                images.get(random.nextInt(images.size())),
                "Hello" + adapter.getCount(),
                "It\'s me",
                random.nextBoolean()
        ));
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
