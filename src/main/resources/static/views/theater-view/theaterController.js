angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
        function ($rootScope, $scope, $routeParams, $location, TheaterService) {

            //Scoped variables
            $scope.defaultWidth = 0;
            $scope.defaultDepth = 0;

            //Scoped functions

            $scope.fetchTheater = function () {
                console.log("Id " + $routeParams.theater_id + " specified.");

                TheaterService.get({id: $routeParams.theater_id}).$promise.then(
                    //success
                    function (result) {
                        $scope.orginal_name = result.name;
                        $scope.name = result.name;
                        $scope.isDisabled = result.disabled;
                        $scope.theaterWidth = result.rows[0].seats.length;
                        $scope.theaterDepth = result.rows.length;
                    },
                    //fail
                    function (err) {
                        $rootScope.errorHandler(err);
                    });
            };

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
                    setWidthAndHeightFromParams();
                } else {
                    setDefaultWidthAndHeight();
                }
            };

            function setWidthAndHeightFromParams() {
                $scope.theaterWidth = $routeParams.width;
                $scope.theaterDepth = $routeParams.depth;
            }

            function setDefaultWidthAndHeight() {
                $scope.theaterWidth = $scope.defaultWidth;
                $scope.theaterDepth = $scope.defaultDepth;
            }

            //Run on page load
            if (!$routeParams.theater_id) {
                $scope.newTheater();
            } else {
                $scope.fetchTheater();
            }

        }]);
