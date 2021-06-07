package com.bawp.WGU.util;

import android.content.Context;

import com.bawp.WGU.data.TermDao;
import com.bawp.WGU.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Term.class}, version = 1, exportSchema = false)

public abstract class TermRoomDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile TermRoomDatabase INSTANCE;
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        TermDao termDao = INSTANCE.termDao();
                        termDao.deleteAll();

                        Term term = new Term("Paulo", "Teacher");
                        termDao.insert(term);

                        term = new Term("Bond", "Spy");
                        termDao.insert(term);

                        term = new Term("Bruce", "Fighter");
                        termDao.insert(term);


                    });
                }
            };

    public static TermRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TermRoomDatabase.class, "term_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract TermDao termDao();

}
