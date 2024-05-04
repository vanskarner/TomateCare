package com.vanskarner.tomatecare.diseases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.vanskarner.singleadapter.SingleAdapter
import com.vanskarner.tomatecare.databinding.FragmentDiseasesBinding

class DiseasesFragment : Fragment() {
    private lateinit var binding: FragmentDiseasesBinding
    private val diseaseDialog = DiseaseDialog()
    private val singleAdapter = SingleAdapter()
    private val bindAdapter = DiseaseBindAdapter()
    private val viewModel: DiseaseViewModel by viewModels()

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
        binding.rcvDiseases.adapter = singleAdapter
        bindAdapter.setOnClickItem {
            val viewHolder = it.tag as RecyclerView.ViewHolder
            val diseaseDetailModel = singleAdapter.getItem<DiseaseModel>(viewHolder.adapterPosition)
            viewModel.diseaseDetail(diseaseDetailModel.id)
        }
        singleAdapter.add(bindAdapter)
    }

    private fun setupViewModel() {
        viewModel.diseasesList()
        viewModel.diseases.observe(viewLifecycleOwner) { showDiseases(it) }
        viewModel.diseaseDetail.observe(viewLifecycleOwner) { showDiseaseDetail(it) }
    }

    private fun showDiseases(list: List<DiseaseModel>) = singleAdapter.set(list)

    private fun showDiseaseDetail(item: DiseaseDetailModel) =
        diseaseDialog.show(childFragmentManager, item)

}