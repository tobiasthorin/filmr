'use strict';

app.controller('cinemaController', ['$scope', '$rootScope', '$routeParams', '$location', 'MovieService', 'TheaterService', 'RepertoireService', 'CinemaService',
	function ($scope, $rootScope, $routeParams, $location, MovieService, TheaterService, RepertoireService, CinemaService) {

		//Local variables
		var moviesInRepertoire;
		var addableMovies;
		var theaters;
		var currentCinema;

		//Execute on page load
		fetchCurrentCinema(function() {
			fetchMoviesInRepertorie();
			fetchAddableMovies();
			fetchTheaters();
			setCinemaName();
		});

		//Publicly accessible variables and functions
		$scope.newTheater = {};

		$scope.getMoviesInRepertoire = function () {
			return moviesInRepertoire;
		};

		$scope.getCurrentCinema = function() {
			return currentCinema;
		};

		$scope.getTheaters = function () {
			return theaters;
		};


        $scope.validateScheduleAddTheater = function() {

			if(typeof $scope.theater_name != "string") return false;
			if(!($scope.theater_name.length>0 && $scope.theater_name.length<=48)) return false;

            if(!$scope.number_of_rows) return false;
            if($scope.number_of_rows<1) return false;
			if($scope.number_of_rows>128) return false;

            if(!$scope.number_of_seats) return false;
            if($scope.number_of_seats<1) return false;
			if($scope.number_of_seats>128) return false;

            return true;
        };


		$scope.addNewTheater = function () {

			if(!$scope.validateScheduleAddTheater()) {
				$rootScope.genericError();
				return;
			}

//			TheaterService.save({width: $scope.number_of_seats,number_of_rows: $scope.number_of_rows, name: $scope.theater_name}).$promise.then(
//				function () {
//					$scope.fetchCinemas();
//					$scope.resetFields();
//					$rootScope.alert("Success! ","Cinema "+$scope.newCinema.name+" was created",1);
//				},
//				function(error){
//					$rootScope.errorHandler(error);
//				}
//			);

//			var url = $location.path() + '/theater/new';
  //          $location.path(url).search({width: $scope.number_of_seats,depth: $scope.number_of_rows, name: $scope.theater_name});
			createNewTheater($scope.theater_name,currentCinema.id,$scope.number_of_rows,$scope.number_of_seats);
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

		$scope.validateAddMovieToRepertoire = function() {
			if(typeof $scope.add_movie_to_repertoire_select == "undefined") return false;
			if($scope.add_movie_to_repertoire_select === null) return false;
			return true;
		}

		$scope.addMovieToRepertoire = function () {

			if(!$scope.validateAddMovieToRepertoire()) {
				$rootScope.genericError();
				return;
			}

			var repertoireId = currentCinema.repertoire.id;
			var movieId = $scope.add_movie_to_repertoire_select.id;

			var updateParams = {"id": repertoireId, "add_movie_with_id": movieId};
			var updateBody = {"id": repertoireId};

			RepertoireService.update(updateParams, updateBody).$promise.then(
				function (result) {
					moviesInRepertoire = result.movies;
					fetchAddableMovies();
				},
				function (error) {
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
					fetchAddableMovies();
				},
				function () {
					$rootScope.errorHandler(error);
				}
			);
};

		$scope.getCurrentCinema = function () {
			return currentCinema;
		};

		$scope.updateCinemaName = function () {
			$rootScope.clearAlerts();

			if(!$scope.validateCinemaNameForm()) {
				$rootScope.genericError();
				return;
			}

			currentCinema.name = $scope.edited_cinema_name;
			CinemaService.update(currentCinema).$promise.then(function () {
				setCinemaName();
				$rootScope.alert("Success! ","Cinema was updated",1);
			}, function (error) {
				$rootScope.errorHandler(error);
			});
		};

        //Returns false if edited_cinema_name is falsy
        $scope.validateCinemaNameForm = function () {
			if(typeof $scope.edited_cinema_name!="string") return false;
			if(!($scope.edited_cinema_name.length>0 && $scope.edited_cinema_name.length<=48)) return false;
            return true;
        };

		function fetchMoviesInRepertorie() {

			var id = currentCinema.repertoire.id;
			RepertoireService.get({"id": id}).$promise.then(
				// success
				function (result) {
					moviesInRepertoire = result.movies;
					fetchAddableMovies();
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

			MovieService.query({"not_in_repertoire_with_id": currentCinema.repertoire.id}).$promise.then(
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

		function setCinemaName() {
			$scope.cinema_name = currentCinema.name;
			$scope.edited_cinema_name = currentCinema.name;
		}

		function createNewTheater(name,cinema_id,depth,width) {
			var newTheater = {
				"name": name,
				cinema: {id: cinema_id}
			};

			var theaterParams = {
				number_of_rows: depth,
				max_row_size: width
			};

			TheaterService.save(theaterParams, newTheater).$promise.then(
				function (result) {
					$rootScope.alert("Success! ", "Theater "+name+" was created",1);
					fetchTheaters();
				},
				function (error) {
					$rootScope.errorHandler(error)
				});

			$scope.original_name = newTheater.name;
			$scope.name = newTheater.name;
		}
}]);
