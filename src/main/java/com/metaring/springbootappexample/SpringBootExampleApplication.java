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

package com.metaring.springbootappexample;

import com.metaring.springbootappexample.service.roles.callback.CallbackScriptRunner;
import com.metaring.springbootappexample.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.metaring.framework.ext.spring.boot.MetaRingSpringBootApplication;
import com.metaring.springbootappexample.repository.PersonRepository;

import java.util.UUID;

@SpringBootApplication
public class SpringBootExampleApplication {

    public static void main(String[] args) {
        MetaRingSpringBootApplication.run(args, SpringBootExampleApplication.class);
        // Example 1: CallbackScriptRunner.invokeDatabaseCallback();
        // Example 2: CallbackScriptRunner.executeScriptWithContinuations();
        // Example 3: CallbackScriptRunner.doTopCall()
        CallbackScriptRunner.doTopCall();
    }

    @Bean
    public CommandLineRunner demo(PersonRepository repository) {
        return args -> {
            repository.save(new User(UUID.randomUUID().toString(), "Hayk", "Hovhannisyan"));
            repository.save(new User(UUID.randomUUID().toString(), "Marco", "Vasapollo"));
            repository.save(new User(UUID.randomUUID().toString(), "Jack", "Bauer"));
            repository.save(new User(UUID.randomUUID().toString(), "Chloe", "O'Brian"));
            repository.save(new User(UUID.randomUUID().toString(), "Vardan", "Hovhannisyan"));
            repository.save(new User(UUID.randomUUID().toString(), "David", "Palmer"));
            repository.save(new User(UUID.randomUUID().toString(), "Michelle", "Dessler"));
            repository.save(new User(UUID.randomUUID().toString(), "John", "Doe"));
        };
    }
}