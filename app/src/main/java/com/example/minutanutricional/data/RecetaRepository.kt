package com.example.minutanutricional.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun obtenerRecetas(): Flow<List<Receta>> = callbackFlow {
    val database = FirebaseDatabase.getInstance().getReference("recetas")

    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val listaRecetas = mutableListOf<Receta>()
            for (hijo in snapshot.children) {
                val receta = hijo.getValue(Receta::class.java)
                if (receta != null) {
                    listaRecetas.add(receta)
                }
            }
            trySend(listaRecetas)
        }

        override fun onCancelled(error: DatabaseError) {
            close(error.toException())
        }
    }

    database.addValueEventListener(listener)

    awaitClose { database.removeEventListener(listener) }
}