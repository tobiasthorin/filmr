app
	.controller('cinemaController', ['$location', '$rootScope', '$scope', '$routeParams', 'MovieService', 'TheaterService', '$resource', 'RepertoireService', 'CinemaService',
		function ($location, $rootScope, $scope, $routeParams, MovieService, TheaterService, $resource, RepertoireService, CinemaService) {

	var moviesInRepertoire;
	var addableMovies;
	var currentCinema;

	fetchAddableMovies();
	fetchCurrentCinema();

	$scope.getMoviesInRepertoire = function() {
		return moviesInRepertoire;
	}

	$scope.getAddableMovies = function() {
		return addableMovies;
	}

	function fetchMoviesInRepertorie() {


		var id = currentCinema.repertoire.id;
		RepertoireService.get({"id":id}).$promise.then(
			// success
			function(result){
				moviesInRepertoire = result.movies;
			},
			// error
			function(error) {
				$rootScope.errorHandler(error);
			}
		);
		

	};

	function fetchCurrentCinema() {

		var id = $routeParams.id;
		

		CinemaService.get({"id":id}).$promise.then(

			function(result) {
				console.log("current cinema: ");
				currentCinema = result;
				fetchMoviesInRepertorie();
			},
			function() {

			});
	}

	function fetchAddableMovies() {


		
		MovieService.query().$promise.then(
			// success
			function(result){

				addableMovies = result;
			},
			// error
			function(error) {
				$rootScope.errorHandler(error);
			}
		);
	};

	$scope.addMovieToRepertoire = function() {


		var repertoireId = currentCinema.repertoire.id;
		var movieId = $scope.add_movie_to_repertoire_select.id;


		var updateParams = {"id":repertoireId,"add_movie_with_id":movieId};
		var updateBody = {"id":repertoireId};


		RepertoireService.update(updateParams,updateBody).$promise.then(
			function(result){
				moviesInRepertoire = result.movies;
			},
			function(){
				$rootScope.errorHandler(error);
			}
		);
	}

	$scope.removeMovieFromRepertoire = function(movieId) {
		

		var repertoireId = currentCinema.repertoire.id;


		var updateParams = {"id":repertoireId,"remove_movie_with_id":movieId};
		var updateBody = {"id":repertoireId};


		RepertoireService.update(updateParams,updateBody).$promise.then(
			function(result){
				moviesInRepertoire = result.movies;
			},
			function(){
				$rootScope.errorHandler(error);
			}
		);

	}

/*
	//VARIABLES
	$scope.addableMovies = [];
	$scope.moviesInRepetoire = [];
	$scope.cinema = null;

	//INIT
	getCurrentCinema(function(){
		getMoviesInRepetoire();
		getAddableMovies();
	});
	getListOfTheaters();


	// PUBLIC
	$scope.removeMovieFromRepertoire = function() {
		return 123; // TODO: just a dummy from test
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
		CinemaService.get({id:1}, function(cinema){
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

		function getListOfTheaters() {
			$scope.theaters = TheaterService.query();
		}
	*/
}]);
