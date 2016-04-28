
angular.module('filmr')
    .controller('showingController',
        ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource', 'RepertoireService','CinemaService','ShowingService',
        function($location,$rootScope, $scope, $routeParams, MovieService, $resource, RepertoireService, CinemaService) {

            $scope.moviesInRepetoire = [];
            $scope.theatersInCinema = [];

            getTheatersAndRepertoireInCinema();
            //getMoviesInRepertoire();
            //getTheatersInCinema();

            $scope.addShowingToTheater = function() {
                console.log("---");
                console.log("call add showing to theater");
                console.log("showing to add"+ $scope.showingToAdd);

            }


            /*function getMoviesInRepertoire(){
                console.log("---");
                console.log("call get repertoire");
                var repertoire = CinemaService.get({id:1}, function(){
                    console.log("get repetoire: ");
                    $scope.moviesInRepetoire = repertoire.movies;
                })
            }*/

            function getTheatersAndRepertoireInCinema() {
                console.log("---");
                console.log("call get cinema");
                var cinema = CinemaService.get({id:1}, function(){
                    console.log("current Cinema");
                    $scope.theatersInCinema = cinema.theaters;
                    $scope.moviesInRepetoire= cinema.repertoire.movies;
                })
            }

        }]);