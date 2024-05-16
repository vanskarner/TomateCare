package com.vanskarner.tomatecare.diseases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.Selection
import com.vanskarner.tomatecare.databinding.FragmentDiseasesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class DiseasesFragment : BaseBindingFragment<FragmentDiseasesBinding>() {
    @Inject
    lateinit var diseaseDialog : DiseaseDialog
    @Inject
    lateinit var diseaseAdapter : DiseaseAdapter
    private val viewModel: DiseaseViewModel by viewModels()
    private val viewModelActivity:MainViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDiseasesBinding = FragmentDiseasesBinding.inflate(layoutInflater)

    override fun setupView() {
        viewModelActivity.showBottomNavigation(Selection.Diseases)
        binding.rcvDiseases.adapter = diseaseAdapter
        diseaseAdapter.setOnClickListener { viewModel.exampleDiseaseDetail() }
    }

    override fun setupViewModel() {
        viewModel.exampleDiseases()
        viewModel.diseases.observe(viewLifecycleOwner) { showDiseases(it) }
        viewModel.diseaseDetail.observe(viewLifecycleOwner) { showDiseaseDetail(it) }
    }

    private fun showDiseases(list: List<DiseaseModel>) = diseaseAdapter.updateList(list)

    private fun showDiseaseDetail(item: DiseaseDetailModel) =
        diseaseDialog.show(childFragmentManager, item)

}