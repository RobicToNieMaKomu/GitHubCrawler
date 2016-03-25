angular.module('githubCrawler').directive('ngCrawler',
  function() {
      return {
        require: '^^graphPanel',
        restrict: 'E',
        templateUrl: 'templates/crawler.html'
      }
});