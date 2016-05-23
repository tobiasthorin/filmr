'use strict';

angular.module('filmr')
	.controller('bookShowingController',
		['$rootScope', '$scope', '$routeParams', '$location', '$resource', '$log', 'CinemaService','ScheduleService','BookingService',
		function ($rootScope, $scope, $routeParams, $location, $resource, $log, CinemaService, ScheduleService, BookingService) {


			var today = getToday();

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
			$scope.movieForShowing = {};
			$scope.cinema= $scope.allCinemas[0];

			$scope.selectedMovie = null;
			$scope.selectedDates = [];

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
				$scope.selectedMovie = null;
				$scope.theater = {};
				$scope.selectedDates = [];
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

			$scope.setDate = function (date) {
				$log.info("---");
				$log.info("set date");
				$log.info(date);

				var cd = compareDates(today,date);
				$log.debug(cd);
				if(cd==-1) return;


				if (date == $scope.selectedDates) {
					$scope.selectedDates = [];
				}
				else {
					$scope.selectedDates = [date];
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
				$("td").removeClass("selected");

				if($scope.selectedDates && $scope.selectedDates.length==1) {
					console.log("!!!");
					$("td[data-date="+$scope.selectedDates[0]+"]").addClass("selected");
					return;
				}

				if($scope.selectedMovie == null && $scope.selectedDates.length==0) return;
                for(var i=0; i<dates.length; i++) {
                    if(dates[i].substr(0,1)=="2") //TODO: this is an hack to make sure we just handle dates from result. We also get other stuff like promise etc
                        $("td[data-date="+dates[i]+"]").addClass("highlight");
                }
            }

			function getShowingsWithParams(callbackWhenDone){
				$log.debug("---");
				$log.debug("get showings with params");

				var fromDate = today+" 00:00";
				var toDate = null;
				var movieId = $scope.selectedMovie == null ? null : $scope.selectedMovie.id;
				$log.debug($scope.selectedDates);
				if($scope.selectedDates && $scope.selectedDates.length==1) {
					$log.debug($scope.selectedDates[0]);
					var date = $scope.selectedDates[0];
					fromDate = date+" 00:00";
					toDate = date+" 23:59";
				}
				var params = {
					"only_for_cinema_with_id" : $scope.cinema.id,
					"only_for_theater_with_id" : $scope.theater.id,
					"only_for_movie_with_id" : movieId,
					"from_date" : parseDateStringToValidAPIDateString(fromDate),
					"to_date" : parseDateStringToValidAPIDateString(toDate),
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

			function getToday() {
				var d = new Date();
				var month = d.getMonth()+1;
				var date = d.getDate();
				month = month<10 ? "0" + month : month;
				date = date<10 ? "0" + date : date;
				return d.getFullYear()+"-"+month+"-"+date;
			}

			function compareDates(dateA,dateB) {
				if(dateA.substring(0,4)<dateB.substring(0,4)) return 1;
				if(dateA.substring(5,7)<dateB.substring(5,7)) return 1;
				if(dateA.substring(8,10)<dateB.substring(8,10)) return 1;
				if(dateA.substring(0,4)>dateB.substring(0,4)) return -1;
				if(dateA.substring(5,7)>dateB.substring(5,7)) return -1;
				if(dateA.substring(8,10)>dateB.substring(8,10)) return -1;
				return 0;
			}

		}]);

