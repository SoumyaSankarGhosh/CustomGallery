package com.example.kiit.mygallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by kiit on 17-12-2016.
 */
public class UploadActivity extends AppCompatActivity {

    SparseBooleanArray mSparseBooleanArray;

    private DisplayImageOptions options;

    private ImgAdapter imageAdapter;

    private ArrayList<String> imageUrls1;

    GridView gridView2;

    LinearLayout changeLayout2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
      //
        mSparseBooleanArray = new SparseBooleanArray();
        imageUrls1 = getIntent().getExtras().getStringArrayList("NIMAGE");
      /*  for (int i=0;i<imageUrls1.size();i++){
               Toast.makeText(getApplicationContext(),"Selected"+imageUrls1,Toast.LENGTH_SHORT).show();
        }*/

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();

        imageAdapter = new ImgAdapter(this, imageUrls1);
        changeLayout2 = (LinearLayout)findViewById(R.id.changeLayout2);

        gridView2 = (GridView) findViewById(R.id.PhoneImageGrid2);
        gridView2.setAdapter(imageAdapter);


    }

   /* public void btnChoosePhotosClick2(View v) {
        ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
        Toast.makeText(UploadActivity.this,"Total photos selected: " + selectedItems.size(),Toast.LENGTH_SHORT).show();
        Log.d(MainActivity.class.getSimpleName(),"Selected Items: " + selectedItems.toString());
    }*/

    public class ImgAdapter extends BaseAdapter {
        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;

        public ImgAdapter(Context context, ArrayList<String> imageList) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mList = new ArrayList<String>();
            this.mList = imageList;

        }

        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for (int i = 0; i < mList.size(); i++) {
                if (mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                }
            }
            return mTempArry;
        }

        @Override
        public int getCount() {
            return imageUrls1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.griditem1, null);
            }


            final ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
            ImageLoader.getInstance().displayImage("file://" + imageUrls1.get(position), imageView2, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Animation anim = AnimationUtils.loadAnimation(UploadActivity.this, R.anim.fade_in);
                    imageView2.setAnimation(anim);
                    anim.start();
                }
            });


            return convertView;
        }
    }

}
