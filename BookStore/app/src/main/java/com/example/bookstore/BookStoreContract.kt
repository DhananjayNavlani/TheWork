package com.example.bookstore

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.MutableRealmInt
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Author: RealmObject {
    @PrimaryKey
    var _id: Int = 0
    var authorName: String = ""
    var authorDob: String = ""
    var authorDied: String? = null
    var authorCitizenship: String = ""
    var authorBio: String = ""
    var aimageUrl: String = ""
    var booklist: RealmList<Books> = realmListOf()
}

class Books: RealmObject{
    companion object{
        var counter: Int = 0
    }
    @PrimaryKey
    var id: Int = counter++
    var title: String = ""
    var synopsis: String = ""
    var genre: String = ""
    var price: Double = 0.0
    var bimageUrl: String = ""
    var publisherName: String = ""
    var publishDate: String = ""
    var isbn: String = ""
    val author: RealmResults<Author> by backlinks(Author::booklist)
}