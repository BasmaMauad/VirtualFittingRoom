package com.example.barmsss;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class customer extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView customerImageView;
    Button btnList;
    private Button button3;
    String encodedImage = "";
    String url = "https://ancient-eyrie-13107.herokuapp.com/";
    String human = "sort bni2dm";
    String clothes = "sort clothes";
    String category = "blause";
    public static SQLiteHelper sqLiteHelper;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        init();
        sqLiteHelper = new SQLiteHelper(this, "CLOTHESDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CLOTHES(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, type VARCHAR, image BLOB)");
        Button customerButton = (Button) findViewById(R.id.customerButton);
        customerImageView = (ImageView) findViewById(R.id.customerImageView);
//        Button btnList = (Button) findViewById(R.id.btnList);
        sqLiteHelper = new SQLiteHelper(this, "CLOTHESDB.sqlite", null, 1);
//         DatabaseAccess databaseAccess=DatabaseAccess.getInstance(this);
//         DatabaseAccess.open();
        //disable button if user has no camera
        if(!hasCamera())
            customerButton.setEnabled(false);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customer.this, ClothesList.class);
                startActivity(intent);
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert_post();
                // ...
// Instantiate the RequestQueue.
                /*final RequestQueue queue = Volley.newRequestQueue(customer.this);
                //String url ="https://aqueous-beyond-38350.herokuapp.com/"; //L url l 3leh l response

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                /////////// LOOK HERE YA WLAAAD//////////////.
                                //textView.setText("Response is: "+ response.substring(0,10)); // da kda bizhr awl 10 7rof mn l response
                                Toast.makeText(getApplication(),response.substring(0,10),Toast.LENGTH_SHORT).show();
                                byte[] decodedString = Base64.decode(response, Base64.DEFAULT); // da w; t7to by7wlo mn base64 l sora
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                customerImageView.setImageBitmap(decodedByte); // da 3shan azhr l sora fl image view

                                queue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(customer.this,error+"",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        queue.stop();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);*/
            }
        });

    }
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            //photo de kda b2a fiha l sora l at5dd ashof b2a ana 3iza a3ml biha a hna msln hi3rda f image view
            customerImageView.setImageBitmap(photo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    private void init(){

        btnList = (Button) findViewById(R.id.btnList);

    }
    private void Insert_post(){
        /*StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url_post,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(store.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("language",input);
                params.put("framework",input2);
                return params;
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(store.this);
        requestQueue2.add(stringRequest2);*/
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplication(),response.substring(0,10),Toast.LENGTH_SHORT).show();
                        byte[] decodedString = Base64.decode(response, Base64.DEFAULT); // da w; t7to by7wlo mn base64 l sora
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        customerImageView.setImageBitmap(decodedByte);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(customer.this,error+"",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                human = encodedImage;
                clothes = ClothesList.encoded;
                category = ClothesList.type;
                params.put("human", human);
                params.put("clothes", clothes);
                params.put("category", category);
                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(customer.this);
        requestQueue2.add(jsonObjRequest);
    }
}
