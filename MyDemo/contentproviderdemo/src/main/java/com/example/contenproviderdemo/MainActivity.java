package com.example.contenproviderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.contenproviderdemo.greendaodemo.db.DBManager;
import com.example.contenproviderdemo.greendaodemo.entity.MovieCollect;
import com.example.contenproviderdemo.greendaodemo.entity.Student;
import com.example.contenproviderdemo.greendaodemo.greendaodb.DaoManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btInsert;
    private Button btQuery;
    private DBManager dbManager;
    private TestDatabaseHelper mDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btInsert = findViewById(R.id.btInsert);
        btQuery = findViewById(R.id.btQuery);
        btInsert.setOnClickListener(this);
        btQuery.setOnClickListener(this);
        dbManager = new DBManager(this);
        mDataBaseHelper = new TestDatabaseHelper(this, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btInsert:
//                for (int i = 0; i < 10; i++) {
//                    Student student = new Student();
//                    student.id = i;
//                    student.age = 5 + i;
//                    student.info = i + 1 + "班";
//                    student.name = "学生" + i;
//                    student.sex = i / 2 == 0 ? true : false;
//                    mDataBaseHelper.commitInsert(student);
//                }
                for (int i = 0; i < 10; i++) {
                    MovieCollect movieCollect = new MovieCollect();
                    movieCollect.setMovieImage("file://" + i + ".png");
                    movieCollect.setTitle("电影" + i);
                    movieCollect.setYear(10 + i);
                    mDataBaseHelper.commitInsert(movieCollect);
                }

                break;
            case R.id.btQuery:
//                List<Student> students = mDataBaseHelper.getStudents();
//                for (int i = 0; i < students.size(); i++) {
//                    Log.d("---->", "onClick: "+students.get(i).toString());
//                }

                List<MovieCollect> movieCollects = mDataBaseHelper.getMovieCollect();
                for (int i = 0; i < movieCollects.size(); i++) {
                    Log.d("---->", "onClick: "+movieCollects.get(i).toString());
                }

                break;
        }
    }
}
