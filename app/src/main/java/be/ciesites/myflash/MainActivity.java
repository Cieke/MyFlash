package be.ciesites.myflash;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private boolean hasFlash;

    private CameraManager manager;

    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

       // hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


        manager = (CameraManager) getSystemService(CAMERA_SERVICE);







        manager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            String[] cameraIdList = manager.getCameraIdList();
            for (String cameraId : cameraIdList){
                hasFlash = manager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (!hasFlash) {

                    AlertDialog alert = new AlertDialog.Builder(this).create();
                    alert.setTitle("Error");
                    alert.setMessage("Your device does not support a flash");
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }   else {
                    //TODO select
                }

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
