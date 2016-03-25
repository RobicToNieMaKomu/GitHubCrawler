angular.module('githubCrawler').controller(
		'mainController',
		[
				'$scope',
				'$http',
				function($scope, $http) {
					console.log('mainController!' + $scope.type);
					var range = [ 'a', 'b', 'c' ];
					this.scope = $scope;
					this.http = $http;
					this.recentSearches = range;
					this.recent = this.recentSearches[0];
					this.depthOfSearch = ['1', '2', '3'];
                    this.depth = this.depthOfSearch[0];

					this.showCytoscape = function() {
					    this.scope.showForm = !this.scope.showForm;
					    this.scope.showCanvas = !this.scope.showCanvas;
					}
					this.request = function() {
					    var url = "http://localhost:8081/front"
						if (this.scope.type === 'crawler') {
						    console.log("crawler");
						    url += '/getGraph?user=' + $('#username').val() + '&depth=' + this.depth;
						} else if (this.scope.type === 'fullTopology') {
						    console.log("fullTopology");
						    url += '/fullTopology'
						} else if (this.scope.type === 'recentGraphs') {
						    console.log("recentSearches");
						    drawRecent()
						}
						console.log(url);
						this.showCytoscape();
						//loadGraph(this.scope, this.http, url);
					};

					this.set = function(what, event) {
						this[what] = event;
					};
					var drawRecent = function(searchName) {
					};
					var loadRecentSearches = function($scope, $http) {
					    var url = "http://localhost:8081/front/getRecent";
					    $http.get(url)
					    .success(function (data) {
					        console.log("grejt success!" + data);
					    })
					    .error(function (evt) {
					        console.log("uber fejl!" + evt);
					    });
					};
					// initial request
					loadRecentSearches(this.scope, this.http);
				} ]);

