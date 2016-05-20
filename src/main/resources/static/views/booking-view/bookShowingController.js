/**
 * Created by Marco on 2016-05-18.
 */
'use strict';

angular.module('filmr')
	.controller('bookShowingController',
		['$rootScope', '$scope', '$routeParams', '$location', '$resource','CinemaService','ScheduleService','BookingService',
		function ($rootScope, $scope, $routeParams, $location, $resource, CinemaService, ScheduleService, BookingService) {


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
				console.log("---");
				console.log("updating Showings List");
                refreshPage();
			};

			$scope.updateCinemaScope = function() {
				console.log("---");
				console.log("Set Cinema, get movies and theaters");
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

            function refreshPage() {
               console.log("cc"); 
                getShowingsWithParams(function(){
                    refreshDates();
                });
            }

            function refreshDates() {
               console.log("aa"); 
               var dates = Object.keys($scope.allShowings);
                console.log(dates);
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
				console.log(params);
				console.log("Get Showings with Params");
				ScheduleService.query(params).$promise.then(
					function (result){
						console.log("in showings with params");
						console.log(result);
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
				console.log(r);
				return r;
			}
		}]);

