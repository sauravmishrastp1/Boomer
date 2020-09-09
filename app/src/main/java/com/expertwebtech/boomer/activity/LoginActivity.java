package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    private ProgressBar progressBar;
    private String username,password,type;
    private EditText usernameEt,passEt;
    private TextView register_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        progressBar=findViewById(R.id.progressbar);
        usernameEt=findViewById(R.id.usernameEt);
        passEt=findViewById(R.id.passEt);
        register_txt = findViewById(R.id.register_usertxt);
        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn=findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              username=usernameEt.getText().toString();
              password=passEt.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameEt.setError("Required");
                    usernameEt.requestFocus();

                } else if (TextUtils.isEmpty(password)) {
                    passEt.setError("Required");
                    passEt.requestFocus();
                }  else {
                    login();
                }
            }
        });
    }


    private void login()
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);

                            String status = obj.getString("status_code");

                            if (status.equals("200")) {
                                JSONObject userJson = obj.getJSONObject("data");
                                String userId = userJson.getString("id");
                                String username = userJson.getString("name");
                                String userEmail = userJson.getString("email");
                                //String userPhone = userJson.getString("phone");
                                String user_type = userJson.getString("user_type");
                               // String image = userJson.getString("image");

                                    User user=new User(userId,username,userEmail,user_type,"","","");
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();

                                progressBar.setVisibility(View.GONE);


                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                // params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("access-token", ACCESS_TOKEN);
//                return params;
//            }

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
