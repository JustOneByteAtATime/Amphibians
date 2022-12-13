/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// TODO: Create a property for the base URL provided in the codelab - DONE
private const val BASE_URL = "https://developer.android.com/courses/pathways/android-basics-kotlin-unit-4-pathway-2/"

// TODO: Build the Moshi object with Kotlin adapter factory that Retrofit will be using to parse JSON - DONE
// At the top of the file, just before the Retrofit builder, add the following code to create the
// Moshi object, similar to the Retrofit object.
private val moshi = Moshi.Builder()
// For Moshi's annotations to work properly with Kotlin, in the Moshi builder, add the
// KotlinJsonAdapterFactory, and then call build().
    .add(KotlinJsonAdapterFactory())
    .build()

// TODO: Build a Retrofit object with the Moshi converter - DONE
// 4. Just below that constant, add a Retrofit builder to build and create a Retrofit object.
private val retrofit = Retrofit.Builder()
    // 5. Retrofit needs the base URI for the web service, and a converter factory to build a
    // web services API. The converter tells Retrofit what to do with the data it gets back from
    // the web service. In this case, you want Retrofit to fetch a JSON response from the web
    // service, and return it as a String. Retrofit has a ScalarsConverter that supports strings
    // and other primitive types, so you call addConverterFactory() on the builder with an
    // instance of ScalarsConverterFactory.

    // In the retrofit object declaration change the Retrofit builder to use the MoshiConverterFactory
    // instead of the ScalarConverterFactory, and pass in the moshi instance you just created.
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    // 6. Add the base URI for the web service using baseUrl() method. Finally, call build() to create the Retrofit object.
    .baseUrl(BASE_URL)
    .build()

interface AmphibianApiService {
    // TODO: Declare a suspended function to get the list of amphibians - DONE
    // Implement the AmphibianApiService interface with a suspend function for each API method
// (for this app, there's only one method, to GET the list of amphibians).
    @GET("android-basics-kotlin-unit-4-pathway-2-project-api.json")
    suspend fun getAmphibianList(): List<Amphibian>
}

// TODO: Create an object that provides a lazy-initialized retrofit service - DONE
// Create an AmphibianApi object to expose a lazy-initialized Retrofit service that uses the AmphibianApiService interface.
object AmphibianApi
{
    val retrofitService: AmphibianApiService by lazy{
        retrofit.create(AmphibianApiService::class.java)
    }
}

