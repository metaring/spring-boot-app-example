/**
 *    Copyright 2019 MetaRing s.r.l.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.metaring.springbootexample.service;

import com.metaring.springbootexample.model.PersonResponseModel;
import com.metaring.springbootexample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<List<PersonResponseModel>> getPersonsByLastName(String lastName) throws ExecutionException, InterruptedException {
        List<PersonResponseModel> responseModels = personRepository.findByLastName(lastName)
                .get()
                .stream()
                .map(currentPerson -> new PersonResponseModel(currentPerson.getFirstName(), currentPerson.getLastName()))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(responseModels);
    }
}