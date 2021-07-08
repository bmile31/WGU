package com.bawp.WGU.util;

import android.content.Context;

import com.bawp.WGU.data.AssessmentDao;
import com.bawp.WGU.data.CourseDao;
import com.bawp.WGU.data.TermDao;
import com.bawp.WGU.model.Assessment;
import com.bawp.WGU.model.Course;
import com.bawp.WGU.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class}, version = 4, exportSchema = false)

public abstract class Database extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile Database INSTANCE;
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        TermDao termDao = INSTANCE.termDao();
                        CourseDao courseDao = INSTANCE.courseDao();
                        AssessmentDao assessmentDao = INSTANCE.assessmentDao();
                        termDao.deleteAll();
                        courseDao.deleteAll();
                        assessmentDao.deleteAll();

                        Term term = new Term("Fall 2020", "2020-07-31", "2020-12-28");
                        termDao.insert(term);

                        term = new Term("Spring 2021", "2021-01-03", "2021-05-28");
                        termDao.insert(term);

                        term = new Term("Summer 2021", "2021-05-31", "2021-08-25");
                        termDao.insert(term);

                        Course course = new Course("World History", "2020-09-02", "2020-07-02", "In Progress", 1);
                        courseDao.insert(course);

                        course = new Course("Biology", "2020-09-02", "2020-12-02", "In Progress", 2);
                        courseDao.insert(course);

                        course = new Course("English 101", "2020-12-02", "2020-12-03", "In Progress", 3);
                        courseDao.insert(course);

                        Assessment assessment = new Assessment("Test 1", "2020-09-02", "OA", 1);
                        assessmentDao.insert(assessment);

                        assessment = new Assessment("Test 2", "2020-09-02", "OA", 2);
                        assessmentDao.insert(assessment);

                        assessment = new Assessment("Test 3", "2020-09-02", "OA", 3);
                        assessmentDao.insert(assessment);

                    });
                }
            };

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "wgu_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

}
