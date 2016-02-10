package sckqn8.locateandpin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ListOptions extends AppCompatActivity {
    public int TP=1;
    public int PICK_IMAGE_REQUEST=1;
    ImageView profileImg;
    Uri chosenImg;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profileImg = (ImageView) findViewById(R.id.dp);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void CapturePhoto(View v) {
        Intent capturePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capturePhoto, TP);
    }

    public void OpenGallery(View v) {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TP && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");

            profileImg.setImageBitmap(photo);

        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Let's read picked image data - its URI
            chosenImg = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(chosenImg, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            imgPath = imagePath;
            // Now we need to set the GUI ImageView data with data read from the picked file.
            profileImg.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            Log.d("pick", imgPath);
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();


        } else {
            //No activityResponse
        }

    }

    public void LocateOnMap(View v) {
        Intent redirect = new Intent(ListOptions.this, Pin.class);
        String filePathAbsolute = imgPath;
        redirect.putExtra("imgURLOnPhone", filePathAbsolute);
        startActivity(redirect);
    }

}
