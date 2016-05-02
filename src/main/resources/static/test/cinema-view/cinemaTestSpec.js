describe("Tests for cinemaController.js", function () {

	beforeEach(module('filmr'));

	beforeEach(function () {

		MockedMovieService = {};
		MockedTheaterService = {};
		MockedRepertoireService = {};
		MockedCinemaService = {};

	});

	//Injections
	beforeEach(inject(function (_$controller_) {
		$controller = _$controller_;
		$scope = {};

'MovieService', 'TheaterService', '$resource', 'RepertoireService', 'CinemaService',

		$controller('cinemaController', {$scope : $scope, MovieService:MockedMovieService, TheaterService:MockedTheaterService, RepertoireService:MockedRepertoireService, CinemaService:MockedCinemaService});
	}));

	//TESTS


	//  - movies / repertoire


	it("Check getter for all movies in repertoire is working on start", function(){

	});

	it("Check getter for all movies that can be addable to repertoire is working on start", function(){

	});


	it("Check save is called with correct params when add movie to repertoire (both movies-in-repertoire and movies-can-be-added must be refreshed after call)", function(){

	});

	it("Check remove movie from repertoire (both movies-in-repertoire and movies-can-be-added must be refreshed after call)", function(){

	});


	//  - theaters

	it("Check getter for all theaters is working on start", function(){

	});


	it("Stores the theater data in an object when submit is clicked", function(){
	});

	it("Sends the theater object to the API via POST", function(){
	});


	it("Logs a success message if the add theater is to API successful", function(){
	});

	it("Logs an error message if the add theater is to API unsuccessful", function(){

	});

	it("Resets the input fields after add theater", function () {

	})

});
