package com.vanskarner.tomatecare.diseases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.Selection
import com.vanskarner.tomatecare.databinding.FragmentDiseasesBinding

class DiseasesFragment : Fragment() {
    private lateinit var binding: FragmentDiseasesBinding
    private val diseaseDialog = DiseaseDialog()
    private val diseaseAdapter = DiseaseAdapter()
    private val viewModel: DiseaseViewModel by viewModels()
    private val viewModelActivity:MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiseasesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        viewModelActivity.showBottomNavigation(Selection.Diseases)
        binding.rcvDiseases.adapter = diseaseAdapter
        diseaseAdapter.setOnClickListener { viewModel.exampleDiseaseDetail() }
    }

    private fun setupViewModel() {
        viewModel.exampleDiseases()
        viewModel.diseases.observe(viewLifecycleOwner) { showDiseases(it) }
        viewModel.diseaseDetail.observe(viewLifecycleOwner) { showDiseaseDetail(it) }
    }

    private fun showDiseases(list: List<DiseaseModel>) = diseaseAdapter.updateList(list)

    private fun showDiseaseDetail(item: DiseaseDetailModel) =
        diseaseDialog.show(childFragmentManager, item)

}