package com.example.instagram.register.data

import android.net.Uri
import com.example.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireRegisterDataSource : RegisterDataSource {

    override fun create(email: String, callback: RegisterCallback) {

        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Usuario ja Cadastrado")
                }
            }
            .addOnFailureListener { exeption ->
                callback.onFailure(exeption.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
//        variavel email esta passaondo o "nome" de usuário
//        e Variavel name esta passando o email

        FirebaseAuth.getInstance()
//          .createUserWithEmailAndPassword(name, password)  funciona porem o servidor retorna msg "Usuario não tem acesso ao objto"
            .createUserWithEmailAndPassword(email, password)// "retorna erro de email mal formatado "
            .addOnSuccessListener { result ->

                val uid = result.user?.uid
                if (uid == null) {
                    callback.onFailure("Erro interno no servidor")
                    // return@addOnSuccessListener
                } else {
                    FirebaseFirestore.getInstance()
                        .collection("/users")
                        .document(uid)
                        .set(
                            hashMapOf(
                                "name" to name,
                                "email" to email,
                                "followers" to 0,
                                "following" to 0,
                                "postCount" to 0,
                                "uuid" to uid,
                                "photoUrl" to null
                            )
                        )
                        .addOnSuccessListener {
                            callback.onSuccess()
                        }
                        .addOnFailureListener { exeption ->
                            callback.onFailure(exeption.message ?: "Erro interno")
                        }
                        .addOnCompleteListener {
                            callback.onComplete()
                        }
                }
            }
            .addOnFailureListener { exeption ->
                callback.onFailure(exeption.message ?: "Erro interno no servidor")
            }

    }

    override fun updateUser(photo: Uri, callback: RegisterCallback) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null || photo.lastPathSegment == null) {
            callback.onFailure("Usuário não encontrado ")
            return
        }
        val storageRef = FirebaseStorage.getInstance().reference
        val imgRef = storageRef.child("images/")
            .child(uid)
            .child(photo.lastPathSegment!!)
        imgRef.putFile(photo)
            .addOnSuccessListener { result ->
                imgRef.downloadUrl
                    .addOnSuccessListener { res ->

                        val userRef =
                            FirebaseFirestore.getInstance().collection("/users").document(uid)
                        userRef.get()
                            .addOnSuccessListener { document ->

                                val user = document.toObject(User::class.java)
                                val newUser = user?.copy(photoUrl = res.toString())

                                if (newUser != null) {
                                    userRef.set(newUser)
                                        .addOnSuccessListener {
                                            callback.onSuccess()
                                        }
                                        .addOnFailureListener { exception ->
                                            callback.onFailure(
                                                exception.message ?: "Falha ao atualizar a foto"
                                            )
                                        }
                                        .addOnCompleteListener {
                                            callback.onComplete()
                                        }
                                }
                            }
                    }
            }
            .addOnFailureListener { exeption ->
                callback.onFailure(
                    exeption.message ?: "Falha ao carregar o arquivo tente novamente"
                )

            }
    }
}