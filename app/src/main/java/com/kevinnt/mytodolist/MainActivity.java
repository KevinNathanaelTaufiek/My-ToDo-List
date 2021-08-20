package com.kevinnt.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_add;
    private RecyclerView rv_todo_list;
    private ConstraintLayout layout_add_task;
    private EditText et_add_task;
    private Button btn_add_task;
    private TextView tv_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        fab_add = findViewById(R.id.fab_add);
        rv_todo_list = findViewById(R.id.rv_todo_list);
        layout_add_task = findViewById(R.id.layout_add_task);
        et_add_task = findViewById(R.id.et_add_task);
        btn_add_task = findViewById(R.id.btn_add_task);
        tv_credit = findViewById(R.id.tv_credit);

        RecyclerViewToDoListAdapter adapter = new RecyclerViewToDoListAdapter(this);
        rv_todo_list.setAdapter(adapter);
        rv_todo_list.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rv_todo_list);

        DatabaseHandler db = new DatabaseHandler(this);

//        ArrayList<Item> dummyItem = new ArrayList<>();
//        dummyItem.add(new Item(1, "masak yuk", false));
//        dummyItem.add(new Item(2, "mandi yuk", false));
//        dummyItem.add(new Item(3, "sarapan yuk", false));
//        dummyItem.add(new Item(4, "kerjain pr yuk", false));
//        dummyItem.add(new Item(5, "latihan coding yuk", false));
//        dummyItem.add(new Item(6, "main main yuk", false));
//        dummyItem.add(new Item(7, "scroll tiktok yuk", false));
//        dummyItem.add(new Item(1, "masak yuk", false));
//        dummyItem.add(new Item(2, "mandi yuk", false));
//        dummyItem.add(new Item(3, "sarapan yuk", false));
//        dummyItem.add(new Item(4, "kerjain pr yuk", false));
//        dummyItem.add(new Item(5, "latihan coding yuk", false));
//        dummyItem.add(new Item(6, "main main yuk", false));
//        dummyItem.add(new Item(7, "scroll tiktok yuk", false));
//        adapter.setItem(dummyItem);

        adapter.setItem(db.getAllTask());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(layout_add_task.getVisibility() == View.GONE){
                    layout_add_task.setVisibility(View.VISIBLE);
                    tv_credit.setVisibility(View.GONE);
                } else {
                    layout_add_task.setVisibility(View.GONE);
                    tv_credit.setVisibility(View.VISIBLE);
                    hideKeyboard();
                }
            }
        });

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_add_task.getText().toString().equals("")){
                    try {
//                    dummyItem.add(new Item(-1, et_add_task.getText().toString(), false));
                        db.addTask(new Item(-1, et_add_task.getText().toString(), false));
                        adapter.setItem(db.getAllTask());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    et_add_task.setText("");
                    layout_add_task.setVisibility(View.GONE);
                    hideKeyboard();
                }

            }
        });


    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}