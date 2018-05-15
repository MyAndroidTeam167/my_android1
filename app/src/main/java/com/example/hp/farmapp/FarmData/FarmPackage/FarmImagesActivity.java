package com.example.hp.farmapp.FarmData.FarmPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.FarmActionReplyActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Utiltiy.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 15/12/17.
 */

public class FarmImagesActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    Context context;
    private int REQUEST_CAMERA1 = 10, SELECT_FILE1 = 1,SELECT_FILE2 = 2,SELECT_FILE3 = 3,SELECT_FILE4 = 4;
    private int REQUEST_CAMERA2 = 11,REQUEST_CAMERA3 = 12,REQUEST_CAMERA4 = 13;
    String pictureImagePath = "";
    private String userChoosenTask;
    Button uploadButton;
    ProgressDialog progressDialog;
    Bitmap myBitmap;
    Bitmap myBitmap2;
    Bitmap myBitmap3;
    Bitmap myBitmap4;
    ArrayList<String> encodedImageList;
    JSONObject jsonObject;
    String audio_encoded,fetch_id,s_task_status,date_of_task,farmer_response,imgs_quantity;

    String returnResp;
    String farm_num,user_num;
    final String API_URL = "http://spade.farm/app/index.php/farmApp/save_farm_img";
    //final String API_NEW_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/save_multiple_farm_imgs";
    final String API_NEW_URL="http://spade.farm/app/index.php/farmCalendar/set_farmer_reponse";
    final String KEY_TOKEN="token4";
    //final String API_NEW_URL="http://192.168.1.13/myfarmapp/index.php/farmCalendar/set_farmer_reponse";
    String ct1;

    ImageView imageView,imageView2,imageView3,imageView4;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE1){
                String result="capture1";
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                onSelectFromGalleryResult(data,result);
                }
            else if (requestCode == SELECT_FILE2){
                String result="capture2";
                // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onSelectFromGalleryResult(data,result);
            }
            else if (requestCode == SELECT_FILE3){
                String result="capture3";
                // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onSelectFromGalleryResult(data,result);
            }
            else if (requestCode == SELECT_FILE4){
                String result="capture4";
                // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onSelectFromGalleryResult(data,result);
            }
            else if (requestCode == REQUEST_CAMERA1){
                String result="capture1";
               // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onCaptureImageResult(data,result);
            }

            else if (requestCode == REQUEST_CAMERA2){
                String result="capture2";
               // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onCaptureImageResult(data,result);
            }
            else if (requestCode == REQUEST_CAMERA3){
                String result="capture3";
               // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onCaptureImageResult(data,result);
            }
            else if (requestCode == REQUEST_CAMERA4){
                String result="capture4";
               // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                onCaptureImageResult(data,result);
            }

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
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_images);
        context = this;



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            farm_num = extras.getString("farm_num");
            user_num = extras.getString("user_num");
            if(extras.getString("audio_file")!=null){
            audio_encoded=extras.getString("audio_file");}
            fetch_id=extras.getString("fetch_id");
            date_of_task=extras.getString("date_of_task");
            s_task_status=extras.getString("s_task_status");
            farmer_response=extras.getString("farmer_reply");
            imgs_quantity=extras.getString("imgs_quntatity");
            Toast.makeText(context, farm_num, Toast.LENGTH_SHORT).show();
        }



        ct1= SharedPreferencesMethod.getString(context,"cctt");

       // Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivity(intent);
        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText("Farm Images");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);



        //Button demo=(Button)findViewById(R.id.demobutt);
        /*demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });*/

        TextView capture = (TextView) findViewById(R.id.capturefarmimage);
        TextView capture2=(TextView)findViewById(R.id.capturefarmimage2);
        TextView capture3=(TextView)findViewById(R.id.capturefarmimage3);
        TextView capture4=(TextView)findViewById(R.id.capturefarmimage4);
         imageView = (ImageView) findViewById(R.id.imgoncapture);
         imageView2 = (ImageView) findViewById(R.id.imgoncapture2);
         imageView3 = (ImageView) findViewById(R.id.imgoncapture3);
         imageView4 = (ImageView) findViewById(R.id.imgoncapture4);

        jsonObject = new JSONObject();
        encodedImageList = new ArrayList<>();


        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture1");
            }
        });
        capture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture2");
            }
        });
        capture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture3");
            }
        });
        capture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture4");
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture1");
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture2");
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture3");
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("capture4");
            }
        });


        if(imgs_quantity.equals("1")){
            capture4.setVisibility(View.GONE);
            capture3.setVisibility(View.GONE);
            capture2.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);
            imageView3.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
        }
        else if(imgs_quantity.equals("2")){
            capture4.setVisibility(View.GONE);
            capture3.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);
            imageView3.setVisibility(View.GONE);
        }
        else if(imgs_quantity.equals("3")){
            capture4.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);
        }
        else{
        }



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
        //farm_num = SharedPreferencesMethod.getString(context,"farm_num");
        //user_num = SharedPreferencesMethod.getString(context,"UserNum");
        Log.e("checkArray","farm and user num"+farm_num+" "+user_num);

    }

   /* @Override
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
    }*/

    private void selectImage(final String onclick) {
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
                        cameraIntent(onclick);

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent(onclick);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent(String onclick) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        if(onclick.equals("capture1")) {
            startActivityForResult(intent, REQUEST_CAMERA1);
        }
        else if(onclick.equals("capture2")){
            startActivityForResult(intent, REQUEST_CAMERA2);
        }else if(onclick.equals("capture3")){
            startActivityForResult(intent, REQUEST_CAMERA3);
        }
        else {
            startActivityForResult(intent, REQUEST_CAMERA4);
        }
    }

    private void galleryIntent(String onclick) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.putExtra("result",onclick);
        if(onclick.equals("capture1")) {
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE1);
        }
        else if(onclick.equals("capture2")){
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE2);
        }else if(onclick.equals("capture3")){
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE3);
        }
        else {
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE4);
        }
    }

    private void onCaptureImageResult(Intent data,String onclick) {
//        Bundle extras = data.getExtras();
        //String onclick=extras.getString("result");
        File imgFile = new File(pictureImagePath);
        if (imgFile.exists()) {
            if(onclick.equals("capture1")){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myBitmap = getResizedBitmap(myBitmap,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            imageView.setImageBitmap(myBitmap);}
            else if(onclick.equals("capture2")){
                myBitmap2 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myBitmap2 = getResizedBitmap(myBitmap2,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
                imageView2.setImageBitmap(myBitmap2);
            }else if(onclick.equals("capture3")){
                myBitmap3 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myBitmap3 = getResizedBitmap(myBitmap3,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
                imageView3.setImageBitmap(myBitmap3);
            }else{
                myBitmap4 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myBitmap4 = getResizedBitmap(myBitmap4,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
                imageView4.setImageBitmap(myBitmap4);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data,String onclick) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        if(onclick.equals("capture1")){
            myBitmap = BitmapFactory.decodeFile(picturePath);
            myBitmap = getResizedBitmap(myBitmap,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            imageView.setImageBitmap(myBitmap);}
        else if(onclick.equals("capture2")){
            myBitmap2 = BitmapFactory.decodeFile(picturePath);
            myBitmap2 = getResizedBitmap(myBitmap2,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            imageView2.setImageBitmap(myBitmap2);
        }else if(onclick.equals("capture3")){
            myBitmap3 = BitmapFactory.decodeFile(picturePath);
            myBitmap3 = getResizedBitmap(myBitmap3,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            imageView3.setImageBitmap(myBitmap3);
        }else{
            myBitmap4 = BitmapFactory.decodeFile(picturePath);
            myBitmap4 = getResizedBitmap(myBitmap4,720,1080);
//            Bitmap scaledBitmap = scaleDown(realImage, MAX_IMAGE_SIZE, true);
            imageView4.setImageBitmap(myBitmap4);
        }
    }

    public void ImageUploadToServerFunction() {
        encodedImageList.clear();
        Log.e("checkArray", "Reached ImageUploadToServerFunction");
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setMessage("Uploading Image");
        progressDialog.setCancelable(false);
 /*       ByteArrayOutputStream byteArrayOutputStreamObject1;
        byteArrayOutputStreamObject1 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStreamObject2;
        byteArrayOutputStreamObject2 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStreamObject3;
        byteArrayOutputStreamObject3 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStreamObject4;
        byteArrayOutputStreamObject4 = new ByteArrayOutputStream();*/
//      Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
        if(imgs_quantity.equals("1")){
            ByteArrayOutputStream byteArrayOutputStreamObject1;
            byteArrayOutputStreamObject1 = new ByteArrayOutputStream();

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject1);

            final String ConvertImage1 = Base64.encodeToString(byteArrayOutputStreamObject1.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage1);
            Log.e("image_encoded: ",ConvertImage1);

        }
        else if(imgs_quantity.equals("2")){
            ByteArrayOutputStream byteArrayOutputStreamObject1;
            byteArrayOutputStreamObject1 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject2;
            byteArrayOutputStreamObject2 = new ByteArrayOutputStream();

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject1);
            myBitmap2.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject2);

            final String ConvertImage1 = Base64.encodeToString(byteArrayOutputStreamObject1.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage1);
            final String ConvertImage2 = Base64.encodeToString(byteArrayOutputStreamObject2.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage2);



        }
        else if(imgs_quantity.equals("3")){
            ByteArrayOutputStream byteArrayOutputStreamObject1;
            byteArrayOutputStreamObject1 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject2;
            byteArrayOutputStreamObject2 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject3;
            byteArrayOutputStreamObject3 = new ByteArrayOutputStream();

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject1);
            myBitmap2.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject2);
            myBitmap3.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject3);

            final String ConvertImage1 = Base64.encodeToString(byteArrayOutputStreamObject1.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage1);
            final String ConvertImage2 = Base64.encodeToString(byteArrayOutputStreamObject2.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage2);
            final String ConvertImage3 = Base64.encodeToString(byteArrayOutputStreamObject3.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage3);
        }
        else{
            ByteArrayOutputStream byteArrayOutputStreamObject1;
            byteArrayOutputStreamObject1 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject2;
            byteArrayOutputStreamObject2 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject3;
            byteArrayOutputStreamObject3 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStreamObject4;
            byteArrayOutputStreamObject4 = new ByteArrayOutputStream();
//
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject1);
            myBitmap2.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject2);
            myBitmap3.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject3);
            myBitmap4.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject4);

            final String ConvertImage1 = Base64.encodeToString(byteArrayOutputStreamObject1.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage1);
            final String ConvertImage2 = Base64.encodeToString(byteArrayOutputStreamObject2.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage2);
            final String ConvertImage3 = Base64.encodeToString(byteArrayOutputStreamObject3.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage3);
            final String ConvertImage4 = Base64.encodeToString(byteArrayOutputStreamObject4.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(ConvertImage4);
        }

       /* byte[] byteArrayVar1 = byteArrayOutputStreamObject1.toByteArray();
        byte[] byteArrayVar2 = byteArrayOutputStreamObject2.toByteArray();
        byte[] byteArrayVar3 = byteArrayOutputStreamObject3.toByteArray();
        byte[] byteArrayVar4 = byteArrayOutputStreamObject4.toByteArray();
*/
       /* final String ConvertImage1 = Base64.encodeToString(byteArrayOutputStreamObject1.toByteArray(), Base64.DEFAULT);
        encodedImageList.add(ConvertImage1);
        final String ConvertImage2 = Base64.encodeToString(byteArrayOutputStreamObject2.toByteArray(), Base64.DEFAULT);
        encodedImageList.add(ConvertImage2);
        final String ConvertImage3 = Base64.encodeToString(byteArrayOutputStreamObject3.toByteArray(), Base64.DEFAULT);
        encodedImageList.add(ConvertImage3);
        final String ConvertImage4 = Base64.encodeToString(byteArrayOutputStreamObject4.toByteArray(), Base64.DEFAULT);
        encodedImageList.add(ConvertImage4);*/

       /* Log.e("Message checkArray", "This is convert img" + ConvertImage1);
        Log.e("Message checkArray", "This is convert img" + ConvertImage2);
        Log.e("Message checkArray", "This is convert img" + ConvertImage3);
        Log.e("Message checkArray", "This is convert img" + ConvertImage4);
*/


        final JSONArray jsonArray = new JSONArray();

        if (encodedImageList.isEmpty()){
            Toast.makeText(this, "Please select some images first.", Toast.LENGTH_SHORT).show();
            return;
        }
