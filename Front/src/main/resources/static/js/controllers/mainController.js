angular.module('githubCrawler').controller(
		'mainController',
		[
				'$scope',
				'restService',
				'graphService',
				function($scope, restService, graphService) {
					console.log('mainController!' + $scope.type);
					this.scope = $scope;
					this.recentSearches = [ 'a', 'b', 'c' ];
					this.recent = this.recentSearches[0];
					this.depthOfSearch = ['1', '2', '3'];
                    this.depth = this.depthOfSearch[0];
                    this.username = '';

					this.set = function(what, event) {
						this[what] = event;
					};
					this.load = function(what) {
                        if (what === 'crawler') {
                            restService.getGraph(this.username, this.depth, graphService.drawGraph);
                        } else if (what === 'fullTopology') {
                            restService.getFullTopology(graphService.drawGraph);
                        } else if (what === 'recentSearches') {
                            var username = this.recent.split(":")[0];
                            var depth = this.recent.split(":")[1];
                            restService.getGraph(username, depth, graphService.drawGraph);
                        }
                    };

					$scope.$watch('type', function(newValue, oldValue) {
                            if (newValue === 'recentSearches') {
                               restService.getRecentSearches($scope.mainCtl); // aaarg ?
                            }
                        });
				} ]);

