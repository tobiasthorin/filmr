app.controller('cinemaController', ['$scope', '$rootScope', '$routeParams', 'MovieService', 'TheaterService', 'RepertoireService', 'CinemaService',
	function ($scope, $rootScope, $routeParams, MovieService, TheaterService, RepertoireService, CinemaService) {

		//Local variables
		var moviesInRepertoire;
		var addableMovies;
		var theaters;
		var currentCinema;

		//Publicly accessible variables and functions
		$scope.newTheater = {};

		$scope.getMoviesInRepertoire = function () {
			return moviesInRepertoire;
		};

		$scope.getTheaters = function () {
			return theaters;
		};

		$scope.submitTheater = function () {

			if (!$scope.add_theater_disabled) {
				$scope.add_theater_disabled = false;
			}

			$scope.newTheater.cinema = {id:currentCinema.id};
			$scope.newTheater.name = $scope.add_theater_name;
			$scope.newTheater.disabled = $scope.add_theater_disabled;
			$scope.newTheater.numberOfSeats = $scope.add_theater_seats;

			TheaterService.save($scope.newTheater).$promise.then(function () {
					$scope.alert("Success!");
					$scope.resetFields();
					fetchTheaters();
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

		$scope.getAddableMovies = function () {
			return addableMovies;
		};

		$scope.getCurrentCinemaId = function () {
			return currentCinema.id;
		};

		$scope.addMovieToRepertoire = function () {

			var repertoireId = currentCinema.repertoire.id;
			var movieId = $scope.add_movie_to_repertoire_select.id;

			var updateParams = {"id": repertoireId, "add_movie_with_id": movieId};
			var updateBody = {"id": repertoireId};

			RepertoireService.update(updateParams, updateBody).$promise.then(
				function (result) {
					moviesInRepertoire = result.movies;
				},
				function () {
					$rootScope.errorHandler(error);
				}
			);
		};

		$scope.removeMovieFromRepertoire = function (movieId) {

			var repertoireId = currentCinema.repertoire.id;
			var updateParams = {"id": repertoireId, "remove_movie_with_id": movieId};
			var updateBody = {"id": repertoireId};

			RepertoireService.update(updateParams, updateBody).$promise.then(
				function (result) {
					moviesInRepertoire = result.movies;
				},
				function () {
					$rootScope.errorHandler(error);
				}
			);
		};

		function fetchMoviesInRepertorie() {

			var id = currentCinema.repertoire.id;
			RepertoireService.get({"id": id}).$promise.then(
				// success
				function (result) {
					moviesInRepertoire = result.movies;
				},
				// error
				function (error) {
					$rootScope.errorHandler(error);
				});
		}

		function fetchCurrentCinema(callbackWhenDone) {

			var id = $routeParams.id;

			CinemaService.get({"id": id}).$promise.then(
				function (result) {
					currentCinema = result;
					callbackWhenDone();
				},
				function (error) {
					$rootScope.errorHandler(error);
				});
		}
		
		function fetchAddableMovies() {

			MovieService.query().$promise.then(
				// success
				function (result) {

					addableMovies = result;
				},
				// error
				function (error) {
					$rootScope.errorHandler(error);
				});
		}

		function fetchTheaters() {
			console.log("Getting theaters...");
			TheaterService.query({cinema_id:currentCinema.id}).$promise.then(function (result) {
					theaters = result;
				},
				function (error) {
					$rootScope.errorHandler(error);
				});
		}

		//Execute on page load
		fetchAddableMovies();
		fetchCurrentCinema(function() {
			fetchMoviesInRepertorie();
			fetchTheaters();
		});
	}]);
