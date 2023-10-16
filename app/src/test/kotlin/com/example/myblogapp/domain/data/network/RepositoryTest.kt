package com.example.myblogapp.domain.data.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myblogapp.model.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(JUnit4::class)
class RepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var fireStore: FirebaseFirestore

    @Mock
    private lateinit var auth: FirebaseAuth

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Mock
    private lateinit var collectionReference: CollectionReference

    @Mock
    private lateinit var query: Query

    @Mock
    private lateinit var task: Task<QuerySnapshot>

    @Mock
    private lateinit var observer: Observer<MutableList<Posts>>

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        Mockito.`when`(fireStore.collection("Posts")).thenReturn(collectionReference)
        Mockito.`when`(collectionReference.get()).thenReturn(task)
        Mockito.`when`(auth.currentUser).thenReturn(firebaseUser)
        Mockito.`when`(firebaseUser.uid).thenReturn("testUid")
        Mockito.`when`(collectionReference.whereEqualTo("authorId", "testUid")).thenReturn(query)
        Mockito.`when`(query.get()).thenReturn(task)
        repository = Repository(fireStore, auth)
        repository.getAllData().observeForever(observer)
    }

    @Test
    fun testGetAllDataWhenCalledThenFirestoreMethodsAreCalled() {
        repository.getAllData()
        verify(fireStore).collection("Posts")
        verify(collectionReference).get()
    }

    @Test
    fun testGetAllDataWhenFirestoreReturnsErrorThenLiveDataNotUpdated() {
        Mockito.`when`(task.isSuccessful).thenReturn(false)
        repository.getAllData()
        verify(observer, Mockito.never()).onChanged(Mockito.any())
    }

    @Test
    fun testGetUserPostsWhenCalledThenCallsWhereEqualToWithCorrectParameters() {
        repository.getUserPosts()
        verify(collectionReference).whereEqualTo("authorId", "testUid")
    }

    @Test
    fun testGetUserPostsWhenFirestoreCallSuccessfulThenUpdatesLiveDataWithCorrectData() {
        val posts = mutableListOf<Posts>()
        val documentSnapshot = Mockito.mock(DocumentSnapshot::class.java)
        Mockito.`when`(documentSnapshot.getString("title")).thenReturn("testTitle")
        Mockito.`when`(documentSnapshot.getString("authorName")).thenReturn("testAuthorName")
        Mockito.`when`(documentSnapshot.getString("authorId")).thenReturn("testAuthorId")
        Mockito.`when`(documentSnapshot.getString("content")).thenReturn("testContent")
        Mockito.`when`(documentSnapshot.getTimestamp("date")).thenReturn(null)
        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(task.result)
            .thenReturn(QuerySnapshot(fireStore, listOf(documentSnapshot), null, null))
        repository.getUserPosts()
        verify(observer).onChanged(posts)
    }

    @Test
    fun testGetUserPostsWhenFirestoreCallFailsThenDoesNotUpdateLiveData() {
        Mockito.`when`(task.isSuccessful).thenReturn(false)
        repository.getUserPosts()
        verify(observer, Mockito.never()).onChanged(Mockito.any())
    }
}