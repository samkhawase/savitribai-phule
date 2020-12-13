package com.vik.typingassist

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vik.typingassist.utils.Logger
import com.vik.typingassist.utils.Utility


class MainActivity : AppCompatActivity() {
    private val TAG : String = "MainActivity"
    private var keyPressCounter : Int = 0
    var list = ArrayList<String>()
    private lateinit var itemsLayout: LinearLayout
    private lateinit var keyBoardLayout: RelativeLayout
    private var lesson = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()
        keyBoardLayout = findViewById(R.id.keyboard_container)
        list = Utility.getJList()
        preparePracticeListView(list)
        updatePracticeViewList()
    }

    private fun preparePracticeListView(list : ArrayList<String>) {
        itemsLayout = findViewById(R.id.ll_display_container)
        //remove if any existing child view
        itemsLayout.removeAllViews()
        for(item in list) {
            val view = layoutInflater.inflate(R.layout.layout_textview, null)
            val tvItem: TextView = view.findViewById(R.id.text)
            tvItem.setText(item)
            itemsLayout.addView(view)
        }
    }

    private fun updatePracticeViewList() {
        if(list.size != keyPressCounter) {
            var v: View? = null
            v = itemsLayout.getChildAt(keyPressCounter)
            val tvItem: TextView = v.findViewById(R.id.text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvItem.setBackground(getDrawable(R.drawable.blue_text_background))
            }
        }
    }

    private fun updateView(isMatched : Boolean) {
        var v: View? = null
        v = itemsLayout.getChildAt(keyPressCounter)
        val tvItem: TextView = v.findViewById(R.id.text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isMatched) {
                tvItem.setBackground(getDrawable(R.drawable.green_text_background))
            } else {
                tvItem.setBackground(getDrawable(R.drawable.wrong_text_background))
                tvItem.postDelayed(Runnable
                {
                    tvItem.setBackground(getDrawable(R.drawable.blue_text_background))
                }, 300)
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        super.onKeyUp(keyCode, event)
        Logger.d(TAG, "Key code Up : $keyCode")
        val id = resources.getIdentifier("key_btn_$keyCode", "id", packageName)
        val btn : Button = findViewById(id)
        val pressedButtonText = btn.getText().toString()
        Logger.d(TAG, "pressedButtonText: $pressedButtonText")
        val hintText = list.get(keyPressCounter)
        Logger.d(TAG, "hintText: $hintText")
        if (pressedButtonText.equals(hintText, ignoreCase = true)) {
            Logger.d(TAG, "Key matched!!!")
            btn.setBackground(getDrawable(R.drawable.key_selector))
            updateView(true)
            keyPressCounter++
            updatePracticeViewList()
        } else {
            Logger.e(TAG,"Key does not match")
            btn.setBackground(getDrawable(R.drawable.key_wrong_selector))
            updateView(false)
        }
        simulateButtonDown(btn)
        btn.postDelayed(Runnable
        {
            simulateButtonUp(btn)
        }, 300)

        if(list.size == keyPressCounter) {
            refreshView()
        }
        return true
    }

    private fun refreshView() {
        keyPressCounter = 0
        //go to next lesson
        lesson++
        if(lesson == 1) {
            list = Utility.getFList()
        } else if(lesson == 2) {
            list = Utility.getFJList()
        } else if(lesson == 3) {
            list = Utility.getFJSpaceList();
        }
        preparePracticeListView(list)
        updatePracticeViewList()
    }

    private fun simulateButtonDown(view : View) {
        view.setPressed(true);
        view.invalidate();
    }

    private fun simulateButtonUp(view : View) {
        view.setPressed(false);
        view.invalidate();
    }

    private fun hideSystemUI() {
        if (getWindow() != null) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }
    }
}