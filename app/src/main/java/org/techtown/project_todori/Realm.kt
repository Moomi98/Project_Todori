package org.techtown.project_todori

import android.app.Activity
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmClass() {
    companion object{

        lateinit var todoRealm : Realm

        fun initRealm(activity : Activity){
            Realm.init(activity)
            val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            Realm.setDefaultConfiguration(config)
            todoRealm = Realm.getDefaultInstance() // 여기 까지가 기본 작업

        }
    }
}