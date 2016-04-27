angular.module('filmr')
.controller('cinemaController', ['$location','$rootScope', '$scope', '$routeParams', 'MovieServicee','$resource',
	function($location,$rootScope, $scope, $routeParams, MovieServicee, $resource) {

	$scope.moviesToBeAbleToAdd = [];

	getMoviesToBeAbleToAdd();

	$scope.removeMovieFromRepertoire = function() {



	}

	function getMoviesToBeAbleToAdd() {

		var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";

		MovieServicee;
		console.log(MovieServicee);

		console.log("getMoviesToBeAbleToAdd:");
		console.log(moviesBaseUrl);
		var o = $resource(moviesBaseUrl);
		var i = o.query();
		console.log(o)
		console.log(i)


//		$scope.moviesToBeAbleToAdd = ??;
		/*
		MovieService.getAllMovies().then(
			// on success
			function(response) {

				var moviesArray = response.data;
				$scope.moviesToBeAbleToAdd = moviesArray;

			}, // on error
			function(error) {
				console.log("error!", error);
			}
		);*/

	}
}]);
