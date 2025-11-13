package com.example.myapplication.data
import com.example.myapplication.di.ApplicationScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val placeDao: PlaceDao,
    private val firestore: FirebaseFirestore,
    @ApplicationScope private val externalScope: CoroutineScope
) {
    val allPlaces: Flow<List<Place>> = placeDao.getAllPlaces()

    fun getPlaceById(id: String): Flow<Place?> = placeDao.getPlaceById(id)

    init {
        externalScope.launch {
            listenForFirestoreChanges()
        }
    }

    private suspend fun listenForFirestoreChanges() {
        firestore.collection("lugares").snapshots().collect { snapshot ->
            val places = snapshot.toObjects<Place>()
            placeDao.syncFromFirebase(places)
        }
    }

    suspend fun addPlace(nome: String, descricao: String, endereco: String) {
        val newPlace = Place(
            id = firestore.collection("lugares").document().id,
            nome = nome,
            descricao = descricao,
            endereco = endereco,
            dataCriacao = System.currentTimeMillis()
        )
        firestore.collection("lugares").document(newPlace.id).set(newPlace).await()
    }

    suspend fun updatePlace(place: Place) {
        firestore.collection("lugares").document(place.id).set(place).await()
    }

    suspend fun deletePlace(place: Place) {
        firestore.collection("lugares").document(place.id).delete().await()
    }
}