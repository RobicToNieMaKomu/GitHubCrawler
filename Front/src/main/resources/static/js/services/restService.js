angular.module('githubCrawler').service('restService',
    function($http) {
        var http = $http;
        var baseUrl = 'http://localhost:8081/front';
        var rsUrl = baseUrl + '/getRecent';
        var ftUrl = baseUrl + '/fullTopology';
        var ggUrl = baseUrl + '/getGraph?';
        this.getRecentSearches = function() {
            $http.get(rsUrl)
                .success(function (data) {
                    console.log("grejt success!" + data);
                })
                .error(function (evt) {
                    console.log("uber fejl! " + rsUrl + evt);
                });
            return [];
        };
        this.getFullTopology = function(onSuccess) {
            $http.get(ftUrl)
                .success(function (data) {
                    console.log("grejt success!" + data);
                    onSuccess(data);
                })
                .error(function (evt) {
                    console.log("uber fejl! " + ftUrl + evt);
                });
        };
        this.getGraph = function(username, depth, onSuccess) {
            $http.get(ggUrl + 'user=' + username + "&depth=" + depth)
                .success(function (data) {
                    console.log("grejt success!" + data);
                    onSuccess(data);
                })
                .error(function (evt) {
                    console.log("uber fejl! " + ggUrl + evt);
                });
        };
    }
);