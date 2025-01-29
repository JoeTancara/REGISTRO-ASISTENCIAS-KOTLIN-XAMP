package com.joedev.registro

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Asistencias : AppCompatActivity() {
    var txtFecha: EditText? = null
    var txtEstado: EditText? = null
    var txtCodEstudiante: EditText? = null
    var txtNomMateria: EditText? = null
    var tbAsistencias: TableLayout? = null
    var idGlobal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencias)
        txtFecha = findViewById(R.id.txtFecha)
        txtEstado = findViewById(R.id.txtEstado)
        txtCodEstudiante = findViewById(R.id.txtCodigoEstudiante)
        txtNomMateria = findViewById(R.id.txtNombreM)
        tbAsistencias = findViewById(R.id.tbAsistencias)

        txtFecha?.setOnClickListener {
            showDatePickerDialog()
        }

        cargaTabla()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            txtFecha?.setText(dateFormat.format(selectedDate.time))
        }, year, month, day)

        datePickerDialog.show()
    }

    fun cargaTabla() {
        tbAsistencias?.removeAllViews()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ASISTENCIA/registros.php"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val registro = LayoutInflater.from(this).inflate(R.layout.table_row_asistencias, null, false)
                        val colFecha = registro.findViewById<View>(R.id.colFecha) as TextView
                        val colEstado = registro.findViewById<View>(R.id.colEstado) as TextView
                        val colCodEstudiante = registro.findViewById<View>(R.id.colCodEstudiante) as TextView
                        val colNomMateria = registro.findViewById<View>(R.id.colNomMateria) as TextView
                        val colEditar = registro.findViewById<View>(R.id.colEditarA)
                        val colBorrar = registro.findViewById<View>(R.id.colBorrarA)
                        colFecha.text = jsonObject.getString("fecha")
                        colEstado.text = jsonObject.getString("estado")
                        colCodEstudiante.text = jsonObject.getString("cod_estudiante")
                        colNomMateria.text = jsonObject.getString("nombre_materia")
                        colEditar.id = jsonObject.getString("id_asistencia").toInt()
                        colBorrar.id = jsonObject.getString("id_asistencia").toInt()
                        tbAsistencias?.addView(registro)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun clicGuardarEditr(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ASISTENCIA/editar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "La asistencia se actualizó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al actualizar asistencia $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_asistencia"] = idGlobal!!
                parametros["fecha"] = txtFecha?.text.toString()
                parametros["estado"] = txtEstado?.text.toString()
                parametros["cod_estudiante"] = txtCodEstudiante?.text.toString()
                parametros["nombre_materia"] = txtNomMateria?.text.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clicTablaEditar(view: View) {
        idGlobal = view.id.toString()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ASISTENCIA/registro.php?id_asistencia=${idGlobal}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                txtFecha?.setText(response.getString("fecha"))
                txtEstado?.setText(response.getString("estado"))
                txtCodEstudiante?.setText(response.getString("cod_estudiante"))
                txtNomMateria?.setText(response.getString("nombre_materia"))
            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun clicTablaBorrar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ASISTENCIA/borrar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "La asistencia se eliminó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al eliminar asistencia $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_asistencia"] = view.id.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clickBtnInsertar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ASISTENCIA/insertar.php"
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                cargaTabla()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["fecha"] = txtFecha?.text.toString()
                params["estado"] = txtEstado?.text.toString()
                params["cod_estudiante"] = txtCodEstudiante?.text.toString()
                params["nombre_materia"] = txtNomMateria?.text.toString()
                return params
            }
        }
        queue.add(request)
    }

    fun clicReset(view: View) {
        txtFecha?.setText("")
        txtEstado?.setText("")
        txtCodEstudiante?.setText("")
        txtNomMateria?.setText("")
    }
}