angular.module('filmr')
.controller('cinemaController', ['$location','$rootScope', '$scope', '$routeParams', 'MovieService','$resource', 'RepertoireService','CinemaService',
	function($location,$rootScope, $scope, $routeParams, MovieService, $resource, RepertoireService, CinemaService) {

	$scope.addableMovies = [];
	$scope.moviesInRepetoire = [];
	$scope.cinema = null;

	getCurrentCinema();
	getAddableMovies();

	$scope.removeMovieFromRepertoire = function() {

	}

	$scope.addMovieToRepertoire = function() {

		console.log("---");
		console.log("call add movie to repertoire");
		console.log("movie to add "+$scope.movieToAdd);

		var repertoire = new RepertoireService();
		
		repertoire.save();		
	}

	function getCurrentCinema() { //TODO:..and update movies in repetoir
		console.log("---");
		console.log("call get current cinema");
		var cinema = CinemaService.get({id:1}, function(){
			console.log("current cinema: ");
			$scope.cinema = cinema;
			getMoviesInRepetoire();
		});
	}

	function getAddableMovies() {
		$scope.addableMovies = MovieService.query();
	}

	function getMoviesInRepetoire() {
		$scope.moviesInRepetoire = $scope.cinema.repertoire.movies;
	}

}]);
