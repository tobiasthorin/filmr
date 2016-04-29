describe("Tests for customerController.js", function () {

	beforeEach(module('filmr'));

	var $controller, $scope, MockedCinemaService;

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
		$scope.submitCinema();

		expect($scope.newCinema.name).toBeDefined();
		expect($scope.newCinema.disabled).toBeDefined();
	});

	it("Sends the data object to the API via POST", function(){
		spyOn(MockedCinemaService, 'save');

		$scope.submitCinema(mockedCinemas[0]);

		expect(MockedCinemaService.save).toHaveBeenCalledWith(mockedCinemas[0]);
	});

	it("Logs a success message if the POST is successful", function(){
		$scope.submitCinema();
		expect($scope.logMessage).toEqual("Success!");
	});

	it("Logs an error message if the POST is unsuccessful", function(){
		$scope.submitCinema();
		expect($scope.logMessage).toEqual("Error!");
	});
});