describe("Tests for customerController.js", function () {

	beforeEach(module('filmr'));

	var $controller, $scope, MockedCinemaService, mockedSavedCinema;

	//Mocks
	var mockedCinemas = [
		{name: "TestCinema1", disabled: false},
		{name: "TestCinema2", disabled: false},
		{name: "TestCinema3", disabled: false}
	];

	beforeEach(function () {
		MockedCinemaService = {
			'query': function(params){
				return {
					'$promise': {
						then: function (success, fail) {
							success(mockedCinemas);
						}
					}
				}
			},
			'save': function (params) {
				return {
					'$promise': {
						then: function (success, fail) {
							if (typeof params != 'undefined') {
								success();
								mockedSavedCinema = params;
							} else {
								fail();
							}
						}
					}
				}
			}
		};
	});

	//Injections
	beforeEach(inject(function (_$controller_) {
		$controller = _$controller_;
		$scope = {};
		$controller('customerController', {$scope : $scope, CinemaService: MockedCinemaService});
	}));

	//TESTS

	it("Fetches cinemas from the api and puts them in a variable", function(){
		$scope.fetchCinemas();

		expect($scope.cinemas).toEqual(mockedCinemas);
	});

	it("Stores the input data in an object when submit is clicked", function(){
		$scope.add_cinema_name = "testname";

		expect($scope.add_cinema_disabled).toBeUndefined();

		$scope.submitCinema();

		expect($scope.newCinema.name).toBeDefined();
		expect($scope.newCinema.disabled).toBeDefined();
	});

	it("Sends the data object to the API via POST", function(){
		$scope.add_cinema_name = "testname";
		$scope.add_cinema_disabled = false;

		$scope.submitCinema();

		expect(mockedSavedCinema).toEqual({name: "testname", disabled: false});
	});

	it("Logs a success message if the POST is successful", function(){
		spyOn($scope, 'alert');

		$scope.add_cinema_name = "Cinema name";
		$scope.add_cinema_disabled = false;

		$scope.submitCinema();
		expect($scope.alert).toHaveBeenCalledWith("Success!");
	});

	it("Logs an error message if the POST is unsuccessful", function(){
		spyOn($scope, 'alert');
		$scope.submitCinema();
		expect($scope.alert).toHaveBeenCalledWith("Error!");
	});

	it("Resets the input fields", function () {
		$scope.resetFields();

		expect($scope.add_cinema_name).toEqual("");
		expect($scope.add_cinema_disabled).toEqual(false);
	})
});
