'use strict';

angular.module('filmr')
	.controller('bookShowingController',
		['$rootScope', '$scope', '$routeParams', '$location', '$resource', '$log', 'CinemaService','ScheduleService','BookingService',
		function ($rootScope, $scope, $routeParams, $location, $resource, $log, CinemaService, ScheduleService, BookingService) {


			//Execute on page load
			getCinemas(function(){
				$scope.cinema = $scope.allCinemas[0];
				getTheatersAndRepertoireInCinema();
                refreshPage
			});

			//Publicly accessible variables and functions
			$scope.moviesInRepertoire = [];
			$scope.theatersInCinema = [];
			$scope.allShowings = [];
			$scope.allCinemas = [];

			$scope.theater = {};
			$scope.selectedMovie = {};
			$scope.movieForShowing = {};
			$scope.cinema= $scope.allCinemas[0];

			$scope.updateShowings = function() {
				$log.debug("---");
				$log.debug("updating Showings List");
                refreshPage();
			};

			$scope.updateCinemaScope = function() {
				$log.debug("---");
				$log.debug("Set Cinema, get movies and theaters");
				getTheatersAndRepertoireInCinema();
                refreshPage();
			};

			$scope.clearAllFilters = function() {
				$scope.fromDate = null;
				$scope.toDate = null;
				$scope.selectedMovie = {};
				$scope.theater = {};
				$scope.showingIsDisabled = false;
                refreshPage();
			};

			$scope.setMovie = function (movie) {
				if (movie == $scope.selectedMovie) {
					$scope.selectedMovie = {};
				}
				else {
					$scope.selectedMovie = movie;
				}
                $scope.updateShowings();
			};

            $scope.goToSelectSeat = function(showing) {
                $location.url("book/showing/"+showing.id+"/seat_select");
            };

			function getCinemas(callbackWhenDone) {
				CinemaService.query().$promise.then(
					function(result) {
						$log.debug("in getCinemas");
						$log.debug(result);
						$scope.allCinemas = result;
						callbackWhenDone();
					}
				)
			}

			function getTheatersAndRepertoireInCinema() {
				$log.debug("---");
				$log.debug("call get cinema");
				CinemaService.get({id:$scope.cinema.id}).$promise.then(
					function(result){
						var cinema = result;
						$log.debug("Set Movies and Theaters lists")
						$scope.moviesInRepertoire = cinema.repertoire.movies;
						$scope.theatersInCinema = cinema.theaters;

					},
					function(error){
						$rootScope.errorHandler(error);
					}
				)
			}

            function refreshPage() {
                getShowingsWithParams(function(){
                    refreshDates();
                });
            }

            function refreshDates() {
               var dates = Object.keys($scope.allShowings);
				$log.debug(dates);
                $("td").removeClass("highlight");
                for(var i=0; i<dates.length; i++) {
                    if(dates[i].substr(0,1)=="2") //TODO: this is an hack to make sure we just handle dates from result. We also get other stuff like promise etc
                        $("td[data-date="+dates[i]+"]").addClass("highlight");
                }
            }

			function getShowingsWithParams(callbackWhenDone){
				var params = {
					"only_for_cinema_with_id" : $scope.cinema.id,
					"only_for_theater_with_id" : $scope.theater.id,
					"only_for_movie_with_id" : $scope.selectedMovie.id,
					"from_date" : parseDateStringToValidAPIDateString($scope.fromDate),
					"to_date" : parseDateStringToValidAPIDateString($scope.toDate),
					"show_disabled_showings" : $scope.showingIsDisabled,
					"include_empty_slots_for_movie_of_length" : $scope.movieForShowing.lengthInMinutes
				}
				$log.debug(params);
				$log.debug("Get Showings with Params");
				ScheduleService.query(params).$promise.then(
					function (result){
						$log.debug("in showings with params");
						$log.debug(result);
						$scope.allShowings = result;
                        if(callbackWhenDone)callbackWhenDone();
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
				$log.debug(r);
				return r;
			}
		}]);

