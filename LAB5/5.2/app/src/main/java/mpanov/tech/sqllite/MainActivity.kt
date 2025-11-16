package mpanov.tech.sqllite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.time.LocalDateTime

class MainActivity : ComponentActivity(), View.OnClickListener {
    private val LOG_TAG = "myLogs"

    private lateinit var btnAdd: Button
    private lateinit var btnRead: Button
    private lateinit var btnClear: Button

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText

    private lateinit var sqlLiteData: TextView

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd = findViewById(R.id.buttonAdd)
        btnRead = findViewById(R.id.buttonRead)
        btnClear = findViewById(R.id.buttonClear)

        edtName = findViewById(R.id.editTextName)
        edtEmail = findViewById(R.id.editTextEmail)

        sqlLiteData = findViewById<TextView>(R.id.sqlLiteData)

        btnAdd.setOnClickListener(this)
        btnRead.setOnClickListener(this)
        btnClear.setOnClickListener(this)

        dbHelper = DBHelper(this)
    }

    override fun onClick(v: View?) {
        if (v == null) return

        val cv = ContentValues()

        val name = edtName.text.toString()
        val email = edtEmail.text.toString()

        val db: SQLiteDatabase = dbHelper.writableDatabase

        when (v.id) {

            R.id.buttonAdd -> {
                Log.d(LOG_TAG, "--- Insert into mytable ---")

                val currentDate = LocalDateTime.now().toString()

                cv.put("name", name)
                cv.put("email", email)
                cv.put("creationDate", currentDate)

                val rowId = db.insert("mytable", null, cv)
                Log.d(LOG_TAG, "row inserted, ID = $rowId")
            }

            R.id.buttonRead -> {
                Log.d(LOG_TAG, "--- Rows in mytable ---")

                val cursor: Cursor = db.query("mytable", null,
                    null, null, null, null, null)

                val sb = StringBuilder();

                if (cursor.moveToFirst()) {
                    val idIndex = cursor.getColumnIndex("id")
                    val nameIndex = cursor.getColumnIndex("name")
                    val emailIndex = cursor.getColumnIndex("email")
                    val dateIndex = cursor.getColumnIndex("creationDate")

                    do {
                        Log.d(
                            LOG_TAG,
                            "ID = ${cursor.getInt(idIndex)}, "
                                    + "name = ${cursor.getString(nameIndex)}, "
                                    + "email = ${cursor.getString(emailIndex)} "
                                    + "creationDate = ${cursor.getString(dateIndex)}"
                        )

                        val row =
                            "ID = ${cursor.getInt(idIndex)}\n" +
                                    "name = ${cursor.getString(nameIndex)}\n" +
                                    "email = ${cursor.getString(emailIndex)}\n" +
                                    "creationDate = ${cursor.getString(dateIndex)}\n\n"

                        sb.append(row);

                    } while (cursor.moveToNext())

                } else {
                    Log.d(LOG_TAG, "0 rows")
                    sb.append("0 rows")
                }

                cursor.close()

                sqlLiteData.text = sb.toString()
            }

            R.id.buttonClear -> {
                Log.d(LOG_TAG, "--- Clear mytable ---")
                val count = db.delete("mytable", null, null)
                Log.d(LOG_TAG, "deleted rows count = $count")
            }
        }
    }
}