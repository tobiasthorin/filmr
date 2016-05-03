describe("Tests for cinemaController.js", function () {

	beforeEach(module('filmr'));

	beforeEach(function () {

		MockedTheaterService = {};


		MockedMovieService = {
			'query': function(params){
				return {
					'$promise': {
						then: function (success, fail) {
							success(mockedAddableMovies);
						}
					}
				}
			},
		};

		MockedRepertoireService = {
			'get': function(){
				return {
					'$promise': {
						then: function (success, fail) {
							success(mockedRepertoire);
						}
					}					
				}
			},
			'update': function(header,body){
				return {
					'$promise': {
						then: function (success, fail) {
							mockedLastUpdateRepertoirHeader = header;
							mockedLastUpdateRepertoirBody = body;
							success(mockedRepetoireUpdateSuccessResult);
						}
					}					
				}
			},
		};

		MockedCinemaService = {
			'get': function(){
				return {
					'$promise': {
						then: function (success, fail) {
							success(mockedCinema);
						}
					}					
				}
			}
		};

		Mocked$routeParams = {
			'id': 1
		};

	});

	//Injections
	beforeEach(inject(function (_$controller_) {
		$controller = _$controller_;
		$scope = {};


		$controller('cinemaController', {$scope : $scope, $routeParams:Mocked$routeParams,
						MovieService:MockedMovieService, TheaterService:MockedTheaterService, 
						RepertoireService:MockedRepertoireService, CinemaService:MockedCinemaService

		});
	}));


	// MOCKS


	var mockedMoviesInRepertoire = [
		{name: "Lion king"},
		{name: "Lion queen"},
		{name: "Alien"}
	];

	var mockedRepertoire = {
		movies: mockedMoviesInRepertoire
	}

	var mockedAddableMovies = [
		{name:"Mars Attacks"},
		{name:"Deer Hunter"}
	];

	var mockedTheaters = [
		{name:"sal01"},
		{name:"sal02"}
	];

	var mockedCinema = {id: 1, name:"Lasses Biograf", repertoire:{id:567}};

	var mockedLastUpdateRepertoirHeader = null;
	var mockedLastUpdateRepertoirBody = null;
	var mockedRepetoireUpdateSuccessResult = {movies:[ { name: 'Lion king' }, { name: 'Lion queen' }, { name: 'Alien' }, { name: 'Mars Attacks' } ]};

	//TESTS


	//  - movies / repertoire


	it("Check getter for all movies in repertoires is working on start", function(){
		expect($scope.getMoviesInRepertoire()).toEqual(mockedMoviesInRepertoire);
	});

	it("Check getter for all movies that can be addable to repertoire is working on start", function(){
		expect($scope.getAddableMovies()).toEqual(mockedAddableMovies);
	});

	it("Check save is called with correct params when add movie to repertoire (both movies-in-repertoire and movies-can-be-added must be refreshed after call)", function(){
		$scope.add_movie_to_repertoire_select = {id: 123, name:"Mars Attacks"};
		$scope.addMovieToRepertoire();

		expect(mockedLastUpdateRepertoirHeader).toEqual({"id":567,"add_movie_with_id":123});
		expect(mockedLastUpdateRepertoirBody).toEqual({"id":567});

		expect($scope.getMoviesInRepertoire()).toEqual(mockedRepetoireUpdateSuccessResult.movies);

		/*

		TODO: add check on refresh addable movies list

		expect(mockedSavedRepertoire).toEqual([{name: "Lion king"},{name: "Lion queen"},{name: "Alien"},{name:"Mars Attacks"}]);
		expect($scope.getMoviesInRepertoire()).toEqual([{name: "Lion king"},{name: "Lion queen"},{name: "Alien"},{name:"Mars Attacks"}]);
		expect($scope.getAddableMovies()).toEqual([{name:"Deer Hunter"}]);
		*/
	});

	it("Check remove movie from repertoire (both movies-in-repertoire and movies-can-be-added must be refreshed after call)", function(){
		$scope.removeMovieFromRepertoire(178);

		expect(mockedLastUpdateRepertoirHeader).toEqual({"id":567,"remove_movie_with_id":178});
		expect(mockedLastUpdateRepertoirBody).toEqual({"id":567});

		expect($scope.getMoviesInRepertoire()).toEqual(mockedRepetoireUpdateSuccessResult.movies); //note/confusing: the mockedRepetoireUpdateSuccessResult is based on a list of added movies. The same list work here since we are just compareing(toEqual) and mocked service do not actual modify list in a correct way

		//TODO: add check on refresh addable movies list

///		expect(mockedSavedRepertoire).toEqual([{name: "Lion king"},{name: "Lion queen"}]);
//		expect($scope.getMoviesInRepertoire()).toEqual([{name: "Lion king"},{name: "Lion queen"}]);
//		expect($scope.getAddableMovies()).toEqual([{name:"Mars Attacks"},{name:"Deer Hunter"},{name:"Alien"}]);

	});


	//  - theaters

	it("Check getter for all theaters is working on start", function(){
		expect($scope.getTheaters()).toEqual(mockedTheaters);
	});


	it("Stores the theater data in an object when submit is clicked", function(){
		$scope.add_theater_name = "sal03";

		expect($scope.add_theater_disabled).toBeUndefined();

		$scope.submitTheater();

		expect($scope.newTheater.name).toBe("sal03");
		expect($scope.newTheater.disabled).toBe(false);
	});

	it("Sends the theater object to the API via POST", function(){
		$scope.add_theater_name = "sal04";
		$scope.add_theater_disabled = false;
		$scope.submitTheater();

		expect(mockedSavedTheater).toEqual({name: "sal04", disabled: false});
	});


	it("Logs a success message if the add theater is to API successful", function(){
		spyOn($scope, 'alert');

		$scope.add_theater_name = "sal04";
		$scope.add_theater_disabled = false;
		$scope.submitTheater();

		$scope.submitCinema();
		expect($scope.alert).toHaveBeenCalledWith("Success!");
	});

	it("Logs an error message if the add theater is to API unsuccessful", function(){
		spyOn($scope, 'alert');
		$scope.submitTheater();

		$scope.submitCinema();
		expect($scope.alert).toHaveBeenCalledWith("Error!");

	});

	it("Resets the input fields after add theater", function () {
		$scope.resetFields();

		expect($scope.add_theater_name).toEqual("");
		expect($scope.add_theater_disabled).toEqual(false);

	})

});
