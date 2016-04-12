angular.module('githubCrawler').service('restService',
    function($http) {
        var http = $http;
        var baseUrl = 'http://localhost:8081/front';
        var rsUrl = baseUrl + '/getRecent';
        var ftUrl = baseUrl + '/fullTopology';
        var ggUrl = baseUrl + '/getGraph?';
        this.getRecentSearches = function(mainCtrl) {
            $http.get(rsUrl)
                .success(function (data) {
                    mainCtrl.recentSearches.splice(0, mainCtrl.recentSearches.length);
                    for (var item in data) {
                        var tuple = data[item];
                        mainCtrl.recentSearches.push(tuple[0] + ':' + tuple[1]);
                    }
                    mainCtrl.recent = mainCtrl.recentSearches.length > 0 ? mainCtrl.recentSearches[0] : '';
                });
        };
        this.getFullTopology = function(onSuccess, showCytoscape) {
            showCytoscape(true);
            $http.get(ftUrl)
                .success(function (data) {
                    onSuccess(data);
                })
                .error(function(evt) {
                    showCytoscape(false);
                });;
        };
        this.getGraph = function(username, depth, onSuccess, showCytoscape) {
            showCytoscape(true);
            $http.get(ggUrl + 'user=' + username + "&depth=" + depth)
                .success(function (data) {
                    onSuccess(data);
                })
                .error(function(evt) {
                    showCytoscape(false);
                });
        };
    }
);