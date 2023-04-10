package com.example.jishiben;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.jishiben.Adpter.MyAdapter;

import com.example.jishiben.Util.SpfUtil;
import com.example.jishiben.bean.Note;
import com.example.jishiben.date.NoteDbOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private List<Note> mNotes;
    private MyAdapter mAdapter;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    public static final int MODE_LINEAR = 0;
    public static final int MODE_GRID = 1;

    public static final String KEY_LAYOUT_MODE = "key_layout_mode";

    private int currentListLayoutMode = MODE_LINEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
        initEvent();
    }
//生命周期执行
    @Override
    protected void onResume() {
        super.onResume();
        refreshDateFromDb();
        setListLayout();
    }

    private void setListLayout() {
        currentListLayoutMode = SpfUtil.getIntWithDefault(this, KEY_LAYOUT_MODE, MODE_LINEAR);
        if (currentListLayoutMode == MODE_LINEAR) {
            setToLinearList();
        }else{
            setToGridList();
        }
    }

    //添加数据后刷新数据
    private void refreshDateFromDb() {
        mNotes = getDateFromDB();
        mAdapter.refreshDate(mNotes);
    }

    private void initEvent() {
//        mAdapter = new MyAdapter(this,mNotes);
//        mRecyclerView.setAdapter(mAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        mAdapter = new MyAdapter(this, mNotes);
        mRecyclerView.setAdapter(mAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mMyAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        setListLayout();

    }

    private void initDate() {
        mNotes = new ArrayList<>();
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
//        //假数据自动生成界面
//        for (int i = 0; i < 30; i++) {
//
//
//            Note note = new Note();
//            note.setTitle("这是标题" + i);
//            note.setContent("这是内容" + i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNotes.add(note);
//
//        }
    //得到数据
      //  mNotes = getDateFromDB();

    }

    private List<Note> getDateFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }

    public void add(View view) {
        Intent intent = new Intent(this,addActivity.class);
        startActivity(intent);
    }
//选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
       // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.memnu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               mNotes = mNoteDbOpenHelper.queryFromDbByTitle(newText);
                mAdapter.refreshDate(mNotes);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.menu_linear:

//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//                mRecyclerView.setLayoutManager(linearLayoutManager);
//                mAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
//                mAdapter.notifyDataSetChanged();
                setToLinearList();
                currentListLayoutMode = MODE_LINEAR;
                SpfUtil.saveInt(this,KEY_LAYOUT_MODE,MODE_LINEAR);

                return true;


            case R.id.menu_grid:
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//                mRecyclerView.setLayoutManager(gridLayoutManager);
//                mAdapter.setViewType(MyAdapter.TYPE_GRID_LAYOUT);
//                mAdapter.notifyDataSetChanged();
                setToGridList();
                currentListLayoutMode = MODE_GRID;
                SpfUtil.saveInt(this,KEY_LAYOUT_MODE,MODE_GRID);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    private void setToLinearList() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        mAdapter.notifyDataSetChanged();
    }


    private void setToGridList() {
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setViewType(MyAdapter.TYPE_GRID_LAYOUT);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentListLayoutMode == MODE_LINEAR) {
            MenuItem item = menu.findItem(R.id.menu_linear);
            item.setChecked(true);
        } else {
            menu.findItem(R.id.menu_grid).setChecked(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}