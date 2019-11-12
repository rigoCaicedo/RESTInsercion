package caicedo2k18.webhostapp.consumiendoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private Button mButtonDo;
    private Button botonGuardar;
    private TextView mTextView;
    private EditText identificacion, nombre, curso, nota1, nota2, nota3;
    // private String JSONURLpost = "http://52.168.76.48/estudianteapi/read.php";

    private String JSONURLget = "http://caicedo2k18.000webhostapp.com/estudianteAPI/read.php";
    private String JSONURLpost = "http://caicedo2k18.000webhostapp.com/estudianteAPI/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        // Get the widget reference from XML layout
        mButtonDo = (Button) findViewById(R.id.btn_do);
        botonGuardar = (Button) findViewById(R.id.btnGuardar);
        mTextView = (TextView) findViewById(R.id.tv);

        identificacion = (EditText)findViewById(R.id.txtIdent);

        nombre = (EditText)findViewById(R.id.txtNombre);

        curso = (EditText)findViewById(R.id.txtCurso);

        nota1 = (EditText)findViewById(R.id.txtNota1);

        nota2 = (EditText)findViewById(R.id.txtNota2);

        nota3 = (EditText)findViewById(R.id.txtNota3);



        // Set a click listener for button widget
        mButtonDo.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                // Empty the TextView
                mTextView.setText("");


                // Initialize a new RequestQueue instance
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                JsonArrayRequest request = new JsonArrayRequest(JSONURLget, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject objestudent = jsonArray.getJSONObject(i);
                                String ident = objestudent.getString("identificacion");
                                String nomb = objestudent.getString("nombre");
                                String cur = objestudent.getString("curso");
                                String nuno = objestudent.getString("nota1");
                                String ndos = objestudent.getString("nota2");
                                String ntres = objestudent.getString("nota3");
                                mTextView.append(ident + " " + nomb + " " + cur
                                        + " " + nuno + " " + ndos + " " + ntres+ "\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
                requestQueue.add(request);
            }
        });

        // Set a click listener for button widget
        botonGuardar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                // Empty the TextView
                mTextView.setText("");
                String eident = identificacion.getText().toString();

                String enombre = nombre.getText().toString();

                String ecurso = curso.getText().toString();

                String enota1 = nota1.getText().toString();

                String enota2 = nota2.getText().toString();

                String enota3 = nota3.getText().toString();
                if (eident.equals("")||enombre.equals("")||ecurso.equals("")||enota1.equals("")||enota2.equals("")||enota3.equals("")) {

                    Toast.makeText(MainActivity.this,"No se permiten campos acios", Toast.LENGTH_SHORT).show();

                }else{

                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("id", "9");
                        jsonBody.put("identificacion", eident);
                        jsonBody.put("nombre", enombre);
                        jsonBody.put("curso",  ecurso);
                        jsonBody.put("nota1",  enota1);
                        jsonBody.put("nota2",  enota2);
                        jsonBody.put("nota3",  enota3);
                        final String requestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSONURLpost, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", error.toString());
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                    // can get more details such as response.headers
                                }
                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                            }
                        };

                        requestQueue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


    }




}