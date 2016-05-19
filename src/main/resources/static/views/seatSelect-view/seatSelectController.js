'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService) {

        $scope.theaterRows = [];

        $scope.fetchShowing = function () {
            console.log($routeParams.showingId);

            ShowingService.get({id: $routeParams.showingId}).$promise.then(
                function (result) {
                    console.log(result);
                },
                function () {
                    //fail
                }
            )
        };

        //Run on page load
        $scope.fetchShowing();

    }]);