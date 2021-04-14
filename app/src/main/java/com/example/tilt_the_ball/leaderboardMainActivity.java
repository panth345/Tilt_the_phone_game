package com.example.tilt_the_ball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class leaderboardMainActivity extends AppCompatActivity {
    ListView recyclerView;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_main);
        getSupportActionBar().hide();


        Cursor result = db.gethighscore();
        String name[] = new String[result.getCount()];
        String score[] = new String[result.getCount()];

        int index=0;
        while(result.moveToNext()){
            name[index] = result.getString(1);
            score[index] = result.getString(5);
            index++;
        }
        final ListView listview = (ListView) findViewById(R.id.list_view);


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < name.length; ++i) {
            String dr = name[i] + "                               " + score[i] ;
            list.add(dr);


        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter((ListAdapter) adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(leaderboardMainActivity context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    // int employeeModelClasses = databaseHelperClass.Scoreupdate(name[index],score[index]);
/*
        if (name.length > 0){
            scoreAdapterClass employeadapterclass = new scoreAdapterClass();
            recyclerView.setAdapter(employeadapterclass);
        }else {
            Toast.makeText(this, "There is no employee in the database", Toast.LENGTH_SHORT).show();
        }



    }*/
}