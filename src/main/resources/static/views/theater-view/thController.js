'use strict';

angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService', '$log',
        function ($rootScope, $scope, $routeParams, $location, TheaterService, $log) {

            //Used to make sure server is done
            var activeRequest = false;

            $scope.currentTheater = {};
            $scope.theaterRows = {}; //TODO what does this even do?

            //Scoped variables
            $scope.theaterWidthDefault = 1;
            $scope.theaterDepthDefault = 1;

            //Actual values. Used by page so must be scope
            $scope.theaterName;
            $scope.rowReset;
            $scope.theaterWidth;
            $scope.theaterDepth;

            //Edit values
            $scope.theaterNameEdit;
            $scope.rowResetEdit;
            $scope.theaterWidthEdit;
            $scope.theaterDepthEdit;



            $scope.fetchTheater = function () {
                $log.info("Id " + $routeParams.theater_id + " specified.");

                TheaterService.get({id: $routeParams.theater_id}).$promise.then(
                    //success
                    function (result) {
                        $scope.theaterName = result.name;
                        $scope.theaterNameEdit = result.name;
                        $scope.currentTheater = result;
                        $scope.currentTheater.cinema = {id: result.cinemaId};
                        $scope.rowReset = !result.usingContinuousSeatLabeling;

                        if (!result.rows[0]) {
                            $scope.theaterWidth = $scope.defaultWidth
                        } else {
                            $scope.theaterWidth = result.rows[0].seats.length;
                        }

                        if (!result.rows.length) {
                            $scope.theaterDepth = $scope.defaultDepth;
                        } else {
                            $scope.theaterDepth = result.rows.length;
                        }

                        updateRows();

                    },
                    //fail
                    function (err) {
                        $rootScope.errorHandler(err); //TODO proper error?
                    });
            };

            //Set
            $scope.setTheaterName = function () {
                $scope.theaterName = $scope.theaterNameEdit;
                //TODO extra validation?
                $scope.updateTheater();
            }

            $scope.addRow = function (){
                $scope.theaterDepthEdit++;
                if(!$scope.validateRowInput($scope.theaterDepthEdit)) {
                    $rootScope.genericError();
                    $scope.theaterDepthEdit--;
                    return;
                }
                $scope.theaterDepth = $scope.theaterDepthEdit;
                $scope.updateTheater();
	        };

            $scope.removeRow = function (){
                $scope.theaterDepthEdit--;
                if(!$scope.validateRowInput($scope.theaterDepthEdit)) {
                    $rootScope.genericError();
                    $scope.theaterDepthEdit++;
                    return;
                };
                $scope.theaterDepth = $scope.theaterDepthEdit;
		        $scope.updateTheater();
	        };

            $scope.addSeats = function() {
                $scope.theaterWidthEdit++;
                if(!$scope.validateSeatInput($scope.theaterWidthEdit)) {
                    $rootScope.genericError();
                    $scope.theaterWidthEdit--;
                    return;
                }
                $scope.theaterWidth = $scope.theaterWidthEdit;
                $scope.updateTheater();
	        };

            $scope.removeSeats = function(){
                $scope.theaterWidth--;
                if(!$scope.validateSeatInput($scope.theaterWidthEdit)) {
                    $rootScope.genericError();
                    $scope.theaterWidthEdit++;
                    return;
                }
                $scope.theaterWidth = $scope.theaterWidthEdit;
    	        $scope.updateTheater();
	        };


            //Validation
            $scope.validateNameInput = function() {
                if(typeof $scope.theaterNameEdit != "string")
                    return false;
                if(!($scope.theaterNameEdit.length > 0 && $scope.theaterNameEdit.length<=48))
                    return false;
                return true;
            };

            //TODO row validation


            $scope.toggleSeatState = function(seat){
                if (!activeRequest) {
                    activeRequest = true;
                    switch (seat.state) {
                        case "ENABLED":
                            seat.state = "DISABLED";
                            break;
                        case "DISABLED":
                            seat.state = "NOT_A_SEAT";
                            break;
                        case "NOT_A_SEAT":
                            seat.state = "ENABLED";
                            break;
                    }
                    activeRequest = false;
                    $scope.updateTheater();
                }
	        };


}