function prepareOrdersPage () {

    var app = angular.module("WebUi", ["ngCookies"]);
    
    ///////////////////////////////////////////////////////////
    //Security Service
    ///////////////////////////////////////////////////////////
    app.factory('Oauth2SecurityService', function($injector:ng.auto.IInjectorService, $q:ng.IQService, $cookies:any) {
    
        var queue:any[] = [];
        
        ///////////////////////////////////////////////////
        //check if queue has more elements to process
        ///////////////////////////////////////////////////
        var hasMore: () => boolean = ():boolean => {
            console.log ("Oauth2SecurityService hasMore returning " + (queue.length > 0));
            
            return queue.length > 0;
        };
        
        /////////////////////////////////////////////////////////////////
        //Refresh security token
        /////////////////////////////////////////////////////////////////
        var refreshAccessToken:() => void = () => {
            var url:string = "oauth/authorize?response_type=token&client_id=webui&redirect_uri=../extractToken.html&ut=" + new Date().getTime ();
            
            console.log ("Oauth2SecurityService refreshToken called");
            $("#getTokenFrame").attr ('src',url);   
        };
        
        /////////////////////////////////////////////////////////////////
        //Push request to queue
        /////////////////////////////////////////////////////////////////
        var pushToQueue:(rejection:any) => ng.IPromise<any> = (rejection:any) : ng.IPromise<any> => {
            console.log ("Oauth2SecurityService pushToQueue called for request:" + rejection.config.url);
            
            var defer = $q.defer();
            var retryFunction = ():void => {
                var activeToken = $cookies['accessToken'];
                var config = rejection.config;
                config.headers.Authorization = 'Bearer ' + activeToken;
                
                //cannot inject $http directly because of circular dependency
                $injector.get ('$http') (config).then(
                    function(res) {
                        defer.resolve(res);
                    }, 
                    function(err){
                        defer.reject(err);
                    }
                );
            };
            
            //push request for future processing
            queue.push (retryFunction);
            
            return defer.promise;
        };
        
        /////////////////////////////////////////////////////////////////
        //reprocess all queries stored in queue
        /////////////////////////////////////////////////////////////////
        var processQueue:() => void = () : void => {
        
            console.log ("Oauth2SecurityService processing queue");
            while (hasMore ()) {
                var retryFunction: () => void = queue.shift();
                retryFunction ();
            }
        };
        
        return {
            refreshAccessToken:refreshAccessToken,
            pushToQueue:pushToQueue,
            processQueue:processQueue
        };
    }
    );
    
    ///////////////////////////////////////////////////////////
    //Http interceptor
    ///////////////////////////////////////////////////////////
    app.factory('WebUiAuthenticationHttpInterceptor', function($q, $location, $cookies, Oauth2SecurityService) {

        //add access token to any request
        var request = function (config) {
            config.headers['Authorization'] = "Bearer " + $cookies['accessToken'];
            return config;
        }
        
        //////////////////////////////////////////////////////////////
        //if request was rejected check if access token is missing
        //and if so, refresh tokens and reapply request
        //////////////////////////////////////////////////////////////
        var error = function (rejection) {
        
            var rc:ng.IPromise<any> = null;
            
            //console.log ("WebUiAuthenticationHttpInterceptor called on error:" + angular.toJson (rejection));
            
            if (rejection && rejection.data && rejection.data.error && 
                (rejection.data.error == "invalid_token" || rejection.data.error == "token_expired"
                || rejection.data.error == "token_not_found") && 
                rejection.status && rejection.status == 401){
                console.log ("WebUiAuthenticationHttpInterceptor: require new tokens");
                
                //queue the request and refresh token
                rc = Oauth2SecurityService.pushToQueue (rejection);
                Oauth2SecurityService.refreshAccessToken ();
                
            }
            else{
                rc = $q.reject(rejection);
            };
            
            return rc;
        };
    
        var response = function (response) {
        
            if (response && response.data && response.data.indexOf && response.data.indexOf ("<html><head><title>Login Page</title>") > -1){
                 top.location.href = 'login';
            };
            return response;
        }
        
        return {
            request:request,
            response:response,
            responseError:error
        }
    });
    
    app.config(['$httpProvider', function($httpProvider) {
        $httpProvider.interceptors.push('WebUiAuthenticationHttpInterceptor');
    }]);    
    
    ///////////////////////
    //controller
    ///////////////////////
    app.controller("MainController", ["$rootScope", "$scope", "$cookies",  "$http", "Oauth2SecurityService", MainAppController]);
    
	angular.bootstrap($(document.body), ['WebUi', 'tonyGridDirectiveModule']);
}


function MainAppController ($rootScope, $scope, $cookies,  $http, Oauth2SecurityService) {
    
    $scope.setAccessToken=(token:string, refreshInFuture:boolean, expiresIn:number):void=> {
        $cookies ['accessToken']=token;
        Oauth2SecurityService.processQueue ();
        if (refreshInFuture){
            //setTimeout (function () {$scope.refreshAccessToken ();}, (expiresIn - 600) * 1000);
        };
        
    };
};