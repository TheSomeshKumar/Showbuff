package com.thesomeshkumar.pixelvideos.di

import com.thesomeshkumar.pixelvideos.data.datasource.remote.APIs
import com.thesomeshkumar.pixelvideos.data.datasource.remote.RemoteDataSourceImpl
import com.thesomeshkumar.pixelvideos.data.repository.VideoRepository
import com.thesomeshkumar.pixelvideos.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.PEXEL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${Constants.PEXEL_KEY}"
                    ).build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideApiServices(retrofitClient: Retrofit): APIs {
        return retrofitClient.create(APIs::class.java)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        api: APIs
    ): VideoRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return VideoRepository(remoteDataSourceImpl)
    }
}
