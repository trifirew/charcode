package pw.wuqs.app.charcode

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Show version name
        versionTextView.text = versionName()
        // Activate web links
        websiteTV.movementMethod = LinkMovementMethod.getInstance()
        sourceTV.movementMethod = LinkMovementMethod.getInstance()
    }

    fun showLicense(view: View) {
        startActivity(Intent(this, LicenseActivity::class.java))
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
