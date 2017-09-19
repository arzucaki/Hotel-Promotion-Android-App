/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/
package com.mobile.sherwoodhotels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;

import com.mobile.sherwoodhotels.utils.Utils;

import java.nio.ByteBuffer;

import veg.mediaplayer.sdk.MediaPlayerConfig;

public class Camera extends Activity implements veg.mediaplayer.sdk.MediaPlayer.MediaPlayerCallback
{

    private static ProgressDialog mDialog;
    private static ProgressDialog progressDialog;

    VideoView videoView ;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder vidHolder;
    private SurfaceView vidSurface;
    String vidAddress = Utils.selectedCamLink;

    private veg.mediaplayer.sdk.MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //VideoView cam=(VideoView)findViewById(R.id.videoView);
        //Uri uriURL=Uri.parse(Utils.selectedCamLink);
        //rtsp://192.168.1.126/user=admin_password=qaz_123!_channel=1_stream=0.dsp


        //Uri uriURL=Uri.parse("rtsp://admin:12345@192.168.1.76/");
        //Uri uriURL=Uri.parse("rtsp://192.168.1.77/user=admin_password=qaz_123!_channel=1_stream=0.dsp");


        //Uri uriURL=Uri.parse("rtsp://admin:qaz_123!@192.168.1.77:554/video.h264");
        //cam.setVideoURI(uriURL);
        //cam.requestFocus();
        //cam.start();

        //videoView = (VideoView) findViewById(R.id.videoView);
        //progressDialog = ProgressDialog.show(Camera.this, "", getString(R.string.buffering), true);
        //progressDialog.setCancelable(true);
        //PlayVideo();

        //vidSurface = (SurfaceView) findViewById(R.id.surfView);
        //vidHolder = vidSurface.getHolder();
        //vidHolder.addCallback(this);


        player=(veg.mediaplayer.sdk.MediaPlayer)findViewById(R.id.player);
        player.getSurfaceView().setZOrderOnTop(true);

        mDialog=new ProgressDialog(Camera.this);
        mDialog.setMessage(getString(R.string.buffering));
        mDialog.show();

        if(player.getConfig()!=null)
        {
            player.getConfig().setConnectionUrl(Utils.selectedCamLink);

            MediaPlayerConfig config=new MediaPlayerConfig();
            config.setConnectionUrl(player.getConfig().getConnectionUrl());
            config.setConnectionNetworkProtocol(-1);
            config.setConnectionDetectionTime(2000);
            config.setConnectionBufferingTime(5);
            config.setDecodingType(1);
            config.setRendererType(1);
            config.setSynchroEnable(1);
            config.setSynchroNeedDropVideoFrames(1);
            config.setEnableColorVideo(1);
            config.setDataReceiveTimeout(30000);
            config.setNumberOfCPUCores(2);

            player.Open(config, Camera.this);
        }

    }

    private void PlayVideo()
    {
        try
        {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);

            Uri video = Uri.parse(Utils.selectedCamLink);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                public void onPrepared(MediaPlayer mp)
                {
                    progressDialog.dismiss();
                    videoView.start();
                }
            });

        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            System.out.println("Video Play Error :"+e.toString());
            finish();
        }

    }

    @Override
    public int Status(int i) {
        if (i == veg.mediaplayer.sdk.MediaPlayer.PlayerNotifyCodes.PLP_PLAY_SUCCESSFUL.ordinal())
            mDialog.dismiss();
        return 0;
    }

    @Override
    public int OnReceiveData(ByteBuffer byteBuffer, int i, long l) {
        return 0;
    }
}
