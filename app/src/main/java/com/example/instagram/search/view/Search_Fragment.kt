package com.example.instagram.search.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.model.UserAuth
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.search.Search
import com.example.instagram.search.presenter.SearchPresenter
import java.util.*

class Search_Fragment : BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    private var searchListner : SearchListner? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchListner){
            searchListner = context
        }
    }

    override lateinit var presenter: Search.Presenter
    private val adapiter by lazy { SearchAdapiter(onItemClick) }

    override fun setupViews() {
        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        //spanCount quantidade de colunas
        binding?.searchRv?.adapter = adapiter
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.searchRepository()
        presenter = SearchPresenter(this, repository)
    }
    private val onItemClick : (String) ->Unit = { uuid ->
        //deposi do click
        searchListner?.goToProfile(uuid)
    }

    override fun getMenu() = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if(p0?.isNotEmpty() == true){
                        presenter.fetchUsers(p0)
                        return true
                    }
                    return false
                }
            })
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.searchProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayFullUsers(users: List<UserAuth>) {
        binding?.searchTxtEmpyt?.visibility = View.GONE
        binding?.searchRv?.visibility = View.VISIBLE
        adapiter.itens = users
        adapiter.notifyDataSetChanged()
    }

    override fun displayEmptyUsers() {
        binding?.searchTxtEmpyt?.visibility = View.VISIBLE
        binding?.searchRv?.visibility = View.GONE
    }
    interface SearchListner{
        fun goToProfile(uuid: String)
    }
    companion object{
        const val KEY_USER_ID = "key_user_Id"
    }
}
