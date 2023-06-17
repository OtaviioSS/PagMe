import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler(
    private val activity: Activity,
    private val permissions: Array<String>,
    private val requestCode: Int,
    private val callback: (Boolean) -> Unit
) {

    fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(activity, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), requestCode)
        } else {
            // Todas as permissões já foram concedidas
            callback.invoke(true)
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == this.requestCode) {
            val granted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            callback.invoke(granted)
        }
    }
}
