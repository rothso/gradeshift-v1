package com.rothanak.grades.data.course;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rothanak.grades.data.auth.AuthFacade;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CourseDatabaseRepositoryTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock private SQLiteOpenHelper helper;
    @Mock private AuthFacade auth;

    private BriteDatabase database;
    private CourseRepository repository;

    @Before
    public void setUp() throws URISyntaxException {
        String resourcePath = "/database/TestDatabase.db"; // ${project_root}/src/test/resources
        String relativePath = getClass().getResource(resourcePath).toURI().getPath();
        String absolutePath = new File(relativePath).getAbsolutePath();

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(absolutePath, null, OPEN_READWRITE);
        when(helper.getReadableDatabase()).thenReturn(sqLiteDatabase);
        when(helper.getWritableDatabase()).thenReturn(sqLiteDatabase);
        doAnswer(answer -> {sqLiteDatabase.close(); return answer;}).when(helper).close();

        database = SqlBrite.create().wrapDatabaseHelper(helper);
        repository = new CourseDatabaseRepository(database, auth);
    }

    @Test
    public void test() {
        when(auth.getUserId()).thenReturn(1);

        repository.findByYear(2016)
                .flatMapIterable(courses -> courses)
                .map(course -> System.currentTimeMillis() + " " + course.getName())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println(System.currentTimeMillis())
                );

        ContentValues values = new ContentValues(7);
        values.putNull("id");
        values.put("user_id", 1);
        values.put("year", 2016);
        values.put("name", "Something A");
        values.put("instructor", "Teach A");
        values.put("period", 6);
        values.put("grade", 98.3);

        database.beginTransaction();
        try {
            database.insert("courses", values);
            database.insert("courses", values);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    @After
    public void tearDown() throws IOException {
        database.close();
    }

}