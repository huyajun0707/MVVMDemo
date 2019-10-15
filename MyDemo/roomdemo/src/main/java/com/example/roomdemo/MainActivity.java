package com.example.roomdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.roomdemo.dao.RoomDemoDatabase;
import com.example.roomdemo.entity.StudentEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RoomDemoDatabase database =  Room.databaseBuilder(getApplicationContext(),
                RoomDemoDatabase.class, "database_name")//数据库名称
                .addCallback(new RoomDatabase.Callback() {
                    //第一次创建数据库时调用，但是在创建所有表之后调用的
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }

                    //当数据库被打开时调用
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .allowMainThreadQueries()//允许在主线程查询数据
                .addMigrations()//迁移数据库使用，下面会单独拿出来讲
                .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                .build();

        findViewById(R.id.btInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StudentEntity [] studentEntities = new StudentEntity[10];

                for (int i = 0; i < 10; i++) {
                    studentEntities[i] = new StudentEntity();
                    studentEntities[i].setName("小雪"+i+10);
                    studentEntities[i].setSex(i%2);
                    studentEntities[i].setIgnoreText("忽略"+i+10);
                }

                database.studentDao().insert(studentEntities);
            }
        });

        findViewById(R.id.btQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StudentEntity> studentEntities = database.studentDao().getAll();
                for (int i = 0; i < studentEntities.size(); i++) {
                    Log.d("--->", "onClick: "+studentEntities.get(i).toString());
                }
                
                
            }
        });
        





    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book "
                    + " ADD COLUMN pub_year INTEGER");
        }
    };

    private void upData(){

        //数据库升级
//        Room.databaseBuilder(getApplicationContext(), MyDb.class, "database-name")
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
    }
}