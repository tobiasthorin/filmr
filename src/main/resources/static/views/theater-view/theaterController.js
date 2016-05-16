'use strict';

angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
        function ($rootScope, $scope, $routeParams, $location, TheaterService) {

            //Scoped variables
            $scope.defaultWidth = 5;
            $scope.defaultDepth = 10;

            $scope.currentTheater = {};
            $scope.theaterRows = {};

            //Scoped functions

            $scope.fetchTheater = function () {
                console.log("Id " + $routeParams.theater_id + " specified.");

                TheaterService.get({id: $routeParams.theater_id}).$promise.then(
                    //success
                    function (result) {
                        $scope.original_name = result.name;
                        $scope.currentTheater = result;
                        
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
                        $rootScope.errorHandler(err);
                    });
            };
            //TODO fix
            $scope.submitTheater = function () {
                console.log("Submitting edited theater...");
                console.log($scope.name + ", id: " + $routeParams.theater_id);

                $scope.newTheater = {};

                $scope.newTheater.id = $routeParams.theater_id;
                $scope.newTheater.name = $scope.name;
                $scope.newTheater.numberOfSeats = $scope.numberOfSeats;
                $scope.newTheater.disabled = $scope.isDisabled;
                $scope.newTheater.cinema = {id: $routeParams.cinema_id};

                TheaterService.update($scope.newTheater).$promise.then(
                    function () {
                        //success
                        console.log("Updated!");
                        $location.path('/cinema/' + $routeParams.cinema_id);
                    },
                    function () {
                        //fail
                    });
            };

            $scope.newTheater = function () {
                console.log("New theater");
                if ($routeParams.width && $routeParams.depth) {
                    setWidthAndDepthFromParams();
                    createNewTheater();
                } else {
                    setDefaultWidthAndDepth();
                }
            };

            function setWidthAndDepthFromParams() {
                $scope.theaterWidth = $routeParams.width;
                $scope.theaterDepth = $routeParams.depth;
            }

            function setDefaultWidthAndDepth() {
                $scope.theaterWidth = $scope.defaultWidth;
                $scope.theaterDepth = $scope.defaultDepth;
            }

            function createNewTheater() {
                var newTheater = {
                    name: 'New Theater',
                    cinemaId: $routeParams.cinema_id
                };

                var theaterParams = {
                    number_of_rows: $scope.theaterDepth,
                    max_row_size: $scope.theaterWidth
                };

                TheaterService.save(theaterParams, newTheater).$promise.then(
                    function (result) {
                        $scope.currentTheater = result;
                        updateRows();
                    },
                    function (error) {
                        $rootScope.errorHandler(error)
                    });

                $scope.original_name = newTheater.name;
            }

            function updateRows() {
                $scope.theaterRows = $scope.currentTheater.rows;
            }

            //Run on page load
            if ($routeParams.theater_id === 'new') {
                $scope.newTheater();
            } else {
                $scope.fetchTheater();
            }

        }]);
