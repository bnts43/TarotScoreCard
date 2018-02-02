package com.bneuts.tarotscorecard

import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Test

import org.junit.Assert.*
import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnSuccessListener
import org.junit.Assert


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
/*
    @Test
    fun connectDatabase() {
        val db = FirebaseFirestore.getInstance()
        //Map<String, Object> user = new HashMap<>();
        val user = hashMapOf("Ada" to "first", "Lovelace" to "last",1815 to "born")

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id) }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }


    }*/
}
