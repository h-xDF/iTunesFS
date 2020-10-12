package ru.raralux.itunesfs.service

import android.os.Build
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private const val BASE_URL = "https://itunes.apple.com"

    private var retrofit: ITunesServices? = null

    fun getClient(): ITunesServices {
        if (retrofit == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(makeClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                    .create(ITunesServices::class.java)

            } else {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                    .create(ITunesServices::class.java)
            }
        }
        return retrofit!!
    }

    private fun makeClient(): OkHttpClient {
        val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
            .build()

        var okttp= OkHttpClient()
        try {
            okttp = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        } catch (e: KeyManagementException) {
            e.printStackTrace();
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace();
        }
        return okttp
    }
}