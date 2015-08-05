'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dummy', {
                parent: 'entity',
                url: '/dummy',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Dummys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dummy/dummys.html',
                        controller: 'DummyController'
                    }
                },
                resolve: {
                }
            })
            .state('dummyDetail', {
                parent: 'entity',
                url: '/dummy/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Dummy'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dummy/dummy-detail.html',
                        controller: 'DummyDetailController'
                    }
                },
                resolve: {
                }
            });
    });
