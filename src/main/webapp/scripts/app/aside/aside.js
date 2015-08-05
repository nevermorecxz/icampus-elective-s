/*
'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aside', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'aside@':{
                        templateUrl: 'scripts/app/aside/aside.html',
                        controller: 'AsideController'
                }

                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('aside');
                        return $translate.refresh();
                    }]
                }
            });
    });
*/

angular.module('icampusApp')
    .controller('AsideController', function($scope, $aside) {
        var asideInstance = $scope.openAside = function openAside(position) {
            $aside.open({
                placement: position,
                templateUrl: 'scripts/app/aside/aside.html',
                size: 'lg'
            });
        };

    });