else {
            for (String encoded : encodedImageList) {
                jsonArray.put(encoded);
            }

           // Log.e("JSOnARRAy", jsonArray.toString());

            try {
               // jsonObject.put("name", "myImages");
                jsonObject.put("imageList", jsonArray);
                jsonObject.put("user_num",user_num);
                jsonObject.put("farm_num",farm_num);
                jsonObject.put("audio_file",audio_encoded);
                jsonObject.put("task_done_status",s_task_status);
                jsonObject.put("farmer_reply",farmer_response);
                jsonObject.put("data_id",fetch_id);
                jsonObject.put("data_task_date",date_of_task);
                jsonObject.put("by_farmer","Y");
                jsonObject.put("by_admin","N");
                jsonObject.put("by_inspector","N");
                jsonObject.put(KEY_TOKEN,ct1);

            } catch (JSONException e) {
                Log.e("JSONObject Here", e.toString());
            }

            Log.e("TAG",jsonArray.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_NEW_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.e("Message from server", jsonObject.toString());
                            //Log.e("Message from server", jsonArray.toString());
                            try {
                                progressDialog.dismiss();
                                String imglist=jsonObject.getString("msg");
                                Log.e("Message_img",imglist);
                                Toast.makeText(context,imglist, Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(context, FarmActionReplyActivity.class);
                                intent.putExtra("id",fetch_id);
                                intent.putExtra("task_date",date_of_task);
                                intent.putExtra("type","pager");
                                startActivity(intent);
                                finish();

                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(context,"Some error occum=red Please Re-upload images", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Message server er", volleyError.toString());
                    Toast.makeText(getApplication(), "Error "+volleyError.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200 * 30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
        /*break;*/


       /* StringRequest stringRequest = new StringRequest(Request.Method.POST,API_URL,
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
                params.put(IMAGE,ConvertImage1);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);*/
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