package mpanov.tech.sqllite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "myDB", null, 2) {
    private val LOG_TAG = "myLogs"
    override fun onCreate(db: SQLiteDatabase) {

        Log.d(LOG_TAG, "--- onCreate database ---")
        db.execSQL(
            """
            CREATE TABLE mytable (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                email TEXT,
                creationDate TEXT
            );
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "--- onUpgrade database ---")
        db.execSQL("DROP TABLE IF EXISTS mytable")
        onCreate(db)
    }
}
