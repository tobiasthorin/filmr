'use strict';

angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService', '$log',
        function ($rootScope, $scope, $routeParams, $location, TheaterService, $log) {

            //Used to make sure server is done
            var activeRequest = false;

            $scope.currentTheater = {};
            $scope.theaterRows = {}; //TODO what does this even do?

            //Defaults and limits - In a perfect world these should be loaded from admin settings
            var nameLimit = 48;
            var lowerLimit = 0;
            var rowLimit = 64;
            var seatLimit = 64;

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
            $scope.rowResetEdit; //Not needed?
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
                        $scope.theaterWidthEdit = $scope.theaterWidth;

                        if (!result.rows.length) {
                            $scope.theaterDepth = $scope.defaultDepth;
                        } else {
                            $scope.theaterDepth = result.rows.length;
                        }
                        $scope.theaterDepthEdit = $scope.theaterDepth;

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
                $scope.theaterWidthEdit--;
                if(!$scope.validateSeatInput($scope.theaterWidthEdit)) {
                    $rootScope.genericError();
                    $scope.theaterWidthEdit++;
                    return;
                }
                $scope.theaterWidth = $scope.theaterWidthEdit;
    	        $scope.updateTheater();
	        };

            $scope.updateTheater = function () {

                //No validation here as every SET method is expected to do this
                $rootScope.clearAlerts();
                if(!activeRequest){
                    activeRequest = true;

                    //Set
                    $scope.currentTheater.name = $scope.theaterName;

                    var updateParams = {
                        new_number_of_rows: $scope.theaterDepth,
                        new_max_row_size: $scope.theaterWidth,
                        reset_seat_numbers_for_each_row: $scope.rowReset
                    };

					TheaterService.update(updateParams,$scope.currentTheater).$promise.then(
						function (result) {
							//success
							activeRequest = false;

							$scope.currentTheater = result;
							$scope.currentTheater.cinema = {id: result.cinemaId};
                            $scope.theaterName = result.name;
                            $scope.theaterNameEdit = result.name;

							updateRows();
                            $rootScope.alert("Success! ","Theater was updated",1);
						},
						function (error) {
                            //fail
							activeRequest = false;
							$rootScope.errorHandler(error);
						});
				}

            };


            //VALIDATION
            $scope.validateNameInput = function() {
                if(typeof $scope.theaterNameEdit != "string")
                    return false;
                if(!($scope.theaterNameEdit.length > 0 && $scope.theaterNameEdit.length <= nameLimit))
                    return false;
                return true;
            };

            $scope.validateRowInput = function (value) { //TODO return here; use edit?

                value = typeof value == "undefined" ? 0 : value;

                if(!(typeof value == "number"))
                    return false;
                if(typeof value == "number" && value <= lowerLimit)
                    return false;
                if(typeof value == "number" && value > rowLimit)
                    return false;
                return true;
            };

            $scope.validateSeatInput = function (value) { //TODO use edit? rewrite?

                value = typeof value == "undefined" ? 0 : value;

                if(!(typeof value == "number"))
                    return false;
                if(typeof value == "number" && value <= lowerLimit)
                    return false;
                if(typeof value == "number" && value > seatLimit)
                    return false;
                return true;
            };

            $scope.setRowReset = function (value) {
                //TODO if Edit is used use it here
                $scope.rowReset = value;
                $scope.updateTheater();
            };

            //TODO never copied setDefaultWidthAndDepth as they are never used

            function updateRows() {
                $scope.theaterRows = $scope.currentTheater.rows;
            }


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

            //Run on page load
            if ($routeParams.theater_id === 'new') {
                $scope.newTheater();
            } else {
                $scope.fetchTheater();
            }
}]);