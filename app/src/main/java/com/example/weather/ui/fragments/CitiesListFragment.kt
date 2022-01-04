package com.example.weather.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.presenters.CitiesListPresenter
import com.example.weather.ui.WeatherApplication
import com.example.weather.ui.adapters.CitiesAdapter
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.helpers.ItemTouchHelperAdapter
import com.example.weather.ui.helpers.SimpleItemTouchHelperCallback
import com.example.weather.ui.holders.CityViewHolder
import com.example.weather.ui.interfaces.ICitiesListView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

private val TAG = CitiesListFragment::class.java.simpleName

/**
 * Fragment for displaying a list of cities in RecyclerView
*/
class CitiesListFragment : MvpAppCompatFragment(R.layout.cities_list_fragment), ICitiesListView {

    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var citiesAdapter : CitiesAdapter
    private lateinit var loadSpaceView : ImageView
    private lateinit var progressBarView : ProgressBar

    // Presenter setup
    @InjectPresenter
    lateinit var citiesPresenter : CitiesListPresenter

    @ProvidePresenter
    fun provideCitiesPresenter() : CitiesListPresenter{
        return CitiesListPresenter(
            ((requireActivity().application) as WeatherApplication).getCitiesInteractor,
            ((requireActivity().application) as WeatherApplication).getCurrentCityInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "On create")
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cities_list_fragment, container, false)

        initView(view)

        Log.i(TAG, "On create view")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        citiesPresenter.onViewReady()
    }

    override fun onResume() {
        super.onResume()
        citiesPresenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        citiesPresenter.onDestroyView()
        Log.d(TAG, "On destroy")
    }

    override fun updateData(cities : MutableList<CityViewEntity>) {
        this.citiesAdapter.submitList(cities)
    }

    override fun openDetailWindow(city: CityViewEntity, currentCity : CityViewEntity?) {
        (requireActivity() as IOpenFragment).run(city, currentCity)
    }

    override fun showMessage(message: Int) {
        TODO("Not yet implemented")
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun moveCities(firstCityInd: Int, secondCityInd: Int) {
        this.citiesAdapter.notifyItemMoved(firstCityInd, secondCityInd)
    }

    override fun startLaunch() {
        loadSpaceView.isVisible = true
        progressBarView.isVisible = true
    }

    override fun endLaunch() {
        loadSpaceView.isVisible = false
        progressBarView.isVisible = false
    }

    private fun initView(view : View){
        citiesRecyclerView = view.findViewById(R.id.cities_recycler_view)
        loadSpaceView = view.findViewById(R.id.load_space_view)
        progressBarView = view.findViewById(R.id.progress_bar_view)
    }
    private fun setupRecyclerView(){
        val itemTouchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(touchHelper))
        itemTouchHelper.attachToRecyclerView(citiesRecyclerView)
        citiesAdapter = CitiesAdapter.newInstance(
            object : CityViewHolder.OnClickCallback{
                override fun onClick(city: CityViewEntity) {
                    citiesPresenter.clickOnCityCard(city)
                }
            },
            object : CityViewHolder.OnLongClickCallback{
                override fun onClick(holder : RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(holder)
                }
            }
        )

        citiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CitiesListFragment.citiesAdapter
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
    }

    private val touchHelper = object : ItemTouchHelperAdapter {
        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            this@CitiesListFragment.citiesPresenter.citiesReplace(fromPosition, toPosition)
            return true
        }
    }

    companion object{
        fun newInstance() : CitiesListFragment{
            return CitiesListFragment().apply {

            }
        }
    }

    /** Interface for open fragment */
    interface IOpenFragment{
        fun run(city : CityViewEntity, currentCity : CityViewEntity?)
    }
}