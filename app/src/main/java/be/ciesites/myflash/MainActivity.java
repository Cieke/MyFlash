package be.ciesites.myflash;

import android.content.DialogInterface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private boolean hasFlash;
    private CameraManager manager;
    private ToggleButton toggleButton;
    private String flashCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
       manager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            String[] cameraIdList = manager.getCameraIdList();
            for (String cameraId : cameraIdList){
                hasFlash = manager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (hasFlash) {
                    flashCameraId = cameraId;
                    break;
                }
            }
        } catch (CameraAccessException e) {
           showAlert();
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setFlash(isChecked);
            }
        });
    }

    private void setFlash(boolean isChecked) {
        Log.d("MyFlash", "Setting Torch Mode to " + isChecked);
        if (manager == null || flashCameraId == null) return;
        try {
            manager.setTorchMode(flashCameraId, isChecked);
        } catch (CameraAccessException e) {
            Log.e("MyFlash", "Unable to change the Torch Mode", e);
        }
        toggleButton.setChecked(isChecked);
    }

    @Override
    protected void onPause() {
        super.onPause();
       setFlash(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setFlash(false);
    }

    private void showAlert() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Error");
        alert.setMessage("Your device does not support a flash");
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }
}
