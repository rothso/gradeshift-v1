package com.rothanak.grades.data.database;

import android.database.Cursor;

import java.util.Iterator;

import rx.Observable;

public class ObservableCursor {

    private static class IterableCursor implements Iterable<Cursor> {

        private final Cursor cursor;
        private final Iterator<Cursor> iterator = new Iterator<Cursor>() {
            @Override public boolean hasNext() {
                if (!cursor.isLast()) return true;
                cursor.close();
                return false;
            }

            @Override public Cursor next() {
                cursor.moveToNext();
                return cursor;
            }

            @Override public void remove() {

            }
        };

        public IterableCursor(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override public Iterator<Cursor> iterator() {
            return iterator;
        }
    }

    public static Observable<Cursor> from(Cursor cursor) {
        return Observable.from(new IterableCursor(cursor));
    }

}