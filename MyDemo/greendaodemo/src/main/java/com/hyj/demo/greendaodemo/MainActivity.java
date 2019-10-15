package com.hyj.demo.greendaodemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hyj.demo.greendaodemo.db.DBManager;
import com.hyj.demo.greendaodemo.entity.MovieCollect;
import com.hyj.demo.greendaodemo.entity.Student;
import com.hyj.demo.greendaodemo.greendaodb.DaoManager;
import com.hyj.demo.greendaodemo.greendaodb.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btInsert, btQuery, btUpdate, btDelete;
    private DBManager dbManager;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dbManager = new DBManager(this);
        daoSession = new DaoManager().getDaoSession();
        btInsert = findViewById(R.id.btInsert);
        btInsert.setOnClickListener(this);
        btQuery = findViewById(R.id.btQquery);
        btQuery.setOnClickListener(this);
        btUpdate = findViewById(R.id.btUpdate);
        btUpdate.setOnClickListener(this);
        btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btInsert:
//                List<Student> students = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    Student student = new Student();
//                    student.id = i;
//                    student.name = "学生" + i;
//                    student.age = 10 + i;
//                    student.info = "我是一个好学生" + i;
//                    students.add(student);
//                }
//                dbManager.add(students);
//                break;
//            case R.id.btQquery:
//                List<Student> queryStudents = dbManager.query();
//                if (queryStudents != null && queryStudents.size() > 0)
//                    for (int i = 0; i < queryStudents.size(); i++) {
//                        Log.d("sutdents:" + i, "-----" + queryStudents.get(i).toString());
//                    }
//                break;
//            case R.id.btUpdate:
//                Student student = new Student();
//                student.name = "学生1";
//                student.age = 100;
//                dbManager.updateAge(student);
//                break;
//            case R.id.btDelete:
//                break;
            case R.id.btInsert:
                List<MovieCollect> movieCollects = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    MovieCollect movieCollect = new MovieCollect();
                    movieCollect.setMovieImage("file://" + i + ".png");
                    movieCollect.setTitle("电影" + i);
                    movieCollect.setYear(10 + i);
                    movieCollects.add(movieCollect);
                }
                daoSession.getMovieCollectDao().insertInTx(movieCollects);
                break;
            case R.id.btQquery:
                List<MovieCollect> listMovieCollect = daoSession.getMovieCollectDao().loadAll();
                for (MovieCollect movieCollect : listMovieCollect) {
                    Log.d("---->", movieCollect.toString());
                }
                break;
            case R.id.btUpdate:
                break;
            case R.id.btDelete:
                break;

        }
    }
}
