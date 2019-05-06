package com.example.cristianavila.townqs
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val client by lazy {
        INetworkApi.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_list_posts.layoutManager = LinearLayoutManager(this)

        showPosts()
    }

    private fun showPosts() {

        disposable = client.getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rv_list_posts.adapter = PostItemAdapter(this, it)
                }

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
