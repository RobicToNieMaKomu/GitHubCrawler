angular.module('githubCrawler').directive('ngPanel',
  function() {
      return {
        scope: {
              title: '@',
              subTitle: '@',
              showForm: '@',
              type: '@',
              showCanvas: '@'
        },
        controller: 'mainController',
        controllerAs: 'mainCtl',
        restrict: "E",
        templateUrl: 'templates/graphPanel.html'
      }
});