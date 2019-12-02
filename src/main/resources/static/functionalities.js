function functionalities(endPointProvider, identificationDataProvider) {

  if(!endPointProvider) {
    throw 'End Point Provider must be defined';
  }

  var context = this;
  context.endPointProvider = endPointProvider;

  if(!identificationDataProvider) {
    throw 'Identification Data Provider must be defined';
  }
  context.identificationDataProvider = identificationDataProvider;

  context.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_CHANGE_PASSWORD = function(input, callback) {
    return new Promise(function(accept) {
      setTimeout(function() {
        var internalCallback = function(response, request) {
          accept(response);
          callback && callback(response, request);
        };
        context.endPointProvider({
          id : parseInt((Math.random() * new Date().getTime() * Math.random() + new Date().getTime()).toString().split('.').join()),
          name : 'com.metaring.springbootappexample.service.changePassword',
          param : input || null
        }, internalCallback);
      });
    });
  };

  context.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_GET_PERSONS_BY_LAST_NAME = function(input, callback) {
    return new Promise(function(accept) {
      setTimeout(function() {
        var internalCallback = function(response, request) {
          accept(response);
          callback && callback(response, request);
        };
        context.endPointProvider({
          id : parseInt((Math.random() * new Date().getTime() * Math.random() + new Date().getTime()).toString().split('.').join()),
          name : 'com.metaring.springbootappexample.service.getPersonsByLastName',
          param : input || null
        }, internalCallback);
      });
    });
  };

  context.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_MESSAGE = function(input, callback) {
    return new Promise(function(accept) {
      setTimeout(function() {
        var internalCallback = function(response, request) {
          accept(response);
          callback && callback(response, request);
        };
        context.endPointProvider({
          id : parseInt((Math.random() * new Date().getTime() * Math.random() + new Date().getTime()).toString().split('.').join()),
          name : 'com.metaring.springbootappexample.service.message',
          param : input || null
        }, internalCallback);
      });
    });
  };

  context.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_SUBSCRIBE = function(input, callback) {
    return new Promise(function(accept) {
      setTimeout(function() {
        var internalCallback = function(response, request) {
          accept(response);
          callback && callback(response, request);
        };
        context.endPointProvider({
          id : parseInt((Math.random() * new Date().getTime() * Math.random() + new Date().getTime()).toString().split('.').join()),
          name : 'com.metaring.framework.rpc.auth.callReserved',
          param : {
            name : 'com.metaring.springbootappexample.service.subscribe',
            data : context.identificationDataProvider(),
            param : input || null
          }
        }, internalCallback);
      });
    });
  };
}
