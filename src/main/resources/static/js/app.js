angular
	.module('app', [ 'ngRoute', 'ngResource' ])

	.config(function($routeProvider, $httpProvider) {
		$routeProvider
			/**
			 * Home
			 */
			.when('/', {
				redirectTo: '/home'
			})
			.when('/home', {
				templateUrl : 'templates/home.html'
			})
			.when('/login', {
				templateUrl : 'templates/login.html',
				controller : 'Navigation'
			})
			/**
			 * Resources
			 */
			.when('/resource', {
				redirectTo: '/resource/list'
			})
			.when('/resource/list', {
				templateUrl : 'templates/resource/list.html',
				controller : 'ResourceList'
			})
			.when('/resource/add', {
				templateUrl : 'templates/resource/form.html',
				controller : 'ResourceAdd'
			})
			.when('/resource/edit/:id', {
				templateUrl : 'templates/resource/form.html',
				controller : 'ResourceEdit'
			})
			/**
			 * Default
			 */
			.otherwise('/');

		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	})

	.factory("Resource", Resource)

	.controller("Navigation", Navigation)

	.controller("ResourceList", ResourceList)
	.controller("ResourceAdd", ResourceAdd)
	.controller("ResourceEdit", ResourceEdit)
;
