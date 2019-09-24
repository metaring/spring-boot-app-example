var VERSION = 'MetaRing-SW-v2-Marco';
var CACHE_NAME = 'MetaRing-SW-v2-Cache';
var INDEXED_DB_NAME = 'MetaRing-SW-v2-IndexedDB';

self.addEventListener('message', function message(event) {
    event && event.data && event.data.command
            && typeof self[event.data.command] === "function"
            && self[event.data.command].apply(self, event.data.args);
});

self.newClientId = function clientId(clientId, identificationData) {
    self.clientId = clientId;
    self.identificationData = identificationData;
    createOrUpdateDB().then(function(objectStore) {
        return new Promise(function(ok, ko) {
            var request = objectStore.put({
                id: "identificationData",
                data: identificationData
            });
            request.onerror = function(event) {
                return ko(event.error);
            }
            request.onsuccess = ok;
        });
    }).then(prepareServerManager).then(self.link);
};

self.link = function link() {
//    self.functionalitiesClient.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_SUBSCRIBE(clientId);
}

self.prepareServerManager = function prepareServerManager() {
    return new Promise(function(ok) {
        if (self.serverManager && self.serverManager.sockOK
                && self.serverManager.sock.readyState === 1) {
            return ok();
        }
        return self.initServiceWorker().then(ok);
    });
}

self.initServiceWorker = function initServiceWorker() {
    self.serverManager && self.serverManager.sock.close();
    delete self.serverManager;
    delete self.functionalitiesClient;
    if (self.ServerManager) {
        return connect();
    }
    return createOrUpdateDB().then(function(objectStore) {
        return new Promise(function(ok, ko) {
            var request = objectStore.get('identificationData');
            request.onerror = function(event) {
                ko(event.error);
            }
            request.onsuccess = function(event) {
                event && event.target && event.target.result && event.target.result.data && (self.identificationData = event.target.result.data);
                ok(self.identificationData);
            }
        });
    }).then(sync).then(connect).then(cacheAssets);
};

self.sync = function sync() {
    return Promise.all([
        fetch('./webjars/sockjs-client/sockjs.min.js'),
        fetch('./server.manager.js'),
        fetch('./functionalities.js')
    ]).then(
        function(responses) {
            return Promise.all(responses.map(function(response) {
                return response.text();
            }));
        }).then(
            function(values) {
                for (var i in values) {
                    eval(values[i]
                        .split('function functionalities')
                        .join("self.functionalities = function functionalities")
                        .split('function ServerManager')
                        .join("self.ServerManager = function ServerManager")
                        .split('//CUSTOM-NOTIFY')
                        .join("self['on' + data] && self['on' + data](response);"));
                }
            });
};

self.connect = function() {
    return new Promise(function(ok) {
        self.serverManager = new ServerManager(null, null, function() {
            self.functionalitiesClient = new functionalities(self.serverManager.call, self.provideIdentificationData,self.provideIdentificationData);
            return ok();
        }, self.registration.scope);
    });
}

self.onpush = function(command, message) {
    if (!(self.Notification && self.Notification.permission === 'granted')) {
        return;
    }

    var data = {
        dateOfArrival : Date.now(),
        primaryKey : 1,
        url : 'https://github.com/metaring/spring-boot-app-example'
    };
    self.registration.showNotification('Hello MetaRing World', {
        body : message,
        icon : 'static/_pwa/favicon-96x96.png',
        tag : 'MetaRing-push-demo-notification-tag',
        data : data
    })
}

self.addEventListener('activate', event => {
  event.waitUntil(clients.claim());
});

function doSomeStuff(clientId) {
     return new Promise(function(success, error) {
        console.log("Here Is background sync stuff .... .... .....");
        self.functionalitiesClient.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_SUBSCRIBE(clientId);
     });
}

self.addEventListener('sync', function(event) {
  if (event.tag == 'subscribeFunctionalitySync') {
    event.waitUntil(doSomeStuff(event.srcElement.clientId));
  }
});

self.addEventListener('notificationclick', function(event) {
    const target = event.notification.data.url;
    event.notification.close();
    event.waitUntil(clients.matchAll({
        type : 'window',
        includeUncontrolled : true
    }).then(function(clientList) {
        for (var i = 0; i < clientList.length; i++) {
            var client = clientList[i];
            if (client.url == target && 'focus' in client) {
                return client.focus();
            }
        }
        return clients.openWindow(target);
    }));
});

function log(message) {
    console.log(VERSION, message);
};

function provideIdentificationData() {
    return self.identificationData;
};

function XMLHttpRequest() {
    var context = this;
    context.method = 'GET';
    context.readyState = 4;
    context.status = 200;
    context.responseText = self.responseText;
    context.xhr = context;

    Object.defineProperty(context, "onreadystatechange", {
        set : function(value) {
            context.onrsc = value;
        },
        get : function() {
            return context.onrsc;
        }
    });

    context.open = function open() {
        if (context.responseText && context.onrsc) {
            context.onrsc(context);
            return;
        }
        fetch(arguments[1]).then(function(response) {
            return response.text();
        }).then(function(text) {
            self.responseText = text;
            context.responseText = text;
            context.onrsc && context.onrsc(context);
        });
    };

    context.send = function send() {
    };

    context.once = function once() {
    };

    context.setRequestHeader = function setRequestHeader() {
    };
}

function cacheAssets() {
    return caches.open(CACHE_NAME).then(
        function(cache) {
            return cache.addAll([
                './webjars/sockjs-client/sockjs.min.js',
                './server.manager.js'
            ]);
        }
    );
}

function createOrUpdateDB() {
    if (!self.indexedDB) {
        log('IndexedDB NOT supported');
        return;
    }
    return new Promise(function(success, error) {
        var openRequest = indexedDB.open(INDEXED_DB_NAME, 1);
        var db, transaction, objectStore;
        // This event handles the event whereby a new version of the
        // database needs to be created. Either one has not been created
        // before, or a new version number has been submitted via the
        // indexedDB.open line above.
        // TODO Please check: https://stackoverflow.com/questions/55226585/indexeddb-executing-function-with-method-add-several-times-no-new-db-entry
        openRequest.onupgradeneeded = function(event) {
          // Triggers if the client had no database
          log('Running onUpgradeNeeded!');
          var db = event.target.result;
          var transaction = event.target.transaction;
          var objectStore = db.createObjectStore("identificationDB", {keyPath: "id"});

          success(objectStore);
        };

        openRequest.onsuccess = function(event) {
            log('Running onSuccess!');
            db = openRequest.result;
            db.onversionchange = function() {
               db.close();
            };
            db.onerror = function(event){
               log("Database Error: " + event.target.errorCode);
            }

            transaction = db.transaction(['identificationDB'], 'readwrite');
            transaction.oncomplete = function() {
                log('Data finished!');
                db.close();
            };

            transaction.onabort = function() {
              log('Data aborted!');
              // Otherwise the transaction will automatically abort due the failed request.
              report(transaction.error);
            };

            objectStore = transaction.objectStore("identificationDB");

            success(objectStore);
        };

        openRequest.onerror = function(event) {
            log('Running onError!' + event.target.errorCode);
            error(event.error);
        };
    });
}

self.addEventListener('fetch', function(event) {
    event.respondWith(caches.open(CACHE_NAME).then(function(cache) {
        return cache.match(event.request).then(function(response) {
            return response || fetch(event.request)
        });
    }));
});

self.initServiceWorker().catch(log);