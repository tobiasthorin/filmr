'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService) {

        $scope.theaterRows = [];
        $scope.currentShowing = {};

        $scope.fetchShowing = function () {
            console.log($routeParams.showingId);

            ShowingService.get({id: $routeParams.showingId}).$promise.then(
                function (result) {
                    $scope.currentShowing = result;
                    $scope.theaterRows = result.theater.rows;
                },
                function () {
                    //fail
                }
            )
        };

        //Run on page load
        $scope.fetchShowing();

    }]);