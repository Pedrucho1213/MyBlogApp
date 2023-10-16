package com.example.myblogapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
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
import com.example.myblogapp.viewModel.SignInViewModel
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
                R.id.posts_btn -> {
                    getAllData()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.mypost_btn -> {
                    getUserPosts()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.logout_btn -> {
                    viewModel.signOut().observe(this) {
                        if (it) {
                            PreferenceManager.logOut(this)
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

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

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        val query = binding.searchTxt.query
        performSearch(query.toString())
        return true
    }
}