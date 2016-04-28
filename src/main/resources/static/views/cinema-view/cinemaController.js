angular.module('filmr')
.controller('cinemaController', ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource', 'RepertoireService','CinemaService',
	function($location,$rootScope, $scope, $routeParams, MovieService, $resource, RepertoireService, CinemaService) {

	//VARIABLES
	$scope.addableMovies = [];
	$scope.moviesInRepetoire = [];
	$scope.cinema = null;

	//INIT
	getCurrentCinema(function(){
		getMoviesInRepetoire();
		getAddableMovies();
	});


	// PUBLIC
	$scope.removeMovieFromRepertoire = function() {

	}

	$scope.addMovieToRepertoire = function() {

		console.log("---");
		console.log("call add movie to repertoire");
		console.log("movie to add:")
		console.log($scope.movieToAdd);
		console.log("repertoire:")
		console.log($scope.cinema.repertoire);


		// Get a "entry-based" epertoire, Push movie to it and Save,
		RepertoireService.get({id:$scope.cinema.repertoire.id}).$promise.then(

			// success
			function(result) {

				repertoireEntry = result;

				repertoireEntry.movies.push($scope.movieToAdd);
				RepertoireService.save(repertoireEntry).$promise.then(

					// success
					function(result) {
						console.log("movie added to repertoire");
						getAddableMovies();
						getMoviesInRepetoire();
					},
					// error
					function(error){ 
						$rootScope.errorHandler(error);
					}
				);
			},
			// error
			function(error) {
				$rootScope.errorHandler(error);
			}
		);
		// -----


	}

	// PRIVATE
	function getCurrentCinema(callbackWhenDone) {
		console.log("---");
		console.log("call get current cinema");
		var cinema = CinemaService.get({id:1}, function(){
			console.log("current cinema: ");
			$scope.cinema = cinema;
			callbackWhenDone();
		});
	}

	function getAddableMovies() {

		MovieService.query().$promise.then(
			// success
			function(result){
				$scope.addableMovies = result;
			},
			// error
			function(error) {
				$rootScope.errorHandler(error);
			}
		);
	}

	function getMoviesInRepetoire() {
		RepertoireService.get({id:$scope.cinema.repertoire.id}).$promise.then(
			// success
			function(result) {
				repertoireEntry = result;
				$scope.moviesInRepetoire = repertoireEntry.movies;
			},
			// error
			function(error) {
				$rootScope.errorHandler(error);
			}
		);
	}
	
}]);
