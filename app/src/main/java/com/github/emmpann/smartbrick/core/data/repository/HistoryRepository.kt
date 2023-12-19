package com.github.emmpann.smartbrick.core.data.repository

import com.github.emmpann.smartbrick.core.data.remote.response.History
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class HistoryRepository {
    fun getAllHistory() = flow {
        try {
//            val successResponse = apiService.getAllArticle()
            emit(ResultApi.Success(historyList))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)
}

val historyList = listOf(
    History("1", "Pembelian Barang A", "2023-12-15", "Sukses"),
    History("2", "Pemesanan Tiket Konser", "2023-11-28", "Gagal"),
    History("3", "Pengiriman Paket B", "2023-12-02", "Sukses"),
    History("4", "Pembayaran Tagihan Listrik", "2023-12-10", "Sukses"),
    History("5", "Pembelian Buku Online", "2023-11-20", "Gagal"),
    History("6", "Pemesanan Makanan Online", "2023-12-05", "Sukses"),
    History("7", "Pengiriman Paket C", "2023-11-30", "Sukses"),
    History("8", "Pembayaran Tagihan Air", "2023-12-08", "Sukses"),
    History("9", "Pembelian Tiket Film", "2023-11-25", "Gagal"),
    History("10", "Pengiriman Dokumen Penting", "2023-12-18", "Gagal"),
    History("11", "Pembayaran Tagihan Telepon", "2023-12-12", "Sukses"),
    History("12", "Pengiriman Paket D", "2023-11-22", "Sukses"),
    History("13", "Pembelian Perlengkapan Olahraga", "2023-11-17", "Gagal"),
    History("14", "Pembelian Elektronik", "2023-12-07", "Sukses"),
    History("15", "Pemesanan Baju", "2023-12-01", "Gagal"),
    History("16", "Pengiriman Paket E", "2023-11-24", "Sukses"),
    History("17", "Pembayaran Tagihan Internet", "2023-12-14", "Sukses"),
    History("18", "Pembelian Kosmetik", "2023-11-19", "Gagal"),
    History("19", "Pemesanan Elektronik Rumah Tangga", "2023-12-09", "Sukses"),
    History("20", "Pengiriman Paket F", "2023-11-27", "Sukses")
)