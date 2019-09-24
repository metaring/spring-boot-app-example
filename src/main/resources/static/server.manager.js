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
function ServerManager(defaultInit, errorsManager, callback, base_url) {

  var context = this;

  context.defaultInit = defaultInit;
  context.errorsManager = errorsManager || {};
  context.callback = callback;

  context.sock = null;
  context.sockOK = false;
  context.first = true;
  context.pendingRequests = {};

  context.base_url = base_url;

  try {
     context.base_url = context.base_url || window.base_url;
  } catch(e) {
  }

  context.base_url = context.base_url || '.';

  context.base_url.charAt(context.base_url.length - 1) === '/' && (context.base_url = context.base_url.substring(0, context.base_url.length - 1));

  context.call = function call(request, callback) {
    if(context.sockOK && !context.forceRest(request)) {
      context.sockjs(request, callback);
    } else {
      context.rest(request, callback);
    }
  };

  context.forceRest = function forceRest(request) {
    if(!context.forcedRestNames) {
      return false;
    }
    var names = [];
    var fillNames = function fillNames(obj) {
      var keys = [];
      try {
        keys = Object.keys(obj);
      } catch(e) {
      }
      if(keys.length === 0) {
        return;
      }
      for(k in keys) {
        var propertyName = keys[k];
        if(propertyName === 'name') {
          names.push(obj[propertyName]);
        }
        if(typeof (obj[propertyName]) === 'object') {
          fillNames(obj[propertyName]);
        }
      }
    }
    fillNames(request);
    var restNames = Enumerable.From(context.forcedRestNames);
    for(var n in names) {
      if(restNames.Contains(names[n])) {
        return true;
      }
    }
    return false;
  }

  context.rest = function(request, callback) {
    $.ajax({
      type : 'POST',
      url : context.base_url + '/call',
      data : JSON.stringify(request),
      success : function(response) {
        context.manageResponse(request, response, callback)
      }
    });
  };

  context.sockjs = function(request, callback) {
    context.pendingRequests[request.id] = {
      request : request,
      callback : callback
    };
    context.sock.send(JSON.stringify(request));
  };

  context.manageSockjsResponse = function(sockjsResponse) {
    var response = JSON.parse(sockjsResponse.data);
    if(response.topic) {
      context.notify(response.topic, response.payload);
      return;
    }
    var request = context.pendingRequests[response.id].request;
    var callback = context.pendingRequests[response.id].callback;
    delete context.pendingRequests[response.id];
    context.manageResponse(request, response, callback);
  };

  context.manageResponse = function(request, response, callback) {
    if((typeof response).toLowerCase() === 'string') {
      try {
        response = JSON.parse(response);
      } catch(e) {}
    }
    response.data && Object.keys(response.data).map(function(data) {context.notify(data, response)});
    var callCallback = callback !== undefined && callback !== null && (typeof callback).toLowerCase() === 'function';
    if(response.verdict === undefined && response.result !== undefined && response.result.verdict !== undefined) {
      response = response.result;
    }
    if (response.verdict === 'FAULT') {
      callCallback = context.manageFault(request, response, callback, callCallback);
    }
    if (response.verdict === 'WARNING') {
      callCallback = context.manageWarning(request, response, callback, callCallback);
    }
    callCallback && callback(response, request);
  };

  context.manageFault = function(request, response, callback, callCallback) {
    var managedError = context.errorsManager[response.errorData.codeName];
    if (managedError === undefined) {
      if (callCallback) {
        callback(response, request);
      } else {
        console.log(response.errorData);
      }
    } else {
      if (callCallback && managedError.before) {
        callback(response, request);
      }
      managedError.call(response, request, callback);
      if (callCallback && !managedError.before && managedError.after) {
        callback(response, request);
      }
    }
  };

  context.notify = function(data, response) {
    try {
        $.publish(data, response);
    } catch(e) {
    }
    //CUSTOM-NOTIFY
  };

  context.manageWarning = function(request, response, callback, callCallback) {
  };

  context.sockjsClose = function sockjsClose(event) {
    context.sockOK = false;
    if(event.code === 1000) {
        return;
    }
    if(Object.keys(context.pendingRequests).length > 0) {
      for(var i in context.pendingRequests) {
        var pendingRequest = context.pendingRequests[i];
        delete context.pendingRequests[i];
        context.rest(pendingRequest.request, pendingRequest.callback);
      }
    }
    context.initSockJS();
  }

  context.initSockJS = function() {
    context.sockOK = false;
    context.sock = null;
    context.sock = new SockJS(context.base_url + '/call.sock');
    context.sock.onopen = function() {
      context.sockOK = true;
      context.connectionId = context.sock._transport.url.split('/')[5];
      if(context.first) {
        context.first = false;
        context.callback && context.callback();
      } else {
          context.defaultInit && context.defaultInit();
      }
    };
    context.sock.onclose = context.sockjsClose;
    context.sock.onmessage = context.manageSockjsResponse;
  }

  context.initSockJS();
};