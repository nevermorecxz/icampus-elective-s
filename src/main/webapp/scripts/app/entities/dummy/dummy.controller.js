'use strict';

angular.module('icampusApp')
    .controller('DummyController', function ($scope, Dummy, ParseLinks) {
        $scope.dummys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Dummy.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.dummys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.dummys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Dummy.get({id: id}, function(result) {
                $scope.dummy = result;
                $('#saveDummyModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.dummy.id != null) {
                Dummy.update($scope.dummy,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Dummy.save($scope.dummy,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Dummy.get({id: id}, function(result) {
                $scope.dummy = result;
                $('#deleteDummyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Dummy.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteDummyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveDummyModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dummy = {name: null, age: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
