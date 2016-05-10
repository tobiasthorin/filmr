angular.module('filmr')
    .controller('showingController',
        ['$location', '$rootScope', '$scope', '$routeParams', '$resource', 'CinemaService', 'ShowingService',
            function ($location, $rootScope, $scope, $routeParams, $resource, CinemaService, ShowingService) {

                //Variables
                $scope.moviesInRepertoire = [];
                $scope.theatersInCinema = [];
                $scope.allShowings = [];
                $scope.allCinemas = [];


                //Showing Filter Parameters

                $scope.theater = {};
                $scope.movie = {};
                $scope.cinema = {};

                //INIT
                getCinemas();
                getTheatersAndRepertoireInCinema();
                getShowingsWithParams();

                //PUBLIC
                $scope.updateShowings = function () {
                    console.log("---");
                    console.log("updating Showings List");
                    getShowingsWithParams();
                };

                $scope.disableShowing = function (showing) {
                    console.log("---");
                    console.log("Disable showing with id: " + showing.id);

                    showing.isDisabled = !showing.isDisabled;

                    console.log("Showing has datestring: " + showing.showDateTime);

                    ShowingService.update(showing).$promise.then(
                        function (result) {
                            console.log("showing enabled/disabled");
                            console.log(result);
                            getShowingsWithParams();
                        },
                        function (error) {
                            $rootScope.errorHandler(error);
                            showing.isDisabled = !showing.isDisabled;
                        })
                };

                $scope.validateScheduleNewShowing = function() {
                    if(!$scope.price && $scope.price!=0) return true; //note that zero is false i JS. we want all other "false"-values to be considered none valid
                    if($scope.price<0) return true;
                    return false;
                }

                $scope.createShowing = function () {
                    console.log("---");
                    console.log("call add showing to theater");
                    var newShowing = new ShowingService();
                    newShowing.movie = $scope.movie;
                    newShowing.theater = $scope.theater;
                    newShowing.showDateTime = $scope.date;
                    console.log("Date is: " + newShowing.showDateTime);

                    ShowingService.save(newShowing, function (result) {
                            console.log("Saved!");
                            console.log(result);
                            getShowingsWithParams();
                        },
                        function (error) {
                            console.log(error);
                        });

                    console.log("showing to add");
                    console.log(newShowing);
                };

                function getCinemas() {
                    CinemaService.query().$promise.then(
                        function (result) {
                            console.log("in getCinemas");
                            console.log(result);
                            $scope.allCinemas = result;
                        }
                    )
                }

                function getTheatersAndRepertoireInCinema() {
                    console.log("---");
                    console.log("call get cinema");
                    CinemaService.get({id: 1}).$promise.then(
                        function (result) {
                            var cinema = result;
                            console.log("Set Movies and Theaters lists");
                            $scope.moviesInRepertoire = cinema.repertoire.movies;
                            $scope.theatersInCinema = cinema.theaters;

                        },
                        function (error) {
                            $rootScope.errorHandler(error);
                        }
                    )

                }


                function getShowingsWithParams() {
                    var params = {
                        "only_for_cinema_with_id": $scope.cinema.id,
                        "only_for_theater_with_id": $scope.theater.id,
                        "only_for_movie_with_id": $scope.movie.id,
                        "from_date": $scope.fromDate,
                        "to_date": $scope.toDate,
                        "show_disabled_showings": $scope.showingIsDisabled,
                        "include_empty_slots_for_movie_of_length": $scope.movie.lengthInMinutes
                    };
                    console.log(params);
                    console.log("Get Showings with Params");
                    ShowingService.query(params).$promise.then(
                        function (result) {
                            console.log("in showings with params");
                            console.log(result);
                            $scope.allShowings = result;
                        },
                        function (error) {
                            $rootScope.errorHandler(error);
                        }
                    )
                }

            }]);
