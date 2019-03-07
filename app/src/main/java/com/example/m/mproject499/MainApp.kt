package com.example.m.mproject499

import android.app.Application
import android.util.Log
import com.example.m.mproject499.Dagger.AppComponent
import com.example.m.mproject499.Dagger.AppModule
import com.example.m.mproject499.Dagger.DaggerAppComponent
import com.example.m.mproject499.Data.RealmMigrations
import com.example.m.mproject499.Model.TestWord
import com.example.m.mproject499.Model.WordFireBase
import com.google.firebase.database.*
import com.vicpin.krealmextensions.save
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.HashMap


open class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
        lateinit var instance: MainApp
        private lateinit var database: DatabaseReference
        var wordsList: MutableList<WordFireBase> = mutableListOf()

    }

    override fun onCreate() {
        super.onCreate()
        initializeGraph()
        instance = this


        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder().schemaVersion(9).migration(RealmMigrations()).build()
        Realm.setDefaultConfiguration(realmConfig)

        initializeGraph()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().reference

        //writeNewWord()
        initWords()
    }

    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Realm.getDefaultInstance().close()
    }

    private fun initWords() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                wordsList.clear()
                dataSnapshot.children.mapNotNullTo(wordsList) { it.getValue<WordFireBase>(WordFireBase::class.java) }
                Log.d(MainActivity.TAG, wordsList.size.toString())
                //generateWord()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("words").addListenerForSingleValueEvent(userListener)
    }

//    private fun generateWord() {
//        wordsList.forEachIndexed { index, wordFireBase ->
//            TestWord().let {
//                it.id = index
//                it.word = wordFireBase.word
//                it.meaning = wordFireBase.meaning
//                it.desc_th = wordFireBase.desc_th
//                it.desc_eng = wordFireBase.desc_eng
//                it.save()
//            }
//        }
//    }

    private fun writeNewWord() {
        val key = database.child("words").push().key
        if (key == null) {
            Log.w("", "Couldn't get push key for posts")
            return
        }
        val choice: MutableList<String> = mutableListOf("A", "B", "C", "D")
        val word = WordFireBase("Ant", "มด", "ant is red", "มดส้ม", 1, 2, choice)
        val wordValues = word.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates["/words/$key"] = wordValues
        database.updateChildren(childUpdates)
        Log.d("Firebase word", "$word")
    }
}