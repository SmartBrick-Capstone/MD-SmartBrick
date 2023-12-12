package com.github.emmpann.smartbrick.core.data.local.repository

import androidx.lifecycle.LiveData
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.core.data.remote.response.ArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResult
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class ArticleRepository(
    private val apiService: ApiService
) {
    fun getAllArticle() = flow {
        try {
            emit(ResultApi.Success(ArticleResponse(articleList, false, "success")))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)
}

val article1 = Article(
    "1",
    "https://ds393qgzrxwzn.cloudfront.net/resize/m720x480/cat1/img/images/0/3PVbsoo9Wa.jpg",
    "Pengantar Pemrograman Kotlin",
    "Artikel ini membahas tentang dasar-dasar pemrograman Kotlin.",
    "2023-01-15"
)

val article2 = Article(
    "2",
    "https://ds393qgzrxwzn.cloudfront.net/resize/m720x480/cat1/img/images/0/E3luVfQTv3.jpg",
    "Pengenalan Android Development",
    "Pelajari bagaimana memulai pengembangan aplikasi Android.",
    "2023-02-20"
)

val article3 = Article(
    "3",
    "https://akcdn.detik.net.id/community/media/visual/2018/05/14/5d8c5df7-046f-4daf-8115-d55474fa139d.jpeg?w=2530",
    "Panduan Belajar React Native",
    "Temukan cara membangun aplikasi cross-platform dengan React Native.",
    "2023-03-10"
)

val article4 = Article(
    "4",
    "https://akcdn.detik.net.id/community/media/visual/2020/09/19/pantai-kelingking.jpeg?w=700&q=90",
    "Exploring Machine Learning Algorithms",
    "Learn about various machine learning algorithms and their applications.",
    "2023-04-05"
)

val article5 = Article(
    "5",
    "https://static.promediateknologi.id/crop/0x0:0x0/750x500/webp/photo/2023/05/20/Ilustrasi-dari-Googlemaps-Coral-Rocks-Zanzibar-953726137.jpeg",
    "Tutorial Python: Dasar-Dasar Pemrograman Python",
    "Pelajari dasar-dasar pemrograman Python dari awal.",
    "2023-05-12"
)

val article6 = Article(
    "6",
    "https://cdn1-production-images-kly.akamaized.net/ZCbugT3l18UYdnWCJimpxAFnIaU=/680x383/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/2917274/original/062528600_1568972189-Road_Trip_dengan_Mobil.jpg",
    "Pengenalan Flutter untuk Pemula",
    "Temukan cara membuat aplikasi mobile dengan Flutter.",
    "2023-06-18"
)

val article7 = Article(
    "7",
    "https://media-cldnry.s-nbcnews.com/image/upload/t_fit-1240w,f_auto,q_auto:best/rockcms/2023-05/230523-florida-travel-advisory-se-1210p-c09619.jpg",
    "Panduan Belajar SQL: Dasar-Dasar Bahasa Query",
    "Pelajari dasar-dasar bahasa query SQL untuk mengelola database.",
    "2023-07-25"
)

val article8 = Article(
    "8",
    "https://www.ruangmenyala.com/api/uploads/on-c2-s3-ruangmenyala-prod/6efe1104-0dcc-4443-b360-e77fcd62f792/biaya%20hidup%20di%20jepang.webp",
    "Mendalami Konsep Jaringan Komputer",
    "Memahami konsep-konsep penting dalam jaringan komputer.",
    "2023-08-30"
)

// Menyimpan objek-objek ArticleResponse ke dalam sebuah list
val articleList = listOf(article1, article2, article3, article4, article5, article6, article7, article8)