package com.jeiu.mynavigationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jeiu.mynavigationapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = arguments?.getInt("bookId") ?: -1
        val book = BookRepository.getBookById(bookId)

        if (book != null) {
            binding.txtBookTitle.text = book.title
            
            binding.btnEdit.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("bookId", bookId)
                }
                findNavController().navigate(R.id.action_detailFragment_to_addEditFragment, bundle)
            }
        } else {
            binding.txtBookTitle.text = "도서를 찾을 수 없습니다."
            binding.btnEdit.visibility = View.GONE
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}