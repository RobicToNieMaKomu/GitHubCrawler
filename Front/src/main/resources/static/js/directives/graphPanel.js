angular.module('githubCrawler').directive('ngPanel',
  function() {
      return {
        scope: {
              title: '@',
              subTitle: '@',
              type: '@'
        },
        controller: 'mainController',
        controllerAs: 'mainCtl',
        restrict: "E",
        templateUrl: 'templates/graphPanel.html'
      }
});