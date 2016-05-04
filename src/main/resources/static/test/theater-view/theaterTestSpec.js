describe("cinemaController.js", function () {

	//Specify module
	beforeEach(module('filmr'));

	// Mocked data
	var fetchedTheater;

	var Mocked$routeParams = {
		'cinema_id': 1,
		'theater_id': 2
	};

	//Mocked services
	beforeEach(function () {

		MockedTheaterService = {
			'get': function (params) {
				return {
					'$promise': {
						then: function (success, fail) {

							fetchedTheater = {
								id: params.id,
								name: 'testTheater',
								numberOfSeats: 330,
								disabled: false,
								cinema: {id: 1}
							};
							success(fetchedTheater);
						}
					}
				}
			},
			'update': function (params) {
				return {
					'$promise': {
						then: function (success, fail) {
							if (params.id && params.name && params.numberOfSeats && params.cinema) {
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

	//Injections, defining which mocked services to use instead of the real ones
	beforeEach(inject(function (_$controller_) {
		$controller = _$controller_;
		$scope = {};

		$controller('theaterController', {
			$scope: $scope, $routeParams: Mocked$routeParams,
			TheaterService: MockedTheaterService
		});
	}));

	/* Test specifications */

	it("Fetches the theater specified in the route", function () {
		expect(fetchedTheater.id).toEqual(Mocked$routeParams.theater_id);
	});

	it("Sets the scope variables to the content of the fetched theater", function () {
		expect($scope.name).toEqual(fetchedTheater.name);
		expect($scope.numberOfSeats).toEqual(fetchedTheater.numberOfSeats);
		expect($scope.isDisabled).toEqual(fetchedTheater.disabled);
	});

	it("Creates a theater object in preparation of submitting it", function () {
		$scope.submitTheater();
		expect($scope.newTheater.id).toEqual(fetchedTheater.id);
		expect($scope.newTheater.name).toEqual(fetchedTheater.name);
		expect($scope.newTheater.numberOfSeats).toEqual(fetchedTheater.numberOfSeats);
		expect($scope.newTheater.disabled).toEqual(fetchedTheater.disabled);
		expect($scope.newTheater.cinema).toEqual(fetchedTheater.cinema);
	});
});
