package com.jeiu.mynavigationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jeiu.mynavigationapp.databinding.FragmentAddEditBinding

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 bookId를 가져옵니다. (없으면 -1)
        val bookId = arguments?.getInt("bookId") ?: -1
        val existingBook = BookRepository.getBookById(bookId)

        if (existingBook != null) {
            // 수정 모드
            binding.txtHeader.text = "도서 정보 수정"
            binding.editBookTitle.setText(existingBook.title)
            binding.btnSave.text = "수정 완료"
        } else {
            // 추가 모드
            binding.txtHeader.text = "새 도서 추가"
            binding.btnSave.text = "추가 완료"
        }

        binding.btnSave.setOnClickListener {
            val newTitle = binding.editBookTitle.text.toString()
            if (newTitle.isNotBlank()) {
                if (existingBook != null) {
                    // 기존 도서 수정
                    BookRepository.updateBook(bookId, newTitle)
                    Toast.makeText(requireContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 새 도서 추가
                    BookRepository.addBook(newTitle)
                    Toast.makeText(requireContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                // 이전 화면으로 돌아가기
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}