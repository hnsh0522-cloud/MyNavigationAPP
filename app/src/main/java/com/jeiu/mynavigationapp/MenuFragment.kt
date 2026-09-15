package com.jeiu.mynavigationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jeiu.mynavigationapp.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 화면이 보일 때마다 목록을 새로고침합니다.
        refreshBookList()

        binding.btnAddBook.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("bookId", -1) // 새 책 추가는 -1 전달
            }
            findNavController().navigate(R.id.action_menuFragment_to_addEditFragment, bundle)
        }

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun refreshBookList() {
        binding.bookListContainer.removeAllViews()
        val books = BookRepository.getBooks()
        
        for (book in books) {
            val button = Button(requireContext()).apply {
                text = book.title
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 16)
                }
                setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt("bookId", book.id)
                    }
                    findNavController().navigate(R.id.action_menuFragment_to_detailFragment, bundle)
                }
            }
            binding.bookListContainer.addView(button)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}