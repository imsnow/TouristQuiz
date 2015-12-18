package ru.mishaignatov.touristquiz.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.database.CountryTable;

/**
 * Created by Ignatov on 13.08.2015.
 * Storage for specific country
 */
public class Country {

    private int id;
    private String filename;
    private String value;
    private int answered;
    private int total;
    //private boolean isClosed = true;

    public Country(int id, String filename, String value, int answered, int total){
        this.id       = id;
        this.filename = filename;
        this.value    = value;
        this.answered = answered;
        this.total    = total;
    }
    public int getId()         { return id; }
    public String getValue()   { return value; }
    public String getFileame() { return filename; }
    public int getAnswered(){ return answered; }
    public int getTotal()   { return total; }

    public boolean isFinished(){
        return answered == total;
    }


    public static CountryCursor queryCountry(){
        Cursor c = App.getDBHelper().getReadableDatabase()
                .query(CountryTable.TABLE_NAME, null, null, null, null, null, CountryTable.COLUMN_ID + " desc");
        return new CountryCursor(c);
    }

    public static class CountryCursor extends CursorWrapper {

        public CountryCursor(Cursor cursor) {
            super(cursor);
        }

        public Country getCountry(){
            if(isBeforeFirst() || isAfterLast())
                return null;
            int id          = getInt(getColumnIndex(CountryTable.COLUMN_ID));
            String filename = getString(getColumnIndex(CountryTable.COLUMN_FILE));
            String value    = getString(getColumnIndex(CountryTable.COLUMN_VALUE));
            int total       = getInt(getColumnIndex(CountryTable.COLUMN_TOTAL));
            int answered    = getInt(getColumnIndex(CountryTable.COLUMN_ANSWERED));

            return new Country(id, filename, value, answered, total);
        }
    }
}
