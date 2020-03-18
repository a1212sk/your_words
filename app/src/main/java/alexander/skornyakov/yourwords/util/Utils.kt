package alexander.skornyakov.yourwords.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.UnknownHostException

class Utils {
    companion object{
        fun hideKeyboard(activity: FragmentActivity) {
            val imm: InputMethodManager =
                activity.baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
//            val inputMethodManager =
//                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//
//            // Check if no view has focus
//            val currentFocusedView = activity.currentFocus
//            currentFocusedView?.let {
//                inputMethodManager.hideSoftInputFromWindow(
//                    activity.window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//            }
        }

        fun showKeyboard(activity: FragmentActivity) {
            val imm: InputMethodManager =
                activity.baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

         fun isNetworkAvailable(context: Context): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

         fun isInternetAvailable(context: Context): Boolean = runBlocking {
            try {
                if(isNetworkAvailable(context)) {
                    val address = withContext(Dispatchers.IO) { InetAddress.getByName("www.google.com") }
                    !address.equals("")
                }
                else{
                    false
                }
            } catch (e: UnknownHostException) {
                false
            }
        }

        fun disableButton(btn: Button){
            btn.alpha = 0.5f
            btn.isEnabled = false
        }

        fun enableButton(btn: Button){
            btn.alpha = 1f
            btn.isEnabled = true
        }
    }

}