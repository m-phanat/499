package com.example.m.mproject499

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.m.mproject499.Activity.GoogleLoginActivity
import com.example.m.mproject499.R.string.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: DatabaseReference

    companion object {
        const val TAG = "fxdxdk"
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //supportActionBar?.hide()
        MainApp.graph.inject(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        createFragment(MainFragment.fragment(this), getString(learning_mode))

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }

        if (supportFragmentManager.backStackEntryCount == 1) {
            this.finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        try {
            auth.currentUser?.email?.let {
                user_email.text = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = ""
        when (item.itemId) {
            R.id.learning_mode -> {
                fragment = MainFragment.fragment(this)
                title = getString(learning_mode)
            }
            R.id.test_mode -> {
                title = getString(test_mode)
                fragment = TestFragment.fragment(this)
            }
            R.id.stat -> {
                title = getString(stat)
                fragment = StatFragment.fragment(this)
            }
            R.id.logout -> {
                logout()
            }
        }
        fragment?.let {
            createFragment(it, title)
        }
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, GoogleLoginActivity::class.java))
        finish()
    }

    private fun createFragment(fragment: Fragment, name: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content, fragment)
        fragmentTransaction.commit()
        title = name
    }

}
