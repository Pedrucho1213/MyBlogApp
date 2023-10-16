package com.example.myblogapp.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myblogapp.R
import com.example.myblogapp.adapters.PostsAdapter
import com.example.myblogapp.databinding.ActivityHomeBinding
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.Posts
import com.example.myblogapp.view.interfaces.OnPostSavedListener
import com.example.myblogapp.viewModel.PostViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp


class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener, OnPostSavedListener {

    private lateinit var binding: ActivityHomeBinding
    private var posts = mutableListOf<Posts>()
    private lateinit var adapter: PostsAdapter
    private val viewModel by lazy { ViewModelProvider(this)[PostViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleInternetConnection()
        setEvents()
        getAllData()

    }

    override fun onPostSaved(title: String, content: String) {
        val name = PreferenceManager.getName(this).toString()
        val uid = PreferenceManager.getUID(this)
        val time = Timestamp.now()
        val post = Posts(title, name, uid, content, time)
        viewModel.sendData(post)
        getAllData()
    }

    private fun getAllData() {
        posts.clear()
        viewModel.fetchPostData().observe(this) {
            posts = it.sortedByDescending { post -> post.date }.toMutableList()
            initRecyclerView()
        }
    }

    private fun getUserPosts() {
        posts.clear()
        viewModel.fetchUserPosts().observe(this) {
            posts = it.sortedByDescending { post -> post.date }.toMutableList()
            initRecyclerView()
        }
    }

    private fun setEvents() {
        binding.newPostBtn.setOnClickListener {
            NewPostFragment().show(supportFragmentManager, "new post")
        }
        binding.searchTxt.setOnQueryTextListener(this)
        binding.searchTxt.setIconifiedByDefault(false)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.posts_btn -> handlePostsButton()
                R.id.mypost_btn -> handleMyPostsButton()
                R.id.logout_btn -> handleLogoutButton()
                else -> false
            }
        }
    }

    private fun handlePostsButton(): Boolean {
        getAllData()
        return true
    }

    private fun handleMyPostsButton(): Boolean {
        getUserPosts()
        return true
    }

    private fun handleLogoutButton(): Boolean {

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_dialog))
            .setMessage(resources.getString(R.string.body_dialog))

            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->

            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                viewModel.signOut().observe(this) {
                    if (it) {
                        PreferenceManager.logOut(this)
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            .show()


        return true
    }

    private fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            val filteredPosts = posts.filter { post ->
                post.title!!.contains(query, ignoreCase = true) ||
                        post.content!!.contains(query, ignoreCase = true) ||
                        post.authorName!!.contains(query, ignoreCase = true)
            }
            adapter.updateData(filteredPosts)
        } else {
            adapter.updateData(posts)
        }
    }

    private fun initRecyclerView() {
        adapter = PostsAdapter(posts, this)
        binding.postsRv.layoutManager = LinearLayoutManager(this)
        binding.postsRv.adapter = adapter
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun handleInternetConnection() {
        if (isNetworkAvailable()) {
            enableAddEntryOption()
        } else {
            disableAddEntryOption()
            showDownloadedEntries()
            showNoInternetMessage()
        }
    }

    private fun enableAddEntryOption() {
        binding.newPostBtn.isEnabled = true
    }

    private fun showDownloadedEntries() {
        // Mostrar las entradas descargadas previamente
    }

    private fun disableAddEntryOption() {
        binding.newPostBtn.isEnabled = false
    }

    private fun showNoInternetMessage() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        val query = binding.searchTxt.query
        performSearch(query.toString())
        return true
    }
}