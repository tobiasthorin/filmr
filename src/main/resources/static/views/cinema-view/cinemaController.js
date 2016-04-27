angular.module('filmr')
    .controller('cinemaController', ['$rootScope', '$scope', 'MovieService', 'TheaterService', '$resource',
        function ($rootScope, $scope, MovieService, TheaterService, $resource) {

            $scope.addableMovies = [];

            getAddableMovies();
            getListOfTheaters();

            function getAddableMovies() {
                var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";
                $scope.addableMovies = MovieService.query();
            }

            function getListOfTheaters() {
                var theatersBaseUrl = $rootScope.API_baseUrl + "theaters/";
                $scope.theaters = TheaterService.query();
            }

        }]);
