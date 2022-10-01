package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting the content view using DataBindingUtil creates an instance of
        // ActivityMainBinding from the supplied activity and the supplied layout. This object
        // contains mappings between the activity and layout,
        // and functionality to interact with them.
        // mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

}
