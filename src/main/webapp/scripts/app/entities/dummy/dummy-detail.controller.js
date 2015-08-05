'use strict';

angular.module('icampusApp')
    .controller('DummyDetailController', function ($scope, $stateParams, Dummy) {
        $scope.dummy = {};
        $scope.load = function (id) {
            Dummy.get({id: id}, function(result) {
              $scope.dummy = result;
            });
        };
        $scope.load($stateParams.id);
    });
