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
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import com.example.amphibians.network.AmphibianApiService
import kotlinx.coroutines.launch

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    // TODO: Create properties to represent MutableLiveData and LiveData for the API status - DONE
    // Add a _status a private MutableLiveData variable that can hold an AmphibianApiStatus enum and
    // backing property status for the status.
    private val _status = MutableLiveData<AmphibianApiStatus>()
    val status: LiveData<AmphibianApiStatus> = _status


    // TODO: Create properties to represent MutableLiveData and LiveData for a list of amphibian objects - DONE
    // Add an amphibians variable and private backing property _amphibians for the list of
    // amphibians, of type List<Amphibian>.
    private val _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians: LiveData<List<Amphibian>> = _amphibians


    // TODO: Create properties to represent MutableLiveData and LiveData for a single amphibian object.
    //  This will be used to display the details of an amphibian when a list item is clicked - DONE
    // Add an _amphibian variable of type MutableLiveData<Amphibian> and backing property amphibian
    // for the selected amphibian object, of type LiveData<Amphibian>. This will be used to store
    // the selected amphibian shown on the detail screen.
    private val _amphibian = MutableLiveData<Amphibian>()
    val amphibian: LiveData<Amphibian> = _amphibian

    // TODO: Create a function that gets a list of amphibians from the api service and sets the
    //  status via a Coroutine - DONE
    // Define a function called getAmphibianList(). Launch a coroutine using ViewModelScope, inside
    // the coroutine, perform a GET request to download the amphibian data by calling the
    // getAmphibians() method of the Retrofit service. You'll need to use try and catch to
    // appropriately handle errors. Before making the request, set the value of the _status to
    // AmphibianApiStatus.LOADING . A successful request should set _amphibians to the list of
    // amphibians from the server and set the _status to AmphibianApiStatus.DONE. In the event of an
    // error, _amphibians should be set to an empty list and the _status set to AmphibianApiStatus.ERROR.
    private fun getAmphibianList(){
        viewModelScope.launch{
            _status.value = AmphibianApiStatus.LOADING
            try{
                _amphibians.value = AmphibianApi.retrofitService.getAmphibianList()
                _status.value = AmphibianApiStatus.DONE
            } catch (e: Exception){
                _amphibians.value = listOf()
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }


    fun onAmphibianClicked(amphibian: Amphibian) {
        // TODO: Set the amphibian object - DONE
        // Implement the onAmphibianClicked() method to set the _amphibian property you defined to
    // the amphibian argument passed into the function. This method is already called when an
    // amphibian is selected, so that it will be displayed on the detail screen.
        _amphibian.value = amphibian
    }
}
