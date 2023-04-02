package com.thesomeshkumar.showbuff.di

import com.thesomeshkumar.showbuff.BuildConfig
import com.thesomeshkumar.showbuff.data.datasource.remote.ApiService
import com.thesomeshkumar.showbuff.data.datasource.remote.RemoteDataSourceImpl
import com.thesomeshkumar.showbuff.data.repository.ShowbuffRepository
import com.thesomeshkumar.showbuff.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                    .build()

                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
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
    fun provideApiServices(retrofitClient: Retrofit): ApiService {
        return retrofitClient.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        api: ApiService
    ): ShowbuffRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return ShowbuffRepository(remoteDataSourceImpl)
    }
}
