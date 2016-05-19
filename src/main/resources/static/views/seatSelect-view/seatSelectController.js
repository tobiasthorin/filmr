'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService) {

        $scope.theaterRows = [];
        $scope.currentShowing = {};
        $scope.selectedSeats = [];

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

        $scope.toggleSelection = function (id) {

            var seatIndex = jQuery.inArray(id, $scope.selectedSeats);
            console.log(seatIndex);

            if(seatIndex < 0){
                console.log("select");
                selectSeat(id);
            } else {
                console.log("unselect");
                unselectSeat(seatIndex);
            }

        };

        function selectSeat(id) {
            $scope.selectedSeats.push(id);
        }

        function unselectSeat(id) {

        }

        //Run on page load
        $scope.fetchShowing();
    }]);