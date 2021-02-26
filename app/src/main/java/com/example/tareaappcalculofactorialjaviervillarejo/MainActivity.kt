package com.example.tareaappcalculofactorialjaviervillarejo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.example.tareaappcalculofactorialjaviervillarejo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var stringBuilder = StringBuilder()
    var parametro = 0
    var resultado = 1
    private lateinit var task: MyTask
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        binding.operacionText?.movementMethod = ScrollingMovementMethod()
        stringBuilder = StringBuilder("Inicio de calculadora\n")
        stringBuilder.append("Introduce un Numero\n")
        binding.operacionText.text = "${stringBuilder.toString()}"
        task = MyTask(this, "Operacion", 1.0)

        binding.buttonCalcular.setOnClickListener {
            if (!binding.txtNumber.text.isEmpty()) {
                parametro = binding.txtNumber.text.toString().toInt()
                binding.buttonCalcular.isEnabled = false

                startTasks()
            }
        }
    }

    fun startTasks() {
        resultado = 1
        job = MainScope().launch {
            task.execute(parametro)
        }
        binding.buttonCalcular.isEnabled = true
    }

    suspend fun actualizacion(valor: Int) = withContext(Dispatchers.Main) {
        var numAux = resultado
        var result = valor * numAux
        stringBuilder.append("${valor} * ${numAux} = ${result}\n")
        resultado = result
        binding.operacionText.text = "${stringBuilder.toString()}"
    }

    suspend fun finOperacion(mensaje: String) = withContext(Dispatchers.Main) {
        stringBuilder.append("${mensaje}\n")
        binding.operacionText.text = "${stringBuilder.toString()}"
    }
}