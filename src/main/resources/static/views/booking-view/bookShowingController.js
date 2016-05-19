/**
 * Created by Marco on 2016-05-18.
 */
'use strict';

angular.module('filmr')
	.controller('bookShowingController',
		['$rootScope', '$scope', '$routeParams', '$location', '$resource','CinemaService','ShowingService','BookingService',
		function ($rootScope, $scope, $routeParams, $location, $resource, CinemaService, ShowingService, BookingService) {


			//Execute on page load
			getCinemas(function(){
				$scope.cinema = $scope.allCinemas[0];
				getTheatersAndRepertoireInCinema();
				getShowingsWithParams();
			});

			//Publicly accessible variables and functions
			$scope.moviesInRepertoire = [];
			$scope.theatersInCinema = [];
			$scope.allShowings = [];
			$scope.allCinemas = [];

			$scope.theater = {};
			$scope.movie = {};
			$scope.movieForShowing = {};
			$scope.cinema= $scope.allCinemas[0];

			$scope.updateShowings = function() {
				console.log("---");
				console.log("updating Showings List");
				getShowingsWithParams();
			};

			$scope.updateCinemaScope = function() {
				console.log("---");
				console.log("Set Cinema, get movies and theaters");
				getTheatersAndRepertoireInCinema();
				getShowingsWithParams();
			};

			$scope.clearAllFilters = function() {
				$scope.fromDate = null;
				$scope.toDate = null;
				$scope.movie = {};
				$scope.theater = {};
				$scope.showingIsDisabled = false;
				getShowingsWithParams();
			};

			function getCinemas(callbackWhenDone) {
				CinemaService.query().$promise.then(
					function(result) {
						console.log("in getCinemas");
						console.log(result);
						$scope.allCinemas = result;
						callbackWhenDone();
					}
				)
			}

			function getTheatersAndRepertoireInCinema() {
				console.log("---");
				console.log("call get cinema");
				CinemaService.get({id:$scope.cinema.id}).$promise.then(
					function(result){
						var cinema = result;
						console.log("Set Movies and Theaters lists")
						$scope.moviesInRepertoire = cinema.repertoire.movies;
						$scope.theatersInCinema = cinema.theaters;

					},
					function(error){
						$rootScope.errorHandler(error);
					}
				)
			}

			function getShowingsWithParams(){
				var params = {
					"only_for_cinema_with_id" : $scope.cinema.id,
					"only_for_theater_with_id" : $scope.theater.id,
					"only_for_movie_with_id" : $scope.movie.id,
					"from_date" : parseDateStringToValidAPIDateString($scope.fromDate),
					"to_date" : parseDateStringToValidAPIDateString($scope.toDate),
					"show_disabled_showings" : $scope.showingIsDisabled,
					"include_empty_slots_for_movie_of_length" : $scope.movieForShowing.lengthInMinutes
				}
				console.log(params);
				console.log("Get Showings with Params");
				ShowingService.query(params).$promise.then(
					function (result){
						console.log("in showings with params");
						console.log(result);
						$scope.allShowings = result;
					},
					function (error) {
						$rootScope.errorHandler(error);
					}
				)
			}

			var parseDateStringToValidAPIDateString = function(f) {
				if(!f) return "";
				var r = f.substr(0,4+3+3);
				r += "T";
				r += f.substr(8+3);
				console.log(r);
				return r;
			}

		}]);

