package com.bawp.WGU.util;

import android.content.Context;

import com.bawp.WGU.data.TermDao;
import com.bawp.WGU.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Term.class}, version = 2, exportSchema = false)

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
                        termDao.deleteAll();

                        Term term = new Term("Fall 2020", "2020-07-31", "2020-12-28");
                        termDao.insert(term);

                        term = new Term("Spring 2021", "2021-01-03", "2021-05-28");
                        termDao.insert(term);

                        term = new Term("Summer 2021", "2021-05-31", "2021-08-25");
                        termDao.insert(term);

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

}
