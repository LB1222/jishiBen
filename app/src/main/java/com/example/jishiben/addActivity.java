package com.example.jishiben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.jishiben.Util.ToastUtil;
import com.example.jishiben.bean.Note;
import com.example.jishiben.date.NoteDbOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addActivity extends AppCompatActivity {
   private EditText etTitle,etContent;
   private NoteDbOpenHelper mNoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
         etTitle = findViewById(R.id.et_title);
         etContent = findViewById(R.id.et_content);
         mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }


    public void add(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)){
            ToastUtil.toastshort(this,"标题不能为空！");
        }
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long row = mNoteDbOpenHelper.insertDate(note);
        if(row !=-1){
            ToastUtil.toastshort(this,"添加成功！");
            //添加成功后自动返回界面
            this.finish();

        }else {
            ToastUtil.toastshort(this,"添加失败！");

        }

    }
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}