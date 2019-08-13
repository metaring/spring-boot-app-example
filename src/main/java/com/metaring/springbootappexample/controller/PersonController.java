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
 
package com.metaring.springbootappexample.controller;

import com.metaring.springbootappexample.model.PersonMessageResponseModel;
import com.metaring.springbootappexample.model.PersonMessageModel;
import com.metaring.springbootappexample.model.PersonResponseModel;
import com.metaring.springbootappexample.service.PersonService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @Async("asyncExecutor")
    @GetMapping("/{lastName}")
    public CompletableFuture<String> getPersonsByLastName(@PathVariable("lastName") String lastName, Model model) throws ExecutionException, InterruptedException {
        CompletableFuture<List<PersonResponseModel>> personsByLastName = personService.getPersonsByLastName(lastName);
        Map<String, List<PersonResponseModel>> responseModel = new HashMap<>();
        responseModel.put("persons", personsByLastName.get());
        model.addAllAttributes(responseModel);
        return CompletableFuture.completedFuture("person");
    }

    @Async("asyncExecutor")
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public CompletableFuture<PersonMessageResponseModel> greeting(PersonMessageModel message) throws Exception {
        return CompletableFuture.completedFuture(
                new PersonMessageResponseModel("Hello, " +
                        HtmlUtils.htmlEscape(message.getFirstName() + " " + message.getLastName()) + "!"));
    }

    @Async("asyncExecutor")
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
