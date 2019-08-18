package com.metaring.springbootappexample.service;

import static com.ea.async.Async.await;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;

import com.metaring.framework.broadcast.BroadcastFunctionalitiesManager;
import com.metaring.framework.util.ObjectUtil;
import com.metaring.springbootappexample.domain.Person;
import com.metaring.springbootappexample.repository.PersonRepository;

class GetPersonsByLastNameFunctionalityImpl extends GetPersonsByLastNameFunctionality {

    @Autowired
    private PersonRepository personRepository;

    @Override
    protected CompletableFuture<Void> preConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<PersonResponseModelSeries> call(String input) throws Exception {
        List<Person> result = await(personRepository.findByLastName(input));
        Person person = ObjectUtil.isNullOrEmpty(result) ? null : result.get(0);
        if(person != null) {
            await(ServiceFunctionalitiesManager.message(person.getFirstName() + " " + person.getLastName() + " is now connected!"));
            await(BroadcastFunctionalitiesManager.subscribe("message"));
        }
        return end(ObjectUtil.isNullOrEmpty(result) ? null : PersonResponseModelSeries.fromObject(result));
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(String input, PersonResponseModelSeries output) throws Exception {
        return end;
    }
}