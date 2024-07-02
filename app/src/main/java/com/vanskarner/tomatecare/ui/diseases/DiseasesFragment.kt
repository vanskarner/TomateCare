package com.vanskarner.tomatecare.ui.diseases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vanskarner.tomatecare.databinding.FragmentDiseasesBinding
import com.vanskarner.tomatecare.ui.MainViewModel
import com.vanskarner.tomatecare.ui.Selection
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class DiseasesFragment : BaseBindingFragment<FragmentDiseasesBinding>() {
    @Inject
    lateinit var diseaseDialog: DiseaseDialog

    @Inject
    lateinit var diseaseAdapter: DiseaseAdapter
    private val viewModel: DiseaseViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()
    private val args: DiseasesFragmentArgs by navArgs()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDiseasesBinding = FragmentDiseasesBinding.inflate(layoutInflater)

    override fun setupView() {
        viewModelActivity.showBottomNavigation(Selection.Diseases)
        binding.rcvDiseases.adapter = diseaseAdapter
        diseaseAdapter.setOnClickListener { viewModel.findDisease(it.id) }
        binding.svDiseases.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterByName(text)
                return false
            }
        })
    }

    override fun setupViewModel() {
        viewModel.startInfo(args.keyCode)
        viewModel.diseases.observe(viewLifecycleOwner) { showDiseases(it) }
        viewModel.diseaseDetail.observe(viewLifecycleOwner) { showDiseaseDetail(it) }
        viewModel.moreInfo.observe(viewLifecycleOwner) {
            binding.svDiseases.isIconified = false
            binding.svDiseases.setQuery(it, true)
        }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun showDiseases(list: List<DiseaseModel>) = diseaseAdapter.updateList(list)

    private fun showDiseaseDetail(item: DiseaseDetailModel) =
        diseaseDialog.show(childFragmentManager, item)

}