package com.fpt.university.apphistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fpt.university.apphistory.database.HistoryDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvHistory;
    private HistoryApdapter apdapter;

    private void BindingView(){
        rcvHistory = findViewById(R.id.rcv_history);
    }

    private void loadData(){
        List<History> list = HistoryDatabase.getInstance(this).historyDAO().getListHistory();

        list.add(new History(1,R.drawable.camchatgpt, "14/3/2023", "Test 1"));

        apdapter = new HistoryApdapter(list, this);
        rcvHistory.setAdapter(apdapter);
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

        loadData();
    }


//    private List<History> getHistoryList() {
//        List<History> list = new ArrayList<>();
//        list.add(new History(1,R.drawable.camchatgpt, "14/3/2023", "Test 1"));
//        list.add(new History(2, R.drawable.camchatgpt, "13/3/2023", "Test 2"));
//        list.add(new History(3, R.drawable.camchatgpt, "12/3/2023", "Test 3"));
//        list.add(new History(4, R.drawable.camchatgpt, "11/3/2023", "Test 3"));
//
//        return list;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(apdapter != null){
            apdapter.release();
        }
    }
}