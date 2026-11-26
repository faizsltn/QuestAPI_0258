package com.example.myapplication.repositori

import com.example.myapplication.apiservice.ServiceApiSiswa
import com.example.myapplication.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void>
}
