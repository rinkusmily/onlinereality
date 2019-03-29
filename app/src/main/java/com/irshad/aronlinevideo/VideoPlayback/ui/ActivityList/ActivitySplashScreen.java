/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of QUALCOMM Incorporated, registered in the United States 
and other countries. Trademarks of QUALCOMM Incorporated are used with permission.
===============================================================================*/

package com.irshad.aronlinevideo.VideoPlayback.ui.ActivityList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.irshad.aronlinevideo.SampleApplication.vuforia.GetAllTargets;
import com.irshad.aronlinevideo.SampleApplication.vuforia.PostNewTarget;
import com.irshad.aronlinevideo.SampleApplication.vuforia.Summary;
import com.irshad.aronlinevideo.SampleApplication.vuforia.SummeryDetails;
import com.irshad.aronlinevideo.VideoPlayback.R;
import com.irshad.aronlinevideo.VideoPlayback.app.VideoPlayback.VideoPlayback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;


public class ActivitySplashScreen extends Activity
{
    
    private static long SPLASH_MILLIS = 1000;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.new_adder);


    /*    Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.icon);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();*/


      /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();



        PostNewTarget postNewTarget = new PostNewTarget(Base64.encodeToString(bitMapData,
                Base64.NO_WRAP),"TestImage");
        postNewTarget.postTargetThenPollStatus();
        try {
             new GetAllTargets().main(new String[0]);
             new Summary().main(new String[0]);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
      /*  try {
            new SummeryDetails().main("087347aa639c44638389dd06465593f1");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        postNewTarget.postTargetThenPollStatus();
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
            R.layout.splash_screen, null, false);
        
        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                
//                Intent intent = new Intent(ActivitySplashScreen.this,
//                    AboutScreen.class);
//                intent.putExtra("ACTIVITY_TO_LAUNCH",
//                    "app.VideoPlayback.VideoPlayback");
//                intent.putExtra("ABOUT_TEXT_TITLE", "Video Playback");
//                intent.putExtra("ABOUT_TEXT", "VideoPlayback/VP_about.html");
                    Intent intent = new Intent(ActivitySplashScreen.this,
                    VideoPlayback.class);
                startActivity(intent);
                
            }
            
        }, SPLASH_MILLIS);
    }
    
}
