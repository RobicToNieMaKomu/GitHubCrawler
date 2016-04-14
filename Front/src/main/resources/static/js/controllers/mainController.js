angular.module('githubCrawler').controller(
		'mainController',
		[
				'$scope',
				'restService',
				'graphService',
				'csLayoutFactory',
				function($scope, restService, graphService, csLayoutFactory) {
                    var that = this;
					this.recentSearches = [];
					this.recent = this.recentSearches[0];
					this.depthOfSearch = ['1', '2', '3'];
                    this.depth = this.depthOfSearch[0];
                    this.username = '';
                    this.showCytoscape = function(flag) {
                        that.isCsVisible = flag;
                    };
                    this.isCsVisible = false;

					this.set = function(what, event) {
						this[what] = event;
					};
					this.load = function(what) {
                        if (what === 'crawler') {
                            restService.getGraph(this.username, this.depth, graphService.drawGraph, this.showCytoscape);
                        } else if (what === 'fullTopology') {
                            restService.getFullTopology(graphService.drawGraph, that.showCytoscape);
                        } else if (what === 'recentSearches') {
                            var username = this.recent.split(":")[0];
                            var depth = this.recent.split(":")[1];
                            restService.getGraph(username, depth, graphService.drawGraph, that.showCytoscape);
                        }
                    };
                    this.setLayout = function(newLayout) {
                                this.currLayout = newLayout;
                                graphService.redraw(newLayout);
                            };
                    this.layouts = csLayoutFactory.allLayouts();
                    this.currLayout = csLayoutFactory.getDefault();

					$scope.$watch('type', function(newValue, oldValue) {
                        if (newValue === 'recentSearches') {
                           restService.getRecentSearches($scope.mainCtl);
                        }
                        that.showCytoscape(false);
                    });
				}
		]
);

