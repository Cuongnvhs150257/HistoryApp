package com.fpt.university.apphistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fpt.university.apphistory.database.HistoryDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvHistory;
    private HistoryApdapter apdapter;

    private List<History> list;
    private int id = 1;

    private History history;

    private void BindingView(){
        rcvHistory = findViewById(R.id.rcv_history);
    }

    private void loadData(List<History> listSearch){

        if(listSearch != null){
            list = new ArrayList<>();
            list = listSearch;

        }else{
            list = HistoryDatabase.getInstance(this).historyDAO().getListHistory();
            //list.add(new History(1,R.drawable.camchatgpt, "14/3/2023", "Test 1"));
        }
        apdapter = new HistoryApdapter(list, this);
        rcvHistory.setAdapter(apdapter);
    }

    private void New(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        EditText editSearch = dialogView.findViewById(R.id.edtSearch);

        new AlertDialog.Builder(this)
                .setTitle("Enter new History")
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String searchText = editSearch.getText().toString().trim();
                        history = new History(id, R.drawable.camchatgpt, searchText, searchText);
                        HistoryDatabase.getInstance(MainActivity.this).historyDAO().insertHistory(history);
                        id++;
                        loadData(null);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void Search(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        EditText editSearch = dialogView.findViewById(R.id.edtSearch);

        new AlertDialog.Builder(this)
                .setTitle("Enter what do you want to search")
                .setView(dialogView)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String searchText = editSearch.getText().toString().trim();
                        List<History> listSearch = HistoryDatabase.getInstance(MainActivity.this).historyDAO().searchHistory(searchText);
                        loadData(listSearch);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optSearch:
                Search();
                break;
            case R.id.optNew:
                New();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindingView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvHistory.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvHistory.addItemDecoration(itemDecoration);

        loadData(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(apdapter != null){
            apdapter.release();
        }
    }
}