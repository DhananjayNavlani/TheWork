package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

class FragmentManagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_management)

        val addFrag = findViewById<Button>(R.id.add_frag)
        addFrag.setOnClickListener {
            val fragment = OneFragment()
            val fragTransaction = supportFragmentManager.beginTransaction()
            fragTransaction.add(R.id.framelayout, fragment, "One Fragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        }

        val rmFrag = findViewById<Button>(R.id.remove_frag)
        rmFrag.setOnClickListener {
            val f = supportFragmentManager.findFragmentById(R.id.framelayout)
            //only for fragment one
            val fOne = supportFragmentManager.findFragmentByTag("One Fragment")

            f?.let {
                supportFragmentManager.beginTransaction().remove(f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit()
            }
        }

        val rpFrag = findViewById<Button>(R.id.replace_frag)
        rpFrag.setOnClickListener {
            val f = TwoFragment()
            with(supportFragmentManager.beginTransaction()){
                replace(R.id.framelayout,f)
                addToBackStack(null)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                commit()
            }
        }
    }
}