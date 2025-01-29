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

class Estudiantes : AppCompatActivity() {
    var txtNombre: EditText? = null
    var txtApellidos: EditText? = null
    var txtGrado: EditText? = null
    var txtCurso: EditText? = null
    var txtCodigo: EditText? = null
    var tbEstudiantes: TableLayout? = null
    var idGlobal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)
        txtNombre = findViewById(R.id.txtNombreEstudiante)
        txtApellidos = findViewById(R.id.txtApellidoEstudiante)
        txtGrado = findViewById(R.id.txtGradoEstudiante)
        txtCurso = findViewById(R.id.txtCursoEstudiante)
        txtCodigo = findViewById(R.id.txtCodigoEstudiante)
        tbEstudiantes = findViewById(R.id.tbEstudiantes)
        cargaTabla()
    }

    fun cargaTabla() {
        tbEstudiantes?.removeAllViews()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ESTUDIANTE/registros.php"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val registro = LayoutInflater.from(this).inflate(R.layout.table_row_estudiantes, null, false)
                        val colNombre = registro.findViewById<View>(R.id.colNombre) as TextView
                        val colApellidos = registro.findViewById<View>(R.id.colApellidos) as TextView
                        val colGrado = registro.findViewById<View>(R.id.colGrado) as TextView
                        val colCurso = registro.findViewById<View>(R.id.colCurso) as TextView
                        val colCodigo = registro.findViewById<View>(R.id.colCodigo) as TextView
                        val colEditar = registro.findViewById<View>(R.id.colEditarE)
                        val colBorrar = registro.findViewById<View>(R.id.colBorrarE)
                        colNombre.text = jsonObject.getString("nombre")
                        colApellidos.text = jsonObject.getString("apellido")
                        colGrado.text = jsonObject.getString("grado")
                        colCurso.text = jsonObject.getString("curso")
                        colCodigo.text = jsonObject.getString("codigo")
                        colEditar.id = jsonObject.getString("id_estudiante").toInt()
                        colBorrar.id = jsonObject.getString("id_estudiante").toInt()
                        tbEstudiantes?.addView(registro)
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
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ESTUDIANTE/editar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "El estudiante se actualizó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al actualizar estudiante $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_estudiante"] = idGlobal!!
                parametros["nombre"] = txtNombre?.text.toString()
                parametros["apellido"] = txtApellidos?.text.toString()
                parametros["grado"] = txtGrado?.text.toString()
                parametros["curso"] = txtCurso?.text.toString()
                parametros["codigo"] = txtCodigo?.text.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clicTablaEditar(view: View) {
        idGlobal = view.id.toString()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ESTUDIANTE/registro.php?id_estudiante=${idGlobal}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                txtNombre?.setText(response.getString("nombre"))
                txtApellidos?.setText(response.getString("apellido"))
                txtGrado?.setText(response.getString("grado"))
                txtCurso?.setText(response.getString("curso"))
                txtCodigo?.setText(response.getString("codigo"))
            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun clicTablaBorrar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ESTUDIANTE/borrar.php"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "El estudiante se eliminó de forma exitosa", Toast.LENGTH_SHORT).show()
                cargaTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al eliminar estudiante $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros["id_estudiante"] = view.id.toString()
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clickBtnInsertar(view: View) {
        val url = "http://192.168.236.155/API_REST_ASISTENCIA/ESTUDIANTE/insertar.php"
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
                params["nombre"] = txtNombre?.text.toString()
                params["apellido"] = txtApellidos?.text.toString()
                params["grado"] = txtGrado?.text.toString()
                params["curso"] = txtCurso?.text.toString()
                params["codigo"] = txtCodigo?.text.toString()
                return params
            }
        }
        queue.add(request)
    }

    fun clicReset(view: View) {
        txtNombre?.setText("")
        txtApellidos?.setText("")
        txtGrado?.setText("")
        txtCurso?.setText("")
        txtCodigo?.setText("")
    }
}