package com.joedev.registro

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

class Materias : AppCompatActivity() {
    var txtNombreMateria: EditText? = null
    var txtProfesor: EditText? = null
    var txtHorario: EditText? = null
    var tbMaterias: TableLayout? = null
    var idGlobal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materias)
        txtNombreMateria = findViewById(R.id.txtNombreMateria)
        txtProfesor = findViewById(R.id.txtProfesor)
        txtHorario = findViewById(R.id.txtHorario)
        tbMaterias = findViewById(R.id.tbMaterias)
        cargaTabla()
    }

    fun cargaTabla() {
        tbMaterias?.removeAllViews()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/MATERIA/registros.php"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val registro = LayoutInflater.from(this).inflate(R.layout.table_row_materias, null, false)
                        val colNombreMateria = registro.findViewById<View>(R.id.colNombreMateria) as TextView
                        val colProfesor = registro.findViewById<View>(R.id.colProfesor) as TextView
                        val colHorario = registro.findViewById<View>(R.id.colHorario) as TextView
                        val colEditar = registro.findViewById<View>(R.id.colEditarM)
                        val colBorrar = registro.findViewById<View>(R.id.colBorrarM)
                        colNombreMateria.text = jsonObject.getString("nombre_materia")
                        colProfesor.text = jsonObject.getString("profesor")
                        colHorario.text = jsonObject.getString("horario")
                        colEditar.id = jsonObject.getString("id_materia").toInt()
                        colBorrar.id = jsonObject.getString("id_materia").toInt()
                        tbMaterias?.addView(registro)
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
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/MATERIA/editar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "La materia se actualizó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al actualizar materia $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_materia"] = idGlobal!!
                parametros["nombre_materia"] = txtNombreMateria?.text.toString()
                parametros["profesor"] = txtProfesor?.text.toString()
                parametros["horario"] = txtHorario?.text.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clicTablaEditar(view: View) {
        idGlobal = view.id.toString()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/MATERIA/registro.php?id_materia=${idGlobal}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                txtNombreMateria?.setText(response.getString("nombre_materia"))
                txtProfesor?.setText(response.getString("profesor"))
                txtHorario?.setText(response.getString("horario"))
            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun clicTablaBorrar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/MATERIA/borrar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "La materia se eliminó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al eliminar materia $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_materia"] = view.id.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clickBtnInsertar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/MATERIA/insertar.php"
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
                params["nombre_materia"] = txtNombreMateria?.text.toString()
                params["profesor"] = txtProfesor?.text.toString()
                params["horario"] = txtHorario?.text.toString()
                return params
            }
        }
        queue.add(request)
    }

    fun clicReset(view: View) {
        txtNombreMateria?.setText("")
        txtProfesor?.setText("")
        txtHorario?.setText("")
    }
}