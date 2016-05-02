
angular.module('filmr')
    .controller('showingController',
        ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource', 'RepertoireService','CinemaService','ShowingService',
        function($location,$rootScope, $scope, $routeParams, MovieService, $resource, RepertoireService, CinemaService, ShowingService) {

            //Variables
            $scope.moviesInRepertoire = [];
            $scope.theatersInCinema = [];
            $scope.allShowings = [];

            //INIT
            getTheatersAndRepertoireInCinema();
            getAllShowings();

            //PUBLIC
            $scope.createShowing = function() {
                console.log("---");
                console.log("call add showing to theater");
                console.log($scope.movie);
                console.log($scope.theater);
                console.log($scope.date);
                var newShowing = new ShowingService();
                newShowing.movie = $scope.movie;
                newShowing.theater =$scope.theater;
                newShowing.showDateTime = $scope.date;

                ShowingService.save(newShowing, function(){
                    console.log("Saved!");
                    getAllShowings();
                });

                console.log("showing to add"+ newShowing);
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



        }]);