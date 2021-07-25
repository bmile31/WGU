package com.bawp.WGU.util;

import android.content.Context;

import com.bawp.WGU.data.AssessmentDao;
import com.bawp.WGU.data.CourseDao;
import com.bawp.WGU.data.InstructorDao;
import com.bawp.WGU.data.TermDao;
import com.bawp.WGU.model.Assessment;
import com.bawp.WGU.model.Course;
import com.bawp.WGU.model.Instructor;
import com.bawp.WGU.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 5, exportSchema = false)

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
                        InstructorDao instructorDao = INSTANCE.instructorDao();
                        termDao.deleteAll();
                        courseDao.deleteAll();
                        assessmentDao.deleteAll();
                        instructorDao.deleteAll();


                        Term term = new Term("Fall 2020", "2021-07-07", "2021-07-07");
                        termDao.insert(term);

                        term = new Term("Spring 2021", "2021-07-07", "2021-07-07");
                        termDao.insert(term);

                        term = new Term("Summer 2021", "2021-07-07", "2021-07-07");
                        termDao.insert(term);

                        Course course = new Course("World History", "2021-07-07", "2021-07-07", "In progress", 1, "Something, something...");
                        courseDao.insert(course);

                        course = new Course("Biology", "2021-07-07", "2021-07-07", "Completed", 2, "Test note");
                        courseDao.insert(course);

                        course = new Course("English 101", "2021-07-07", "2021-07-07", "Dropped", 3, "Course notes would go here.");
                        courseDao.insert(course);

                        Assessment assessment = new Assessment("Test 1", "2021-07-07", "Performance assessment", 1);
                        assessmentDao.insert(assessment);

                        assessment = new Assessment("Test 2", "2021-07-07", "Objective assessment", 2);
                        assessmentDao.insert(assessment);

                        assessment = new Assessment("Test 3", "2021-07-07", "Objective assessment", 3);
                        assessmentDao.insert(assessment);

                        Instructor instructor = new Instructor("John Smith", "john@mail.com", "555-555-5555", 1);
                        instructorDao.insert(instructor);

                        instructor = new Instructor("Bobby Brown", "bobby@mail.com", "555-555-5555", 1);
                        instructorDao.insert(instructor);

                        instructor = new Instructor("Jan Doe", "jane@mail.com", "555-555-5555", 2);
                        instructorDao.insert(instructor);

                        instructor = new Instructor("Brenda Hendrix", "brenda@mail.com", "555-555-5555", 3);
                        instructorDao.insert(instructor);
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
    public abstract InstructorDao instructorDao();

}
