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
				    this.render = function(what) {
				        console.log('what do you want fool? ' + what);
				        this.scope['show' + this.capitalizeFirstLetter(what)] = !this.scope['show' + this.capitalizeFirstLetter(what)];
				    }
				    this.capitalizeFirstLetter = function(string) {
                        return string.charAt(0).toUpperCase() + string.slice(1);
                    }
				}


		]
);