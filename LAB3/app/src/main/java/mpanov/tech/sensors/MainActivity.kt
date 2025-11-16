package mpanov.tech.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mpanov.tech.sensors.ui.theme.SensorsTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    private var aX: TextView? = null
    private var aY: TextView? = null
    private var aZ: TextView? = null
    private var mX: TextView? = null
    private var mY: TextView? = null
    private var mZ: TextView? = null
    private var proximity: TextView? = null
    private var light: TextView? = null

    private lateinit var sensorManager: SensorManager
    private var aSensor: Sensor? = null
    private var pSensor: Sensor? = null
    private var mSensor: Sensor? = null
    private var lSensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aX = findViewById<TextView?>(R.id.textView2).apply { text = "0.0" }
        aY = findViewById<TextView?>(R.id.textView3).apply { text = "0.0" }
        aZ = findViewById<TextView?>(R.id.textView4).apply { text = "0.0" }
        mX = findViewById<TextView?>(R.id.textView6).apply { text = "0.0" }
        mY = findViewById<TextView?>(R.id.textView7).apply { text = "0.0" }
        mZ = findViewById<TextView?>(R.id.textView8).apply { text = "0.0" }
        proximity = findViewById<TextView?>(R.id.textView10).apply { text = "0.0" }
        light = findViewById<TextView?>(R.id.textView12).apply { text = "0.0" }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        pSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        lSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        enableEdgeToEdge()
        setContent {
            SensorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                aX?.text = "${event.values[0]}"
                aY?.text = "${event.values[1]}"
                aZ?.text = "${event.values[2]}"
            }
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                mX?.text = "${event.values[0]}"
                mY?.text = "${event.values[1]}"
                mZ?.text = "${event.values[2]}"
            }
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                proximity?.text = "${event.values[0]}"
            }
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                light?.text = "${event.values[0]}"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this, aSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, mSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, pSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, lSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this, aSensor)
        sensorManager.unregisterListener(this, mSensor)
        sensorManager.unregisterListener(this, pSensor)
        sensorManager.unregisterListener(this, lSensor)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SensorsTheme {
        Greeting("Android")
    }
}