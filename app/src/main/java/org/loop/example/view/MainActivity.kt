package org.loop.example.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import org.loop.example.DataLoader
import org.loop.example.R
import org.loop.example.di.MyApplication
import org.loop.example.models.pojo.RedditResponse

class MainActivity : AppCompatActivity(), HomeActivityContract.View {

    val TAG = MainActivity::class.java.name

    private var presenter: HomeActivityContract.Presenter? = null
    private var adapter: HomeAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setPresenter(HomePresenter(this, DataLoader()))

        MyApplication.component.inject(this)


        setupRecycler()

    }

    private fun setupRecycler() {
        this.adapter = HomeAdapter(this, response = RedditResponse())
        home_recycler.adapter = adapter
        home_recycler.layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)

        setScrollListener()
    }

    private fun setScrollListener() {

        home_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            var lastVisible: Int = 0
            var itemCount: Int = 0
            var lastPostionReached = true

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                lastVisible = (home_recycler.layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                itemCount = home_recycler.layoutManager.itemCount

                if (lastVisible < itemCount - 1) lastPostionReached = false
                else if (lastVisible == itemCount - 1 && dy > 0) {
                    lastPostionReached = true
                    fetchMoreResult()
                }
            }
        })
    }

    private fun fetchMoreResult() {
        Toast.makeText(this, "Load Next Result ", Toast.LENGTH_SHORT).show()
        val nextKey: String = (home_recycler.adapter as HomeAdapter).requestNextKey()
        if (!nextKey.isEmpty()) presenter?.loadNextResults(nextKey)

    }


    override fun setPresenter(presenter: HomeActivityContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDataLoadError() {
        Log.i(TAG, "Error Loading Data")
        Toast.makeText(this, R.string.data_load_error_message, Toast.LENGTH_SHORT).show()
    }

    override fun onDataLoadSuccess(response: RedditResponse) {
        Log.i(TAG, response.data.toString())
        adapter?.updateList(response)

    }

    override fun showProgress(visibility: Boolean) {
        if (visibility) home_progress.visibility = View.VISIBLE
        else home_progress.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadInitialResults(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            (presenter as HomePresenter).onStop()
            presenter = null
        }
    }

}
