package com.example.hp.farmapp.FarmData.FarmPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.SpinnerAdapter;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.CalendarPackage.NavGetterSetter.SpinnerData;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Utiltiy.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by user on 15/12/17.
 */

public class FarmImagesActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    Context context;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String pictureImagePath = "";
    private String userChoosenTask;
    Button uploadButton;
    ProgressDialog progressDialog;
    Bitmap myBitmap;
    String returnResp;
    String farm_num,user_num;
    final String API_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/save_farm_img";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
/*        Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_images);
        context = this;

       // Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivity(intent);
        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText("Farm Images");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        TextView capture = (TextView) findViewById(R.id.capturefarmimage);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        uploadButton = (Button) findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "button clicked", Toast.LENGTH_SHORT).show();
                ImageUploadToServerFunction();
//                uploadFileToServer();
            }
        });
        farm_num = SharedPreferencesMethod.getString(context,"farm_num");
        user_num = SharedPreferencesMethod.getString(context,"UserNum");
        Log.e("checkArray","farm and user num"+farm_num+" "+user_num);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(FarmImagesActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FarmImagesActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void onCaptureImageResult(Intent data) {
        File imgFile = new File(pictureImagePath);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myBitmap = getResizedBitmap(myBitmap,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            ImageView myImage = (ImageView) findViewById(R.id.imgoncapture);
            myImage.setImageBitmap(myBitmap);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        myBitmap = BitmapFactory.decodeFile(picturePath);
        myBitmap = getResizedBitmap(myBitmap,720,1080);
        ImageView imageView = (ImageView) findViewById(R.id.imgoncapture);
        imageView.setImageBitmap(myBitmap);
    }

    public void ImageUploadToServerFunction() {
        Log.e("checkArray", "Reached ImageUploadToServerFunction");
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setMessage("Uploading Image");
        progressDialog.setCancelable(false);
        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
//      Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        Log.e("checkArray", "This is convert img" + ConvertImage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,API_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            progressDialog.dismiss();
                            Log.e("checkArray :","this is resp: "+response);
                            Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("checkArray",error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(context,"Failed!! Image too large",Toast.LENGTH_LONG).show();
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e("checkArray",String.valueOf(networkResponse.statusCode));
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("checkArray","timeout error");

                        } else if (error instanceof AuthFailureError) {
                            Log.e("checkArray","authfailure error");

                        } else if (error instanceof ServerError) {
                            Log.e("checkArray","Server error");

                        } else if (error instanceof NetworkError) {
                            //TODO
                            Log.e("checkArray","network error");

                        } else if (error instanceof ParseError) {
                            Log.e("checkArray","parse error");
                            //TODO
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                String USERNUM = "user_num";
                String FARMNUM = "farm_num";
                String IMAGE = "image_data";
                params.put(USERNUM,user_num);
                params.put(FARMNUM,farm_num);
//                Log.e("checkArray","reached params "+ConvertImage);
                params.put(IMAGE,ConvertImage);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}