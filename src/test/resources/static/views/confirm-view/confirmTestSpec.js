'use strict';

describe("Tests for seatSelectController.js", function () {

    beforeEach(module('filmr'));

    var $controller, $scope, $routeParams;

    //Mocks

    var fetchedBooking = {
        bookingCinemaName: "name",
        bookingMovieTitle: "movie",
        phoneNumber: "phone"
    };

    $routeParams = {
        showingId: 10,
        bookingId: 42
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
        },
        "get": function (params) {//TODO
            return {
                "$promise": {
                    then: function (success, fail) {
                        fetchedBooking.id = params.id;
                        success(fetchedBooking);
                    }
                }
            }
        }
    };

    //Injections
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
        $scope = {};
        $controller(
            'confirmController', {
                $scope: $scope,
                $routeParams: $routeParams,
                BookingService: BookingService
            });
    }));

    //TESTS

    describe("Getting and setting variables", function () {

        it("Sets the booking id", function () {
            expect($scope.bookingId).toEqual($routeParams.bookingId);
        });
        it("Sets the cinema name", function () {
            expect($scope.cinemaName).toEqual(fetchedBooking.bookingCinemaName);
        });
        it("Sets the phone number", function () {
            expect($scope.phoneNumber).toEqual(fetchedBooking.phoneNumber);
        });
        it("Sets the movie name", function () {
            expect($scope.movieName).toEqual(fetchedBooking.bookingMovieTitle);
        });
    });


});
