/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.hellosharedprefs

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView


/**
 * HelloSharedPrefs is an adaptation of the HelloToast app from chapter 1.
 * It includes:
 * - Buttons for changing the background color.
 * - Maintenance of instance state.
 * - Themes and styles.
 * - Read and write shared preferences for the current count and the color.
 *
 *
 * This is the starter code for HelloSharedPrefs.
 */
class MainActivity : AppCompatActivity() {
    // Current count
    private var mCount = 0

    // Current background color
    private var mColor = 0

    // Text view to display both count and color
    private var mShowCountTextView: TextView? = null

    // Key for current count
    private val COUNT_KEY = "count"

    private var mPreferences: SharedPreferences? = null
    private val sharedPrefFile = "com.example.android.hellosharedprefs"

    // Key for current color
    private val COLOR_KEY = "color"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview)
        mColor = ContextCompat.getColor(this,
                R.color.default_background)
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)

        // Restore the saved instance state.
        mCount = mPreferences!!.getInt(COUNT_KEY, 0)
        mShowCountTextView!!.text = String.format("%s", mCount);
        mColor = mPreferences!!.getInt(COLOR_KEY, mColor)
        mShowCountTextView!!.setBackgroundColor(mColor);
    }
    override fun onPause() {
        super.onPause()
        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.putInt(COUNT_KEY, mCount)
        preferencesEditor.putInt(COLOR_KEY, mColor)
        preferencesEditor.apply()
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        mShowCountTextView!!.setBackgroundColor(color)
        mColor = color
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    fun countUp(view: View?) {
        mCount++
        mShowCountTextView!!.text = String.format("%s", mCount)
    }

    /**
     * Saves the instance state if the activity is restarted (for example,
     * on device rotation.) Here you save the values for the count and the
     * background color.
     *
     * @param outState The state data.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_KEY, mCount)
        outState.putInt(COLOR_KEY, mColor)
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    fun reset(view: View?) {
        // Reset count
        mCount = 0
        mShowCountTextView!!.text = String.format("%s", mCount)

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background)
        mShowCountTextView!!.setBackgroundColor(mColor)
        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.clear();
        preferencesEditor.apply();
    }
}