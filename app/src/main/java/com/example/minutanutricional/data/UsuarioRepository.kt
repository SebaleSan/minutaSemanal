package com.example.minutanutricional.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

suspend fun registrarUsuario(
    correo: String,
    contrasena: String,
    nombre: String,
    dieta: String,
    sexo: String
): Result<Unit> {
    return try {
        val auth = FirebaseAuth.getInstance()
        val resultado = auth.createUserWithEmailAndPassword(correo, contrasena).await()
        val uid = resultado.user?.uid ?: throw Exception("No se pudo obtener el UID del usuario")

        val datosUsuario = mapOf(
            "nombre" to nombre,
            "correo" to correo,
            "dieta" to dieta,
            "sexo" to sexo
        )

        FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .child(uid)
            .setValue(datosUsuario)
            .await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception(traducirErrorFirebase(e)))
    }
}

suspend fun iniciarSesion(
    correo: String,
    contrasena: String
): Result<Unit> {
    return try {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(correo, contrasena).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception(traducirErrorFirebase(e)))
    }
}

data class DatosUsuario(
    val nombre: String = "",
    val correo: String = "",
    val dieta: String = "",
    val sexo: String = ""
)

suspend fun obtenerUsuarioActual(): Result<DatosUsuario> {
    return try {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("No hay un usuario con sesión iniciada")

        val snapshot = FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .child(uid)
            .get()
            .await()

        val datos = snapshot.getValue(DatosUsuario::class.java)
            ?: throw Exception("No se encontraron datos del usuario")

        Result.success(datos)
    } catch (e: Exception) {
        Result.failure(Exception(traducirErrorFirebase(e)))
    }
}

suspend fun actualizarUsuario(
    nombre: String,
    dieta: String,
    sexo: String
): Result<Unit> {
    return try {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("No hay un usuario con sesión iniciada")

        val datosActualizados = mapOf(
            "nombre" to nombre,
            "dieta" to dieta,
            "sexo" to sexo
        )

        FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .child(uid)
            .updateChildren(datosActualizados)
            .await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception(traducirErrorFirebase(e)))
    }
}

suspend fun eliminarUsuario(): Result<Unit> {
    return try {
        val usuario = FirebaseAuth.getInstance().currentUser
            ?: throw Exception("No hay un usuario con sesión iniciada")
        val uid = usuario.uid

        FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .child(uid)
            .removeValue()
            .await()

        usuario.delete().await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception(traducirErrorFirebase(e)))
    }
}





fun traducirErrorFirebase(excepcion: Exception): String {
    return when (excepcion) {
        is FirebaseAuthWeakPasswordException ->
            "La contraseña debe tener al menos 6 caracteres"
        is FirebaseAuthInvalidCredentialsException ->
            "Correo o contraseña incorrectos"
        is FirebaseAuthUserCollisionException ->
            "Ya existe una cuenta registrada con ese correo"
        is FirebaseAuthInvalidUserException ->
            "No existe una cuenta registrada con ese correo"
        else ->
            "Ocurrió un error. Intenta nuevamente"
    }
}



