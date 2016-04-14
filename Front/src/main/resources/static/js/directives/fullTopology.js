angular.module('githubCrawler').directive('ngFullTopology',
  function() {
      return {
        require: '^^graphPanel',
        restrict: 'E',
        templateUrl: 'templates/topology.html'
      }
});