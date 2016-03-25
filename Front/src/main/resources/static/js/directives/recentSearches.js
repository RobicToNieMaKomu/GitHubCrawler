angular.module('githubCrawler').directive('ngRecentSearches',
  function() {
      return {
        require: '^^graphPanel',
        restrict: "E",
        templateUrl: 'templates/recentSearches.html'
      }
});