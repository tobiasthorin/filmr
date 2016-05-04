
angular.module('filmr')
    .controller('showingController',
        ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource', 'RepertoireService','CinemaService','ShowingService',
        function($location,$rootScope, $scope, $routeParams, MovieService, $resource, RepertoireService, CinemaService, ShowingService) {

            //Variables
            $scope.moviesInRepertoire = [];
            $scope.theatersInCinema = [];
            $scope.allShowings = [];



            //Showing Filter Parameters

            $scope.theater = {};
            $scope.movie = {};




            //INIT
            getTheatersAndRepertoireInCinema();
            getShowingsWithParams();

            //PUBLIC

            $scope.updateShowings = function() {
                console.log("---");
                console.log("updating Showings List");


                getShowingsWithParams();
            }

            $scope.createShowing = function() {
                console.log("---");
                console.log("call add showing to theater");
                var newShowing = new ShowingService();
                newShowing.movie = $scope.movie;
                newShowing.theater =$scope.theater;
                newShowing.showDateTime = $scope.date;

                ShowingService.save(newShowing, function(result){
                    console.log("Saved!");
                    console.log(result);
                    getAllShowings();
                },
                function(error){
                    console.log(error);
                });

                console.log("showing to add");
                console.log(newShowing);
            }



            function getTheatersAndRepertoireInCinema() {
                console.log("---");
                console.log("call get cinema");
                CinemaService.get({id:1}).$promise.then(
                    function(result){
                        var cinema = result;
                        console.log("Set Movies and Theaters lists")
                        $scope.moviesInRepertoire = cinema.repertoire.movies;
                        $scope.theatersInCinema = cinema.theaters;

                    },
                    function(error){
                        $rootScope.errorHandler(error);
                    }

                )

            }

            function getAllShowings(){
                console.log("Get Showings")
                ShowingService.query().$promise.then(
                    function (result) {
                        console.log("in all showings ");
                        console.log(result);
                        $scope.allShowings = result;
                    },
                    function(error){
                        $rootScope.errorHandler(error);
                    }
                )
            }

            function getShowingsWithParams(){
                var params = {
                    "only_for_theater_with_id" : $scope.theater.id,
                    "only_for_movie_with_id" : $scope.movie.id,
                    "from_date" : $scope.fromDate,
                    "to_date" : $scope.toDate,
                    "include_empty_slots_for_movie_of_length" : $scope.movie.lengthInMinutes
                }
                console.log(params);
                console.log("Get Showings with Params");
                ShowingService.query(params).$promise.then(
                    function (result){
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