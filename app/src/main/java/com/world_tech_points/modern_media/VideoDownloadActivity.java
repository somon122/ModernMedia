package com.world_tech_points.modern_media;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoDownloadActivity extends AppCompatActivity {

    DownloadManager downloadManager;
    long queueId;
    Button downloadBtn,showDownloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_download);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
                {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(queueId);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()){
                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex))
                        {
                            VideoView videoView = findViewById(R.id.videoShow_Show_id);
                            String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            MediaController mediaController = new MediaController(getApplicationContext());
                            mediaController.setAnchorView(videoView);
                            videoView.requestFocus();
                            videoView.setVideoURI(Uri.parse(uriString));
                            videoView.start();



                        }else {
                            Toast.makeText(context, "Not download", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        };



        registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        downloadBtn = findViewById(R.id.download_id);
        showDownloadBtn = findViewById(R.id.downloadShow_id);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://gaana.com/song/tum-bin-11"));

              /*  request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Download")
                        .setDescription("Downloading Video...")
                        .allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalFilesDir(Environment.DIRECTORY_DOWNLOADS,"","${System.currentTimeMillis()}");
*/


                downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                queueId = downloadManager.enqueue(request);

            }
        });

        showDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                startActivity(i);
            }
        });

    }
}
