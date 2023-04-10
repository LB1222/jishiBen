package com.example.jishiben;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.jishiben.Util.ToastUtil;
import com.example.jishiben.bean.Note;
import com.example.jishiben.date.NoteDbOpenHelper;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private Note note;
    private EditText etTitle,etContent;
    private NoteDbOpenHelper mNoteDbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null){
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void save (View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)){
            ToastUtil.toastshort(this,"标题不能为空！");
            return;
        }
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long rowId = mNoteDbOpenHelper.updateDate(note);
        if (rowId != -1)
        {
            ToastUtil.toastshort(this,"修改成功！");
            this.finish();
        }else {
            ToastUtil.toastshort(this,"修改失败！");
        }
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}