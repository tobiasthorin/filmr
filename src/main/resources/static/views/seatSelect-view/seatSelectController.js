'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService) {

        $scope.theaterRows = [];
        $scope.currentShowing = {};
        $scope.selectedSeats = new Set();
        $scope.bookedSeats = new Set;

        $scope.fetchShowing = function () {
            ShowingService.get({id: $routeParams.showingId}).$promise.then(
                function (result) {
                    $scope.currentShowing = result;
                    $scope.theaterRows = result.theater.rows;
                    findBookedSeats();
                },
                function () {
                    //fail
                }
            )
        };

        $scope.toggleSelection = function (id) {

            if (!$scope.bookedSeats.has(id)) {

                if ($scope.selectedSeats.has(id)) {
                    unselectSeat(id);
                } else {
                    selectSeat(id);
                }
            }
        };

        $scope.checkIfSelected = function (id) {
            return $scope.selectedSeats.has(id);
        };

        $scope.checkIfBooked = function (id) {
            return $scope.bookedSeats.has(id);
        };

        function selectSeat(id) {
            $scope.selectedSeats.add(id);
        }

        function unselectSeat(id) {
            $scope.selectedSeats.delete(id);
        }

        function findBookedSeats() {
            for (var i = 0; i < $scope.currentShowing.bookings.length; i++) {
                for (var j = 0; j < $scope.currentShowing.bookings[i].bookedSeats.length; j++) {
                    $scope.bookedSeats.add($scope.currentShowing.bookings[i].bookedSeats[j].id);
                }
            }
        }

        //Run on page load
        $scope.fetchShowing();
    }]);