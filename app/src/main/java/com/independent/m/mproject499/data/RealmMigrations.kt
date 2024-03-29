package com.independent.m.mproject499.data

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration


class RealmMigrations : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema


        if (oldVersion == 1L) {
            schema.create("Answers").apply {
                addField("id", Int::class.javaObjectType, FieldAttribute.PRIMARY_KEY)
                addField("intent", String::class.javaObjectType)
                addField("answer", String::class.javaObjectType)
                addField("condition", String::class.javaObjectType)
                addField("on_answer", String::class.javaObjectType)
            }

        }

        if (oldVersion == 2L) {
            schema.create("TestWord").apply {
                addField("id", Int::class.javaObjectType, FieldAttribute.PRIMARY_KEY)
                addField("word", String::class.javaObjectType)
                addField("meaning", String::class.javaObjectType)
                addField("desc_th", String::class.javaObjectType)
                addField("desc_eng", String::class.javaObjectType)
            }

        }
    }
}