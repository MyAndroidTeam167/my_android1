package com.example.hp.farmapp.Response;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.Login.LoginVerificationActivity;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Utiltiy.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImageActivity extends BaseActivity implements View.OnClickListener {

    private static final int PICK_FROM_FILE = 2;
    private static final int CAMERA_REQUEST = 1;

    Button imgbutt;
    ImageView imgprvw;
    String Adress="";

    Float Latitude, Longitude;
     boolean valid = false;



    private String picturePath;
    Button logout;
    public static byte[] ByteArray;
    final String TAG="look here";

    Context context;
    TextView Exif,latlong;
     String pictureImagePath = "";



    String imagefile;


    Boolean login;
    Button fillprofiledemo,profileacdemo,profileinfoact;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
   // private Button btnSelect;
    //private ImageView ivImage;
    private String userChoosenTask;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

    }

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
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        context=this;

/*
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
       // if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
         //       !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
*/

       /* Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
*/


        imgbutt=(Button)findViewById(R.id.buttimage);
       // latlong=(TextView)findViewById(R.id.latlong);
       // imgprvw=(ImageView)findViewById(R.id.imgprvw);
        imgprvw=(ImageView)findViewById(R.id.imgprvw);
        logout=(Button)findViewById(R.id.testlogout);
        fillprofiledemo=(Button)findViewById(R.id.profiledemo);
        profileacdemo=(Button)findViewById(R.id.profileactivitydemo);
        profileinfoact=(Button)findViewById(R.id.fillprofiledemo) ;


        fillprofiledemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,FillProfileActivity.class);
                startActivity(intent);

            }
        });

        profileacdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(context,ProfileActivity.class);
                Intent launchNextActivity;
                launchNextActivity = new Intent(context,LoginVerificationActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
                startActivity(launchNextActivity);

            }
        });

        profileinfoact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,FarmAddActivity.class);
                startActivity(intent);

            }
        });



        Exif = (TextView)findViewById(R.id.exif);
       // SharedPreferences pref = getApplicationContext().getSharedPreferences("sharedPref", 0); // 0 - for private mode


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesMethod.clear(context);
                SharedPreferencesMethod.setBoolean(context,"Login",false);
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);

            }
        });
       // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        imgbutt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectImage();

           }
       });


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
            /*case 1:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }*/
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ImageActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      //  intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }



    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void cameraIntent()
    {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void onCaptureImageResult(Intent data) {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        File imgFile = new  File(pictureImagePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.imgprvw);
            myImage.setImageBitmap(myBitmap);
            Exif.setText(ReadExif(imgFile.getAbsolutePath()));

        }

       /* Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 99, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                 "/myfarm/"+".jpg");
        Uri imageUri =Uri.fromFile(destination);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newconverturi=String.valueOf(imageUri);
        newconverturi= newconverturi.substring(7, newconverturi.length());
        imageUri=Uri.parse(newconverturi);

//        String s = getRealPathFromURI_API19(context,imageUri);
        imagefile="/myfarm/"+".jpg";
        imgprvw.setImageBitmap(thumbnail);
        //editText1.setText(s);
      //  Uri uri =getImageUri(context,thumbnail);

       // Toast.makeText(ImageActivity.this, s, Toast.LENGTH_SHORT).show();*/
    }



    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        ImageView imageView = (ImageView) findViewById(R.id.imgprvw);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        Exif.setText(ReadExif(picturePath));


       /* Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                Log.e(TAG, String.valueOf(bm));

                //Uri selectedImageUri = data.getData();

               String s = getRealPathFromURI_API19(context,data.getData());

                Exif.setText(ReadExif(s));

                Toast.makeText(ImageActivity.this, s, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        //imgprvw.setImageBitmap(bm);
    }






    String ReadExif(String file){
        String exif="Exif: " + file;
        try {
            ExifInterface exifInterface = new ExifInterface(file);

            String attrLATITUDE="";
             attrLATITUDE = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)+"";
/*            String attrLATITUDE_REF = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String attrLONGITUDE = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String attrLONGITUDE_REF = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

            if((attrLATITUDE !=null)
                    && (attrLATITUDE_REF !=null)
                    && (attrLONGITUDE != null)
                    && (attrLONGITUDE_REF !=null))
            {
                valid = true;

                if(attrLATITUDE_REF.equals("N")){
                    Latitude = convertToDegree(attrLATITUDE);
                }
                else{
                    Latitude = 0 - convertToDegree(attrLATITUDE);
                }

                if(attrLONGITUDE_REF.equals("E")){
                    Longitude = convertToDegree(attrLONGITUDE);
                }
                else{
                    Longitude = 0 - convertToDegree(attrLONGITUDE);
                }


                //Toast.makeText(ImageActivity.this, Float.toString(Latitude)+Float.toString(Longitude), Toast.LENGTH_LONG).show();
               Adress= getCompleteAddressString(Latitude,Longitude);
                //latlong.setText(Float.toString(Latitude)+" "+Float.toString(Longitude));
                   // latlong.setText("Address : "+Adress);
                //Toast.makeText(ImageActivity.this, "add"+Adress, Toast.LENGTH_SHORT).show();
            }
*/





            exif += "\nIMAGE_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            exif += "\nIMAGE_WIDTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            exif += "\n DATETIME: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            exif += "\n TAG_MAKE: " + exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            exif += "\n TAG_MODEL: " + exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            exif += "\n TAG_ORIENTATION: " + exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            exif += "\n TAG_WHITE_BALANCE: " + exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            exif += "\n TAG_FOCAL_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            exif += "\n TAG_FLASH: " + exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            exif += "\nGPS related:";
            exif += "\n TAG_GPS_DATESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
            exif += "\n TAG_GPS_TIMESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            exif += "\n TAG_GPS_LATITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exif += "\n TAG_GPS_LATITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exif += "\n TAG_GPS_LONGITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            exif += "\n TAG_GPS_LONGITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            exif += "\n TAG_GPS_PROCESSING_METHOD: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);

            if(attrLATITUDE.equals("null"))
            {
                Toast.makeText(ImageActivity.this, "Please Take Image from Camera and then pick it from Gallery", Toast.LENGTH_SHORT).show();
                selectImage();
            }
            else{
                Toast.makeText(ImageActivity.this, "Image successfully loaded", Toast.LENGTH_SHORT).show();

            }


          //  Toast.makeText(ImageActivity.this, "finished", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(ImageActivity.this,
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }



    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }




    private Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    };

    public boolean isValid()
    {
        return valid;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return (String.valueOf(Latitude)
                + ", "
                + String.valueOf(Longitude));
    }

    public int getLatitudeE6(){
        return (int)(Latitude*1000000);
    }

    public int getLongitudeE6(){
        return (int)(Longitude*1000000);
    }



    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }



    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(String... params) {




          /*  LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}

            if(!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage(*//*context.getResources().getString(R.string.gps_network_not_enabled*//*"network not enabled");
                dialog.setPositiveButton(*//*context.getResources().getString(R.string.open_location_settings*//*"openlocationsetting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(myIntent);
                        //get gps
                    }
                });
                dialog.setNegativeButton(*//*context.getString(R.string.Cancel*//*"Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
*/

            return "";
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

                  }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }




}




