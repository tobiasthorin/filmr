'use strict';

describe("Tests for seatSelectController.js", function () {

    beforeEach(module('filmr'));

    var $controller, $scope, $routeParams;

    //Mocks
    var savedBooking = {};
    var fetchedShowing = {
        showDateTime: "2016-05-12T12:00",
        movie: {
            id: 1,
            title: "Lion King",
            description: "Story about lions",
            lengthInMinutes: 119,
            defaultPrice: 65
        },
        theater: {
            id: 1,
            name: "Lasses Stora",
            rows: [
                {
                    seats: [
                        {id: 1},
                        {id: 2},
                        {id: 3}
                    ]
                }
            ]
        },
        bookings: [
            {
                id: 1,
                bookedSeats: [
                    {id: 1}
                ]
            },
            {
                id: 2,
                bookedSeats: [
                    {id: 2}
                ]
            }
        ]
    };

    var ShowingService = {
        "get": function (params) {
            return {
                "$promise": {
                    then: function (success, fail) {
                        fetchedShowing.id = params.id;
                        success(fetchedShowing);
                    }
                }
            }
        }
    };

    var BookingService = {
        "save": function (params, obj) {
            return {
                "$promise": {
                    then: function (success, fail) {
                        //success?
                    }
                }
            }
        }
    };

    //Injections
    beforeEach(inject(function (_$controller_, _$routeParams_) {
        $controller = _$controller_;
        $scope = {};
        $routeParams = _$routeParams_;
        $controller(
            'seatSelectController', {
                $scope: $scope,
                $routeParams: $routeParams,
                ShowingService: ShowingService,
                BookingService: BookingService
            });
    }));

    //TESTS

    describe("Getting and setting variables", function () {

        it("Fetches the showing from the backend", function () {
            expect($scope.currentShowing).toEqual(fetchedShowing)
        });

        it("References the rows in a separate variable", function () {
            expect($scope.theaterRows).toEqual(fetchedShowing.theater.rows)
        });

        it("References the booked seats in a separate variable", function () {
            expect($scope.bookedSeats.has(1)).toBeTruthy();
            expect($scope.bookedSeats.has(2)).toBeTruthy();
        });
    });

    describe("Selecting seats", function () {

        it("Puts a seat in the selected-seat-list when it is clicked, if it's not booked", function () {
            $scope.toggleSelection(1);
            expect($scope.selectedSeats.has(1)).toBeFalsy();

            $scope.toggleSelection(5);
            expect($scope.selectedSeats.has(5)).toBeTruthy();
        });

        it("Removes a seat from the selected-seat-list when it is clicked", function () {
            $scope.toggleSelection(1);
            $scope.toggleSelection(1);
            expect($scope.selectedSeats.has(1)).toBeFalsy();
        });

        it("Checks if a seat is selected", function () {
            expect($scope.checkIfSelected(4)).toBeFalsy();
            $scope.toggleSelection(4);
            expect($scope.checkIfSelected(4)).toBeTruthy();
        });

        it("Checks if a seat is booked", function () {
            expect($scope.checkIfBooked(1)).toBeTruthy();
            expect($scope.checkIfBooked(99)).toBeFalsy();
        });

        it("Updates the number of selected seats", function () {
            expect($scope.numberOfSelectedSeats).toEqual(0);
            $scope.toggleSelection(10);
            expect($scope.numberOfSelectedSeats).toEqual(1);
            $scope.toggleSelection(9);
            expect($scope.numberOfSelectedSeats).toEqual(2);
        });
    });

    describe("Verifications", function () {
        it("Verifies the phone number", function () {
            $scope.numberOfSelectedSeats = 5;
            $scope.phoneNumber = "";
            expect($scope.validateInputs()).toEqual(false);
            $scope.phoneNumber = "abc";
            expect($scope.validateInputs()).toEqual(false);
            $scope.phoneNumber = 123;
            expect($scope.validateInputs()).toEqual(true);
            $scope.phoneNumber = "0123";
            expect($scope.validateInputs()).toEqual(true);
        });

        it("Verifies the seats selected", function () {
            $scope.phoneNumber = 123;
            $scope.numberOfSelectedSeats = 0;
            expect($scope.validateInputs()).toEqual(false);
            $scope.numberOfSelectedSeats = 5;
            expect($scope.validateInputs()).toEqual(true);
        });

        it("Checks for double bookings", function () {
            $scope.selectedSeats.clear();
            expect($scope.checkBookingConflict()).toEqual(true);
            $scope.selectedSeats.add(1);
            expect($scope.checkBookingConflict()).toEqual(false);
        });
    });


});
