package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.modeldata.DataSiswa
import com.example.myapplication.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.Response

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    /* Fungsi untuk menyimpan data yang di entry */
    fun addSiswa(onSuccess: () -> Unit = {}) {
        if (validasiInput()) {
            viewModelScope.launch {
                try {
                    val sip: Response<Void> = repositoryDataSiswa.postDataSiswa(
                        uiStateSiswa.detailSiswa.toDataSiswa()
                    )
                    if (sip.isSuccessful) {
                        println("Sukses Tambah Data : ${sip.message()}")
                        onSuccess()
                    } else {
                        println("Gagal tambah data : ${sip.code()} ${sip.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    println("Error jaringan saat tambah data: ${e.message}")
                }
            }
        }
    }
}
