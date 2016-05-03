app.controller('cinemaController', ['$scope', '$rootScope', '$routeParams', 'MovieService', 'TheaterService', 'RepertoireService', 'CinemaService',
	function ($scope, $rootScope, $routeParams, MovieService, TheaterService, RepertoireService, CinemaService) {

		var moviesInRepertoire;
		var theaters;
		var currentCinemaId;

		fetchMoviesInRepertoire();
		setCurrentCinemaId();

		$scope.newTheater = {};

		$scope.getMoviesInRepertoire = function () {
			return moviesInRepertoire;
		};

		$scope.getTheaters = function () {
			TheaterService.query().$promise.then(function (result) {
					//success
					theaters = result;
				},
				function () {
					//fail
				});

			return theaters;
		};

		$scope.submitTheater = function () {

			if (!$scope.add_theater_disabled) {
				$scope.add_theater_disabled = false;
			}

			$scope.newTheater.cinemaId = currentCinemaId;
			$scope.newTheater.name = $scope.add_theater_name;
			$scope.newTheater.disabled = $scope.add_theater_disabled;
			$scope.newTheater.numberOfSeats = $scope.add_theater_seats;

			TheaterService.save($scope.newTheater).$promise.then(function () {
					$scope.alert("Success!");
					$scope.resetFields();
				},
				function () {
					$scope.alert("Error!");
				});
		};

		$scope.alert = function (message) {
			console.log(message);
		};

		$scope.resetFields = function () {
			$scope.add_theater_name = '';
			$scope.add_theater_disabled = false;
			$scope.add_theater_seats = 0;
		};

		$scope.getCurrentCinemaId = function () {
			return currentCinemaId;
		};

		function fetchMoviesInRepertoire() {

			MovieService.query().$promise.then(
				// success
				function (result) {
					moviesInRepertoire = result;
				},
				// error
				function (error) {
					$rootScope.errorHandler(error);
				}
			);
		}

		 function setCurrentCinemaId() {
			currentCinemaId = $routeParams.id;
		}

		/*
		 //VARIABLES
		 $scope.addableMovies = [];
		 $scope.moviesInRepetoire = [];
		 $scope.cinema = null;

		 //INIT
		 getCurrentCinemaId(function(){
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
		 */
		// PRIVATE


		/*
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
