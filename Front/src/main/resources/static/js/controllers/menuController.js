angular.module('githubCrawler').controller(
		'menuController',
		[
				'$scope',
				'$http',
				'$modal',
				function($scope, $http, $modal) {
				    console.log('menu ctrl!');
				    this.scope = $scope;
				    $scope.showCrawler=true;
				    $scope.showRecentSearches=false;
				    $scope.showTopology=false;
				    this.currentlyOpenTab = 'crawler';
				    this.render = function(what) {
				        if (this.currentlyOpenTab === what) {
				            console.log('what do you want fool? ' + what);
				        } else {
				            var openTab = 'show' + this.capitalizeFirstLetter(what);
				            var closeTab = 'show' + this.capitalizeFirstLetter(this.currentlyOpenTab);
				            this.scope[closeTab] = !this.scope[closeTab];
				            this.scope[openTab] = !this.scope[openTab];
				            this.currentlyOpenTab = what;
				        }
				    }
				    this.capitalizeFirstLetter = function(string) {
                        return string.charAt(0).toUpperCase() + string.slice(1);
                    }
				}
		]
);