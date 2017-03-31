package com.example.user.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mylibrary.src.MyView;
import com.thebrownarrow.permissionhelper.ActivityManagePermission;
import com.thebrownarrow.permissionhelper.PermissionResult;
import com.thebrownarrow.permissionhelper.PermissionUtils;
import com.tonyodev.fetch.Fetch;
import com.tonyodev.fetch.listener.FetchListener;
import com.tonyodev.fetch.request.Request;

public class MainActivity extends ActivityManagePermission {

    //This is a branch for module testing

    Fetch fetch;
    public static String file_url = "http://vhost2.hansenet.de/10_mb_file.bin.gz";
    public static String file_path = Environment.getExternalStorageDirectory().toString() + "/sdcard/myapp/";
    public static String file_name = "10_mb_file.bin.gz";
    long downloadId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        View v = new MyView(this);
        setContentView(v);

        fetch = Fetch.getInstance(this);

        //check permision granted
        boolean isPermissionGranted = isPermissionsGranted(MainActivity.this, new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, PermissionUtils.Manifest_READ_EXTERNAL_STORAGE});

        if (!isPermissionGranted) {
            askCompactPermissions(new String[]{PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE}, new PermissionResult() {
                @Override
                public void permissionGranted() {
                    //permission granted
                    //replace with your action
                    Toast.makeText(getApplicationContext(), "granted", Toast.LENGTH_SHORT).show();
                    Request request = new Request(file_url, file_path, file_name);
                    downloadId = fetch.enqueue(request);
                }

                @Override
                public void permissionDenied() {
                    //permission denied
                    //replace with your action
                    Toast.makeText(getApplicationContext(), "denied", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void permissionForeverDenied() {
                    // user has check 'never ask again'
                    // you need to open setting manually
                    openSettingsApp(MainActivity.this);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "already granted", Toast.LENGTH_SHORT).show();
            Request request = new Request(file_url, file_path, file_name);
            downloadId = fetch.enqueue(request);
        }

        fetch.addFetchListener(new FetchListener() {

            @Override
            public void onUpdate(long id, int status, int progress, long downloadedBytes, long fileSize, int error) {

                if (downloadId == id && status == Fetch.STATUS_DONE) {
                    fetch.release();
                    Toast.makeText(getApplicationContext(), "download completed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
