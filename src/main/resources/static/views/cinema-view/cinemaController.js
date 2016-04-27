angular.module('filmr')
.controller('cinemaController', ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource',
	function($location,$rootScope, $scope, $routeParams, MovieService, $resource) {

	$scope.addableMovies = [];

	getAddableMovies();

	$scope.removeMovieFromRepertoire = function() {

	}

	function getAddableMovies() {
		var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";
		$scope.addableMovies = MovieService.query();

	}
}]);
