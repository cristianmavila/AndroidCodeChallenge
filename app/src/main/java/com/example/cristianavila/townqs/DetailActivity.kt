package com.example.cristianavila.townqs
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cristianavila.townqs.PostItemAdapter.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    val client by lazy {
        INetworkApi.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val titlePost = intent.getStringExtra(ViewHolder.POST_TITLE)

        txtPostTitle.text = titlePost

        val postID = intent.getIntExtra(ViewHolder.POST_ID, -1)
        val postUser = intent.getIntExtra(ViewHolder.POST_USER, -1)

        showPost(postID)
        showPostAuthor(postUser)
    }

    private fun showPost(id: Int) {

        disposable = client.getPost(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    txtPostBody.text = it.body
                }

    }

    private fun showPostAuthor(id: Int) {

        disposable = client.getPostAuthor(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    txtPostAuthor.text = it.name
                    txtPostAuthorEmail.text = it.email
                }

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
