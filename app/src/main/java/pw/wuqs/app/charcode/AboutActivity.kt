package pw.wuqs.app.charcode

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        versionTextView.text = versionName()

        // Activate web links
        websiteTV.movementMethod = LinkMovementMethod.getInstance()
        sourceTV.movementMethod = LinkMovementMethod.getInstance()
    }

    public fun showLicense(view: View) {
        Toast.makeText(this, "Open source license", Toast.LENGTH_SHORT).show()
    }

    /** Get version name of the app */
    private fun versionName(): String {
        var pInfo: PackageInfo? = null
        try {
            pInfo = packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pInfo!!.versionName
    }
}
