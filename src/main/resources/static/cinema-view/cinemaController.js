angular.module('filmr')
.controller('cinemaController', ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource',
	function($location,$rootScope, $scope, $routeParams, MovieService, $resource) {

	$scope.moviesToBeAbleToAdd = [];

	console.log("test: "+$resource);

	$scope.removeMovieFromRepertoire = function() {
	}

	function getMoviesToBeAbleToAdd() {

		MovieService.getAllMovies().then(
			// on success
			function(response) {

				var moviesArray = response.data;
				$scope.moviesToBeAbleToAdd = moviesArray;

			}, // on error
			function(error) {
				console.log("error!", error);
			}
		);

	}
}]);
