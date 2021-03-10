package com.example.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArray);
        listView.setAdapter(arrayAdapter);


        //listViewımıza tıklandıgında yapacagı seyı onItemClickListener ile yapıyoruz
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("artId", idArray.get(position));
                intent.putExtra("info","old");
                startActivity(intent);
            }
        });

        getData();
    }

    public void getData() {
        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM arts", null);
            int nameIx = cursor.getColumnIndex("artname");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                nameArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));
            }
            arrayAdapter.notifyDataSetChanged();
            cursor.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //hangi menuyu gostereceğimizi burada belirliyoruz
        //inflater : bir xml yarattıgınızda onu bır aktıvıtede gostermek. Bu appde bir tane menuinflater var

        MenuInflater menuInflater = getMenuInflater(); //menuyu urdakı akvıteye bağlamıs olduk
        menuInflater.inflate(R.menu.add_art,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //kullaıcı herhangı bır itei seçerse ne yapacagımzı buraya yazıyoruz

        if (item.getItemId() == R.id.add_ar_item)
        {
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            intent.putExtra("info", "new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}