package com.thesomeshkumar.tmdb.di

import com.thesomeshkumar.tmdb.data.datasource.remote.APIs
import com.thesomeshkumar.tmdb.data.datasource.remote.RemoteDataSourceImpl
import com.thesomeshkumar.tmdb.data.repository.TmdbRepository
import com.thesomeshkumar.tmdb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
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
            .baseUrl(Constants.TMDB_BASE_URL)
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
                val original = chain.request()
                    .newBuilder()
//                    .addHeader(
//                        "Authorization",
//                        "Bearer ${Constants.TMDB_KEY}"
//                    )
                    .build()

                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", Constants.TMDB_KEY)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

                val newRequest: Request = requestBuilder.build()
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
    ): TmdbRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return TmdbRepository(remoteDataSourceImpl)
    }
}
