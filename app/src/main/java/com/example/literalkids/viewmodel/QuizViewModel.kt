package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.literalkids.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log

data class QuizQuestion(
    val questionLabel: String,
    val questionText: String,
    val answers: List<String>,
    val correctAnswer: String,
    val correctAnswerExplanation: String,
    val starScore: Int,
    val hasSound: Boolean = false,
    val hasNext: Boolean = false,
    val backgroundRes: Int
)

data class QuizUiState(
    val storyTitle: String = "Kancil Mencuri Ketimun",
    val currentQuestionIndex: Int = 0,
    val questions: List<QuizQuestion> = listOf(
        QuizQuestion(
            questionLabel = "Soal 1",
            questionText = "Apa yang membuat Pak Tani marah?",
            answers = listOf(
                "Kancil Minta Ketimun",
                "Kancil Mencuri Ketimun",
                "Kancil Tidur Di Kebun",
                "Kancil Membantu Di Kebun"
            ),
            correctAnswer = "Kancil Mencuri Ketimun",
            correctAnswerExplanation = "Pak Tani marah karena Kancil mencuri ketimun dari kebunnya.",
            starScore = 2,
            hasSound = true,
            hasNext = true,
            backgroundRes = com.example.literalkids.R.drawable.bg_quiz1
        ),
        QuizQuestion(
            questionLabel = "Soal 2",
            questionText = "Bagaimana cara Pak Tani menangkap kancil?",
            answers = listOf(
                "Menggunakan Perangkap Jebakan",
                "Mengejar Kancil Dengan Cepat",
                "Memanggil Hewan Lain",
                "Memasang Jaring"
            ),
            correctAnswer = "Menggunakan Perangkap Jebakan",
            correctAnswerExplanation = "Pak Tani menangkap Kancil dengan menggunakan perangkap jebakan.",
            starScore = 2,
            hasSound = true,
            backgroundRes = com.example.literalkids.R.drawable.bg_quiz2
        ),
        QuizQuestion(
            questionLabel = "Soal 3",
            questionText = "Apa pelajaran dari cerita Kancil Mencuri Ketimun?",
            answers = listOf(
                "Jangan pernah mencuri",
                "Berlari lebih cepat",
                "Minta izin terlebih dahulu",
                "Berbohong untuk menang"
            ),
            correctAnswer = "Jangan pernah mencuri",
            correctAnswerExplanation = "Dari cerita Si Kancil, kita belajar bahwa kita harus selalu bersikap jujur dan tidak pernah mencuri.",
            starScore = 2,
            hasSound = true,
            hasNext = true,
            backgroundRes = com.example.literalkids.R.drawable.bg_quiz3
        )
    ),
    val selectedAnswer: String? = null,
    val showAnswer: Boolean = false,
    val isAnswerCorrect: Boolean = false,
    val answerFeedback: String = "",
    val totalScore: Int = 0,
    val totalCoins: Int = 0,
    val feedbackEmoji: String? = null,
    val isQuizFinished: Boolean = false
)

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun selectAnswer(answer: String) {
        Log.d("QuizViewModel", "Jawaban dipilih: $answer")
        _uiState.value = _uiState.value.copy(selectedAnswer = answer)
    }

    fun checkAnswer() {
        Log.d("QuizViewModel", "Tombol cek jawaban ditekan")
        val currentState = _uiState.value
        if (currentState.questions.isEmpty()) {
            Log.e("QuizViewModel", "No questions loaded!")
            return
        }
        val currentQuestion = currentState.questions[currentState.currentQuestionIndex]
        val selected = currentState.selectedAnswer
        if (selected != null) {
            val isCorrect = selected == currentQuestion.correctAnswer
            Log.d("QuizViewModel", "Checking - Selected: $selected, Correct Answer: ${currentQuestion.correctAnswer}, Is Correct: $isCorrect")
            val feedback = if (isCorrect) {
                "Jawaban: Benar \n${currentQuestion.correctAnswerExplanation}"
            } else {
                "Jawaban: Salah"
            }
            val newScore = if (isCorrect) currentState.totalScore + 20 else currentState.totalScore
            _uiState.value = currentState.copy(
                showAnswer = true,
                isAnswerCorrect = isCorrect,
                answerFeedback = feedback,
                totalScore = newScore
            )
            Log.d("QuizViewModel", "After Check - Total Score: $newScore, Is Correct: $isCorrect")
        }
    }

    fun onContinueClicked(navController: NavController) {
        Log.d("QuizViewModel", "Tombol lanjut diklik")
        val currentState = _uiState.value
        if (currentState.questions.isEmpty()) {
            Log.e("QuizViewModel", "No questions loaded!")
            return
        }
        val nextIndex = currentState.currentQuestionIndex + 1
        if (nextIndex < currentState.questions.size) {
            _uiState.value = currentState.copy(
                currentQuestionIndex = nextIndex,
                selectedAnswer = null,
                showAnswer = false,
                isAnswerCorrect = false,
                answerFeedback = ""
            )
            Log.d("QuizViewModel", "Moved to next question - Total Score: ${currentState.totalScore}")
        } else {
            val finalScore = currentState.totalScore
            val finalCoins = 20
            _uiState.value = currentState.copy(
                totalScore = finalScore,
                totalCoins = finalCoins,
                isQuizFinished = true
            )
            Log.d("QuizViewModel", "Before Navigation - Final Score: $finalScore, Final Coins: $finalCoins")
            navController.navigate(Screen.QuizResult.route)
        }
    }

    // Fungsi baru untuk navigasi manual
    fun goToPreviousQuestion() {
        Log.d("QuizViewModel", "Navigating to previous question")
        val currentState = _uiState.value
        if (currentState.currentQuestionIndex > 0) {
            _uiState.value = currentState.copy(
                currentQuestionIndex = currentState.currentQuestionIndex - 1,
                selectedAnswer = null,
                showAnswer = false,
                isAnswerCorrect = false,
                answerFeedback = ""
            )
            Log.d("QuizViewModel", "Moved to previous question - Index: ${currentState.currentQuestionIndex - 1}")
        }
    }

    fun goToNextQuestion() {
        Log.d("QuizViewModel", "Navigating to next question")
        val currentState = _uiState.value
        if (currentState.currentQuestionIndex < currentState.questions.size - 1) {
            _uiState.value = currentState.copy(
                currentQuestionIndex = currentState.currentQuestionIndex + 1,
                selectedAnswer = null,
                showAnswer = false,
                isAnswerCorrect = false,
                answerFeedback = ""
            )
            Log.d("QuizViewModel", "Moved to next question - Index: ${currentState.currentQuestionIndex + 1}")
        }
    }

    fun onSoundClicked() {
        Log.d("QuizViewModel", "Tombol suara diklik!")
        // TODO: Tambahkan logika Text-to-Speech
    }


    fun selectFeedbackEmoji(emoji: String) {
        Log.d("QuizViewModel", "Feedback emoji dipilih: $emoji")
        _uiState.value = _uiState.value.copy(feedbackEmoji = emoji)
    }

    fun onFinishQuiz(navController: NavController) {
        Log.d("QuizViewModel", "Tombol selesai quiz diklik")
        navController.navigate(Screen.Profile.route) {
            popUpTo(Screen.Profile.route) { inclusive = true }
        }
    }
}