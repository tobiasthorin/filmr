'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService) {

        $scope.theaterRows = [];
        $scope.currentShowing = {};
        $scope.selectedSeats = new Set();

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

            if($scope.selectedSeats.has(id)){
                console.log("unselect " + id);
                unselectSeat(id);
            } else {
                console.log("select " + id);
                selectSeat(id);
            }

        };
        
        $scope.checkIfSelected = function (id) {
            return $scope.selectedSeats.has(id);
        };

        function selectSeat(id) {
            $scope.selectedSeats.add(id);
        }

        function unselectSeat(id) {
            $scope.selectedSeats.delete(id);
        }

        //Run on page load
        $scope.fetchShowing();
    }]);