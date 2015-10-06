angular.module('app', [ 'ngRoute', 'ngResource', 'app.services', 'app.controllers' ])
	.config(function($routeProvider, $httpProvider) {

		$routeProvider
			.when('/', {
				templateUrl : 'templates/home.html',
				controller : 'home'
			})
			.when('/login', {
				templateUrl : 'templates/login.html',
				controller : 'navigation'
			})
			.when('/add', {
				templateUrl : 'templates/form.html',
				controller : 'add'
			})
			.when('/edit/:id', {
				templateUrl : 'templates/form.html',
				controller : 'edit'
			})
			.otherwise('/');

		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	});
