var app = angular.module('myApp', ['ngRoute','ngCookies']);


app.config(function($routeProvider){
	$routeProvider
	
	.when('/', {
		templateUrl : 'index.html' ,
		controller : 'HomeController'
		
	})
	
	.when('/login', {
		templateUrl : 'user/login.html' ,
		controller : 'UserController'
	})
	
	.when('/register', {
		templateUrl : 'user/register.html' ,
		controller : 'UserController'
	})
	.otherwise({redirectTo: '/'});
});