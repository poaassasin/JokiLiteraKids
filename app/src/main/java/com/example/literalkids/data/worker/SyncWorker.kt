import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.literalkids.data.local.LiterakidsDatabase
import com.example.literalkids.data.network.ApiClient
import com.example.literalkids.data.repository.StoryRepository

class SyncWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Logika untuk mendapatkan repository Anda akan bergantung pada setup Dependency Injection (Hilt, dll)
        // Untuk sekarang, kita asumsikan kita bisa membuatnya.
        val database = LiterakidsDatabase.getDatabase(applicationContext)
        val apiService = ApiClient.getApiService(applicationContext)
        val storyRepository = StoryRepository(apiService, database.userDao())

        return try {
            storyRepository.syncOfflineData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}