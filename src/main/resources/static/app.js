/**
 * Copyright 2019 MetaRing s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
function showGreeting(event, message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendPersonData() {
    var message = $('#message').val();
    if (!message) {
        return alert('Write something first!');
    }
    $('#message').val('');
    message = $('#person').html() + ' says: ' + message;
    window.functionalities.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_MESSAGE(message);
}

async function login() {
    var response = await window.functionalities.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_CHANGE_PASSWORD("pass");
    $('#person').html('CHECK ' + (response.verdict === 'SUCCESS' ? '' : 'NOT ') + 'PASSED');
    return;
    var lastName = $("#lastName").val().split(' ').join('');

    if (!lastName) {
        return alert('Insert Last Name first');
    }

    $('#person').html('');

    var response = await window.functionalities.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_GET_PERSONS_BY_LAST_NAME(lastName);
    try {
        var person = response.result[0];
        $('#person').html(person.firstName + " " + person.lastName);
    } catch (e) {
        return alert('Login failed');
    }
}

function provideIdentificationData() {
    return {
        token: "" + new Date().getTime()
    }
}

function provideEnableData() {
    return {
        role: "USER",
        notRequiredParam: $("#lastName").val().split(' ').join('')
    }
}

function initServiceWorker() {
    destroyCache().then(destroyIndexedDB).then(destroyServiceWorker).then(function() {
        return Notification.requestPermission().then(
            function(status) {
                if (status !== 'granted') {
                    return;
                }
                navigator.serviceWorker.register('service-worker.js').then(
                    function() {
                        navigator.serviceWorker.ready.then(
                            function(serviceWorkerRegistration) {
                                serviceWorkerRegistration.sync.register('subscribeFunctionalitySync');
                                serviceWorkerRegistration.active.postMessage({ command: 'newClientId', args: [serverManager.connectionId, provideIdentificationData()] });
                            }
                        );
                    }
                );
            }
        );
    });
}

function destroyServiceWorker() {
    return navigator.serviceWorker.getRegistrations().then(function(registrations) {
        return Promise.all(registrations.map(function(registration) {
            return registration.unregister({
                immediate: true
            });
        }));
    });
};

function destroyIndexedDB() {
    return new Promise(function(ok) {
        if (!'indexedDB' in window) {
            return ok();
        }
        window.indexedDB.deleteDatabase('MetaRing-SW-v2-IndexedDB').onsuccess = ok;
    });
}

function destroyCache() {
    return new Promise(function(ok) {
        if (!'caches' in window) {
            return ok();
        }
        return caches.keys().then(function(keyList) {
            return Promise.all(keyList.map(function(key) {
                return caches.delete(key);
            }));
        }).then(ok);
    });
}

window.addEventListener('beforeinstallprompt', (event) => {
    // Log the platforms provided as options in an install prompt
    console.log(event.platforms); // e.g., ["web", "android", "windows"]
    // Prevent Chrome 67 and earlier from automatically showing the prompt
    event.preventDefault();
    // Stash the event so it can be triggered later.
    var deferredPrompt = event;

    var btnAdd = document.getElementById("btnAdd");
    // Update UI by showing a button to notify the user they can add to home screen
    btnAdd.style.display = 'block';

    btnAdd.addEventListener('click', (e) => {
        // hide our user interface that shows our A2HS button
        btnAdd.style.display = 'none';
        // Show the prompt
        deferredPrompt.prompt();
        // Wait for the user to respond to the prompt
        deferredPrompt.userChoice
            .then((choiceResult) => {
                if (choiceResult.outcome === 'accepted') {
                    console.log('User accepted the MetaRing prompt');
                } else {
                    console.log('User dismissed the MetaRing prompt');
                }
                deferredPrompt = null;
            });
    });
});

window.addEventListener('appinstalled', (evt) => {
    console.log('MetaRing', 'installed');
});

$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#login").click(login);
    $("#send").click(sendPersonData);
    $.subscribe('message', showGreeting);
    window.base_url = window.location.origin;
    window.serverManager = new ServerManager(null, null, initServiceWorker);
    window.functionalities = new functionalities(window.serverManager.call, provideIdentificationData, provideEnableData);
});