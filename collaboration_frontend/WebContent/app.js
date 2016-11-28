var app=amgular.module('myapp',['ngRoute']);

app.config(function($routeProvider)
{
	$routeProvider
	
	.when('/',{
		templateUrl:'index.html',
		controller:'homecontroller'
	})
	
	.when('/login',{
		templateUrl:'login.html',
		controller:'logincontroller'
		
	})
	
	.when('/register',{
		templateUrl:'register.html',
		controller:'registercontroller'
	})
	
	.otherwise({redirectto:'/'})
	})