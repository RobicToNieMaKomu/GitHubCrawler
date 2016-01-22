angular.module('githubCrawler').directive('ngGraphPanel',
  function() {
      return {
        scope: {
              title: '@',
              subTitle: '@',
              showForm: '@',
              type: '@'
        },
        controller: 'mainController',
        controllerAs: 'mainCtl',
        restrict: "EA",
        templateUrl: 'templates/ng-graph-panel.html'
      }
});